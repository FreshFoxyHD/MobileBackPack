package me.maxi.mobilebackpack.Manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    public static String getLastVersion() throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://www.mythiccloud.net/s/nWn7K2BGWLyp7j2/download/info.json");
        //System.out.println(json.toString());
        //System.out.println(json.getLong("addtime"));
        return json.getString("version");
    }
}