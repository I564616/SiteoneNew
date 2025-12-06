/**
 *
 */
package com.siteone.core.externaltax.impl;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.externaltax.ExternalTaxDocument;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.TaxValue;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.externaltax.SiteOneCalculateExternalTaxesStrategy;
import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.integration.services.vertexclient.TaxCalculationWebService;
import com.siteone.integration.webserviceclient.vertexclient.LineItemQSOType;
import com.siteone.integration.webserviceclient.vertexclient.VertexEnvelope;
import com.siteone.integration.webserviceclient.vertexclient.TaxesType;
import com.siteone.integration.constants.SiteoneintegrationConstants;

import java.util.Map;
import java.util.HashMap;

/**
 * @author 1099417
 *
 */
public class DefaultSiteOneCalculateExternalTaxesStrategy implements SiteOneCalculateExternalTaxesStrategy
{

	private static final String USD = "USD";
	private static final Double ZERO = Double.valueOf(0);
	private static final Logger LOG = Logger.getLogger(DefaultSiteOneCalculateExternalTaxesStrategy.class);
		
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;
	@Resource(name = "sessionService")
	private SessionService sessionService;

	private TaxCalculationWebService taxCalculationWebService;

	/**
	 * Real time call to make tax calculation to Vertex
	 *
	 * @param abstractOrder
	 * @return ExternalTaxDocument
	 */
	@Override
	public ExternalTaxDocument calculateExternalTaxes(final AbstractOrderModel abstractOrder)
	{
		final ExternalTaxDocument externalDocument = new ExternalTaxDocument();

		if (abstractOrder == null)
		{
			throw new IllegalStateException("Order is null. Cannot apply external tax to it.");
		}

		Double taxValue;

		if (null != abstractOrder.getDeliveryAddress() || 	(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("SplitMixedPickupBranches",
 				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId()))
				|| (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",
				((PointOfServiceData) getSessionService().getAttribute("sessionStore")).getStoreId())))
		{

			final VertexEnvelope taxResponse = taxCalculationWebService.calculateTax(abstractOrder);

			taxValue = ZERO;

			if (null != taxResponse && null != taxResponse.getQuotationResponse()
					&& null != taxResponse.getQuotationResponse().getTotalTax())
			{

				LOG.info("Received tax information for order :" + abstractOrder.getCode() + " Tax :"
						+ taxResponse.getQuotationResponse().getTotalTax());
				taxValue = Double.valueOf(taxResponse.getQuotationResponse().getTotalTax().doubleValue());

				PointOfServiceData sessionStore = (PointOfServiceData) getSessionService().getAttribute("sessionStore");
                String storeId = "null";
                if (sessionStore != null) 
				{
                storeId = sessionStore.getStoreId();
                }
				LOG.error("Order Code=" + abstractOrder.getCode()
			    + ", DeliveryAddress=" + (abstractOrder.getDeliveryAddress() != null ? "Present" : "Absent")
			    + ", StoreID=" + storeId
			    + ", TotalTax=" + taxValue);

			if (taxValue != null && taxValue <= 0) 
			{
			  LOG.error("Incorrect tax for OrderCode=" + abstractOrder.getCode()
			      + ", returnedTax=" + taxValue);
			}

			}
			
			if (null != taxResponse && null != taxResponse.getQuotationResponse() && null != taxResponse.getQuotationResponse().getSeller()
					&& taxResponse.getQuotationResponse().getSeller().getDivision().equalsIgnoreCase(SiteoneintegrationConstants.TAX_REQ_DIVISION_CA))
			{
				double gst = SiteoneCoreConstants.TAX_ZERO;
				double hst = SiteoneCoreConstants.TAX_ZERO;
				double pst = SiteoneCoreConstants.TAX_ZERO;
				double other = SiteoneCoreConstants.TAX_ZERO;
				Map<String, Double> tax = new HashMap();
				if (CollectionUtils.isNotEmpty(taxResponse.getQuotationResponse().getLineItem()))
				{
					for (final LineItemQSOType entryItem : taxResponse.getQuotationResponse().getLineItem())
					{
						for (final TaxesType taxType : entryItem.getTaxes())
						{
							if(null != taxType.getImposition()) {
								if(taxType.getImposition().getValue().equalsIgnoreCase(SiteoneCoreConstants.GST_HST)) {
	   							if(taxType.getEffectiveRate().doubleValue() <= 0.5) {
	   								gst = gst + taxType.getCalculatedTax().doubleValue();
	   							}
	   							else {
	   								hst = hst + taxType.getCalculatedTax().doubleValue();
	   							}
	   						}
	   						else if (taxType.getImposition().getValue().contains(SiteoneCoreConstants.PST) || taxType.getImposition().getValue().contains("Provincial Sales Tax")) {
	   							pst = pst + taxType.getCalculatedTax().doubleValue();
	   						}
	   						else {
	   							if(null != tax) {
	   								if(tax.containsKey(taxType.getImposition().getValue())) {
	   									tax.put(taxType.getImposition().getValue(), (tax.get(taxType.getImposition().getValue())+taxType.getCalculatedTax().doubleValue()));
	   								}else {
	   									tax.put(taxType.getImposition().getValue(),taxType.getCalculatedTax().doubleValue());
	   								}
	   							}else {
	   								tax.put(taxType.getImposition().getValue(),taxType.getCalculatedTax().doubleValue());
	   							}
	   						}
							}
   					}
					}
				}
				
				if(gst > SiteoneCoreConstants.TAX_ZERO) {
					tax.put(SiteoneCoreConstants.GST,gst);
				}
				if(hst > SiteoneCoreConstants.TAX_ZERO) {
					tax.put(SiteoneCoreConstants.HST,hst);
				}
				if(pst > SiteoneCoreConstants.TAX_ZERO) {
					tax.put(SiteoneCoreConstants.PST,pst);
				}
				getSessionService().setAttribute("taxCA",tax);
			}
		}
		else
		{
			taxValue = ZERO;
		}

		
		TaxValue tax;
		if (null != abstractOrder.getCurrency() && null != abstractOrder.getCurrency().getIsocode())
		{
			tax = new TaxValue("TAX_" + abstractOrder.getCode(), taxValue, true, taxValue, abstractOrder.getCurrency().getIsocode());
			externalDocument.setTaxesForOrderEntry(1, tax);
		}
		else
		{
			tax = new TaxValue("TAX_" + abstractOrder.getCode(), taxValue, true, taxValue, USD);
			externalDocument.setTaxesForOrderEntry(1, tax);
		}

		return externalDocument;
	}

	/**
	 * @return the taxCalculationWebService
	 */
	public TaxCalculationWebService getTaxCalculationWebService()
	{
		return taxCalculationWebService;
	}

	/**
	 * @param taxCalculationWebService
	 *           the taxCalculationWebService to set
	 */
	public void setTaxCalculationWebService(final TaxCalculationWebService taxCalculationWebService)
	{
		this.taxCalculationWebService = taxCalculationWebService;
	}

	@Override
	public ExternalTaxDocument calculateExternalOrderEntryTaxes(final AbstractOrderModel abstractOrder)
	{
		final ExternalTaxDocument externalDocument = new ExternalTaxDocument();

		if (abstractOrder == null)
		{
			throw new IllegalStateException("Order is null. Cannot apply external tax to it.");
		}
		double entryTax = 0;
		final VertexEnvelope taxResponse = taxCalculationWebService.calculateTax(abstractOrder);

		if (null != taxResponse && null != taxResponse.getQuotationResponse())
		{
			if (CollectionUtils.isNotEmpty(taxResponse.getQuotationResponse().getLineItem()))
			{
				for (final LineItemQSOType entryItem : taxResponse.getQuotationResponse().getLineItem())
				{
					for (final AbstractOrderEntryModel entries : abstractOrder.getEntries())
					{
						if (entryItem.getVendorSKU().equalsIgnoreCase(entries.getProduct().getCode()))
						{
							TaxValue tax;
							entryTax = entryItem.getTotalTax().doubleValue();
							if (null != abstractOrder.getCurrency() && null != abstractOrder.getCurrency().getIsocode())
							{
								tax = new TaxValue("TAX_" + entries.getProduct().getCode(), entryTax, true, entryTax,
										abstractOrder.getCurrency().getIsocode());
								externalDocument.setTaxesForOrderEntry(entries.getEntryNumber(), tax);
							}
							else
							{
								tax = new TaxValue("TAX_" + entries.getProduct().getCode(), entryTax, true, entryTax, USD);
								externalDocument.setTaxesForOrderEntry(entries.getEntryNumber(), tax);
							}
						}
					}
				}
			}

		}

		return externalDocument;
	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(SessionService sessionService)
	{
		this.sessionService = sessionService;
	}


}






