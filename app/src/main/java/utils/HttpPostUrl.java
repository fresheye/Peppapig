package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Lenovo on 2017/3/8.
 */

public class HttpPostUrl {
    public static String sendPost(String url, Map<String, String> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            String param = "";
            if (paramMap != null && paramMap.size() > 0) {
                Iterator<String> ite = paramMap.keySet().iterator();
                while (ite.hasNext()) {
                    String key = ite.next();// key
                    String value = paramMap.get(key);
                    param += key + "=" + value + "&";
                }
                param = param.substring(0, param.length() - 1);
            }
            System.out.println(param);
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.err.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }
    public static void main(String[] args) {
        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("username", "tangmaoxing");
        mapParam.put("encryptedPassword","111111");
        String pathUrl = "http://211.65.99.53:8080/user_login.action";
        String result = sendPost(pathUrl, mapParam);
        System.out.println(result);
    }

}

