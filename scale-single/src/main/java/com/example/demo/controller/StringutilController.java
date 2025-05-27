package com.example.demo.controller;

import org.apache.commons.lang3.StringUtils;
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

  @GetMapping("/toUpper")
  public String add(@RequestParam String str) throws InterruptedException {
    requestCount.incrementAndGet();
    Thread.sleep(2000);
    return StringUtils.upperCase(str);
  }

  @GetMapping("/reqeusts-count")
  public int getRequestCount() {
    return this.requestCount.get();
  }

}
