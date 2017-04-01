package net.okdi.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientStatuUtil {
	public static String getStatu(String detil) {
		if("签收".equals(detil)){
			return "1";
		}
		String[] strAyy = { "已签收", "图片签收", "图片 签收", "拍照签收", "代签收", "门卫签收",
				"已签", "草签", "签收成功", "正常签收", "已【签收】", " 签收", "妥投", "已妥投",
				"派送成功", "已交付", "正常签收", "已送达", "送达", "本人收签收", "已送货","收发章收" };
		if (detil == null)
			return null;
		for (int i = 0; i < strAyy.length; i++) {
			if (detil.contains(strAyy[i])) {
				return "1";
			}
		}
		return "0";
	}

	public static String formatDateString(String dateString)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		date = sdf.parse(dateString);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		return sdf2.format(date);
	}
}
