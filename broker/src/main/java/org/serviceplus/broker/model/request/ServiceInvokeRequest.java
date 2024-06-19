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
package org.serviceplus.broker.model.request;

import lombok.Data;

/**
 * @author lixiaoshuang
 */
@Data
public class ServiceInvokeRequest {
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private Object[] params;
    /**
     * 参数类型
     */
    private String[] paramTypes;
    /**
     * 返回值类型
     */
    private String returnType;
}
