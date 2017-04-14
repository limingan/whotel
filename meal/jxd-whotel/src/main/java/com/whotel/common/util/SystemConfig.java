package com.whotel.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * parse system config file, and retrieve property value by given key
 * 
 * @author
 * 
 */
public class SystemConfig {
	private static final Logger log = LoggerFactory
			.getLogger(SystemConfig.class);

	private static Properties props = new Properties();
	private static String configPath;
	private static String classPath;
	private static String appRoot;
	private static String appAbsolutePath;
	private static boolean isWindows;
	private static String resPath;

	private SystemConfig() {
	}

	static {
		initialize();
	}

	public static boolean isLinux() {
		return !isWindows;
	}

	public static void initialize() {
		String osType = System.getProperty("os.name");
		isWindows = osType == null || osType.toUpperCase().indexOf("WINDOWS") > -1;
		if (isWindows) {
			classPath = SystemConfig.class.getResource("/").getPath();
			classPath = classPath.replace("file:", "");
			int pos = classPath.indexOf("/classes/");
			if (pos > -1)
				classPath = classPath.substring(1, pos + 9);
		} else {
			classPath = SystemConfig.class.getResource("").getPath();
			classPath = classPath.replace("file:", "");
			int pos = classPath.indexOf("/classes/");
			if (pos == -1) {
				pos = classPath.indexOf("/lib/");
				if (pos > -1)
					classPath = classPath.substring(0, pos + 5);
			} else {
				classPath = classPath.substring(0, pos + 9);
			}
		}
		classPath = classPath.replaceAll("/lib/", "/classes/");

		// configPath = classPath + "config/";
		configPath = classPath;
		appAbsolutePath = classPath.replaceAll("/WEB-INF/classes/", "");
		// appRoot = appAbsolutePath.substring(appAbsolutePath.lastIndexOf("/"));
		try {
			classPath = java.net.URLDecoder.decode(classPath, "UTF-8");
		} catch (Exception e) {
			// do nothing
		}
		IOFileFilter fileFilter = new WildcardFileFilter("application-*.properties");
		Collection<File> files = FileUtils.listFiles(new File(configPath), fileFilter, null);
		if (files != null) {
			for (File file : files) {
				loadProperties(file);
			}
		}

		appRoot = props.getProperty("app.root", "/");
		resPath = props.getProperty("static.resource.root", "/");
		// System.out.println("classPath==========="+classPath);
		// System.out.println("configPath=========="+configPath);
		// System.out.println("appAbsolutePath====="+appAbsolutePath);
		// System.out.println("appRoot============="+appRoot);
	}

	public static void reloadProperties() {
		props.clear();
		IOFileFilter fileFilter = new WildcardFileFilter("application-*.properties");
		Collection<File> files = FileUtils.listFiles(new File(configPath), fileFilter, null);
		if (files != null) {
			for (File file : files) {
				loadProperties(file);
			}
		}
	}

	public static Properties getProperties() {
		return props;
	}

	public static String getResPath() {
		return resPath;
	}

	public static String getProperty(String key, String defaultProperty) {
		return props.getProperty(key, defaultProperty);
	}

	public static String getProperty(String key, Object... args) {
		String pattern = props.getProperty(key);
		if (pattern == null)
			return "No such property with key: " + key;
		return MessageFormat.format(pattern, args);
	}

	public static String getConfigPath() {
		return configPath;
	}

	public static String getClassPath() {
		return classPath;
	}

	public static String getAppRoot() {
		return appRoot;
	}

	public static String getAppAbsolutePath() {
		return appAbsolutePath;
	}

	private static void loadProperties(File propertyFile) {
		log.info("Loading " + propertyFile.getAbsolutePath());
		InputStream ins = null;
		try {
			ins = new FileInputStream(propertyFile);
			props.load(ins);
			ins.close();
		} catch (Exception e) {
			System.out.println("Can't found system config file: " + propertyFile.getAbsolutePath() + ", please check." + e);
		} finally {
			try {
				if (ins != null)
					ins.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}

}
