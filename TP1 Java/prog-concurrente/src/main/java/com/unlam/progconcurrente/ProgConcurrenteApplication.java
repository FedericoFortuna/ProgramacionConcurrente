package com.unlam.progconcurrente;

import com.unlam.progconcurrente.tasks.ProcessHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ProgConcurrenteApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ProgConcurrenteApplication.class, args);
        ProcessHandler ph = context.getBean(ProcessHandler.class);
        try {
            ph.main();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

