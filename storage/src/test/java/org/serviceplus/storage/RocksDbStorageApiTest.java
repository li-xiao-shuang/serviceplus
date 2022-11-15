package org.serviceplus.storage;/*
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


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.serviceplus.storage.api.RocksDbStorage;

import java.util.Properties;

/**
 * @author lixiaoshuang
 */
public class RocksDbStorageApiTest {

    private RocksDbStorage rocksDbStore;

    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("rocksdb.path", "target/rocksdb");
        rocksDbStore = new RocksDbStorage(properties);
        rocksDbStore.init();
    }

    @Test
    public void testPut() {
        boolean put = rocksDbStore.put("key", "hello store");
        Assert.assertTrue(put);
    }

    @Test
    public void testGet() {
        String key = "key";
        String value = "hello store";
        rocksDbStore.put(key, value);
        String returnValue = rocksDbStore.get(key);
        Assert.assertEquals(value, returnValue);
    }

    @Test
    public void testDelete() {
        String key = "key";
        String value = "hello store";
        rocksDbStore.put(key, value);
        boolean delete = rocksDbStore.delete(key);
        Assert.assertTrue(delete);
    }
}