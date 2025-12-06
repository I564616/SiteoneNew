package com.siteone.integration.services.ue.impl;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.rest.client.SiteOneRestClient;
import com.siteone.integration.services.ue.SiteOneOrderWebService;
import com.siteone.integration.submitOrder.data.SiteOneWsSubmitOrderRequestData;
import com.siteone.integration.submitOrder.data.SiteOneWsSubmitOrderResponseData;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.internal.model.impl.ItemModelCloneCreator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.util.Config;

public class DefaultSiteOneOrderWebService implements SiteOneOrderWebService {
	private SiteOneRestClient<SiteOneWsSubmitOrderRequestData, SiteOneWsSubmitOrderResponseData> siteOneRestClient;
	private Converter<ConsignmentModel, SiteOneWsSubmitOrderRequestData> siteOneWsSubmitOrderConverter;
	private ItemModelCloneCreator itemModelCloneCreator;
	@Resource
	private ModelService modelService;
	@Resource
	private I18NService i18nService;
	@Resource
	private TypeService typeService;
	@Resource(name = "sessionService")
	private SessionService sessionService;
	
	@Override
	public SiteOneWsSubmitOrderResponseData submitOrder(ConsignmentModel consignmentModel, boolean isNewBoomiEnv)
			throws ResourceAccessException, RestClientException {

		SiteOneWsSubmitOrderRequestData siteOneWsSubmitOrderRequestData = getSiteOneWsSubmitOrderConverter()
				.convert(consignmentModel);
		 if(BooleanUtils.isTrue(isNewBoomiEnv))
		 {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set(SiteoneintegrationConstants.X_API_HEADER, Config.getString(SiteoneintegrationConstants.X_API_KEY_CUSTOMER, null));
			return getSiteOneRestClient().execute(
					Config.getString(SiteoneintegrationConstants.ORDER_SUBMIT_NEW_URL_KEY, StringUtils.EMPTY), HttpMethod.POST,
					siteOneWsSubmitOrderRequestData, SiteOneWsSubmitOrderResponseData.class,
					siteOneWsSubmitOrderRequestData.getCorrelationId(),
					SiteoneintegrationConstants.ORDER_SUBMIT_SERVICE_NAME, httpHeaders);
		 }
		 else
		 {
			 return getSiteOneRestClient().execute(
						Config.getString(SiteoneintegrationConstants.ORDER_SUBMIT_URL_KEY, StringUtils.EMPTY), HttpMethod.POST,
						siteOneWsSubmitOrderRequestData, SiteOneWsSubmitOrderResponseData.class,
						siteOneWsSubmitOrderRequestData.getCorrelationId(),
						SiteoneintegrationConstants.ORDER_SUBMIT_SERVICE_NAME, null);
		 }
	}

	private void createOrderBackUp(OrderModel orderModel) {
		itemModelCloneCreator = new ItemModelCloneCreator(modelService, i18nService, typeService);
		final OrderModel orderClone = (OrderModel) itemModelCloneCreator.copy(orderModel);

		orderClone.setCode(orderClone.getCode() + "_bak");
		orderClone.setIsDuplicate(true);
		// OrderAuditLogModel auditLogModel= (OrderAuditLogModel)orderClone;
		
		modelService.save(orderClone);
	}

	public Converter<ConsignmentModel, SiteOneWsSubmitOrderRequestData> getSiteOneWsSubmitOrderConverter() {
		return siteOneWsSubmitOrderConverter;
	}

	public void setSiteOneWsSubmitOrderConverter(
			Converter<ConsignmentModel, SiteOneWsSubmitOrderRequestData> siteOneWsSubmitOrderConverter) {
		this.siteOneWsSubmitOrderConverter = siteOneWsSubmitOrderConverter;
	}

	public SiteOneRestClient<SiteOneWsSubmitOrderRequestData, SiteOneWsSubmitOrderResponseData> getSiteOneRestClient() {
		return siteOneRestClient;
	}

	public void setSiteOneRestClient(
			SiteOneRestClient<SiteOneWsSubmitOrderRequestData, SiteOneWsSubmitOrderResponseData> siteOneRestClient) {
		this.siteOneRestClient = siteOneRestClient;
	}

}