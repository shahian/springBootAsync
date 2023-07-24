# springBootAsync

Spring provides a feature to run a long-running process in a separate thread. This feature is helpful when scaling services. By using the @Async and @EnableAsync annotations, we can run the run expensive jobs in the background and wait for the results by using Java’s CompletableFuture interface.

### Asynchronous programming
Asynchronous programming is a multithreaded model that’s applied to networking and communications. Asynchronous is a non-blocking architecture, which means it doesn’t block further execution while one or more operations are in progress. 

In simple terms, this means that multiple related operations can run concurrently without waiting for other tasks to complete. 

One example of asynchronous programming  is texting. Texting is an asynchronous communication method. One person can send a text message, and the recipient can respond at their leisure. In the meantime, the sender may do other things while waiting for a response.

### Synchronous programming
Synchronous is a blocking architecture and is best for programming reactive systems. As a single-thread model, it follows a strict set of sequences, which means that operations are performed one at a time, in perfect order.

While one operation is being performed, other operations’ instructions are blocked. The completion of the first task triggers the next, and so on.

To illustrate how synchronous programming works, think of a telephone conversation. While one person speaks, the other listens. When the first person finishes, the second tends to respond immediately.


In most applications, multithreading supports parallel processing. This allows us to make use of the CPU's processing capabilities.

We can handle our own threads by utilizing @Async annotation in accordance with needs as each application by default has a different number of threads for a variety of tasks.


>  The differences between asynchronous and synchronous include:
* Async is multi-thread, which means operations or programs can run in parallel.
* Sync is single-thread, so only one operation or program will run at a time.
* Async is non-blocking, which means it will send multiple requests to a server.
* Sync is blocking — it will only send the server one request at a time and will wait for that request to be answered by the server.
* Async increases throughput because multiple operations can run at the same time.
* Sync is slower and more methodical.

Sometimes, depending on the complexity of your application or certain requirements, you might need to customize the behavior of the thread pool used by your CompletableFuture instances.

By default, CompletableFuture uses a static ForkJoinPool.commonPool(). If no threads are available in the pool, tasks will wait until one becomes available. This default might not be ideal in all cases.

can customize your executor configuration by creating a new class annotated with @Configuration and defining a TaskExecutor bean inside of it:

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskConfig {

    @Bean("taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
```

 I have set some basic properties for the executor, such as the core and max pool size. You can adjust these values based on your needs. The thread name prefix is just a nice-to-have that can help you distinguish the threads if you need to debug.

Now, you can inject this executor into your service and use it with the @Async annotation to specify that this executor should be used:

```
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
    
    // Rest of the methods...
}

```
