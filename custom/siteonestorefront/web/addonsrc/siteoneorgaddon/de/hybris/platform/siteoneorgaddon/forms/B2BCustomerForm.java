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
package de.hybris.platform.siteoneorgaddon.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdateProfileForm;
import de.hybris.platform.b2bapprovalprocessfacades.company.data.B2BPermissionData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUserGroupData;
import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


/**
 * Pojo for 'B2BCustomer' form.
 */
public class B2BCustomerForm extends UpdateProfileForm
{
	private boolean active;
	private String uid;
	private String parentB2BUnit;
	private String roles;
	private Collection<CustomerData> approvers;
	private Collection<B2BUserGroupData> approverGroups;
	private Collection<B2BUserGroupData> permissionGroups;
	private Collection<B2BPermissionData> permissions;
	private String email;
	private boolean invoicePermissions;
	private boolean partnerProgramPermissions;
	private boolean accountOverviewForParent;
	private boolean accountOverviewForShipTos;
	private Collection<String> assignedShipTo = new ArrayList<String>();
	private String phoneNumber;
	private boolean payBillOnline;
	private boolean placeOrder;
	private String langPreference;
	private boolean enableAddModifyDeliveryAddress;
	private boolean needsOrderApproval;
	
	/**
	 * @return the accountOverviewForParent
	 */
	public boolean isAccountOverviewForParent()
	{
		return accountOverviewForParent;
	}

	/**
	 * @param accountOverviewForParent the accountOverviewForParent to set
	 */
	public void setAccountOverviewForParent(boolean accountOverviewForParent)
	{
		this.accountOverviewForParent = accountOverviewForParent;
	}

	/**
	 * @return the accountOverviewForShipTos
	 */
	public boolean isAccountOverviewForShipTos()
	{
		return accountOverviewForShipTos;
	}

	/**
	 * @param accountOverviewForShipTos the accountOverviewForShipTos to set
	 */
	public void setAccountOverviewForShipTos(boolean accountOverviewForShipTos)
	{
		this.accountOverviewForShipTos = accountOverviewForShipTos;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * @return the langPreference
	 */
	public String getLangPreference()
	{
		return langPreference;
	}

	/**
	 * @param langPreference the langPreference to set
	 */
	public void setLangPreference(String langPreference)
	{
		this.langPreference = langPreference;
	}
	
	public Collection<String> getAssignedShipTo()
	{
		return assignedShipTo;
	}

	public void setAssignedShipTo(final Collection<String> assignedShipTo)
	{
		this.assignedShipTo = assignedShipTo;
	}


	
	/**
	 * @return the invoicePermissions
	 */
	public boolean isInvoicePermissions()
	{
		return invoicePermissions;
	}



	/**
	 * @param invoicePermissions the invoicePermissions to set
	 */
	public void setInvoicePermissions(boolean invoicePermissions)
	{
		this.invoicePermissions = invoicePermissions;
	}


	/**
	 * @return the partnerProgramPermissions
	 */
	public boolean isPartnerProgramPermissions()
	{
		return partnerProgramPermissions;
	}



	/**
	 * @param partnerProgramPermissions the partnerProgramPermissions to set
	 */
	public void setPartnerProgramPermissions(boolean partnerProgramPermissions)
	{
		this.partnerProgramPermissions = partnerProgramPermissions;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(final String uid)
	{
		this.uid = uid;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(final boolean active)
	{
		this.active = active;
	}

	public Collection<CustomerData> getApprovers()
	{
		return approvers;
	}

	public void setApprovers(final Collection<CustomerData> approvers)
	{
		this.approvers = approvers;
	}

	public Collection<B2BUserGroupData> getApproverGroups()
	{
		return approverGroups;
	}

	public void setApproverGroups(final Collection<B2BUserGroupData> approverGroups)
	{
		this.approverGroups = approverGroups;
	}

	public Collection<B2BUserGroupData> getPermissionGroups()
	{
		return permissionGroups;
	}

	public void setPermissionGroups(final Collection<B2BUserGroupData> permissionGroups)
	{
		this.permissionGroups = permissionGroups;
	}

	public Collection<B2BPermissionData> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(final Collection<B2BPermissionData> permissions)
	{
		this.permissions = permissions;
	}

	/**
	 * @return the roles
	 */
	public String getRoles()
	{
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles)
	{
		this.roles = roles;
	}

	public String getParentB2BUnit()
	{
		return parentB2BUnit;
	}

	public void setParentB2BUnit(final String parentB2BUnit)
	{
		this.parentB2BUnit = parentB2BUnit;
	}

	@NotNull(message = "{profile.email.invalid}")
	@Size(min = 1, max = 255, message = "{profile.email.invalid}")
	@Email(message = "{profile.email.invalid}")
	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}
	
	/**
	 * @return the payBillOnline
	 */
	public boolean isPayBillOnline()
	{
		return payBillOnline;
	}

	/**
	 * @param payBillOnline the payBillOnline to set
	 */
	public void setPayBillOnline(boolean payBillOnline)
	{
		this.payBillOnline = payBillOnline;
	}
	
	/**
	 * @return the placeOrder
	 */
	public boolean isPlaceOrder()
	{
		return placeOrder;
	}

	/**
	 * @param orderOnlinePermissions the orderOnlinePermissions to set
	 */
	public void setPlaceOrder(boolean placeOrder)
	{
		this.placeOrder = placeOrder;
	}
	
	/**
	 * @return the enableAddModifyDeliveryAddress
	 */
	public boolean isEnableAddModifyDeliveryAddress()
	{
		return enableAddModifyDeliveryAddress;
	}

	/**
	 * @param enableAddModifyDeliveryAddress the enableAddModifyDeliveryAddress to set
	 */
	public void setEnableAddModifyDeliveryAddress(boolean enableAddModifyDeliveryAddress)
	{
		this.enableAddModifyDeliveryAddress = enableAddModifyDeliveryAddress;
	}

	/**
	 * @return the needsOrderApproval
	 */
	public boolean isNeedsOrderApproval()
	{
		return needsOrderApproval;
	}

	/**
	 * @param needsOrderApproval the needsOrderApproval to set
	 */
	public void setNeedsOrderApproval(boolean needsOrderApproval)
	{
		this.needsOrderApproval = needsOrderApproval;
	}

}
