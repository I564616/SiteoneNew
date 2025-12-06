/**
 *
 */
package com.siteone.facades.order.converters.populator;

import de.hybris.platform.commercefacades.order.converters.populator.OrderEntryGroupPopulator;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeAndBranchOrderEntryGroupData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.data.OrderEntryGroupData;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author SNavamani
 *
 */

public class DeliveryModeAndBranchOrderEntryGroupPopulator extends OrderEntryGroupPopulator {
	private Converter<DeliveryModeModel, DeliveryModeData> deliveryModeConverter;
	private Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void populate(final AbstractOrderModel source, final AbstractOrderData target) throws ConversionException {
		super.populate(source, target);

		if (target.getDeliveryModeAndBranchOrderGroups() == null) {
			target.setDeliveryModeAndBranchOrderGroups(new ArrayList<DeliveryModeAndBranchOrderEntryGroupData>());
		}

		for (final AbstractOrderEntryModel entryModel : source.getEntries()) {
			createUpdateDeliveryModeAndBranchGroupData(entryModel, target);

		}

		// soryByDeliveryPointOfService(target);
		sortGroupByDeliveryMode(target);

		for (final OrderEntryGroupData orderEntryGroup : target.getDeliveryModeAndBranchOrderGroups()) {
			// orderEntryGroup.setQuantity(Long.valueOf(sumOrderGroupQuantity(orderEntryGroup)));
		}

		// target.setPickupItemsQuantity(Long.valueOf(sumPickupItemsQuantity(source)));
	}

	protected void createUpdateDeliveryModeAndBranchGroupData(final AbstractOrderEntryModel entryModel,
			final AbstractOrderData target) {
		final DeliveryModeModel deliveryModeModel = entryModel.getDeliveryMode();
		if (deliveryModeModel != null) {
			DeliveryModeAndBranchOrderEntryGroupData groupData = null;
			for (final DeliveryModeAndBranchOrderEntryGroupData listGroupData : target
					.getDeliveryModeAndBranchOrderGroups()) {
				if (deliveryModeModel.getCode().equals(listGroupData.getDeliveryMode().getCode())) {
					groupData = listGroupData;
					break;
				}
			}
			if (groupData == null) {
				groupData = new DeliveryModeAndBranchOrderEntryGroupData();
				final DeliveryModeData deliveryModeData = getDeliveryModeConverter().convert(deliveryModeModel);
				groupData.setDeliveryMode(deliveryModeData);
				groupData.setEntries(new ArrayList<OrderEntryData>());
				// groupData.setDistance(pointOfServiceData.getDistanceKm());
				target.getDeliveryModeAndBranchOrderGroups().add(groupData);
			}

			// updateGroupTotalPriceWithTax(entryModel, groupData);
			groupData.getEntries().add(getOrderEntryData(target, entryModel.getEntryNumber()));
		}
	}

	protected void soryByDeliveryPointOfService(final AbstractOrderData target) {

		for (final DeliveryModeAndBranchOrderEntryGroupData listGroupData : target
				.getDeliveryModeAndBranchOrderGroups()) {
			Collections.sort((ArrayList) listGroupData.getEntries(),
					(OrderEntryData a1, OrderEntryData a2) -> (a1.getDeliveryPointOfService().getName())
							.compareToIgnoreCase(a2.getDeliveryPointOfService().getName()));
			/*
			 * (listGroupData.getEntries()) .sort((OrderEntryData a1, OrderEntryData a2) ->
			 * (a1.getDeliveryPointOfService().getName())
			 * .compareTo(a2.getDeliveryPointOfService().getName()));
			 */

		}
	}

	protected void sortGroupByDeliveryMode(final AbstractOrderData target) {

		(target.getDeliveryModeAndBranchOrderGroups()).sort((DeliveryModeAndBranchOrderEntryGroupData a1, DeliveryModeAndBranchOrderEntryGroupData a2) -> Integer.compare(a1.getDeliveryMode().getCode().length(),a2.getDeliveryMode().getCode().length()));
							
	}
	
	/**
	 * @return the deliveryModeConverter
	 */
	public Converter<DeliveryModeModel, DeliveryModeData> getDeliveryModeConverter() {
		return deliveryModeConverter;
	}

	/**
	 * @param deliveryModeConverter the deliveryModeConverter to set
	 */
	public void setDeliveryModeConverter(final Converter<DeliveryModeModel, DeliveryModeData> deliveryModeConverter) {
		this.deliveryModeConverter = deliveryModeConverter;
	}

	/**
	 * @return the pointOfServiceSiteOneConverter
	 */
	public Converter<PointOfServiceModel, PointOfServiceData> getPointOfServiceSiteOneConverter() {
		return pointOfServiceSiteOneConverter;
	}

	/**
	 * @param pointOfServiceSiteOneConverter the pointOfServiceSiteOneConverter to
	 *                                       set
	 */
	public void setPointOfServiceSiteOneConverter(
			final Converter<PointOfServiceModel, PointOfServiceData> pointOfServiceSiteOneConverter) {
		this.pointOfServiceSiteOneConverter = pointOfServiceSiteOneConverter;
	}

}