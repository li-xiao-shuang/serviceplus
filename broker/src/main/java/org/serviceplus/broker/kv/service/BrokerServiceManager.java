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
package org.serviceplus.broker.kv.service;

import io.grpc.BindableService;

import java.util.ArrayList;
import java.util.List;

/**
 * 绑定服务注册器.
 *
 * @author lixiaoshuang
 */
public class BrokerServiceManager {

    private static final List<BindableService> BINDABLE_SERVICE_LIST = new ArrayList<>();

    private BrokerServiceManager() {
    }

    public static class BrokerServiceManagerHolder {
        private static final BrokerServiceManager INSTANCE = new BrokerServiceManager();
    }

    public static BrokerServiceManager getInstance() {
        return BrokerServiceManagerHolder.INSTANCE;
    }

    public void initialized() {
        this.bindService(new BrokerKvStorageService());
    }

    public void bindService(BindableService bindableService) {
        BINDABLE_SERVICE_LIST.add(bindableService);
    }

    public List<BindableService> getBindableServiceList() {
        return BINDABLE_SERVICE_LIST;
    }
}
