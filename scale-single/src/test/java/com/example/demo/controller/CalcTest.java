package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (c) Jaguar Land Rover Ltd 2024. All rights reserved
 */
class CalcTest {

  AtomicInteger errorCount = new AtomicInteger(0);
  AtomicInteger successCount = new AtomicInteger(0);
  int requestsize = 400;
  ExecutorService executorService = Executors.newFixedThreadPool(requestsize);
  final String URL = "http://localhost:8081/toUpper?str=abc123";


  CountDownLatch latch = new CountDownLatch(1);

  @Test
  void evaluate() {

    HttpClient httpClient = HttpClient.newHttpClient();

    for (int j = 0; j < requestsize; j++) {
      executorService.submit(() -> {
        try {
          HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(URL))
              .header("User-Agent", "Java HttpClient")
              .build();
          latch.await();
          HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
          System.out.println("Thread " + Thread.currentThread().getName() + " - Response Body: " + response.body() + "successCount:" + successCount.incrementAndGet());
        } catch (IOException | InterruptedException e) {
          errorCount.incrementAndGet();
          System.out.println("出错了：" + errorCount.get() + "个");
        }
      });
    }
    latch.countDown();
    try {
      if (!executorService.awaitTermination(6000, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

}
