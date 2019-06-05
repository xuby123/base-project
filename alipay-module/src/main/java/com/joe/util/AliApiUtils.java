package com.joe.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.google.common.collect.Maps;
import com.joe.factory.AlipayAPIClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <dl>
 * <dt>AliApiUtils</dt>
 * <dd>Description:支付号生活号开发，工具类</dd>
 * <dd>Copyright: Copyright (C) 2017</dd>
 * <dd>Company:步长科技有限公司</dd>
 * <dd>CreateDate: 2018/1/8</dd>
 * </dl>
 *
 * @author xby
 */
public class AliApiUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliApiUtils.class);

    /**
    *<p>功能描述:根据authCode获取用户信息</p >
    *<ul>
    *<li>@param [authCode]</li>
    *<li>@return java.lang.String</li>
    *<li>@throws </li>
    *<li>@author xuby</li>
    *<li>@date 2018/1/8 15:57</li>
    *</ul>
    */
    public static String getUserInfo(String authCode) {
        try {
            //3. 利用authCode获得authToken
            AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
            oauthTokenRequest.setCode(authCode);
            oauthTokenRequest.setGrantType("authorization_code");
            /*单例模式？*/
            AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClientScx();
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient
                    .execute(oauthTokenRequest);
            LOGGER.info("获取到的accessToken===={}",oauthTokenResponse.getAccessToken());
            //成功获得authToken
            if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
                //4. 利用authToken获取用户信息
                AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
                AlipayUserInfoShareResponse response = alipayClient.execute(request,oauthTokenResponse.getAccessToken());
                //成功获得用户信息
                if (null != response && response.isSuccess()) {
                    //这里仅是简单打印， 请开发者按实际情况自行进行处理
                    LOGGER.info("获取用户信息成功{}",response.getBody());
                    return response.getBody();
                } else {
                    LOGGER.info("获取用户信息失败");
                }
            } else {
                LOGGER.info("authCode换取authToken失败");
            }
        } catch (AlipayApiException alipayApiException) {
            alipayApiException.printStackTrace();
        }
        return null;
    }

    /**
    *<p>功能描述:手机网站支付，支付解析支付回调参数</p >
    *<ul>
    *<li>@param [params]</li>
    *<li>@return java.util.Map<java.lang.String,java.lang.String></li>
    *<li>@throws </li>
    *<li>@author xuby</li>
    *<li>@date 2018/1/8 16:39</li>
    *</ul>
    */
    public static Map<String,String> getParameterMap(String params) {
        // 返回值Map
        Map<String,String> returnMap =null;
        if (!ToolUtil.strIsEmpty(params)) {
            returnMap = new HashMap<>();
            String[] KeyValues = params.split("&");
            for (int i = 0; i < KeyValues.length; i++) {
                String[] keyValue = KeyValues[i].split("=");
                /*参数需要url转一下*/
                try {
                    returnMap.put(keyValue[0], URLDecoder.decode(keyValue[1],"UTF-8"));
                    LOGGER.info(keyValue[0]+"------------->"+URLDecoder.decode(keyValue[1],"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMap;
    }

    
    /**
    *<p>功能描述:支付宝App支付解析参数</p >
    *<ul>
    *<li>@param [params]</li>
    *<li>@return java.util.Map<java.lang.String,java.lang.String></li>
    *<li>@throws </li>
    *<li>@author xuby</li>
    *<li>@date 2018/5/30 10:47</li>
    *</ul>
    */
    public static Map<String,String> getParameter(Map params){

        Map<String,String> map = Maps.newHashMap();
        for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) params.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            /*try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            map.put(name, valueStr);
        }
        return map;
    }


//    public static Map<String, String> getRequestParams(HttpServletRequest request){
//        Map<String, String> params = Maps.newHashMap();
//        if(null != request){
//            Set<String> paramsKey = request.getParameterMap().keySet();
//            for(String key : paramsKey){
//                params.put(key, request.getParameter(key));
//            }
//        }
//        return params;
//    }
}