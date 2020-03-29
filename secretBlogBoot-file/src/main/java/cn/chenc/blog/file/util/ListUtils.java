package cn.chenc.blog.file.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtils {

	/**
	 * 将数组转换为制定字符串隔开的字符串。
	 *
	 * @param array
	 *            数组
	 * @param split
	 *            分割符
	 * @return
	 */
	public static String arrayToString(int[] array, String split) {
		if (array == null || array.length == 0)
			return "";
		String result = "";
		for (int i = 0; i < array.length - 1; i++)
			result += array[i] + split;
		result += array[array.length - 1];
		return result;
	}

	/**
	 * 将集合转换为制定字符串隔开的字符串。
	 *
	 *            数组
	 * @param split
	 *            分割符
	 * @return
	 */
	public static String listToString(List list, String split) {
		if (list == null)
			return "";
		Object[] array = list.toArray(new Object[list.size()]);
		return arrayToString(array, split);
	}

	/**
	 * 将数组转换为制定字符串隔开的字符串。
	 *
	 * @param array
	 *            数组
	 * @param split
	 *            分割符
	 * @return
	 */
	public static String arrayToString(Object[] array, String split) {
		return arrayToString(array, split, null);
	}

	/**
	 * 将数组转换为制定字符串隔开的字符串。
	 *
	 * @param array
	 *            数组
	 * @param split
	 *            分割符
	 * @return
	 */
	public static String arrayToString(Object[] array, String split, String valueWrapper) {
		if (array == null || array.length == 0)
			return "";
		String result = "", strWrapper = (valueWrapper == null ? "" : valueWrapper);
		for (int i = 0; i < array.length - 1; i++)
			result += strWrapper + array[i] + strWrapper + split;
		result += strWrapper + array[array.length - 1] + strWrapper;
		return result;
	}

	/**
	 * List类型转换为数组类型String[]
	 *
	 * @param a
	 * @return
	 */
	public static String[] listToArrayString(List<String> a) {
		if (a != null && a.size() > 0) {
			String[] temZ = new String[a.size()];
			a.toArray(temZ);
			return temZ;
		} else {
			return null;
		}
	}

	public static List<String> stringToListString(String str){
	    if(StringUtils.isEmpty(str)){
	        return new ArrayList<String>();
	    }
		return Arrays.asList(str.split(","));
	}
	public static List<Object> stringToListObject(String str){
	    if(StringUtils.isEmpty(str)){
            return new ArrayList<Object>();
        }
		return Arrays.asList(str.split(","));
	}


}
