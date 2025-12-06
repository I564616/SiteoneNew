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
package com.siteone.fulfilmentprocess.service;

import com.siteone.core.constants.GeneratedSiteoneCoreConstants;
import com.siteone.core.enums.OrderTypeEnum;
import com.siteone.core.services.SiteOneStockLevelService;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Default implementation of {@link CheckNearbyOrderService}
 */
public class DefaultCheckNearbyOrderService implements CheckNearbyOrderService
{
	private static final Logger LOG = Logger.getLogger(DefaultCheckNearbyOrderService.class);

	private ModelService modelService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "siteOneStockLevelService")
	private SiteOneStockLevelService siteOneStockLevelService;

	@Override
	public boolean checkNearbyOrderHold(final ConsignmentModel consignment)
	{
		PointOfServiceModel  pointOfServiceModel = consignment.getDeliveryPointOfService();
		List <WarehouseModel> warehouseModelList = pointOfServiceModel.getWarehouses();
		WarehouseModel homeWarehouse = null;
		boolean isNearbyOrder = false;
 
		for (WarehouseModel warehouseModel : warehouseModelList)
		{
			if (pointOfServiceModel.getStoreId().equalsIgnoreCase(warehouseModel.getCode()))
			{
				homeWarehouse = warehouseModel;
				break;
			}
		}

		Set<ConsignmentEntryModel> consignmentEntries = consignment.getConsignmentEntries();

		for (ConsignmentEntryModel consignmentEntry : consignmentEntries) {
			final StockLevelModel stockLevelModel = siteOneStockLevelService.getStockLevelForWarehouse(consignmentEntry.getOrderEntry().getProduct().getCode(), homeWarehouse);
			if (null == stockLevelModel || (consignmentEntry.getQuantity() > stockLevelModel.getAvailable())) {
				//this order has nearby inventory
				isNearbyOrder = true;
				break;
			}
		}

		return isNearbyOrder;
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