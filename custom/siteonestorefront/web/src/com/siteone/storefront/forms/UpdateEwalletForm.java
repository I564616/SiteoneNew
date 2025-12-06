/**
 *
 */
package com.siteone.storefront.forms;

import java.util.List;


/**
 * @author KArasan
 *
 */
public class UpdateEwalletForm
{
	private String vaultToken;
	private List<String> CustUids;
	private String operationType;

	/**
	 * @return the vaultToken
	 */
	public String getVaultToken()
	{
		return vaultToken;
	}

	/**
	 * @param vaultToken
	 *           the vaultToken to set
	 */
	public void setVaultToken(final String vaultToken)
	{
		this.vaultToken = vaultToken;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType()
	{
		return operationType;
	}

	/**
	 * @param operationType
	 *           the operationType to set
	 */
	public void setOperationType(final String operationType)
	{
		this.operationType = operationType;
	}

	/**
	 * @return the custUid
	 */
	public List<String> getCustUids()
	{
		return CustUids;
	}

	/**
	 * @param custUid
	 *           the custUid to set
	 */
	public void setCustUid(final List<String> custUids)
	{
		CustUids = custUids;
	}
}
