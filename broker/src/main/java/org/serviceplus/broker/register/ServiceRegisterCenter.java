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

import org.serviceplus.broker.model.AdminService;

/**
 * @author lixiaoshuang
 */
public interface ServiceRegisterCenter {
    /**
     * 注册服务
     *
     * @param applicationName 应用名
     * @param applicationIp   ip
     * @param service         服务
     */
    void registerService(String applicationName, String applicationIp, AdminService service);
}
