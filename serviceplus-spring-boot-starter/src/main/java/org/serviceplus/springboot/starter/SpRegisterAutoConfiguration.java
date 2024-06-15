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
package org.serviceplus.springboot.starter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author lixiaoshuang
 */
@Configuration
public class SpRegisterAutoConfiguration {

    private final BeanFactory beanFactory;
    private final Environment environment;

    public SpRegisterAutoConfiguration(BeanFactory beanFactory, Environment environment) {
        this.beanFactory = beanFactory;
        this.environment = environment;
    }

    @Bean
    @ConditionalOnMissingBean
    public SpRegisterScanner spRegisterScanner() {
        return SpRegisterScanner.getInstance(beanFactory, environment);
    }
}
