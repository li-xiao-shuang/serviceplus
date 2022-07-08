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

package org.serviceplus.store;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author lixiaoshuang
 */
public class StoreStartUp {
    
    public static void main(String[] args) {
        Properties properties = new Properties();
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            properties.load(new FileReader(path + "/store.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RocksDbStoreImpl rocksDbStore = new RocksDbStoreImpl(properties);
        rocksDbStore.init();
    }
}
