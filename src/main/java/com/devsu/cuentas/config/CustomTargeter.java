package com.devsu.cuentas.config;

import feign.Feign;
import feign.Target;
import org.springframework.cloud.openfeign.FeignClientFactory;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;

import org.springframework.cloud.openfeign.Targeter;

public class CustomTargeter implements Targeter {

    @Override
    public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignClientFactory context, Target.HardCodedTarget<T> target) {
        return feign.target(target);
    }
}
