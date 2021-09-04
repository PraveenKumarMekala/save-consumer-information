package com.saveconsumer.info.encyption;

import com.saveconsumer.info.domain.Consumer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EncryptAndDecrypt {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return cipher.doFinal(pText);
    }


    public static List<Consumer> decryptConsumers(byte[] cText, SecretKey secret, byte[] iv) throws Exception {
        byte[] plainText = cipherText(cText, secret, iv);
        return byteArrayToListConsumers(new String(plainText, UTF_8).getBytes());
    }

    public static Consumer decryptConsumer(byte[] cText, SecretKey secret, byte[] iv) throws Exception {
        byte[] plainText = cipherText(cText, secret, iv);
        return byteArrayToConsumer(new String(plainText, UTF_8).getBytes());
    }

    private static byte[] cipherText(byte[] consumerText, SecretKey secret, byte[] iv) throws Exception{
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return cipher.doFinal(consumerText);
    }

    public static byte[] encryptedObjects(List<Consumer> consumers){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        byte[] encryptedText = null;
        try {
            SecretKey secretKey = CryptoUtils.getAESKey(256);
            byte[] iv = CryptoUtils.getRandomNonce(12);
            out = new ObjectOutputStream(bos);
            out.writeObject(consumers);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            encryptedText = EncryptAndDecrypt.encrypt(yourBytes, secretKey, iv);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    private static List<Consumer> byteArrayToListConsumers(byte[] input) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        List<Consumer> consumers;
        try {
            in = new ObjectInputStream(bis);
            consumers = (List<Consumer>) in.readObject();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return consumers;
    }

    private static Consumer byteArrayToConsumer(byte[] input) {
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        Consumer consumer = null;
        try {
            in = new ObjectInputStream(bis);
            consumer = (Consumer) in.readObject();
        } catch (IOException e) {
            // TODO: Add Logger
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return consumer;
    }
}
