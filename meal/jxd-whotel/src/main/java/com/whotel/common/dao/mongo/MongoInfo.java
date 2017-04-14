package com.whotel.common.dao.mongo;

/**
 * Mongo数据库信息
 * 
 * @author
 * 
 */
public class MongoInfo {
	private String version;
	private String host;
	private int port;
	private String databaseName;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

}
