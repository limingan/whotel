package com.whotel.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author
 * 
 */
public class URLUtil {
	private static Logger logger = LoggerFactory.getLogger(URLUtil.class);
	private static final Pattern ipPattern = Pattern
			.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
	private static final String domainPatternString1 = "aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel";
	private static final String domainPatternString2 = "ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw";
	private static final String domainPatternString = "[\\w-]+(\\.("
			+ domainPatternString1 + ")(\\.(" + domainPatternString2
			+ "))|\\.(" + domainPatternString1 + "|" + domainPatternString2
			+ "))$";
	private static final Pattern domainPattern = Pattern.compile(
			domainPatternString, Pattern.CASE_INSENSITIVE);
	private static final String HTTP_PROTOCAL = "http://";
	private static final String HTTPS_PROTOCAL = "https://";
	private static final String FTP_PROTOCAL = "ftp://";

	private URLUtil() {}
	
	/**
	 * 判断字符串是否为IPv4地址格式
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isIpAddress(String str) {
		return ipPattern.matcher(str).matches();
	}

	/**
	 * 判断字符串中是否包含域名摸样的子串
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean hasDomainName(String str) {
		Matcher matcher = domainPattern.matcher(str);
		return matcher.find();
	}

	/**
	 * 从url中解析出域名
	 * 
	 * <pre>
	 * http://192.168.1.2:8080/abcd/ef.action		-> 192.168.1.2
	 * http://www.abc.com:8080/abcd/ef.action		-> abc.com
	 * www.baidu.com						-> baidu.com
	 * http://www.abc.com:8080@user:abcd/efgh/ij.action	-> abc.com
	 * http://localhost:8080@user:asdf/asdfa/dd.action	-> http://localhost:8080@user:asdf/asdfa/dd.action
	 * www.baidu.comabc					-> www.baidu.comabc
	 * </pre>
	 * 
	 * @param url
	 * @return 域名
	 */
	public static final String getDomainName(String url) {
		if (null == url || "".equals(url)) {
			return null;
		}
		String website = getWebsite(url);
		if (isIpAddress(website)) {
			return website;
		}
		Matcher matcher = domainPattern.matcher(website);
		return matcher.find() ? matcher.group() : url;
	}

	/**
	 * 从字符串中解析出域名
	 * 
	 * <pre>
	 * http://192.168.1.2:8080/abcd/ef.action		-> 192.168.1.2
	 * http://www.abc.com:8080/abcd/ef.action		-> www.abc.com
	 * www.baidu.com						-> www.baidu.com
	 * http://www.abc.com:8080@user:abcd/efgh/ij.action	-> www.abc.com
	 * http://localhost:8080@user:asdf/asdfa/dd.action	-> localhost
	 * www.baidu.comabc					-> www.baidu.comabc
	 * http://mp.weixin.qq.com/index.html?t=123123	-> mp.weixin.qq.com
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static final String getWebsite(String str) {
		String str1 = str.toLowerCase();
		int beginIndex = 0;
		if (str1.startsWith(HTTP_PROTOCAL) || str1.startsWith(HTTPS_PROTOCAL)
				|| str1.startsWith(FTP_PROTOCAL)) {
			beginIndex = str1.startsWith(HTTP_PROTOCAL) ? HTTP_PROTOCAL
					.length()
					: str1.startsWith(HTTPS_PROTOCAL) ? HTTPS_PROTOCAL.length()
							: FTP_PROTOCAL.length();
		} else {
			beginIndex = str1.indexOf("://");
		}
		if (beginIndex < 0) {
			beginIndex = 0;
		}
		int endIndex = str1.indexOf("/", beginIndex);
		if (endIndex <= 0) {
			endIndex = str1.length();
		}
		String website = str1.substring(beginIndex, endIndex);
		website = website.split("@")[0];
		endIndex = website.indexOf(":");
		return endIndex > 0 ? website.substring(0, endIndex) : website;
	}

	/**
	 * 
	 * @param urlString
	 * @return
	 */
	public static final String decode(String urlString) {
		String decodeCharset = "GBK";
		try {
			if (isUtf8Url(urlString)) {
				decodeCharset = "UTF-8";
			}
			if (isUtf16Url(urlString)) {
				String url = "http://www.hahabei.com/SearchPlayFile.aspx?%u6D4B%u8BD5";
				url = url.replaceAll("%u", "\\\\u");
				return ascii2native(url);
			}
			return URLDecoder.decode(urlString, decodeCharset);
		} catch (Exception e) {
			logger.error("decodeCharset:" + decodeCharset + " urlString:"
					+ urlString, e);
			return urlString;
		}
	}

	/**
	 * URL utf-8编码
	 * 
	 * @param s
	 * @return
	 */
	public static final String encode(String text) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();
	}

	/**
	 * 编码是否有效
	 * 
	 * @param text
	 * @return
	 */
	private static final boolean isUtf8Charset(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int p = 0; p != -1;) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 是否utf8Url编码
	 * 
	 * @param text
	 * @return
	 * @throws MalformedURLException
	 */
	public static final boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p >= 9) {
			text = text.substring(p, p + 9);
		}
		return isUtf8Charset(text);
	}

	/**
	 * 是否是utf18Url编码
	 * 
	 * @param text
	 * @return
	 */
	public static final boolean isUtf16Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%u");
		if (p != -1 && text.length() - p >= 6) {
			return true;
		}
		return false;
	}

	/**
	 * prefix of ascii string of native character
	 */
	private static final String PREFIX = "\\u";

	/**
	 * Convert native text file to ascii text file, it's same as execut
	 * native2ascii.exe.
	 * 
	 * @param dir
	 *            file directory
	 * @param srcFile
	 *            sources file - native language text file
	 * @param desFile
	 *            destination file - unicode file
	 * @param encoding
	 *            file character encoding
	 * @throws IOException
	 */
	public static final void native2ascii(String dir, String srcFile,
			String desFile, String encoding) {
		File folder = new File(dir);
		if (!folder.isDirectory()) {
			System.out.println("dir: " + dir + " is not valid directory");
			return;
		}
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			if (encoding != null)
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(dir + "/" + srcFile), encoding));
			else
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(dir + "/" + srcFile)));
			writer = new BufferedWriter(new FileWriter(dir + "/" + desFile));
			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(native2ascii(line));
				writer.newLine();
			}
			System.out.println("native2ascii successful [" + dir + "/"
					+ desFile + "]");
		} catch (IOException e) {
			System.out.println("native2ascii failed: " + e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Convert native string to ascii string.
	 * 
	 * @param str
	 *            native string
	 * @return ascii string
	 */
	public static final String native2ascii(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			sb.append(char2ascii(chars[i]));
		}
		return sb.toString();
	}

	/**
	 * Native character to ascii string.
	 * 
	 * @param c
	 *            native character
	 * @return ascii string
	 */
	private static final String char2ascii(char c) {
		if (c > 255) {
			StringBuilder sb = new StringBuilder();
			sb.append(PREFIX);
			int code = (c >> 8);
			String tmp = Integer.toHexString(code).toUpperCase();
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			code = (c & 0xFF);
			tmp = Integer.toHexString(code).toUpperCase();
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			return sb.toString();
		} else {
			return Character.toString(c);
		}
	}

	/**
	 * convert sscii string to native string.
	 * 
	 * @param str
	 *            ascii string
	 * @return native string
	 */
	public static final String ascii2native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	/**
	 * Ascii to native character.
	 * 
	 * @param str
	 *            ascii string
	 * @return native character
	 */
	private static final char ascii2char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

}
