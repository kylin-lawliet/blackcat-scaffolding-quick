package com.blackcat.scaffolding.utils;

import com.blackcat.scaffolding.config.Sm4StringEncryptor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 获取 IP
 * @author : zhangdahui  2024/8/23 下午3:09
 */
public class IpUtil {

    @Getter
    private static String ipAddress;
    //需要屏蔽的网络前缀（可能有不同的区域），根据需要自行定义多个
    private static final String CANAL_IP_PREFIX = "Canal前缀";//例如10.10
    private static final String CANALYZ_IP_PREFIX = "127.0.0.1";
    private static final String CANALXC_IP_PREFIX = "Canal前缀";

    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

    static {
        if (ipAddress == null){
            try {
                if (isWindowsOS()){
                    ipAddress = getWindowsIP();
                }else {
                    ipAddress = getLinuxLocalIp();
                }
            } catch (Exception e) {
                logger.error("发生错误------------>" , e);
                ipAddress = "127.0.0.1";
            }
        }
    }

    public static void main(String[] args) {
        if (isWindowsOS()){
            ipAddress = getWindowsIP();
        }else {
            ipAddress = getLinuxLocalIp();
        }
        System.out.println(ipAddress);
    }

    /**
     * 判断操作系统是否为windows系统
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

    /**
     * 获取WindowsIP
     */
    private static String getWindowsIP() {
        StringBuilder serverIP = new StringBuilder();
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (Exception e) {
            // ignore
        }
        if (null != addr) {
            byte[] ipAddr = addr.getAddress();
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    serverIP.append(".");
                }
                serverIP.append(ipAddr[i] & 0xFF);
            }
        }
        return serverIP.toString();
    }
    /**
     * 获取LinuxIp
     */
    private static String getLinuxLocalIp() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")
                                    && !ipaddress.startsWith(CANAL_IP_PREFIX)
                                    && !ipaddress.startsWith(CANALYZ_IP_PREFIX)
                                    && !ipaddress.startsWith(CANALXC_IP_PREFIX)) {// 忽略容器Canal IP
                                ip = ipaddress;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
