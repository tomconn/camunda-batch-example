package org.camunda.bpm.getstarted.batch;

import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.extension.batch.CustomBatchJobHandler;
import org.springframework.stereotype.Component;

@Component
public class BatchJobHandler extends CustomBatchJobHandler<String> {

    private static final Logger LOGGER = Logger.getLogger(BatchJobHandler.class.getSimpleName());

    public static final String TYPE = "batch-handler";

    @Override
    public void execute(List<String> data, CommandContext commandContext) {

        LOGGER.info("Work on data: " + data.get(0));
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
