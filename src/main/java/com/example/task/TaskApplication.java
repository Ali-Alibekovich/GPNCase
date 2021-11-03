package com.example.task;

import com.example.task.hazelcast.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TaskApplication {

    public static void main(String[] args) {
        if(args.length>=2){
            Client.HOST = args[0];
            Client.PORT = args[1];
        }
        SpringApplication.run(TaskApplication.class, args);
    }

}
