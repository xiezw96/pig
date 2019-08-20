package com.pig4cloud.pig.utils;

import java.util.Random;

/**
 * <p>Title: ShareCodeUtil</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年05月12日
 * @since 1.8
 */
public class ShareCodeUtil {
	/**
	 * 自定义进制(0,1没有加入,容易与o,l混淆)
	 */
	private static final char[] r = new char[]{'Q', 'w', 'e', '8', 'a', 'S', '2', 'd', 'Z', 'x', '9', 'C', '7', 'p', '5', 'I', 'k', '3', 'm', 'J', 'u', 'f', 'r', '4', 'V', 'y', 'l', 't', 'N', '6', 'b', 'g', 'h'};

	/**
	 * (不能与自定义进制有重复)
	 */
	private static final char b = 'o';

	/**
	 * 进制长度
	 */
	private static final int binLen = r.length;

	/**
	 * 序列最小长度
	 */
	private static final int s = 6;

	/**
	 * 根据ID生成六位随机码，最大ID为728999999，超过进位
	 *
	 * @param id ID
	 * @return 随机码
	 */
	public static String toSerialCode(long id) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}
}
