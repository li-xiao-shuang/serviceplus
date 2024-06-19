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
package org.serviceplus.broker.register.service;

import org.serviceplus.broker.model.BrokerApplicationInfo;
import org.serviceplus.broker.model.BrokerService;
import org.serviceplus.broker.register.storage.ApplicationRegisterStorageCenter;
import org.serviceplus.broker.register.storage.BrokerRegisterStorageCenterFactory;
import org.serviceplus.broker.register.storage.ServiceRegisterStorageCenter;
import org.serviceplus.register.proto.SpServiceRegisterOuterClass;

import java.util.List;

/**
 * @author lixiaoshuang
 */
public class RegisterUnifiedProcessingCenter {
    /**
     * 注册应用及服务
     *
     * @param serviceRegister 注册信息
     */
    public static void registerApplicationAndService(SpServiceRegisterOuterClass.ServiceRegister serviceRegister) {
        BrokerApplicationInfo brokerApplicationInfo = BrokerApplicationInfo.builder()
                .applicationName(serviceRegister.getApplicationName())
                .applicationIp(serviceRegister.getApplicationIp())
                .applicationPort(serviceRegister.getApplicationPort())
                .applicationType(serviceRegister.getApplicationType())
                .build();

        ApplicationRegisterStorageCenter applicationRegisterStorageCenter = BrokerRegisterStorageCenterFactory.createApplicationRegisterStorageCenter();
        applicationRegisterStorageCenter.registerApplication(serviceRegister.getApplicationName(), brokerApplicationInfo);

        ServiceRegisterStorageCenter serviceRegisterStorageCenter = BrokerRegisterStorageCenterFactory.createServiceRegisterStorageCenter();
        serviceRegister.getClientServiceList().forEach(clientService -> {
            BrokerService brokerService = getBrokerService(clientService);
            serviceRegisterStorageCenter.registerService(brokerApplicationInfo, brokerService);
        });
    }

    private static BrokerService getBrokerService(SpServiceRegisterOuterClass.ClientService clientService) {
        BrokerService brokerService = new BrokerService();
        brokerService.setClassName(clientService.getClassName());
        brokerService.setSimpleClassName(clientService.getSimpleClassName());
        brokerService.setMethodDesc(clientService.getMethodDesc());
        brokerService.setMethodName(clientService.getMethodName());
        brokerService.setParamNames(clientService.getParamNamesList());
        brokerService.setParamDesc(clientService.getParamDescList());
        brokerService.setParamTypes(clientService.getParamTypesList());
        brokerService.setReturnName(clientService.getReturnName());
        brokerService.setReturnType(clientService.getReturnType());
        return brokerService;
    }

    /**
     * 获取应用列表
     */
    public static List<BrokerApplicationInfo> getApplicationList() {
        ApplicationRegisterStorageCenter applicationRegisterStorageCenter = BrokerRegisterStorageCenterFactory.createApplicationRegisterStorageCenter();
        return applicationRegisterStorageCenter.getApplicationList();
    }

    /**
     * 获取应用ip列表
     *
     * @param applicationName 应用名
     * @return ip列表
     */
    public static List<String> getIpList(String applicationName) {
        ApplicationRegisterStorageCenter applicationRegisterStorageCenter = BrokerRegisterStorageCenterFactory.createApplicationRegisterStorageCenter();
        return applicationRegisterStorageCenter.getApplicationIps(applicationName);
    }

    /**
     * 获取服务列表
     *
     * @param applicationName 应用名
     * @param applicationIp   ip
     * @return 服务列表
     */
    public static List<BrokerService> getServiceList(String applicationName, String applicationIp, String applicationPort) {
        ServiceRegisterStorageCenter serviceRegisterStorageCenter = BrokerRegisterStorageCenterFactory.createServiceRegisterStorageCenter();
        return serviceRegisterStorageCenter.getService(applicationName, applicationIp, applicationPort);
    }
}
