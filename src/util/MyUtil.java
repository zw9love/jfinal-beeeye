package util;
/**
 * Created by admin on 2018/2/2.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyUtil {
    public static JSONObject getJson(String message, int status, Object data) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("data", data);
            jsonObj.put("status", status);
            jsonObj.put("msg", message);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObj;
    }

    public static JSONObject getPageJson(JSONArray list, int pageNumber, int pageSize, int totalPage, int totalRow) {
        JSONObject resObj = new JSONObject();
        try {
            resObj.put("list", list);
            resObj.put("pageNumber", pageNumber);
            resObj.put("pageSize", pageSize);
            resObj.put("totalPage", totalPage);
            resObj.put("totalRow", totalRow);
            resObj.put("firstPage", pageNumber == 1 ? true : false);
            boolean lastPage;
            if (totalPage == 0)
                lastPage = true;
            else
                lastPage = pageNumber == totalPage;
            resObj.put("lastPage", lastPage);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return resObj;

    }

    public static JSONObject getSizeJson(JSONArray list, JSONObject size, int total) {
        JSONObject resObj = new JSONObject();
        try {
            resObj.put("list", list);
            resObj.put("total", total);
            resObj.put("size", size);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return resObj;
    }


    public static String getRandomString() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24);
        return uuid;
    }

    public static Map<String, Object> getJsonData(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        Map<String, Object> list = new Gson().fromJson(str, new TypeToken<Map<String, Object>>() {
        }.getType());
        return list;
    }

    public static List<String> getListData(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        List<String> list = new Gson().fromJson(str, new TypeToken<List<Object>>() {
        }.getType());
        return list;
    }

    public static List<Map<String, Object>> getListMapData(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        List<Map<String, Object>> list = new Gson().fromJson(str, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        return list;
    }

    public static int getTime() {
        return Math.round(new Date().getTime() / 1000) + 30 * 60;
    }
}
