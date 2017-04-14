package com.whotel.common.util;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @author
 */
public class TextUtil {
	public static final Charset GBK = Charset.forName("GBK");
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

	private TextUtil() {}
	/**
	 * 按中文两字符来截取子字符串
	 * 
	 * @param beginIndex the beginning index, inclusive.
	 * @return the specified substring.
	 * @exception IndexOutOfBoundsException if <code>beginIndex</code> is negative or larger than the length of this
	 *                <code>String</code> object.
	 */
	public static final String substring(String str, int beginIndex) {
		return substring(str, beginIndex, str.length());
	}

	/**
	 * 按中文两字符来截取子字符串
	 * 
	 * @param beginIndex the beginning index, inclusive.
	 * @param endIndex the ending index, exclusive.
	 * @return the specified substring.
	 * @return
	 */
	public static final String substring(String str, int beginIndex, int endIndex) {
		if (str == null) {
			return "";
		}
		String resu = "";
		int beg = 0;
		int end = 0;
		int count1 = 0;
		char[] temp = new char[str.length()];
		str.getChars(0, str.length(), temp, 0);
		boolean[] bol = new boolean[str.length()];
		for (int i = 0; i < temp.length; i++) {
			bol[i] = false;
			if (temp[i] > 255) {// 说明是中文
				count1++;
				bol[i] = true;
			}
		}

		if (beginIndex > str.length() + count1) {
			resu = null;
		}
		if (beginIndex > endIndex) {
			resu = null;
		}
		if (beginIndex < 1) {
			beg = 0;
		} else {
			beg = beginIndex - 1;
		}
		if (endIndex > str.length() + count1) {
			end = str.length() + count1;
		} else {
			end = endIndex;// 在substring的末尾一样
		}
		// 下面开始求应该返回的字符串
		if (resu != null) {
			if (beg == end) {
				int count = 0;
				if (beg == 0) {
					if (bol[0] == true) {
						resu = null;
					} else {
						resu = new String(temp, 0, 1);
					}
				} else {
					int len = beg;// zheli
					for (int y = 0; y < len; y++) {// 表示他前面是否有中文,不管自己
						if (bol[y] == true) {
							count++;
						}
						len--;// 想明白为什么len--
					}
					// for循环运行完毕后，len的值就代表在正常字符串中，目标beg的上一字符的索引值
					if (count == 0) {// 说明前面没有中文
						if (temp[beg] > 255)// 说明自己是中文
						{
							resu = null;// 返回空
						} else {
							resu = new String(temp, beg, 1);
						}
					} else {// 前面有中文，那么一个中文应与2个字符相对
						if (temp[len + 1] > 255)// 说明自己是中文
						{
							resu = null;// 返回空
						} else {
							resu = new String(temp, len + 1, 1);
						}
					}
				}
			} else {// 下面是正常情况下的比较
				int temSt = beg;
				int temEd = end - 1;// 这里减掉一
				for (int i = 0; i < temSt; i++) {
					if (bol[i] == true) {
						temSt--;
					}
				}// 循环完毕后temSt表示前字符的正常索引
				for (int j = 0; j < temEd; j++) {
					if (bol[j] == true) {
						temEd--;
					}
				}// 循环完毕后temEd-1表示最后字符的正常索引
				if (bol[temSt] == true)// 说明是字符，说明索引本身是汉字的后半部分，那么应该是不能取的
				{
					int cont = 0;
					for (int i = 0; i <= temSt; i++) {
						cont++;
						if (bol[i] == true) {
							cont++;
						}
					}
					if (beginIndex == cont)// 是偶数不应包含,如果pstart<cont则要包含
					{
						temSt++;// 从下一位开始
					}
				}
				if (bol[temEd] == true) {// 因为temEd表示substring
					// 的最面参数，此处是一个汉字，下面要确定是否应该含这个汉字
					int cont = 0;
					for (int i = 0; i <= temEd; i++) {
						cont++;
						if (bol[i] == true) {
							cont++;
						}
					}
					if (endIndex < cont)// 是汉字的前半部分不应包含
					{
						temEd--;// 所以只取到前一个
					}
				}
				if (temSt == temEd) {
					resu = new String(temp, temSt, 1);
				} else if (temSt > temEd) {
					resu = null;
				} else {
					resu = str.substring(temSt, temEd + 1);
				}
			}
		}
		return resu;// 返回结果
	}

	/**
	 * 将数组转换为分隔符分割的字符串
	 * 
	 * @param array 对象数组
	 * @param separator 分隔符
	 * @return
	 */
	public static final String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		int arraySize = array.length;
		StringBuilder buffer = new StringBuilder();

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buffer.append(separator);
			}
			if (array[i] != null) {
				buffer.append(array[i]);
			}
		}
		return buffer.toString();
	}

	/**
	 * 按ascii码计算中英文字符串长度
	 * 
	 * @return
	 */
	public static final int getAsciiLength(String str) {
		return new String(str.getBytes(GBK), ISO_8859_1).length();
	}

	/**
	 * 截取指定长度的子字符串
	 * 
	 * @param input 要截取的字符串
	 * @param maxlength 截取的长度
	 * @return 截取后的字符串
	 */
	public static final String truncate(String input, int maxlength) {
		if (input == null)
			return null;
		if (input.length() <= maxlength)
			return input;
		String temp = input.substring(0, maxlength);
		byte[] bytes = temp.getBytes();
		int count = 0;
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] > 0) {
				count++;
			}
		}
		if (count > 0) {
			count = count / 2 + 1;
		}
		if (input.length() <= maxlength + count)
			return input;
		else
			return input.substring(0, maxlength + count);
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param input
	 * @return
	 */
	public static final boolean notEmpty(String input) {
		return (input != null && input.trim().length() > 0);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param input
	 * @return
	 */
	public static final boolean isEmpty(String input) {
		return (input == null || input.trim().length() == 0);
	}

	/**
	 * <p>
	 * 格式化字符串
	 * </p>
	 * 
	 * <pre>
	 * StrUtils.format("This is {1} {0}", "Test", 1)               = "This is 1 Test"
	 * StrUtils.format("{0,number,#.00}", 1)                       = "1.00"
	 * StrUtils.format("{0,date,yyyy-mm-dd hh:mm:ss}", new Date()) = "2014-40-20 04:40:45"
	 * </pre>
	 * 
	 * @param input 文本模版
	 * @param arguments 参数
	 * @return 格式化后的字符串
	 */
	public static final String format(String input, Object... arguments) {
		if (input == null)
			return null;
		return MessageFormat.format(input, arguments);
	}

	/**
	 * aaa"bb"cc ==> aaa\"bb\"cc
	 * 
	 * @param input
	 * @return
	 */
	public static final String jsStrEscape(String input) {
		if (input == null)
			return null;
		return input.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\\'").replaceAll("(\\r)?\\n", "\\\\n");
	}

	/**
	 * 将html标签转义成显示模式，以便网页显示html编码内容
	 * 
	 * @param input
	 * @return
	 */
	public static final String htmlEscape(String input) {
		if (input == null)
			return null;
		return input.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
	}

	/**
	 * SQL语句转义
	 * 
	 * @param input
	 * @return
	 */
	public static final String sqlEscape(String input) {
		if (input == null)
			return null;
		return input.replaceAll("'", "''");
	}

	/**
	 * csv escape input string
	 * 
	 * @param input input string
	 * @return csv escaped string
	 */
	public static final String escapeCSV(String input) {
		if (input == null || input.trim().length() == 0)
			return "";
		if (input.indexOf('\n') > -1) {
			input = input.replaceAll("(\\r)?\\n", "<BR/>");
		}
		if (input.indexOf('"') > -1 || input.indexOf(",") > -1) {
			input = input.replaceAll("\"", "\"\"");
			return '"' + input + '"';
		}
		return input;
	}

	/**
	 * 将字符串中的空格和换行变成HTML式的空格和换行
	 * 
	 * @param input
	 * @return
	 */
	public static final String toHTML(String input) {
		if (input == null)
			return null;
		return input.replaceAll(" ", "&nbsp;").replaceAll("(\\r)?\\n", "<BR>");
	}

	/**
	 * 获得文件附加名
	 * 
	 * @param filePath file name or full path name
	 * @return file extension name
	 */
	public static final String getFileType(String filePath) {
		if (filePath == null)
			return null;
		return filePath.substring(filePath.lastIndexOf(".") + 1);
	}

	/**
	 * 将Html标签小写化
	 * 
	 * @param html
	 * @return
	 */
	public static final String htmlTag2LowerCase(String html) {
		Matcher matcher = Pattern.compile("<[^>]+>").matcher(html);
		int lastIdx = 0;
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String str = matcher.group();
			sb.append(html.substring(lastIdx, matcher.start()));
			sb.append(str.toLowerCase());
			lastIdx = matcher.end();
		}
		return sb.append(html.substring(lastIdx)).toString();
	}

	/**
	 * 清除<script></script>标签包围的内容
	 * 
	 * @param input
	 * @return
	 */
	public static final String clearScriptCode(String input) {
		if (input == null)
			return null;
		Matcher matcher = Pattern.compile("<[^>]*>").matcher(input);
		int lastIdx = 0;
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String str = matcher.group();
			sb.append(input.substring(lastIdx, matcher.start()));
			String tag = str.toLowerCase();
			if (tag.startsWith("<script") || tag.startsWith("</script")) {
				sb.append(tag);
			} else {
				sb.append(str);
			}
			lastIdx = matcher.end();
		}
		sb.append(input.substring(lastIdx));
		return sb.toString().replaceAll("<script[^>]*>[^<]*</script[^>]*>", "");
	}

	/**
	 * 去除html中的所有标签
	 * 
	 * @param html
	 * @return
	 */
	public static final String html2text(String html) {
		if (html == null)
			return null;
		return htmlTag2LowerCase(html).replaceAll("<script[^>]*>[^<]*</script[^>]*>", "")
				.replaceAll("<style[^>]*>[^<]*</style[^>]*>", "").replaceAll("&([a-zA-Z]+);", " ")
				.replaceAll("(\\r)?\\n", " ").replaceAll("<[^>]+>", "").replaceAll("(\\s)+", " ");
	}

	/**
	 * 将html先删除所有标签内容，然后截取指定长度
	 * 
	 * @param html
	 * @param length
	 * @return
	 */
	public static final String truncateHTML(String html, int length) {
		if (html == null)
			return null;
		return truncate(html2text(html).trim(), length).replaceAll("\"", "&quot;");
	}

	/**
	 * 
	 * @param html
	 * @param length
	 * @return
	 */
	public static final String truncateHTML2(String html, int length) {
		if (html == null)
			return null;
		String lowerHtml = html.toLowerCase();
		final String START_TAG = "<span class=\"s_highlight\">";
		final String END_TAG = "</span>";
		int startPos = lowerHtml.indexOf(START_TAG);
		java.util.List<String> words = new java.util.ArrayList<String>();
		while (startPos > -1) {
			int endPos = lowerHtml.indexOf(END_TAG, startPos + START_TAG.length());
			if (endPos == -1)
				break;
			String word = html.substring(startPos + START_TAG.length(), endPos);
			words.add(word);
			startPos = lowerHtml.indexOf(START_TAG, endPos + END_TAG.length());
		}
		html = html.replaceAll("<[^>]*>", "").replaceAll("(\\r)?\\n", " ").replaceAll("(\\s)+", " ");

		if (html.length() > length) {
			StringBuilder sb = new StringBuilder(length * 2);
			float count = 0;
			for (int i = 0; i < html.length() && count < length; i++) {
				char c = html.charAt(i);
				count = count + (c < 128 ? 0.5f : 1.0f);
				sb.append(c);
			}
			html = sb.toString();
		}

		for (String word : words)
			html = html.replaceAll(word, START_TAG + word + END_TAG);
		return html.trim().replaceAll("\\\\", " ");
	}

	static final char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	static final char[] nums = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 获得指定长度的随机字符串
	 * 
	 * @param num
	 * @return
	 */
	public static final String genRandomString(int num) {
		StringBuilder sb = new StringBuilder(num);
		for (int i = 0; i < num; i++)
			sb.append(letters[RandomUtils.nextInt(letters.length)]);
		return sb.toString();
	}

	/**
	 * 获得指定长度的随机数字串
	 * 
	 * @param num
	 * @return
	 */
	public static final String genRandomNumString(int num) {
		StringBuilder sb = new StringBuilder(num);
		for (int i = 0; i < num; i++)
			sb.append(nums[RandomUtils.nextInt(nums.length)]);
		return sb.toString();
	}

	/**
	 * 处理搜索中的关键词中的特殊字符,并将多个连续的空格替换成一个空格
	 * 
	 * @param keyWord 关键词
	 * @return
	 */
	public static final String filterKeyWord(String keyWord) {
		String result = keyWord;
		if (isEmpty(result)) {
			return "";
		}
		// 删除掉特殊字符
		String regEx = "[`~!@#$%^&*=|{}()':;',//[//].<>/?~！@#￥%……&*_|｛｝（）【】‘；：”“’。，、？\\[\\](\\\\)+\\-]+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(result);
		result = m.replaceAll("").trim();

		// 处理空格字符,多个空格替换成一个空格
		regEx = "[\\s]+";
		p = Pattern.compile(regEx);
		m = p.matcher(result);
		result = m.replaceAll(" ").trim();

		return result.toLowerCase();
	}

	/**
	 * 转义Solr/Lucene的保留运算字符 保留字符有+ - && || ! ( ) { } [ ] ^ ” ~ * ? : \
	 * 
	 * @param input
	 * @return 转义后的字符串
	 */
	public static final String transformSolrMetacharactor(String input) {
		StringBuffer sb = new StringBuffer();
		String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			matcher.appendReplacement(sb, "\\\\" + matcher.group());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Arrays.asList 生成的List不支持删除添加操作，所以添加该方法，将字符串处理成List
	 * 
	 * @param s
	 * @return
	 */
	public static final List<String> stringToList(String s, String delimiter) {
		String[] sArr = s.split(delimiter);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < sArr.length; i++) {
			list.add(sArr[i]);
		}
		return list;
	}

	/**
	 * 把字符串的第一个字母转成小写
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static final String uncapitalize(String str) throws Exception {
		if (str == null || str.trim().equals(""))
			return str;
		char c = str.charAt(0);
		// str.toLowerCase(locale)
		if (c >= 'A' && c <= 'Z') {
			char c2 = (char) ('a' + c - 'A');

			return c2 + str.substring(1, str.length());
		} else {
			return str;
		}
	}

	/**
	 * 把字符串的第一个字母转成大写
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static final String capitalize(String str) throws Exception {
		if (str == null || str.trim().equals(""))
			return str;
		if(str.indexOf("_")>0){
			String[] stu = str.split("_");
			str = "";
			for (int i = 0; i < stu.length; i++) {
				str+=toFirstUpperCase(stu[i]);
			}
		}else{
			str=toFirstUpperCase(str);
		}
		return str;
	}
	
	public static final String toFirstUpperCase(String str) throws Exception {
		if (str == null || str.trim().equals(""))
			return str;
		char c = str.charAt(0);
		// str.toLowerCase(locale)
		if (c >= 'a' && c <= 'z') {
			char c2 = (char) ('A' + c - 'a');
			return c2 + str.substring(1, str.length());
		} else {
			return str;
		}
	}

	/**
	 * 获取一个字符串中特定子串包围的内容，常用于标签内容解析
	 * 
	 * <pre>
	 * ("&lt;div&gt;info&lt;/div&gt;", "&lt;div&gt;", "&lt;/div&gt;")	==>	info
	 * ("&lt;div&gt;info&lt;/table&gt;", "&lt;div&gt;", "&lt;/div&gt;")	==>	null
	 * 
	 * @param str
	 * @param begStr
	 * @param endStr
	 * @return
	 */
	public static final String strBetween(String str, String begStr, String endStr) {
		if (str == null)
			return null;
		int pos = str.indexOf(begStr);
		if (pos >= 0) {
			int beg = pos + begStr.length();
			int end = str.indexOf(endStr, beg);
			if (end == -1) {
				return null;
			} else {
				return str.substring(beg, end);
			}
		}
		return null;
	}

	/**
	 * 标准的UUID格式为：xxxxxxxx-xxxx-xxxx-xxxxxx-xxxxxxxxxx (8-4-4-4-12)，去掉横线节省空间
	 * 
	 * @return 长度32的UUID字符串
	 */
	public static String getShortUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	private static final char[] charMap;
	static {
		charMap = new char[64];
		for (int i = 0; i < 10; i++) {
			charMap[i] = (char) ('0' + i);
		}
		for (int i = 10; i < 36; i++) {
			charMap[i] = (char) ('a' + i - 10);
		}
		for (int i = 36; i < 62; i++) {
			charMap[i] = (char) ('A' + i - 36);
		}
		charMap[62] = '_';
		charMap[63] = '-';
	}

	private static String hexTo64(String hex) {
		StringBuffer r = new StringBuffer();
		int index = 0;
		int[] buff = new int[3];
		int l = hex.length();
		for (int i = 0; i < l; i++) {
			index = i % 3;
			buff[index] = Integer.parseInt("" + hex.charAt(i), 16);
			if (index == 2) {
				r.append(charMap[buff[0] << 2 | buff[1] >>> 2]);
				r.append(charMap[(buff[1] & 3) << 4 | buff[2]]);
			}
		}
		return r.toString();
	}

	/**
	 * 获取更短的UUID，长度为22
	 * 
	 * @return
	 */
	public static String get64UUID() {
		String uuid = "0" + getShortUUID();
		return hexTo64(uuid);
	}

}
