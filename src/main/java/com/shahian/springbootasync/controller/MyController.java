package com.shahian.springbootasync.controller;

import com.shahian.springbootasync.service.MyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class MyController {
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/v1/start-task")
    public String startLongRunningTask() throws ExecutionException, InterruptedException {
        myService.method1();
        myService.method2();
        CompletableFuture<String> stringCompletableFuture = myService.executeLongTask();
        //stringCompletableFuture.get();
        myService.method5();
        return "Task started. Check the console for the result after a few seconds.";
    }
    @GetMapping("/v1/start-task1")
    public String startLongRunningTask1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = myService.executeLongTask1();
        return "Task started. Check the console for the result after a few seconds.";
    }
}
