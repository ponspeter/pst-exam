package com.pst.exam.extension;

import com.pst.exam.extension.container.PostgresTestContainer;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestContainerExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        PostgresTestContainer.initialize();
    }
}
