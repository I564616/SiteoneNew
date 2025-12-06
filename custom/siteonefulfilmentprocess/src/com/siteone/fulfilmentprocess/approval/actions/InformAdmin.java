/**
 *
 */
package com.siteone.fulfilmentprocess.approval.actions;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.process.approval.actions.AbstractSimpleB2BApproveOrderDecisionAction;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.task.RetryLaterException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.log4j.Logger;


/**
 * @author BS
 *
 */
public class InformAdmin extends AbstractSimpleB2BApproveOrderDecisionAction
{

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(InformAdmin.class);

	/** The b2b unit service. */
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Override
	public Transition executeAction(final B2BApprovalProcessModel process) throws RetryLaterException
	{
		final OrderModel order = process.getOrder();
		try
		{

			final B2BCustomerModel customer = (B2BCustomerModel) order.getUser();
			final B2BUnitModel parent = b2bUnitService.getParent(customer);
			final List<B2BCustomerModel> b2bAdminGroupUsers = new ArrayList(
					b2bUnitService.getUsersOfUserGroup(parent, B2BConstants.B2BADMINGROUP, true));
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Found [" + b2bAdminGroupUsers.size() + "] admins for unit: " + parent.getUid());
			}
			// remove the user who placed the order.
			b2bAdminGroupUsers.removeIf(Predicate.not(PredicateUtils.notPredicate(PredicateUtils.equalPredicate(customer))));

			if (CollectionUtils.isNotEmpty(b2bAdminGroupUsers))
			{
				order.setStatus(OrderStatus.ASSIGNED_TO_ADMIN);
			}
			else
			{
				order.setStatus(OrderStatus.B2B_PROCESSING_ERROR);
			}
			modelService.save(order);
			return Transition.OK;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage(), e);
			handleError(order, e);
			return Transition.NOK;
		}
	}

	protected void handleError(final OrderModel order, final Exception e)
	{

		if (order != null)
		{
			this.setOrderStatus(order, OrderStatus.B2B_PROCESSING_ERROR);
		}
	}

	/**
	 * Gets the b2b unit service.
	 *
	 * @return the b2bUnitService
	 */
	public B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2bUnitService()
	{
		return b2bUnitService;
	}

	/**
	 * Sets the b2b unit service.
	 *
	 * @param b2bUnitService
	 *           the b2bUnitService to set
	 */
	public void setB2bUnitService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}
}