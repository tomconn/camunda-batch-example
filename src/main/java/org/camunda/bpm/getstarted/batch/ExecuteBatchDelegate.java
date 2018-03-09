package org.camunda.bpm.getstarted.batch;

import org.camunda.bpm.extension.batch.CustomBatchBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;
import java.util.List;

@Service("executeBatch")
public class ExecuteBatchDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger(ExecuteBatchDelegate.class.getName());

    @Autowired
    private BatchJobHandler batchJobHandler;

    @Value("${constant.jobsPerSeed}")
    private int jobsPerSeed;

    @Value("${constant.invocationsPerBatchJob}")
    private int invocationsPerBatchJob;

    public void execute(DelegateExecution execution) throws Exception {

        List<String> ids = (List<String>) execution.getVariableLocalTyped("processInstances").getValue();

        LOGGER.info("ExecuteBatchDelegate ids size:" + ids.size());

        // See https://docs.camunda.org/manual/7.8/user-guide/process-engine/batch/
        CustomBatchBuilder<String> builder = CustomBatchBuilder.of(ids)
                //.configuration(event.getProcessEngine().getProcessEngineConfiguration())
                .jobHandler(batchJobHandler)
                .jobsPerSeed(jobsPerSeed) // WORKERS: Seed job: creates all batch execution jobs required to complete the batch
                .invocationsPerBatchJob(invocationsPerBatchJob) // number of items in a batch
                ;

        builder.create();
    }

}
