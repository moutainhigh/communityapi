package net.okdi.core.util;

import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 创建SEQUENCE
 * 
 * @author chenxt
 * @created 2013-08-29 上午10:50:05
 * @since v1.1
 * @history
 * @see
 */

public class SequenceUtil {
	private static SequenceUtil _instance = new SequenceUtil();
	private static long max = 999;
	private static long saveSecond = 0l;
	private static String IP_LAST;
	int i = 1;
	static {
		saveSecond = System.currentTimeMillis() / 1000;

		// saveSecond = saveSecond / 60;
	}

	private SequenceUtil() {
		try {
			InetAddress objAddr = InetAddress.getLocalHost();
			// 获取IP地址
			String ip = objAddr.getHostAddress();

			String[] ips = ip.split("\\.");

			String ip1 = ips[3];

			int ip1_last = Integer.valueOf(ip1);

			String ip_3 = formatIP(ip1);

			IP_LAST = ip_3;

		} catch (Exception e) {
			// log.error("get IP_LAST error!", e);
			e.printStackTrace();
			String ip_last1 = format(String.valueOf(new Random().nextInt(255)),
					2);
			IP_LAST = ip_last1;
		}
	}

	/**
	 * 
	 * @return 返回实例
	 * @author chenxt
	 * @created 2013-08-29 上午10:50:05
	 */
	public static SequenceUtil getInstance() {
		return _instance;
	}

	/**
	 * 设置时间
	 * 
	 * @author chenxt
	 * @created 2013-08-29 上午10:50:05
	 * @since v1.1
	 * @history
	 * @see
	 */
	private synchronized String getUniqTimer() {

		StringBuffer sb = new StringBuffer();

		if (max == i) {
			long currentTime = System.currentTimeMillis() / 1000;
			// currentTime = currentTime / 60;

			if (currentTime <= saveSecond) {
				saveSecond++;
			} else {
				saveSecond = currentTime;
			}
			i = 1;

		} else {
			i++;
		}
		sb.append(saveSecond);

		String saveTime = format(String.valueOf(saveSecond), 4);

		// String add_i = format(String.valueOf(i), 6);
		String add_i = formatNum(i, 3);
		// System.out.println("saveSecond="+saveSecond );
		// System.out.println("UniqTimer+sequence="+saveTime + add_i);

		return saveTime + add_i;
	}

	/**
	 * 获取下一个Sequence键值
	 * 
	 * @return 下一个Sequence键值(String)
	 * @author chenxt
	 * @created 2013-08-29 上午10:50:05
	 */
	public static String getNextKeyValue() {
		StringBuffer sb = new StringBuffer();
		sb.append(IP_LAST);
		sb.append(SequenceUtil.getInstance().getUniqTimer());
		// System.out.println("UniqTimer()="+CreateId.getInstance().getUniqTimer());

		System.out.println("SequenceId=" + sb);
		return sb.toString();
	}

	/**
	 * 获取下一个Sequence键值
	 * 
	 * @return 下一个Sequence键值(long)
	 * @author chenxt
	 * @created 2013-08-29 上午10:50:05
	 */
	public static long getID() {
		StringBuffer sb = new StringBuffer();
		sb.append(IP_LAST);
		sb.append(SequenceUtil.getInstance().getUniqTimer());
		// System.out.println("UniqTimer()="+CreateId.getInstance().getUniqTimer());
		return Long.valueOf(sb.toString());
	}

	/**
	 * 
	 * @param str
	 * @param len
	 * @return
	 * @author chenxt
	 * @created 2013-08-29 上午10:50:05
	 */
	private static String format(String str, int len) {
		if (str.length() < len) {
			for (int i = 0; i < len - str.length(); i++) {
				str = "0" + str;
			}
			return str;
		} else {
			return str;
		}
	}

	private static String formatNum(int number, int len) {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(len);
		formatter.setGroupingUsed(false);
		String str = formatter.format(number);
		// System.out.println("format str=" + str);
		return str;
	}

	private static String formatIP(String str) {
		if (str.length() == 1) {
			str = "31" + str;
			return str;

		} else if (str.length() == 2) {
			str = "4" + str;
			return str;
		} else {
			return str;
		}
	}

	public static void main(String args[]) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 10000; i++) {
			// set.add(SequenceUtilImpl.getInstance().getNextKeyValue());

			set.add(SequenceUtil.getNextKeyValue());
			// set.add(String.valueOf(SequenceUtilImpl.getID()));
			// System.out.println("SequenceId()="
			// + SequenceUtilImpl.getNextKeyValue());
		}

		System.out.println("set size=" + set.size());
		
		

	}

}
