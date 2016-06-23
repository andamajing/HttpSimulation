
package com.tools.simulation.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * http辅助类
 * <p>
 */
public class HttpHelper {
    private int timeOut = 5000;

    private HttpClient httClient = null;

    private String errorString = "";

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getErrorString() {
        return errorString;
    }

    public HttpHelper() {
        httClient = new HttpClient();
    }

    public HttpHelper(int timeOut) {
        this();
        this.timeOut = timeOut;
    }

    /**
     * @param url
     * @param webContent
     * @param encoding
     *            编码如：UTF-8
     * @return
     */
    public boolean get(String url, StringBuffer content, String encoding, Map<String, String> headers) {
        HttpMethod method = new GetMethod(url);
        // 设置url连接超时
        httClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
        // 设置读取数据超时
        httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);

        // 这两行解决 too many open files问题 
        httClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        // 解决Httpclient远程请求所造成Socket没有释放 
        method.addRequestHeader("Connection", "close");

        // 添加额外的Header
        if (!MapUtils.isEmpty(headers)) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                method.addRequestHeader(key, headers.get(key));
            }
        }

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

        try {
            httClient.executeMethod(method);
            String respString = method.getResponseBodyAsString();
            content.append(respString);

        } catch (ConnectTimeoutException e) {
            errorString = "Connect Timeout:" + url;
            return false;
        } catch (SocketTimeoutException e) {
            errorString = "Socket Timeout:" + url;
            return false;
        } catch (IOException e) {
            errorString = e.toString();
            return false;
        } finally {
            method.releaseConnection();
        }

        return true;
    }

    /**
     * @param url
     * @param webContent
     * @param encoding
     *            编码如：UTF-8
     * @return
     */
    public boolean get(String url, StringBuffer content, String encoding) {
        HttpMethod method = new GetMethod(url);
        // 设置url连接超时
        httClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
        // 设置读取数据超时
        httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);

        // 这两行解决 too many open files问题 by jdzhan
        httClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        // 解决Httpclient远程请求所造成Socket没有释放 by jdzhan
        method.addRequestHeader("Connection", "close");

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

        try {
            httClient.executeMethod(method);
            String respString = method.getResponseBodyAsString();
            content.append(respString);

        } catch (ConnectTimeoutException e) {
            errorString = "Connect Timeout:" + url;
            return false;
        } catch (SocketTimeoutException e) {
            errorString = "Socket Timeout:" + url;
            return false;
        } catch (IOException e) {
            errorString = e.toString();
            return false;
        } finally {
            method.releaseConnection();
        }

        return true;
    }

    /**
     * @param url
     * @param sendData
     *            ： post的内容
     * @param content
     *            ：返回的内容
     * @param encoding
     *            ：编码方式 ， utf-8
     * @return
     */
    public boolean post(String url, String sendData, StringBuffer content, String encoding) {
        PostMethod method = new PostMethod(url);
        // 设置url连接超时
        httClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
        // 设置读取数据超时
        httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);

        // 这两行解决 too many open files问题
        httClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        // 解决Httpclient远程请求所造成Socket没有释放 
        method.addRequestHeader("Connection", "close");

        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);

        try {
            method.setRequestEntity(new StringRequestEntity(sendData, "text/html", encoding));
            httClient.executeMethod(method);
            String respString = method.getResponseBodyAsString();
            content.append(respString);
        } catch (ConnectTimeoutException e) {
            errorString = "Connect Timeout:" + url;
            return false;
        } catch (SocketTimeoutException e) {
            errorString = "Socket Timeout:" + url;
            return false;
        } catch (IOException e) {
            errorString = e.toString();
            return false;
        } finally {
            method.releaseConnection();
        }

        return true;
    }

}
