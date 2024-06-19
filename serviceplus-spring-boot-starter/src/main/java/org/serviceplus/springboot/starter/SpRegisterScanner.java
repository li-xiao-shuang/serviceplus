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
package org.serviceplus.springboot.starter;

import org.apache.commons.lang3.StringUtils;
import org.serviceplus.client.ApplicationTypeEnum;
import org.serviceplus.client.ServicePlusFactory;
import org.serviceplus.client.annotation.SpRegister;
import org.serviceplus.client.exception.ClientException;
import org.serviceplus.client.model.SpApplication;
import org.serviceplus.client.model.SpService;
import org.serviceplus.client.register.RegisterClient;
import org.serviceplus.common.constant.EnvironmentConstant;
import org.serviceplus.common.utils.NetworkUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lixiaoshuang
 */
public class SpRegisterScanner {

    private static final String PARAM_DESC_SEPARATOR = ";";
    private final BeanFactory beanFactory;
    private final Environment environment;
    private static volatile SpRegisterScanner instance;

    public static SpRegisterScanner getInstance(BeanFactory beanFactory, Environment environment) {
        if (instance == null) {
            synchronized (SpRegisterScanner.class) {
                if (instance == null) {
                    instance = new SpRegisterScanner(beanFactory, environment);
                }
            }
        }
        return instance;
    }

    private SpRegisterScanner(BeanFactory beanFactory, Environment environment) {
        this.beanFactory = beanFactory;
        this.environment = environment;
        this.scanner();
    }

    /**
     * 扫描被@SpRegister注解的方法，将服务注册到注册中心
     */
    private void scanner() {
        if (beanFactory instanceof ListableBeanFactory) {
            List<SpService> spServices = ((ListableBeanFactory) beanFactory).getBeansWithAnnotation(Component.class).values().stream()
                    .flatMap(bean -> Arrays.stream(bean.getClass().getDeclaredMethods()))
                    .filter(method -> method.isAnnotationPresent(SpRegister.class))
                    .map(this::createSpService)
                    .collect(Collectors.toList());

            SpApplication spApplication = createSpApplication(spServices);
            //todo 修改为读配置文件
            Properties properties = new Properties();
            properties.setProperty("host", "127.0.0.1");
            properties.setProperty("port", "8766");
            RegisterClient registerClient = ServicePlusFactory.createRegisterClient(properties);
            registerClient.register(spApplication);
        }
    }

    /**
     * 创建服务
     *
     * @param method 方法
     * @return 服务
     */
    private SpService createSpService(Method method) {
        SpRegister spRegister = method.getAnnotation(SpRegister.class);
        return SpService.builder()
                .className(method.getDeclaringClass().getName())
                .simpleClassName(method.getDeclaringClass().getSimpleName())
                .methodDesc(spRegister.name())
                .methodName(method.getName())
                .paramNames(Stream.of(method.getParameters()).map(Parameter::getName).collect(Collectors.toList()))
                .paramDesc(Arrays.asList(spRegister.paramDesc().split(PARAM_DESC_SEPARATOR)))
                .paramTypes(Arrays.asList(method.getParameterTypes()))
                .returnNames(method.getReturnType().getName())
                .returnTypes(method.getReturnType())
                .build();
    }

    /**
     * 创建应用
     *
     * @param spServices 服务列表
     * @return 应用
     */
    private SpApplication createSpApplication(List<SpService> spServices) {
        SpApplication spApplication = new SpApplication();
        spApplication.setApplicationName(this.getApplicationName());
        spApplication.setApplicationIp(NetworkUtils.getLocalHostExactAddress());
        spApplication.setApplicationPort(this.getPort());
        spApplication.setApplicationType(ApplicationTypeEnum.JAVA.name());
        spApplication.setSpServices(spServices);
        return spApplication;
    }


    private String getApplicationName() {
        String applicationName = environment.getProperty(EnvironmentConstant.APPLICATION_NAME);
        if (StringUtils.isBlank(applicationName)) {
            throw new ClientException("The application name is not configured.Check the configuration item: spring.application.name");
        }
        return environment.getProperty(EnvironmentConstant.APPLICATION_NAME);
    }

    private String getPort() {
        return environment.getProperty(EnvironmentConstant.SERVER_PORT);
    }
}
