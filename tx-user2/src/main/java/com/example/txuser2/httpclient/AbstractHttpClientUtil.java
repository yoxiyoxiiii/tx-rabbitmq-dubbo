package com.example.txuser2.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http clent util
 * @author yj
 */
public abstract class AbstractHttpClientUtil {

    protected  CloseableHttpClient build;

    protected HttpResponse post(String url) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = getHttpPost(url);
        Map<String, String> parameters = getParameters();
        Map<String, String> headerMap = getHeaderMap();

        //添加http 请求头
        headerMap.forEach((key, value) -> httpPost.addHeader(key, value));
        //存在参数
        if (parameters.entrySet().size() != 0) {
            return post(httpClient, httpPost, parameters);
        } else {
            return post(httpClient, httpPost);
        }
    }

    protected HttpResponse get(String url) {
        CloseableHttpClient httpClient = getHttpClient();
        Map<String, String> parameters = getParameters();
        Map<String, String> headerMap = getHeaderMap();

        int size = parameters.entrySet().size();
        //得到含有请求参数得 URI
        if (size >0) {
            URI uri = uriBuilder(url, parameters);

            HttpGet httpGet = getHttpGet(uri);
            //添加http 请求头
            headerMap.forEach((key, value) -> httpGet.addHeader(key, value));
            return  get(httpClient, httpGet);
        } else {
            //无参
            HttpGet httpGet = getHttpGet(url);
            //添加http 请求头
            headerMap.forEach((key, value) -> httpGet.addHeader(key, value));
            return get(httpClient,httpGet);
        }
    }

    private HttpPost getHttpPost(String url) {
       HttpPost httpPost = new HttpPost(url);
       return httpPost;
    }
    private HttpGet getHttpGet(URI uri) {
       HttpGet httpPost = new HttpGet(uri);
       return httpPost;
    }
    private HttpGet getHttpGet(String uri) {
       HttpGet httpPost = new HttpGet(uri);
       return httpPost;
    }

    private CloseableHttpClient getHttpClient() {
        return getCustomHttpClient();
    }


    private URI uriBuilder(String url, Map<String, String> parameters) {
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            Set<Map.Entry<String, String>> entries = parameters.entrySet();
            entries.forEach(kv->{
                String key = kv.getKey();
                String value = kv.getValue();
                uriBuilder.addParameter(key, value);
            });

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 请求参数设置
     * @param parameters
     * @return
     */
    private List<NameValuePair> nameValuePairs(Map<String, String> parameters) {
        int size = 1;
        Set<Map.Entry<String, String>> entries = parameters.entrySet();
        if (entries.size() != 0) {
            size = entries.size();
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>(size);
        entries.forEach(kv->{
            String key = kv.getKey();
            String value = kv.getValue();
            nameValuePairs.add(new BasicNameValuePair(key, value));
        });
        return nameValuePairs;



    }

    /**
     * 关闭 资源
     * @param build
     */
    protected void release(CloseableHttpClient build) {
        if (build!=null) {
            try {
                build.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                build = null;
            }
        }
    }

    private HttpResponse post( CloseableHttpClient httpClient ,HttpPost httpPost,Map<String, String> parameters ) {
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs(parameters),"utf-8"));
            return httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http",1,1),404,"error"));
    }

    private HttpResponse post( CloseableHttpClient httpClient ,HttpPost httpPost) {
        try {
            return httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getCustomHttpResponse();
    }


    private HttpResponse get(CloseableHttpClient httpClient ,HttpGet httpGet) {
        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getCustomHttpResponse();
    }

    private BasicHttpResponse getCustomHttpResponse() {
        return new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http",1,1),404,"error"));
    }

    //设置参数
    protected abstract Map<String, String> getParameters();
    //设置请求头
    protected abstract Map<String, String> getHeaderMap();
    private   CloseableHttpClient getCustomHttpClient(){
        build = org.apache.http.impl.client.HttpClientBuilder.create().build();
        return build;
    }



}
