package com.essexboy;

public interface RelayHttpClient {
    String execute(HttpEndPoint httpEndPoint, String body) throws Exception;
    String execute(HttpEndPoint httpEndPoint) throws Exception;
}
