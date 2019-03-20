package com.example.txuser2.httpclient;

import org.apache.http.HttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yj
 */
public class HttpClientUtil extends  AbstractHttpClientUtil {

    private Map<String, String> parameterMap;
    private Map<String, String> headerMap;


    /**
     * 参数返回
     * @return
     */
    @Override
    protected Map<String, String> getParameters() {
        if (StringUtils.isEmpty(parameterMap)) {
            return new ConcurrentHashMap<>();
        }
        return parameterMap;
    }

    @Override
    protected Map<String, String> getHeaderMap() {
       if (StringUtils.isEmpty(headerMap)) {
           return new ConcurrentHashMap<>();
       }
        return headerMap;
    }

    public HttpResponse httpost(String url , Map<String, String> parameterMap) {
//        this.parameterMap = parameterMap;
//        return super.post(url);
        return this.httpost(url, parameterMap, null);
    }


    public HttpResponse httpget(String url , Map<String, String> parameterMap) {
//        this.parameterMap = parameterMap;
//        return super.get(url);
        return this.httpget(url, parameterMap, null);
    }

    public HttpResponse httpget(String url , Map<String, String> parameterMap,  Map<String, String> headerMap) {
        this.parameterMap = parameterMap;
        this.headerMap = headerMap;
        return super.get(url);
    }

    public HttpResponse httpost(String url , Map<String, String> parameterMap, Map<String, String> headerMap) {
        this.parameterMap = parameterMap;
        this.headerMap = headerMap;
        return super.post(url);
    }


    /**
     * 获取 结果
     * @param httpResponse
     * @return
     */
    public String getResult(HttpResponse httpResponse){
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            try {
                InputStream content = httpResponse.getEntity().getContent();
                return org.apache.commons.io.IOUtils.toString(content,"utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                release(super.build);
            }
        }
        return statusCode+"";
    }


    static class HttpClientBuilderImpl implements HttpClientBuilder{

        @Override
        public HttpClientUtil builder() {
            return new HttpClientUtil();
        }
    }

    public static HttpClientBuilder httpClientBuilder() {
        return new HttpClientBuilderImpl();
    }
}
