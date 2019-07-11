package ijinbu.recognition.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    static final String charsetName = "UTF-8";

    public static String doGet(String url) throws MalformedURLException,
            IOException {
        return doGet(url, 10);
    }

    public static String doGet(String url, int seconds)
            throws MalformedURLException, IOException {
        HttpURLConnection urlconn = null;
        urlconn = (HttpURLConnection) new URL(url).openConnection();
        urlconn.getRequestProperties();
        urlconn.setRequestProperty("content-type", "text/html");
        urlconn.setRequestMethod("GET");
        urlconn.setConnectTimeout(10000);
        urlconn.setReadTimeout(seconds * 1000);
        urlconn.setDoInput(true);

        int code = urlconn.getResponseCode();
        return del(urlconn,code);

    }

    public static String doPost(String url, String value)
            throws IOException {
        return doPost(url, value, 10);
    }

    public static String doPost(String url, String value, int seconds) {
        HttpURLConnection urlconn = null;
        int code = 0;
        try {
            urlconn = (HttpURLConnection) new URL(url).openConnection();
            urlconn.getRequestProperties();
            urlconn.setRequestProperty("content-type", "application/json");
            urlconn.setRequestMethod("POST");
            urlconn.setConnectTimeout(100);
            urlconn.setReadTimeout(seconds * 1000);
            urlconn.setDoInput(true);
            urlconn.setDoOutput(true);
            urlconn.getOutputStream().write(value.getBytes(charsetName));
            urlconn.getOutputStream().close();
            code = urlconn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(code);
        }

        return del(urlconn,code);
    }

    private static String del(HttpURLConnection urlconn,int code) {
   if(code==200){
        BufferedReader rd = null;
        String temp = null;
        StringBuffer sb = new StringBuffer();
        try {
            rd = new BufferedReader(new InputStreamReader(
                    urlconn.getInputStream(), charsetName));
            temp = rd.readLine();
            while (temp != null) {
                sb.append(temp);
                temp = rd.readLine();
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

       urlconn.disconnect();
        return sb.toString();
    }
    return null;}
}