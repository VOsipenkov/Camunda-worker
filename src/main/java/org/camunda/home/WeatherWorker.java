package org.camunda.home;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;

@Slf4j
public class WeatherWorker {

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
            .baseUrl("http://localhost:8080/engine-rest")
            .asyncResponseTimeout(10000) // long polling timeout
            .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("Degree")
            .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
            .handler((externalTask, externalTaskService) -> {
                // Put your business logic here
                log.info("worker..");
                // Complete the task
                externalTaskService.complete(externalTask);
            })
            .open();
        int i = 1;
    }
}
