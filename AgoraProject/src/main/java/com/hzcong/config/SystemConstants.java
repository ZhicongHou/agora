package com.hzcong.config;

import com.hzcong.config.sdk.WXPayConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SystemConstants {


    public static final String Agora_AppId = "074779f606ad40a6b8fe7ae8bb47b395";

    /**
     * 系统用户admin识别常量，用于判别用户是否为admin
     */
    public static final String ADMIN_SIGN = "c10a03dfe3224d719d6d3e64cb51de16";

    /**
     * 教师角色teacher判别，用于判断用户是否为teacher
     */
    public static final String TEACHER_SIGN = "921f26af94904a3a8509de04a733508b";

    /**
     * 学生角色student判别，用于判断用户是否为student
     */
    public static final String STUDENT_SIGN = "5675ac07886642a0aa7fa3b7a79d769f";

    /**
     * 微信支付类型的判别码
     */
    public static final String WXPAY = "c9349741b71e4f798722745ce8bbe47b";

    /**
     * 支付宝字符类型的判别码
     */
    public static final String ALIPAY = "839bdbd940354a48adfa242736be5356";

    /**
     * 签名类型常量
     */
    public static final WXPayConstants.SignType SIGN_TYPE = WXPayConstants.SignType.HMACSHA256;


    /**
     * 用于上课时间段星期和时间的排序map
     */
    public static Map<Character, Integer> map = new ConcurrentHashMap<Character, Integer>();
    static {
        map.put('一', 1);
        map.put('二', 2);
        map.put('三', 3);
        map.put('四', 4);
        map.put('五', 5);
        map.put('六', 6);
        map.put('日', 7);
    }

    /**
     * 每页显示的数量
     */
    public static final int PAGESIZEOFMESSAGE = 10;


    /**
     *
     */
    public static final String DEFAULT_EMAIL = "scau_qzy@163.com";
}
