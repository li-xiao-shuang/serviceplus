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

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.serviceplus.register.proto.SpServiceRegisterGrpc;
import org.serviceplus.register.proto.SpServiceRegisterOuterClass;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoshuang
 */
@Slf4j
@Service
public class BrokerServiceRegisterService extends SpServiceRegisterGrpc.SpServiceRegisterImplBase {

    @Override
    public StreamObserver<SpServiceRegisterOuterClass.ClientRegisterRequest> bidirectionalStreamingMethod(
            StreamObserver<SpServiceRegisterOuterClass.ServerRegisterRequest> responseObserver) {
        StreamObserver<SpServiceRegisterOuterClass.ClientRegisterRequest> streamObserver =
                new StreamObserver<SpServiceRegisterOuterClass.ClientRegisterRequest>() {
                    @Override
                    public void onNext(SpServiceRegisterOuterClass.ClientRegisterRequest clientRegisterRequest) {
                        boolean hasServiceRegister = clientRegisterRequest.hasServiceRegister();
                        if (hasServiceRegister) {
                            log.info("A request for registration services is received：{}",
                                    clientRegisterRequest.getServiceRegister().getApplicationName());
                            RegisterUnifiedProcessingCenter.registerApplicationAndService(clientRegisterRequest.getServiceRegister());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("收到注册服务请求异常");
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("收到注册服务请求完成");
                    }
                };
        // 向客户端返回调用结果
        SpServiceRegisterOuterClass.ServiceRegisterResponse registerResponse = SpServiceRegisterOuterClass.ServiceRegisterResponse.newBuilder()
                .setMessage("注册服务成功").setCode(200).build();
        responseObserver.onNext(SpServiceRegisterOuterClass.ServerRegisterRequest.newBuilder()
                .setServiceRegisterResponse(registerResponse).build());
        return streamObserver;
    }
}
