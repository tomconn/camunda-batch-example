package org.camunda.bpm.getstarted.batch;

import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.extension.batch.plugin.CustomBatchHandlerPlugin;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.Collections;

@SpringBootApplication
@EnableProcessApplication
public class BatchProcessApplication {

    public static void main(String... args) {
        SpringApplication.run(BatchProcessApplication.class, args);
    }

    @EventListener
    private void processPostDeploy(PostDeployEvent event) {
    }

    @Bean
    public BatchJobHandler simpleCustomBatchJobHandler() {
        return new BatchJobHandler();
    }

    @Bean
    public ProcessEnginePlugin customBatchHandlerPlugin(BatchJobHandler batchJobHandler) {
        return new CustomBatchHandlerPlugin(Collections.singletonList(batchJobHandler));
    }

    @Autowired
    private BatchJobHandler batchJobHandler;

    @EventListener
    public void afterEngineStarted(PostDeployEvent event) {
    }
}