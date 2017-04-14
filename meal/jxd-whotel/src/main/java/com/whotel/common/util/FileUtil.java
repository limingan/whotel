package com.whotel.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件系统操作工具类
 * 
 * @author
 * 
 */
public class FileUtil extends org.apache.commons.io.FileUtils {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * The buffer.
	 */
	protected static byte buf[] = new byte[1024];

	private FileUtil() {}
	
	/**
	 * 检查文件名是否合法.文件名字不能包含字符\/:*?"<>|
	 * 
	 * @param fileName文件名
	 * @return boolean is valid file name
	 */
	public static final boolean isValid(String filename) {
		String regex = "(.*[\\\\|/]{1})*(.+\\.+.+)$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher match = pattern.matcher(filename);
		if (match.find() && match.groupCount() == 2) {
			String name = match.group(2);
			String illegalRegex = "\\\\+|/+|:+|\\*+|\\?+|\"+|<+|>+"; // 非法字符
			pattern = Pattern.compile(illegalRegex, Pattern.CASE_INSENSITIVE);
			match = pattern.matcher(name);
			if (match.find()) {
				return false;
			}
			return true;
		}
		return false;

	}

	/**
	 * 把非法文件名转换为合法文件名.
	 * 
	 * @param fileName
	 * @return
	 */
	public static final String replaceInvalidFileChars(String fileName) {
		StringBuffer out = new StringBuffer();

		for (int i = 0; i < fileName.length(); i++) {
			char ch = fileName.charAt(i);
			// Replace invlid chars: \\/:*?\"<>|
			switch (ch) {
			case '\\':
			case '/':
			case ':':
			case '*':
			case '?':
			case '\"':
			case '<':
			case '>':
			case '|':
				out.append('_');
				break;
			default:
				out.append(ch);
			}
		}

		return out.toString();
	}

	/**
	 * <pre>
	 * Convert a given file name to a URL(URI) string.
	 * For example:
	 * Convert data.csv  ====>  file:/D:/projects/eclipse-jee/galaxy-commons/data.csv
	 * Convert D:/project/data.csv  ====>  file:/D:/project/data.csv
	 * 
	 * @param fileName - the file to parse
	 * @return - URL string
	 */
	public static final String filePathToURL(String fileName) {
		String fileUrl = new File(fileName).toURI().toString();
		return fileUrl;
	}

	/**
	 * Write InputSteam to specified file
	 * 
	 * @param filename
	 * @param in
	 * @throws IOException
	 */
	public static final void writeToFile(String filename, InputStream in) throws IOException {
		writeToFile(new File(filename), in);
	}

	/**
	 * Write InputSteam to specified file.
	 * 
	 * @param file
	 * @param in
	 * @throws IOException
	 */
	public static final void writeToFile(File file, InputStream in) throws IOException {
		OutputStream out = null;
		try {
			out = org.apache.commons.io.FileUtils.openOutputStream(file);
			IOUtils.copy(in, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * <pre>
	 * Get file extension name.
	 * For example:
	 * read.txt  got txt
	 * 
	 * @param filename
	 * @return
	 */
	public static final String getFilenameExtension(String filename) {
		if (filename.indexOf(".") < 0) {
			return null;
		}
		return filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
	}

	/**
	 * 目录下所有内容
	 * 
	 * @param oPath 目录
	 */
	private static final void deleteFolder(final File oPath) {
		final File[] dirs = oPath.listFiles();
		if (dirs != null) {
			for (final File oSubPath : dirs) {
				if (oSubPath.isDirectory()) {
					deleteFolder(oSubPath);
				}
			}
		}
		oPath.delete();
	}

	/**
	 * 递归删除目录下所有内容
	 * 
	 * @param[in] sPath 目录
	 */
	public static final void deleteFolder(final String sPath) {
		final File oPath = new File(sPath);
		if (!oPath.exists() || !oPath.isDirectory()) {
			return;
		}
		deleteFolder(oPath);
	}

	/**
	 * 删除指定文件，不会删除目录
	 * 
	 * @param filename
	 */
	public static final void deleteFile(String filename) {
		final File file = new File(filename);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	/**
	 * 创建多级目录
	 * 
	 * @param[in] sPath 目录
	 * @return 是否创建成功
	 */
	public static final boolean createFolder(final String sPath) {
		try {
			final File oPath = new File(sPath);
			if (!oPath.exists()) {
				oPath.mkdirs();
			}
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param[in] sFile1
	 * @param[in] sFile2
	 * @throws IOException
	 */
	public static final void copyFile(final String sourceFile, final String targetFile) throws IOException {
		final File oFile1 = new File(sourceFile);
		if (oFile1.exists()) {
			final String sPath = targetFile.substring(0, targetFile.lastIndexOf('\\'));
			createFolder(sPath); // 确保目标目录存在

			final File oFile2 = new File(targetFile);
			final RandomAccessFile inData = new RandomAccessFile(oFile1, "r");
			final RandomAccessFile opData = new RandomAccessFile(oFile2, "rw");
			final FileChannel inChannel = inData.getChannel();
			final FileChannel opChannel = opData.getChannel();
			inChannel.transferTo(0, inChannel.size(), opChannel);
			// =========================上一行代码与下面的代码功能相同=========================
			// final long size = inChannel.size();
			// final MappedByteBuffer buf =
			// inChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
			// opChannel.write(buf);
			// =================================================================
			inChannel.close();
			inData.close();
			opChannel.close();
			opData.close();
		}
	}

	/**
	 * 根据上下文环境获取真实路径
	 * 
	 * @param servletContext
	 * @param path
	 * @return
	 */
	public static final String getRealPath(ServletContext servletContext, String path) {
		File file = new File(path);
		return file.isAbsolute() ? path : servletContext.getRealPath(path);
	}

	/**
	 * 根据基本路径获取相对路径
	 * 
	 * @param basePath
	 * @param filename
	 * @return
	 */
	public static final String getRelationPath(String basePath, String filename) {
		int beginIndex = filename.indexOf(basePath);
		int endIndex = filename.lastIndexOf(File.separator);
		return filename.substring(beginIndex, endIndex + 1);
	}

	/**
	 * 返回指定类所在的路径
	 * 
	 * @param clazz
	 * @return /D:/projects/eclipse-jee/galaxy-commons/target/test-classes/
	 */
	public static final String getClassRootPath(Class<?> clazz) {
		String path = getDirFromClassLoader(clazz);
		if (path == null) {
			path = System.getProperty("user.dir");
		}
		return path;
	}

	/**
	 * <pre>
	 * 从通过Class Loading计算路径： 
	 * 1 class文件通过jar包加载： 如果为jar包，该包为d:/test/myProj.jar
	 * 该方法返回d:/test这个目录（不受用户工作目录影响） 
	 * 提示：在jar包加载的时候，通过指定加载FileUtil的class资源得到jar:<url>!/{entry}计算出加载路径
	 * 2 class文件直接被加载：
	 * 如果是web工程 ,class文件放在D:\tools\apache-tomcat-5.5.27\webapps\webProj\WEB-INF\classes，
	 * 该方法返回D:\tools\apache-tomcat-5.5.27\webapps\webProj\WEB-INF，即返回放class文件夹的上一层目录。
	 * */
	private static final String getDirFromClassLoader(Class<?> clazz) {
		try {
			String path = clazz.getName().replace(".", "/");
			path = "/" + path + ".class";
			URL url = clazz.getResource(path);
			String classPath = url.getPath();
			if (classPath.startsWith("file:")) {
				if (classPath.length() > 5) {
					classPath = classPath.substring(5);
				}
				classPath = classPath.split("!")[0];

			} else {
				classPath = clazz.getResource("/").toString().substring(5);
			}
			return classPath;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param jarPath
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static final InputStream getInputStreamFromJar(String jarPath, String filePath) throws IOException {
		JarFile jarFile = new JarFile(jarPath);
		JarEntry jarEntry = jarFile.getJarEntry(filePath);
		InputStream in = jarFile.getInputStream(jarEntry);
		
		if(jarFile != null) {
			jarFile.close();
		}
		return in;
	}

	/**
	 * 关闭IO对象
	 * 
	 * @param io
	 */
	public static final void closeIO(Closeable... io) {
		for (Closeable aIO : io) {
			if (aIO != null)
				try {
					aIO.close();
				} catch (Exception ex) {
					log.error("Close io failed!", ex);
				}
		}
	}

}
