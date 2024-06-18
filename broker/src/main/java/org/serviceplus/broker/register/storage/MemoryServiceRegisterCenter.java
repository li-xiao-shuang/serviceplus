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
import org.serviceplus.broker.model.BrokerService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiaoshuang
 */
@Component
@Slf4j
public class MemoryServiceRegisterCenter implements ServiceRegisterCenter {
    /**
     * 服务存储
     * 应用名 -> 应用ip -> 服务列表
     */
    public static final Map<String, Map<String, Set<BrokerService>>> SERVICE_MAP = new ConcurrentHashMap<>();

    @Override
    public void registerService(String applicationName, String applicationIp, BrokerService service) {
        if (StringUtils.isBlank(applicationName) || StringUtils.isBlank(applicationIp) || service == null) {
            log.error("register service error, applicationName: {}, applicationIp: {}, service: {}", applicationName, applicationIp, service);
            return;
        }
        SERVICE_MAP.computeIfAbsent(applicationName, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(applicationIp, k -> ConcurrentHashMap.newKeySet())
                .add(service);
    }
}
