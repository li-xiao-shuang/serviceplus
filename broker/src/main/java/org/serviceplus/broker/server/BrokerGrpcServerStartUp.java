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

import io.grpc.Server;
import org.serviceplus.broker.kv.service.BrokerServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * broker grpc 服务端启动类
 *
 * @author lixiaoshuang
 */
@Component
public class BrokerGrpcServerStartUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerGrpcServerStartUp.class);

    @Value("${serviceplus.broker.grpc.port:8766}")
    private int port;

    @PostConstruct
    public void start() {
        BrokerGrpcServerBuilder brokerGrpcServerBuilder = BrokerGrpcServerBuilder.forPort(port);
        Server server = brokerGrpcServerBuilder.addService(BrokerServiceManager.getInstance().getBindableServiceList()).build();
        try {
            server.start();
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
            LOGGER.info("The grpc server at the broker tier is successfully started.");
        } catch (IOException e) {
            LOGGER.error("The grpc server at the broker tier fails to be started.", e);
        }
    }
}
