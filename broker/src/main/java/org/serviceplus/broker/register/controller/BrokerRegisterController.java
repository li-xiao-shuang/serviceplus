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
package org.serviceplus.broker.register.controller;

import org.apache.commons.lang3.StringUtils;
import org.serviceplus.broker.enums.ErrorCode;
import org.serviceplus.broker.model.*;
import org.serviceplus.broker.model.vo.BrokerApplicationVO;
import org.serviceplus.broker.model.vo.BrokerServiceVO;
import org.serviceplus.broker.model.vo.ParamInfoVO;
import org.serviceplus.broker.register.service.RegisterUnifiedProcessingCenter;
import org.serviceplus.broker.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping(path = "register/v1")
public class BrokerRegisterController {

    @GetMapping(path = "application/list")
    public Response<List<BrokerApplicationVO>> applicationList() {
        List<BrokerApplicationVO> result = RegisterUnifiedProcessingCenter.getApplicationList().stream()
                .map(brokerApplication -> {
                    BrokerApplicationVO brokerApplicationVo = new BrokerApplicationVO();
                    brokerApplicationVo.setApplicationName(brokerApplication.getApplicationName());
                    brokerApplicationVo.setApplicationPort(brokerApplication.getApplicationPort());
                    brokerApplicationVo.setApplicationType(brokerApplication.getApplicationType());
                    brokerApplicationVo.setIpList(RegisterUnifiedProcessingCenter.getIpList(brokerApplication.getApplicationName()));
                    return brokerApplicationVo;
                })
                .collect(Collectors.toList());
        return ResponseUtil.success(result);
    }

    @GetMapping(path = "service/list")
    public Response<List<BrokerServiceVO>> serviceList(@RequestParam("applicationName") String applicationName,
                                                       @RequestParam("applicationIp") String applicationIp,
                                                       @RequestParam("applicationPort") String applicationPort) {
        if (StringUtils.isBlank(applicationName) || StringUtils.isBlank(applicationIp) || StringUtils.isBlank(applicationPort)) {
            return ResponseUtil.fail(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMessage());
        }
        List<BrokerService> serviceList = RegisterUnifiedProcessingCenter.getServiceList(applicationName, applicationIp, applicationPort);
        List<BrokerServiceVO> result = serviceList.stream().map(brokerService -> {
            BrokerServiceVO brokerServiceVo = new BrokerServiceVO();
            brokerServiceVo.setClassName(brokerService.getClassName());
            brokerServiceVo.setSimpleClassName(brokerService.getSimpleClassName());
            brokerServiceVo.setMethodName(brokerService.getMethodName());
            brokerServiceVo.setMethodDesc(brokerService.getMethodDesc());
            List<ParamInfoVO> paramInfoVOS = IntStream.range(0, brokerService.getParamNames().size())
                    .mapToObj(i -> {
                        ParamInfoVO paramInfoVo = new ParamInfoVO();
                        paramInfoVo.setParamName(StringUtils.defaultIfBlank(brokerService.getParamDesc().get(i),
                                brokerService.getParamNames().get(i)));
                        paramInfoVo.setParamType(brokerService.getParamTypes().get(i));
                        return paramInfoVo;
                    }).collect(Collectors.toList());
            brokerServiceVo.setParamInfos(paramInfoVOS);
            brokerServiceVo.setReturnName(brokerService.getReturnName());
            brokerServiceVo.setReturnType(brokerService.getReturnType());
            return brokerServiceVo;
        }).collect(Collectors.toList());
        return ResponseUtil.success(result);
    }

    @PostMapping(path = "service/invoke")
    public Response<String> invokeService() {
//        Object result = RegisterUnifiedProcessingCenter.invokeService(brokerServiceVO);
        return ResponseUtil.success("invoke success");
    }
}
