package top.api.common;

import com.alibaba.fastjson.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AES {

    public static String encrypt(String content, String key) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(generateKey(key).getEncoded(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("UTF-8"));

        if (b == null || b.length == 0) {
            return "";
        }

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {

            String hex = Integer.toHexString(b[i] & 0xFF);

            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            buf.append(hex.toUpperCase());
        }
        return buf.toString();
    }

    //解密
    public static String decrypt(String content, String key) throws Exception {

        if (content == null || content.length() == 0) {
            return null;
        }

        byte[] b = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            b[i] = (byte) (high * 16 + low);
        }

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(generateKey(key).getEncoded(), "AES"));

        return new String(cipher.doFinal(b));
    }

    private static SecretKey generateKey(String key) throws NoSuchAlgorithmException {

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        KeyGenerator kg = KeyGenerator.getInstance("AES");

        secureRandom.setSeed(key.getBytes());
        kg.init(128, secureRandom);

        return kg.generateKey();
    }

    public static void main(String[] args) {

        try {
            JSONObject object = new JSONObject();
            object.put("name","李四");
            String str = object.toJSONString();
            String secretKey = "秘钥";

            System.out.println("秘钥:" + secretKey);
            System.out.println("明文:" + str);

            String str0 = encrypt(str, secretKey);
            System.out.println("密文:" + str0);
            System.out.println("解密:" + decrypt(str0, secretKey));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}