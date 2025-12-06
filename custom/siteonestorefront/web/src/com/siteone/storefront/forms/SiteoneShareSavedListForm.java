/**
 *
 */
package com.siteone.storefront.forms;



/**
 * @author 1003567
 *
 */
public class SiteoneShareSavedListForm
{
	private String users;
	private String note;
	private String listname;
	private String code;
	private Boolean isViewEdit;


/**
	 * @return the isViewEdit
	 */
	public Boolean getIsViewEdit()
	{
		return isViewEdit;
	}

	/**
	 * @param isViewEdit the isViewEdit to set
	 */
	public void setIsViewEdit(Boolean isViewEdit)
	{
		this.isViewEdit = isViewEdit;
	}


	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the listname
	 */
	public String getListname()
	{
		return listname;
	}

	/**
	 * @param listname
	 *           the listname to set
	 */
	public void setListname(final String listname)
	{
		this.listname = listname;
	}

	/**
	 * @return the users
	 */
	public String getUsers()
	{
		return users;
	}

	/**
	 * @param users
	 *           the users to set
	 */
	public void setUsers(final String users)
	{
		this.users = users;
	}

	/**
	 * @return the note
	 */
	public String getNote()
	{
		return note;
	}

	/**
	 * @param note
	 *           the note to set
	 */
	public void setNote(final String note)
	{
		this.note = note;
	}




}
