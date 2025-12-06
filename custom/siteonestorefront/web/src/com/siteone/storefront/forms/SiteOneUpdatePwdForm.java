/**
 *
 */
package com.siteone.storefront.forms;

/**
 * @author 1099417
 *
 */
public class SiteOneUpdatePwdForm
{
	private String pwd;
	private String checkPwd;
	private String token;
	private String stateToken;

	/**
	 * @return the pwd
	 */

	public String getPwd()
	{
		return pwd;
	}

	/**
	 * @return the checkPwd
	 */
	public String getCheckPwd()
	{
		return checkPwd;
	}

	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * @return the stateToken
	 */
	public String getStateToken()
	{
		return stateToken;
	}

	/**
	 * @param pwd
	 *           the pwd to set
	 */
	public void setPwd(final String pwd)
	{
		this.pwd = pwd;
	}

	/**
	 * @param checkPwd
	 *           the checkPwd to set
	 */
	public void setCheckPwd(final String checkPwd)
	{
		this.checkPwd = checkPwd;
	}

	/**
	 * @param token
	 *           the token to set
	 */
	public void setToken(final String token)
	{
		this.token = token;
	}

	/**
	 * @param stateToken
	 *           the stateToken to set
	 */
	public void setStateToken(final String stateToken)
	{
		this.stateToken = stateToken;
	}

}
