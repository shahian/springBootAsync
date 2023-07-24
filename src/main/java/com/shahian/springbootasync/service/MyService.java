package com.shahian.springbootasync.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MyService {

    @Async
    public CompletableFuture<String> executeLongTask() {
        // Let's simulate a long-running task
        //Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            System.out.println("running loop " + i);

        }
        System.out.println("Long running task completed111111");
        // Return a completed future with the result of the operation.
        return CompletableFuture.completedFuture("Long running task completed");
    }
    public void method1() {
        System.out.println("result of method1");
    }

    public void method2() {
        System.out.println("result of method2");

    }

    public void method5() {
        System.out.println("result of method5");

    }
}
