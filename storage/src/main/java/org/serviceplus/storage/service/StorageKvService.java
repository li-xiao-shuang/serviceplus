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
package org.serviceplus.storage.service;

import io.grpc.stub.StreamObserver;
import org.serviceplus.storage.api.StorageApi;
import org.serviceplus.storage.api.StorageManager;
import org.serviceplus.store.proto.KvServiceGrpc;
import org.serviceplus.store.proto.KvServiceOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * kv 操作实现类.
 *
 * @author lixiaoshuang
 */
public class StorageKvService extends KvServiceGrpc.KvServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageKvService.class);

    @Override
    public void put(KvServiceOuterClass.KvRequest request, StreamObserver<KvServiceOuterClass.KvResponse> responseObserver) {
        String key = request.getKey();
        String value = request.getValue();
        StorageManager storageManager = StorageManager.instance();
        StorageApi storage = storageManager.getStorage();
        boolean result = storage.put(key, value);
        LOGGER.info("put operation,key:{},value:{},result:{}", key, value, result);
        KvServiceOuterClass.KvResponse kvResponse = KvServiceOuterClass.KvResponse
                .newBuilder().setErrorCode("0").setErrorMessage("").setData(String.valueOf(result)).build();
        responseObserver.onNext(kvResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void get(KvServiceOuterClass.KvRequest request, StreamObserver<KvServiceOuterClass.KvResponse> responseObserver) {
        String key = request.getKey();
        StorageManager storageManager = StorageManager.instance();
        StorageApi storage = storageManager.getStorage();
        String value = storage.get(key);
        LOGGER.info("get operation key:{},value:{}", key, value);
        KvServiceOuterClass.KvResponse kvResponse = KvServiceOuterClass.KvResponse
                .newBuilder().setErrorCode("0").setErrorMessage("").setData(value).build();
        responseObserver.onNext(kvResponse);
        responseObserver.onCompleted();
    }
}
