/*
 * Copyright 2024 service plus open source organization.
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.serviceplus.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author lixiaoshuang
 */
public class NetworkUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkUtils.class);

    /**
     * 获取本地主机的精确地址
     *
     * @return IP地址
     */
    public static String getLocalHostExactAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                     inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddresses.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr.getHostAddress();
                        }
                    }
                }
            }
            return getLocalHostIP();
        } catch (Exception e) {
            LOGGER.error("getLocalHostExactAddress error", e);
        }
        return null;
    }

    /**
     * 获取本地主机的IP地址
     *
     * @return IP地址
     */
    public static String getLocalHostIP() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            LOGGER.error("Unable to obtain IP address", e);
        }
        return "";
    }
}
