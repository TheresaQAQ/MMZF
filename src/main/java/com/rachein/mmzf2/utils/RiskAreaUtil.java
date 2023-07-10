package com.rachein.mmzf2.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Remark:
 * @Author: lsw
 * @Date : 2022/3/1 16:22
 */
public class RiskAreaUtil {

    public static String url = "http://103.66.32.242:8005/zwfwMovePortal/interface/interfaceJson";
    public static String a = "23y0ufFl5YxIyGrI8hWRUZmKkvtSjLQA";
    public static String i = "123456789abcdefg";
    public static String signature = "fTN2pfuisxTavbTuYVSsNJHetwq5bJvCQkjjtiLM2dCratiA";

    public static String x_wif_nonce = "QkjjtiLM2dCratiA";
    public static String x_wif_paasid = "smt-application";

    public static String appId = "NcApplication";
    public static String paasHeader = "zdww";
    public static String nonceHeader = "123456789abcdefg";
    public static String key = "3C502C97ABDA40D0A60FBEE50FAAD1DA";

    public static String get_risk(){
        long timestamp = get_timestamp();
        Map<String,String> headers = new HashMap<>();
        headers.put("x-wif-nonce", x_wif_nonce);
        headers.put("x-wif-signature", get_x_wif_signature(timestamp));
        headers.put("x-wif-timestamp", timestamp+"");
        headers.put("x-wif-paasid", x_wif_paasid);
        headers.put("Accept", "*/*");

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("appId",appId);
        data.put("paasHeader",paasHeader);
        data.put("timestampHeader",timestamp);
        data.put("nonceHeader",nonceHeader);
        data.put("signatureHeader",crypo_sha256(timestamp));
        data.put("key",key);
//        String res = HttpRequestUtils.post(url, headers, JSONUtil.toJsonStr(data));
        String result = HttpUtil.createPost(url)
                .headerMap(headers,false)
                .body(JSONUtil.toJsonStr(data)).execute().body();
        return result;
    }

    public static long get_timestamp(){
        long timestamp = System.currentTimeMillis()/1000;
        return timestamp;
    }
    public static String crypo_sha256(long timestamp){
        String str = timestamp + a + i + timestamp;
        String r = SHA256Util.getSHA256String(str).toUpperCase();
        return r;
    }
    public static String get_x_wif_signature(long timestamp) {
        String str = (timestamp + signature +timestamp);
        String r = SHA256Util.getSHA256String(str).toUpperCase();
        return r;
    }

    public static void main(String[] args) {
        String res = get_risk();
        System.out.println(res);

    }

}
