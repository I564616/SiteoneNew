package com.siteone.integration.algonomy;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import java.util.Collections;

@Configuration
public class AlgonomyConfig {

    @Resource(name = "configurationService")
    private ConfigurationService configurationService;

	/**
	* 1) Bind all props into POJO
	*/
	@Bean
	AlgonomyProperties algonomyProperties() {
        var config = configurationService.getConfiguration();
        AlgonomyProperties p = new AlgonomyProperties();
        p.setUrl(config.getString(AlgonomyProperties.URL_KEY, ""));
        p.setApiKey(config.getString(AlgonomyProperties.API_KEY_KEY, ""));
        p.setApiClientKey(config.getString(AlgonomyProperties.API_CLIENT_KEY_KEY, ""));
        p.setConnectTimeout(config.getInt(AlgonomyProperties.CONNECT_TIMEOUT_KEY, 2000));
        p.setReadTimeout(config.getInt(AlgonomyProperties.READ_TIMEOUT_KEY, 2000));
        p.setMaxRetry(config.getInt(AlgonomyProperties.MAX_RETRY_KEY, 1));
        p.setMaxTotalConnections(config.getInt(AlgonomyProperties.MAX_TOTAL_CONN_KEY, 50));
        p.setMaxPerRoute(config.getInt(AlgonomyProperties.MAX_PER_ROUTE_KEY, 20));
        p.setSamplePlacements(config.getString(AlgonomyProperties.SAMPLE_PLACEMENTS_KEY, ""));
        p.setSampleUserId(config.getString(AlgonomyProperties.SAMPLE_USERID_KEY, ""));
        p.setSampleSessionId(config.getString(AlgonomyProperties.SAMPLE_SESSION_KEY, ""));
        p.setSampleRegionId(config.getString(AlgonomyProperties.SAMPLE_REGION_KEY, ""));
        return p;
    }

	/**
	* 2) Connection pool manager
	*/
	@Bean(destroyMethod = "shutdown")
	PoolingHttpClientConnectionManager poolingConnectionManager(AlgonomyProperties p) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(p.getMaxTotalConnections());
        cm.setDefaultMaxPerRoute(p.getMaxPerRoute());
        return cm;
    }

	/**
	* 3) Pooled Apache HTTP client
	*/
	@Bean
	CloseableHttpClient algonomyHttpClient(PoolingHttpClientConnectionManager cm) {
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

	/**
	* 4) Spring’s factory around that client, with timeouts
	*/
	@Bean
	ClientHttpRequestFactory algonomyRequestFactory(
			CloseableHttpClient httpClient, AlgonomyProperties p) {
        HttpComponentsClientHttpRequestFactory f = new HttpComponentsClientHttpRequestFactory(httpClient);
        f.setConnectTimeout(p.getConnectTimeout());
        // Manual migration to `SocketConfig.Builder.setSoTimeout(Timeout)` necessary; see: https://docs.spring.io/spring-framework/docs/6.0.0/javadoc-api/org/springframework/http/client/HttpComponentsClientHttpRequestFactory.html#setReadTimeout(int)
        f.setReadTimeout(p.getReadTimeout());
        return f;
    }

	/**
	* 5) Reusable RestTemplate
	*/
	@Bean
	RestTemplate algonomyRestTemplate(
			ClientHttpRequestFactory factory, AlgonomyProperties p) {

        RestTemplate tpl = new RestTemplate(factory);
        tpl.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        tpl.setInterceptors(Collections.singletonList(
                new BasicAuthenticationInterceptor(p.getApiClientKey(), "")
        ));
        return tpl;
    }

	/**
	* 6) Shared Jackson mapper
	*/
	@Bean
	ObjectMapper algonomyObjectMapper() {
        return new ObjectMapper();
    }
}