package com.essexboy;

import lombok.Getter;

/**
 * Entry point
 */
@Getter
public class App {
    private RelayCron relayCron;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        new App().run();
    }

    /**
     * load the default config
     * @throws Exception
     */
    public void load() throws Exception {
        relayCron = new RelayCron(getClass().getResourceAsStream("/testConfig.yaml"), new RelayHttpClientImpl());
    }

    /**
     * run the app indefinately
     */
    public void run() {
        relayCron.cron();
    }
}
