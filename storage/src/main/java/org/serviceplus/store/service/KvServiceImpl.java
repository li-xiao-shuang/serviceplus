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
package org.serviceplus.store.service;

import io.grpc.stub.StreamObserver;
import org.serviceplus.store.proto.KvServiceGrpc;
import org.serviceplus.store.proto.KvServiceOuterClass;

/**
 * @author lixiaoshuang
 */
public class KvServiceImpl extends KvServiceGrpc.KvServiceImplBase {

    @Override
    public void put(KvServiceOuterClass.KvRequest request, StreamObserver<KvServiceOuterClass.KvResponse> responseObserver) {
        String key = request.getKey();
        String value = request.getValue();
        System.out.println("存储成功");
        KvServiceOuterClass.KvResponse kvResponse = KvServiceOuterClass.KvResponse
                .newBuilder().setErrorCode("88888").setErrorMessage("存储成功").build();
        responseObserver.onNext(kvResponse);
        responseObserver.onCompleted();
    }
}
