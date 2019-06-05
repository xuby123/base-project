package com.joe.factory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 *<p>功能描述: API调用客户端工厂</p >
 *<ul>
 *<li>@param null </li>
 *<li>@return </li>
 *<li>@throws </li>
 *<li>@author xubiyu</li>
 *<li>@date 2019-06-05 18:51</li>
 *</ul>
*/
public class AlipayAPIClientFactory {

    //调用地址
    private static final String SERVERURL = "";

    //H5支付appid
    private static final String ALIAPPID = "";

    //生活号的appid
    private static final String SHALIAPPID = "";

    //小程序的appid
    private static final String XCX_APPID ="";

    //生成的秘钥
    private static final String PRIVEKEY = "";

    //支付宝公钥，不是和私钥一起生成，是上传秘钥，支付宝那边返回的 (这里三个应用都是同一个支付的公钥和秘钥。所以都是同一个支付宝公钥)
    private static final String PUBLICKEY ="";

    /**
     * API调用客户端
     */
    private static AlipayClient alipayClient;

    private static AlipayClient alipayClientShh;

    private static AlipayClient alipayClientScx;

    /**
     * 获得API调用客户端
     *
     * @return
     */
    public static AlipayClient getAlipayClient() {
        /*这里使用生活号支付宝公钥，不是应用公钥*/
        if (null == alipayClient) {
            alipayClient = new DefaultAlipayClient(SERVERURL, ALIAPPID,
                    PRIVEKEY, "json", "UTF-8",PUBLICKEY, "RSA2");
        }
        return alipayClient;
    }


    /*支付宝生活号*/
    public static AlipayClient getAlipayClientShh() {

        if (null == alipayClientShh) {
            /*生活号*/
            alipayClientShh = new DefaultAlipayClient(SERVERURL, SHALIAPPID,
                    PRIVEKEY, "json", "UTF-8", PUBLICKEY, "RSA2");

        }
        return alipayClientShh;
    }

    /*支付宝小程序*/
    public static AlipayClient getAlipayClientScx() {

        if (null == alipayClientScx) {
            /*小程序*/
            alipayClientScx = new DefaultAlipayClient(SERVERURL, XCX_APPID,
                    PRIVEKEY, "json", "UTF-8", PUBLICKEY, "RSA2");

        }
        return alipayClientScx;
    }
}
