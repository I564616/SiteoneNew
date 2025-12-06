
/**
 *
 */
package com.siteone.core.services.impl;

/**
 * @author AA04994
 *
 */

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.couponservices.model.AbstractCouponModel;
import de.hybris.platform.couponservices.model.CouponRedemptionModel;
import de.hybris.platform.couponservices.services.impl.DefaultCouponManagementService;

import java.util.Optional;


public class DefaultSiteOneCouponManagementService extends DefaultCouponManagementService
{

	@Override
	protected void createCouponRedemption(final String couponCode, final OrderModel order)
	{


		final Optional<AbstractCouponModel> couponModel = this.findCoupon(couponCode, order);
		final CouponRedemptionModel couponRedemption = (CouponRedemptionModel) this.getModelService()
				.create(CouponRedemptionModel.class);
		this.findCoupon(couponCode, order);
		couponRedemption.setCouponCode(couponCode);
		couponRedemption.setOrder(order);
		if(couponModel.isPresent()){
			couponRedemption.setCoupon(couponModel.get());
		}
		couponRedemption.setUser(order.getUser());
		if (order.getContactPerson() != null && order.getContactPerson().getDefaultB2BUnit() != null)
		{
			couponRedemption.setAccountName(order.getContactPerson().getDefaultB2BUnit().getUid()+" - "+order.getContactPerson().getDefaultB2BUnit().getName());
		}
		couponRedemption.setOrderDate(order.getCreationtime());
		couponRedemption.setOrderNumber(order.getCode());
		couponRedemption.setPoNumber(order.getPurchaseOrderNumber());
		if (order.getPointOfService() != null)
		{
			couponRedemption.setBranchNumber(order.getPointOfService().getStoreId());
		}
		this.getModelService().save(couponRedemption);

	}
}

