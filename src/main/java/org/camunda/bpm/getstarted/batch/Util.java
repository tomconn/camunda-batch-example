package org.camunda.bpm.getstarted.batch;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Util {

    private final static Logger LOGGER = Logger.getLogger("REQUESTS");

    public void sendMessage(DelegateExecution execution, String messageName) {

        RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
        runtimeService.startProcessInstanceByMessage(messageName);
    }

}