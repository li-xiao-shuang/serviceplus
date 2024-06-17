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
package org.serviceplus.client.register;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.serviceplus.client.kv.DefaultKvClient;
import org.serviceplus.client.model.SpApplication;
import org.serviceplus.register.proto.SpServiceRegisterGrpc;
import org.serviceplus.register.proto.SpServiceRegisterOuterClass;

import java.util.Properties;

/**
 * @author lixiaoshuang
 */
public class DefaultRegisterClient implements RegisterClient {

    private static volatile DefaultRegisterClient instance;

    private final ManagedChannel channel;
    private final SpServiceRegisterGrpc.SpServiceRegisterStub spServiceRegisterStub;

    private DefaultRegisterClient(Properties properties) {
        String host = properties.getProperty("host");
        int post = Integer.parseInt(properties.getProperty("port"));
        this.channel = ManagedChannelBuilder.forAddress(host, post)
                .usePlaintext()
                .build();
        this.spServiceRegisterStub = SpServiceRegisterGrpc.newStub(channel);
        Runtime.getRuntime().addShutdownHook(new Thread(channel::shutdown));
    }

    @Override
    public void register(SpApplication spApplication) {
        if (spApplication == null) {
            throw new IllegalArgumentException("spApplication is null");
        }
        spServiceRegisterStub.bidirectionalStreamingMethod(new StreamObserver<SpServiceRegisterOuterClass.ServerRegisterRequest>() {
            @Override
            public void onNext(SpServiceRegisterOuterClass.ServerRegisterRequest serverRegisterRequest) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public static DefaultRegisterClient getInstance(Properties properties) {
        if (instance == null) {
            synchronized (DefaultKvClient.class) {
                if (instance == null) {
                    instance = new DefaultRegisterClient(properties);
                }
            }
        }
        return instance;
    }
}
