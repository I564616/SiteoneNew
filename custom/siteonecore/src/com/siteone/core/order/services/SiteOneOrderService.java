/**
 *
 */
package com.siteone.core.order.services;

import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.siteone.core.model.OrderReadyForPickUpRemainderEmailCronJobModel;
import com.siteone.core.model.OrderStatusEmailCronJobModel;
import com.siteone.core.model.OrderStatusNotificationCronJobModel;
import com.siteone.core.model.QuoteToOrderStatusEmailCronJobModel;


/**
 * @author 1099417
 *
 */
public interface SiteOneOrderService
{
	public void sendOrderStatusEmail(OrderStatusEmailCronJobModel orderStatusEmailCronJobModel) throws IOException;
	
	public void sendQuoteToOrderStatusEmail(QuoteToOrderStatusEmailCronJobModel quoteToOrderStatusEmailCronJobModel) throws IOException;
	
	public void sendOrderReadyForPickUpRemainderEmail(OrderReadyForPickUpRemainderEmailCronJobModel orderReadyForPickUpRemainderEmailCronJobModel) throws IOException;

	public void sendOrderStatusNotification(OrderStatusNotificationCronJobModel orderStatusNotificationCronJobModel)
			throws IOException;

	public String getOrderingAccountforOrder(final String orderId);

	public Date getPurchasedDateforOrder(final String orderId);

	List<OrderModel> getLastTwoHybrisOrdersForCustomer(UserModel userModel);

	void updateHubStorePosForParcelShippingOrder(CartModel cartModel);
	
	public Map<String, Boolean> populateFulfilmentStatus(final OrderModel orderModel);
	
	OrderModel getOrderForCode(final String orderNumber);	
	
}