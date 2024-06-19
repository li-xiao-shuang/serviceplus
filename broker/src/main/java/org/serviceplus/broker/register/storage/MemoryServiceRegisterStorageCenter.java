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
import org.apache.commons.lang3.StringUtils;
import org.serviceplus.broker.model.BrokerApplicationInfo;
import org.serviceplus.broker.model.BrokerService;
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
public class MemoryServiceRegisterStorageCenter implements ServiceRegisterStorageCenter {
    /**
     * 服务存储
     * 应用名 -> 应用ip+port -> 服务列表
     */
    public static final Map<String, Map<String, Set<BrokerService>>> SERVICE_MAP = new ConcurrentHashMap<>();

    public static class MemoryServiceRegisterCenterHolder {
        public static final MemoryServiceRegisterStorageCenter INSTANCE = new MemoryServiceRegisterStorageCenter();
    }

    public static MemoryServiceRegisterStorageCenter getInstance() {
        return MemoryServiceRegisterCenterHolder.INSTANCE;
    }

    @Override
    public void registerService(BrokerApplicationInfo applicationInfo, BrokerService service) {
        if (applicationInfo == null || service == null) {
            log.error("register service error, applicationInfo: {}, service: {}", applicationInfo, service);
            return;
        }
        SERVICE_MAP.computeIfAbsent(applicationInfo.getApplicationName(), k -> new ConcurrentHashMap<>())
                .computeIfAbsent(applicationInfo.getApplicationIp() + applicationInfo.getApplicationPort(), k -> ConcurrentHashMap.newKeySet())
                .add(service);
    }

    @Override
    public List<BrokerService> getService(String applicationName, String applicationIp, String applicationPort) {
        Map<String, Set<BrokerService>> applicationMap = SERVICE_MAP.get(applicationName);
        if (applicationMap == null) {
            return new ArrayList<>();
        }
        Set<BrokerService> brokerServices = applicationMap.get(applicationIp + applicationPort);
        if (brokerServices == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(brokerServices);
    }
}
