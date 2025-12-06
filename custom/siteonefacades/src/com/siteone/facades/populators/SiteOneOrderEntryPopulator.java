/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.order.converters.populator.OrderEntryPopulator;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.BooleanUtils;


/**
 * @author 1219341
 *
 */
public class SiteOneOrderEntryPopulator extends OrderEntryPopulator
{

	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);
	private SessionService sessionService;
	private Converter<ProductModel, ProductData> siteOneOrderEntriesProductConverter;

	/**
	 * @return the siteOneOrderEntriesProductConverter
	 */
	public Converter<ProductModel, ProductData> getSiteOneOrderEntriesProductConverter()
	{
		return siteOneOrderEntriesProductConverter;
	}

	/**
	 * @param siteOneOrderEntriesProductConverter
	 *           the siteOneOrderEntriesProductConverter to set
	 */
	public void setSiteOneOrderEntriesProductConverter(
			final Converter<ProductModel, ProductData> siteOneOrderEntriesProductConverter)
	{
		this.siteOneOrderEntriesProductConverter = siteOneOrderEntriesProductConverter;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	@Override
	protected void addProduct(final AbstractOrderEntryModel orderEntry, final OrderEntryData entry)
	{
		if (null != orderEntry.getProduct() && orderEntry.getProduct().getCode().equals("9999999"))
		{
			final ProductData productData = new ProductData();
			productData.setCode(orderEntry.getStoreProductCode());
			productData.setItemNumber(orderEntry.getStoreProductItemNumber());
			productData.setName(orderEntry.getStoreProductDesc());
			productData.setAvailableStatus(Boolean.FALSE);
			entry.setProduct(productData);
		}
		else
		{
			getSessionService().setAttribute("isSkipRetailAPI", true);
			entry.setProduct(getSiteOneOrderEntriesProductConverter().convert(orderEntry.getProduct()));
			getSessionService().setAttribute("isSkipRetailAPI", false);
		}
	}

	@Override
	public void populate(final AbstractOrderEntryModel source, final OrderEntryData target)
	{
		if (null != source)
		{

			super.populate(source, target);

			if (null != source.getQuantityText())
			{

				target.setQuantityText(source.getQuantityText());
			}
			if (null != source.getDiscountAmount())
			{
				target.setDiscountAmount(createPrice(source, source.getDiscountAmount()));
			}

			if (null != source.getActualItemCost())
			{
				target.setActualItemCost(createPrice(source, source.getActualItemCost()));
			}
			if (null != source.getCouponDescription())
			{
				target.setCouponDescription(source.getCouponDescription());
			}
			if (null != source.getListPrice())
			{
				final double listPrice = source.getListPrice().doubleValue();
				BigDecimal lPrice = new BigDecimal(listPrice);
				lPrice = lPrice.setScale(CURRENCY_UNIT_PRICE_DIGITS, BigDecimal.ROUND_HALF_UP);
				target.setListPrice(lPrice);
			}
			if (null != source.getIsCustomerPrice())
			{
				target.setIsCustomerPrice(source.getIsCustomerPrice());
			}

			if (null != source.getInventoryUOM())
			{
				target.setUomMeasure(source.getInventoryUOM().getInventoryUPCDesc());
				target.setUomId(source.getInventoryUOM().getInventoryUPCID());
				target.setUomMultiplier(source.getInventoryUOM().getInventoryMultiplier());
			}
			else if (null == source.getInventoryUOM() && source.getProduct()!=null &&
					source.getProduct().getInventoryUOM()!=null &&
					source.getProduct().getInventoryUOM().getInventoryUOMDesc()!=null)
				
			{
				target.setUomMeasure(source.getProduct().getInventoryUOM().getInventoryUOMDesc());
			}
			
			if (null != source.getUomPrice())
			{
				final double uomPrice = source.getUomPrice().doubleValue();
				BigDecimal uPrice = new BigDecimal(uomPrice);
				uPrice = uPrice.setScale(CURRENCY_UNIT_PRICE_DIGITS, BigDecimal.ROUND_HALF_UP);
				target.setUomPrice(uPrice);
			}
			 if(null != source.getProduct().getCode()) {
					target.setStoreProductCode(source.getProduct().getCode());
				}
				if(null != source.getProduct().getItemNumber()) {
					target.setStoreProductItemNumber(source.getProduct().getItemNumber());
				}
				if(null != source.getProduct().getProductShortDesc()) {
					target.setStoreProductDesc(source.getProduct().getProductShortDesc());
				}
		}

	}
}