package com.example.demo.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) Jaguar Land Rover Ltd 2024. All rights reserved
 */
class CalcTest {

  AtomicInteger errorCount = new AtomicInteger(0);
  AtomicInteger successCount = new AtomicInteger(0);
  // request count
  int requestsize = 100;
  ExecutorService executorService = Executors.newFixedThreadPool(requestsize);
  //   NGINX URL
  final String URL = "http://localhost:8889/toUpper?str=abc123";
//  final String URL = "http://localhost:8081/toUpper?str=abc123";


  CountDownLatch latch = new CountDownLatch(1);


  List<Callable<String>> tasks = new ArrayList<>();

  @Test
  @Disabled
  void evaluate() {

    HttpClient httpClient = HttpClient.newHttpClient();

    for (int j = 0; j < requestsize; j++) {
      tasks.add(() -> {
        try {
          HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(URL))
              .header("User-Agent", "Java HttpClient")
              .timeout(Duration.ofSeconds(10))
              .build();
          latch.await();
          HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
          System.out.println("Thread " + Thread.currentThread().getName() + " - Response Body: " + response.statusCode() + " - " + response.body() + "successCount:" + successCount.incrementAndGet());
          return "successCount:" + successCount.get();
        } catch (Exception e) {
          System.out.println(e.getMessage());
//          System.out.println("出错了：" + errorCount.incrementAndGet() + "个");
          return null;
        }
      });
    }
    latch.countDown();
    try {
      long startTime = System.currentTimeMillis();
      List<Future<String>> futures = executorService.invokeAll(tasks);
      for (Future<String> future : futures) {
        future.get();
      }
      long endTime = System.currentTimeMillis();

      System.out.println("总耗时：" + (endTime - startTime) + "ms");
      System.out.println("成功次数：" + successCount.get());
      if (!executorService.awaitTermination(6000, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (Exception e) {
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

}
