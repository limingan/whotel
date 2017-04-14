package com.whotel.common.dao.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.mongodb.DBAddress;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;

/**
 * Mongo工厂类
 * 
 * @author
 * 
 */
public class MongoFactoryBean implements InitializingBean, DisposableBean {
	private static final Logger logger = LoggerFactory
			.getLogger(MongoFactoryBean.class);

	protected Morphia morphia;

	private MongoClient mongo; // Mongo对象
	private String database; // 库名
	private String username; // 连接用户名
	private String password; // 连接密码
	public int connectionsPerHost = Integer.parseInt(System.getProperty(
			"MONGO.POOLSIZE", "10")); // 连接池大小
	private String url;
	private String host; // 主机
	private int port = 0; // 服务端口
	private int maxWaitTime = 1000 * 60 * 2; // 最大等待连接毫秒
	private int connectTimeout = 0; // 连接超时，0表示永不超时
	private int socketTimeout = 0; // socket超时，0表示永不超时
	private int threadsAllowedToBlockForConnectionMultiplier;

	/**
	 * 自动扫描带@Entity注解的实体类包名
	 */
	private String[] entityPackages;

	public MongoClient getMongo() {
		return this.mongo;
	}

	public Morphia getMorphia() {
		return morphia;
	}

	public String getDatabase() {
		return database;
	}

	public void setConnectionsPerHost(int connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setDatabase(String dbName) {
		this.database = dbName;
	}

	@Required
	public void setEntityPackages(String[] entityPackages) {
		this.entityPackages = entityPackages;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(url != null || host != null,
				"Must specify the mongodb address!");
		buildMongoFactory();
	}

	public void buildMongoFactory() throws UnknownHostException {
		logger.debug("Building mongodb factory.");
		List<ServerAddress> dbAddresses = new ArrayList<ServerAddress>();
		if (url != null) {
			String[] addresses = url.split(",");
			for (String address : addresses) {
				DBAddress sa = new DBAddress(address);
				host = sa.getHost();
				port = sa.getPort();
				database = sa.getDBName();
				dbAddresses.add(sa);
			}
		} else {
			if (port == 0) {
				dbAddresses.add(new ServerAddress(host));
			} else {
				dbAddresses.add(new ServerAddress(host, port));
			}
		}
		
		Builder builer = MongoClientOptions.builder();
		
		builer.connectionsPerHost(this.connectionsPerHost);
		builer.connectTimeout(this.connectTimeout);
		builer.maxWaitTime(this.maxWaitTime);
		builer.socketTimeout(this.socketTimeout);
		builer.threadsAllowedToBlockForConnectionMultiplier(this.threadsAllowedToBlockForConnectionMultiplier);
		
		MongoClientOptions options = builer.build();
		logger.info("Building mongodb factory." + options);
		
		
		if(StringUtils.isNotBlank(password)) {
			List<MongoCredential> credentialsList = new ArrayList<>();
			credentialsList.add(MongoCredential.createCredential(username, database, password.toCharArray()));
			mongo = new MongoClient(dbAddresses, credentialsList, options);
		} else {
			mongo = new MongoClient(dbAddresses, options);
		}
		
		morphia = new Morphia();
		for (String pkg : entityPackages) {
			morphia.mapPackage(pkg);
		}
	}

	@Override
	public void destroy() throws Exception {
		mongo.close();
	}

}
