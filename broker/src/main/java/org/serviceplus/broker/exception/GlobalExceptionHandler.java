/*
 * Copyright 2024 service plus open source organization.
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

import lombok.extern.slf4j.Slf4j;
import org.serviceplus.broker.model.Response;
import org.serviceplus.broker.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author lixiaoshuang
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理特定异常
    @ExceptionHandler(BrokerException.class)
    public Response<String> handleSpecificException(BrokerException ex) {
        return ResponseUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception.class)
    public Response<String> handleAllUncaughtException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
