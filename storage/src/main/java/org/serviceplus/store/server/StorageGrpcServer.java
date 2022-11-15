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
package org.serviceplus.store.server;

import io.grpc.Server;
import org.serviceplus.store.RocksDbStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 存储模块 Grpc 服务端.
 *
 * @author lixiaoshuang
 */
public class StorageGrpcServer extends Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageGrpcServer.class);

    private Server server;

    public StorageGrpcServer(Server server) {
        this.server = server;
    }

    @Override
    public Server start() throws IOException {
        // 先初始化 RocksDB
        Properties properties = new Properties();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            properties.load(new FileReader(path + "/store.properties"));
        } catch (IOException e) {
            LOGGER.error("[StorageGrpcServer] Failed to read the configuration file.", e);
        }
        RocksDbStorage rocksDbStore = new RocksDbStorage(properties);
        rocksDbStore.init();
        // 启动 grpc 服务
        return server.start();
    }

    @Override
    public Server shutdown() {
        return server.shutdown();
    }

    @Override
    public Server shutdownNow() {
        return server.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return server.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return server.isTerminated();
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return server.awaitTermination(l, timeUnit);
    }

    @Override
    public void awaitTermination() throws InterruptedException {
        server.awaitTermination();
    }
}
