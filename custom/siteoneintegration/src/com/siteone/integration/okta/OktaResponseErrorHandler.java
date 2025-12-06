package com.siteone.integration.okta;

import com.google.common.io.ByteStreams;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by arun on 24/9/17.
 */
public class OktaResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return true;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String res = new String(ByteStreams.toByteArray(response.getBody()));

    }
}
