package com.hy.lightning.orm.datasource;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hy.lightning.orm.IDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource implements IDataSource {
	
	public static Map<String, ComboPooledDataSource> dataSourceMap = new HashMap<>();
	
	public DataSource() {
	}

	private Configuration getConfiguration(Element e) {
		String key = e.attributeValue("key");
		String name = e.attributeValue("name");
		String ip = e.attributeValue("ip");
		String port = e.attributeValue("port");
		String username = e.attributeValue("username");
		String password = e.attributeValue("password");
		Configuration configuration = new Configuration();
		if (key != null)
			configuration.setKey(key);
		if (name != null)
			configuration.setName(name);
		if (ip != null)
			configuration.setIp(ip);
		if (port != null)
			configuration.setPort(port);
		if (username != null)
			configuration.setUsername(username);
		if (password != null)
			configuration.setPassword(password);
		return configuration;
	}
	
	@Override
	public DataSource loadDataSource(String xmlPath) {
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new File(xmlPath));
			Element root = document.getRootElement();
			List<?> elements = root.elements();
			for (Iterator<?> it = elements.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				Configuration configuration = getConfiguration(e);
				loadDataSource(configuration);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public DataSource loadDataSource(Configuration conf) {
		dataSourceMap.put(conf.getKey(), getDataSource(conf));
		return this;
	}
	
	private ComboPooledDataSource getDataSource(Configuration configuration) {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		String url = configuration.getUrl();
		dataSource.setJdbcUrl(String.format(url, configuration.getIp(), configuration.getPort(), configuration.getName()));
		try {
			dataSource.setDriverClass(configuration.getDriverClassName());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		dataSource.setUser(configuration.getUsername());
		dataSource.setPassword(configuration.getPassword());
		dataSource.setMaxPoolSize(configuration.getMaxPoolSize());
		dataSource.setMinPoolSize(configuration.getMinPoolSize());
		dataSource.setInitialPoolSize(configuration.getInitialPoolSize());
		dataSource.setMaxIdleTime(configuration.getMaxIdleTime());
		dataSource.setCheckoutTimeout((int)configuration.getCheckoutTimeout());
		dataSource.setAcquireIncrement(configuration.getAcquireIncrement());
		dataSource.setAcquireRetryAttempts(configuration.getAcquireRetryAttempts());
		dataSource.setAcquireRetryDelay((int)configuration.getAcquireRetryDelay());
		dataSource.setAutoCommitOnClose(configuration.isAutoCommitOnClose());
		dataSource.setBreakAfterAcquireFailure(configuration.isBreakAfterAcquireFailure());
		dataSource.setIdleConnectionTestPeriod(configuration.getIdleConnectionTestPeriod());
		dataSource.setMaxStatements(configuration.getMaxStatements());
		dataSource.setMaxStatementsPerConnection(configuration.getMaxStatementsPerConnection());
		return dataSource;
	}
}
