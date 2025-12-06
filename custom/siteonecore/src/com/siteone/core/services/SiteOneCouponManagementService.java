/**
 *
 */
package com.siteone.core.services;

import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.couponservices.model.AbstractCouponModel;
import de.hybris.platform.couponservices.service.data.CouponResponse;
import de.hybris.platform.couponservices.services.CouponManagementService;

import java.util.Optional;


public interface SiteOneCouponManagementService extends CouponManagementService
{
	Optional<AbstractCouponModel> getCouponForCode(String var1);

	Optional<AbstractCouponModel> getValidatedCouponForCode(String var1);

	CouponResponse verifyCouponCode(String var1, AbstractOrderModel var2);

	CouponResponse validateCouponCode(String var1, UserModel var2);

	void releaseCouponCode(String var1);

	boolean redeem(String var1, CartModel var2);

	CouponResponse redeem(String var1, OrderModel var2);


	/**
	 * @param couponCode
	 * @param order
	 * @param accountName
	 * @param orderData
	 * @param dateSubmitted
	 * @param orderNumber
	 * @param poNumber
	 * @param branchNumber
	 */
	void createCouponRedemption(String couponCode, OrderModel order, String accountName, String orderDate, String dateSubmitted,
			String orderNumber, String poNumber, String branchNumber);
}
