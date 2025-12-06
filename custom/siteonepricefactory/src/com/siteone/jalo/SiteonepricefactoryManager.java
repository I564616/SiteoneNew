/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.siteone.jalo;

import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.Registry;
import de.hybris.platform.europe1.channel.strategies.RetrieveChannelStrategy;
import de.hybris.platform.europe1.constants.Europe1Tools;
import de.hybris.platform.europe1.constants.GeneratedEurope1Constants.TC;
import de.hybris.platform.europe1.enums.PriceRowChannel;
import com.siteone.service.SiteOnePDTRowsQueryBuilder;
import de.hybris.platform.europe1.jalo.PDTRowsQueryBuilder.QueryWithParams;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.flexiblesearch.FlexibleSearch;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.AbstractOrderEntry;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.order.TaxService;
import de.hybris.platform.order.DiscountService;
import de.hybris.platform.storelocator.jalo.PointOfService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.util.StandardDateRange;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.order.strategies.calculation.FindPriceStrategy;
import de.hybris.platform.order.strategies.calculation.FindTaxValuesStrategy;
import de.hybris.platform.order.strategies.calculation.FindDiscountValuesStrategy;
import de.hybris.platform.servicelayer.user.UserNetCheckingStrategy;
import de.hybris.platform.order.strategies.calculation.impl.internal.PricingCustomizationDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.siteone.constants.SiteonepricefactoryConstants;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;
import de.hybris.platform.b2b.services.B2BOrderService;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import com.siteone.jalo.GeneratedSiteonepricefactoryManager;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.core.model.product.ProductModel;

/**
 * This is the extension manager of the Siteonepricefactory extension.
 */
public class SiteonepricefactoryManager extends GeneratedSiteonepricefactoryManager
{
	/** Edit the local|project.properties to change logging behavior (properties 'log4j.*'). */
	private static final Logger LOG = LoggerFactory.getLogger(SiteonepricefactoryManager.class);


	private static final String HARDSCAPE_PRODUCT= "CH01_15";

	private static final String NURSERY_PRODUCT= "CH01_16";
	
	private RetrieveChannelStrategy retrieveChannelStrategy;
	private ModelService modelService;
	private TaxService taxService;
	private DiscountService discountService;
	private PriceService priceService;
	private FindPriceStrategy findPriceStrategy;
	private FindTaxValuesStrategy findTaxValuesStrategy;
	private FindDiscountValuesStrategy findDiscountValuesStrategy;
	private UserNetCheckingStrategy userNetCheckingStrategy;
	private PricingCustomizationDetector pricingCustomizationDetector;
	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;
	
	@Resource(name = "pointOfServiceDao")
	private SiteOnePointOfServiceDao siteOnePosDAO;
	
	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	@Resource(name = "productVariantFacade")
	private ProductFacade productFacade;
	
	@Resource(name = "b2bOrderService")
	private B2BOrderService b2bOrderService;

	@Resource(name = "productService")
	private ProductService productService;
	
	public SiteOnePointOfServiceDao getSiteOnePosDAO() {
		return siteOnePosDAO;
	}

	public void setSiteOnePosDAO(SiteOnePointOfServiceDao siteOnePosDAO) {
		this.siteOnePosDAO = siteOnePosDAO;
	}

	@Override
	protected List getPriceInformations(final SessionContext ctx, final Product product, final EnumerationValue productGroup,
			final User user, final EnumerationValue userGroup, final Currency curr, final boolean net, final Date date,
			final Collection taxValues) throws JaloPriceFactoryException
	{
		final List priceRows = this
				.filterPriceRows(this.matchPriceRowsForInfo(ctx, product, productGroup, user, userGroup, curr, date, net));// 1637
		final ArrayList priceInfos = new ArrayList(priceRows.size());// 1639
		Collection theTaxValues = taxValues;// 1640
		final ArrayList defaultPriceInfos = new ArrayList(priceRows.size());// 1642
		final PriceRowChannel channel = this.retrieveChannelStrategy.getChannel(ctx);// 1643
		final Iterator arg15 = priceRows.iterator();

		while (true)
		{
			while (arg15.hasNext())
			{// 1645
				final PriceRow row = (PriceRow) arg15.next();
				PriceInformation pInfo = Europe1Tools.createPriceInformation(row, curr);// 1647
				if (pInfo.getPriceValue().isNet() != net)
				{// 1649
					if (theTaxValues == null)
					{// 1652
						theTaxValues = Europe1Tools.getTaxValues(
								this.getTaxInformations(product, this.getPTG(ctx, product), user, this.getUTG(ctx, user), date));// 1654 1655
					}

					pInfo = new PriceInformation(pInfo.getQualifiers(), pInfo.getPriceValue().getOtherPrice(theTaxValues));// 1658
				}

				if (row.getChannel() == null)
				{// 1662
					defaultPriceInfos.add(pInfo);// 1664
				}

				if (channel == null && row.getChannel() == null)
				{// 1668
					priceInfos.add(pInfo);// 1670
				}
				else if (channel != null && row.getChannel() != null
						&& row.getChannel().getCode().equalsIgnoreCase(channel.getCode()))
				{// 1673
					priceInfos.add(pInfo);// 1675
				}
			}

			if (priceInfos.size() == 0)
			{// 1679
				return defaultPriceInfos;// 1681
			}

			return priceInfos;// 1685
		}
	}

	@Override
	public PriceRow matchPriceRowForPrice(final SessionContext ctx, final Product product, final EnumerationValue productGroup,
			final User user, final EnumerationValue userGroup, final long qtd, final Unit unit, final Currency currency,
			final Date date, final boolean net, final boolean giveAwayMode) throws JaloPriceFactoryException
	{
	        LOG.info("#A-Inside matchPriceRowForPrice Method for " + product.getCode());
		if (product == null && productGroup == null)
		{
			throw new JaloPriceFactoryException(
					"cannot match price without product and product group - at least one must be present", 0);
		}
		else if (user == null && userGroup == null)
		{
			throw new JaloPriceFactoryException("cannot match price without user and user group - at least one must be present", 0);
		}
		else if (currency == null)
		{
			throw new JaloPriceFactoryException("cannot match price without currency", 0);
		}
		else if (date == null)
		{
			throw new JaloPriceFactoryException("cannot match price without date", 0);
		}
		else if (unit == null)
		{
			throw new JaloPriceFactoryException("cannot match price without unit", 0);
		}
		else
		{
		    LOG.info("# A - Inside matchPriceRowForPrice Method else condition for " + product.getCode());
			Collection<PriceRow> rows = new ArrayList<>();
			PointOfServiceData sessionPOS = this.getStoreSessionFacade().getSessionStore();
			PointOfServiceModel posModel = null;
			if(null != sessionPOS)
			{
				final ProductModel productModel = productService.getProductForCode(product.getCode());
				if(CollectionUtils.isNotEmpty(productModel.getClassificationClasses()))
			       {
						ClassificationClassModel classificationClass = (ClassificationClassModel)productModel.getClassificationClasses().get(0);
						if(productModel.getClassificationClasses().get(0).getCode().startsWith(HARDSCAPE_PRODUCT)||productModel.getClassificationClasses().get(0).getCode().startsWith(NURSERY_PRODUCT) )
						{
							LOG.info("#B - Inside  matchPriceRowForPrice Method for cart classification if condition for " + product.getCode());
							posModel = getPointOfServiceBasedonStock(product.getCode(),sessionPOS);
						}
						else
						{
							LOG.info("#C - Inside  matchPriceRowForPrice Method for cart classification else condition for " + product.getCode());
							 posModel = this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
						}
					}
			      else if(CollectionUtils.isNotEmpty(productModel.getFeatures())) 
			       {
						ClassificationClassModel classificationClass = productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass();
						if(productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass().getCode().startsWith(HARDSCAPE_PRODUCT)|| productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass().getCode().startsWith(NURSERY_PRODUCT))
						{
							LOG.info("#D - Inside  matchPriceRowForPrice Method for cart feature if condition for " + product.getCode());
							posModel = getPointOfServiceBasedonStock(product.getCode(),sessionPOS);
						}
						else
						{
							LOG.info("#E - Inside  matchPriceRowForPrice Method for cart feature else condition for " + product.getCode());
							 posModel = this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
						}
					}
					else
					{
					LOG.info("#F - Inside  matchPriceRowForPrice Method for cart else condition for " + product.getCode());
			        posModel = this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
			        }
			rows = siteOneQueryPriceRows4Price(ctx, product, productGroup, user, userGroup,posModel);
			}
			if (!rows.isEmpty())
			{
				final PriceRowChannel channel = this.retrieveChannelStrategy.getChannel(ctx);
				final List list = this.filterPriceRows4Price(rows, qtd, unit, currency, date, giveAwayMode, channel);
				if (list.isEmpty())
				{
					return null;
				}
				else if (list.size() == 1)
				{
					return (PriceRow) list.get(0);
				}
				else
				{
					Collections.sort(list, new SiteonepricefactoryManager.PriceRowMatchComparator(currency, net, unit));
					return (PriceRow) list.get(0);
				}
			}
			else
			{
				return null;
			}
		}
	}
	
	public PriceRow matchPriceRowForPrice(final SessionContext ctx, final Product product, final EnumerationValue productGroup,
			final User user, final EnumerationValue userGroup, final long qtd, final Unit unit, final Currency currency,
			final Date date, final boolean net, final boolean giveAwayMode, final String storeId) throws JaloPriceFactoryException
	{
	        LOG.info("#I - Inside matchPriceRowForPrice Overloaded Method for " + product.getCode());
		if (product == null && productGroup == null)
		{
			throw new JaloPriceFactoryException(
					"cannot match price without product and product group - at least one must be present", 0);
		}
		else if (user == null && userGroup == null)
		{
			throw new JaloPriceFactoryException("cannot match price without user and user group - at least one must be present", 0);
		}
		else if (currency == null)
		{
			throw new JaloPriceFactoryException("cannot match price without currency", 0);
		}
		else if (date == null)
		{
			throw new JaloPriceFactoryException("cannot match price without date", 0);
		}
		else if (unit == null)
		{
			throw new JaloPriceFactoryException("cannot match price without unit", 0);
		}
		else
		{
		    LOG.info("#II - Inside matchPriceRowForPrice Overloaded Method else condition for " + product.getCode());
			Collection<PriceRow> rows = new ArrayList<>();
			PointOfServiceModel posModel = this.getSiteOnePosDAO().getStoreForId(storeId);
			rows = siteOneQueryPriceRows4Price(ctx, product, productGroup, user, userGroup,posModel);
			if (!rows.isEmpty())
			{
				final PriceRowChannel channel = this.retrieveChannelStrategy.getChannel(ctx);
				final List list = this.filterPriceRows4Price(rows, qtd, unit, currency, date, giveAwayMode, channel);
				if (list.isEmpty())
				{
					return null;
				}
				else if (list.size() == 1)
				{
					return (PriceRow) list.get(0);
				}
				else
				{
					Collections.sort(list, new SiteonepricefactoryManager.PriceRowMatchComparator(currency, net, unit));
					return (PriceRow) list.get(0);
				}
			}
			else
			{
				return null;
			}
		}
	}

	protected class PriceRowMatchComparator implements Comparator<PriceRow>
	{
		private final Currency curr;
		private final boolean net;
		private final Unit unit;

		protected PriceRowMatchComparator(final Currency curr, final boolean net, final Unit unit)
		{
			this.curr = curr;
			this.net = net;
			this.unit = unit;
		}

		public int compare(final PriceRow row1, final PriceRow row2)
		{
			final int matchValue1 = row1.getMatchValueAsPrimitive();
			final int matchValue2 = row2.getMatchValueAsPrimitive();
			if (matchValue1 != matchValue2)
			{
				return matchValue2 - matchValue1;
			}
			else
			{
				final boolean c1Match = this.curr.equals(row1.getCurrency());
				final boolean c2Match = this.curr.equals(row2.getCurrency());
				if (c1Match != c2Match)
				{
					return c1Match ? -1 : 1;
				}
				else
				{
					final boolean n1Match = this.net == row1.isNetAsPrimitive();
					final boolean n2Match = this.net == row2.isNetAsPrimitive();
					if (n1Match != n2Match)
					{
						return n1Match ? -1 : 1;
					}
					else
					{
						final boolean u1Match = this.unit.equals(row1.getUnit());
						final boolean u2Match = this.unit.equals(row2.getUnit());
						if (u1Match != u2Match)
						{
							return u1Match ? -1 : 1;
						}
						else
						{
							final long min1 = row1.getMinqtdAsPrimitive();
							final long min2 = row2.getMinqtdAsPrimitive();
							if (min1 != min2)
							{
								return min1 > min2 ? -1 : 1;
							}
							else
							{
								final boolean row1Range = row1.getStartTime() != null;
								final boolean row2Range = row2.getStartTime() != null;
								if (row1Range != row2Range)
								{
									return row1Range ? -1 : 1;
								}
								else
								{
									SiteonepricefactoryManager.LOG
											.warn("found ambigous price rows " + row1 + " and " + row2 + " - using PK to distinguish");
									return row1.getPK().compareTo(row2.getPK());
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<PriceRow> matchPriceRowsForInfo(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup, final User user, final EnumerationValue userGroup, final Currency currency,
			final Date date, final boolean net) throws JaloPriceFactoryException
	{
	        LOG.info("#1 - Inside  matchPriceRowsForInfo Method for " + product.getCode());
		if (product == null && productGroup == null)
		{
			throw new JaloPriceFactoryException(
					"cannot match price info without product and product group - at least one must be present", 0);
		}
		else if (user == null && userGroup == null)
		{
			throw new JaloPriceFactoryException("cannot match price info without user and user group - at least one must be present",
					0);
		}
		else if (currency == null)
		{
			throw new JaloPriceFactoryException("cannot match price info without currency", 0);
		}
		else if (date == null)
		{
			throw new JaloPriceFactoryException("cannot match price info without date", 0);
		}
		else
		{
			Collection<PriceRow> priceRows = new ArrayList<>();	
			PointOfServiceData sessionPOS = this.getStoreSessionFacade().getSessionStore();
			PointOfServiceModel posModel = null;
			if(null != sessionPOS)
			{
				final ProductModel productModel = productService.getProductForCode(product.getCode());
			    if (siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",sessionPOS.getStoreId()))
				{
				LOG.info("#2 - Inside  matchPriceRowsForInfo Method for mixed cart condition for " +  product.getCode());
				posModel=getPointOfServiceBasedonStock(product.getCode(),sessionPOS);
				}
			    else
			    {
			    	LOG.info("#3 - Inside matchPriceRowForPrice Method else condition for " + product.getCode());
			       if(CollectionUtils.isNotEmpty(productModel.getClassificationClasses()))
			       {
						ClassificationClassModel classificationClass = (ClassificationClassModel)productModel.getClassificationClasses().get(0);
						if(productModel.getClassificationClasses().get(0).getCode().startsWith(HARDSCAPE_PRODUCT)||productModel.getClassificationClasses().get(0).getCode().startsWith(NURSERY_PRODUCT) )
						{
							LOG.info("#4 - Inside  matchPriceRowsForInfo Method for non mixed cart classification if condition for " + product.getCode());
							posModel = getPointOfServiceBasedonStock(product.getCode(),sessionPOS);
						}
						else
						{
							LOG.info("#5 - Inside  matchPriceRowsForInfo Method for non mixed cart classification else condition for " + product.getCode()+" "+sessionPOS.getStoreId());
							 posModel = this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
						}
					}
			      else if(CollectionUtils.isNotEmpty(productModel.getFeatures())) 
			       {
						ClassificationClassModel classificationClass = productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass();
						if(productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass().getCode().startsWith(HARDSCAPE_PRODUCT)|| productModel.getFeatures().get(0).getClassificationAttributeAssignment().getClassificationClass().getCode().startsWith(NURSERY_PRODUCT))
						{
							LOG.info("#6 - Inside  matchPriceRowsForInfo Method for non mixed cart feature if condition for " + product.getCode());
							posModel = getPointOfServiceBasedonStock(product.getCode(),sessionPOS);
						}
						else
						{
							LOG.info("#7 - Inside  matchPriceRowsForInfo Method for non mixed cart feature else condition for " + product.getCode()+" "+sessionPOS.getStoreId());
							 posModel = this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
						}
					}
					else
					{
					LOG.info("#8 - Inside  matchPriceRowsForInfo Method for non mixed cart else condition for " + product.getCode());
			        posModel = this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
			        }
			    }
			priceRows = siteOneQueryPriceRows4Price(ctx, product, productGroup, user, userGroup,posModel);
			}
			final ArrayList priceRowsList = new ArrayList(priceRows);
			
			if (priceRows != null && priceRows.size() > 0) {
				return priceRowsList;
			}

			/*if (!priceRows.isEmpty())
			{
				final PriceRowChannel channel = this.retrieveChannelStrategy.getChannel(ctx);
				if (priceRowsList.size() > 1)
				{
					Collections.sort(priceRowsList, new SiteonepricefactoryManager.PriceRowInfoComparator(currency, net));
				}
				return this.filterPriceRows4Info(priceRowsList, currency, date, channel);
			}*/
			else
			{
				return Collections.emptyList();
			}
		}
	}
	
	protected PointOfServiceModel getPointOfServiceBasedonStock(String code,PointOfServiceData sessionPOS)
	{
		final ProductData productData = productFacade.getProductForCodeAndOptions(code,Arrays.asList(ProductOption.STOCK));
		if(null != productData.getStock() && StringUtils.isNotEmpty(productData.getStock().getFullfillmentStoreId()))
		{
		LOG.info("@POS -Inside  getPointOfServiceBasedonStock Method if condition for " + code);
		return  this.getSiteOnePosDAO().getStoreForId(productData.getStock().getFullfillmentStoreId());
		}
		else
		{
		 LOG.info("#POS -Inside  getPointOfServiceBasedonStock Method else condition for " + code);
		 return this.getSiteOnePosDAO().getStoreForId(sessionPOS.getStoreId());
		}
	}

	@Override
	protected List<PriceRow> filterPriceRows4Info(final Collection<PriceRow> rows, final Currency curr, final Date date,
			final PriceRowChannel channel)
	{
		if (rows.isEmpty())
		{
			return Collections.emptyList();
		}
		else
		{
			final Currency base = curr.isBase().booleanValue() ? null : C2LManager.getInstance().getBaseCurrency();
			final ArrayList ret = new ArrayList(rows);
			boolean hasChannelRowMatching = false;
			ListIterator it = ret.listIterator();

			while (true)
			{
				PriceRow priceRow;
				while (it.hasNext())
				{
					priceRow = (PriceRow) it.next();
					final Currency currency = priceRow.getCurrency();
					if (!curr.equals(currency) && (base == null || !base.equals(currency)))
					{
						it.remove();
					}
					else
					{
						final StandardDateRange dateRange = priceRow.getDateRange();
						if (dateRange != null && !dateRange.encloses(date))
						{
							it.remove();
						}
						else if (priceRow.isGiveAwayPriceAsPrimitive())
						{
							it.remove();
						}
						else if (channel != null && priceRow.getChannel() != null
								&& !priceRow.getChannel().getCode().equalsIgnoreCase(channel.getCode()))
						{
							it.remove();
						}
						else if (channel != null && priceRow.getChannel() != null
								&& priceRow.getChannel().getCode().equalsIgnoreCase(channel.getCode()))
						{
							hasChannelRowMatching = true;
						}
					}
				}

				if (hasChannelRowMatching && ret.size() > 1)
				{
					it = ret.listIterator();

					while (it.hasNext())
					{
						priceRow = (PriceRow) it.next();
						if (priceRow.getChannel() == null)
						{
							it.remove();
						}
					}
				}

				return ret;
			}
		}
	}

	protected class PriceRowInfoComparator implements Comparator<PriceRow>
	{
		private final Currency curr;
		private final boolean net;

		protected PriceRowInfoComparator(final Currency curr, final boolean net)
		{
			this.curr = curr;
			this.net = net;
		}

		public int compare(final PriceRow row1, final PriceRow row2)
		{
			final long u1Match = row1.getUnit().getPK().getLongValue();
			final long u2Match = row2.getUnit().getPK().getLongValue();
			if (u1Match != u2Match)
			{
				return u1Match < u2Match ? -1 : 1;
			}
			else
			{
				final long min1 = row1.getMinqtdAsPrimitive();
				final long min2 = row2.getMinqtdAsPrimitive();
				if (min1 != min2)
				{
					return min1 > min2 ? -1 : 1;
				}
				else
				{
					final int matchValue1 = row1.getMatchValueAsPrimitive();
					final int matchValue2 = row2.getMatchValueAsPrimitive();
					if (matchValue1 != matchValue2)
					{
						return matchValue2 - matchValue1;
					}
					else
					{
						final boolean c1Match = this.curr.equals(row1.getCurrency());
						final boolean c2Match = this.curr.equals(row2.getCurrency());
						if (c1Match != c2Match)
						{
							return c1Match ? -1 : 1;
						}
						else
						{
							final boolean n1Match = this.net == row1.isNetAsPrimitive();
							final boolean n2Match = this.net == row2.isNetAsPrimitive();
							if (n1Match != n2Match)
							{
								return n1Match ? -1 : 1;
							}
							else
							{
								final boolean row1Range = row1.getStartTime() != null;
								final boolean row2Range = row2.getStartTime() != null;
								return row1Range != row2Range ? (row1Range ? -1 : 1) : row1.getPK().compareTo(row2.getPK());
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected Collection<PriceRow> queryPriceRows4Price(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup, final User user, final EnumerationValue userGroup)
	{
		final PK productPk = product == null ? null : product.getPK();
		final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
		final PK userPk = user == null ? null : user.getPK();
		final PK userGroupPk = userGroup == null ? null : userGroup.getPK();
		final String productId = this.extractProductId(ctx, product);
		final SiteOnePDTRowsQueryBuilder builder = SiteOnePDTRowsQueryBuilder.defaultBuilder(TC.PRICEROW);
		final QueryWithParams queryAndParams = builder.withAnyProduct().withAnyUser().withProduct(productPk)
				.withProductId(productId).withProductGroup(productGroupPk).withUser(userPk).withUserGroup(userGroupPk).build();
		return FlexibleSearch.getInstance().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(), PriceRow.class)
				.getResult();
	}

	@Override
	protected List<PriceRow> filterPriceRows4Price(final Collection<PriceRow> rows, final long _quantity, final Unit unit,
			final Currency curr, final Date date, final boolean giveAwayMode, final PriceRowChannel channel)
	{
		if (rows.isEmpty())
		{
			return Collections.emptyList();
		}
		else
		{
			final Currency base = curr.isBase().booleanValue() ? null : C2LManager.getInstance().getBaseCurrency();
			final Set convertible = unit.getConvertibleUnits();
			final ArrayList ret = new ArrayList(rows);
			final long quantity = _quantity == 0L ? 1L : _quantity;
			boolean hasChannelRowMatching = false;
			ListIterator it = ret.listIterator();

			while (true)
			{
				PriceRow priceRow;
				while (it.hasNext())
				{
					priceRow = (PriceRow) it.next();
					if (quantity < priceRow.getMinqtdAsPrimitive())
					{
						it.remove();
					}
					else
					{
						final Currency currency = priceRow.getCurrency();
						if (!curr.equals(currency) && (base == null || !base.equals(currency)))
						{
							it.remove();
						}
						else
						{
							final Unit user = priceRow.getUnit();
							if (!unit.equals(user) && !convertible.contains(user))
							{
								it.remove();
							}
							else
							{
								final StandardDateRange dateRange = priceRow.getDateRange();
								if (dateRange != null && !dateRange.encloses(date))
								{
									it.remove();
								}
								else if (giveAwayMode != priceRow.isGiveAwayPriceAsPrimitive())
								{
									it.remove();
								}
								else if (channel != null && priceRow.getChannel() != null
										&& !priceRow.getChannel().getCode().equalsIgnoreCase(channel.getCode()))
								{
									it.remove();
								}
								else if (channel != null && priceRow.getChannel() != null
										&& priceRow.getChannel().getCode().equalsIgnoreCase(channel.getCode()))
								{
									hasChannelRowMatching = true;
								}
							}
						}
					}
				}

				if (hasChannelRowMatching && ret.size() > 1)
				{
					it = ret.listIterator();

					while (it.hasNext())
					{
						priceRow = (PriceRow) it.next();
						if (priceRow.getChannel() == null)
						{
							it.remove();
						}
					}
				}

				return ret;
			}
		}
	}

	@Override
	public PriceValue getBasePrice(final AbstractOrderEntry entry) throws JaloPriceFactoryException
	{
		final SessionContext ctx = this.getSession().getSessionContext();
		final AbstractOrder order = entry.getOrder(ctx);
		Currency currency = null;
		EnumerationValue productGroup = null;
		User user = null;
		EnumerationValue userGroup = null;
		Unit unit = null;
		long quantity = 0L;
		boolean net = false;
		Date date = null;
		final Product product = entry.getProduct();
		final boolean giveAwayMode = entry.isGiveAway(ctx).booleanValue();
		final boolean entryIsRejected = entry.isRejected(ctx).booleanValue();
		PriceRow row;
		if (giveAwayMode && entryIsRejected)
		{
			row = null;
		}
		else
		{
			PointOfServiceData sessionPOS = this.getStoreSessionFacade().getSessionStore();
			final AbstractOrderModel orderModel = b2bOrderService.getAbstractOrderForCode(order.getCode());
			final AbstractOrderEntryModel orderEntry = orderModel.getEntries().get(entry.getEntryNumber());
			if(siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("MixedCartEnabledBranches",sessionPOS.getStoreId()) && orderEntry.getDeliveryPointOfService() != null)
			{
				final String storeId =  orderEntry.getDeliveryPointOfService().getStoreId();
				row = this.matchPriceRowForPrice(ctx, product, productGroup = this.getPPG(ctx, product), user = order.getUser(),
						userGroup = this.getUPG(ctx, user), quantity = entry.getQuantity(ctx).longValue(), unit = entry.getUnit(ctx),
						currency = order.getCurrency(ctx), date = order.getDate(ctx), net = order.isNet().booleanValue(), giveAwayMode,storeId);
			}
			else
			{
			row = this.matchPriceRowForPrice(ctx, product, productGroup = this.getPPG(ctx, product), user = order.getUser(),
					userGroup = this.getUPG(ctx, user), quantity = entry.getQuantity(ctx).longValue(), unit = entry.getUnit(ctx),
					currency = order.getCurrency(ctx), date = order.getDate(ctx), net = order.isNet().booleanValue(), giveAwayMode);
			}
		}		
		if (row != null)
		{
			final Currency msg1 = row.getCurrency();
			double price;
			if (currency.equals(msg1))
			{
				price = row.getPriceAsPrimitive() / row.getUnitFactorAsPrimitive();
			}
			else
			{
				price = msg1.convert(currency, row.getPriceAsPrimitive() / row.getUnitFactorAsPrimitive());
			}

			final Unit priceUnit = row.getUnit();
			final Unit entryUnit = entry.getUnit();
			final double convertedPrice = priceUnit.convertExact(entryUnit, price);
			return new PriceValue(currency.getIsoCode(), convertedPrice, row.isNetAsPrimitive());
		}
		else if (giveAwayMode)
		{
			return new PriceValue(order.getCurrency(ctx).getIsoCode(), 0.0D, order.isNet().booleanValue());
		}
		else
		{
			/*
			 * final String msg = Localization
			 * .getLocalizedString("exception.europe1pricefactory.getbaseprice.jalopricefactoryexception1", new Object[] {
			 * product, productGroup, user, userGroup, Long.toString(quantity), unit, currency, date, Boolean.toString(net)
			 * }); throw new JaloPriceFactoryException(msg, 0);
			 */
			LOG.info("Price Row not available for " + entry.getProduct().getCode());
			return new PriceValue(order.getCurrency(ctx).getIsoCode(), 0.0D, order.isNet().booleanValue());
		}
	}

	protected Collection<PriceRow> siteOneQueryPriceRows4Price(final SessionContext ctx, final Product product,
			final EnumerationValue productGroup, final User user, final EnumerationValue userGroup,final PointOfServiceModel posModel)
	{
		final PK productPk = product == null ? null : product.getPK();
		final PK productGroupPk = productGroup == null ? null : productGroup.getPK();
		final PK userPk = user == null ? null : user.getPK();
		final PK userGroupPk = userGroup == null ? null : userGroup.getPK();
		final PK posPK = posModel == null ? null : posModel.getPk();
		final String productId = this.extractProductId(ctx, product);
		final SiteOnePDTRowsQueryBuilder builder = SiteOnePDTRowsQueryBuilder.defaultBuilder(TC.PRICEROW);
		final QueryWithParams queryAndParams = builder.withAnyProduct().withAnyUser().withProduct(productPk)
				.withProductId(productId).withProductGroup(productGroupPk).withUser(userPk).withUserGroup(userGroupPk).withPOS(posPK).build();
		return FlexibleSearch.getInstance().search(ctx, queryAndParams.getQuery(), queryAndParams.getParams(), PriceRow.class)
				.getResult();
	}

	/**
	 * Get the valid instance of this manager.
	 *
	 * @return the current instance of this manager
	 */
	public static SiteonepricefactoryManager getInstance()
	{
		return (SiteonepricefactoryManager) Registry.getCurrentTenant().getJaloConnection().getExtensionManager()
				.getExtension(SiteonepricefactoryConstants.EXTENSIONNAME);
	}


	/**
	 * Never call the constructor of any manager directly, call getInstance() You can place your business logic here -
	 * like registering a jalo session listener. Each manager is created once for each tenant.
	 */
	public SiteonepricefactoryManager() // NOPMD
	{
		LOG.debug("constructor of SiteonepricefactoryManager called.");
	}

	/**
	 * @return the retrieveChannelStrategy
	 */
	public RetrieveChannelStrategy getRetrieveChannelStrategy()
	{
		return retrieveChannelStrategy;
	}

	/**
	 * @param retrieveChannelStrategy
	 *           the retrieveChannelStrategy to set
	 */
	@Override
	public void setRetrieveChannelStrategy(final RetrieveChannelStrategy retrieveChannelStrategy)
	{
		this.retrieveChannelStrategy = retrieveChannelStrategy;
	}
	


	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		return modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}
	
	
	
	/**
	 * @return the taxService
	 */
	public TaxService getTaxService() {
		return taxService;
	}

	/**
	 * @param taxService the taxService to set
	 */
	public void setTaxService(TaxService taxService) {
		this.taxService = taxService;
	}
	
	/**
	 * @return the discountService
	 */
	public DiscountService getDiscountService() {
		return discountService;
	}

	/**
	 * @param discountService the discountService to set
	 */
	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}
	
	
	/**
	 * @return the priceService
	 */
	public PriceService getPriceService() {
		return priceService;
	}

	/**
	 * @param priceService the priceService to set
	 */
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}
	
	
	/**
	 * @return the findPriceStrategy
	 */
	public FindPriceStrategy getFindPriceStrategy() {
		return findPriceStrategy;
	}

	/**
	 * @param findPriceStrategy the findPriceStrategy to set
	 */
	public void setFindPriceStrategy(FindPriceStrategy findPriceStrategy) {
		this.findPriceStrategy = findPriceStrategy;
	}

	/**
	 * @return the findTaxValuesStrategy
	 */
	public FindTaxValuesStrategy getFindTaxValuesStrategy() {
		return findTaxValuesStrategy;
	}

	/**
	 * @param findTaxValuesStrategy the findTaxValuesStrategy to set
	 */
	public void setFindTaxValuesStrategy(FindTaxValuesStrategy findTaxValuesStrategy) {
		this.findTaxValuesStrategy = findTaxValuesStrategy;
	}
	
	/**
	 * @return the findDiscountValuesStrategy
	 */
	public FindDiscountValuesStrategy getFindDiscountValuesStrategy() {
		return findDiscountValuesStrategy;
	}

	/**
	 * @param findDiscountValuesStrategy the findDiscountValuesStrategy to set
	 */
	public void setFindDiscountValuesStrategy(FindDiscountValuesStrategy findDiscountValuesStrategy) {
		this.findDiscountValuesStrategy = findDiscountValuesStrategy;
	}
	
	/**
	 * @return the userNetCheckingStrategy
	 */
	public UserNetCheckingStrategy getUserNetCheckingStrategy() {
		return userNetCheckingStrategy;
	}

	/**
	 * @param userNetCheckingStrategy the userNetCheckingStrategy to set
	 */
	public void setUserNetCheckingStrategy(UserNetCheckingStrategy userNetCheckingStrategy) {
		this.userNetCheckingStrategy = userNetCheckingStrategy;
	}
	
	/**
	 * @return the pricingCustomizationDetector
	 */
	public PricingCustomizationDetector getPricingCustomizationDetector() {
		return pricingCustomizationDetector;
	}

	/**
	 * @param pricingCustomizationDetector the pricingCustomizationDetector to set
	 */
	public void setPricingCustomizationDetector(PricingCustomizationDetector pricingCustomizationDetector) {
		this.pricingCustomizationDetector = pricingCustomizationDetector;
	}
	
	/**
	 * @return the storeSessionFacade
	 */
	public SiteOneStoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	/**
	 * @param storeSessionFacade
	 *           the storeSessionFacade to set
	 */
	public void setStoreSessionFacade(final SiteOneStoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

}
