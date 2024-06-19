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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.serviceplus.broker.model.BrokerApplicationInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiaoshuang
 */
@Component
@Slf4j
public class MemoryApplicationRegisterStorageCenter implements ApplicationRegisterStorageCenter {
    /**
     * 应用信息存储
     * 应用名 -> 应用列表
     */
    private static final Map<String, Set<BrokerApplicationInfo>> APPLICATION_MAP = new ConcurrentHashMap<>();
    /**
     * 应用ip存储
     * 应用名 -> 应用ip
     */
    public static final Map<String, Set<String>> APPLICATION_IP_MAP = new ConcurrentHashMap<>();

    public static class MemoryApplicationRegisterCenterHolder {
        public static final MemoryApplicationRegisterStorageCenter INSTANCE = new MemoryApplicationRegisterStorageCenter();
    }

    public static MemoryApplicationRegisterStorageCenter getInstance() {
        return MemoryApplicationRegisterCenterHolder.INSTANCE;
    }

    @Override
    public void registerApplication(String applicationName, BrokerApplicationInfo application) {
        if (StringUtils.isBlank(applicationName) || application == null) {
            log.error("register application error, applicationName: {}, application: {}", applicationName, application);
            return;
        }
        APPLICATION_MAP.computeIfAbsent(applicationName, k -> ConcurrentHashMap.newKeySet()).add(application);
        APPLICATION_IP_MAP.computeIfAbsent(applicationName, k -> ConcurrentHashMap.newKeySet()).add(application.getApplicationIp());
    }

    @Override
    public List<BrokerApplicationInfo> getApplicationList() {
        Set<String> applicationNames = APPLICATION_MAP.keySet();
        List<BrokerApplicationInfo> brokerApplicationInfos = new ArrayList<>();
        for (String applicationName : applicationNames) {
            Set<BrokerApplicationInfo> applications = APPLICATION_MAP.get(applicationName);
            if (CollectionUtils.isNotEmpty(applications)) {
                brokerApplicationInfos.addAll(applications);
            }
        }
        return brokerApplicationInfos;
    }

    @Override
    public List<String> getApplicationIps(String applicationName) {
        if (StringUtils.isBlank(applicationName) || !APPLICATION_IP_MAP.containsKey(applicationName)) {
            return null;
        }
        return new ArrayList<>(APPLICATION_IP_MAP.get(applicationName));
    }
}
