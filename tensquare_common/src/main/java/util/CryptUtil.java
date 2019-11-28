package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 加密工具类
 */
public class CryptUtil {

    //使用base64进行加密
    public static String base64Encode(String password){
        byte[] crypt=Base64.getUrlEncoder().encode(password.getBytes());
        return new String(crypt);
    }

    //使用base64进行解密
    public static String base64Decode(String crypt){
        byte[] password=Base64.getUrlDecoder().decode(crypt.getBytes());
        return new String(password);
    }

    //md5加密
    public static String md5(String password) throws NoSuchAlgorithmException {
        MessageDigest digest=MessageDigest.getInstance("MD5");
        byte[] crypt=digest.digest(password.getBytes());
        return  new String(crypt);
    }



    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password="123456";
        //String crypt=base64Encode(password);
        String crypt=md5(password);
        String crypt2=md5(password);
        System.out.println("加密后的密码："+crypt+",=>"+(crypt.equalsIgnoreCase(crypt2)));
        //System.out.println("解密后的密码："+base64Decode(crypt));
    }
}
