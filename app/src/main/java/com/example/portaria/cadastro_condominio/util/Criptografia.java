package com.example.portaria.cadastro_condominio.util;

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


//classe que serve para salvar um arquivo com uma senha criptografada e pode ser usada em uma tela de login
public class Criptografia {


    private  Cipher cipher;
    private  SecretKeySpec key;
    private AlgorithmParameterSpec spec;
    // criar uma senha com essa string
    public static String SEED_16_CHARACTER = "U1MjU1M0FDOUZ.Qz";


//construtor da classe
    public Criptografia() throws Exception {
        // hash password with SHA-256 and crop the output to 128-bit for key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(SEED_16_CHARACTER.getBytes(Charset.defaultCharset()));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }


    public AlgorithmParameterSpec getIV() {
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(Charset.defaultCharset()));
        String encryptedText = new String( Base64.encode(encrypted,
                Base64.DEFAULT), Charset.defaultCharset());

        return encryptedText;
    }

    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, Charset.defaultCharset());

        return decryptedText;
    }
}
