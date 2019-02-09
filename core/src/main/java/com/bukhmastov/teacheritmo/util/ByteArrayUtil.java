package com.bukhmastov.teacheritmo.util;

public class ByteArrayUtil {

    /**
     * Create a byte Array from String of hexadecimal digits using Character conversion
     * @param hexString - Hexadecimal digits as String
     * @return Desired byte Array
     */
    public static byte[] hex2bytes(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
        }
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hex2byte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    /**
     * Create a String of hexadecimal digits from a byte Array using Character conversion
     * @param byteArray - The byte Array
     * @return Desired String of hexadecimal digits in lower case
     */
    public static String bytes2hex(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            sb.append(byte2hex(byteArray[i]));
        }
        return sb.toString();
    }

    public static String byte2hex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static byte hex2byte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }
}
