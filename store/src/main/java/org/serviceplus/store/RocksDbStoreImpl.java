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

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.io.File;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author lixiaoshuang
 */
public class RocksDbStoreImpl extends AbstractStoreService {
    
    private static final String DEFAULT_ROCKSDB_PATH = "rocksdb";
    
    private static final String ROCKSDB_PATH_KEY = "rocksdb.path";
    
    private static final AtomicBoolean INIT_STATUS = new AtomicBoolean(false);
    
    private static RocksDB rocksdb;
    
    private Properties properties;
    
    
    public RocksDbStoreImpl(Properties properties) {
        this.properties = properties;
    }
    
    @Override
    public void init() {
        if (!INIT_STATUS.compareAndSet(false, true)) {
            return;
        }
        String rocksdbPath = Optional.ofNullable(String.valueOf(properties.get(ROCKSDB_PATH_KEY)))
                .orElse(DEFAULT_ROCKSDB_PATH);
        File file = new File(rocksdbPath);
        if (!file.exists() && !file.isDirectory()) {
            boolean mkdir = file.mkdirs();
        }
        
        //加载 RocksDB C++ 库的静态方法。
        RocksDB.loadLibrary();
        try (Options options = new Options()) {
            options.setCreateIfMissing(true);
            rocksdb = RocksDB.open(options, rocksdbPath);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void close() {
        try {
            rocksdb.closeE();
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean put(byte[] key, byte[] value) {
        try {
            rocksdb.put(key, value);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    
    @Override
    public byte[] get(byte[] key) {
        try {
            return rocksdb.get(key);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }
}
