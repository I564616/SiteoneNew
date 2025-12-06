/**
 *
 */
package com.siteone.facade.order.impl;

import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BOrderApprovalData;
import de.hybris.platform.b2bacceleratorfacades.order.impl.DefaultB2BOrderFacade;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.workflow.enums.WorkflowActionType;
import de.hybris.platform.workflow.model.WorkflowActionModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;


/**
 * @author BS
 *
 */
public class DefaultSiteOneB2BOrderFacade extends DefaultB2BOrderFacade
{

	@Override
	public SearchPageData<B2BOrderApprovalData> getPagedOrdersForApproval(final WorkflowActionType[] actionTypes,
			final PageableData pageableData)
	{

		final List<WorkflowActionModel> updatedActions = new ArrayList<>();
		final SearchPageData<WorkflowActionModel> actions = getPagedB2BWorkflowActionDao()
				.findPagedWorkflowActionsByUserAndActionTypes(getUserService().getCurrentUser(), actionTypes, pageableData);
		for (final WorkflowActionModel workflowActionModel : actions.getResults())
		{
			final B2BApprovalProcessModel attachment = (B2BApprovalProcessModel) CollectionUtils
					.find(workflowActionModel.getAttachmentItems(), PredicateUtils.instanceofPredicate(B2BApprovalProcessModel.class));
			if (null != attachment.getOrder())
			{
				updatedActions.add(workflowActionModel);
			}
		}
		actions.setResults(updatedActions);
		return convertPageData(actions, getB2bOrderApprovalDashboardConverter());
	}
}
