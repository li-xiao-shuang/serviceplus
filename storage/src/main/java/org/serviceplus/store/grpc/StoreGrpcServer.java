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
package org.serviceplus.store.grpc;

import io.grpc.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiaoshuang
 */
public class StoreGrpcServer extends Server {

    @Override
    public Server start() throws IOException {
        return null;
    }

    @Override
    public Server shutdown() {
        return null;
    }

    @Override
    public Server shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override
    public void awaitTermination() throws InterruptedException {

    }
}
