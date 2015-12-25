package com.waimai.ops.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 * 
 * @author wenzhiwei 2015年12月24日 CommonHelper by eclipse
 */
public class CommonUtil {

	/** Prefix for system property placeholders: "${" */
	public static final String PLACEHOLDER_PREFIX = "${";

	/** Suffix for system property placeholders: "}" */
	public static final String PLACEHOLDER_SUFFIX = "}";
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/** DOCUMENT ME! */
	public static final String INDEX_FILED_SPERATER = " ";

	/**
	 * 私有构建器，本类中的所有方法均为静态引用
	 */
	private CommonUtil() {
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param indexField
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static String createIndexField(Collection<String> indexField) {
		if ((indexField == null) || indexField.isEmpty()) {
			return "";
		}

		StringBuilder result = new StringBuilder(INDEX_FILED_SPERATER);

		for (String f : indexField) {
			if (StringUtils.isNotEmpty(f)) {
				result.append(f).append(INDEX_FILED_SPERATER);
			}
		}

		return result.toString();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param indexField
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static String[] splitIndexField(String indexField) {
		if (StringUtils.isNotBlank(indexField)) {
			indexField = indexField.trim();
			indexField = indexField.replaceAll("\\s+", INDEX_FILED_SPERATER);

			return indexField.split(INDEX_FILED_SPERATER);
		}

		return new String[] {};
	}

	/**
	 * 判断时间是否是今天之前,是返回true，不是返回false
	 * 
	 * @param date
	 * @return
	 */
	public static boolean beforeToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Calendar inday = Calendar.getInstance();
		inday.setTime(date);
		return inday.before(cal);
	}

	/**
	 * 根据类型转换对象
	 * 
	 * @param initialValue
	 * @param dataType
	 * 
	 * @return
	 */
	public static Object parseObject(String initialValue, String dataType) {
		Object ret = null;

		if ((initialValue == null) || (initialValue.trim().length() == 0)) {
			if (dataType.equals("FLOAT")) {
				ret = 0f;
			} else if (dataType.equals("INTEGER")) {
				ret = 0d;
			} else if (dataType.equals("DATETIME")) {
				ret = new Date();
			} else if (dataType.equals("BOOLEAN")) {
				ret = false;
			} else {
				ret = "";
			}
		} else {
			if (dataType.equals("FLOAT")) {
				Float value;

				try {
					value = new Float(initialValue);
				} catch (NumberFormatException nfe) {
					value = new Float(0);
				}

				ret = value;
			} else if (dataType.equals("INTEGER")) {
				Integer value;

				try {
					value = Integer.valueOf(initialValue);
				} catch (NumberFormatException nfe) {
					try {
						Double d = Double.valueOf(initialValue);
						value = Integer.valueOf(d.intValue());
					} catch (Exception ex) {
						value = new Integer(0);
					}
				}

				ret = value;
			} else if (dataType.equals("DATETIME")) {
				Date value;

				try {
					value = DateFormat.getDateInstance().parse(initialValue);
				} catch (Exception pe) {
					try {
						value = new Date(Long.parseLong(initialValue));
					} catch (Exception ex) {
						value = new Date();
					}
				}

				ret = value;
			} else if (dataType.equals("BOOLEAN")) {
				Boolean value = Boolean.FALSE;

				try {
					if (initialValue.equalsIgnoreCase("true") || initialValue.equals("1")) {
						value = Boolean.TRUE;
					} else if (initialValue.equalsIgnoreCase("false") || initialValue.equals("0")) {
						value = Boolean.FALSE;
					}
				} catch (Exception ex) {
				}

				ret = value;
			} else {
				ret = initialValue;
			}
		}

		return ret;
	}

	/**
	 * 将特定字符串分隔成List
	 * 
	 * @param specialStr
	 *            待分隔字符串
	 * @param split
	 *            分隔符
	 * 
	 * @return
	 */
	public static List<String> splitString2List(String specialStr, String split) {
		List<String> retValue = new ArrayList<String>();

		if (StringUtils.isEmpty(specialStr)) {
			return retValue;
		}

		if (StringUtils.isEmpty(split)) {
			if (StringUtils.isNotBlank(specialStr)) {
				retValue.add(specialStr);
			}

			return retValue;
		}

		String[] strs = specialStr.split(split);

		for (String value : strs) {
			if (StringUtils.isNotEmpty(value)) {
				retValue.add(value);
			}
		}

		return retValue;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param beanClass
	 *            DOCUMENT ME!
	 * @param beanInstance
	 *            DOCUMENT ME!
	 * @param fieldName
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	@SuppressWarnings("rawtypes")
	public static Object getBeanFieldValueByName(Class beanClass, Object beanInstance, String fieldName) {
		try {
			Field field = beanClass.getDeclaredField(fieldName);

			if (!field.isAccessible()) {
				field.setAccessible(true);
			}

			return field.get(beanInstance);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 验证邮件地址是否合法
	 * 
	 * @param email
	 *            待验证邮件地址
	 * 
	 * @return
	 */
	public static boolean validateEmail(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]{2,3}");
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * 验证电话是否合法
	 * 
	 * @param phone
	 *            待验证邮件地址
	 * 
	 * @return
	 */
	public static boolean validatePhone(String phone) {
		Pattern p = Pattern.compile("1[3|5|8]\\d{9}");
		Matcher m = p.matcher(phone);

		return m.matches();
	}

	/**
	 * Resolve ${...} placeholders in the given text, replacing them with
	 * corresponding system property values.
	 * 
	 * @param text
	 *            the String to resolve
	 * @param properties
	 *            DOCUMENT ME!
	 * 
	 * @return the resolved String
	 * 
	 * @see #PLACEHOLDER_PREFIX
	 * @see #PLACEHOLDER_SUFFIX
	 */
	public static String resolvePlaceholders(String text, Properties properties) {
		StringBuilder buf = new StringBuilder(text);
		int startIndex = text.indexOf(PLACEHOLDER_PREFIX);

		while (startIndex != -1) {
			int endIndex = buf.toString().indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());

			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
				int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();

				try {
					String propVal = properties.getProperty(placeholder);

					if (propVal == null) {
						propVal = System.getenv(placeholder);
					}

					if (propVal != null) {
						buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
						nextIndex = startIndex + propVal.length();
					}
				} catch (Exception ex) {
					// do nothing
				}

				startIndex = buf.toString().indexOf(PLACEHOLDER_PREFIX, nextIndex);
			} else {
				startIndex = -1;
			}
		}

		return buf.toString();
	}

	/**
	 * MD5加密
	 * 
	 * @param src
	 *            String 要加密的串
	 * 
	 * @return String 加密后的字符串
	 */
	public static String md5Encoding(String src) {
		try {
			byte[] strTemp = src.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(str);
		} catch (Exception e) {
			return src;
		}
	}

	/**
	 * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @param isNumOrChar
	 *            是否只包含数字或字母
	 * 
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len, boolean isNumOrChar) {
		// 26个字母+10个数字,去掉0，O，0，l,1,I六个
		String iniStr = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789!@#$%^*()_-+=";

		if (isNumOrChar) {
			iniStr = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
		}

		char[] str = iniStr.toCharArray();
		int strlength = str.length;
		StringBuilder pwd = new StringBuilder("");
		Random r = new Random();
		boolean isComplexPwd = false; // 是否为符合规范的随机码

		while (!isComplexPwd) {
			for (int i = 0; i < pwd_len; i++) {
				// 生成随机数，取绝对值，防止生成负数，
				pwd.append(str[Math.abs(r.nextInt(strlength))]);
			}

			isComplexPwd = isComplexPwd(pwd.toString());

			// 是否复杂
			if (!isComplexPwd) {
				pwd = new StringBuilder(""); // 清空pwd
			}
		}

		return pwd.toString();
	}

	/**
	 * 判断输入是否是一个由 0-9 / A-Z / a-z /特殊字符4个种的3个类型组成
	 * 
	 * @param pwd
	 * 
	 * @return
	 */
	private static boolean isComplexPwd(String pwd) {
		boolean isComplexPwd = false;
		int intScore = 0;
		Pattern pattern = Pattern.compile("[a-z]");
		Matcher matcher = pattern.matcher(pwd);

		if (matcher.find()) {
			intScore++;
		}

		pattern = Pattern.compile("[A-Z]");
		matcher = pattern.matcher(pwd);

		if (matcher.find()) {
			intScore++;
		}

		pattern = Pattern.compile("\\d");
		matcher = pattern.matcher(pwd);

		if (matcher.find()) {
			intScore++;
		}

		pattern = Pattern.compile("[!,@#$%^&*?_~]");
		matcher = pattern.matcher(pwd);

		if (matcher.find()) {
			intScore++;
		}

		if (intScore >= 3) {
			isComplexPwd = true;
		}

		return isComplexPwd;
	}

	private static InputStream getInputStreamFromString(final String str) {
		InputStream in = new ByteArrayInputStream(str.getBytes());
		return in;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param a
	 *            DOCUMENT ME!
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String deepToString(Object[] a) {
		if (a == null) {
			return "null";
		}

		int bufLen = 20 * a.length;

		if ((a.length != 0) && (bufLen <= 0)) {
			bufLen = Integer.MAX_VALUE;
		}

		StringBuilder buf = new StringBuilder(bufLen);
		deepToString(a, buf, new HashSet());

		return buf.toString();
	}

	/**
	 * 显示值
	 * 
	 * @param a
	 * @param maxSize
	 *            最大的位数
	 * 
	 * @return
	 */
	public static String deepToString(Object[] a, int maxSize) {
		String str = deepToString(a);

		if (str.length() > maxSize) {
			return str.substring(0, maxSize);
		}

		return str;
	}

	@SuppressWarnings("rawtypes")
	private static void deepToString(Object[] a, StringBuilder buf, Set<Object[]> dejaVu) {
		if (a == null) {
			buf.append("null");

			return;
		}

		dejaVu.add(a);
		buf.append('[');

		for (int i = 0; i < a.length; i++) {
			if (i != 0) {
				buf.append(", ");
			}

			Object element = a[i];

			if (element == null) {
				buf.append("null");
			} else {
				Class eClass = element.getClass();

				if (eClass.isArray()) {
					if (eClass == byte[].class) {
						buf.append("[....]"); // Arrays.toString((byte[])
						// element));
					} else if (eClass == short[].class) {
						buf.append(Arrays.toString((short[]) element));
					} else if (eClass == int[].class) {
						buf.append(Arrays.toString((int[]) element));
					} else if (eClass == long[].class) {
						buf.append(Arrays.toString((long[]) element));
					} else if (eClass == char[].class) {
						buf.append(Arrays.toString((char[]) element));
					} else if (eClass == float[].class) {
						buf.append(Arrays.toString((float[]) element));
					} else if (eClass == double[].class) {
						buf.append(Arrays.toString((double[]) element));
					} else if (eClass == boolean[].class) {
						buf.append(Arrays.toString((boolean[]) element));
					} else { // element is an array of object references

						if (dejaVu.contains(element)) {
							buf.append("[...]");
						} else {
							deepToString((Object[]) element, buf, dejaVu);
						}
					}
				} else if (element instanceof Collection) {
					deepToString(((Collection) element).toArray(), buf, dejaVu);
					deepToString(((Collection) element).toArray());
				} else {
					// element is non-null and not an array
					try {
						// 自己或父类实现了toString方法，调用自己的
						/*
						 * if(!element.getClass().getMethod("toString").
						 * getDeclaringClass().equals(Object.class)) {
						 * buf.append(element.toString()); } else {
						 * buf.append(ReflectionToStringBuilder
						 * .toString(element)); }
						 */
						buf.append(element.toString());
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}

		buf.append("]");
		dejaVu.remove(a);
	}

	/**
	 * 计算单层文件夹下的文件大小和
	 * 
	 * @param dirPath
	 * 
	 * @return
	 */
	public static long computeDirSize(String dirPath) {
		File dirFile = new File(dirPath);

		if (!dirFile.exists()) {
			return 0;
		}

		long totalSize = 0L;
		File[] files = dirFile.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}

			totalSize += file.length();
		}

		return totalSize;
	}

	/**
	 * 为url添加http
	 * 
	 * @param url
	 * 
	 * @return
	 */
	public static String handleUrl(String url) {
		if (StringUtils.isNotEmpty(url)) {
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}
		}

		return url;
	}

	/**
	 * 将parameterMap附加在href中
	 * 
	 * @param parameterMap
	 *            DOCUMENT ME!
	 * @param href
	 * 
	 * @return DOCUMENT ME!
	 */
	@SuppressWarnings("rawtypes")
	public static String getParamtersUrlPatten(Map parameterMap, String href) {
		if (parameterMap == null) {
			return "";
		}

		// 解析当前href中的参数
		List<String> myParams = new ArrayList<String>();
		int n = -1;

		if ((n = href.indexOf('?')) > 0) {
			String p = href.substring(n + 1);
			myParams.addAll(Arrays.asList(p.split("&")));
		}

		StringBuilder ret = new StringBuilder();
		Set set = parameterMap.entrySet();

		for (Object elem : set) {
			Map.Entry entry = (Map.Entry) elem;
			String key = entry.getKey().toString();
			Object value = entry.getValue();

			if (value instanceof String[]) {
				String[] vs = (String[]) value;

				for (int i = 0; i < vs.length; i++) {
					String p = key + "=" + vs[i];

					if (myParams.contains(p)) {
						continue;
					}

					ret.append(p);
					ret.append("&");
				}
			} else {
				String p = key + "=" + value;

				// 如果已经存在，则不再添加了
				if (myParams.contains(p)) {
					continue;
				}

				ret.append(p);
				ret.append("&");
			}
		}

		if (ret.length() > 0) {
			ret.deleteCharAt(ret.length() - 1);
		}

		return ret.toString();
	}

	/**
	 * 去除字符串两端的空格，并且如果字符串末尾含有/, \ , 则去除
	 * 
	 * @param originString
	 * @return
	 */
	public static String dealStringTail(String originString) {
		if (originString == null) {
			return null;
		}

		String ret = originString.trim();
		for (int i = ret.length() - 1; i >= 0; i--) {
			if ((ret.charAt(i) == '/') || (ret.charAt(i) == '\\')) {
				ret = ret.substring(0, i);
			} else {
				break;
			}
		}

		return ret;

	}

	/**
	 * 判断一个集合里面的字符串是否全是null或者""
	 * 
	 * @param collection
	 *            要判断的集合
	 * @return 集合本身为空或者元素全部为空，返回true，其余情况均为false
	 */
	public static boolean checkCollectionItemsNull(Collection<String> collection) {
		if (CollectionUtils.isEmpty(collection)) {
			return true;
		}
		boolean allNull = true;
		for (String str : collection) {
			if (!StringUtils.isBlank(str)) {
				allNull = false;
				break;
			}
		}
		return allNull;
	}

	/**
	 * 格式话显示字符串，转化html特殊字符，把回车符合换成<br>
	 * 
	 * @param inputString
	 * @return
	 */
	public static String convertHTMLString(String inputString) {
		// 转化html特殊字符
		inputString = inputString.replaceAll("&", "&amp;");
		inputString = inputString.replaceAll("\"", "&quot;");
		inputString = inputString.replaceAll("<", "&lt;");
		inputString = inputString.replaceAll(">", "&gt;");
		inputString = inputString.replaceAll(" ", "&nbsp;");

		return inputString;
	}

	private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

	public static Object getThreadLocalValue() {

		return threadLocal.get();

	}

	public static void setThreadLocalValue(Object object) {

		threadLocal.set(object);

	}

}
