package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class MD5Util {

    private final static String HEX_NUMS_STR = "0123456789ABCDEF";
    private final static Integer SALT_LENGTH = 12;

    //将16进制字符串转换成数组
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR
                    .indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    //将数组转换成16进制字符串
    public static String byteToHexString(byte[] salt){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < salt.length; i++) {
            String hex = Integer.toHexString(salt[i] & 0xFF);
            if(hex.length() == 1){
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 密码验证
     * @param passwd 用户输入密码
     * @param dbPasswd 数据库保存的密码
     */
    public static boolean validPasswd(String passwd, String dbPasswd)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] pwIndb =  hexStringToByte(dbPasswd);
        //定义salt
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(pwIndb, 0, salt, 0, SALT_LENGTH);
        //创建消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //将盐数据传入消息摘要对象
        md.update(salt);
        md.update(passwd.getBytes("UTF-8"));
        byte[] digest = md.digest();
        //声明一个对象接收数据库中的口令消息摘要
        byte[] digestIndb = new byte[pwIndb.length - SALT_LENGTH];
        //获得数据库中口令的摘要
        System.arraycopy(pwIndb, SALT_LENGTH, digestIndb, 0,digestIndb.length);
        //比较根据输入口令生成的消息摘要和数据库中的口令摘要是否相同
        System.out.println("校验的密码："+byteToHexString(digest));
        System.out.println("原理的密码："+byteToHexString(digestIndb));
        if(Arrays.equals(digest, digestIndb)){
            //口令匹配相同
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获得md5之后的16进制字符
     * @param passwd 用户输入密码字符
     * @return String md5加密后密码字符
     */
    public static String getEncryptedPwd(String passwd)
            throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //拿到一个随机数组，作为盐
        byte[] pwd = null;
        SecureRandom sc= new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sc.nextBytes(salt);

        //声明摘要对象，并生成
        MessageDigest md = MessageDigest.getInstance("MD5");
        //计算MD5函数
        md.update(salt);
        //passwd.getBytes("UTF-8")将输入密码变成byte数组，即将某个数装换成一个16进制数
        md.update(passwd.getBytes("UTF-8"));
        //计算后获得字节数组,这就是那128位了即16个元素
        byte[] digest = md.digest();
        pwd = new byte[salt.length + digest.length];
        System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
        System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        return byteToHexString(pwd);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password="123456";
        String crypt=MD5Util.getEncryptedPwd(password);
        System.out.println("加密后的密码："+crypt);
        crypt=MD5Util.getEncryptedPwd(password);
        System.out.println("加密后的密码："+crypt);

        boolean result=MD5Util.validPasswd(password,crypt);
        System.out.println(result);

    }
}
