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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;


/**
 * @author BS
 *
 */
public class FindApprovers extends AbstractSimpleB2BApproveOrderDecisionAction
{
	private static final Logger LOG = Logger.getLogger(FindApprovers.class);
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService;

	@Override
	public Transition executeAction(final B2BApprovalProcessModel approvalProcess) throws RetryLaterException
	{
		OrderModel order = null;
		try
		{
			order = approvalProcess.getOrder();

			final B2BCustomerModel customer = (B2BCustomerModel) order.getUser();
			final B2BUnitModel parent = b2bUnitService.getParent(customer);
			final List<B2BCustomerModel> b2bAdminGroupUsers = new ArrayList(
					b2bUnitService.getUsersOfUserGroup(parent, B2BConstants.B2BADMINGROUP, true));

			if (CollectionUtils.isNotEmpty(b2bAdminGroupUsers))
			{
				final Set<B2BCustomerModel> b2bAdmins = new HashSet<>(b2bAdminGroupUsers);
				customer.setApprovers(b2bAdmins);
				this.modelService.save(customer);
				this.modelService.save(order);
				return Transition.OK;
			}
			return Transition.NOK;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage(), e);
			this.handleError(order, e);
		}
		return Transition.NOK;
	}

	protected void handleError(final OrderModel order, final Exception e)
	{

		if (order != null)
		{
			this.setOrderStatus(order, OrderStatus.B2B_PROCESSING_ERROR);
		}
		LOG.error(e.getMessage(), e);
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
