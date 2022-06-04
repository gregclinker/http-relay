package com.essexboy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that does all the heavy lifting
 */
@Getter
public class RelayCron extends TimerTask {

    final static Logger LOGGER = LoggerFactory.getLogger(RelayCron.class);

    private RelayHttpClient relayHttpClient;
    private int count = 0;
    private Timer timer = new Timer();
    private RelayConfig relayConfig;
    private RelayHttpClientImpl relayHttpClientImpl;
    private String inputFile;

    /**
     * load the conifg & the http client
     * @param inputStream config
     * @param relayHttpClient http client
     * @throws Exception
     */
    public RelayCron(InputStream inputStream, RelayHttpClient relayHttpClient) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        relayConfig = mapper.readValue(inputStream, RelayConfig.class);
        this.relayHttpClient = relayHttpClient;
    }

    /**
     * start the cron
     */
    public void cron() {
        timer.scheduleAtFixedRate(this, 0, relayConfig.getInterval() * 1000);
    }

    /**
     * stop the cron
     */
    public void stop() {
        timer.cancel();
    }

    @Override
    public void run() {
        count++;
        LOGGER.debug("run");
        try {
            final String sourceResponseBody = relayHttpClient.execute(relayConfig.getSource());
            final List<String> sinkRequestBodys = toSinkBodys(new SourceToSinkTransformer().transform(sourceResponseBody));
            for (String sinkRequestBody : sinkRequestBodys) {
                relayHttpClient.execute(relayConfig.getSink(), sinkRequestBody);
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

    private List<String> toSinkBodys(List<String> transforms) throws JsonProcessingException {
        List<String> sinkBodys = new ArrayList<>();
        for (String transform : transforms) {
            sinkBodys.add(toSinkBody(transform, relayConfig));
        }
        return sinkBodys;
    }

    private String toSinkBody(String string, RelayConfig relayConfig) throws JsonProcessingException {
        SinkItem sinkItem = new SinkItem();
        sinkItem.setNamespace(relayConfig.getNamespace());
        sinkItem.setValue(string);
        return new ObjectMapper().writeValueAsString(sinkItem);
    }
}
