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
package org.serviceplus.broker.register.storage;

import org.serviceplus.broker.model.BrokerApplication;

import java.util.List;

/**
 * @author lixiaoshuang
 */
public interface ApplicationRegisterCenter {
    /**
     * 注册应用
     *
     * @param applicationName 应用名
     * @param application     应用
     */
    void registerApplication(String applicationName, BrokerApplication application);

    /**
     * 获取应用名列表
     *
     * @return 应用名列表
     */
    List<String> getApplicationNames();

    /**
     * 获取应用ip列表
     *
     * @param applicationName 应用名
     * @return ip列表
     */
    List<String> getApplicationIps(String applicationName);
}
