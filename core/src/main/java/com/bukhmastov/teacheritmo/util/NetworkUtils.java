package com.bukhmastov.teacheritmo.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {

    public static String host2hex(String host) {
        return ByteArrayUtil.bytes2hex(NetworkUtils.host2binary(host));
    }

    public static byte[] host2binary(String host) {
        try {
            if (host == null) {
                return new byte[]{};
            }
            return InetAddress.getByName(host).getAddress();
        } catch (UnknownHostException e) {
            return new byte[]{};
        }
    }

    public static String hex2host(String hex) {
        return NetworkUtils.binary2host(ByteArrayUtil.hex2bytes(hex));
    }

    public static String binary2host(byte[] bytes) {
        try {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            InetAddress address = InetAddress.getByAddress(bytes);
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
