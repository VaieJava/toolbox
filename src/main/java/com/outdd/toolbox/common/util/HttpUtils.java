package com.outdd.toolbox.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Http工具，包含：
 * 普通http请求工具(使用httpClient进行http,https请求的发送)
 * </pre>
 * Created by xuyh at 2017/7/17 19:08.
 */
public class HttpUtils {
    /**
     * 请求超时时间,默认20000ms
     */
    private int timeout = 20000;
    /**
     * cookie表
     */
    private Map<String, String> cookieMap = new HashMap<>();

    /**
     * 请求编码(处理返回结果)，默认UTF-8
     */
    private String charset = "UTF-8";

    private static HttpUtils httpUtils;

    private HttpUtils() {
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (httpUtils == null)
            httpUtils = new HttpUtils();
        return httpUtils;
    }

    /**
     * 清空cookieMap
     */
    public void invalidCookieMap() {
        cookieMap.clear();
    }

    public int getTimeout() {
        return timeout;
    }

    /**
     * 设置请求超时时间
     *
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getCharset() {
        return charset;
    }

    /**
     * 设置请求字符编码集
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 将网页返回为解析后的文档格式
     *
     * @param html
     * @return
     * @throws Exception
     */
    public static Document parseHtmlToDoc(String html) throws Exception {
        return removeHtmlSpace(html);
    }

    private static Document removeHtmlSpace(String str) {
        Document doc = Jsoup.parse(str);
        String result = doc.html().replace("&nbsp;", "");
        return Jsoup.parse(result);
    }

    /**
     * 执行get请求,返回doc
     *
     * @param url
     * @return
     * @throws Exception
     */
    public Document executeGetAsDocument(String url) throws Exception {
        return parseHtmlToDoc(executeGet(url));
    }

    /**
     * 执行get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String executeGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpClient httpClient = null;
        String str = "";
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
            int state = response.getStatusLine().getStatusCode();
            if (state == 404) {
                str = "";
            }
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str = EntityUtils.toString(entity, charset);
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return str;
    }

    /**
     * 用https执行get请求,返回doc
     *
     * @param url
     * @return
     * @throws Exception
     */
    public Document executeGetWithSSLAsDocument(String url) throws Exception {
        return parseHtmlToDoc(executeGetWithSSL(url));
    }

    public static String httpGetHeader(String url,String cook,String header) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //请求头设置，特别是cookie设置
        con.header("Accept", "text/html, application/xhtml+xml, */*");
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");
        con.header("Cookie", cook);
        //发送请求
        Connection.Response resp=con.method(Connection.Method.GET).execute();
        //获取cookie名称为__bsi的值
        String cookieValue = resp.cookie("__bsi");
        System.out.println("cookie  __bsi值：  "+cookieValue);
        //获取返回cookie所值
        Map<String,String> cookies = resp.cookies();
        System.out.println("所有cookie值：  "+cookies);
        //获取返回头文件值
        String headerValue = resp.header(header);
        System.out.println("头文件"+header+"的值："+headerValue);
        //获取所有头文件值
        Map<String,String> headersOne =resp.headers();
        System.out.println("所有头文件值："+headersOne);
        return headerValue;

    }

    /**
     * 用https执行get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String executeGetWithSSL(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", convertCookieMapToString(cookieMap));
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpClient httpClient = null;
        String str = "";
        try {
            httpClient = createSSLInsecureClient();
            HttpClientContext context = HttpClientContext.create();
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
            int state = response.getStatusLine().getStatusCode();
            if (state == 404) {
                str = "";
            }
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str = EntityUtils.toString(entity, charset);
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw e;
        } catch (GeneralSecurityException ex) {
            throw ex;
        } finally {
            try {
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return str;
    }

    /**
     * 执行post请求,返回doc
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public Document executePostAsDocument(String url, Map<String, String> params) throws Exception {
        return parseHtmlToDoc(executePost(url, params));
    }

    /**
     * 执行post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String executePost(String url, Map<String, String> params) throws Exception {
        String reStr = "";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        List<NameValuePair> paramsRe = new ArrayList<>();
        for (String key : params.keySet()) {
            paramsRe.add(new BasicNameValuePair(key, params.get(key)));
        }
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse response;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramsRe));
            HttpClientContext context = HttpClientContext.create();
            response = httpclient.execute(httpPost, context);
            getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
            HttpEntity entity = response.getEntity();
            reStr = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            throw e;
        } finally {
            httpPost.releaseConnection();
        }
        return reStr;
    }

    /**
     * 用https执行post请求,返回doc
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public Document executePostWithSSLAsDocument(String url, Map<String, String> params) throws Exception {
        return parseHtmlToDoc(executePostWithSSL(url, params));
    }

    /**
     * 用https执行post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String executePostWithSSL(String url, Map<String, String> params) throws Exception {
        String re = "";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> paramsRe = new ArrayList<>();
        for (String key : params.keySet()) {
            paramsRe.add(new BasicNameValuePair(key, params.get(key)));
        }
        post.setHeader("Cookie", convertCookieMapToString(cookieMap));
        post.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpResponse response;
        try {
            CloseableHttpClient httpClientRe = createSSLInsecureClient();
            HttpClientContext contextRe = HttpClientContext.create();
            post.setEntity(new UrlEncodedFormEntity(paramsRe));
            response = httpClientRe.execute(post, contextRe);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                re = EntityUtils.toString(entity, charset);
            }
            getCookiesFromCookieStore(contextRe.getCookieStore(), cookieMap);
        } catch (Exception e) {
            throw e;
        }
        return re;
    }

    /**
     * 发送JSON格式body的POST请求
     *
     * @param url 地址
     * @param jsonBody json body
     * @return
     * @throws Exception
     */
    public String executePostWithJson(String url, String jsonBody) throws Exception {
        String reStr = "";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        httpPost.setHeader("Cookie", convertCookieMapToString(cookieMap));
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse response;
        try {
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            HttpClientContext context = HttpClientContext.create();
            response = httpclient.execute(httpPost, context);
            getCookiesFromCookieStore(context.getCookieStore(), cookieMap);
            HttpEntity entity = response.getEntity();
            reStr = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            throw e;
        } finally {
            httpPost.releaseConnection();
        }
        return reStr;
    }

    /**
     * 发送JSON格式body的SSL POST请求
     *
     * @param url 地址
     * @param jsonBody json body
     * @return
     * @throws Exception
     */
    public String executePostWithJsonAndSSL(String url, String jsonBody) throws Exception {
        String re = "";
        HttpPost post = new HttpPost(url);
        post.setHeader("Cookie", convertCookieMapToString(cookieMap));
        post.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build());
        CloseableHttpResponse response;
        try {
            CloseableHttpClient httpClientRe = createSSLInsecureClient();
            HttpClientContext contextRe = HttpClientContext.create();
            post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
            response = httpClientRe.execute(post, contextRe);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                re = EntityUtils.toString(entity, charset);
            }
            getCookiesFromCookieStore(contextRe.getCookieStore(), cookieMap);
        } catch (Exception e) {
            throw e;
        }
        return re;
    }

    private void getCookiesFromCookieStore(CookieStore cookieStore, Map<String, String> cookieMap) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
    }

    private String convertCookieMapToString(Map<String, String> map) {
        String cookie = "";
        for (String key : map.keySet()) {
            cookie += (key + "=" + map.get(key) + "; ");
        }
        if (map.size() > 0) {
            cookie = cookie.substring(0, cookie.length() - 2);
        }
        return cookie;
    }

    /**
     * 创建 SSL连接
     *
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    (s, sslContextL) -> true);
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (GeneralSecurityException e) {
            throw e;
        }
    }
}
