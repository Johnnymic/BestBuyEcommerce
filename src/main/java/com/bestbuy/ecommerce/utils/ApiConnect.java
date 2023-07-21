package com.bestbuy.ecommerce.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter

public class ApiConnect {
    private String url;

    private  String API_KEY = "sk_test_1a4eeee1f0dec91f20e281a867dfe66013a3dde9";

    public ApiConnect(String url)  {
        this.url = url;
        this.enforceTlSvPoint();
    }

    private void enforceTlSvPoint()  {
   try {
       SSLContext context = SSLContexts.custom().build();
       SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context, new String[]{"TLSv1"},
               null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
       CloseableHttpClient client = HttpClients.custom()
               .setSSLSocketFactory(socketFactory)
               .build();
       Unirest.setHttpClient(client);
   }catch ( NoSuchAlgorithmException | KeyManagementException exception){
        exception.printStackTrace();
   }
    }

    public   JSONObject connectAndQuery(ApiQuery query){
        HttpResponse<JsonNode> queryForResponse = null;
        try {
            queryForResponse = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .fields(query.getObjectHashMap())
                    .asJson();
        }catch ( UnirestException e){
               e.printStackTrace();
        }
        assert  queryForResponse != null;
        return queryForResponse.getBody().getObject();

    }
}

