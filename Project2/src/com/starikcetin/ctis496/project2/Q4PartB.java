package com.starikcetin.ctis496.project2;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Q4PartB {
    private static final String IV = "AAAAAAAAAAAAAAAA";
    private static final String encryptionKey = "FooBarBazQuazQux";
    private static final String plaintextRelativePath = "/home/starikcetin/temp/message.txt";

    public static void main(String[] args) throws Exception {

        System.out.println("--------- Encrypting ---------");
        encryptAndWriteToFiles(plaintextRelativePath, encryptionKey);

        System.out.println("--------- Decrypting ---------");
        decryptAndPrintEncryptedFiles(plaintextRelativePath, encryptionKey);
    }

    private static void encryptAndWriteToFiles(String plaintextRelativePath, String encryptionKey) throws Exception {
        System.out.println("Reading plaintext from: " + plaintextRelativePath);
        var plaintext = readFileAsString(plaintextRelativePath).trim();
        System.out.println("Plaintext: " + plaintext);
        System.out.println();

        // ECB
        var ecbPath = plaintextRelativePath + ".ecb";
        var ecbCiphertextByteArr = encryptECB(plaintext, encryptionKey, "ECB");

        System.out.print("ECB ciphertext: ");
        printByteArray(ecbCiphertextByteArr);
        System.out.println();

        System.out.println("Writing ECB ciphertext to: " + ecbPath);
        writeByteArrayToFile(ecbPath, ecbCiphertextByteArr);
        System.out.println();

        // CTR
        var ctrPath = plaintextRelativePath + ".ctr";
        var ctrCiphertextByteArr = encrypt(plaintext, encryptionKey, "CTR");

        System.out.print("CTR ciphertext: ");
        printByteArray(ctrCiphertextByteArr);
        System.out.println();

        System.out.println("Writing CTR ciphertext to: " + ctrPath);
        writeByteArrayToFile(ctrPath, ctrCiphertextByteArr);
        System.out.println();

        // OFB
        var ofbPath = plaintextRelativePath + ".ofb";
        var ofbCiphertextByteArr = encrypt(plaintext, encryptionKey, "OFB");

        System.out.print("OFB ciphertext: ");
        printByteArray(ofbCiphertextByteArr);
        System.out.println();

        System.out.println("Writing OFB ciphertext to: " + ofbPath);
        writeByteArrayToFile(ofbPath, ofbCiphertextByteArr);
        System.out.println();
    }

    private static void decryptAndPrintEncryptedFiles(String plaintextRelativePath, String encryptionKey) throws Exception {
        // ECB
        var ecbPath = plaintextRelativePath + ".ecb";
        System.out.println("Reading ECB ciphertext from: " + ecbPath);
        var ecbCipherText = readFileAsByteArray(ecbPath);
        var ecbPlainText = decryptECB(ecbCipherText, encryptionKey, "ECB");
        System.out.println("ECB decrypted: " + ecbPlainText);
        System.out.println();

        // CTR
        var ctrPath = plaintextRelativePath + ".ctr";
        System.out.println("Reading CTR ciphertext from: " + ctrPath);
        var ctrCipherText = readFileAsByteArray(ctrPath);
        var ctrPlainText = decrypt(ctrCipherText, encryptionKey, "CTR");
        System.out.println("CTR decrypted: " + ctrPlainText);
        System.out.println();

        // OFB
        var ofbPath = plaintextRelativePath + ".ofb";
        System.out.println("Reading OFB ciphertext from: " + ofbPath);
        var ofbCipherText = readFileAsByteArray(ofbPath);
        var ofbPlainText = decrypt(ofbCipherText, encryptionKey, "OFB");
        System.out.println("OFB decrypted: " + ofbPlainText);
        System.out.println();
    }

    private static byte[] encrypt(String plainText, String encryptionKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    private static byte[] encryptECB(String plainText, String encryptionKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    private static String decrypt(byte[] cipherText, String encryptionKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }

    private static String decryptECB(byte[] cipherText, String encryptionKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    }

    private static String readFileAsString(String relativePath) throws IOException {
        return Files.readString(Paths.get(relativePath));
    }

    private static byte[] readFileAsByteArray(String relativePath) throws IOException {
        return Files.readAllBytes(Paths.get(relativePath));
    }

    private static void writeByteArrayToFile(String relativePath, byte[] content) throws IOException {
        Files.write(Paths.get(relativePath), content);
    }

    private static void printByteArray(byte[] arr) {
        for (byte b : arr) {
            System.out.print((int) b + " ");
        }
    }
}
