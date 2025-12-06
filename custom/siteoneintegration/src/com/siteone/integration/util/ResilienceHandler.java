package com.siteone.integration.util;

import java.time.Duration;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

import com.siteone.integration.price.data.SiteOneWsPriceRequestData;
import com.siteone.integration.price.data.SiteOneWsPriceResponseData;
import com.siteone.integration.services.ue.SiteOnePriceWebService;

import de.hybris.platform.core.Registry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;

/**
 * @author LR03818
 *
 */
public class ResilienceHandler {

	private static final Logger LOG = Logger.getLogger(ResilienceHandler.class.getName());

	private static CircuitBreaker circuitBreaker;

	/**
	 * Creating a circuit breaker using custom configuration
	 *
	 * @return
	 */
	static {
		CircuitBreakerConfig circuitBreakerConfig =
				CircuitBreakerConfig.custom()
						.slidingWindowType(SlidingWindowType.COUNT_BASED)
						.minimumNumberOfCalls(5)
						.slidingWindowSize(5)
						.failureRateThreshold(60.0f)
						.waitDurationInOpenState(Duration.ofMinutes(5))
						.permittedNumberOfCallsInHalfOpenState(5)
						.build();
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
		circuitBreaker = circuitBreakerRegistry.circuitBreaker("siteOnePriceWebService");
	}

	/**
	 * This method returns a desired price value when the Cloud Pricing Service is up.
	 * If the Cloud Pricing Service is down, it applies a CircuitBreaker fault tolerance patterns. When all the attempts
	 * are exhausted it calls a fallback method to recover from failure and offers the price value from on premises UE pricing service.
	 *
	 * @param siteOneWsPriceRequestData, apiUrl, httpHeaders
	 * @return
	 */
	public static SiteOneWsPriceResponseData applyResiliencePatterns(SiteOneWsPriceRequestData siteOneWsPriceRequestData, String apiUrl, HttpHeaders httpHeaders) {

		final SiteOnePriceWebService siteOnePriceWebService=(SiteOnePriceWebService) Registry.getApplicationContext().getBean("siteOnePriceWebService");

		LOG.error("Inside Circuit Breaker:::" + circuitBreaker.getState());

		Supplier<SiteOneWsPriceResponseData> priceSupplier = () -> siteOnePriceWebService.callPricingService(siteOneWsPriceRequestData, apiUrl, httpHeaders);
		Supplier<SiteOneWsPriceResponseData> decorated = Decorators
				.ofSupplier(priceSupplier)
				.withCircuitBreaker(circuitBreaker)
				.withFallback(throwable -> siteOnePriceWebService.callFallbackPricingService(siteOneWsPriceRequestData))
				.decorate();

		return decorated.get();
	}

}