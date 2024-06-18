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
import org.serviceplus.client.model.SpService;
import org.serviceplus.register.proto.SpServiceRegisterGrpc;
import org.serviceplus.register.proto.SpServiceRegisterOuterClass;

import java.util.ArrayList;
import java.util.List;
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
        StreamObserver<SpServiceRegisterOuterClass.ClientRegisterRequest> streamObserver = spServiceRegisterStub.bidirectionalStreamingMethod(new StreamObserver<SpServiceRegisterOuterClass.ServerRegisterRequest>() {
            @Override
            public void onNext(SpServiceRegisterOuterClass.ServerRegisterRequest serverRegisterRequest) {
                boolean hassed = serverRegisterRequest.hasServiceRegisterResponse();
                if (hassed) {
                    SpServiceRegisterOuterClass.ServiceRegisterResponse serviceRegisterResponse = serverRegisterRequest.getServiceRegisterResponse();
                    System.out.println("接收到服务注册响应:");
                    System.out.println(serviceRegisterResponse.getMessage());
                    System.out.println(serviceRegisterResponse.getCode());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("服务注册失败:" + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("服务注册完成");
            }
        });
        // 构建客户端请求
        SpServiceRegisterOuterClass.ServiceRegister serviceRegister = SpServiceRegisterOuterClass.ServiceRegister.newBuilder()
                .setApplicationName(spApplication.getApplicationName())
                .setIp(spApplication.getIp())
                .setPort(spApplication.getPort())
                .addAllClientService(this.convertSpServices(spApplication.getSpServices()))
                .build();

        SpServiceRegisterOuterClass.ClientRegisterRequest clientRegisterRequest = SpServiceRegisterOuterClass.ClientRegisterRequest.newBuilder()
                .setServiceRegister(serviceRegister)
                .build();
        streamObserver.onNext(clientRegisterRequest);
        streamObserver.onCompleted();
    }


    /**
     * 将SpService转换为Protobuf消息
     *
     * @param spServices SpService列表
     * @return Protobuf消息列表
     */
    public List<SpServiceRegisterOuterClass.ClientService> convertSpServices(List<SpService> spServices) {
        List<SpServiceRegisterOuterClass.ClientService> clientServices = new ArrayList<>();
        for (SpService spService : spServices) {
            SpServiceRegisterOuterClass.ClientService.Builder clientServiceBuilder =
                    SpServiceRegisterOuterClass.ClientService.newBuilder();
            clientServiceBuilder.setClassName(spService.getClassName());
            clientServiceBuilder.setSimpleClassName(spService.getSimpleClassName());
            clientServiceBuilder.setServiceName(spService.getServiceName());
            clientServiceBuilder.setMethodName(spService.getMethodName());
            if (spService.getParamNames() != null) {
                clientServiceBuilder.addAllParamNames(spService.getParamNames());
            }
            if (spService.getParamDesc() != null) {
                clientServiceBuilder.addAllParamDesc(spService.getParamDesc());
            }
            // 将Class<?>类型的参数转化为字符串（即类的全限定名）
            if (spService.getParamTypes() != null) {
                List<String> paramTypeNames = new ArrayList<>();
                for (Class<?> paramType : spService.getParamTypes()) {
                    paramTypeNames.add(paramType.getName());
                }
                clientServiceBuilder.addAllParamTypes(paramTypeNames);
            }

            // 设置返回值名称和类型（需要将Class<?>类型的返回值转换为字符串）
            clientServiceBuilder.setReturnNames(spService.getReturnNames());
            if (spService.getReturnTypes() != null) {
                clientServiceBuilder.setReturnTypes(spService.getReturnTypes().getName());
            }
            clientServices.add(clientServiceBuilder.build());
        }
        return clientServices;
    }


    /**
     * 单例模式
     *
     * @param properties 配置
     * @return DefaultRegisterClient
     */
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
