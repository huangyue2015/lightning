package com.hy.lightning.orm.datasource;

public class Configuration {
	private String key="local";
	private String driverClassName = "com.mysql.jdbc.Driver";
	private String ip = "192.168.145.128";
	private String port = "3306";
	private String name = "tasty_0306";
	private String url = "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false";
	private String username = "root";
	private String password = "123456";
	
	private int maxPoolSize = 20;//连接池中保留的最大连接数。默认值: 15
	private int minPoolSize = 5;//连接池中保留的最小连接数，默认为：3
	private int initialPoolSize = 10;//初始化连接池中的连接数，取值应在minPoolSize与maxPoolSize之间，默认为3
	private int maxIdleTime = 0;//最大空闲时间，60秒内未使用则连接被丢弃。若为0则永不丢弃。默认值: 0
	private long checkoutTimeout = 3000;//当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException，如设为0则无限期等待。单位毫秒。默认: 0
	private int acquireIncrement = 2;//当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。默认值: 3
	private int acquireRetryAttempts= 30;//定义在从数据库获取新连接失败后重复尝试的次数。默认值: 30 ；小于等于0表示无限次
	private long acquireRetryDelay= 1000;//重新尝试的时间间隔，默认为：1000毫秒
	private boolean autoCommitOnClose = false;//关闭连接时，是否提交未提交的事务，默认为false，即关闭连接，回滚未提交的事务
	private boolean breakAfterAcquireFailure = false;//如果为false，则获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常，但是数据源仍有效保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。默认: false
	private int idleConnectionTestPeriod = 0; //每60秒检查所有连接池中的空闲连接。默认值: 0，不检查
	private int maxStatements = 0;//c3p0全局的PreparedStatements缓存的大小。如果maxStatements与maxStatementsPerConnection均为0，则缓存不生效，只要有一个不为0，则语句的缓存就能生效。如果默认值: 0
	private int maxStatementsPerConnection = 0;//maxStatementsPerConnection定义了连接池内单个连接所拥有的最大缓存statements数。默认值: 0
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public long getCheckoutTimeout() {
		return checkoutTimeout;
	}

	public void setCheckoutTimeout(long checkoutTimeout) {
		this.checkoutTimeout = checkoutTimeout;
	}

	public int getAcquireIncrement() {
		return acquireIncrement;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public long getAcquireRetryDelay() {
		return acquireRetryDelay;
	}

	public void setAcquireRetryDelay(long acquireRetryDelay) {
		this.acquireRetryDelay = acquireRetryDelay;
	}

	public boolean isAutoCommitOnClose() {
		return autoCommitOnClose;
	}

	public void setAutoCommitOnClose(boolean autoCommitOnClose) {
		this.autoCommitOnClose = autoCommitOnClose;
	}

	public boolean isBreakAfterAcquireFailure() {
		return breakAfterAcquireFailure;
	}

	public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		this.breakAfterAcquireFailure = breakAfterAcquireFailure;
	}

	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public int getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(int maxStatements) {
		this.maxStatements = maxStatements;
	}

	public int getMaxStatementsPerConnection() {
		return maxStatementsPerConnection;
	}

	public void setMaxStatementsPerConnection(int maxStatementsPerConnection) {
		this.maxStatementsPerConnection = maxStatementsPerConnection;
	}

	public Configuration(){}
	
	public Configuration(String name){
		this.name = name;
	}
	
	public Configuration(String key, String name) {
		this.key = key;
		this.name = name;
	}
	
	public Configuration(String key, String ip, String port, String name, String username, String password) {
		this.key = key;
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.name = name;
	}
}
