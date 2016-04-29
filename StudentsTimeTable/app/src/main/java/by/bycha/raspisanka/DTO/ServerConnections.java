package by.bycha.raspisanka.DTO;


import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ServerConnections {
    static public void logoutUser(String id) {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("id", id);
        postDataParams.put("type", "logout");
        try {
            HttpURLConnection conn = connect(getPostDataString(postDataParams));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public String loginUser(String user) {
        String response = "";
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("type", "login");
        postDataParams.put("uname", user);
        HttpURLConnection conn = null;
        try {
            conn = connect(getPostDataString(postDataParams));
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String TMP;
            while ((TMP = reader.readLine()) != null) {
                response += TMP;
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    static public String getData(String userId) {
            String response = "";
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("type", "get");
            postDataParams.put("id", userId);
            try {
                HttpURLConnection conn = connect(getPostDataString(postDataParams));
                if (conn != null) {
                    Integer responseCode = conn.getResponseCode();
                    if (responseCode != 200) return null;
                    else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String TMP;
                        while ((TMP = reader.readLine()) != null) {
                            response += TMP;
                        }
                        return response;
                    }
                } else
                    return null;
            } catch (IOException e) {
                return null;
            }
    }
    static public void saveData(OutputStream file , String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(file));
        bw.write(data);
        bw.close();
    }
    static private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    static private HttpURLConnection connect(String postDataParams)  {
        HttpURLConnection conn = null;
        try {

            URL url = new URL("http://ontime.ml/sv/");

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams);

            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            return conn;
        }
        return conn;
    }
}