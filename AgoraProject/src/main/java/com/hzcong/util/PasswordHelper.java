package com.hzcong.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * MD5加密
 */
public class PasswordHelper{
    private static int numHashIterations = 2;
    private static String saltOfSalt = "a494dba5cbf84725b64a3cb1f0dd11b3";

    public static String getSaltString(String username){
        StringBuilder sb = new StringBuilder();
        char[] usernameChars = username.toCharArray();
        for( int i = 0; i<usernameChars.length; i++){
            sb.append(usernameChars[i]);
            sb.append(usernameChars[usernameChars.length-1-i]);
        }
        return  new Md5Hash(sb.toString(), saltOfSalt,numHashIterations).toString();
    }


    public static String encryptPassword(String username, String password){
        String salt = getSaltString(username);
        return new Md5Hash(password,salt,numHashIterations).toString();
    }
}