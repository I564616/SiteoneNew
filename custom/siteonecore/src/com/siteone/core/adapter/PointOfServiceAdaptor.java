/**
 *
 */
package com.siteone.core.adapter;

import de.hybris.platform.storelocator.model.PointOfServiceModel;


/**
 * @author 1099417
 *
 */
public interface PointOfServiceAdaptor
{
	PointOfServiceModel getStoreDetailByPK(String storePK);
}
