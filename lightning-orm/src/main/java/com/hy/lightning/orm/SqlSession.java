package com.hy.lightning.orm;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface SqlSession {

	/**
	 * 获取数据库连接
	 * @return
	 * @throws IllegalAccessException 
	 */
	Connection getConnection() throws IllegalAccessException;

	/**
	 * 数据库更新操作
	 * @param sql
	 * @param params
	 * @return
	 */
	int updateSql(String sql, Object ...params);

	/**
	 * 查询
	 * @param sql
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> select(String sql, Object ...params);

	/**
	 * 查询一条数据
	 * @param sql
	 * @param params
	 * @return
	 */
	Map<String, Object> selectOne(String sql, Object ...params);

	/**
	 * 插入
	 * @param map
	 * @return
	 */
	int insert(String tableName, Map<String, Object> map);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int[] insertBatch(String tableName, List<Map<String, Object>> list);
	
	/**
	 * 更新
	 * @param map
	 * @return
	 */
	int update(String tableName, Map<String, Object> map);
	
	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	int[] updateBatch(String tableName, List<Map<String, Object>> list);
	
	/**
	 * sql批量更新
	 * @param sql
	 * @param paramsList
	 * @return
	 */
	int[] updateSqlBatch(String sql, List<Object[]> paramsList);
	
	/**
	 * 查询数量
	 * @param sql
	 * @param params
	 * @return
	 */
	int getCount(String sql, Object ...params);
	
	/**
	 * 删除
	 * @param tableName
	 * @param map
	 * @return
	 */
	int delete(String tableName, Map<String, Object> map);
}
