package com.ngopidev.project.androidlatihan6_networking.controlData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * created by Irfan Assidiq on 2019-05-28
 * email : assidiq.irfan@gmail.com
 **/
public class HttpHandling {
    public HttpHandling(){
        //do nothing
    }

    public String PostDataReq(String reqURL){
        String response = null;
        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            response = convertStreamtoString(in);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    //untuk request GET
    public String GetDataReq(String reqURL){
        String response = null;
        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            response = convertStreamtoString(in);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    //membuat method convertStreamtoString
    private String convertStreamtoString(InputStream in){
        BufferedReader reader = new BufferedReader((new InputStreamReader(in)));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    return sb.toString();
    }
}
