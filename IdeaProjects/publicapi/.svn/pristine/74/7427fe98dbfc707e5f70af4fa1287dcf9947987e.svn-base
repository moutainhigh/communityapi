package net.okdi.core.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.BeansException;

import com.alibaba.fastjson.JSON;


/**
 * @description: copy from okdiweb
 * @author feng.wang
 * @date 2014-9-5
 * @version: 1.0.0
 */
public class PubMethod {

	public static final String[] flag = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	public static StringBuffer concat(StringBuffer srcStr, String addStr, String splitStr) {
		if (PubMethod.isEmpty(addStr)) {
			return srcStr;
		}
		if (!PubMethod.isEmpty(srcStr) && srcStr.length() > 0 && !PubMethod.isEmpty(addStr)) {
			srcStr.append(splitStr).append(addStr);
		} else {
			srcStr.append(addStr);
		}
		return srcStr;
	}

	/**
	 * List 浅拷贝
	 * 
	 * @param src
	 * @param dest
	 */
	public static void copyCollectionByAdd(Collection src, Collection dest) {
		if (PubMethod.isEmpty(src) || PubMethod.isEmpty(dest))
			return;

		// for (int i = 0 ; i< src.size() ;i++) {//jdk 1.4
		for (Object obj : src) {// jdk 1.5 以上版本
			// Object obj = src.get(i);
			dest.add(obj);
		}
	}

	/*
	 * 对象间值的相互拷贝.
	 */
	public static void copyPropeties(Object srcObj, Object destObj) {
		try {
			org.springframework.beans.BeanUtils.copyProperties(srcObj, destObj);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}

	// 判断是否存在 单引号。
	public static boolean isContainSingleQuotes(String str) {
		if (str.indexOf("'") >= 0)
			return true;
		else
			return false;
	}

	// 判断是否为空。
	public static boolean isEmpty(String Value) {
		return (Value == null || Value.trim().equals("") || Value.trim().equals("null"));
	}

	/*
	 * @function:判空 @author:
	 */
	public static boolean isEmpty(List list) {
		if (list == null || list.size() == 0)
			return true;
		else
			return false;
	}

	/*
	 * @function:判空 @author:
	 */
	public static boolean isEmpty(Set set) {
		if (set == null || set.size() == 0)
			return true;
		else
			return false;
	}

	/*
	 * @function:判空 @author:
	 */
	public static boolean isEmpty(Map map) {
		if (map == null || map.size() == 0)
			return true;
		else
			return false;
	}

	// 判断是否为空。
	public static boolean isEmpty(Object Value) {
		if (Value == null)
			return true;
		else
			return false;
	}

	public static boolean isEmpty(StringBuffer value) {
		if (value == null || value.length() <= 0)
			return true;
		else
			return false;
	}

	public static boolean isEmpty(Double value) {
		if (value == null || value.doubleValue() == 0.0)
			return true;
		else
			return false;
	}

	// 判断是否为空。
	public static boolean isEmpty(Long obj) {
		if (obj == null || obj.longValue() == 0)
			return true;
		else
			return false;
	}

	// 判断是否为空。
	public static boolean isEmpty(Object[] Value) {
		if (Value == null || Value.length == 0)
			return true;
		else
			return false;
	}

	// 返回有效状态值
	public static int validState() {
		return 1;
	}

	// 返回无效状态值
	public static int invalidState() {
		return 0;
	}

	// 判断状态是否有效。0无效、1有效、9删除。
	public static boolean isValid(int state) {
		if (state == 1)
			return true;
		else
			return false;
	}

	// Set集合到List的转换
	public static List getList(Set set) {
		List list = new ArrayList();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	/**
	 * 把List转换为Set
	 * 
	 * @param set
	 * @return
	 */
	public static Set convertList2Set(List list) {
		if (list == null || list.size() == 0)
			return null;
		Set set = new HashSet();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * 用户名正则验证
	 * 
	 * @param account
	 *            用户名
	 * @return
	 */
	public static boolean regexAccount(String account) {

		return true;

	}

	/**
	 * 密码正则验证
	 * 
	 * @param account
	 *            用户名
	 * @return
	 */
	public static boolean regexPwd(String pwd) {

		return true;

	}

	/**
	 * 获取系统操作时间
	 * 
	 * @param
	 * @return String
	 */
	public static String getSysOptDate() {
		Calendar date = Calendar.getInstance();
		Date sysDate = date.getTime();
		String optDate = PubMethod.dateToString(sysDate, "yyyy-MM-dd HH:mm:ss");
		return optDate;
	}

	/**
	 * 字符串转换为Date类型
	 * 
	 * @param strValue
	 *            String 日期串
	 * @return Date
	 */
	public static String dateToString(Date dteValue, String strFormat) {
		if (PubMethod.isEmpty(dteValue)) {
			return null;
		}
		SimpleDateFormat clsFormat = new SimpleDateFormat(strFormat);
		return clsFormat.format(dteValue);
	}

	/**
	 * Description（方法描述）:生成随机六位密码(数字+英文) Author（开发人员）:ＨaiＦｅｎｇ.Ｈｅ Receive
	 * parameters(接收参数): Return parameters(返回参数):
	 */
	public static String createPassword() {
		StringBuffer idenCode = new StringBuffer(); // 生成密码
		Random ran = new Random();
		for (int i = 0; i < 6; i++) {
			idenCode.append(flag[ran.nextInt(36)]);
		}
		return idenCode.toString();
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static Timestamp getSysTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * <功能详细描述>
	 * 创 建 人:  文超
	 * 创建时间:  2014-4-23 下午1:40:15  
	 * @param time 单位（秒）
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Timestamp getSysAfterTimestamp(int time) {
        return new Timestamp(System.currentTimeMillis()+time*1000);
    }
	
	/**
	 * 把date 转换为 timestamp
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		if (PubMethod.isEmpty(date)) {
			return null;
		}
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp getCurrentTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	public static String jsonSuccess(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		return JSON.toJSONString(allMap);
	}

	public static String jsonFailure() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		return JSON.toJSONString(map);
	}

	public static String paramsFailure(String errCode,String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", errCode);
		map.put("msg",msg);
		return JSON.toJSONString(map);
	}
	
	public static String paramsFailure() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("message", "parameter is not correct ");
		return JSON.toJSONString(map);
	}
	
	public static String sysError() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", "000");
		map.put("msg","system is error!");
		return JSON.toJSONString(map);
	}
	
	public static String paramError(String errCode,String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", errCode);
		map.put("msg",msg);
		return JSON.toJSONString(map);
	}
	
	public static String sysErrorUS() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("errCode", "000");
		map.put("msg","system is error!");
		map.put("message","system is error!");
		return JSON.toJSONString(map);
	}
	
}
