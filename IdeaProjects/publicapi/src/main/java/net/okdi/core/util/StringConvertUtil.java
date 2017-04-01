package net.okdi.core.util;

public class StringConvertUtil {

	/**
	 * @param source
	 *            需要处理的字符串
	 * @param oldString
	 *            需要被替换的字符
	 * @param newString
	 *            替换后的字符
	 * @return 比如 需要替换字符串中的? String str = "Who am I ?"; replace(str,"?","%3F");
	 */
	public static String replace(String source, String oldString, String newString) {
		if(source==null||"".equals(source))
			return "";
		StringBuffer output = new StringBuffer();
		int lengthOfSource = source.length();
		int lengthOfOld = oldString.length();
		int posStart = 0;
		int pos; //
		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
			posStart = pos + lengthOfOld;
		}
		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}
	
	public static String toSql(String str) {
		if (str == null)
			return null;
		str = str.replaceAll("%", "[%]");
		str = str.replaceAll("_", "[_]");
		str = str.replaceAll("'", "''");
		return str;
	}
	/**
	 * URL中的特殊字符转换
	 * 
	 * @param str
	 * @return
	 */
	public static String toUrl(String str) {
		if (str == null)
			return "";
		str = str.replaceAll("%", "%25").replaceAll(" ", "%20").replaceAll("/", "%2F").replaceAll("#", "%23").replaceAll("&", "%26").replaceAll("=", "%3D");
		str = replace(str, "+", "%2B");
		str = replace(str, "?", "%3F");
		return str;
	}
	
	public static String gb2iso(String value) {
		if (value == null)
			return "";
		try {
			value = new String(value.getBytes("GBK"), "ISO8859_1");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "";
		}
		return value;
	}
	
	public static String utf2iso(String value) {
		if (value == null)
			return "";
		try {
			value = new String(value.getBytes("utf-8"), "ISO8859_1");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "";
		}
		return value;
	}
	
	public static String GBK2utf(String value) {
		if (value == null)
			return "";
		try {
			value = new String(value.getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "";
		}
		return value;
	}
	
	public static String iso2gb(String value) {
		if (value == null)
			return "";
		try {
			value = new String(value.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "";
		}
		return value;
	}

	public static String iso2utf(String value) {
		if (value == null)
			return "";
		try {
			value = new String(value.getBytes("ISO8859_1"), "utf-8");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "";
		}
		return value;
	}
}
