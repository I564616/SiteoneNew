/**
 *
 */
package com.siteone.core.batch.adapter.impl;

import de.hybris.platform.commerceservices.model.storelocator.StoreLocatorFeatureModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.storelocator.PointOfServiceDao;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.siteone.core.batch.adapter.StoreSpecialtyAdapter;
import com.siteone.core.store.dao.SiteOnePointOfServiceDao;


/**
 * @author PElango
 *
 */
public class DefaultStoreSpecialtyAdapter implements StoreSpecialtyAdapter
{

	private PointOfServiceDao pointOfServiceDao;
	private ModelService modelService;

	@Override
	public void performImport(final String cellValue, final Item pointOfService)
	{
		final PointOfServiceModel pointOfServiceModel = modelService.get(pointOfService);
		if (!cellValue.contains("<ignore>") && !cellValue.isEmpty())
		{
			final List<String> storeSpecialties = Stream.of(cellValue.split(",")).map(String::trim).collect(Collectors.toList());
			final List<StoreLocatorFeatureModel> storeFeatureModelList = ((SiteOnePointOfServiceDao) pointOfServiceDao)
					.getStoreSpecialtyDetails();
			if (storeFeatureModelList != null && !storeFeatureModelList.isEmpty())
			{
				final Set<StoreLocatorFeatureModel> storeFeatures = new HashSet<>();
				storeFeatureModelList.forEach(storeFeatureModel -> {
					if (storeSpecialties.contains(storeFeatureModel.getCode()))
					{
						storeFeatures.add(storeFeatureModel);
					}
				});
				pointOfServiceModel.setFeatures(storeFeatures);
				modelService.save(pointOfServiceModel);
			}
		}

	}

	/**
	 * @return the pointOfServiceDao
	 */
	public PointOfServiceDao getPointOfServiceDao()
	{
		return pointOfServiceDao;
	}

	/**
	 * @param pointOfServiceDao
	 *           the pointOfServiceDao to set
	 */
	public void setPointOfServiceDao(final PointOfServiceDao pointOfServiceDao)
	{
		this.pointOfServiceDao = pointOfServiceDao;
	}

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
}
