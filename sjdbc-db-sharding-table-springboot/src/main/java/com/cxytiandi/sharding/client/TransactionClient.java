package com.cxytiandi.sharding.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "sharding")
public interface TransactionClient {

    @RequestMapping(method = RequestMethod.GET, value = "/addOrder/tra", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Void addorder();
}
