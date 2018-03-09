package org.camunda.bpm.getstarted.batch;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service("markProcessInstances")
public class MarkProcessInstancesDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(MarkProcessInstancesDelegate.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    public void execute(DelegateExecution execution) throws Exception {

        LOGGER.info("ExecuteBatchDelegate entry");
        // All ids
        List<String> ids = (List<String>) execution.getVariableLocalTyped("processInstances").getValue();

        LOGGER.info("ExecuteBatchDelegate ids size:" + ids.size());
        ids.forEach(pid -> {
            //LOGGER.info("Correlate pid=" + pid);
            runtimeService.createMessageCorrelation("Message_Order_Received").processInstanceId(pid).correlate();
        });
    }

}
