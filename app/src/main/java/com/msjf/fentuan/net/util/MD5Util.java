package com.msjf.fentuan.net.util;


public final class MD5Util {
    public static String getMD5(String in) {
        java.security.MessageDigest hash = null;
        try {
            hash = java.security.MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (hash != null) {
            hash.update(in.getBytes());
            byte[] digest = hash.digest();
            String result = hex(digest);
            return result;
        } else {
            return in;
        }
    }

    public static String hex(byte[] data) {
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        char[] result = new char[data.length * 2];
        int index = 0;
        for (byte b : data) {
            result[index++] = digits[(b >>> 4) & 0xf];
            result[index++] = digits[b & 0xf];
        }
        return new String(result);
    }
}
