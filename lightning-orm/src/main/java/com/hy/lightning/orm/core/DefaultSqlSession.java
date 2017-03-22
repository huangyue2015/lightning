package com.hy.lightning.orm.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.hy.lightning.orm.SqlSession;
import com.hy.lightning.orm.datasource.DataSource;
import com.hy.lightning.orm.utils.Globle;
import com.mchange.v2.c3p0.impl.NewProxyPreparedStatement;

public class DefaultSqlSession implements SqlSession {

	private Logger logger = Logger.getLogger(DefaultSqlSession.class);

	private String dataSourceKey = Globle.environment();

	public String getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	public DefaultSqlSession(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	public DefaultSqlSession() {

	}

	@Override
	public Connection getConnection() throws IllegalAccessException {
		if (DataSource.dataSourceMap.isEmpty())
			throw new IllegalAccessException("DataSource未被加载");
		try {
			return DataSource.dataSourceMap.get(dataSourceKey).getConnection();
		} catch (SQLException e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public int[] updateSqlBatch(String sql, List<Object[]> paramsList) {
		Connection conection = null;
		PreparedStatement preparedStatement = null;
		int[] relsult = null;
		try {
			conection = getConnection();
			conection.setAutoCommit(false);
			preparedStatement = getPreparedStatementBatch(conection, sql, paramsList);
			relsult = preparedStatement.executeBatch();
			conection.commit();
			conection.setAutoCommit(true);
		} catch (SQLException | IllegalAccessException e) {
			try {
				conection.rollback();
			} catch (SQLException e1) {}
			logger.error(e, e);
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (conection != null)
					conection.close();
			} catch (SQLException e) {

			}
		}
		return relsult;
	}

	@Override
	public int updateSql(String sql, Object... params) {
		Connection conection = null;
		PreparedStatement preparedStatement = null;
		int relsult = 0;
		try {
			conection = getConnection();
			preparedStatement = getPreparedStatement(conection, sql, params);
			relsult = preparedStatement.executeUpdate();
		} catch (SQLException | IllegalAccessException e) {
			logger.error(e, e);
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (conection != null)
					conection.close();
			} catch (SQLException e) {

			}
		}
		return relsult;
	}

	@Override
	public List<Map<String, Object>> select(String sql, Object... params) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> list = null;
		try {
			connection = getConnection();
			preparedStatement = getPreparedStatement(connection, sql, params);
			resultSet = preparedStatement.executeQuery();
			list = new ArrayList<>();
			ResultSetMetaData metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				Map<String, Object> resultMap = new HashMap<>();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					String key = metaData.getColumnLabel(i + 1);
					Object value = resultSet.getObject(i + 1);
					resultMap.put(key, value);
				}
				list.add(resultMap);
			}
		} catch (SQLException | IllegalAccessException e) {
			logger.error(e, e);
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
		}
		return list;
	}

	@Override
	public Map<String, Object> selectOne(String sql, Object... params) {
		if (!sql.toLowerCase().contains("limit")) {
			sql += " LIMIT 1";
		}
		List<Map<String, Object>> list = select(sql, params);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	@Override
	public int insert(String tableName, Map<String, Object> map) {
		Pair pair = new Pair(map);
		String sql = getInsertSqlByMap(tableName, map, pair.keys);
		return updateSql(sql, pair.values);
	}

	@Override
	public int[] insertBatch(String tableName, List<Map<String, Object>> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<Object[]> paramsList = new ArrayList<>();
		Map<String, Object> map0 = list.get(0);
		Pair pair = new Pair(map0);
		String sql = getInsertSqlByMap(tableName, map0, pair.keys);
		for (Map<String, Object> map : list) {
			paramsList.add(new Pair().getValues(pair.keys, map));
		}
		return updateSqlBatch(sql, paramsList);
	}

	@Override
	public int update(String tableName, Map<String, Object> map) {
		if (!map.containsKey("id")) {
			throw new IllegalAccessError("执行map更新是id不能为空");
		}
		Pair pair = new Pair(map);
		String sql = getUpdateSqlByMap(tableName, pair.keys);
		Object[] objs = pair.values;
		return updateSql(sql, objs);
	}

	@Override
	public int[] updateBatch(String tableName, List<Map<String, Object>> list) {
		if (list == null || list.size() == 0)
			return null;
		if (!list.get(0).containsKey("id")) {
			throw new IllegalAccessError("执行map更新是id不能为空");
		}
		Pair pair = new Pair(list.get(0));
		String sql = getUpdateSqlByMap(tableName, pair.keys);

		List<Object[]> paramsList = new ArrayList<>();
		for (Map<String, Object> map : list) {
			Object[] objs = new Pair().getValues(pair.keys, map);
			paramsList.add(objs);
		}
		return updateSqlBatch(sql, paramsList);
	}

	@Override
	public int getCount(String sql, Object... params) {
		Map<String, Object> map = selectOne(sql, params);
		if (map == null)
			return 0;
		for (String key : map.keySet()) {
			if (map.get(key) != null)
				return Integer.valueOf(map.get(key).toString());
		}
		return 0;
	}

	@Override
	public int delete(String tableName, Map<String, Object> map) {
		if (map.containsKey("id")) {
			String id = map.get("id").toString();
			String sql = "DELETE FROM " + tableName + " WHERE id=?";
			return updateSql(sql, id);
		} else {
			Pair pair = new Pair(map);
			String sql = getDeleteSqlByMap(tableName, pair.keys);
			Object[] objs = pair.values;
			return updateSql(sql, objs);
		}
	}

	/****************************************
	 * 私有方法
	 * 
	 * @throws IllegalAccessException
	 **************************************************************/

	private PreparedStatement getPreparedStatement(Connection conection, String sql, Object... params)
			throws SQLException, IllegalAccessException {
		if (conection == null)
			conection = getConnection();
		PreparedStatement preparedStatement = conection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			Object object = params[i];
			preparedStatement.setObject(i + 1, object);
		}
		printLogSql((NewProxyPreparedStatement) preparedStatement);
		return preparedStatement;
	}

	private void printLogSql(NewProxyPreparedStatement ps) {
		if (Globle.isWindos()) {
			try {
				Field f = ps.getClass().getDeclaredField("inner");
				f.setAccessible(true);
				f.setAccessible(true);
				Object sql = f.get(ps);
				if (sql != null)
					logger.info(String.format("-------sql:%s---------", sql));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private PreparedStatement getPreparedStatementBatch(Connection conection, String sql, List<Object[]> paramss)
			throws SQLException, IllegalAccessException {
		if (conection == null)
			conection = getConnection();
		PreparedStatement preparedStatement = conection.prepareStatement(sql);
		for (Object[] params : paramss) {
			for (int i = 0; i < params.length; i++) {
				Object object = params[i];
				preparedStatement.setObject(i + 1, object);
			}
			preparedStatement.addBatch();
			printLogSql((NewProxyPreparedStatement) preparedStatement);
		}
		return preparedStatement;
	}

	private String getInsertSqlByMap(String tableName, Map<String, Object> map, String[] fields) {
		if (map == null || tableName == null)
			throw new IllegalAccessError("inser插入map表名不存在");
		String fileds = Arrays.deepToString(fields).replace("[", "(").replace("]", ")");
		String[] vaules = new String[fields.length];
		for (int i = 0; i < vaules.length; i++) {
			vaules[i] = "?";
		}
		String params = Arrays.deepToString(vaules).replace("[", "(").replace("]", ")");
		String sql = "insert into `%s`%s values %s ";
		return String.format(sql, tableName, fileds, params);
	}

	private String getUpdateSqlByMap(String tableName, String[] keys) {
		StringBuilder sqlTemp = new StringBuilder("UPDATE `" + tableName + "` SET ");
		for (String key : keys) {
			if ("id".equals(key))
				continue;
			sqlTemp.append(key + "=?,");
		}
		String sql = sqlTemp.toString();
		sql = sql.substring(0, sql.length() - 1) + " where id=?";
		return sql;
	}

	private String getDeleteSqlByMap(String tableName, String[] keys) {
		StringBuilder sqlTemp = new StringBuilder("DELETE FROM `" + tableName + "` WHERE 1=1 AND ");
		for (String key : keys) {
			sqlTemp.append(key + "=? AND ");
		}
		String sql = sqlTemp.toString().trim();
		sql = sql.substring(0, sql.lastIndexOf("AND"));
		return sql;
	}

	private class Pair {
		String[] keys;
		Object[] values;

		public Pair() {};

		public Pair(Map<String, Object> map) {
			List<String> keyList = new ArrayList<>();
			List<Object> valueList = new ArrayList<>();
			for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if ("id".equals(key) || value == null)
					continue;
				keyList.add(key);
				valueList.add(value);
			}
			if (map.get("id") != null) {
				keyList.add("id");
				valueList.add(map.get("id"));
			}
			keys = keyList.toArray(new String[keyList.size()]);
			values = valueList.toArray(new Object[valueList.size()]);
		}

		public Object[] getValues(String[] keys, Map<String, Object> map) {
			values = new Object[keys.length];
			int i = 0;
			for (String key : keys) {
				values[i] = map.get(key);
				i++;
			}
			return values;
		}
	}
}
