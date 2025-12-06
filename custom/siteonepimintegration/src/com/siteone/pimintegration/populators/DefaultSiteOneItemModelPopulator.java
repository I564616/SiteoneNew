/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.siteone.pimintegration.populators;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.ProductFeatureModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.inboundservices.persistence.AttributePopulator;
import de.hybris.platform.inboundservices.persistence.ItemModelPopulator;
import de.hybris.platform.inboundservices.persistence.PersistenceContext;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.variants.model.GenericVariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.siteone.core.model.InventoryUPCModel;
import com.siteone.pimintegration.model.SiteOneDataSheetModel;
import com.siteone.pimintegration.model.SiteOneProductItemImagesModel;
import com.siteone.pimintegration.model.SiteOneProductSpecificationAttributeModel;


/**
 * @author SD02010
 *
 */
public class DefaultSiteOneItemModelPopulator implements ItemModelPopulator
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultSiteOneItemModelPopulator.class);

	private List<AttributePopulator> attributePopulators = Collections.emptyList();

	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}

	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	private ModelService modelService;
	@Resource(name = "catalogVersionService")
	private CatalogVersionService catalogVersionService;
	private ProductService productService;


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the attributePopulators
	 */
	public List<AttributePopulator> getAttributePopulators()
	{
		return attributePopulators;
	}

	private Object productModel;

	@Override
	public void populate(final ItemModel item, final PersistenceContext context)
	{
		Preconditions.checkArgument(item != null, "ItemModel cannot be null");
		Preconditions.checkArgument(context != null, "PersistenceContext cannot be null");

		if ((item instanceof ProductModel) && null != item.getProperty("productKind")
				&& null != context.getIntegrationItem().getAttribute("productKind") && !item.getProperty("productKind").toString()
						.equalsIgnoreCase(context.getIntegrationItem().getAttribute("productKind").toString()))
		{
			LOG.error("Siteone Item model populator");
			if (context.getIntegrationItem().getAttribute("productKind").toString().equalsIgnoreCase("Variant"))
			{
				productModel = modelService.create(GenericVariantProductModel.class);
				modelService.setAttributeValue(productModel, "code", modelService.getAttributeValue(item, "code").toString());
				modelService.setAttributeValue(item, "code",
						modelService.getAttributeValue(item, "code").toString() + "_old" + UUID.randomUUID().toString());
				modelService.setAttributeValue(productModel, "itemNumber",
						modelService.getAttributeValue(item, "itemNumber").toString());
				modelService.setAttributeValue(productModel, "productKind", context.getIntegrationItem().getAttribute("productKind"));
				if (productModel instanceof GenericVariantProductModel
						&& null != context.getIntegrationItem().getAttribute("baseProductCode"))
				{
					final ProductModel baseProduct = productService.getProductForCode(
							modelService.getAttributeValue(item, "catalogVersion"),
							context.getIntegrationItem().getAttribute("baseProductCode").toString());
					if (null != baseProduct)
					{
						modelService.setAttributeValue(productModel, "baseProduct", baseProduct);
					}
					else
					{
						throw new RuntimeException("Base Product is not available in Hybris");
					}
				}

				removeMappedData(item,productModel);

				modelService.save(item);
				modelService.refresh(item);
				modelService.setAttributeValue(item, "convertedProduct", productModel);

			}
			else
			{
				productModel = modelService.create(ProductModel.class);
				modelService.setAttributeValue(productModel, "code", modelService.getAttributeValue(item, "code").toString());
				modelService.setAttributeValue(item, "code",
						modelService.getAttributeValue(item, "code").toString() + "_old" + UUID.randomUUID().toString());
				modelService.setAttributeValue(productModel, "itemNumber",
						modelService.getAttributeValue(item, "itemNumber").toString());
				modelService.setAttributeValue(productModel, "productKind", context.getIntegrationItem().getAttribute("productKind"));
				removeMappedData(item,productModel);
				modelService.save(item);
				modelService.refresh(item);
				modelService.setAttributeValue(item, "convertedProduct", productModel);
			}
		}

		attributePopulators.forEach(p -> p.populate(item, context));
	}

	/**
	 *
	 */
	private void removeMappedData(final ItemModel model,final Object convertedModel)
	{
		
		modelService.setAttributeValue(model, "isProductOffline", true);
		modelService.setAttributeValue(model, "isProductDiscontinued", true);
		
		final List<MediaContainerModel> mediaContainerModelList = modelService.getAttributeValue(model, "galleryImages");
		if (mediaContainerModelList != null && !mediaContainerModelList.isEmpty())
		{
			for (final MediaContainerModel medicontainer : mediaContainerModelList)
			{
				modelService.removeAll(medicontainer.getMedias());
			}
			modelService.removeAll(mediaContainerModelList);
			modelService.setAttributeValue(model, "galleryImages", null);
		}
		final List<MediaContainerModel> brandLogosModelList = modelService.getAttributeValue(model, "brandLogos");
		if (brandLogosModelList != null && !brandLogosModelList.isEmpty())
		{
			for (final MediaContainerModel brandContainer : brandLogosModelList)
			{
				modelService.removeAll(brandContainer.getMedias());
			}
			modelService.removeAll(brandLogosModelList);
			modelService.setAttributeValue(model, "brandLogos", null);
		}
		final List<MediaContainerModel> lifeStyleModelList = modelService.getAttributeValue(model, "lifeStyles");
		if (lifeStyleModelList != null && !lifeStyleModelList.isEmpty())
		{
			for (final MediaContainerModel lifeStyle : lifeStyleModelList)
			{
				modelService.removeAll(lifeStyle.getMedias());
			}
			modelService.removeAll(lifeStyleModelList);
			modelService.setAttributeValue(model, "lifeStyles", null);
		}

		final List<MediaContainerModel> swatchImagesModelList = modelService.getAttributeValue(model, "swatchImages");
		if (swatchImagesModelList != null && !swatchImagesModelList.isEmpty())
		{
			for (final MediaContainerModel swatchImage : swatchImagesModelList)
			{
				modelService.removeAll(swatchImage.getMedias());
			}
			modelService.removeAll(swatchImagesModelList);
			modelService.setAttributeValue(model, "swatchImages", null);
		}


		final List<MediaContainerModel> specialImageTypesModelList = modelService.getAttributeValue(model, "specialImageTypes");
		if (specialImageTypesModelList != null && !specialImageTypesModelList.isEmpty())
		{
			for (final MediaContainerModel specialImageTypesModel : specialImageTypesModelList)
			{
				modelService.removeAll(specialImageTypesModel.getMedias());
			}
			modelService.removeAll(specialImageTypesModelList);
			modelService.setAttributeValue(model, "specialImageTypes", null);
		}

		final Collection<MediaModel> dataSheetModelList = modelService.getAttributeValue(model, "data_sheet");
		if (dataSheetModelList != null && !dataSheetModelList.isEmpty())
		{
			modelService.removeAll(dataSheetModelList);
			modelService.setAttributeValue(model, "data_sheet", null);
		}
		final Collection<InventoryUPCModel> upcDataList = modelService.getAttributeValue(model, "upcData");
		if (upcDataList != null && !upcDataList.isEmpty())
		{
			modelService.removeAll(upcDataList);
			modelService.setAttributeValue(model, "upcData", null);
		}
		final Collection<ProductFeatureModel> productFeatures = modelService.getAttributeValue(model, "features");
		if (productFeatures != null && !productFeatures.isEmpty())
		{
			modelService.removeAll(productFeatures);
			modelService.setAttributeValue(model, "features", null);
		}
		final Collection<SiteOneProductSpecificationAttributeModel> specificationAttributes = modelService.getAttributeValue(model,
				"specificationAttribute");
		if (specificationAttributes != null && !specificationAttributes.isEmpty())
		{
			modelService.removeAll(specificationAttributes);
			modelService.setAttributeValue(model, "specificationAttribute", null);
		}
		final SiteOneDataSheetModel dataSheets = modelService.getAttributeValue(model, "sheets");
		if (dataSheets != null)
		{
			modelService.removeAll(dataSheets);
		}
		modelService.setAttributeValue(model, "sheets", new SiteOneDataSheetModel());
		final Collection<SiteOneProductItemImagesModel> specialImageSheets = modelService.getAttributeValue(model,
				"specialImageType");
		if (specialImageSheets != null && !specialImageSheets.isEmpty())
		{
			modelService.removeAll(specialImageSheets);
			modelService.setAttributeValue(model, "specialImageType", null);
		}
		final MediaModel pictureModel = modelService.getAttributeValue(model, "picture");
		if (pictureModel != null)
		{
			modelService.removeAll(pictureModel);
			modelService.setAttributeValue(model, "picture", null);
		}
		final Collection<SiteOneProductItemImagesModel> itemImagesModel = modelService.getAttributeValue(model, "itemImage");
		if (itemImagesModel != null && !itemImagesModel.isEmpty())
		{
			modelService.removeAll(itemImagesModel);
			modelService.setAttributeValue(model, "itemImage", null);
		}
		final Collection<SiteOneProductItemImagesModel> brandLogoModel = modelService.getAttributeValue(model, "brandLogo");
		if (brandLogoModel != null && !brandLogoModel.isEmpty())
		{
			modelService.removeAll(brandLogoModel);
			modelService.setAttributeValue(model, "brandLogo", null);
		}
		final Collection<SiteOneProductItemImagesModel> lifeStyleModel = modelService.getAttributeValue(model, "lifeStyle");
		if (lifeStyleModel != null && !lifeStyleModel.isEmpty())
		{
			modelService.removeAll(lifeStyleModel);
			modelService.setAttributeValue(model, "lifeStyle", null);
		}
		final Collection<SiteOneProductItemImagesModel> swatchImageModel = modelService.getAttributeValue(model, "swatchImage");
		if (swatchImageModel != null && !swatchImageModel.isEmpty())
		{
			modelService.removeAll(swatchImageModel);
			modelService.setAttributeValue(model, "swatchImage", null);
		}
		final Collection<SiteOneProductItemImagesModel> specialImageTypeModel = modelService.getAttributeValue(model,
				"specialImageType");
		if (specialImageTypeModel != null && !specialImageTypeModel.isEmpty())
		{
			modelService.removeAll(specialImageTypeModel);
			modelService.setAttributeValue(model, "specialImageType", null);
		}
		
		
		final Collection<SiteOneProductSpecificationAttributeModel> specificationAttributeModel = modelService.getAttributeValue(model,
				"specificationAttribute");
		if (specificationAttributeModel != null && !specificationAttributeModel.isEmpty())
		{
			modelService.removeAll(specificationAttributeModel);
			modelService.setAttributeValue(model, "specificationAttribute", new ArrayList<SiteOneProductSpecificationAttributeModel>());
			modelService.setAttributeValue(convertedModel, "specificationAttribute", new ArrayList<SiteOneProductSpecificationAttributeModel>());
		}





	}

	public void setAttributePopulators(final List<AttributePopulator> populators)
	{
		attributePopulators = populators != null ? populators : Collections.emptyList();
	}
}