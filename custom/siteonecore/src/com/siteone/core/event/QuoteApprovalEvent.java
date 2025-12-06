/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import java.util.List;

import com.siteone.core.model.QuoteItemDetailsModel;


/**
 * @author AA04994
 *
 */
public class QuoteApprovalEvent extends AbstractCommerceUserEvent
{
	private String branchInfo;
	private String accountInfo;
	private String shipToInfo;
	private String quoteNumber;
	private String jobName;
	private String dateSubmitted;
	private String expDate;
	private String status;
	private String bidTotal;
	private String itemNumber;
	private String itemDescription;
	private String quantity;
	private String unitPrice;
	private String UOM;
	private String extPrice;
	private String notes;
	private String customerName;
	private String customerEmailAddress;
	private String approverName;
	private String approverEmailAddress;
	private String accountManagerEmail;
	private String branchManagerEmail;
	private String insideSalesRepEmail;
	private String writerEmail;
	private String pricerEmail;
	private String itemCount;
	private String accountName;
	private List<QuoteItemDetailsModel> itemDetails;
	private List<QuoteItemDetailsModel> modifiedItemDetails;
	private Boolean isFullQuote;
	private String quoteId;
	private String accountId;
	private String phoneNumber;
	private String poNumber;
	private String optionalNotes;
	/**
	 * @return the modifiedItemDetails
	 */
	public List<QuoteItemDetailsModel> getModifiedItemDetails()
	{
		return modifiedItemDetails;
	}

	/**
	 * @param modifiedItemDetails the modifiedItemDetails to set
	 */
	public void setModifiedItemDetails(List<QuoteItemDetailsModel> modifiedItemDetails)
	{
		this.modifiedItemDetails = modifiedItemDetails;
	}
	/**
	 * @return the approvalCheck
	 */
	public String getApprovalCheck()
	{
		return approvalCheck;
	}

	/**
	 * @param approvalCheck the approvalCheck to set
	 */
	public void setApprovalCheck(String approvalCheck)
	{
		this.approvalCheck = approvalCheck;
	}

	/**
	 * @return the approvedQty
	 */
	public Integer getApprovedQty()
	{
		return approvedQty;
	}

	/**
	 * @param approvedQty the approvedQty to set
	 */
	public void setApprovedQty(Integer approvedQty)
	{
		this.approvedQty = approvedQty;
	}

	/**
	 * @return the lineNumber
	 */
	public Integer getLineNumber()
	{
		return lineNumber;
	}

	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(Integer lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	private String approvalCheck;
	private Integer approvedQty;
	private Integer lineNumber;
	
	/**
	 * @return the insideSalesRepEmail
	 */
	public String getInsideSalesRepEmail()
	{
		return insideSalesRepEmail;
	}

	/**
	 * @param insideSalesRepEmail the insideSalesRepEmail to set
	 */
	public void setInsideSalesRepEmail(String insideSalesRepEmail)
	{
		this.insideSalesRepEmail = insideSalesRepEmail;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId()
	{
		return accountId;
	}

	/**
	 * @param accountId
	 *           the accountId to set
	 */
	public void setAccountId(final String accountId)
	{
		this.accountId = accountId;
	}

	/**
	 * @return the poNumber
	 */
	public String getPoNumber()
	{
		return poNumber;
	}

	/**
	 * @param poNumber the poNumber to set
	 */
	public void setPoNumber(String poNumber)
	{
		this.poNumber = poNumber;
	}

	/**
	 * @return the optionalNotes
	 */
	public String getOptionalNotes()
	{
		return optionalNotes;
	}

	/**
	 * @param optionalNotes
	 *           the optionalNotes to set
	 */
	public void setOptionalNotes(final String optionalNotes)
	{
		this.optionalNotes = optionalNotes;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *           the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the writerEmail
	 */
	public String getWriterEmail()
	{
		return writerEmail;
	}

	/**
	 * @param writerEmail
	 *           the writerEmail to set
	 */
	public void setWriterEmail(final String writerEmail)
	{
		this.writerEmail = writerEmail;
	}

	/**
	 * @return the pricerEmail
	 */
	public String getPricerEmail()
	{
		return pricerEmail;
	}

	/**
	 * @param pricerEmail
	 *           the pricerEmail to set
	 */
	public void setPricerEmail(final String pricerEmail)
	{
		this.pricerEmail = pricerEmail;
	}



	/**
	 * @return the accountManagerEmail
	 */
	public String getAccountManagerEmail()
	{
		return accountManagerEmail;
	}

	/**
	 * @param accountManagerEmail
	 *           the accountManagerEmail to set
	 */
	public void setAccountManagerEmail(final String accountManagerEmail)
	{
		this.accountManagerEmail = accountManagerEmail;
	}

	/**
	 * @return the branchManagerEmail
	 */
	public String getBranchManagerEmail()
	{
		return branchManagerEmail;
	}

	/**
	 * @param branchManagerEmail
	 *           the branchManagerEmail to set
	 */
	public void setBranchManagerEmail(final String branchManagerEmail)
	{
		this.branchManagerEmail = branchManagerEmail;
	}



	/**
	 * @return the isFullQuote
	 */
	public Boolean getIsFullQuote()
	{
		return isFullQuote;
	}

	/**
	 * @param isFullQuote
	 *           the isFullQuote to set
	 */
	public void setIsFullQuote(final Boolean isFullQuote)
	{
		this.isFullQuote = isFullQuote;
	}

	/**
	 * @return the approverEmailAddress
	 */
	public String getApproverEmailAddress()
	{
		return approverEmailAddress;
	}

	/**
	 * @param approverEmailAddress
	 *           the approverEmailAddress to set
	 */
	public void setApproverEmailAddress(final String approverEmailAddress)
	{
		this.approverEmailAddress = approverEmailAddress;
	}

	/**
	 * @return the branchInfo
	 */
	public String getBranchInfo()
	{
		return branchInfo;
	}

	/**
	 * @param branchInfo
	 *           the branchInfo to set
	 */
	public void setBranchInfo(final String branchInfo)
	{
		this.branchInfo = branchInfo;
	}

	/**
	 * @return the accountInfo
	 */
	public String getAccountInfo()
	{
		return accountInfo;
	}

	/**
	 * @param accountInfo
	 *           the accountInfo to set
	 */
	public void setAccountInfo(final String accountInfo)
	{
		this.accountInfo = accountInfo;
	}

	/**
	 * @return the shipToInfo
	 */
	public String getShipToInfo()
	{
		return shipToInfo;
	}

	/**
	 * @param shipToInfo
	 *           the shipToInfo to set
	 */
	public void setShipToInfo(final String shipToInfo)
	{
		this.shipToInfo = shipToInfo;
	}

	/**
	 * @return the quoteNumber
	 */
	public String getQuoteNumber()
	{
		return quoteNumber;
	}

	/**
	 * @param quoteNumber
	 *           the quoteNumber to set
	 */
	public void setQuoteNumber(final String quoteNumber)
	{
		this.quoteNumber = quoteNumber;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName()
	{
		return jobName;
	}

	/**
	 * @param jobName
	 *           the jobName to set
	 */
	public void setJobName(final String jobName)
	{
		this.jobName = jobName;
	}

	/**
	 * @return the dateSubmitted
	 */
	public String getDateSubmitted()
	{
		return dateSubmitted;
	}

	/**
	 * @param dateSubmitted
	 *           the dateSubmitted to set
	 */
	public void setDateSubmitted(final String dateSubmitted)
	{
		this.dateSubmitted = dateSubmitted;
	}

	/**
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return expDate;
	}

	/**
	 * @param expDate
	 *           the expDate to set
	 */
	public void setExpDate(final String expDate)
	{
		this.expDate = expDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}

	/**
	 * @return the bidTotal
	 */
	public String getBidTotal()
	{
		return bidTotal;
	}

	/**
	 * @param bidTotal
	 *           the bidTotal to set
	 */
	public void setBidTotal(final String bidTotal)
	{
		this.bidTotal = bidTotal;
	}


	/**
	 * @return the itemNumber
	 */
	public String getItemNumber()
	{
		return itemNumber;
	}

	/**
	 * @param itemNumber
	 *           the itemNumber to set
	 */
	public void setItemNumber(final String itemNumber)
	{
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription()
	{
		return itemDescription;
	}

	/**
	 * @param itemDescription
	 *           the itemDescription to set
	 */
	public void setItemDescription(final String itemDescription)
	{
		this.itemDescription = itemDescription;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity()
	{
		return quantity;
	}

	/**
	 * @param quantity
	 *           the quantity to set
	 */
	public void setQuantity(final String quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * @return the unitPrice
	 */
	public String getUnitPrice()
	{
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *           the unitPrice to set
	 */
	public void setUnitPrice(final String unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the uOM
	 */
	public String getUOM()
	{
		return UOM;
	}

	/**
	 * @param uOM
	 *           the uOM to set
	 */
	public void setUOM(final String uOM)
	{
		UOM = uOM;
	}

	/**
	 * @return the extPrice
	 */
	public String getExtPrice()
	{
		return extPrice;
	}

	/**
	 * @param extPrice
	 *           the extPrice to set
	 */
	public void setExtPrice(final String extPrice)
	{
		this.extPrice = extPrice;
	}

	/**
	 * @return the notes
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * @param notes
	 *           the notes to set
	 */
	public void setNotes(final String notes)
	{
		this.notes = notes;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName
	 *           the customerName to set
	 */
	public void setCustomerName(final String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress()
	{
		return customerEmailAddress;
	}

	/**
	 * @param customerEmailAddress
	 *           the customerEmailAddress to set
	 */
	public void setCustomerEmailAddress(final String customerEmailAddress)
	{
		this.customerEmailAddress = customerEmailAddress;
	}

	/**
	 * @return the approverName
	 */
	public String getApproverName()
	{
		return approverName;
	}

	/**
	 * @param approverName
	 *           the approverName to set
	 */
	public void setApproverName(final String approverName)
	{
		this.approverName = approverName;
	}

	/**
	 * @return the itemDetails
	 */
	public List<QuoteItemDetailsModel> getItemDetails()
	{
		return itemDetails;
	}

	/**
	 * @param itemDetails
	 *           the itemDetails to set
	 */
	public void setItemDetails(final List<QuoteItemDetailsModel> itemDetails)
	{
		this.itemDetails = itemDetails;
	}

	/**
	 * @param approverEmailAddress2
	 */
	public void setApproverEmailAddress(final List<String> approverEmailAddress2)
	{
		// YTODO Auto-generated method stub

	}

	/**
	 * @return the itemCount
	 */
	public String getItemCount()
	{
		return itemCount;
	}

	/**
	 * @param itemCount
	 *           the itemCount to set
	 */
	public void setItemCount(final String itemCount)
	{
		this.itemCount = itemCount;
	}

	/**
	 * @return the quoteId
	 */
	public String getQuoteId()
	{
		return quoteId;
	}

	/**
	 * @param quoteId
	 *           the quoteId to set
	 */
	public void setQuoteId(final String quoteId)
	{
		this.quoteId = quoteId;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * @param accountName
	 *           the accountName to set
	 */
	public void setAccountName(final String accountName)
	{
		this.accountName = accountName;
	}

}