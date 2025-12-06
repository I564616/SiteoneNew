/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import jakarta.annotation.Resource;

import com.siteone.core.model.SiteOneInvoiceEntryModel;
import com.siteone.core.services.SiteOneProductService;
import com.siteone.facade.InvoiceEntryData;


/**
 * @author 1219341
 *
 */
public class SiteOneInvoiceEntryPopulator implements Populator<SiteOneInvoiceEntryModel, InvoiceEntryData>
{

	private static final int CURRENCY_TOTAL_PRICE_DIGITS = Config.getInt("curency.totalprice.digits", 2);
	private static final int CURRENCY_UNIT_PRICE_DIGITS = Config.getInt("currency.unitprice.digits", 3);

	@Resource(name = "productFacade")
	private ProductFacade productFacade;

	@Resource(name = "siteOneProductService")
	private SiteOneProductService siteOneProductService;

	@Resource(name = "productModelUrlResolver")
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Resource(name = "productConfiguredPopulator")
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;

	@Resource(name = "productConverter")
	private Converter<ProductModel, ProductData> productConverter;


	@Override
	public void populate(final SiteOneInvoiceEntryModel source, final InvoiceEntryData target) throws ConversionException
	{
		target.setBasePrice(truncatePrice(source.getBasePrice(), CURRENCY_UNIT_PRICE_DIGITS));
		target.setQuantity(source.getQuantityText());
		target.setUnit(source.getUnit());
		target.setProductItemNumber(source.getProductItemNumber());
		target.setDescription(source.getDescription());
		target.setQtyShipped(source.getQtyShipped());
		target.setQtyOpen(source.getQtyOpen());
		target.setExtPrice(truncatePrice(source.getExtprice(), CURRENCY_TOTAL_PRICE_DIGITS));
		target.setNetPrice(truncatePrice(source.getNetprice(), CURRENCY_UNIT_PRICE_DIGITS));
		final ProductModel productModel = siteOneProductService.getProductBySearchTermForSearch(source.getProductItemNumber());
		if (null != productModel)
		{
			final ProductData productData = productConverter.convert(productModel);
			target.setImages(productData.getImages());

			target.setProductUrl(getProductModelUrlResolver().resolve(productModel));
		}
	}

	/**
	 * This method is used to truncate the price into 2.
	 *
	 * @param priceToTruncate
	 *           - price needs to be truncated.
	 *
	 * @return truncated price
	 */
	private String truncatePrice(final Double priceToTruncate, final int toDigits)
	{
		if (null != priceToTruncate)
		{
			final DecimalFormat decimalFormat;
			if (toDigits == 3)
			{
				decimalFormat = new DecimalFormat("#,##0.000");
			}
			else
			{
				decimalFormat = new DecimalFormat("#,##0.00");
			}
			final String truncatedPrice = decimalFormat
					.format(BigDecimal.valueOf(priceToTruncate.doubleValue()).setScale(toDigits, BigDecimal.ROUND_HALF_UP));
			return truncatedPrice;
		}
		else
		{
			return null;
		}
	}

	/**
	 * @return the productFacade
	 */
	public ProductFacade getProductFacade()
	{
		return productFacade;
	}

	/**
	 * @param productFacade
	 *           the productFacade to set
	 */
	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	/**
	 * @return the siteOneProductService
	 */
	public SiteOneProductService getSiteOneProductService()
	{
		return siteOneProductService;
	}

	/**
	 * @param siteOneProductService
	 *           the siteOneProductService to set
	 */
	public void setSiteOneProductService(final SiteOneProductService siteOneProductService)
	{
		this.siteOneProductService = siteOneProductService;
	}

	/**
	 * @return the productModelUrlResolver
	 */
	public UrlResolver<ProductModel> getProductModelUrlResolver()
	{
		return productModelUrlResolver;
	}

	/**
	 * @param productModelUrlResolver
	 *           the productModelUrlResolver to set
	 */
	public void setProductModelUrlResolver(final UrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

}
