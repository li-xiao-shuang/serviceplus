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

package org.serviceplus.storage;


import io.grpc.Server;
import org.serviceplus.storage.server.StorageGrpcServerBuilder;
import org.serviceplus.storage.service.ServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author lixiaoshuang
 */
public class StorageStartUp {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageStartUp.class);

    public static void main(String[] args) {
        StorageGrpcServerBuilder storageGrpcServerBuilder = StorageGrpcServerBuilder.forPort(8866);
        Server server = storageGrpcServerBuilder.addService(ServiceRegister.getBindableServiceList()).build();
        try {
            server.start();
            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
            LOGGER.info("The grpc server at the storage tier is successfully started.");
        } catch (IOException e) {
            LOGGER.error("The grpc server at the storage tier fails to be started.", e);
        }
        while (true) {

        }
    }
}
