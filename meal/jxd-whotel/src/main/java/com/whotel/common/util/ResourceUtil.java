package com.whotel.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.whotel.common.base.Constants;

/**
 * 资源文件
 * 
 */
public class ResourceUtil {
	private static Logger log = LoggerFactory.getLogger(ResourceUtil.class);
	private static String appServer = null;
	// private static String resourceServer = null;
	private static String resourceDomain = null;
	private static String resourceType = null;
	private static String remoteServerSrcRootPath = null; // for write remote
															// file purpose
	private static String resourceRootPath = null;
	private static boolean imgPrcsEnable = false;
	private static String imgPrcsHome = null;
	private static int imgPrcsPort = 0;
	private static String hostIP = null;
	private static String sshPort = "";

	private static Properties props = new Properties();
	private static String classPath;
	private static String appAbsolutePath; // for write local file purpose
	private static boolean isWindows;
	private static boolean isRemote;
	public static final String uploadDir = "upload";
	public static final String draftDir = "draft";

	// Environment File Operations
	// {0}=port; {1}=host; {2}=realPath; {3}=destFilePath
	// private static final String LINUM_SSH_MV =
	// "ssh -p {0} {1} mv -avx {2} {3}";
	// {0}=port; {1}=host; {2}=realPath; {3}=destFilePath
	private static final String LINUX_SSH_CP = "ssh -p {0} {1} cp -avx {2} {3}";

	private static final String LINUX_SSH_MV = "ssh -p {0} {1} mv -f {2} {3}";

	private static final String LINUX_SSH_RM = "ssh -p {0} {1} rm -f {2}";

	// {0}=port; {1}=host; {2}=Directory
	private static final String LINUX_SSH_MKDIR = "ssh -p {0} {1} mkdir -p {2}";
	// {0}=port; {1}=Source Path; {2}=Target Path
	private static final String LINUX_SCP_CP = "scp -P {0} {1} {2}";

	// {0}=Source file; {1}=Target file or folder
	// private static final String LINUX_COPY = "cp {0} {1}";
	// {0}=Source file; {1}=Target file or folder
	private static final String LINUX_MOVE = "mv {0} {1}";
	// {0}=Source file; {1}=Target file or folder
	// private static final String WINDOWS_COPY = "copy {0} {1}";
	// {0}=Source file; {1}=Target file or folder
	private static final String WINDOWS_MOVE = "move {0} {1}";

	static {
		initialize();
		appServer = props.getProperty("app.server.url", "/");
		if (!StringUtils.endsWith(appServer, "/")) {
			appServer = appServer + "/";
		}
		imgPrcsEnable = Boolean.parseBoolean(props.getProperty("imgprcs.enable"));
		imgPrcsHome = props.getProperty("imgprcs.home");
		imgPrcsPort = Integer.parseInt(props.getProperty("imgprcs.port", "8888"));
		// Sample:
		// resourceDomain = http://{0}.icloudxiao.com/
		// resourceType = img
		// resourceServer = http://img.icloudxiao.com/
		resourceDomain = props.getProperty("resource.server.domain", "http://{0}.icloudxiao.com/");
		if (!StringUtils.endsWith(resourceDomain, "/")) {
			resourceDomain = resourceDomain + "/";
		}
		resourceType = props.getProperty("resource.type", "img");
		// resourceServer = MessageFormat.format(resourceDomain, resourceType);

		// something like: user@127.0.0.1:/usr/local/www
		remoteServerSrcRootPath = props.getProperty("resource.server.root.path." + resourceType, "");
		isRemote = StringUtils.isNotBlank(remoteServerSrcRootPath) && remoteServerSrcRootPath.contains("@");
		if (isRemote) {
			hostIP = remoteServerSrcRootPath.substring(remoteServerSrcRootPath.indexOf('@') + 1,
					remoteServerSrcRootPath.indexOf(':'));
			sshPort = props.getProperty("ssh.port", "22");

			resourceRootPath = remoteServerSrcRootPath.substring(remoteServerSrcRootPath.indexOf(':') + 1);
		}
	}
	
	private ResourceUtil() {}

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

		appAbsolutePath = classPath.replaceAll("/WEB-INF/classes/", "");
		// System.out.println("appAbsolutePath=" + appAbsolutePath);
		// appRoot =
		// appAbsolutePath.substring(appAbsolutePath.lastIndexOf("/"));
		try {
			classPath = java.net.URLDecoder.decode(classPath, "UTF-8");
		} catch (Exception ex) {
			log.error("Error:", ex);
		}

		loadProperties(classPath + "application-prd.properties");
		loadProperties(classPath + "application-test.properties");
		loadProperties(classPath + "application-dev.properties");
		loadProperties(classPath + "application-unit.properties");

	}

	private static void loadProperties(String propertyFile) {
		InputStream ins = null;
		try {
			ins = new FileInputStream(propertyFile);
			props.load(ins);
		} catch (Exception ex) {
			// do nothing
		} finally {
			if(ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtName(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}

	/**
	 * 在原文件名后面加上字串，扩展名不变
	 * 
	 * @param filename - 包含扩展名
	 * @param suffix - 字串
	 * @return
	 */
	public static String filenameAppend(String oldfullname, String suffix) {
		return oldfullname.substring(0, oldfullname.lastIndexOf(".")) + suffix + getExtName(oldfullname);
	}

	/**
	 * 保存文件
	 * 
	 * @param file - 被保存的文件
	 * @param type - 文件分类，决定了文件保存时存放的分类目录
	 * @param oldFilePath - 如不为空，则保存文件为此名，否则用UUID，如果带有路径，会自动去除路径信息
	 * @param draftDir - 草稿目录，为空则不用
	 * @return the URL of this file
	 */
	public static String saveFile(File file, String fileName, String type, String oldFilePath, boolean isDraft) {

		FilePath filePath = saveLocalFile(file, fileName, type, oldFilePath, isDraft);
		String retVal = null;
		boolean success = scp(filePath.getLocalFilePath(), filePath.getDesFilePath(), true); // 复制本地文件到远程WEB服务器指定目录下
		retVal = success ? filePath.getServerPath() : null;
		return retVal;
	}

	/**
	 * 指定缩放比例保存文件
	 * 
	 * @param file
	 * @param type
	 * @param oldFilePath
	 * @param isDraft
	 * @param width
	 * @param height
	 * @return
	 */
	public static String saveFile(File file, String fileName, String type, String oldFilePath, boolean isDraft, int width,
			int height, Boolean widthRatio, Boolean saveOri) {

		FilePath filePath = saveLocalFile(file, fileName, type, oldFilePath, isDraft);
		if (filePath == null) {
			return null;
		}
		String localFilePath = filePath.getLocalFilePath();
		boolean success = false;
		String retVal = null;
		if (saveOri) {
			if (isWindows) { // 支持windows生成缩略图片
				File thumbDir = new File(filePath.getThumbnailDir());
				if (thumbDir == null || (!thumbDir.exists() && !thumbDir.mkdir())) {
					log.error("无法创建目录：" + filePath.getThumbnailDir());
				}
				saveFileToLocal(file, filePath.getThumbnailPath());
				localFilePath = filePath.getThumbnailPath();
			}
			scp(filePath.getLocalFilePath(), filePath.getDesFilePath(), false); // 保存原图

			if (imgPrcsEnable) {
				// filePath.getDesFilePath() = kelvin@eqianxian.com:/data/www/docroot/test_res2/hongbao/201401/sample_a.jpg
				String commandFile = filePath.getDesFilePath().substring(imgPrcsHome.length()).replace(File.separatorChar, '+');
				// get test_res2+hongbao+201401:sample_a.jpg
				commandFile = StringUtils.substringBeforeLast(commandFile, "+") + ":" + StringUtils.substringAfterLast(commandFile, "+");
				// test_res2+hongbao+201401:sample_a.jpg:resize=WxH.U
				commandFile = commandFile + ":thumb=" + width + "x" + height + ".U";
				scp(filePath.getLocalFilePath(), imgPrcsHome + File.separator + commandFile, true);
				success = imageProcess(commandFile);	
			} else {
				ImageUtil.resize(localFilePath, width, height, widthRatio, false);
				log.info("upload image, resize:" + width + "," + height);
				success = scp(filePath.getLocalFilePath(), filePath.getThumbnailPath(), true); // 复制本地文件到远程WEB服务器指定目录下	
			}
		} else {
			if (imgPrcsEnable) {
				// filePath.getDesFilePath() = kelvin@eqianxian.com:/data/www/docroot/test_res2/hongbao/201401/sample_a.jpg
				String commandFile = filePath.getDesFilePath().substring(imgPrcsHome.length()).replace(File.separatorChar, '+');
				// get test_res2+hongbao+201401:sample_a.jpg
				commandFile = StringUtils.substringBeforeLast(commandFile, "+") + ":" + StringUtils.substringAfterLast(commandFile, "+");
				// test_res2+hongbao+201401:sample_a.jpg:resize=WxH.U
				commandFile = commandFile + ":resize=" + width + "x" + height + ".U";
				scp(filePath.getLocalFilePath(), imgPrcsHome + File.separator + commandFile, true);
				success = imageProcess(commandFile);				
			} else {
				ImageUtil.resize(localFilePath, width, height, widthRatio, false);
				success = scp(filePath.getLocalFilePath(), filePath.getDesFilePath(), true); // 复制本地文件到远程WEB服务器指定目录下
			}
		}
		retVal = success ? filePath.getServerPath() : null;
		return retVal;
	}

	/**
	 * 指定剪裁大小
	 * 
	 * @param file
	 * @param type
	 * @param oldFilePath
	 * @param isDraft
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public static String saveFile(File file, String fileName, String type, String oldFilePath, boolean isDraft, int x, int y,
			int width, int height, boolean center) {

		FilePath filePath = saveLocalFile(file, fileName, type, oldFilePath, isDraft);
		if (filePath == null) {
			return null;
		}
		String localFilePath = filePath.getLocalFilePath();
		String retVal = null;
		int[] size = resizeImage(localFilePath, width, height);

		boolean success = false;
		if (imgPrcsEnable) {
			// filePath.getDesFilePath() = kelvin@eqianxian.com:/data/www/docroot/test_res2/hongbao/201401/sample_a.jpg
			String commandFile = filePath.getDesFilePath().substring(imgPrcsHome.length()).replace(File.separatorChar, '+');
			// get test_res2+hongbao+201401:sample_a.jpg
			commandFile = StringUtils.substringBeforeLast(commandFile, "+") + ":" + StringUtils.substringAfterLast(commandFile, "+");
			// test_res2+hongbao+201401:sample_a.jpg:resize=WxH.U
			commandFile = commandFile + ":resize=" + width + "x" + height + "+crop=" + size[0] + "x" + size[1] + ".U";
			scp(filePath.getLocalFilePath(), imgPrcsHome + File.separator + commandFile, true);
			success = imageProcess(commandFile);
		} else {
			ImageUtil.clipImage(localFilePath, localFilePath, x, y, size[0], size[1], center);
			log.info("upload image, resize:" + size[0] + "," + size[1]);
			success = scp(filePath.getLocalFilePath(), filePath.getDesFilePath(), true); // 复制本地文件到远程WEB服务器指定目录下			
		}

		retVal = success ? filePath.getServerPath() : null;
		return retVal;
	}

	public static int[] resizeImage(String localFilePath, int width, int height) {
		int[] size = ImageUtil.getImageSize(localFilePath);
		if (size[0] < size[1]) {
			if (size[0] < width) {
				width = size[0];
				height = size[0];
			}
			if (!imgPrcsEnable) {
				ImageUtil.resize(localFilePath, width, height, true, false);
			}
		} else if (size[0] > size[1]) {
			if (size[1] < height) {
				width = size[1];
				height = size[1];
			}
			if (!imgPrcsEnable) {
				ImageUtil.resize(localFilePath, width, height, false, false);
			}
		} else if (size[0] == size[1]) {
			if (size[0] > width) {
				if (!imgPrcsEnable) {
					ImageUtil.resize(localFilePath, width, height, null, false);
				}
			}
		}
		size[0] = width;
		size[1] = height;
		return size;
	}

	/**
	 * 保存文件到本地
	 * 
	 * @param file
	 * @param type
	 * @param oldFilePath
	 * @param isDraft
	 * @return
	 */
	private static FilePath saveLocalFile(File file, String fileName, String type, String oldFilePath, boolean isDraft) {
		String extName = ".";
		if (StringUtils.isBlank(fileName)) {
			fileName = file.getName();
		} 
		extName += StringUtils.substringAfterLast(fileName, ".");
		if (log.isDebugEnabled()) {
			log.debug("this file " + fileName + " ext is " + extName + ", file size is " + file.length());
		}

		String theFileName = "";
		if (type != null) {
			if (StringUtils.contains(type, Constants.UploadFileName.ORIGINAL)) {
				type = StringUtils.substringBefore(type, Constants.UploadFileName.ORIGINAL);
				theFileName = fileName;
			} else if (StringUtils.contains(type, Constants.UploadFileName.CUSTOMER)) {
				theFileName = StringUtils.substring(type, type.lastIndexOf("/") + 1,
						type.indexOf(Constants.UploadFileName.CUSTOMER))
						+ extName;
				type = StringUtils.substringBeforeLast(type, "/");
			}
		}
		String dateDir = getDatePathByType(type);
		String draft = isDraft ? draftDir + "/" : "";
		String relativePath = type + "/" + dateDir + draft;

		// 1.保存为本地文件
		// 产生目标文件名（不带路径）
		if (StringUtils.isNotBlank(oldFilePath)) {
			oldFilePath = oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1);
			Assert.isTrue(StringUtils.isNotEmpty(oldFilePath));
		} else if (StringUtils.isNotBlank(theFileName)) {
			oldFilePath = theFileName;
		} else {
			oldFilePath = TextUtil.get64UUID() + extName;
		}

		// 产生本地目标文件名（带路径，路径体现了分目录存放的逻辑）
		String localPath = appAbsolutePath + "/" + uploadDir + "/" + relativePath;

		// localFilename =
		// ${appRoot}/${uploadDir}/${type}/${dateDir}/${filename}

		String desFilePath = remoteServerSrcRootPath + relativePath;

		if (isWindows) {
			localPath = StringUtils.replace(desFilePath, draftDir + "/", "");
			relativePath = StringUtils.replace(relativePath, draftDir + "/", "");
		}

		File folder = new File(localPath);
		if (!folder.exists() && !folder.mkdirs()) {
			log.error("无法创建目录：" + localPath);
			return null;
		}
		String localFilename = localPath + oldFilePath;
		String desFilename = desFilePath + oldFilePath;

		if (!saveFileToLocal(file, localFilename)) {
			return null;
		}

		String serverPath = resourceType + "/" + relativePath + oldFilePath;

		FilePath fp = new FilePath();
		fp.setLocalFilePath(localFilename);
		fp.setDesFilePath(desFilename);
		fp.setServerPath(serverPath);
		return fp;
	}

	public static String getServerPath(String desFilePath) {
		return resourceType
				+ "/"
				+ desFilePath.substring(desFilePath.indexOf(remoteServerSrcRootPath) + remoteServerSrcRootPath.length()
						+ 1);
	}

	private static boolean saveFileToLocal(File file, String localFilename) {
		// 写本地目标文件
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(localFilename));
			byte[] buff = new byte[8192];
			int i;
			while ((i = bis.read(buff)) != -1)
				bos.write(buff, 0, i);
			bos.flush();
			return true;
		} catch (IOException ex) {
			log.error("Save file " + localFilename + " failed: ", ex);
			return false;
		} finally {
			FileUtil.closeIO(bos, bis);
		}
	}

	public static boolean saveFileToLocal(byte[] data, String localFilename) {
		// 写本地目标文件
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(localFilename));
			bos.write(data);
			bos.flush();
			return true;
		} catch (IOException ex) {
			log.error("Save file " + localFilename + " failed: ", ex);
			return false;
		} finally {
			FileUtil.closeIO(bos);
		}
	}

	private static String getDatePathByType(String type) {
		if (StringUtils.isNotBlank(type) && !type.endsWith("/")) {
			return DateUtil.format(new Date(), "yyyyMM") + "/";
		}
		return "";
	}

	public static boolean scaleImage(String inPath, String outPut, int width, int height) {
		try {
			File file = new File(outPut);
			BufferedImage src = null;
			if (inPath.startsWith("http")) {
				src = javax.imageio.ImageIO.read(new URL(inPath));
			} else {
				src = javax.imageio.ImageIO.read(new File(inPath));
			}
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			bufferedImage.getGraphics().drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(bufferedImage);
			ImageIO.write(bufferedImage, /* "GIF" */"JPEG" /* format desired */, file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 把一个图片相对路径变成绝对数径，例如： "res1/pic/201307/356.jpg" ==> "http://res1.icloudxiao.net/pic/201307/356.jpg"
	 * 
	 * @param path 图片相对路径
	 * @return 转换后的绝对路径
	 */
	public static String getResUrl(String path) {
		if (StringUtils.isBlank(path))
			return "";
		if (path.startsWith("http")) {
			return path;
			// return path.replace("http://res1.icloudxiao-dev.com", "http://192.168.1.116");
		}
		int pos = path.indexOf('/');
		if (pos < 1) // 路径没有斜线或以斜线打头，直接返回路径
			return path;
	
		String server = path.substring(0, pos);
		return resourceDomain.replace("{0}", server) + path.substring(pos + 1);
		// System.out.println("http://192.168.1.116/" + path.substring(pos+1));
		// return "http://192.168.1.116/" + path.substring(pos+1);
	}

	/**
	 * 获取缩略图
	 * 
	 * @param path
	 * @return
	 */
	public static String getThumbUrl(String path) {
		String url = path;
		if (StringUtils.isNotBlank(path) && !path.startsWith("http")) {
			url = getResUrl(path);
		}
		if (StringUtils.isNotBlank(url)) {
			int pos = url.lastIndexOf("/");
			if (pos > -1) {
				return url.substring(0, pos) + "/thumb" + url.substring(pos);
			}
		}
		return url;
	}

	/**
	 * /usr/local/www/{bizType}/{yymm}/file.ext ==> res1/{bizType}/{yymm}/file.ext
	 * 
	 * @param localFileAbsPath
	 * @return
	 */
	public static String getSavePath(String localFileAbsPath) {
		if (StringUtils.isBlank(localFileAbsPath)) {
			return null;
		}

		String path = StringUtils.replace(localFileAbsPath, resourceRootPath, "/");
		return resourceType + path;
	}

	/**
	 * res1/{bizType}/{yymm}/file.ext ==> /usr/local/www/{bizType}/{yymm}/file.ext
	 * 
	 * @param serverPath
	 * @return
	 */
	public static String getLocalFileAbsPath(String serverPath) {
		if (serverPath == null)
			return null;
		int pos = serverPath.indexOf('/');
		if (pos < 1) // 路径没有斜线或以斜线打头，直接返回路径
			return serverPath;
		String server = serverPath.substring(0, pos);

		String ret = resourceRootPath.substring(0, resourceRootPath.indexOf(resourceType)) + serverPath;
		log.debug("loc--> ret=[" + ret + "], serverPath=[" + serverPath + "], pos=[" + pos + "], server=[" + server
				+ "], confKey=[Resource/LocalRootDir/" + server + "], suffix=[" + serverPath.substring(pos) + "].");
		return ret;
	}

	/**
	 * Delete local directory or file
	 * 
	 * @param localFilename - Local filename, can be directory or file
	 */
	public static void deleteLocalFile(String localFilename) {
		new File(localFilename).delete();
	}

	/**
	 * 调用linux scp 命令把文件scp复制到目标主机上
	 * 
	 * @param srcFilePath - 源文件路径，比如: /usr/local/myfile.txt
	 * @param desFilePath - 目标文件路径，形如： user@host:/usr/local/upload/myfile.txt
	 * @return 是否执行scp成功
	 */
	public static boolean scp(final String srcFilePath, final String desFilePath, boolean deleteLocal) {
		if (isWindows)
			return true;
		int colonPos = desFilePath.indexOf(':');
		String host = desFilePath.substring(0, colonPos);
		String dir = desFilePath.substring(colonPos + 1, desFilePath.lastIndexOf('/'));
		try {
			// 创建目标文件夹
			String command = MessageFormat.format(LINUX_SSH_MKDIR, sshPort, host, dir);
			log.info("====== " + command);
			runCmd(command);

			// 再复制文件到资源服务器上
			command = MessageFormat.format(LINUX_SCP_CP, sshPort, srcFilePath, desFilePath);
			log.info("====== " + command);
			runCmd(command);

			if (deleteLocal) {
				// 删除本地临时文件
				log.info("====== deleted");
				new File(srcFilePath).delete();
			}
			return true;
		} catch (Exception ex) {
			log.error("scp failed:srcFilePath=" + srcFilePath + ", desFilePath=" + desFilePath + ", Exception:" + ex);
			return false;
		}
	}

	/**
	 * 调用cp命令把文件复制成一个副本
	 */
	public static String sshCopy(String srcFilePath, String desFilePath) {
		if (isWindows) {
			return getSavePath(desFilePath);
		}
		try {
			String command = MessageFormat.format(LINUX_SSH_CP, sshPort, hostIP, srcFilePath, desFilePath);
			log.info("====== " + command);
			runCmd(command);

			return getSavePath(desFilePath);
		} catch (Exception ex) {
			log.error("cp failed:srcFilePath=" + srcFilePath + ", desFilePath=" + desFilePath + ", Exception:" + ex);
			return getSavePath(srcFilePath);
		}
	}

	/**
	 * 调用rm命令删除文件
	 */
	public static Boolean sshRm(String filePath) {
		String absPath = getLocalFileAbsPath(filePath);
		try {
			String command = MessageFormat.format(LINUX_SSH_RM, sshPort, hostIP, absPath);
			log.info("====== " + command);
			runCmd(command);
			return true;
		} catch (Exception ex) {
			log.error("rm failed:filePath=" + absPath + ", Exception:" + ex);
			return false;
		}
	}

	/**
	 * 调用mv命令把文件移动到另一个目录
	 */
	public static String sshMove(String srcFilePath, String desFilePath) {
		try {
			String command = MessageFormat.format(LINUX_SSH_MV, sshPort, hostIP, srcFilePath, desFilePath);
			log.info("====== " + command);
			runCmd(command);

			return getSavePath(desFilePath);
		} catch (Exception ex) {
			log.error("mv failed:srcFilePath=" + srcFilePath + ", desFilePath=" + desFilePath + ", Exception:" + ex);
			return getSavePath(srcFilePath);
		}
	}

	/**
	 * 将资源服务器草本目录中的文件移回使用目录
	 * 
	 * @param picture
	 * @return
	 */
	public static String moveFileBackFormDraft(String picture) {
		if (StringUtils.isBlank(picture)) {
			return "";
		}
		if (picture.contains(draftDir)) {
			String srcFilePath = ResourceUtil.getLocalFileAbsPath(picture);
			String desFilePath = ResourceUtil.getLocalFileAbsPath(StringUtils.replace(picture, "/" + draftDir, ""));

			if (log.isDebugEnabled()) {
				log.debug("start move file form " + srcFilePath + " to " + desFilePath);
			}
			// 将草本移到正式目录中
			return ResourceUtil.sshMove(srcFilePath, desFilePath);
		} else {
			return picture;
		}
	}

	/**
	 * 调用系统命令把文件移动为新的文件(或目录下)，参数为路径，非URL
	 * 
	 * @param source - 完整路径名 + 源文件名
	 * @param target - 目的，可以是文件名或者路径，如果是路径则保持原文件名
	 */
	public static boolean moveFile(String source, String target) {
		String command = "";
		if (!isWindows) {
			if (isRemote) {
				// command = MessageFormat.format(LINUM_SSH_MV, sshPort, hostIP,
				// source, target);
				return scp(source, target, true);
			} else {
				command = MessageFormat.format(LINUX_MOVE, source, target);
			}
		} else {
			if (isRemote) {
				throw new RuntimeException("Cannot handle now!");
			} else {
				command = MessageFormat.format(WINDOWS_MOVE, source, target);
			}
		}
		try {
			log.info("====== command=" + command);
			runCmd(command);
			return true;
		} catch (Exception ex) {
			log.error("Move file failed!", ex);
			return false;
		}
	}

	/**
	 * 通过socket调用图片处理服务器进行resize
	 * 
	 * @param desFilePath
	 */
	public static boolean imageProcess(String desFilePath) {
		Socket socket = null;
		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			log.info("====== start process image...");
			socket = new Socket(hostIP, imgPrcsPort);
			writer = new PrintWriter(socket.getOutputStream());
			writer.println(desFilePath.substring(desFilePath.lastIndexOf('/') + 1));
			writer.flush();
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return "Y".equalsIgnoreCase(reader.readLine());
		} catch (Exception ex) {
			log.error("Exception occured at call ResourceUtil.imageProcess():" + ex);
			return false;
		} finally {
			log.info("====== completed process image");
			try {
				if(writer != null) {
					writer.close();
				}
				if(reader != null) {
					reader.close();
				}
				if (socket != null)
					socket.close();
			} catch (IOException ex) {
				log.error("Error:", ex);
			}
		}
	}

	public static void downloadFile(ByteArrayOutputStream stream, String desFile) throws IOException {
		FileOutputStream fs = null;
		try {
			File file = new File(desFile);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fs = new FileOutputStream(desFile);
			fs.write(stream.toByteArray());
			fs.flush();
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载网络文件
	 * 
	 * @param url
	 * @param desFile
	 */
	public static void downloadFile(String url, String desFile) {

		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			URL httpUrl = new URL(url);
			URLConnection conn = httpUrl.openConnection();
			inStream = conn.getInputStream();
			File file = new File(desFile);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fs = new FileOutputStream(desFile);
			byte[] buffer = new byte[1204];
			int byteread = 0;
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Whether specified URL exists
	 * 
	 * @param url - URL
	 * @return true is URL response OK
	 */
	public static boolean exists(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		url += "?t=" + new Date().getTime();
		HttpURLConnection.setFollowRedirects(false);
		// 到 URL 所引用的远程对象的连接

		HttpURLConnection con = null;

		try {
			URL urlObj = new URL(url);
			// String hostName = url.getHost();
			// System.out.println(hostName);
			// url = new URL(URLName.replace(hostName, "192.168.0.229"));
			// System.out.println(url + "--" + url.getHost());
			// Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
			// "192.168.0.229", 80));
			con = (HttpURLConnection) urlObj.openConnection();
			// 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE
			// 以上方法之一是合法的，具体取决于协议的限制。
			con.setRequestMethod("HEAD");
			// con.setRequestProperty("Host", url.getHost());
			// 从 HTTP 响应消息获取状态码
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (MalformedURLException ex) {
			log.error("Error:", ex);
		} catch (IOException ex) {
			log.error("Error:", ex);
		}
		return false;
	}

	/**
	 * 获取资源文件中的配置，如无则返回默认
	 * 
	 * @param key
	 * @param defaultProperty
	 * @return
	 */
	public static String getProperty(String key, String defaultProperty) {
		return props.getProperty(key, defaultProperty);
	}

	/**
	 * 获取资源文件中的配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * @return the APP root path in OS format. Such as: /usr/local/tomcat/webapps/ROOT
	 */
	public static String getAppAbsolutePath() {
		return appAbsolutePath;
	}

	/**
	 * @param appAbsolutePath - the APP root path in OS format. Such as: /usr/local/tomcat/webapps/ROOT
	 */
	public static void setAppAbsolutePath(String appAbsolutePath) {
		ResourceUtil.appAbsolutePath = appAbsolutePath;
	}

	/**
	 * Get the resource.server.root.path.img in property file
	 * 
	 * @return String - the APP root path in OS format
	 */
	public static String getCurrentResourceRootPath() {
		return remoteServerSrcRootPath;
	}

	public static void setCurrentResourceRootPath(String currentResourceRootPath) {
		ResourceUtil.remoteServerSrcRootPath = currentResourceRootPath;
	}

	/**
	 * Get the resource.server in property file
	 * 
	 * @return
	 */
	public static String getResourceType() {
		return resourceType;
	}

	public static void setResourceType(String resourceType) {
		ResourceUtil.resourceType = resourceType;
	}

	private static void runCmd(String command) {
		Process process = null;
		InputStream ins = null;
		try {
			process = Runtime.getRuntime().exec(command);
			ins = process.getInputStream();
			byte[] b = new byte[100];
			ins.read(b);
			ins.close();
			process.destroy();
		} catch (Exception ex) {
			log.error("runCmd failed: " + command, ex);
		} finally {
			try {
				if(ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getServerUrl() {
		return appServer;
	}

	public static class FilePath {
		private String localFilePath;

		private String desFilePath;

		private String serverPath;

		public String getLocalFilePath() {
			return localFilePath;
		}

		public void setLocalFilePath(String localFilePath) {
			this.localFilePath = localFilePath;
		}

		public String getDesFilePath() {
			return desFilePath;
		}

		public String getThumbnailPath() {
			if (StringUtils.isNotBlank(desFilePath)) {
				int pos = desFilePath.lastIndexOf("/");
				if (pos > -1) {
					return desFilePath.substring(0, pos) + "/thumb" + desFilePath.substring(pos);
				}
			}
			return null;
		}

		public String getThumbnailDir() {
			if (StringUtils.isNotBlank(desFilePath)) {
				int pos = desFilePath.lastIndexOf("/");
				if (pos > -1) {
					return desFilePath.substring(0, pos) + "/thumb";
				}
			}
			return null;
		}

		public void setDesFilePath(String desFilePath) {
			this.desFilePath = desFilePath;
		}

		public String getServerPath() {
			return serverPath;
		}

		public void setServerPath(String serverPath) {
			this.serverPath = serverPath;
		}
	}

	public static String getResourceRootPath() {
		return resourceRootPath;
	}
}
