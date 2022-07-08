package org.serviceplus.store;/*
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

import java.util.Properties;

/**
 * @author lixiaoshuang
 */
public class RocksDbStoreImplTest {
    
    private RocksDbStoreImpl rocksDbStore;
    
    @Before
    public void setUp() {
        Properties properties = new Properties();
        properties.setProperty("rocksdb.path", "target/rocksdb");
        rocksDbStore = new RocksDbStoreImpl(properties);
        rocksDbStore.init();
    }
    
    @Test
    public void testPut() {
        boolean put = rocksDbStore.put("key".getBytes(), "hello store".getBytes());
        Assert.assertTrue(put);
    }
    
    @Test
    public void testGet() {
        String key = "key";
        String value = "hello store";
        rocksDbStore.put(key.getBytes(), value.getBytes());
        byte[] bytes = rocksDbStore.get(key.getBytes());
        Assert.assertEquals(value, new String(bytes));
    }
    
    @Test
    public void testDelete() {
        String key = "key";
        String value = "hello store";
        rocksDbStore.put(key.getBytes(), value.getBytes());
        boolean delete = rocksDbStore.delete(key.getBytes());
        Assert.assertTrue(delete);
    }
}