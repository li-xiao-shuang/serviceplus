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

package org.serviceplus.broker.util;


import org.serviceplus.broker.enums.ErrorCode;
import org.serviceplus.broker.model.Response;

/**
 * @author lixiaoshuang
 */
public class ResponseUtil {

    /**
     * Construct a successful response to the request with no return value
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> success() {
        return new Response<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    /**
     * Build a successful access response
     *
     * @param dada
     * @return
     */
    public static <T> Response<T> success(T dada) {
        return new Response<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), dada);
    }

    /**
     * Build a successful access response,Specify code and message and data
     *
     * @param code
     * @param message
     * @param dada
     * @return
     */
    public static <T> Response<T> success(int code, String message, T dada) {
        return new Response<>(code, message, dada);
    }

    /**
     * Build failure response,Specify code and message
     *
     * @param code
     * @param message
     * @return
     */
    public static <T> Response<T> fail(int code, String message) {
        return new Response<>(code, message, null);
    }

    /**
     * Build failure response
     *
     * @return
     */
    public static <T> Response<T> fail() {
        return new Response<>(ErrorCode.FAIL.getCode(), ErrorCode.FAIL.getMessage(), null);
    }
}
