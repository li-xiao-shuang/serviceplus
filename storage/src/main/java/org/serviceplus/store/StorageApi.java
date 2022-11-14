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

/**
 * @author lixiaoshuang
 */
public interface StorageApi {

    /**
     * 存储 K/V 数据
     *
     * @param key   key
     * @param value value
     * @return 是否存储成功
     */
    boolean put(String key, String value);

    /**
     * 获取指定 key 的 value
     *
     * @param key key
     * @return key 对应的 value
     */
    String get(String key);

    /**
     * 删除指定 key
     *
     * @param key key
     * @return 是否删除成功
     */
    boolean delete(String key);
}
