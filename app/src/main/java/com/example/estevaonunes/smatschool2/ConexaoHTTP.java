package com.example.estevaonunes.smatschool2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by estevaonunes on 06/10/14.
 */
public class ConexaoHTTP {

    public static final int DID_START = 0;
    public static final int DID_ERROR = 1;
    public static final int DID_SUCCEED = 2;

    private static final int GET = 0;
    private static final int POST = 1;
    private static final int PUT = 2;
    private static final int DELETE = 3;

    public ConexaoHTTP() {
    }

    public String get(String url, String token) throws IllegalStateException, IOException {
        return executeHTTPConnection(GET, url, null, token);
    }

    public String post(String url, String data, String token) throws IllegalStateException, IOException {
        return executeHTTPConnection(POST, url, data, token);
    }

    public String put(String url, String data, String token) throws IllegalStateException, IOException {
        return executeHTTPConnection(PUT, url, data, token);
    }

    public String delete(String url, String token) throws IllegalStateException, IOException {
        return executeHTTPConnection(DELETE, url, null, token);
    }

    public Bitmap bitmap(String url) throws IllegalStateException, IOException {
        return executeHTTPConnectionBitmap(url);
    }


    private String executeHTTPConnection(int method, String url, String data, String token) throws IllegalStateException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 25000);
        HttpResponse response = null;

        switch (method) {
            case GET:
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Token", token);
                response = httpClient.execute(httpGet);
                break;

            case POST:
                HttpPost httpPost = new HttpPost(url);
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("Dados", data));
                httpPost.setHeader("Token", token);
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
                response = httpClient.execute(httpPost);
                break;

            case PUT:
                HttpPut httpPut = new HttpPut(url);
                List<NameValuePair> pares = new ArrayList<NameValuePair>();
                pares.add(new BasicNameValuePair("Dados", data));
                httpPut.setHeader("Token", token);
                httpPut.setEntity(new UrlEncodedFormEntity(pares));
                response = httpClient.execute(httpPut);
                break;

            case DELETE:
                HttpDelete httpDelete = new HttpDelete(url);
                httpDelete.setHeader("Token", token);
                response = httpClient.execute(httpDelete);
                break;

            default:
                throw new IllegalArgumentException("Unknown Request.");

        }

        return processResponse(response.getEntity());

    }

    private Bitmap executeHTTPConnectionBitmap(String url) throws IllegalStateException, IOException {

        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 25000);
        HttpResponse response = httpClient.execute(new HttpGet(url));
        return processBitmapEntity(response.getEntity());

    }

    private String processResponse(HttpEntity entity) throws IllegalStateException, IOException {

        String jsonText = EntityUtils.toString(entity, HTTP.UTF_8);
        return jsonText;

    }

    private Bitmap processBitmapEntity(HttpEntity entity) throws IOException {

        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        Bitmap bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
        return bm;

    }

}
