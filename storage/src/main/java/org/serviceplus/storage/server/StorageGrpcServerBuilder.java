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
package org.serviceplus.storage.server;

import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.*;
import org.serviceplus.storage.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 存储模块 Grpc 服务端构建者.
 *
 * @author lixiaoshuang
 */
public class StorageGrpcServerBuilder extends ServerBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageGrpcServerBuilder.class);

    private final ServerBuilder<?> serverBuilder;

    public StorageGrpcServerBuilder(ServerBuilder<?> serverBuilder) {
        this.serverBuilder = serverBuilder;
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.initialized();
    }

    /**
     * 通过端口创建 StoreGrpcServerBuilder.
     *
     * @param port 监听的端口
     * @return StoreGrpcServerBuilder
     */
    public static StorageGrpcServerBuilder forPort(int port) {
        ServerBuilder<?> builder = ServerBuilder.forPort(port);
        return new StorageGrpcServerBuilder(builder);
    }

    /**
     * 批量绑定服务实现到服务方法.
     *
     * @param bindableServices 服务实现
     * @return StoreGrpcServerBuilder
     */
    public ServerBuilder addService(List<BindableService> bindableServices) {
        for (BindableService bindableService : bindableServices) {
            this.serverBuilder.addService(bindableService);
            LOGGER.info("binding:" + bindableService.bindService().getServiceDescriptor().getName());
        }
        return this;
    }


    @Override
    public ServerBuilder directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    @Override
    public ServerBuilder executor(@Nullable Executor executor) {
        this.serverBuilder.executor(executor);
        return this;
    }

    @Override
    public ServerBuilder addService(ServerServiceDefinition service) {
        this.serverBuilder.addService(service);
        return this;
    }

    @Override
    public ServerBuilder addService(BindableService bindableService) {
        this.serverBuilder.addService(bindableService);
        return this;
    }

    @Override
    public ServerBuilder fallbackHandlerRegistry(@Nullable HandlerRegistry fallbackRegistry) {
        this.serverBuilder.fallbackHandlerRegistry(fallbackRegistry);
        return this;
    }

    @Override
    public ServerBuilder useTransportSecurity(File certChain, File privateKey) {
        this.serverBuilder.useTransportSecurity(certChain, privateKey);
        return this;
    }

    @Override
    public ServerBuilder decompressorRegistry(@Nullable DecompressorRegistry registry) {
        this.serverBuilder.decompressorRegistry(registry);
        return this;
    }

    @Override
    public ServerBuilder compressorRegistry(@Nullable CompressorRegistry registry) {
        this.serverBuilder.compressorRegistry(registry);
        return this;
    }

    @Override
    public Server build() {
        return new StorageGrpcServer(this.serverBuilder.build());
    }
}
