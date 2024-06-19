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
package org.serviceplus.broker.server;

import io.grpc.BindableService;
import lombok.Getter;
import org.serviceplus.broker.kv.service.BrokerKvStorageService;
import org.serviceplus.broker.register.service.BrokerServiceRegisterService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * GRPC绑定服务管理.
 *
 * @author lixiaoshuang
 */
@Getter
@Component
public class BrokerServiceManager {

    @Resource
    private List<BindableService> bindableServiceList;

}
