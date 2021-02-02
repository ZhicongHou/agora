package com.hzcong.config;


import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2019010962817694";
//
     // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCgSx3D9+g+cwQ6Rd10Zn4BvJ6Qj40v8CsWIQNXu1LfgKrimrj2UgyOR0Gosxi4Fi6rqM5WCnwsaLfG8ydadw9MPVdkzfst6m/PS8ya6BrDu3D6a/G4BOErpcfAPJnK7mY5eHR05rL4tg3eIdoPiHowzADp5JmME5IhCgZQoO7vTpFuMbHCoyehhuOakqBkodJJs+1oJ5I3C+toXd8LuMmHTOvXMJDC9rNoYiXpZnW9CXzbB7EgsFRikuf/0PLe5V2tgZXROLnpcoRLyAZ4lhdACoe8rkGIGglxaI2UrR9pdHqDUOQR9MVKJD0YUvcjVmVQf+r6GvteZQfCI/oSl0LfAgMBAAECggEBAIcy9FcvSV8rCdzQwtoD/LIgUqnhupc9fkn67GPaJytGzlu1Xt68E8UJyxKshOP4KcM17VtbGqMmbCPNQVsnYurOLmFi70IqI8Z3y4q4zKJc/gzqcQXib72t5Y56TsmATQ6aT7euQjRO8eFzMga5+T4hQ4AEBZLFixJ87xigtQd4KV8tmlI6gMKd/tIbYNcLa8Wy+emZrJBcN7pOpQnEeF9AWnVFL6eZl815u+tm0bPLokx3Aaz5Ngym0T4uKQiBJR/7o8F4bm1YLoadP7ZXyKx3BGLhRv3NW2hZEO2jBPoz9ForPYURjWSv+6mKpILNW91XIEL/Y0gV/fL0m/kfX7kCgYEA3R/Zdtgyz731ebSEnHcdUXvsMqk/Xko4CjOBuPtJhZgZbr/5DMnhSCzLnLZMv4eUrsPNP5Cyt0RoRtyJFNjrM8koaCEUlxaUvV0aoZD6H2Ter5Db+tiUsSsSsOlK53tmqlWN0v/nV9FoEANc1uLeNOVeYrd2BuA3sIEP1XZ6JJ0CgYEAuZMlbF+U7hVgwFvCzMgqY8LJblGTOUrjhgipmOl0tzYdm4Kikjg4TwKBeKgAv73c2YBpQo0QdBTLJeaqWpTi97K+k1yCe9Y4nBzuLJYaIu9CIDvDfA4yABowO2cIHq8gD/DaouuNgtuEa1GEkqVDlHtmh7Crfjfz4GFZ4ClfpqsCgYEAs6Fm8IOLK3XeT2MVA2JEP+iSpUkWQEu8oJTdAd+pB0zbBDwRXpE1YIN7WLBDQqgqYZqQRt4OQJDiwQXwmi3pS+VS3NxatK5UYEydELkMZ6pyuF7ORUXh7NbX1T1/avK1IRRlJQ5b6dwkVDXXGn2sE35tIXZ1+/nzY4BE05zudoUCgYEAmj71Ryv3NSFtxtU+iEoD+eGo3dpOHbvie8DtftzlF3eSinS9vf2eyAVUKj6ySK1+SQbT9u4bc/DonfspM5s3QB1BDGlDR71aVmAxk7u7w7fM5c/m/1EpPC6IUoekFyUG6FkQ0YQqnk7ptO4P3HgjQOKE6VnuYT4Opj0koA6jo3sCgYApFUAgOIJZ5QUko7EK36LYSf1WWvkuSG4ETOYAjNfYMXi3nG11Jdu77hzmoeXvB/g6J8ZLq8PpvkepCfwb5I4LlLaRJpaPWuiqyrP5jEZJMPEI2dJ50CnC1BllRdUBryxYgpNG6KTyUrf2EbBKLUYIu+SArEI/VKlPrt0alGlUaw==";

        // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmX9Lo0I8STMng3CSUrgQWKUgU3aBhsZDWmwM5TtdwYdfU1QjrOGeQxY/kMSUWtTRnYR9hSKgstoj+xUpahFSc59IdIEmRshlmYnN1Ss2kL7DuGRrnYz4CO8FD3ThjaqUgBNWjATXYH7/IamaHSM0110+YjwuPd4l0Mnmol4j4mYZSW70Cz4mt+2AxskRfhyE/7VhCSbXQ3SzAED0ov3No1OdKxJj5GrRxcil228DZIF0I+nE4lox2LF7NscIN6U5v/zh60NSg+UOKmOgIh04xWzOtuSjX8uGKmyYOzHunmJp8NARO2pCeW3WX8pw9M8ZqpNOx5VZfNxCdBskqdLhXwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "https://noi.uutime.cn/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = "https://noi.uutime.cn/return_url";
    public static String return_url = "https://noi.uutime.cn:443/return_url";
//    public static String return_url = "http://localhost:8080/return_url";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

