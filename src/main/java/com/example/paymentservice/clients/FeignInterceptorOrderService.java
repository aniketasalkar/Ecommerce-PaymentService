package com.example.paymentservice.clients;

import com.example.paymentservice.utils.TokenValidation;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptorOrderService implements RequestInterceptor {

    @Autowired
    private TokenValidation tokenValidation;

    private String serviceToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        serviceToken = tokenValidation.fetchToken("ORDERMANAGEMENTSERVICE").getServiceToken();
        requestTemplate.header("Service-Token", serviceToken);
    }

}
