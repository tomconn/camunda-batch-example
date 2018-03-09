package org.camunda.bpm.getstarted.batch;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@Service("getBatch")
public class GetBatchDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(GetBatchDelegate.class.getName());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Value("${constant.batchSize}")
    private int batchSize;

    public void execute(DelegateExecution execution) throws Exception {

        LOGGER.info("GetBatchDelegate batchSize="+batchSize);
        String key = (String) execution.getVariable("definitionKey");

        // query for latest process definition with given name
        ProcessDefinition myProcessDefinition =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey(key)
                        .latestVersion()
                        .singleResult();

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionId(myProcessDefinition.getId())
                        //.variableValueEquals("type", "somefilter")
                        .active() // we only want the active (not completed) process instances
                        .list();

        List<String> ids = new ArrayList<>();

        int min = Math.min(batchSize, processInstances.size());
        IntStream.range(0, min)
                .forEach(idx -> {
                    ids.add(processInstances.get(idx).getId());
                    }
                );

        ObjectValue arrayValue = Variables.objectValue(ids)
                .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
                .create();

        execution.setVariable("processInstances", arrayValue);
    }

}
