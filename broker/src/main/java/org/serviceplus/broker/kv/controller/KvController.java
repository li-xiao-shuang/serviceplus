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
package org.serviceplus.broker.kv.controller;

import org.serviceplus.broker.kv.storage.KvStorageClient;
import org.serviceplus.broker.kv.storage.KvStorageClientFactory;
import org.serviceplus.broker.model.Response;
import org.serviceplus.broker.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping(path = "kv/v1")
public class KvController {

    @PostMapping(path = "put")
    public Response<Boolean> putKv(@RequestParam("key") String key, @RequestParam("value") String value) {
        KvStorageClient kvStorageClient = KvStorageClientFactory.createKvStorageClient();
        boolean put = kvStorageClient.put(key, value);
        return ResponseUtil.success(put);
    }

    @GetMapping(path = "get")
    public Response getKv(@RequestParam("key") String key) {
        KvStorageClient kvStorageClient = KvStorageClientFactory.createKvStorageClient();
        String value = kvStorageClient.get(key);
        return ResponseUtil.success(value);
    }
}
