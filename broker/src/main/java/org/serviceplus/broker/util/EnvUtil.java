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
package org.serviceplus.broker.util;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lixiaoshuang
 */
public class EnvUtil {

    private static ConfigurableEnvironment environment;

    public static void setEnvironment(ConfigurableEnvironment environment) {
        EnvUtil.environment = environment;
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }
}
