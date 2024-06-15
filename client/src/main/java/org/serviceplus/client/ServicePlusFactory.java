/*
 * Copyright 2022 service plus open source organization.
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
package org.serviceplus.client;

import org.serviceplus.client.register.DefaultRegisterClient;
import org.serviceplus.client.register.RegisterClient;
import org.serviceplus.client.kv.DefaultKvClient;
import org.serviceplus.client.kv.KvClient;

import java.util.Properties;

/**
 * @author lixiaoshuang
 */
public class ServicePlusFactory {
    /**
     * 创建kv客户端
     *
     * @param properties 配置文件
     * @return kv客户端
     */
    public static KvClient createKvClient(Properties properties) {
        return DefaultKvClient.getInstance(properties);
    }

    /**
     * 创建注册客户端
     *
     * @param properties 配置文件
     * @return 注册客户端
     */
    public static RegisterClient createRegisterClient(Properties properties) {
        return DefaultRegisterClient.getInstance();
    }
}
