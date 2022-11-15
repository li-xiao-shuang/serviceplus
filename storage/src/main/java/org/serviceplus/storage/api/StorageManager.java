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
package org.serviceplus.storage.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * 存储管理.
 *
 * @author lixiaoshuang
 */
public class StorageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageManager.class);

    private static final StorageManager storageManager = new StorageManager();

    private StorageApi storageApi;

    private StorageManager() {
    }

    public static StorageManager instance() {
        return storageManager;
    }

    /**
     * 初始化存储.
     */
    public void initialized() {
        if (Objects.nonNull(storageApi)) {
            return;
        }
        Properties properties = new Properties();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            properties.load(new FileReader(path + "/store.properties"));
        } catch (IOException e) {
            LOGGER.error("Failed to read the configuration file.", e);
        }
        RocksDbStorage rocksDbStore = new RocksDbStorage(properties);
        rocksDbStore.init();
        storageApi = rocksDbStore;
    }

    /**
     * 获取存储器.
     *
     * @return StorageApi
     */
    public StorageApi getStorage() {
        return storageApi;
    }
}
