package com.krt.rent.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class GroupMAS {

    private static String MAS_ID = "168";
    private static String PASSWORD = "zC3o115Rt3JzpoBNB9mdn8DCqgyLl84Bee0cqNJwLPU8Zbeya8bexQ==";
    private static String SERVLETURL = "http://218.204.142.67:18080/sjb/HttpSendSMSService";


    public static String buildRequestXMLString(String id, String pwd, String serviceid, String phone, String content) {
        StringBuffer sb = new StringBuffer();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<svc_init ver=\"2.0.0\">")
                .append("<sms ver=\"2.0.0\">")
                .append("<client>")
                .append("<id>").append(id).append("</id>")
                .append("<pwd>").append(pwd).append("</pwd>")
                .append("<serviceid>").append(serviceid).append("</serviceid>")
                .append("</client>")
                .append("<sms_info>")
                .append("<phone>").append(phone).append("</phone>")
                .append("<content>").append(content).append("</content>")
                .append("</sms_info>")
                .append("</sms>")
                .append(" </svc_init>");

//        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String postXMLSendSMSRequest(String servletUrl, String content) {
        String result = null;

        BufferedReader br = null;
        OutputStreamWriter out = null;
        HttpURLConnection con = null;

        try {
            URL url = new URL(servletUrl);

            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

            out.write(content);

            out.flush();

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            String line = null;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            result = sb.toString();

        } catch (IOException e) {
           log.error("",e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {

                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {

                }
            }

            if (con != null) {
                con.disconnect();
                con = null;
            }
        }

        return result;
    }

    public static String sendMsg(String phone, String content) {
        //下面的MAS_ID、PASSWORD仅供测试使用，正式使用由移动公司分配
        String reqXML = buildRequestXMLString(MAS_ID, PASSWORD, "", phone, content);
        String str = postXMLSendSMSRequest(SERVLETURL, reqXML);
        return str;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //下面的MAS_ID、PASSWORD仅供测试使用，正式使用由移动公司分配
        //String MAS_ID = "182";
        //String PASSWORD = "bYcLgYwXOZspDzEnaGsNHAGUykKEiexknHY9H98xVTQ8Zbeya8bexQ==";
        //"http://218.204.110.232:8080/emc/HttpSendSMSService"

        String reqXML = buildRequestXMLString(MAS_ID, PASSWORD, "", "13155991651,17770107976,17370700580", "测试短信");
        String s = postXMLSendSMSRequest(SERVLETURL, reqXML);
        System.out.println(s);
    }

}
