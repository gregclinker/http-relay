package com.essexboy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {

    @Test
    public void test1() throws Exception {
        final App app = new App();
        app.load();
        assertNotNull(app.getRelayCron());
    }
}
