package com.joe.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <dl>
 * <dt>ToolUtil</dt>
 * <dd>Description: 常用工具类</dd>
 * <dd>Copyright: Copyright (C) 2019</dd>
 * <dd>Company:</dd>
 * <dd>CreateDate: 2019/5/22</dd>
 * </dl>
 *
 * @author xby
 */
public class ToolUtil {

    private static  final String MOBILE_MSG = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";

    public ToolUtil() {
    }

    /**
     * <p>功能描述:isEmpty 判断object 是否为null ""."null"</p>
     * <ul>
     * <li>@param obj </li>
     * <li>@return boolean</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/5/22 14:43</li>
     * </ul>
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if ("".equals(obj)) {
            return true;
        } else if ("null".equals(obj)) {
            return true;
        }
        return false;
    }

    /**
     * <p>功能描述:strIsEmpty 判断字符串是否为空</p>
     * <ul>
     * <li>@param temStr </li>
     * <li>@return boolean</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/5/22 14:40</li>
     * </ul>
     */
    public static boolean strIsEmpty(String temStr) {
        if(null == temStr){
            return true;
        }else{
            int len = temStr.length();
            if (len == 0) {
                return true;
            } else {
                int i = 0;
                while(i < len) {
                    switch(temStr.charAt(i)) {
                        case '\t':
                        case '\n':
                        case '\r':
                        case ' ':
                            ++i;
                            break;
                        default:
                            return false;
                    }
                }
                return true;
            }
        }
    }


    /**
     *<p>功能描述:isChinaPhoneLegal 判断是否为正确的手机号</p>
     *<ul>
     *<li>@param str </li>
     *<li>@return boolean</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2019/5/5 15:05</li>
     *</ul>
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Pattern p = Pattern.compile(MOBILE_MSG);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * <p>功能描述:isChinesePunctuation 判断是否包含中文标点符号</p>
     * <ul>
     * <li>@param str </li>
     * <li>@return boolean</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/4/18 10:18</li>
     * </ul>
     */
    public static boolean isChinesePunctuation(String str) {
        Pattern patPunc = Pattern.compile("[~！@#￥……&*（）——|【】‘；：”“'。，、？]");
        Matcher matcher = patPunc.matcher(str);
        return matcher.find();
    }


    /**
     *<p>功能描述:金额字符串转换：单位分转成单元</p >
     *<ul>
     *<li>@param [o]</li>
     *<li>@return java.lang.String</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2017/12/8 11:42</li>
     *</ul>
     */
    public static String fenToYuan(Object o) {
        if (o == null) {
            return "0.00";
        }
        String s = o.toString();
        int len = -1;
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
            s = removeZero(s);
            if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
                len = s.length();
                int tmp = s.indexOf("-");
                if (tmp >= 0) {
                    if (len == 2) {
                        sb.append("-0.0").append(s.substring(1));
                    } else if (len == 3) {
                        sb.append("-0.").append(s.substring(1));
                    } else {
                        sb.append(s.substring(0, len - 2)).append(".").append(s.substring(len - 2));
                    }
                } else {
                    if (len == 1) {
                        sb.append("0.0").append(s);
                    } else if (len == 2) {
                        sb.append("0.").append(s);
                    } else {
                        sb.append(s.substring(0, len - 2)).append(".").append(s.substring(len - 2));
                    }
                }
            } else {
                sb.append("0.00");
            }
        } else {
            sb.append("0.00");
        }
        return sb.toString();
    }

    /**
     *<p>功能描述:金额字符串转换：单位元转成单分</p >
     *<ul>
     *<li>@param [o]</li>
     *<li>@return java.lang.String</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2017/12/8 11:42</li>
     *</ul>
     */
    public static String yuanToFen(Object o) {
        if (o == null) {
            return "0";
        }
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
            posIndex = s.indexOf(".");
            if (posIndex > 0) {
                int len = s.length();
                str = s.substring(0, posIndex);
                if ("0".equals(str)) {
                    str = "";
                }
                if (len == posIndex + 1) {
                    sb.append(str).append("00");
                } else if (len == posIndex + 2) {
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 2)).append("0");
                } else if (len == posIndex + 3) {
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                } else {
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                }
            } else {
                sb.append(s).append("00");
            }
        } else {
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            return str;
        } else {
            return "0";
        }
    }

    /**
     *<p>功能描述:去除字符串首部为"0"字符</p >
     *<ul>
     *<li>@param [str]</li>
     *<li>@return java.lang.String</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2017/12/8 11:46</li>
     *</ul>
     */
    public static String removeZero(String str) {
        char ch;
        String result = "";
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            try {
                for (int i = 0; i < str.length(); i++) {
                    ch = str.charAt(i);
                    if (ch != '0') {
                        result = str.substring(i);
                        break;
                    }
                }
            } catch (Exception e) {
                result = "";
            }
        } else {
            result = "";
        }
        return result;

    }


    


}