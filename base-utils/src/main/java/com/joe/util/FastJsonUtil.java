package com.joe.util;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dt>FastJsonUtil</dt>
 * <dd>Description: fastJson工具类</dd>
 * <dd>Copyright: Copyright (C) 2019</dd>
 * <dd>Company:</dd>
 * <dd>CreateDate: 2019/5/22</dd>
 * </dl>
 *
 * @author xby
 */
public class FastJsonUtil {

    /**
     * <p>功能描述:toJson 转json字符串</p>
     * <ul>
     * <li>@param obj </li>
     * <li>@return java.lang.String</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/5/22 14:26</li>
     * </ul>
     */
    public static String toJson(Object obj) {
        if (ToolUtil.isEmpty(obj)) {
            return null;
        }
        //关闭fastJson循环引用
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * <p>功能描述:jsonStrToBean json字符串转javaBean</p>
     * <ul>
     * <li>@param jsonStr </li>
     * <li>@param t </li>
     * <li>@return T</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/5/22 14:37</li>
     * </ul>
     */
    public static <T> T jsonStrToBean(String jsonStr, Class<T> clazz) {
        if (ToolUtil.isEmpty(jsonStr)) {
            return null;
        }
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * <p>功能描述:jsonStrArray 把JSON文本parse成JSONArray </p>
     * <ul>
     * <li>@param jsonStr </li>
     * <li>@return com.alibaba.fastjson.JSONArray</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/5/22 15:30</li>
     * </ul>
     */
    public static JSONArray jsonStrArray(String jsonStr) {
        return JSON.parseArray(jsonStr);
    }

    /**
     *<p>功能描述:isJSONValid 判断是否为 JSONObject OR JSONArray</p>
     *<ul>
     *<li>@param jonsStr </li>
     *<li>@return boolean</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2019/5/22 16:34</li>
     *</ul>
     */
    public static boolean isJSONValid(String jonsStr){
        try {
            JSONObject.parseObject(jonsStr);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(jonsStr);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    /**
     * <p>功能描述:jsonStrtoList jsonStr转javaBean 集合</p>
     * <ul>
     * <li>@param jsonStr </li>
     * <li>@param clazz </li>
     * <li>@return java.util.List<T></li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/5/22 15:18</li>
     * </ul>
     */
    public static <T> List<T> jsonStrToList(String jsonStr, Class<T> clazz) {
        if (ToolUtil.isEmpty(jsonStr)) {
            return null;
        }
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     *<p>功能描述:jsonStrtoMap jsonStr 转Map</p>
     *<ul>
     *<li>@param jsonStr </li>
     *<li>@return java.util.Map<java.lang.String,java.lang.Object></li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2019/5/22 16:16</li>
     *</ul>
     */
    public static Map<String,Object> jsonStrToMap(String jsonStr){
        if(ToolUtil.isEmpty(jsonStr)){
            return null;
        }
        return JSON.parseObject(jsonStr,new TypeReference<Map<String,Object>>(){});
    }

}