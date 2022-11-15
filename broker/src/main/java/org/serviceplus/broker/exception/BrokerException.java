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
package org.serviceplus.broker.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lixiaoshuang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BrokerException extends RuntimeException {

    private int errorCode;

    private String errorMessage;

    public BrokerException(String errorMessage) {
        super(errorMessage);
    }

    public BrokerException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
