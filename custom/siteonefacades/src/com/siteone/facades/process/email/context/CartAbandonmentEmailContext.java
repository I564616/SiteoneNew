/**
 *
 */
package com.siteone.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.PromotionResultData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.NumberTool;

import com.siteone.core.model.CartAbandonmentEmailProcessModel;
import com.siteone.facades.populators.PromotionPriceUtils;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;



/**
 * @author 1219341
 *
 */
public class CartAbandonmentEmailContext extends AbstractEmailContext<CartAbandonmentEmailProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CartAbandonmentEmailContext.class);
	
	public static final String EMAIL_SUBJECT = "subject";
	private static final String CART = "cart";
	private static final String FIRST_NAME = "firstName";
	private static final String NUMBER_TOOL = "numberTool";
	private static final String CURRENCY_UNITPRICE_FORMAT = "unitpriceFormat";
	private static final String CURRENCY_UNITPRICE_FORMAT_VAL = Config.getString("currency.unitprice.formattedDigits", "#0.000");
	private static final String COUNTRY_BASESITE_ID = "countryBaseSiteID";
	private Converter<CartModel, CartData> cartConverter;
	private static final String BRANCH = "branch";
	private static final String ADDRESS = "address";
	private static final String PHONE = "phone";
	

	@Resource(name = "modelService")
	private ModelService modelService;
	
	@Resource(name="searchRestrictionService")
 	private SearchRestrictionService searchRestrictionService;
	
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Override
	public void init(final CartAbandonmentEmailProcessModel cartAbandonmentEmailProcessModel, final EmailPageModel emailPageModel)
	{
		try
		{
			this.searchRestrictionService.disableSearchRestrictions();
			super.init(cartAbandonmentEmailProcessModel, emailPageModel);
			final CartData cartData = getCartConverter().convert(cartAbandonmentEmailProcessModel.getCart());
			
			if(cartAbandonmentEmailProcessModel.getCustomer() instanceof B2BCustomerModel) {
				B2BCustomerModel customer = (B2BCustomerModel) cartAbandonmentEmailProcessModel.getCustomer();
				
				final PointOfServiceModel pos = customer.getPreferredStore();
				
				if (pos != null) 
				{	
								
				put(BRANCH, pos.getStoreId());
				put(ADDRESS, pos.getAddress());
	         put(PHONE, pos.getAddress().getPhone1());
	         
	         LOG.error("The preferred store is " + pos.getStoreId());
	         
				}
				
				if(customer.getDefaultB2BUnit().getUid().endsWith("_CA")) {
					storeSessionFacade.setCurrentBaseStore("CA");
				}
				
				
				if(customer.getDefaultB2BUnit().getUid().endsWith("_US")) {
					storeSessionFacade.setCurrentBaseStore("US");
				}
			}
		
		if (null != cartData)
		{
			final List<OrderEntryData> cartDataEntries = cartData.getEntries();
			final List<PromotionResultData> appliedPromotions = cartData.getAppliedProductPromotions();
			if (null != cartDataEntries && null != appliedPromotions)
			{
				PromotionPriceUtils.findSalePriceForOrderEntries(cartDataEntries, cartData.getAppliedProductPromotions());
			}
		}

		put(CART, cartData);
		put(FIRST_NAME, ((B2BCustomerModel) cartAbandonmentEmailProcessModel.getCustomer()).getFirstName());
		put(NUMBER_TOOL, new NumberTool());
		put(CURRENCY_UNITPRICE_FORMAT, CURRENCY_UNITPRICE_FORMAT_VAL);
		final BaseSiteModel baseSite = getSite(cartAbandonmentEmailProcessModel);
		put(COUNTRY_BASESITE_ID, baseSite.getUid());
	 }
		
		 finally {
			this.searchRestrictionService.enableSearchRestrictions();
	 }
	 	
		final Map<String, String> bccEmails = new HashMap<>();
		final String bccEmail = Config.getString("bcc.email.address", null);
		if (StringUtils.isNotEmpty(bccEmail))
		{
			final String[] bccEmailList = bccEmail.split(",");
			for (final String email : bccEmailList)
			{
				bccEmails.put(email, "EcommCustomerEmails@siteone.com");
			}
		}
		cartAbandonmentEmailProcessModel.setBccEmails(bccEmails);

		modelService.save(cartAbandonmentEmailProcessModel);
		modelService.refresh(cartAbandonmentEmailProcessModel);
	}

	@Override
	protected BaseSiteModel getSite(final CartAbandonmentEmailProcessModel businessProcessModel)
	{
		return businessProcessModel.getSite();
	}


	@Override
	protected CustomerModel getCustomer(final CartAbandonmentEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getCustomer();
	}


	@Override
	protected LanguageModel getEmailLanguage(final CartAbandonmentEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	/**
	 * @return the cartConverter
	 */
	public Converter<CartModel, CartData> getCartConverter()
	{
		return cartConverter;
	}

	/**
	 * @param cartConverter
	 *           the cartConverter to set
	 */
	public void setCartConverter(final Converter<CartModel, CartData> cartConverter)
	{
		this.cartConverter = cartConverter;
	}


}
