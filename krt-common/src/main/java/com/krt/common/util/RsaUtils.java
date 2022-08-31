package com.krt.common.util;


import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: RAS工具类
 * @date 2019年03月19日
 */
public class RsaUtils {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 获得公钥
     *
     * @param keyMap 秘钥map
     * @return 公钥base64字符串
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return new String(Base64.encodeBase64(key.getEncoded()));
    }

    /**
     * 获得私钥
     *
     * @param keyMap 秘钥map
     * @return 公钥base64字符串
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return new String(Base64.encodeBase64(key.getEncoded()));
    }


    /**
     * 生成公私钥
     *
     * @return 秘钥map
     * @throws Exception
     */
    public static Map<String, Object> initKey() {
        try {
            //获得对象 KeyPairGenerator 参数 RSA 1024个字节
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(1024);
            //通过对象 KeyPairGenerator 获取对象KeyPair
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //公私钥对象存入map中`
            Map<String, Object> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param privateKey 私钥
     * @param data       待加密数据
     * @return 签名数据
     */
    public static String encrypt(String privateKey, String data) {
        try {
            byte[] encodedKey = Base64.decodeBase64(privateKey.getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(key);
            signature.update(data.getBytes());
            return new String(Base64.encodeBase64(Base64.encodeBase64(signature.sign())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据私钥对数据要进行签名异常");
        }
    }

    /**
     * 公钥验名
     *
     * @param publicKey 公钥
     * @param data      数据
     * @param sign      待验签名
     * @return boolean  true签名通过 false签名错误
     */
    public static boolean verify(String publicKey, String data, String sign) {
        try {
            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            // 初始化验证
            signature.initVerify(key);
            signature.update(data.getBytes());
            return signature.verify(Base64.decodeBase64(Base64.decodeBase64(sign.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据私钥对数据要进行签名异常");
        }
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            System.out.println(publicKey);
            String privateKey = getPrivateKey(keyMap);
            System.out.println(privateKey);
            String sign = encrypt(privateKey, "sssssss222222");
            System.out.println(sign);
            boolean flag = verify(publicKey, "sssssss", sign);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
