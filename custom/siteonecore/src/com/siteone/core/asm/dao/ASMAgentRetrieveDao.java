/**
 *
 */
package com.siteone.core.asm.dao;

import de.hybris.platform.core.model.user.EmployeeModel;

import java.util.List;


public interface ASMAgentRetrieveDao
{
	public List<EmployeeModel> getAgentPk(String customerPk);
}
