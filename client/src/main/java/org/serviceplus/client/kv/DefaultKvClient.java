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
package org.serviceplus.client.kv;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.serviceplus.store.proto.KvServiceGrpc;
import org.serviceplus.store.proto.KvServiceOuterClass;

import java.util.Properties;

/**
 * @author lixiaoshuang
 */
public class DefaultKvClient implements KvClient {

    private Properties properties;

    private ManagedChannel channel;

    private KvServiceGrpc.KvServiceBlockingStub kvServiceBlockingStub;

    public DefaultKvClient(Properties properties) {
        this.properties = properties;
        String host = properties.getProperty("host");
        int post = Integer.parseInt(properties.getProperty("port"));
        //初始化连接
        this.channel = ManagedChannelBuilder.forAddress(host, post)
                .usePlaintext()
                .build();
        //初始化远程服务Stub
        this.kvServiceBlockingStub = KvServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public boolean put(String key, String value) {
        KvServiceOuterClass.KvRequest kvRequest = KvServiceOuterClass.KvRequest.newBuilder().setKey(key).setValue(value).build();
        KvServiceOuterClass.KvResponse kvResponse = kvServiceBlockingStub.put(kvRequest);
        return Boolean.parseBoolean(kvResponse.getData());
    }

    @Override
    public String get(String key) {
        KvServiceOuterClass.KvRequest kvRequest = KvServiceOuterClass.KvRequest.newBuilder().setKey(key).build();
        KvServiceOuterClass.KvResponse kvResponse = kvServiceBlockingStub.get(kvRequest);
        return kvResponse.getData();
    }
}
