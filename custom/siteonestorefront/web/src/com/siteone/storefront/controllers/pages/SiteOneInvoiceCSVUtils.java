/**
 *
 */
package com.siteone.storefront.controllers.pages;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.siteone.core.model.UploadErrorProductDetailModel;
import com.siteone.core.model.UploadListErrorInfoModel;
import com.siteone.facade.InvoiceData;
import com.siteone.facade.InvoiceEntryData;
import com.siteone.facades.savedList.data.SavedListData;
import com.siteone.facades.savedList.data.SavedListEntryData;
import com.siteone.integration.constants.SiteoneintegrationConstants;

import org.apache.commons.collections4.CollectionUtils;


/**
 * @author NJoshi
 *
 */
@Component("siteOneInvoiceCSVUtils")
public class SiteOneInvoiceCSVUtils
{
	private static final Logger LOG = Logger.getLogger(SiteOneInvoiceCSVUtils.class);
	private static final String LINE = "L";
	private static final String TOTAL = "T";
	private static final String DATE_FORMATTER = "ddMMyyHHmmss";
	public static final String CSV = ".csv";
	public static final String ZERODOLLER = "$0.00";

	public static byte[] createCSV(final List<InvoiceData> invoices, final String createcsv)
	{
		final StringBuilder csv = new StringBuilder();
		if (createcsv.equalsIgnoreCase("summary"))
		{
			csv.append("Invoice #,Invoice Date,Order #,Account/Ship-To,Purchase Order #,Invoice Total,Balance ");
		}
		else
		{
			csv.append("Line or Total,Invoice #,Invoice Date,Order #,Account/Ship-To,PO #,Ordered By,Billing Terms,"
					+ "Pickup/Delivery Location,Pickup/Delivery Contact,Pickup/Delivery Date and Time,Item ID,Product Information,Unit Price,Quantity,Sub-Total,"
					+ "Tax,Total With Tax");
		}
		csv.append(System.lineSeparator());
		final DateFormat df = new SimpleDateFormat("dd-MMM-yy");
		if (!CollectionUtils.isEmpty(invoices))
		{
			for (final InvoiceData invoice : invoices)
			{
				final String invoiceNumber = checkIfBlank(invoice.getInvoiceNumber(), "", "", true);
				final Date Date = invoice.getInvoiceDate();
				String invoiceDate = null;
				if (Date != null)
				{
					invoiceDate = checkIfBlank(df.format(Date), "", "", false);
				}
				else
				{
					invoiceDate = "NA";
				}
				final String orderNumber = checkIfBlank(invoice.getOrderNumber(), "", "", true);
				String accountDisplay = checkIfBlank(invoice.getAccountDisplay(), "", "", false);
				if (accountDisplay.equals("")) 
				{
				    if (StringUtils.isNotBlank(invoice.getShipToAccountNumber()) && invoice.getShipToAccountNumber().contains("_")) 
				    {
				        accountDisplay = invoice.getShipToAccountNumber().substring(0, invoice.getShipToAccountNumber().indexOf('_'));
				    } 
				    else 
				    {
				        LOG.error("ShipToAccountNumber is blank, null, or doesn't contain '_'");
				        accountDisplay = "NA";
				    }
				}
				final String purchaseOrderNumber = checkIfBlank(invoice.getPurchaseOrderNumber(), "", "", true);
				final String orderTotalPrice = checkIfBlank(invoice.getOrderTotalPrice(), "$", "", true);
				final String amountDue = checkIfBlank(invoice.getAmountDue(), "$", "", true);


				if (createcsv.equalsIgnoreCase("summary"))
				{

					csv.append(invoiceNumber).append(",").append(invoiceDate).append(",").append(orderNumber).append(",").append("\"")
							.append(accountDisplay).append("\"").append(",").append(purchaseOrderNumber).append(",").append("\"")
							.append(orderTotalPrice).append("\"").append(",").append("\"").append(amountDue).append("\"")
							.append(System.lineSeparator());
				}
				else
				{

					final String billingTerms = checkIfBlank(invoice.getBillingTerms(), "", "", true);
					String orderBy = checkIfBlank(invoice.getUserName(), "", "", false);
					if (orderBy.equals(""))
					{
						orderBy = checkIfBlank(invoice.getUser(), "", "", true);
					}
					else
					{
						orderBy += checkIfBlank(invoice.getUser(), "\n", "", false);
					}
					String pickupOrDeliveryDateAndTime = null;
					final Date pickupOrDeliveryDate = invoice.getPickupOrDeliveryDateTime();
					if (pickupOrDeliveryDate != null)
					{
						pickupOrDeliveryDateAndTime = checkIfBlank(df.format(pickupOrDeliveryDate), "", "", false);
					}
					else
					{
						pickupOrDeliveryDateAndTime = "NA";
					}
					final String subTotal = checkIfBlank(invoice.getSubTotal(), "$", "", true);
					final String totalTax = checkIfBlank(invoice.getTotalTax(), "$", "", true);

					if (invoice.getInvoiceEntryList() != null)
					{
						for (final InvoiceEntryData invoiceEntry : invoice.getInvoiceEntryList())
						{
							final String basePrice = checkIfBlank(invoiceEntry.getBasePrice(), "$", "", true);
							final String quantity = !checkIfBlank(invoiceEntry.getQuantity(), "", "", false).equals("")
									? Integer.toString((int) Double.parseDouble(invoiceEntry.getQuantity()))
									: "NA";
							final String extPrice = checkIfBlank(invoiceEntry.getExtPrice(), "$", "", true);
							final String itemId = checkIfBlank(invoiceEntry.getProductItemNumber(), "", "", true);

							csv.append(LINE).append(",").append(invoiceNumber).append(",").append(invoiceDate).append(",").append(orderNumber)
									.append(",").append("\"").append(accountDisplay).append("\"").append(",").append(purchaseOrderNumber)
									.append(",").append("\"").append(orderBy).append("\"").append(",").append(billingTerms).append(",")
									.append("\"").append(pickupOrDeliveryLocation(invoice)).append("\"").append(",").append("\"")
									.append(pickupOrDeliveryContact(invoice)).append("\"").append(",").append(pickupOrDeliveryDateAndTime)
									.append(",").append(itemId).append(",").append("\"").append(productInformation(invoiceEntry)).append("\"")
									.append(",").append("\"").append(basePrice).append("\"").append(",").append(quantity).append(",")
									.append("\"").append(extPrice).append("\"").append(",").append(",").append(System.lineSeparator());

						}
					}
					csv.append(TOTAL).append(",").append(invoiceNumber).append(",").append(invoiceDate).append(",").append(orderNumber)
							.append(",").append("\"").append(accountDisplay).append("\"").append(",").append(purchaseOrderNumber)
							.append(",").append("\"").append(orderBy).append("\"").append(",").append(billingTerms).append(",").append("\"")
							.append(pickupOrDeliveryLocation(invoice)).append("\"").append(",").append("\"")
							.append(pickupOrDeliveryContact(invoice)).append("\"").append(",").append(pickupOrDeliveryDateAndTime)
							.append(",").append(",").append(",").append(",").append(",").append("\"").append(subTotal).append("\"").append(",")
							.append("\"").append(totalTax).append("\"").append(",").append("\"").append(orderTotalPrice).append("\"")
							.append(System.lineSeparator());
				}
			}
		}
		return csv.toString().getBytes();

	}

	private static String checkIfBlank(final String var, final String prefix, final String suffix, final boolean flag)
	{
		if (StringUtils.isBlank(var))
		{
			return flag ? "NA" : "";
		}
		return prefix + var + suffix;
	}

	private static String pickupOrDeliveryLocation(final InvoiceData invoice)
	{
		final StringBuilder pickupOrDeliveryLocation = new StringBuilder();
		if (invoice.getDeliveryMode() != null && (invoice.getDeliveryMode().equalsIgnoreCase("Customer Pick up") || invoice.getDeliveryMode().equalsIgnoreCase("pickup")))
		{
			if (invoice.getAddress() != null && invoice.getBranchAddress() != null)
			{
   			pickupOrDeliveryLocation.append(checkIfBlank(invoice.getStoreName(), "", "\n", false));
   			pickupOrDeliveryLocation.append(checkIfBlank(invoice.getStoreTitle(), "", "\n", false));
   			pickupOrDeliveryLocation.append(checkIfBlank(invoice.getBranchAddress().getLine1(), "", "\n", false));
   			pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getTown(), "", ", ", false));
   			pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getRegion().getIsocodeShort(), "", " ", false));
   			pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getPostalCode(), "", "", false));
   			pickupOrDeliveryLocation.append(!checkIfBlank(pickupOrDeliveryLocation.toString(), "", "", false).equals("")
   					? checkIfBlank(invoice.getBranchAddress().getPhone(), "\n", "", false)
   					: checkIfBlank(invoice.getBranchAddress().getPhone(), "", "", true));
			}
			else
			{
				pickupOrDeliveryLocation.append("NA");
			}
		}
		else
		{
			if (invoice.getAddress() != null)
			{
				pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getTown(), "", ", ", false));
				pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getRegion().getIsocodeShort(), "", " ", false));
				pickupOrDeliveryLocation.append(checkIfBlank(invoice.getBranchNumber(), "#", "", false));
				pickupOrDeliveryLocation.append(!checkIfBlank(pickupOrDeliveryLocation.toString(), "", "", false).equals("")
						? checkIfBlank(invoice.getAddress().getCompanyName(), "\n", "", false)
						: checkIfBlank(invoice.getAddress().getCompanyName(), "", "", false));
				pickupOrDeliveryLocation.append(!checkIfBlank(pickupOrDeliveryLocation.toString(), "", "", false).equals("")
						? checkIfBlank(invoice.getAddress().getLine1(), "\n", "", false)
						: checkIfBlank(invoice.getAddress().getLine1(), "", "", false));
				pickupOrDeliveryLocation.append(!checkIfBlank(pickupOrDeliveryLocation.toString(), "", "", false).equals("")
						? checkIfBlank(invoice.getAddress().getTown(), "\n", ", ", false)
						: checkIfBlank(invoice.getAddress().getTown(), "", ", ", false));
				pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getRegion().getIsocodeShort(), "", " ", false));
				pickupOrDeliveryLocation.append(checkIfBlank(invoice.getAddress().getPostalCode(), "", "", false));
				pickupOrDeliveryLocation.append(!checkIfBlank(pickupOrDeliveryLocation.toString(), "", "", false).equals("")
						? checkIfBlank(invoice.getAddress().getPhone(), "\n", "", false)
						: checkIfBlank(invoice.getAddress().getPhone(), "", "", true));
			}
			else
			{
				pickupOrDeliveryLocation.append("NA");
			}
		}
		return pickupOrDeliveryLocation.toString();
	}

	private static String productInformation(final InvoiceEntryData entry)
	{
		final StringBuilder productInformation = new StringBuilder();
		productInformation.append(checkIfBlank(entry.getDescription(), "", "", false));
		if (!productInformation.toString().equals(""))
		{
			productInformation.append(checkIfBlank(entry.getProductItemNumber(), "\n", "", false));
		}
		else
		{
			productInformation.append(checkIfBlank(entry.getProductItemNumber(), "", "", true));
		}
		return productInformation.toString();
	}

	private static String pickupOrDeliveryContact(final InvoiceData invoice)
	{
		final StringBuilder pickupOrDeliveryContact = new StringBuilder();
		pickupOrDeliveryContact.append(checkIfBlank(invoice.getContact_firstName(), "", "", false));

		if (!pickupOrDeliveryContact.toString().equals(""))
		{
			pickupOrDeliveryContact.append(checkIfBlank(invoice.getContact_phone(), "\n", "", false));
			pickupOrDeliveryContact.append(checkIfBlank(invoice.getContact_emailId(), "\n", "", false));
		}
		else
		{
			pickupOrDeliveryContact.append(checkIfBlank(invoice.getContact_phone(), "", "", false));
			pickupOrDeliveryContact.append(!checkIfBlank(pickupOrDeliveryContact.toString(), "", "", false).equals("")
					? checkIfBlank(invoice.getContact_emailId(), "\n", "", false)
					: checkIfBlank(invoice.getContact_emailId(), "", "", true));
		}
		return pickupOrDeliveryContact.toString();
	}

	public static byte[] createUploadListCSV()
	{
		final StringBuilder csv = new StringBuilder();
		csv.append("ProductId,Quantity");
		csv.append(System.lineSeparator());
		return csv.toString().getBytes();
	}

	public static byte[] createUploadListErrorProduct(final UploadListErrorInfoModel errorInfo)
	{
		final StringBuilder csv = new StringBuilder();
		csv.append("ProductId,Quantity,Error Exception");
		csv.append(System.lineSeparator());
		for (final UploadErrorProductDetailModel productList : errorInfo.getErrorData())
		{
			csv.append(productList.getProductCode()).append(",").append(productList.getQty()).append(",")
					.append(productList.getErrorException()).append(System.lineSeparator());


		}
		return csv.toString().getBytes();

	}

	public static byte[] createListCSV(final SavedListData savedListData, final PointOfServiceData store)
	{
		StringBuilder csv = new StringBuilder();
		csv.append("Branch#,OnHandInventory,Product ID,Description,Retail Price,Your Price,Quantity,Total");
		csv.append(System.lineSeparator());
		String branchNumber = "";
		if (null != store)
		{
			branchNumber = store.getStoreId();
		}

		if (!CollectionUtils.isEmpty(savedListData.getEntries()))
		{
			for (final SavedListEntryData entry : savedListData.getEntries())
			{
				Long onHandQty = 0L;
				if(BooleanUtils.isTrue(entry.getProduct().getInStockImage()) && BooleanUtils.isNotTrue(entry.getProduct().getIsStockInNearbyBranch())) 
				{
					onHandQty = (null != entry.getProduct().getStock() ? entry.getProduct().getStock().getStockLevel() :  0L);
				}
				final String quantity = checkIfBlank(entry.getQty().toString(), "", "", false);				
				final String cspPrice = (null != entry.getProduct().getCustomerPrice())
						  ? checkIfBlank(entry.getProduct().getCustomerPrice().getFormattedValue(), "", "", false)
						  : ZERODOLLER;
				final String retailPrice = (null != entry.getProduct().getPrice())
						  ? checkIfBlank(entry.getProduct().getPrice().getFormattedValue(), "", "", false)
						  : ZERODOLLER;
				final String totalPrice = (null != entry.getTotalPrice())
						  ? checkIfBlank(entry.getTotalPrice().getFormattedValue(), "", "", false)
						  : ZERODOLLER;
				final String productId = checkIfBlank(entry.getProduct().getItemNumber(), "", "", false);
				final String description = checkIfBlank(entry.getProduct().getName(), "", "", false);
				final String onHandInventory = onHandQty.toString();
				csv.append(branchNumber).append(",").append("\"").append(onHandInventory).append("\"").append(",").append("\"")
						.append(productId).append("\"").append(",").append("\"").append(description).append("\"").append(",")
						.append("\"").append(retailPrice).append("\"").append(",")
						.append("\"").append(cspPrice).append("\"").append(",").append("\"").append(quantity).append("\"").append(",")
						.append("\"").append(totalPrice).append("\"").append(System.lineSeparator());
			}
		}
		return csv.toString().getBytes();
	}
	
	public static String getFileName(final String listName)
	{
		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER);
		final String currentServerDate = dateFormat.format(cal.getTime());
		return (listName + SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate + CSV);
	}
}
