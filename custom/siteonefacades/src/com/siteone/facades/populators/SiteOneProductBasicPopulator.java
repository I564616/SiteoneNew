/**
 *
 */
package com.siteone.facades.populators;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.cmsfacades.data.MediaData;
import de.hybris.platform.commercefacades.product.converters.populator.ProductBasicPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.HybrisEnumValue;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.variants.model.VariantProductModel;
import de.hybris.platform.core.model.media.MediaModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import com.siteone.core.b2bunit.service.SiteOneB2BUnitService;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.core.services.SiteOneProductUOMService;
import com.siteone.facades.product.data.InventoryUOMData;
import com.siteone.facades.storesession.SiteOneStoreSessionFacade;
import de.hybris.platform.servicelayer.dto.converter.Converter;


/**
 * @author 1091124
 *
 */
public class SiteOneProductBasicPopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends ProductBasicPopulator<SOURCE, TARGET>
{
	private EnumerationService enumerationService;
	private SiteOneProductUOMService siteOneProductUOMService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "storeSessionFacade")
	private SiteOneStoreSessionFacade storeSessionFacade;

	@Resource(name = "defaultSiteOneB2BUnitService")
	private SiteOneB2BUnitService defaultSiteOneB2BUnitService;
	
	@Resource(name = "mediaModelConverter")
	Converter<MediaModel, MediaData> mediaModelConverter;
	
	@Resource(name = "i18nService")
	private I18NService i18nService;
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;


	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}
	
	private final Logger LOG = Logger.getLogger(SiteOneProductBasicPopulator.class);


	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		super.populate(productModel, productData);
		//storeSessionFacade.setGeolocatedSessionStore();

		final List<String> regionDataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(productModel.getRegulatoryStates()))
		{
			for (final RegionModel regionModel : productModel.getRegulatoryStates())
			{
				regionDataList.add(regionModel.getIsocodeShort());
			}
			productData.setRegulatoryStates(regionDataList);

		}

		final List<String> storeIdList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(productModel.getStores()))
		{
			for (final PointOfServiceModel pointOfServiceModel : productModel.getStores())
			{
				storeIdList.add(pointOfServiceModel.getStoreId());
			}
			productData.setStores(storeIdList);
		}

		if (null != productModel.getProductBrandName())
		{
			productData.setProductBrandName(getEnumerationService()
					.getEnumerationName((HybrisEnumValue) getProductAttribute(productModel, ProductModel.PRODUCTBRANDNAME)));
		}
		if(null!=productModel.getProductQrCode())
		{
		final MediaData mediaData = mediaModelConverter.convert(productModel.getProductQrCode());
		productData.setProductQRCodeMedia(mediaData);
		}
		final Collection<VariantProductModel> variants = productModel.getVariants();
		productData.setMultidimensional(Boolean.valueOf(CollectionUtils.isNotEmpty(variants)));
		productData.setVariantCount(Integer.valueOf(variants.size()));
		productData.setIsRegulateditem(productModel.getIsRegulatedItem());
		productData.setIsProductDiscontinued(productModel.getIsProductDiscontinued());
		productData.setProductLongDesc(productModel.getProductLongDesc()!=null ? (productModel.getProductLongDesc()).replaceAll("&nbsp;"," ") : productModel.getProductLongDesc());
		productData.setItemNumber(productModel.getItemNumber());
		productData.setFeatureBullets(productModel.getFeatureBullets());
		productData.setInventoryUPCID(productModel.getInventoryUPCID());
		if (null != productModel.getSalientBullets())
		{
			productData.setSalientBullets(productModel.getSalientBullets());
		}
		productData.setIsProductOffline(productModel.getIsProductOffline());
		if (null != productModel.getCalcUrl())
		{
			productData.setCalcUrl(productModel.getCalcUrl());
		}
		if (null != productModel.getSavedListProductComment())
		{
			productData.setSavedListComment(productModel.getSavedListProductComment());
		}

		if (storeSessionFacade.getSessionShipTo() != null)
		{
			final B2BUnitModel b2bUnit = defaultSiteOneB2BUnitService
					.getParentForUnit(storeSessionFacade.getSessionShipTo().getUid());
			if (CollectionUtils.isNotEmpty(b2bUnit.getAssignedProducts()))
			{
				//boolean isAvailable = productModel.getB2bUnitGroup().stream().anyMatch(unit ->unit.getUid().equalsIgnoreCase(b2bUnit.getUid()));
				final boolean isAvailable = b2bUnit.getAssignedProducts().stream()
						.anyMatch(product -> product.getCode().equalsIgnoreCase(productModel.getCode()));
				productData.setIsSellableForB2BUnit(isAvailable ? false : true);
			}
			else
			{
				productData.setIsSellableForB2BUnit(Boolean.TRUE);
			}

		}
		else
		{
			productData.setIsSellableForB2BUnit(Boolean.TRUE);
		}

		final List<InventoryUPCModel> inventoryUPC = productModel.getUpcData();
		if (null != inventoryUPC && inventoryUPC.size() > 0)
		{
		    final List<InventoryUOMData> inventoryUOMList = new CopyOnWriteArrayList<>();
		    boolean hideUom = false;
		    boolean singleUom = false;
		    int sellableUomCount = 0;
		    final List<InventoryUPCModel> inventoryUPCChildList = inventoryUPC.stream()
		            .filter(upc -> !upc.getBaseUPC().booleanValue()).collect(Collectors.toList());
		    final InventoryUPCModel inventoryUPCParent = inventoryUPC.stream().filter(upc -> upc.getBaseUPC().booleanValue())
		            .findFirst().orElse(null);


		 
		    final boolean isSpanish = isSpanish();


		    if (CollectionUtils.isNotEmpty(inventoryUPCChildList))
		    {
		        for (final InventoryUPCModel inventoryUOMModel : inventoryUPCChildList)
		        {
		            final InventoryUOMData inventoryUOMData = new InventoryUOMData();
		            inventoryUOMData.setInventoryUOMID(inventoryUOMModel.getInventoryUPCID());
		            
		            // Translate Description
		            String desc = inventoryUOMModel.getInventoryUPCDesc();
		            if (desc != null && isSpanish) {
		                String key = normalizeUOMKey(desc);
		                desc = getMessageSource().getMessage(key, null,desc, getI18nService().getCurrentLocale());
		            }
		            inventoryUOMData.setDescription(desc);


		            if (null != inventoryUPCParent && null != inventoryUPCParent.getInventoryUPCID())
		            {
		                inventoryUOMData.setParentInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
		            }
		            inventoryUOMData.setInventoryMultiplier(inventoryUOMModel.getInventoryMultiplier());
		            inventoryUOMData.setHideUOMOnline(
		                    inventoryUOMModel.getHideUPCOnline() != null ? inventoryUOMModel.getHideUPCOnline() : false);
		            if (null != inventoryUOMModel.getInventoryUPCDesc())
		            {
		                String uomTranslated = inventoryUOMModel.getInventoryUPCDesc();
		                if (isSpanish) {
		                    String key = normalizeUOMKey(uomTranslated);
		                    uomTranslated = getMessageSource().getMessage(key, null,uomTranslated, getI18nService().getCurrentLocale());
		                }
		                inventoryUOMData.setInventoryUOMDesc(uomTranslated);
		                inventoryUOMData.setMeasure(uomTranslated);
		            }
		            if (inventoryUOMModel.getHideUPCOnline() != null && !inventoryUOMModel.getHideUPCOnline().booleanValue())
		            {
		                sellableUomCount++;
		                inventoryUOMList.add(inventoryUOMData);
		            }
		        }
		        if (null != inventoryUPCParent && null != inventoryUPCParent.getInventoryUPCID()
		                && !inventoryUPCParent.getHideUPCOnline().booleanValue())
		        {
		            if (CollectionUtils.isNotEmpty(inventoryUOMList) && inventoryUOMList.size() > 0)
		            {
		                final InventoryUOMData inventoryUOMData = new InventoryUOMData();
		                inventoryUOMData.setInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
		                inventoryUOMData.setParentInventoryUOMID(inventoryUPCParent.getInventoryUPCID());
		                inventoryUOMData.setInventoryMultiplier(inventoryUPCParent.getInventoryMultiplier());
		                inventoryUOMData.setHideUOMOnline(
		                        inventoryUPCParent.getHideUPCOnline() != null ? inventoryUPCParent.getHideUPCOnline() : false);


		                if (null != inventoryUPCParent.getInventoryUPCDesc())
		                {
		                    String parentUom = inventoryUPCParent.getInventoryUPCDesc();
		                    if (isSpanish) {
		                        String key = normalizeUOMKey(parentUom);
		                        parentUom = getMessageSource().getMessage(key, null,parentUom, getI18nService().getCurrentLocale());
		                    }
		                    inventoryUOMData.setDescription(parentUom);
		                    inventoryUOMData.setInventoryUOMDesc(parentUom);
		                    inventoryUOMData.setMeasure(parentUom);
		                }


		                inventoryUOMList.add(inventoryUOMData);
		            }
		            else
		            {
		                singleUom = true;
		                productData.setSingleUom(true);
		                String parentUom = inventoryUPCParent.getInventoryUPCDesc();
		                if (parentUom != null && isSpanish) {
		                    String key = normalizeUOMKey(parentUom);
		                    parentUom = getMessageSource().getMessage(key, null,parentUom, getI18nService().getCurrentLocale());
		                }
		                productData.setSingleUomMeasure(parentUom);
		                productData.setSingleUomDescription(parentUom);
		                if (inventoryUPCParent.getInventoryMultiplier() != null)
		                {
		                    productData.setInventoryMultiplier(inventoryUPCParent.getInventoryMultiplier());
		                }                                                
		            }
		        }
		        else if (CollectionUtils.isNotEmpty(inventoryUOMList) && inventoryUOMList.size() == 1)
		        {
		            hideUom = true;
		            productData.setInventoryMultiplier(inventoryUOMList.get(0).getInventoryMultiplier());
		        }
		    }
		    else
		    {
		        singleUom = true;
		        productData.setSingleUom(true);
		        String parentUom = inventoryUPCParent.getInventoryUPCDesc();
		        if (parentUom != null && isSpanish) {
		            String key = normalizeUOMKey(parentUom);
		            parentUom = getMessageSource().getMessage(key, null,parentUom, getI18nService().getCurrentLocale());
		        }
		        productData.setSingleUomMeasure(parentUom);
		        productData.setSingleUomDescription(parentUom);
		        if (inventoryUPCParent.getInventoryMultiplier() != null)
		        {
		            productData.setInventoryMultiplier(inventoryUPCParent.getInventoryMultiplier());
		        }
		        if (null != inventoryUPCParent && null != inventoryUPCParent.getInventoryUPCID()
		                && inventoryUPCParent.getHideUPCOnline().booleanValue())
		        {
		            hideUom = true;
		        }


		    }
		    productData.setHideUom(hideUom);
		    productData.setSingleUom(singleUom);
		    Collections.sort(inventoryUOMList,
		            (o1, o2) -> o1.getInventoryMultiplier().intValue() - o2.getInventoryMultiplier().intValue());
		    productData.setSellableUoms(inventoryUOMList);
		    productData.setSellableUomsCount(Integer.valueOf(inventoryUOMList.size()));
		}
		
		

		productData.setHideCSP(productModel.getHideCSP());
		productData.setHideList(productModel.getHideList());
		productData.setProductType(productModel.getProductType());
		if (productModel.getInventoryCheck() != null)
		{
			productData.setInventoryCheck(productModel.getInventoryCheck());
		}
		boolean segmentLevelShippingEnabled = false;
		if ((sessionService.getAttribute("segmentLevelShippingEnabled") != null))
		{
			segmentLevelShippingEnabled = (boolean) getSessionService().getAttribute("segmentLevelShippingEnabled");
		}
		if (BooleanUtils.isTrue(productModel.getIsShippable()) && segmentLevelShippingEnabled)
		{
			productData.setIsShippable(true);
		}
		else
		{
			productData.setIsShippable(false);
		}
		productData.setMaxShippableQuantity(productModel.getMaxShippableQuantity());
		productData.setMaxShippableDollarAmount(productModel.getMaxShippableDollarAmount());
		productData.setDeliveryAlwaysEnabledBranches(productModel.getDeliveryAlwaysEnabledBranches());
		if (BooleanUtils.isNotTrue(productModel.getIsDeliverable()) && productModel.getDeliveryAlwaysEnabledBranches() != null
				&& storeSessionFacade.getSessionStore() != null
				&& ArrayUtils.contains(productModel.getDeliveryAlwaysEnabledBranches().trim().split(","),
						storeSessionFacade.getSessionStore().getStoreId()))
		{
			productData.setIsDeliverable(true);
		}
		else
		{
			productData.setIsDeliverable(productModel.getIsDeliverable());
		}
		productData.setIsTransferrable(productModel.getIsTransferrable());
		if (productModel.getPhotoCredit() != null)
		{
			productData.setPhotoCredit(productModel.getPhotoCredit().trim());
		}
		productData.setOrderQuantityInterval(productModel.getOrderQuantityInterval());
		productData.setSecretSku(productModel.getSecretSku());
		productData.setEeee(productModel.getEeee());
		productData.setBulkFlag(productModel.getBulkFlag());
		productData.setWeighAndPayEnabled(productModel.getWeighAndPayEnabled());
	}
	
	private boolean isSpanish() {
	    try {
	        String currentLanguage = storeSessionFacade.getCurrentLanguage().getIsocode();
	        return "es".equals(currentLanguage);
	    } catch (Exception e) {
	        LOG.warn("Error determining translation preference", e);
	        return false;
	    }
	}

	private String normalizeUOMKey(String desc) {
	    return "uom." + desc.toLowerCase()
	                        .replaceAll("[^a-z0-9]", "") // remove punctuation/special chars
	                        .replaceAll("\\s+", "")      // remove whitespace
	                        .trim();
	}
	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}
	
	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}
	
	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	
	/**
	 * @return the enumerationService
	 */
	public EnumerationService getEnumerationService()
	{
		return enumerationService;
	}

	/**
	 * @param enumerationService
	 *           the enumerationService to set
	 */
	public void setEnumerationService(final EnumerationService enumerationService)
	{
		this.enumerationService = enumerationService;
	}

	/**
	 * @return the siteOneProductUOMService
	 */
	public SiteOneProductUOMService getSiteOneProductUOMService()
	{
		return siteOneProductUOMService;
	}

	/**
	 * @param siteOneProductUOMService
	 *           the siteOneProductUOMService to set
	 */
	public void setSiteOneProductUOMService(final SiteOneProductUOMService siteOneProductUOMService)
	{
		this.siteOneProductUOMService = siteOneProductUOMService;
	}


}
