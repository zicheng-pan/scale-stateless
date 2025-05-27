package com.example.demo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) Jaguar Land Rover Ltd 2024. All rights reserved
 */

@RestController
public class StringutilController {

  private final AtomicInteger requestCount = new AtomicInteger(0);

  @Autowired
  private StringRedisTemplate stringRedisTemplate;


  @GetMapping("/toUpper")
  public String add(@RequestParam String str) throws InterruptedException {
//    引入redis
//    stringRedisTemplate.opsForValue().increment("requests-count");

    String appName = System.getenv("APP_NAME");
    requestCount.incrementAndGet();
    Thread.sleep(2000);
    return appName + ": " + StringUtils.upperCase(str);
  }

  @GetMapping("/reqeusts-count")
  public int getRequestCount() {
//    return Integer.parseInt(stringRedisTemplate.opsForValue().get("requests-count") == null ? "0" :
//        Objects.requireNonNull(stringRedisTemplate.opsForValue().get("requests-count")));

    return this.requestCount.get();
  }

}
