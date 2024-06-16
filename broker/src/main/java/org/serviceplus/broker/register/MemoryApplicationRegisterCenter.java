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
package org.serviceplus.broker.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.serviceplus.broker.model.AdminApplication;
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
public class MemoryApplicationRegisterCenter implements ApplicationRegisterCenter {
    /**
     * 应用信息存储
     * 应用名 -> 应用列表
     */
    private static final Map<String, Set<AdminApplication>> APPLICATION_MAP = new ConcurrentHashMap<>();
    /**
     * 应用ip存储
     * 应用名 -> 应用ip
     */
    public static final Map<String, Set<String>> APPLICATION_IP_MAP = new ConcurrentHashMap<>();

    @Override
    public void registerApplication(String applicationName, AdminApplication application) {
        if (StringUtils.isBlank(applicationName) || application == null) {
            log.error("register application error, applicationName: {}, application: {}", applicationName, application);
            return;
        }
        APPLICATION_MAP.computeIfAbsent(applicationName, k -> ConcurrentHashMap.newKeySet()).add(application);
        APPLICATION_IP_MAP.computeIfAbsent(applicationName, k -> ConcurrentHashMap.newKeySet()).add(application.getIp());
    }

    @Override
    public List<String> getApplicationNames() {
        if (CollectionUtils.isEmpty(APPLICATION_MAP.keySet())) {
            return null;
        }
        return new ArrayList<>(APPLICATION_MAP.keySet());
    }

    @Override
    public List<String> getApplicationIps(String applicationName) {
        if (StringUtils.isBlank(applicationName) || !APPLICATION_IP_MAP.containsKey(applicationName)) {
            return null;
        }
        return new ArrayList<>(APPLICATION_IP_MAP.get(applicationName));
    }
}
