package com.joe.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.joe.common.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * <p>功能描述: http工具类</p >
 * <ul>
 * <li>@param </li>
 * <li>@return </li>
 * <li>@throws </li>
 * <li>@author xuby</li>
 * <li>@date 2019/3/1 9:59</li>
 * </ul>
 */
public class HttpClientUtils {

    private  static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder httpBulder = null;
    private static RequestConfig requestConfig = null;
    //最高连接数
    private static final int MAXCONNECTION = 200;
    //路由数量
    private static final int DEFAULTMAXPERROTE = 20;
    //响应超时时间
    private static final int SOCKET_TIME_OUT = 5000;
    //请求超时时间
    private static final int CONNECT_TIME_OUT = 5000;
    //连接超时时间
    private static final int CON_REQ_TIME_OUT = 5000;

    static {
        //设置http的状态参数
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIME_OUT)
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(CON_REQ_TIME_OUT)
                .build();
        //目标主机
        //HttpHost target = new HttpHost(IP, PORT);
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAXCONNECTION);
        connectionManager.setDefaultMaxPerRoute(DEFAULTMAXPERROTE);
        //connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
        httpBulder = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig);
    }


    /**
     * <p>功能描述: </p >
     * <ul>
     * <li> @param reqRetry 是否需要重试机制 </li>
     * <li>@return org.apache.http.impl.client.CloseableHttpClient</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/4 10:37</li>
     * </ul>
     */
    private static CloseableHttpClient getHttpClient(Boolean reqRetry) {
        if (reqRetry) {
            /**
             * 测出超时重试机制为了防止超时不生效而设置
             *  如果直接放回false,不重试
             *  这里会根据情况进行判断是否重试
             */
            HttpRequestRetryHandler retry = new HttpRequestRetryHandler() {
                @Override
                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    // 如果已经重试了3次，就放弃
                    if (executionCount >= 3) {
                        return false;
                    }
                    // 如果服务器丢掉了连接，那么就重试
                    if (exception instanceof NoHttpResponseException) {
                        return true;
                    }
                    // 不要重试SSL握手异常
                    if (exception instanceof SSLHandshakeException) {
                        return false;
                    }
                    // 超时
                    if (exception instanceof InterruptedIOException) {
                        return true;
                    }
                    // 目标服务器不可达
                    if (exception instanceof UnknownHostException) {
                        return false;
                    }
                    // 连接被拒绝
                    if (exception instanceof ConnectTimeoutException) {
                        return false;
                    }
                    // ssl握手异常
                    if (exception instanceof SSLException) {
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };
            return httpBulder.setRetryHandler(retry).build();
        } else {
            return httpBulder.build();
        }
    }


    /**
     * 用于判断是否是https
     */
    private static CloseableHttpClient wrapClient(String url, Boolean reqRetry) {
        CloseableHttpClient httpClient = getHttpClient(reqRetry);
        // TODO: 2019/3/4  https 请求
//        if (url.startsWith("https://")) {
//            sslClient(httpClient);
//        }
        return httpClient;
    }


    /**
     * <p>功能描述: 表单方式 post请求 </p >
     * <ul>
     *
     * @param url       完整的请求地址
     * @param headerMap 请求头参数
     * @param bodyMap   请求参数
     * @param encoding  编码
     * @param reqRetry  是否需要重试机制 默认false
     * @param isStream  是否需要用流的方式读取，默认false
     * @param isJson    是否是json方式请求 默认false
     *                  <li>@return java.lang.String</li>
     *                  <li>@throws </li>
     *                  <li>@author xuby</li>
     *                  <li>@date 2019/3/4 17:13</li>
     *                  </ul>
     */
    public static String doPostFormORJson(String url, Map<String, Object> headerMap,
                                          Map<String, Object> bodyMap, String encoding, Boolean reqRetry, Boolean isStream,
                                          Boolean isJson) throws IOException {
        if (ToolUtil.isEmpty(reqRetry)) {
            reqRetry = false;
        }
        if (ToolUtil.isEmpty(isStream)) {
            isStream = false;
        }
        if (ToolUtil.isEmpty(isJson)) {
            isJson = false;
        }
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try {
            //初始化HttpClient
            CloseableHttpClient httpClient = wrapClient(url, reqRetry);
            //post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 /Windows; U; Windows NT 4.1; de; rv:1.9.1.5) Gecko/20091102 Firefox/3.0");
            //设置请求头参数
            if (null != headerMap) {
                for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                    if (!ToolUtil.isEmpty(entry.getValue())) {
                        httpPost.setHeader(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            if (isJson) {
                if (null == bodyMap) {
                    bodyMap = Maps.newHashMap();
                }
                //设置默认报文头
                httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
                StringEntity stringEntity = new StringEntity(FastJsonUtil.toJson(bodyMap), "utf-8");
                //stringEntity.setContentType("application/json");
                //设置请求体
                httpPost.setEntity(stringEntity);
            } else {
                //设置默认报文头
                httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
                //请求体
                httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(bodyMap), "utf-8"));
            }
            httpResponse = httpClient.execute(httpPost);
            result = getResult(httpResponse, encoding, isStream);
        } catch (Exception e) {
            LOGGER.error(CommonConstant.ERROR_MESSAGE, e);
        } finally {
            if (null != httpResponse) {
                httpResponse.close();
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return result;
    }


    /**
     * <p>功能描述:doGet</p >
     * <ul>
     * <li>@param [host, path, method, headers, querys]</li>
     * <li>@return org.apache.http.HttpResponse</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2017/9/13 14:35</li>
     * </ul>
     */
    public static String doGet(String url, Map<String, Object> headerMap,
                               Map<String, Object> bodyMap, String encoding, Boolean reqRetry, Boolean isStream) throws Exception {
        if (ToolUtil.isEmpty(reqRetry)) {
            reqRetry = false;
        }
        if (ToolUtil.isEmpty(isStream)) {
            //默认为false，已流的方式读取
            isStream = false;
        }
        String result = null;
        CloseableHttpResponse httpResponse = null;
        try {
            /*是否https*/
            CloseableHttpClient httpClient = wrapClient(url, reqRetry);
            //get请求
            HttpGet httpGet = new HttpGet(bulidhostMethodUrl(url, bodyMap));
            //设置默认报文头
            httpGet.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 /Windows; U; Windows NT 4.1; de; rv:1.9.1.5) Gecko/20091102 Firefox/3.0");
            //设置请求头参数
            if (null != headerMap) {
                for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
                    if (!ToolUtil.isEmpty(entry.getValue())) {
                        httpGet.setHeader(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
            httpResponse = httpClient.execute(httpGet);
            //获取结果
            result = getResult(httpResponse, encoding, isStream);
        } catch (Exception e) {
            LOGGER.error(CommonConstant.ERROR_MESSAGE, e);
        } finally {
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    LOGGER.error(CommonConstant.ERROR_MESSAGE, e);
                }
            }
        }
        return result;
    }


    /**
     * 绕过证书 用于https请求
     */
    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            LOGGER.error(CommonConstant.ERROR_MESSAGE, ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(CommonConstant.ERROR_MESSAGE, ex);
        }
    }


    /**
     * <p>功能描述: GET 请求组装参数</p >
     * <ul>
     * <li> @param hostMehtodUrl </li>
     * <li> @param querys  </li>
     * <li>@return java.lang.String</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/4 11:38</li>
     * </ul>
     */
    private static String bulidhostMethodUrl(String hostMehtodUrl, Map<String, Object> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(hostMehtodUrl);
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, Object> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue().toString())) {
                    sbQuery.append(query.getValue().toString());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue().toString())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue().toString(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }


    /**
     * <p>功能描述:表单方式组装请求参数</p >
     * <ul>
     * <li> @param bodyMap  请求参数</li>
     * <li>@return java.util.List<org.apache.http.NameValuePair></li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/4 11:33</li>
     * </ul>
     */
    private static List<NameValuePair> covertParams2NVPS(Map<String, Object> bodyMap) throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePairList = Lists.newArrayList();
        //设置请求体内容
        if (bodyMap != null) {
            for (String key : bodyMap.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, ToolUtil.isEmpty(bodyMap.get(key)) ? "" : bodyMap.get(key).toString()));
            }
        }
        return nameValuePairList;
    }


    /**
     * <p>功能描述: 请求结果处理</p >
     * <ul>
     * <li> @param httpResponse </li>
     * <li> @param encoding 编码</li>
     * <li> @param isStream 是否流的方式获取 </li>
     * <li>@return java.lang.String</li>
     * <li>@throws </li>
     * <li>@author xuby</li>
     * <li>@date 2019/3/4 12:01</li>
     * </ul>
     */
    private static String getResult(CloseableHttpResponse httpResponse, String encoding, Boolean isStream) throws Exception {
        String result = "";
        StringBuilder sb = null;
        if (!ToolUtil.isEmpty(httpResponse)) {
            int status = httpResponse.getStatusLine().getStatusCode();
            LOGGER.info("返回状态码" + status);
            if (status == HttpStatus.SC_OK) {
                sb = new StringBuilder("");
                //获取结果实体
                HttpEntity httpEntity = httpResponse.getEntity();
                if (isStream) {
                    if (!ToolUtil.isEmpty(httpEntity)) {
                        //流的方式
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encoding));
                        String len = "";
                        while ((len = br.readLine()) != null) {
                            sb.append(len);
                        }
                    }
                } else {
                    if (!ToolUtil.isEmpty(httpEntity)) {
                        //按指定编码转换结果实体为String类型
                        sb.append(EntityUtils.toString(httpEntity, encoding));
                    }
                }
                result = sb.toString();
            }
        }
        return result;
    }

    /**
     *<p>功能描述:getURLEncoderString url 编码</p>
     *<ul>
     *<li>@param str </li>
     *<li>@return java.lang.String</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2019/4/25 16:09</li>
     *</ul>
    */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *<p>功能描述:URLDecoderString url解码</p>
     *<ul>
     *<li>@param str </li>
     *<li>@return java.lang.String</li>
     *<li>@throws </li>
     *<li>@author xuby</li>
     *<li>@date 2019/4/25 16:09</li>
     *</ul>
    */
    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
