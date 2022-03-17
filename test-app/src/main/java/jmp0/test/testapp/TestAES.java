package jmp0.test.testapp;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestAES {
    private static final String TAG = "AESCBCUtils";

    // CBC(Cipher Block Chaining, 加密快链)模式，PKCS5Padding补码方式
    // AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    // AES 加密
    private static final String AES = "AES";

    // 密钥偏移量
    //private static final String mstrIvParameter = "1234567890123456";
    /* key必须为16位，可更改为自己的key */
    //String mstrTestKey = "1234567890123456";

    // 加密
    /**
     * AES 加密
     *
     * @param strKey            加密密钥
     * @param strClearText      待加密内容
     * @param mstrIvParameter   密钥偏移量
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt_AES(String strKey, String strClearText, String mstrIvParameter){

        try {
            byte[] raw = strKey.getBytes();
            // 创建AES密钥
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            // 创建偏移量
            IvParameterSpec iv = new IvParameterSpec(mstrIvParameter.getBytes());
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            // 执行加密操作
            byte[] cipherText = cipher.doFinal(strClearText.getBytes());
            //Log.d(TAG, "encrypt result(not BASE64): " + cipherText.toString());
            String strBase64Content = Base64.encodeToString(cipherText, Base64.DEFAULT); // encode it by BASE64 again
            //Log.d(TAG, "encrypt result(BASE64): " + strBase64Content);
            strBase64Content = strBase64Content.replaceAll(System.getProperty("line.separator"), "");

            return strBase64Content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 解密
    /**
     * AES 解密
     *
     * @param strKey            解密密钥
     * @param strCipherText      待解密内容
     * @param mstrIvParameter   偏移量
     * @return 返回Base64转码后的加密数据
     */
    public static String decrypt(String strKey, String strCipherText, String mstrIvParameter) throws Exception {

        try {
            byte[] raw = strKey.getBytes("ASCII");
            // 创建AES秘钥
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            // 创建偏移量
            IvParameterSpec iv = new IvParameterSpec(mstrIvParameter.getBytes());
            // 初始化解密器
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 执行解密操作
            byte[] cipherText = Base64.decode(strCipherText, Base64.DEFAULT); // decode by BASE64 first
            //Log.d(TAG, "BASE64 decode result(): " + cipherText.toString());
            byte[] clearText = cipher.doFinal(cipherText);
            String strClearText = new String(clearText);
            //Log.d(TAG, "decrypt result: " + strClearText);

            return strClearText;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String testAll() throws Exception {
        String AESKey = "1234567890123456";
        String AESIv = "1234567890123456";

        return TestAES.decrypt(AESKey, TestAES.encrypt_AES(AESKey, "abcdefg", AESIv),AESIv);
    }
}
