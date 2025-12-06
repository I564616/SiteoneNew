/**
 *
 */
package com.siteone.storefront.util;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.util.Config;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.siteone.facade.InvoiceData;
import com.siteone.facade.InvoiceEntryData;


/**
 * @author 1219341
 *
 */
@Component("siteOneInvoicePDFUtils")
public class SiteOneInvoicePDFUtils
{

	byte[] pdfToByteArray = null;
	private static final String PATHURL = "https://api.c4pkoaeqo0-siteonela1-p1-public.model-t.cc.commerce.ondemand.com";
	private static final Logger LOG = Logger.getLogger(SiteOneInvoicePDFUtils.class);

	public byte[] createPDF(final InvoiceData invoiceData) throws DocumentException, IOException
	{
		final Document invoiceDocument = new Document(PageSize.A4, 20, 20, 2, 30);
		final ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		final PdfWriter writer = PdfWriter.getInstance(invoiceDocument, byteArrayStream);
		byte[] pdfToByteArray = null;

		final Font boldFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
		Config.getString("siteonestorefront.application-context", null);
		invoiceDocument.open();
		//Document Heading
		final Paragraph heading = new Paragraph("Sales Invoice", boldFont);
		heading.setAlignment(Element.ALIGN_CENTER);
		invoiceDocument.add(heading);

		//SiteLogo
		try
		{
			final Image siteLogo = Image.getInstance(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/../../_ui/responsive/theme-lambda/images/SiteOneLogo.jpg")));
			invoiceDocument.add(siteLogo);
		}
		catch (final IOException e)
		{
			LOG.error("Image not found in given path", e);
		}

		//Store Information
		final PdfPTable leftTable1 = new PdfPTable(1);
		leftTable1.setTotalWidth(new float[]
		{ 200 });
		leftTable1.setLockedWidth(true);
		leftTable1.setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTable1.setSpacingBefore(5);
		leftTable1.setSpacingAfter(5);
		leftTable1.getDefaultCell().setFixedHeight(10f);
		for (int aw = 0; aw < 4; aw++)
		{
			Paragraph content;
			final PdfPCell cell = new PdfPCell();
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Element.ALIGN_TOP | Element.ALIGN_LEFT);
			cell.setPaddingLeft(0);
			cell.setPaddingRight(0);
			cell.setPaddingTop(0);
			if (aw == 0)
			{
				cell.setNoWrap(true);
				content = new Paragraph(10, invoiceData.getBranchAddress().getCompanyName());
				cell.addElement(content);
			}
			else if (aw == 1)
			{
				content = new Paragraph(10, invoiceData.getBranchAddress().getLine1());
				cell.addElement(content);
			}
			else if (aw == 2)
			{
				content = new Paragraph(10,
						invoiceData.getBranchAddress().getTown() + "," + invoiceData.getBranchAddress().getRegion().getIsocode() + " "
								+ invoiceData.getBranchAddress().getPostalCode());
				cell.addElement(content);
			}
			else
			{
				String phone = "";
				if (null != invoiceData.getBranchAddress().getPhone())
				{
					phone = invoiceData.getBranchAddress().getPhone();
				}
				content = new Paragraph(10, phone);
				cell.addElement(content);
			}
			leftTable1.addCell(cell);
		}
		invoiceDocument.add(leftTable1);

		//Order Detail Table
		final PdfPTable leftTable2 = new PdfPTable(5);
		leftTable2.setTotalWidth(new float[]
		{ 75, 75, 50, 75, 75 });
		leftTable2.setLockedWidth(true);
		leftTable2.setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTable2.setSpacingBefore(5);
		leftTable2.setSpacingAfter(5);
		final List<List<String>> dataset = getDataForLeftTable2(invoiceData);
		for (final List<String> record : dataset)
		{
			for (final String field : record)
			{
				leftTable2.addCell(field);
			}
		}
		invoiceDocument.add(leftTable2);


		//Invoice Detail Table
		final PdfPTable leftTable3 = new PdfPTable(5);
		leftTable3.setTotalWidth(new float[]
		{ 45, 80, 75, 75, 75 });
		leftTable3.setLockedWidth(true);
		leftTable3.setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTable3.setSpacingBefore(5);
		leftTable3.setSpacingAfter(5);
		final List<List<String>> datasetForLeftTable3 = getDataForLeftTable3(invoiceData);
		for (final List<String> record : datasetForLeftTable3)
		{
			for (final String field : record)
			{
				leftTable3.addCell(field);
			}
		}
		invoiceDocument.add(leftTable3);

		//Right Column Tables
		final PdfPTable rightTable1 = new PdfPTable(1);
		rightTable1.setTotalWidth(new float[]
		{ 200 });

		rightTable1.setSpacingBefore(5);
		for (int aw = 0; aw < 4; aw++)
		{
			final PdfPCell cellForRightTable1 = new PdfPCell();
			cellForRightTable1.setPaddingLeft(0);
			cellForRightTable1.setPaddingRight(0);
			cellForRightTable1.setPaddingTop(0);
			Paragraph content;
			if (aw == 0)
			{
				cellForRightTable1.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
				String billingAccNumber = " ";
				if (null != invoiceData.getBillingAccNumber())
				{
					billingAccNumber = "  #" + invoiceData.getBillingAccNumber();
				}
				content = new Paragraph(13, invoiceData.getBillingaddress().getCompanyName() + billingAccNumber);
				cellForRightTable1.addElement(content);
			}
			else if (aw == 3)
			{
				cellForRightTable1.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				content = new Paragraph(13, invoiceData.getBillingaddress().getPhone());
				cellForRightTable1.addElement(content);
			}
			else if (aw == 1)
			{
				cellForRightTable1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				content = new Paragraph(13, invoiceData.getBillingaddress().getLine1());
				cellForRightTable1.addElement(content);
			}
			else
			{
				cellForRightTable1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				content = new Paragraph(13,
						invoiceData.getBillingaddress().getTown() + "," + invoiceData.getBillingaddress().getRegion().getIsocodeShort()
								+ " " + invoiceData.getBillingaddress().getPostalCode());
				cellForRightTable1.addElement(content);
			}

			rightTable1.addCell(cellForRightTable1);
		}

		final PdfPTable table1 = new PdfPTable(1);
		table1.setTotalWidth(new float[]
		{ 200 });
		table1.setLockedWidth(true);
		table1.setSpacingBefore(5);
		table1.setSpacingAfter(5);

		for (int aw = 0; aw < 4; aw++)
		{
			Paragraph content;
			final PdfPCell cellForRightTable2 = new PdfPCell();
			cellForRightTable2.setPaddingLeft(0);
			cellForRightTable2.setPaddingRight(0);
			cellForRightTable2.setPaddingTop(0);
			if (aw == 0)
			{
				cellForRightTable2.setBorder(PdfPCell.LEFT | PdfPCell.TOP | PdfPCell.RIGHT);
				content = new Paragraph(13,
						invoiceData.getAddress().getCompanyName() + "  #" + invoiceData.getDeliveryBranchNumber());
				cellForRightTable2.addElement(content);
			}
			else if (aw == 3)
			{
				content = new Paragraph(13, invoiceData.getAddress().getPhone());
				cellForRightTable2.addElement(content);
				cellForRightTable2.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			}
			else if (aw == 1)
			{
				cellForRightTable2.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				content = new Paragraph(13, invoiceData.getAddress().getLine1());
				cellForRightTable2.addElement(content);
			}
			else
			{
				content = new Paragraph(13, invoiceData.getAddress().getTown() + ","
						+ invoiceData.getAddress().getRegion().getIsocodeShort() + " " + invoiceData.getAddress().getPostalCode());
				cellForRightTable2.addElement(content);
				cellForRightTable2.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			}

			table1.addCell(cellForRightTable2);
		}

		final Font helpTextFont = new Font(Font.COURIER, 10, Font.BOLD);
		final PdfPTable table2 = new PdfPTable(1);
		table2.setTotalWidth(new float[]
		{ 200 });
		table2.setLockedWidth(true);
		table2.setSpacingAfter(5);
		for (int aw = 0; aw < 6; aw++)
		{
			final PdfPCell cellForRightTable3 = new PdfPCell();
			Paragraph content;
			cellForRightTable3.setPaddingLeft(0);
			cellForRightTable3.setPaddingRight(0);
			cellForRightTable3.setPaddingTop(0);
			cellForRightTable3.setBorder(Rectangle.NO_BORDER);
			if (aw == 0)
			{
				cellForRightTable3.setNoWrap(true);
				content = new Paragraph("For Chemical Emergency Spill, Leak,", helpTextFont);
				content.setLeading(12);
				cellForRightTable3.addElement(content);
			}
			else if (aw == 1)
			{
				content = new Paragraph("Fire", helpTextFont);
				content.setLeading(13);
				cellForRightTable3.addElement(content);
			}
			else if (aw == 2)
			{
				content = new Paragraph("Exposure, or Accident Emergency", helpTextFont);
				content.setLeading(13);
				cellForRightTable3.addElement(content);
			}
			else if (aw == 3)
			{
				content = new Paragraph("Response", helpTextFont);
				content.setLeading(13);
				cellForRightTable3.addElement(content);
			}
			else if (aw == 4)
			{
				content = new Paragraph("Assistance, call: CHEMTREC", helpTextFont);
				content.setLeading(13);
				cellForRightTable3.addElement(content);
			}
			else
			{
				content = new Paragraph("Day or Night- 1 (800) 424-9300", helpTextFont);
				content.setLeading(10);
				cellForRightTable3.addElement(content);
			}

			table2.addCell(cellForRightTable3);
		}

		final Font subHeadingFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL);
		final Paragraph soldToHeading = new Paragraph("Sold To:", subHeadingFont);
		soldToHeading.setIndentationLeft(22);
		final Paragraph shipToHeading = new Paragraph("Ship To:", subHeadingFont);
		shipToHeading.setIndentationLeft(22);

		final ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(350f, 500f, 600f, 790f);
		ct.addElement(soldToHeading);
		ct.addElement(rightTable1);
		ct.addElement(shipToHeading);
		ct.addElement(table1);
		ct.addElement(table2);
		ct.go();



		//Invoice Entry Detail Table
		final PdfPTable leftTable4 = new PdfPTable(8);
		leftTable4.setTotalWidth(new float[]
		{ 30, 80, 140, 50, 50, 50, 70, 70 });
		leftTable4.setLockedWidth(true);
		leftTable4.setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTable4.setSpacingBefore(25);
		leftTable4.setSpacingAfter(5);
		final List<List<String>> datasetForLeftTable4 = getDataForLeftTable4(invoiceData);
		for (int i = 0; i < datasetForLeftTable4.size(); i++)
		{
			if (i == 0)
			{
				for (int j = 0; j < datasetForLeftTable4.get(i).size(); j++)
				{
					final PdfPCell cellForLeftTable4 = new PdfPCell();
					cellForLeftTable4.setBackgroundColor(Color.GRAY);
					cellForLeftTable4.addElement(new Phrase(datasetForLeftTable4.get(i).get(j)));
					leftTable4.addCell(cellForLeftTable4);
				}
			}
			else
			{
				for (int k = 0; k < datasetForLeftTable4.get(i).size(); k++)
				{
					leftTable4.addCell(datasetForLeftTable4.get(i).get(k));
				}
			}
		}

		invoiceDocument.add(leftTable4);

		//Remit Address or Payment Details
		final PdfPTable leftTable5ForRemitAddr = new PdfPTable(2);
		final Paragraph termsDesc = new Paragraph(10);
		final PdfPTable leftTable5 = new PdfPTable(3);
		if ("On Account".equalsIgnoreCase(invoiceData.getPaymentType()))
		{

			leftTable5ForRemitAddr.setTotalWidth(new float[]
			{ 10, 70 });
			leftTable5ForRemitAddr.setLockedWidth(true);
			leftTable5ForRemitAddr.setHorizontalAlignment(Element.ALIGN_LEFT);
			leftTable5ForRemitAddr.setSpacingBefore(5);

			final List<List<String>> datasetForLeftTable5 = getDataForRemitAddress(invoiceData);


			for (final List<String> record : datasetForLeftTable5)
			{
				for (final String field : record)
				{
					final Font boldFontForleftTable5 = new Font(Font.TIMES_ROMAN, 13, Font.BOLD);

					final PdfPCell cellForLeftTable5 = new PdfPCell();
					cellForLeftTable5.setPaddingLeft(0);
					cellForLeftTable5.setPaddingRight(0);
					cellForLeftTable5.setPaddingTop(0);
					cellForLeftTable5.setBorder(Rectangle.NO_BORDER);
					cellForLeftTable5.setNoWrap(false);

					if (null != field && field.equals("Please remit payment to:"))
					{
						cellForLeftTable5.setNoWrap(true);
						cellForLeftTable5.addElement(new Phrase(13, field, boldFontForleftTable5));
					}
					else
					{
						cellForLeftTable5.setNoWrap(true);
						cellForLeftTable5.addElement(new Phrase(13, field));
					}
					leftTable5ForRemitAddr.addCell(cellForLeftTable5);
				}
			}
			String terms = "";
			if (null != invoiceData.getTerms())
			{
				terms = invoiceData.getTerms();
			}
			final Chunk c1 = new Chunk("Terms : " + terms);
			//final Chunk c2 = new Chunk("Terms Desc");
			termsDesc.add(c1);
			//termsDesc.add(c2);
		}
		else if ("Credit Card".equalsIgnoreCase(invoiceData.getPaymentType()))
		{

			leftTable5.setTotalWidth(new float[]
			{ 70, 100, 50 });
			leftTable5.setLockedWidth(true);
			leftTable5.setHorizontalAlignment(Element.ALIGN_LEFT);
			leftTable5.setSpacingBefore(5);
			final List<List<String>> datasetForLeftTable5 = getDataForLeftTable5(invoiceData);
			for (final List<String> record : datasetForLeftTable5)
			{
				for (final String field : record)
				{

					final PdfPCell cellForLeftTable5 = new PdfPCell();
					cellForLeftTable5.setPaddingLeft(0);
					cellForLeftTable5.setPaddingRight(0);
					cellForLeftTable5.setPaddingTop(0);
					cellForLeftTable5.setBorder(Rectangle.NO_BORDER);
					cellForLeftTable5.setNoWrap(true);

					cellForLeftTable5.addElement(new Phrase(13, field));

					leftTable5.addCell(cellForLeftTable5);
				}
			}
		}


		final PdfPTable leftTable6 = new PdfPTable(3);
		leftTable6.setTotalWidth(new float[]
		{ 50, 110, 50 });
		leftTable6.setLockedWidth(true);
		leftTable6.setSpacingBefore(5);
		final List<List<String>> datasetForLeftTable6 = getDataForLeftTable6(invoiceData);

		for (int i = 0; i < datasetForLeftTable6.size(); i++)
		{
			for (int j = 0; j < datasetForLeftTable6.get(i).size(); j++)
			{
				if (i == 5)
				{
					final PdfPCell cellForLastRow = new PdfPCell();
					cellForLastRow.setBorderWidth(2);
					cellForLastRow.setBorder(Rectangle.TOP);
					cellForLastRow.setNoWrap(true);
					cellForLastRow.addElement(new Paragraph((datasetForLeftTable6.get(i)).get(j)));
					leftTable6.addCell(cellForLastRow);
				}
				else
				{
					final PdfPCell cellForLeftTable6 = new PdfPCell();
					cellForLeftTable6.setPaddingLeft(0);
					cellForLeftTable6.setPaddingRight(0);
					cellForLeftTable6.setPaddingTop(0);
					cellForLeftTable6.setBorder(Rectangle.NO_BORDER);
					cellForLeftTable6.setNoWrap(true);
					cellForLeftTable6.addElement(new Phrase((datasetForLeftTable6.get(i)).get(j)));

					leftTable6.addCell(cellForLeftTable6);
				}
			}
		}



		final PdfPTable outerTable = new PdfPTable(2);
		outerTable.setTotalWidth(550);
		outerTable.setLockedWidth(true);
		final PdfPCell cellOuterTable = new PdfPCell();
		cellOuterTable.setBorder(Rectangle.NO_BORDER);
		if ("On Account".equalsIgnoreCase(invoiceData.getPaymentType()))
		{
			cellOuterTable.addElement(leftTable5ForRemitAddr);
			cellOuterTable.addElement(termsDesc);
		}
		else if ("Credit Card".equalsIgnoreCase(invoiceData.getPaymentType()))
		{
			cellOuterTable.setPaddingTop(28);
			cellOuterTable.addElement(leftTable5);
		}

		final PdfPCell cellOuterTable1 = new PdfPCell();
		cellOuterTable1.setBorder(Rectangle.NO_BORDER);
		cellOuterTable1.addElement(leftTable6);

		outerTable.addCell(cellOuterTable);
		outerTable.addCell(cellOuterTable1);
		invoiceDocument.add(outerTable);

		//Customer signature image
		final PdfPTable customerSignTable = new PdfPTable(2);
		customerSignTable.setTotalWidth(new float[]
		{ 150, 350 });
		customerSignTable.setLockedWidth(true);
		customerSignTable.setSpacingAfter(5);
		customerSignTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final PdfPCell customerSignTablecell = new PdfPCell();
		customerSignTablecell.setRowspan(1);
		customerSignTablecell.setBorder(Rectangle.NO_BORDER);
		final Paragraph signHeading = new Paragraph("Customer Signature :");
		signHeading.setLeading(125);
		signHeading.setAlignment(Element.ALIGN_LEFT);
		customerSignTablecell.addElement(signHeading);

		customerSignTable.addCell(customerSignTablecell);

		final PdfPCell cellForImage = new PdfPCell();
		cellForImage.setBorder(Rectangle.BOTTOM);
		cellForImage.setRowspan(1);



		Image customerSign = null;
		try
		{
			if (null != Config.getString("invoice.pdf.customer.sign.image.path", null))
			{
				final String filePath = Config.getString("invoice.pdf.customer.sign.image.path", null) + "/"
						+ invoiceData.getInvoiceNumber() + ".jpg";
				customerSign = Image.getInstance(filePath);
			}

		}
		catch (final IOException e)
		{
			LOG.info("Signature Image not found in given path", e);
		}

		if (null != customerSign)
		{
			customerSign.setAlignment(Element.ALIGN_CENTER);
			cellForImage.setVerticalAlignment(Element.ALIGN_BOTTOM);
			cellForImage.addElement(customerSign);
		}

		customerSignTable.addCell(cellForImage);

		final PdfPCell cellForBlank = new PdfPCell();
		cellForBlank.setBorder(Rectangle.NO_BORDER);
		cellForBlank.addElement(new Paragraph("     "));

		customerSignTable.addCell(cellForBlank);

		final PdfPCell cellForUserName = new PdfPCell();
		cellForUserName.setBorder(Rectangle.NO_BORDER);
		Paragraph userName = null;
		if (null != invoiceData.getUserName())
		{
			userName = new Paragraph(invoiceData.getUserName());
		}
		else
		{
			userName = new Paragraph("");
		}
		userName.setIndentationLeft(20);
		cellForUserName.addElement(userName);

		customerSignTable.addCell(cellForUserName);

		invoiceDocument.add(customerSignTable);

		final Paragraph customerSignText = new Paragraph(10);
		final Chunk chunk = new Chunk(
				"SiteOne Landscape Supply warrants that all products conform to the description on the label. Because conditions of use, which are of critical importance are beyond our control, seller makes no",
				new Font(Font.TIMES_ROMAN, 8, Font.NORMAL));
		final Chunk chunk1 = new Chunk(
				"warranty, expressed or implied, concerning the use of these products. No employee of the company is authorized to make any warranty or representation, expressed or implied, concerning our",
				new Font(Font.TIMES_ROMAN, 8, Font.NORMAL));
		final Chunk chunk2 = new Chunk(
				"products. Always follow directions and carefully observe all precautions on the label or manufacturer's instructions. Products used contrary to directions may cause serious plant or personal",
				new Font(Font.TIMES_ROMAN, 8, Font.NORMAL));
		final Chunk chunk3 = new Chunk(
				"injury. Buyer assumes all risk of use of handling whether in accordance with direction or not and accepts the products sold to him by this company on these conditions.",
				new Font(Font.TIMES_ROMAN, 8, Font.NORMAL));
		customerSignText.add(chunk);
		customerSignText.add(chunk1);
		customerSignText.add(chunk2);
		customerSignText.add(chunk3);
		invoiceDocument.add(customerSignText);

		//Footer
		final PdfPTable customerObsessedTable = new PdfPTable(2);
		customerObsessedTable.setTotalWidth(new float[]
		{ 100, 350 });
		customerObsessedTable.setLockedWidth(true);
		customerObsessedTable.setSpacingBefore(20);
		customerObsessedTable.setHorizontalAlignment(Element.ALIGN_MIDDLE);

		final PdfPCell customerObsessedTablecell = new PdfPCell();
		final PdfPTable obsHeadingTable = new PdfPTable(1);
		obsHeadingTable.setTotalWidth(new float[]
		{ 90 });
		obsHeadingTable.setLockedWidth(true);
		obsHeadingTable.setHorizontalAlignment(Element.ALIGN_LEFT);


		final PdfPCell cellForobsHeading = new PdfPCell();
		final Paragraph headingObsessed = new Paragraph();
		headingObsessed.setLeading(10);
		headingObsessed.add(new Chunk("CUSTOMER", new Font(Font.BOLD, 15, Font.BOLD)));
		cellForobsHeading.setBorder(Rectangle.NO_BORDER);
		cellForobsHeading.addElement(headingObsessed);

		final PdfPCell cellForobsHeading1 = new PdfPCell();
		final Paragraph headingObsessed1 = new Paragraph();
		headingObsessed1.setLeading(10);
		headingObsessed1.add(new Chunk("OBSESSED", new Font(Font.BOLD, 15, Font.BOLD)));
		cellForobsHeading1.setBorder(Rectangle.NO_BORDER);
		cellForobsHeading1.addElement(headingObsessed1);

		obsHeadingTable.addCell(cellForobsHeading);
		obsHeadingTable.addCell(cellForobsHeading1);

		customerObsessedTablecell.addElement(obsHeadingTable);
		customerObsessedTablecell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		customerObsessedTablecell.setBorderWidth(1);
		final Color myColor = new Color(128, 128, 0);
		customerObsessedTablecell.setBorderColor(myColor);
		customerObsessedTable.addCell(customerObsessedTablecell);

		final PdfPCell customerObsessedSecondTablecell = new PdfPCell();
		final PdfPTable obsDetailTable = new PdfPTable(1);
		obsDetailTable.setTotalWidth(new float[]
		{ 320 });
		obsDetailTable.setLockedWidth(true);
		obsDetailTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

		final PdfPCell cellForobsDetail = new PdfPCell();
		final Paragraph cellobsDetailPara = new Paragraph();
		cellobsDetailPara.setLeading(10);
		cellobsDetailPara
				.add(new Chunk(invoiceData.getCustomerObsessedContactName() + "|" + invoiceData.getCustomerObsessedContactTitle(),
						new Font(Font.NORMAL, 14, Font.NORMAL)));
		cellForobsDetail.setBorder(Rectangle.NO_BORDER);
		cellForobsDetail.addElement(cellobsDetailPara);

		final PdfPCell cellForobsDetail1 = new PdfPCell();
		final Paragraph headingObsessedDetail1 = new Paragraph();
		headingObsessedDetail1.setLeading(10);
		headingObsessedDetail1
				.add(new Chunk(invoiceData.getCustomerObsessedPhoneNumber() + "|" + invoiceData.getCustomerObsessedEmail(),
						new Font(Font.NORMAL, 12, Font.NORMAL)));
		cellForobsDetail1.setBorder(Rectangle.NO_BORDER);
		cellForobsDetail1.addElement(headingObsessedDetail1);

		obsDetailTable.addCell(cellForobsDetail);
		obsDetailTable.addCell(cellForobsDetail1);

		customerObsessedSecondTablecell.setBorderWidth(1);
		customerObsessedSecondTablecell.setBorderColor(myColor);
		customerObsessedSecondTablecell.addElement(obsDetailTable);
		customerObsessedTable.addCell(customerObsessedSecondTablecell);

		final PdfPTable outerTableForObsessedSection = new PdfPTable(2);
		outerTableForObsessedSection.setTotalWidth(new float[]
		{ 30, 500 });
		outerTableForObsessedSection.setLockedWidth(true);
		outerTableForObsessedSection.setSpacingBefore(10);
		outerTableForObsessedSection.setHorizontalAlignment(Element.ALIGN_LEFT);

		final Image footerLogo = Image.getInstance(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("/../../_ui/responsive/theme-lambda/images/logo_symbol.PNG")));
		footerLogo.setSpacingBefore(0);
		footerLogo.setWidthPercentage(140);
		final PdfPCell outerTableForObsessedSectioncell1 = new PdfPCell();
		outerTableForObsessedSectioncell1.addElement(footerLogo);
		outerTableForObsessedSectioncell1.setBorder(Rectangle.NO_BORDER);
		outerTableForObsessedSectioncell1.setHorizontalAlignment(Element.ALIGN_LEFT);

		outerTableForObsessedSection.addCell(outerTableForObsessedSectioncell1);
		final PdfPCell outerTableForObsessedSectioncell2 = new PdfPCell();
		outerTableForObsessedSectioncell2.setBorder(Rectangle.NO_BORDER);
		outerTableForObsessedSectioncell2.setPaddingTop(10);
		outerTableForObsessedSectioncell2.addElement(customerObsessedTable);
		outerTableForObsessedSection.addCell(outerTableForObsessedSectioncell2);

		invoiceDocument.add(outerTableForObsessedSection);

		final Paragraph footerText = new Paragraph(15);
		final Chunk chunkfooterText = new Chunk(
				"We are 100% commited to your success.Please don't hesitate to contact me directly at the Number above with feedback and input regarding your visit to our store today.",
				new Font(Font.TIMES_ROMAN, 10, Font.NORMAL));
		footerText.add(chunkfooterText);
		invoiceDocument.add(footerText);

		invoiceDocument.close();

		pdfToByteArray = byteArrayStream.toByteArray();
		return pdfToByteArray;
	}

	public static List<List<String>> getDataForLeftTable2(final InvoiceData invoiceData)
	{
		final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		final List<List<String>> data = new ArrayList<List<String>>();
		String orderedDate = "";
		String invoiceDate = "";
		if (null != invoiceData.getOrderedDate())
		{
			orderedDate = "" + df.format(invoiceData.getOrderedDate());
		}
		if (null != invoiceData.getInvoiceDate())
		{
			invoiceDate = "" + df.format(invoiceData.getInvoiceDate());
		}
		final String[] leftTable2Row1 =
		{ "Ordered", "Order#", "PO#", "Invoiced", "Invoice#" };
		String poNumber = "";
		if (null != invoiceData.getPurchaseOrderNumber())
		{
			poNumber = invoiceData.getPurchaseOrderNumber();
		}
		final String[] leftTable2Row2 =
		{ orderedDate, invoiceData.getOrderNumber(), poNumber, invoiceDate, invoiceData.getInvoiceNumber() };
		data.add(Arrays.asList(leftTable2Row1));
		data.add(Arrays.asList(leftTable2Row2));
		return data;
	}

	public static List<List<String>> getDataForLeftTable3(final InvoiceData invoiceData)
	{
		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		final Date date = new Date();

		final List<List<String>> data = new ArrayList<List<String>>();
		final String[] leftTable3Row1 =
		{ "Printed", "Requested For", "Ship Via", "Customer Contact", "Sales Associate" };
		String shipVia = "";
		if (null != invoiceData.getShipVia())
		{
			shipVia = invoiceData.getShipVia();
		}
		final String[] leftTable3Row2 =
		{ "" + dateFormat.format(date), invoiceData.getRequestedFor(), shipVia, invoiceData.getContact_firstName(),
				invoiceData.getSalesAssociate() };
		data.add(Arrays.asList(leftTable3Row1));
		data.add(Arrays.asList(leftTable3Row2));
		return data;
	}

	public static List<List<String>> getDataForLeftTable4(final InvoiceData invoiceData)
	{
		final List<List<String>> data = new ArrayList<List<String>>();
		final List<InvoiceEntryData> invoiceEntries = invoiceData.getInvoiceEntryList();
		String[] lineItemArray;
		final String[] leftTable4Row1 =
		{ "LN", "Item#", "Description", "Qty Ordered", "Qty Shipped", "Qty Open", "Net Price", "Ext. Price" };
		data.add(Arrays.asList(leftTable4Row1));
		for (int i = 0; i < invoiceEntries.size(); i++)
		{

			lineItemArray = new String[8];
			lineItemArray[0] = Integer.toString(i + 1);
			if (null != invoiceEntries.get(i).getProductItemNumber())
			{
				lineItemArray[1] = invoiceEntries.get(i).getProductItemNumber();
			}
			lineItemArray[2] = invoiceEntries.get(i).getDescription();
			lineItemArray[3] = invoiceEntries.get(i).getQuantity();
			if (null != invoiceEntries.get(i).getQtyShipped())
			{
				lineItemArray[4] = Integer.toString(invoiceEntries.get(i).getQtyShipped());
			}
			if (null != invoiceEntries.get(i).getQtyOpen())
			{
				lineItemArray[5] = Integer.toString(invoiceEntries.get(i).getQtyOpen());
			}
			if (null != invoiceEntries.get(i).getNetPrice())
			{
				lineItemArray[6] = "$" + invoiceEntries.get(i).getNetPrice();
			}
			if (null != invoiceEntries.get(i).getExtPrice())
			{
				lineItemArray[7] = "$" + invoiceEntries.get(i).getExtPrice();
			}
			data.add(Arrays.asList(lineItemArray));
		}
		return data;
	}

	public static List<List<String>> getDataForLeftTable5(final InvoiceData invoiceData)
	{
		final List<List<String>> data = new ArrayList<List<String>>();
		String totalPayment = "";
		String ccNumberLastFour = "";
		String authNumber = "";
		if (null != invoiceData.getTotalPayment())
		{
			totalPayment = "$" + invoiceData.getTotalPayment();
		}
		if (null != invoiceData.getCcNumberLastFour())
		{
			ccNumberLastFour = invoiceData.getCcNumberLastFour();
		}
		if (null != invoiceData.getAuthNumber())
		{
			authNumber = invoiceData.getAuthNumber();
		}
		final String[] leftTable5Row1 =
		{ "PAYMENT:", invoiceData.getCreditCardType(), totalPayment };
		final String[] leftTable5Row2 =
		{ "", "Acct#:", "" };
		final String[] leftTable5Row3 =
		{ "", "************" + ccNumberLastFour, "" };
		final String[] leftTable5Row4 =
		{ "", "Auth# " + authNumber, "" };
		data.add(Arrays.asList(leftTable5Row1));
		data.add(Arrays.asList(leftTable5Row2));
		data.add(Arrays.asList(leftTable5Row3));
		data.add(Arrays.asList(leftTable5Row4));
		return data;
	}

	public static List<List<String>> getDataForLeftTable6(final InvoiceData invoiceData)
	{
		final String subTotal = (null != invoiceData.getSubTotal()) ? "$" + invoiceData.getSubTotal() : "";
		final String totalTax = (null != invoiceData.getTotalTax()) ? "$" + invoiceData.getTotalTax() : "";
		final String freight = (null != invoiceData.getFreight()) ? "$" + invoiceData.getFreight() : "";
		final String orderTotalPrice = (null != invoiceData.getOrderTotalPrice()) ? "$" + invoiceData.getOrderTotalPrice() : "";
		final String totalPayment = (null != invoiceData.getTotalPayment()) ? "$" + invoiceData.getTotalPayment() : "";
		final String amountDue = (null != invoiceData.getAmountDue()) ? "$" + invoiceData.getAmountDue() : "";
		final List<List<String>> data = new ArrayList<List<String>>();
		final String[] leftTable6Row1 =
		{ "Subtotal:", "", subTotal };
		final String[] leftTable6Row2 =
		{ "Sales Tax:", "", totalTax };
		final String[] leftTable6Row3 =
		{ "Freight:", "", freight };
		final String[] leftTable6Row4 =
		{ "Total:", "", orderTotalPrice };
		final String[] leftTable6Row5 =
		{ "Total Payment: ", "", totalPayment };
		final String[] leftTable6Row6 =
		{ "Amount Due:", "", amountDue };
		data.add(Arrays.asList(leftTable6Row1));
		data.add(Arrays.asList(leftTable6Row2));
		data.add(Arrays.asList(leftTable6Row3));
		data.add(Arrays.asList(leftTable6Row4));
		data.add(Arrays.asList(leftTable6Row5));
		data.add(Arrays.asList(leftTable6Row6));
		return data;
	}

	public static List<List<String>> getDataForRemitAddress(final InvoiceData invoiceData)
	{
		final List<List<String>> data = new ArrayList<List<String>>();
		final String[] leftTable5Row1 =
		{ " ", "Please remit payment to:" };
		final String[] leftTable5Row2 =
		{ " ", invoiceData.getRemitaddress().getCompanyName() };
		final String[] leftTable5Row3 =
		{ " ", invoiceData.getRemitaddress().getLine1() };
		final String[] leftTable5Row4 =
		{ " ", invoiceData.getRemitaddress().getTown() + "," + invoiceData.getRemitaddress().getRegion().getIsocode() + " "
				+ invoiceData.getRemitaddress().getPostalCode() };
		data.add(Arrays.asList(leftTable5Row1));
		data.add(Arrays.asList(leftTable5Row2));
		data.add(Arrays.asList(leftTable5Row3));
		data.add(Arrays.asList(leftTable5Row4));
		return data;
	}

	public static byte[] createListQrPdf(final List<ProductData> products, final String selectedItems, final String label)
			throws Exception

	{
		Document listDocument = new Document(PageSize.LETTER, 12, 12, 36, 5);
		final Rectangle customPageSize = new Rectangle(700.0f, 1000.0f);

		if (label.contains("30"))
		{
			listDocument = new Document(PageSize.LETTER, 5, 5, 38, 5);
		}
		final ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(listDocument, byteArrayStream);
		byte[] pdfToByteArray = null;
		Config.getString("siteonestorefront.application-context", null);
		listDocument.open();
		float labelWidth = 0.0f;
		float labelHeight = 0.0f;
		int labelsPerRow = 0;
		int labelsPerColumn = 0;
		int reminder = 0;
		if (StringUtils.isNotEmpty(label))
		{
			if (label.contains("10"))
			{
				labelWidth = 4 * 74; // 4 inches wide
				labelHeight = 2 * 72;
				labelsPerRow = products.size() >= 2 ? 2 : 1;
				reminder = products.size() % labelsPerRow;
				labelsPerColumn = 5;
			}
			else if (label.contains("15"))
			{
				labelWidth = 2.625f * 74; // 2.625 inches tall
				labelHeight = 2 * 72; // 2 inches wide
				labelsPerRow = products.size() >= 3 ? 3 : products.size() >= 2 ? 2 : 1;
				reminder = products.size() % labelsPerRow;
				labelsPerColumn = 5;
			}
			else
			{
				labelWidth = 2.625f * 74;
				labelHeight = 1.0f * 70;
				labelsPerRow = products.size() >= 3 ? 3 : products.size() >= 2 ? 2 : 1;
				reminder = products.size() % labelsPerRow;
				labelsPerColumn = 10;
			}
		}
		final float leftMargin = 0.3125f * 72; // Example: 5/16 inch
		final float topMargin = 0.5f * 72; // Example: 1/2 inch
		final float horizontalSpacing = 0.125f * 72; // Example: 1/8 inch
		final float verticalSpacing = 0f * 72; // Example: no vertical spacing between labels
		final int labelsPerSheet = labelsPerRow * labelsPerColumn;
		// Calculate overall table width and height (adjust for margins and spacing)
		//final float totalTableWidth = (labelsPerRow * labelWidth) + ((labelsPerRow - 1) * horizontalSpacing);
		final float totalTableWidth = (labelsPerRow * labelWidth);
		final float totalTableHeight = (labelsPerColumn * labelHeight) + ((labelsPerColumn - 1) * verticalSpacing);

		// Create a table with 3 columns
		PdfPTable table = new PdfPTable(labelsPerRow);
		table.setTotalWidth(totalTableWidth); // 2 columns × 288pt (4 inches)
		table.setLockedWidth(true); // prevents auto-resizing
		table.setWidths(labelsPerRow == 1 ? new float[]
		{ labelWidth } : labelsPerRow == 2 ? new float[]
		{ labelWidth, labelWidth } : new float[]
		{ labelWidth, labelWidth, labelWidth }); // Widths for each column
		//table.getDefaultCell().setBorder(Rectangle.BOX);
		//table.setTotalWidth(totalTableWidth);
		//table.setLockedWidth(true);
		int i = 1;
		for (final ProductData product : products)
		{
			PdfPCell cell = new PdfPCell();
			if (label.contains("30"))
			{
				cell = createThirtyLabelCell(labelsPerRow, product, selectedItems, label, labelHeight);
			}
			else
			{
				cell = createLabelCell(labelsPerRow, product, selectedItems, label, labelHeight);
			}
			table.addCell(cell);


			if ((i) % labelsPerSheet == 0 && i < products.size())
			{
				// Check if a new page is needed
				listDocument.add(table);
				listDocument.newPage();
				table = new PdfPTable(labelsPerRow); // Start a new table
				//table.setWidthPercentage(100);
				table.setTotalWidth(totalTableWidth);
				table.setWidths(labelsPerRow == 1 ? new float[]
				{ labelWidth } : labelsPerRow == 2 ? new float[]
				{ labelWidth, labelWidth } : new float[]
				{ labelWidth, labelWidth, labelWidth });
				table.setLockedWidth(true);
			}

			i++;

		}
		if (reminder >= 1)
		{
			table.completeRow();
		}
		listDocument.add(table);
		listDocument.close();

		pdfToByteArray = byteArrayStream.toByteArray();
		LOG.error("pdfToByteArray " + pdfToByteArray);
		return pdfToByteArray;
	}



	private static PdfPCell createLabelCell(final int labelsPerRow, final ProductData product, final String selectedItems,
			final String label, final float labelHeight) throws Exception
	{
		final String pathUrl = Config.getString("website.siteone.api", PATHURL);
		final String qrenable = Config.getString("qr.border.enable", "false");
		final PdfPCell cell = new PdfPCell();
		cell.setFixedHeight(labelHeight);
		cell.setBorder(Rectangle.NO_BORDER);
		if (qrenable.contains("true"))
		{
			cell.setCellEvent(new RoundedRectangleCellEvent());
		}
		cell.setPadding(10);

		//cell.setBorderColor(Color.LIGHT_GRAY);
		// Logo (top right)
		Image siteLogo = null;
		//SiteLogo
		try
		{
			final String logoURL = "/../../_ui/responsive/theme-lambda/images/SiteoneLogo.jpg";
			siteLogo = Image
					.getInstance(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(logoURL)));
		}
		catch (final Exception e)
		{
			LOG.error("siteLogo Image not found in given path", e);
		}
		siteLogo.scaleToFit(70, 20);
		siteLogo.setAlignment(Element.ALIGN_RIGHT);
		Image productImage = null;
		Image qrScanner = null;
		String url = null;
		//SiteLogo

		try
		{
			if (product.getImages() != null && product.getImages().size() > 0)
			{
				for (final ImageData image : product.getImages())
				{
					if (image.getFormat().equalsIgnoreCase("thumbnail"))
					{
						url = image.getUrl();
						break;
					}
				}

				final String imageUrl1 = pathUrl + url;

				final URL imageUrl = URI.create(imageUrl1).toURL();
				productImage = Image.getInstance(imageUrl);
			}
			else
			{
				final String missingImage = "/../../_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg";
				productImage = Image.getInstance(
						IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(missingImage)));


			}
		}
		catch (final Exception e)
		{
			LOG.error("productImage not found in given path", e);
		}
		if (productImage != null)
		{

			productImage.scaleToFit(40, 40);

			productImage.setAlignment(Element.ALIGN_LEFT);
		}
		// QR Code
		try
		{
			final String qrCodePath = pathUrl + product.getProductQRCodeMedia().getUrl();

			final URL qrCodeUrl = URI.create(qrCodePath).toURL();
			qrScanner = Image.getInstance(qrCodeUrl);

		}
		catch (final Exception e)
		{
			LOG.error("qrScanner Image not found in given path", e);
		}
		if (qrScanner != null)
		{

			qrScanner.scaleToFit(55, 55);

			qrScanner.setAlignment(Element.ALIGN_RIGHT);
		}
		final int charLimit = label.contains("10") ? 35 : 27;
		final String limitDesc = createTwoLineParagraphCell(product.getName(), charLimit, 2);
		// Label content
		final Paragraph code = new Paragraph(product.getItemNumber(),
				new Font(Font.HELVETICA, label.contains("15") ? 9 : 10, Font.BOLD | Font.UNDERLINE));
		final Paragraph line1 = new Paragraph(limitDesc, new Font(Font.HELVETICA, label.contains("15") ? 8 : 9));

		// Layout table inside cell (2 columns: image and text)
		final PdfPTable innerTable = new PdfPTable(2);
		innerTable.setWidthPercentage(100);
		innerTable.setWidths(new int[]
		{ 1, 2 });
		if (selectedItems.contains("image"))
		{
			final PdfPCell imgCell = new PdfPCell(productImage);
			imgCell.setBorder(Rectangle.NO_BORDER);
			innerTable.addCell(imgCell);
		}

		else
		{
			final PdfPCell topRow1 = new PdfPCell(new Paragraph("\n"));
			topRow1.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(topRow1);
		}

		final PdfPCell textCell = new PdfPCell();
		textCell.setBorder(Rectangle.NO_BORDER);
		if (selectedItems.contains("itemnumber") && selectedItems.contains("description"))
		{
			textCell.addElement(code);
			textCell.addElement(line1);
			innerTable.addCell(textCell);
		}
		else if (selectedItems.contains("itemnumber") || selectedItems.contains("description"))
		{
			textCell.addElement(selectedItems.contains("itemnumber") ? code : line1);
			innerTable.addCell(textCell);
		}
		else
		{
			final PdfPCell topRow2 = new PdfPCell(new Paragraph("\n"));
			topRow2.setBorder(PdfPCell.NO_BORDER);
			innerTable.addCell(topRow2);
		}
		// Add logo
		cell.addElement(siteLogo);
		// Add image + text row
		cell.addElement(innerTable);
		// Add QR code
		cell.addElement(qrScanner);

		return cell;
	}

	private static PdfPCell createThirtyLabelCell(final int labelsPerRow, final ProductData product, final String selectedItems,
			final String label, final float labelHeight) throws Exception
	{
		final String pathUrl = Config.getString("website.siteone.api", PATHURL);
		final String qrenable = Config.getString("qr.border.enable", "false");
		final PdfPTable labelTable = new PdfPTable(3);
		labelTable.setTotalWidth(180 - 10);
		labelTable.setLockedWidth(true);
		labelTable.setWidths(new float[]
		{ 1.2f, 2.5f, 1.0f });
		//labelTable.setWidthPercentage(100);
		//labelTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		Image siteLogo = null;
		//SiteLogo
		try
		{
			final String logoURL = "/../../_ui/responsive/theme-lambda/images/SiteoneLogo.jpg";
			siteLogo = Image
					.getInstance(IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(logoURL)));
		}
		catch (final Exception e)
		{
			LOG.error("siteLogo Image not found in given path", e);
		}
		siteLogo.scaleAbsolute(35, 20);
		Image productImage = null;
		Image qrScanner = null;
		String url = null;
		//SiteLogo

		try
		{
			if (product.getImages() != null && product.getImages().size() > 0)
			{
				for (final ImageData image : product.getImages())
				{
					if (image.getFormat().equalsIgnoreCase("thumbnail"))
					{
						url = image.getUrl();
						break;
					}
				}

				final String imageUrl1 = pathUrl + url;

				final URL imageUrl = URI.create(imageUrl1).toURL();
				productImage = Image.getInstance(imageUrl);
			}
			else
			{
				final String missingImage = "/../../_ui/responsive/theme-lambda/images/missing_product_EN_300x300.jpg";
				productImage = Image.getInstance(
						IOUtils.toByteArray(Thread.currentThread().getContextClassLoader().getResourceAsStream(missingImage)));


			}
		}
		catch (final Exception e)
		{
			LOG.error("productImage not found in given path", e);
		}
		if (productImage != null)
		{
			productImage.scaleToFit(30, 30);
		}
		// QR Code
		try
		{
			final String qrCodePath = pathUrl + product.getProductQRCodeMedia().getUrl();

			final URL qrCodeUrl = URI.create(qrCodePath).toURL();
			qrScanner = Image.getInstance(qrCodeUrl);

		}
		catch (final Exception e)
		{
			LOG.error("qrScanner Image not found in given path", e);
		}
		if (qrScanner != null)
		{
			qrScanner.scaleAbsolute(30, 30);
		}
		final Font boldFont = new Font(Font.HELVETICA, 9, Font.BOLD | Font.UNDERLINE);
		final Font normalFont = new Font(Font.HELVETICA, 8);
		// Left: Flower Image
		if (selectedItems.contains("image"))
		{
			final PdfPCell imgCell = new PdfPCell(productImage, false);
			imgCell.setBorder(Rectangle.NO_BORDER);
			imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			labelTable.addCell(imgCell);
		}
		else
		{
			final PdfPCell topRow1 = new PdfPCell(new Paragraph("\n"));
			topRow1.setHorizontalAlignment(Element.ALIGN_CENTER);
			topRow1.setBorder(PdfPCell.NO_BORDER);
			labelTable.addCell(topRow1);
		}
		final String limitDesc = createTwoLineParagraphCell(product.getName(), 27, 2);
		// Middle: Text
		final PdfPCell textCell = new PdfPCell();
		// Label content
		final Paragraph code = new Paragraph(product.getItemNumber(), new Font(Font.HELVETICA, 7, Font.BOLD | Font.UNDERLINE));
		final Paragraph line1 = new Paragraph(limitDesc, new Font(Font.HELVETICA, 6));

		textCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		textCell.setBorder(Rectangle.NO_BORDER);
		final Paragraph p = new Paragraph();
		if (selectedItems.contains("itemnumber") && selectedItems.contains("description"))
		{
			//	p.add(new Chunk(product.getItemNumber() + "\n", boldFont));
			//	p.add(new Chunk(product.getName(), normalFont));
			textCell.addElement(code);
			textCell.addElement(line1);
		}
		else if (selectedItems.contains("itemnumber") || selectedItems.contains("description"))
		{
			textCell.addElement(selectedItems.contains("itemnumber") ? code : line1);
			//p.add(selectedItems.contains("itemnumber") ? new Chunk(product.getItemNumber() + "\n", boldFont)
			//	: new Chunk(product.getName(), normalFont));
		}
		else
		{
			textCell.addElement(new Paragraph("\n"));
		}
		//textCell.addElement(p);
		labelTable.addCell(textCell);

		final PdfPCell logoCell = new PdfPCell();
		logoCell.setBorder(Rectangle.NO_BORDER);
		logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//logoCell.setVerticalAlignment(Element.ALIGN_TOP);
		logoCell.addElement(siteLogo);
		logoCell.addElement(qrScanner);
		labelTable.addCell(logoCell);

		// Wrap the label in an outer cell
		final PdfPCell wrapper = new PdfPCell(labelTable);
		wrapper.setPadding(3);
		wrapper.setBorder(Rectangle.NO_BORDER);
		//wrapper.setBorderColor(Color.LIGHT_GRAY);
		if (qrenable.contains("true"))
		{
			wrapper.setCellEvent(new RoundedRectangleCellEvent());
		}

		wrapper.setFixedHeight(labelHeight); // Adjust height as needed
		//wrapper.setBackgroundColor(Color.WHITE);
		return wrapper;
	}

	private static String createTwoLineParagraphCell(String text, final int maxCharsPerLine, final int maxLines)
	{
		final int maxChars = maxLines * maxCharsPerLine;

		if (text.length() > maxChars)
		{
			text = text.substring(0, maxChars - 3) + "...";
		}
		return text;
	}

	// Custom PdfPCellEvent to create rounded rectangles
	static class RoundedRectangleCellEvent implements PdfPCellEvent
	{
		@Override
		public void cellLayout(final PdfPCell cell, final Rectangle position, final PdfContentByte[] canvases)
		{
			final PdfContentByte canvas = canvases[PdfPTable.BACKGROUNDCANVAS];
			canvas.saveState();
			canvas.setLineWidth(0f);
			//canvas.setColorStroke(Color.LIGHT_GRAY);
			canvas.roundRectangle(position.getLeft() + 6, position.getBottom() + 1, position.getWidth() - 8,
					position.getHeight() - 2, 10);
			//canvas.stroke();
			canvas.fill();
		}
	}
}



