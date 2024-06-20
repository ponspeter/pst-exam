package com.pst.exam.extension.container;

import org.testcontainers.containers.PostgreSQLContainer;

public enum PostgresTestContainer {
    INSTANCE;
    public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "password";
    public static final int IN_CONTAINER_PORT = 5432;
    public static final String exam_APP_USER = "exam_app_user";
    public static final String exam_APP_USER_PASSWORD = "password";
    private PostgreSQLContainer postgreSQLContainer;

    PostgresTestContainer() {
        postgreSQLContainer  = new PostgreSQLContainer<>("postgres:13.4")
                .withDatabaseName("exam")
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withExposedPorts(IN_CONTAINER_PORT)
                .withInitScript("pre-liquibase-init.sql");
        postgreSQLContainer.start();
        System.setProperty(SPRING_DATASOURCE_URL, postgreSQLContainer.getJdbcUrl());
        System.setProperty(SPRING_DATASOURCE_USERNAME, exam_APP_USER);
        System.setProperty(SPRING_DATASOURCE_PASSWORD, exam_APP_USER_PASSWORD);
        org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PostgresTestContainer.class);
        log.info("postgres test container exposed at " + System.getProperty(SPRING_DATASOURCE_URL) + ", username=" + System.getProperty(SPRING_DATASOURCE_USERNAME) + ",password=", System.getProperty(PASSWORD));
    }

    public static PostgreSQLContainer initialize(){
        return INSTANCE.postgreSQLContainer;
    }
}
