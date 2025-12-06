/**
 *
 */
package com.siteone.fulfilmentprocess.actions.order;

import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.SiteOnePaymentInfoData;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.payment.SiteoneCreditCardPaymentInfoModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.task.RetryLaterException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.checkout.facades.utils.SiteOnePaymentUtil;
import com.siteone.core.ewallet.cache.SiteOneFeatureSwitchCacheService;
import com.siteone.facade.payment.cayan.SiteOneCayanPaymentFacade;


/**
 *
 */
public class CapturePaymentProcessAction extends AbstractAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(CapturePaymentProcessAction.class);

	@Resource(name = "siteOneCayanPaymentFacade")
	private SiteOneCayanPaymentFacade siteOneCayanPaymentFacade;

	@Resource(name = "siteOnePaymentUtil")
	private SiteOnePaymentUtil siteOnePaymentUtil;

	@Resource(name = "sessionService")
	private SessionService sessionService;
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;
	@Resource(name = "siteOnePaymentInfoDataReverseConverter")
	private Converter<SiteOnePaymentInfoData, SiteoneCreditCardPaymentInfoModel> siteOnePaymentInfoDataReverseConverter;

	@Resource(name = "siteOneFeatureSwitchService")
	private SiteOneFeatureSwitchCacheService siteOneFeatureSwitchCacheService;

	public enum Transition
	{
		OK, NOK, ERROR;

		public static Set<String> getStringValues()
		{
			final Set<String> res = new HashSet<String>();

			for (final CheckNearbyOrderAction.Transition transition : CheckNearbyOrderAction.Transition.values())
			{
				res.add(transition.toString());
			}
			return res;
		}
	}

	@Override
	public String execute(final ConsignmentProcessModel consignmentProcessModel) throws RetryLaterException, Exception
	{
		final ConsignmentModel consignment = consignmentProcessModel.getConsignment();
		final AbstractOrderModel order = consignment.getOrder();

		//Split mixed more than 1 order
		if (order != null && CollectionUtils.isNotEmpty(order.getPaymentInfoList())
				&& order.getPaymentInfoList().get(0).getPaymentType().equalsIgnoreCase("3"))
		{
			final PointOfServiceData sessionPOS = sessionService.getAttribute("sessionStore");
			final PointOfServiceModel pos = consignment.getDeliveryPointOfService();
			String hubStore = null;
			if (pos != null)
			{
				hubStore = pos.getStoreId();
			}
			else if (sessionPOS != null && sessionPOS.getHubStores() != null && sessionPOS.getHubStores().get(0) != null)
			{
				hubStore = sessionPOS.getHubStores().get(0);
			}
			final boolean flag = hubStore != null && consignment.getDeliveryMode() != null
					&& consignment.getDeliveryMode().getCode().equalsIgnoreCase("free-standard-shipping")
					&& siteOneFeatureSwitchCacheService.isBranchPresentUnderFeatureSwitch("CCEnabledDCShippingBranches", hubStore)
					&& order.getConsignments().size() > 1;

			if (flag)
			{
				LOG.error("Cayan capture consignment code " + consignment.getCode());
				final SiteoneCreditCardPaymentInfoModel creditCardPaymentInfo = siteOnePaymentUtil
						.processCayanCapturePayment(consignment, order.getPaymentInfoList().get(0));
				creditCardPaymentInfo.setUser(order.getUser());
				creditCardPaymentInfo.setCode(order.getCode());
				getModelService().save(creditCardPaymentInfo);
				final List<SiteoneCreditCardPaymentInfoModel> list = new ArrayList<>(order.getPaymentInfoList());
				list.add(creditCardPaymentInfo);
				order.setPaymentInfoList(list);
				getModelService().save(order);
			}
		}

		return Transition.OK.toString();
	}

	@Override
	public Set<String> getTransitions()
	{
		return CheckNearbyOrderAction.Transition.getStringValues();
	}
}
