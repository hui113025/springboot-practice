package com.zheng.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.regex.Pattern;


@SuppressWarnings("unchecked")
public class StringUtil {

	private static final Log logger = LogFactory.getLog(StringUtil.class);
	
	/**
	 * Function: Tests the email address is valid or not.
	 * 
	 * In accordance with the demand requirements <br>
	 *
	 * @author
	 * 
	 * @param emailStr
	 * @return
	 */
	public static boolean validateEmail(String emailStr) {
		String email = "^[\\w\\.-]+@[\\w-]+(?:\\.[a-zA-Z]+)+$";
		return Pattern.matches(email, emailStr);
	}

	/**
	 * Function: If you want to judge a string exist or not, for example: Good<br>
	 * but the real string in the content may be GOOD, good, gOOd, gooD, GooD,,,<br>
	 * you can easy know it exist or not, but if you want to replace it, you don't know the real string<br>
	 * If the target string in contentStr are all same,<br>
	 * then use this method is appropriate.<br>
	 * 
	 * @author
	 * 
	 * @param contentStr
	 * @param target
	 *            the target string
	 * @return
	 */
	public static String checkStrExistIgnoreCase(String contentStr, String target) {
		if (empty(contentStr) || empty(target)) {
			return null;
		}
		// all not empty
		int index = contentStr.toLowerCase().indexOf(target.toLowerCase());
		if (index != -1) {
			return contentStr.substring(index, index + target.length());
		}
		// don't contains return null
		return null;
	}

	/**
	 * Function: this method is similar with the above method, <br>
	 * The difference is that: the target string in contentStr are not same, <br>
	 * then use this method return all string form.<br>
	 * For example: <br>
	 * content = "This is a very GoOd-ggAAAggxyz$%#~!Good@solution&gooDgooDGoOd*****GoODGoODGOOD"; <br>
	 * target = good <br>
	 * program will return all different good<br>
	 * GoOd,Good,gooD,GoOD,GOOD
	 * 
	 * @author
	 * 
	 * @param contentStr
	 * @param target
	 * @return
	 */
	public static String[] checkStrMutlExistIgnoreCase(String contentStr, String target) {
		if (empty(contentStr) || empty(target)) {
			return null;
		}
		// all not empty
		int pos = 0;
		int index = 0;
		List<String> list = new ArrayList<String>();
		// efficiency algorithm
		while (pos <= contentStr.length() - target.length()
				&& ((index = contentStr.toUpperCase().indexOf(target.toUpperCase(), pos)) >= pos)) {
			int ep = index + target.length();
			String real = contentStr.substring(index, ep);
			pos = ep;
			// don't add duplicate string
			if (!list.contains(real)) {
				list.add(real);
			}
		}
		if (list.size() > 0) {
			String[] arr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arr[i] = list.get(i);
			}
			return arr;
		}
		// don't contains return null
		return null;
	}

	/**
	 * Function: If object is null return empty else return toString()
	 * 
	 * @author
	 * 
	 * @param object
	 * @return
	 */
	public static String getString(Object object) {
		if (null == object) {
			return "";
		}
		return object.toString();
	}
	
	
	/**
	 * Function: Get Random number by seed
	 * 
	 * @author
	 * 
	 * @param seed
	 * @return
	 */
	public static int getRandomNum(int seed) {
		Random random = new Random();
		return random.nextInt(seed);
	}

	/**
	 * Function: Judge every element in string array is not empty
	 * 
	 * @author
	 * 
	 * @param strArray
	 * @return
	 */
	public static boolean notEmpty(String... strArray) {
		if (strArray == null || strArray.length == 0) {
			return false;
		}
		for (String str : strArray) {
			if (str == null || "".equals(str.trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Function: loop print container item , for test.
	 * 
	 * @author
	 * 
	 * @param c
	 */
	public static void printContainer(Collection c) {
		if (empty(c)) {
			logger.info(">> Collection is empty.");
			return;
		}
		for (Iterator it = c.iterator(); it.hasNext();) {
			Object obj = (Object) it.next();
			logger.info(obj.toString());
		}
	}

	/**
	 * Function: loop print Map item, for test.
	 * 
	 * @author
	 * 
	 * @param map
	 */
	public static void printMap(Map map) {
		logger.info("---------------------Begin: ");
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
			logger.info(entry.getKey() + " = " + entry.getValue());
		}
		logger.info("---------------------End ");
	}

	/**
	 * Function: loop print array item, for test.
	 * 
	 * @author
	 * 
	 * @param objs
	 */
	public static void printArray(Object[] objs) {
		if (empty(objs)) {
			logger.info(">> Array is empty.");
			return;
		}
		for (Object obj : objs) {
			logger.info(obj.toString());
		}
	}

	/**
	 * Function: Checks if the string is not empty.
	 * 
	 * @author
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean notEmpty(String str) {
		return str != null && !"".equals(str.trim());
	}

	/**
	 * Function: judge a collection for null or not
	 * 
	 * @author
	 * 
	 * @param c
	 * @return
	 */
	public static boolean notEmpty(Collection c) {
		return !empty(c);
	}

	/**
	 * Function:
	 * 
	 * @author
	 * 
	 * @param c
	 * @return
	 */
	public static boolean empty(Collection c) {
		return c == null || c.size() == 0;
	}

	/**
	 * 
	 * Function:
	 * 
	 * @author
	 * 
	 * @param os
	 * @return
	 */
	public static boolean empty(Object[] os) {
		return !notEmpty(os);
	}


	/**
	 * return Random in 1000000
	 * 
	 * @return
	 */
	public static int getRandowNumInTenThundrand() {
		return getRandomNum(1000000);
	}

	/**
	 * 
	 * Function:
	 * 
	 * @author
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean notEmpty(Object[] objs) {
		return objs != null && objs.length > 0;
	}

	/**
	 * 
	 * Function: judge string for null
	 * 
	 * @author
	 * 
	 * @param str
	 * @return
	 */
	public static boolean empty(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * remove url port
	 * 
	 * @param url
	 * @return
	 */
	public static String removeUrlPort(String url) {
		String pattern = "^(http:.+)(:\\d{4,5})(.+)$";
		if (Pattern.matches(pattern, url)) {
			return url.replaceFirst(pattern, "$1$3");
		} else {
			return url;
		}
	}
	/**
	 * Function: 1.Separated by commas 2.Such as the comma (,) is the field value using the half-width quotes ("")
	 * contains up 3.Such as the existence of half-angle quotation mark (") should be replaced into the half-width
	 * double quotes (" ") to escape and use the half-angle quotation marks (" ") contains the field values .
	 * 
	 * @author
	 * 
	 * @param args
	 * @return
	 */
	public static String geneCsvArgs(Object[] args) {
		StringBuilder param = new StringBuilder();
		for (Object arg : args) {
			if (arg != null) {
				String argStr = arg.toString();
				// First. replace one double quotes to two double quotes.
				if (argStr.indexOf("\"") >= 0) {
					argStr = argStr.replaceAll("\"", "\"\"");
				}
				// Then. Wrap with double quotes
				param.append("\"").append(argStr).append("\",");
			} else {
				param.append("Null,");
			}
		}
		param.deleteCharAt(param.length() - 1);
		return param.toString();
	}

	/**
	 * Function: Reverser csv to generate common args
	 * 
	 * @author
	 * 
	 * @param param
	 * @return
	 */
	public static String[] revCsv2Str(String param) {
		if (StringUtil.empty(param)) {
			return null;
		} else {
			List<String> list = new ArrayList<String>();
			int qNum = 0;
			boolean lastChEqQt = false;
			boolean thisChEqQt = false;
			// Determine whether a string is end
			int j = 0;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < param.length(); i++) {
				j++;
				String ch = param.substring(i, i + 1);
				// If quotes don't add
				if ("\"".equals(ch)) {
					qNum++;
					thisChEqQt = true;
				} else {
					// Not equal, add
					sb.append(ch);
					thisChEqQt = false;
				}
				// If continuous existence two quotes
				if (lastChEqQt && thisChEqQt) {
					// add quotes, number reset to zero, and set Continuous false
					sb.append(ch);
					qNum -= 2;
					thisChEqQt = false;
				}
				// This time change to last time
				lastChEqQt = thisChEqQt;

				// If have arrive to string separator or the end.
				if ((",".equals(ch) || j == param.length()) && qNum > 0 && qNum % 2 == 0) {
					if (j < param.length()) {
						// this comma use as delimited
						sb.deleteCharAt(sb.length() - 1);
					}
					list.add(sb.toString());
					// clear buffer
					sb.delete(0, sb.length());
					qNum = 0;
					lastChEqQt = thisChEqQt = false;
				}
			}
			// Convert to array
			Object[] objs = list.toArray();
			// Convert to String array, easy use in other file.
			String[] retArr = new String[objs.length];
			for (int i = 0; i < objs.length; i++) {
				retArr[i] = objs[i].toString();
			}
			return retArr;
		}
	}

	/**
     * Convert Json
     * 
     * @param s
     * @return
     */
    public static String string2Json(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
  
            char c = s.charAt(i);
            switch (c)
            {
            case '\"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
    
            case '‘':
                sb.append("");
                break;     
            
            case '/':
                sb.append("\\/");
                break;
            
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                sb.append(c);
            	break;
            }
        }
        return sb.toString();
    }
}