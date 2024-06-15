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
package org.serviceplus.client.annotation;

import java.lang.annotation.*;

/**
 * 用于标记需要注册的服务
 *
 * @author lixiaoshuang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpRegister {
    /**
     * 服务名称
     */
    String name() default "";

    /**
     * 参数描述,多个参数用;分隔.如: 用户名;密码
     */
    String paramDesc() default "";
}
