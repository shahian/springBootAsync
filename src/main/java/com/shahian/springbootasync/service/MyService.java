package com.shahian.springbootasync.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class MyService {
    private final Executor taskExecutor;

    public MyService(@Qualifier("taskExecutor") Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    //@Async
    @Async("taskExecutor")
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

    @Async
    public CompletableFuture<String> executeLongTask1() {
        method1();
        method2();
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            method3();
            return "complete process";
        });
        return completableFuture;
    }

    private void method3() {
        // Let's simulate a long-running task
        //Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            System.out.println("running loop " + i);

        }
        System.out.println("Long running task completed111111");
    }
}
