package com.shengdangjia.hermesaccount.utility;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 短信工具类
 */
public class SMSHelper {

    private static final String SMS_Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
    private static final String SMS_Account = "cf_molan2016";
    private static final String SMS_Password = "Molan@2016";


    /**
     * 发送验证码
     * @param mobile 手机号
     * @param verifyCode 验证码
     * @return
     */
    public static boolean sendVerifyCode(String mobile, String verifyCode) {
        String content = String.format("您的验证码是：%s。请不要把验证码泄露给其他人。", verifyCode);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        String body = String.format("&account=%s&password=%s&mobile=%s&content=%s", SMS_Account, SMS_Password, mobile, content);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SMS_Url + body))
                .timeout(Duration.ofSeconds(5))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var resbody = response.body();
            // System.out.println(resbody);

            Pattern p = Pattern.compile("<code>(\\d+)</code>");
            var matcher = p.matcher(resbody);
            if (matcher.find()) {
                var code = matcher.group(1);
                return code.equals("2");
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("io execption" + e.toString());
            return false;
        } catch (InterruptedException e) {
            System.out.println("interrupted execption" + e.toString());
            return false;
        }
    }

    /**
     * 生成手机验证码
     * @return 六位验证码
     */
    public static String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
