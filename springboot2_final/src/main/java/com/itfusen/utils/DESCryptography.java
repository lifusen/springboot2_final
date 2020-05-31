package com.itfusen.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESCryptography {

    public static final String key = "cjhcy123";
    public static void main(String[] args) {

        String content="123456";

        System.out.println("加密前：" + content);
        byte[] encrypted=DES_CBC_Encrypt(content.getBytes(), key.getBytes());
        String str1 = byteToHexString(encrypted);

        System.out.println("加密后：" + str1);
        byte[] decrypted=DES_CBC_Decrypt(parseHexStr2Byte(str1), key.getBytes());
        System.out.println("解密后："+new String(decrypted));
    }

    public static byte[] DES_CBC_Encrypt(byte[] content, byte[] keyBytes){
        try {
            DESKeySpec keySpec=new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey key=keyFactory.generateSecret(keySpec);

            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(keySpec.getKey()));
            byte[] result=cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static byte[] DES_CBC_Decrypt(byte[] content, byte[] keyBytes){
        try {
            DESKeySpec keySpec=new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey key=keyFactory.generateSecret(keySpec);

            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result=cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
}