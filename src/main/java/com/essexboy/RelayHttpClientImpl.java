package com.essexboy;

import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

/**
 * Http client
 */
@Setter
public class RelayHttpClientImpl implements RelayHttpClient {

    private int timeout = 10;
    final static Logger LOGGER = LoggerFactory.getLogger(RelayHttpClientImpl.class);

    private CloseableHttpClient getHttpClient() throws Exception {
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(new TrustAllStrategy())
                .build();

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

        return HttpClients.custom()
                .setSSLHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .setSSLContext(sslContext)
                .setDefaultRequestConfig(config)
                .build();
    }

    @Override
    public String execute(HttpEndPoint httpEndPoint, String body) throws Exception {
        final CloseableHttpClient httpClient = getHttpClient();
        try {
            LOGGER.debug("execute {}, body {}", httpEndPoint, body);
            HttpRequestBase request;
            switch (httpEndPoint.getMethod()) {
                case "GET":
                    request = new HttpGet(httpEndPoint.getUrl());
                    break;
                case "POST":
                    request = new HttpPost(httpEndPoint.getUrl());
                    ((HttpPost) request).setEntity(new StringEntity(body));
                    break;
                default:
                    throw new RuntimeException("invalid method : " + httpEndPoint.getMethod());
            }
            if (httpEndPoint.getHeaders() != null) {
                for (Header header : httpEndPoint.getHeaders()) {
                    request.addHeader(header);
                }
            }
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() > 299) {
                throw new RuntimeException("error " + httpEndPoint + " returned " + response.getStatusLine().getStatusCode());
            }
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpClient.close();
        }
    }

    @Override
    public String execute(HttpEndPoint httpEndPoint) throws Exception {
        return execute(httpEndPoint, null);
    }
}
