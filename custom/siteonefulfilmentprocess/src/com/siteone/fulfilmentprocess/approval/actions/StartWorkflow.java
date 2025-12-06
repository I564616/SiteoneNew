/**
 *
 */
package com.siteone.fulfilmentprocess.approval.actions;

import de.hybris.platform.b2b.enums.WorkflowTemplateType;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.process.approval.actions.AbstractProceduralB2BOrderAproveAction;
import de.hybris.platform.b2b.process.approval.actions.B2BPermissionResultHelperImpl;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2b.services.B2BEscalationService;
import de.hybris.platform.b2b.services.B2BWorkflowIntegrationService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.workflow.WorkflowProcessingService;
import de.hybris.platform.workflow.WorkflowService;
import de.hybris.platform.workflow.model.WorkflowModel;
import de.hybris.platform.workflow.model.WorkflowTemplateModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author BS
 *
 */
public class StartWorkflow extends AbstractProceduralB2BOrderAproveAction
{
	private B2BWorkflowIntegrationService b2bWorkflowIntegrationService;
	private WorkflowProcessingService workflowProcessingService;
	private WorkflowService workflowService;
	private B2BEscalationService b2bEscalationService;
	private B2BPermissionResultHelperImpl permissionResultHelper;

	private static final Logger LOG = Logger.getLogger(StartWorkflow.class);
	private boolean escalate;

	@Override
	public void executeAction(final B2BApprovalProcessModel aprovalProcess) throws RetryLaterException
	{
		try
		{
			final OrderModel order = aprovalProcess.getOrder();
			final B2BCustomerModel customer = (B2BCustomerModel) order.getUser();
			final List<B2BCustomerModel> approvers = new ArrayList<B2BCustomerModel>(customer.getApprovers());
			if (LOG.isDebugEnabled())
			{
				final List<String> approverUids = new ArrayList<String>();
				for (final B2BCustomerModel b2bCustomerModel : approvers)
				{
					approverUids.add(b2bCustomerModel.getUid());
				}
				LOG.debug(String.format("Creating a worflow for order %s and approvers %s", order.getCode(), approverUids));
			}
			final String workflowTemplateCode = "B2B_APPROVAL_WORKFLOW_FOR_" + customer.getGuid();
			final WorkflowTemplateModel workflowTemplate = b2bWorkflowIntegrationService.createWorkflowTemplate(approvers,
					workflowTemplateCode, "Generated B2B Order Approval Workflow", WorkflowTemplateType.ORDER_APPROVAL);

			final WorkflowModel workflow = workflowService.createWorkflow(workflowTemplate.getName(), workflowTemplate,
					Collections.<ItemModel> singletonList(aprovalProcess), workflowTemplate.getOwner());
			workflowProcessingService.startWorkflow(workflow);
			this.modelService.saveAll(); // workaround for PLA-10938
			order.setWorkflow(workflow);
			order.setStatus(OrderStatus.PENDING_APPROVAL);
			order.setExhaustedApprovers(new HashSet<B2BCustomerModel>(approvers));
			this.modelService.save(order);

			if (escalate)
			{
				b2bEscalationService.scheduleEscalationTask(order);
			}

		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage(), e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * Sets the b2b workflow integration service.
	 *
	 * @param b2bWorkflowIntegrationService
	 *           the b2bWorkflowIntegrationService to set
	 */
	public void setB2bWorkflowIntegrationService(final B2BWorkflowIntegrationService b2bWorkflowIntegrationService)
	{
		this.b2bWorkflowIntegrationService = b2bWorkflowIntegrationService;
	}

	/**
	 * Sets the b2b escalation service.
	 *
	 * @param b2bEscalationService
	 *           the b2bEscalationService to set
	 */
	public void setB2bEscalationService(final B2BEscalationService b2bEscalationService)
	{
		this.b2bEscalationService = b2bEscalationService;
	}

	/**
	 * @param permissionResultHelper
	 *           the permissionResultHelper to set
	 */
	public void setPermissionResultHelper(final B2BPermissionResultHelperImpl permissionResultHelper)
	{
		this.permissionResultHelper = permissionResultHelper;
	}

	public void setWorkflowProcessingService(final WorkflowProcessingService workflowProcessingService)
	{
		this.workflowProcessingService = workflowProcessingService;
	}

	public void setWorkflowService(final WorkflowService workflowService)
	{
		this.workflowService = workflowService;
	}

	/**
	 * @return the escalate
	 */
	public boolean isEscalate()
	{
		return escalate;
	}

	/**
	 * @param escalate
	 *           the escalate to set
	 */
	public void setEscalate(final boolean escalate)
	{
		this.escalate = escalate;
	}
}
