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
package com.siteone.fulfilmentprocess.actions.order;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.storelocator.model.OpeningScheduleModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.storelocator.model.WeekdayOpeningDayModel;
import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.util.Config;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import com.siteone.core.services.SiteOneStockLevelService;
import com.siteone.fulfilmentprocess.actions.facade.SiteOneCheckNearbyActionFacade;


/**
 * This example action checks the order for required data in the business process. Skipping this action may result in
 * failure in one of the subsequent steps of the process. The relation between the order and the business process is
 * defined in basecommerce extension through item OrderProcess. Therefore if your business process has to access the
 * order (a typical case), it is recommended to use the OrderProcess as a parentClass instead of the plain
 * BusinessProcess.
 */
public class checkNearbyOrderSubmitWindowAction extends AbstractAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(checkNearbyOrderSubmitWindowAction.class);

	private SiteOneCheckNearbyActionFacade siteOneCheckNearbyActionFacade;

	/**
	 * @return the siteOneCheckNearbyActionFacade
	 */
	public SiteOneCheckNearbyActionFacade getSiteOneCheckNearbyActionFacade()
	{
		return siteOneCheckNearbyActionFacade;
	}

	/**
	 * @param siteOneCheckNearbyActionFacade
	 *           the siteOneCheckNearbyActionFacade to set
	 */
	public void setSiteOneCheckNearbyActionFacade(final SiteOneCheckNearbyActionFacade siteOneCheckNearbyActionFacade)
	{
		this.siteOneCheckNearbyActionFacade = siteOneCheckNearbyActionFacade;
	}

	@Resource(name = "b2bCustomerService")
	private B2BCustomerService b2bCustomerService;

	@Resource(name = "b2bUnitService")
	private B2BUnitService b2bUnitService;
	
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
	public String execute(ConsignmentProcessModel consignmentProcessModel) throws RetryLaterException, Exception {
		final ConsignmentModel consignment = consignmentProcessModel.getConsignment();
		final AbstractOrderModel order = consignment.getOrder();

		if (CustomerType.GUEST.equals(((CustomerModel) order.getUser()).getType()))
		{
			final B2BCustomerModel customerModel = (B2BCustomerModel) b2bCustomerService
					.getUserForUID(order.getGuestContactPerson().getContactEmail().trim().toLowerCase());
			if (null != customerModel)
			{
				((CustomerModel) order.getUser()).setType(CustomerType.REGISTERED);
				order.setUser(customerModel);
				if(null != customerModel.getCustomerID() || null != customerModel.getDefaultB2BUnit().getUid())
				{
					order.setOrderingAccount((B2BUnitModel) b2bUnitService.getUnitForUid(customerModel.getDefaultB2BUnit().getUid()));
					if(null != (B2BUnitModel) b2bUnitService.getUnitForUid(customerModel.getDefaultB2BUnit().getUid()))
					{
						order.setUnit((B2BUnitModel) b2bUnitService.getUnitForUid(customerModel.getDefaultB2BUnit().getUid()));
					}
				}
				if(customerModel.getDefaultB2BUnit() != null)
				{
					B2BUnitModel b2bUnit = customerModel.getDefaultB2BUnit();
					final Collection<AddressModel> shippingAddresses = b2bUnit.getAddresses();
					if (CollectionUtils.isNotEmpty(shippingAddresses))
					{
						for (final AddressModel address : shippingAddresses)
						{
							if (address.getVisibleInAddressBook() != null && address.getVisibleInAddressBook().booleanValue())
							{
								order.setDeliveryAddress(address);
								break;
							}
						}
					}
				}
				order.setContactPerson(customerModel);
				consignment.setOrder(order);
				consignmentProcessModel.setConsignment(consignment);
				modelService.save(consignmentProcessModel);
				modelService.save(order);
			}
		}

		if (consignment == null)
		{
			LOG.error("Missing the consignment, exiting the process");
			return Transition.ERROR.toString();
		}

		PointOfServiceModel store = consignment.getDeliveryPointOfService();
		ZoneId zoneId = ZoneId.of(store.getTimezoneId());
		LocalTime localTime = LocalTime.now(zoneId);
		LocalDate today = LocalDate.now(zoneId);
		int totalMinutes = 0;
		final OpeningScheduleModel openingSchedule = store.getOpeningSchedule();
		final List<WeekdayOpeningDayModel> openingDays = siteOneCheckNearbyActionFacade
				.getWeekdayOpeningDayModel(openingSchedule.getPk().toString());
		
		DayOfWeek saturday =	DayOfWeek.SATURDAY;
		DayOfWeek sunday =	DayOfWeek.SUNDAY;
		DayOfWeek dayOfWeek = today.getDayOfWeek();
		 
		if (CollectionUtils.isNotEmpty(openingDays))
		{
			for (final WeekdayOpeningDayModel openDay : openingDays)
			{
				if (openDay.getClosingTime() != null
						&& openDay.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())
						&& !dayOfWeek.toString().equalsIgnoreCase(saturday.toString()) && !dayOfWeek.toString().equalsIgnoreCase(sunday.toString()))
				{
					totalMinutes = Config.getInt("order.nearby.submit.cutoff", 900);
					break;
				}
				else if(openDay.getClosingTime() != null
						&& openDay.getDayOfWeek().toString().equalsIgnoreCase(dayOfWeek.toString())
						&& (dayOfWeek.toString().equalsIgnoreCase(saturday.toString()) || dayOfWeek.toString().equalsIgnoreCase(sunday.toString())))
				{
					totalMinutes = Config.getInt("order.nearby.submit.cutoff.weekend", 600);
					break;
				}
			}
		}
		else 
		{
			return Transition.NOK.toString();
		}
				
		final int current_hour_Minutes = localTime.getHour() * 60;
		final int current_Minutes = localTime.getMinute();
		final int total_Current_Minutes = current_hour_Minutes + current_Minutes;


		final int nearbyOrderSubmitAllow = Config.getInt("order.nearby.submit.allow", 300);


		if (total_Current_Minutes < totalMinutes && total_Current_Minutes > nearbyOrderSubmitAllow)
		{
			return Transition.OK.toString();
		}
		else
		{
			return Transition.NOK.toString();
		}
	}

	@Override
	public Set<String> getTransitions() {
		return CheckNearbyOrderAction.Transition.getStringValues();
	}
}
