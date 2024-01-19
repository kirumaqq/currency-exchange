package io.naoki.currencyspring;

import org.flywaydb.core.Flyway;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class DatabaseCleanupExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestExecution(TestContext testContext) throws Exception {
        Flyway flyway = testContext.getApplicationContext().getBean(Flyway.class);
        flyway.migrate();
    }

    @Override
    public void afterTestExecution(TestContext testContext) throws Exception {
        Flyway flyway = testContext.getApplicationContext().getBean(Flyway.class);
        flyway.clean();
    }
}
