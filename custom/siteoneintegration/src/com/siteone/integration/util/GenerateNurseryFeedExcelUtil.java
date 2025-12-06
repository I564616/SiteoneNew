package com.siteone.integration.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFTextBox;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipTypes;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.openxmlformats.schemas.drawingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPictureNonVisual;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

public class GenerateNurseryFeedExcelUtil {

	private static final Logger logger = Logger.getLogger(GenerateNurseryFeedExcelUtil.class);

	private static final String[] NURSERY_INVENTORY_FILE_HEADER = { "Item Number", "Item Name", "On Hand", "Link",
			"Category 1", "Category 2", "Category 3", "Category 4" };
	private static final String TEXT_FONT = "Arial";

	public void createNurseryFeedExcelFile(String fileName, List<Map<Integer, String>> nurseryInventoryMapList,
			byte[] inputImageBytes, String websiteUrl) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet("Availability");
			sheet.createFreezePane(0, 3);
			sheet.setDefaultRowHeightInPoints((short) 28);
			sheet.setDisplayGridlines(false);
			sheet.setColumnWidth(0, 2 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 95 * 256);
			sheet.setColumnWidth(3, 18 * 256);
			sheet.setColumnWidth(4, 18 * 256);
			sheet.setColumnWidth(5, 25 * 256);
			sheet.setColumnWidth(6, 25 * 256);
			sheet.setColumnWidth(7, 25 * 256);
			sheet.setColumnWidth(8, 25 * 256);
			sheet.setColumnWidth(9, 2 * 256);

			if (!nurseryInventoryMapList.isEmpty()) {
				XSSFTable table = sheet.createTable(null);
				int lastRow = 3 + nurseryInventoryMapList.size();

				CTTable cttable = table.getCTTable();
				cttable.setDisplayName("Inventory");
				cttable.setId(1);
				cttable.setName("Inventory");
				cttable.setRef("B3:I" + lastRow);
				cttable.setTotalsRowShown(false);
				cttable.addNewAutoFilter().setRef("B3:I3");

				CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
				styleInfo.setName("TableStyleMedium2");
				styleInfo.setShowColumnStripes(false);
				styleInfo.setShowRowStripes(true);

				CTTableColumns columns = cttable.addNewTableColumns();
				columns.setCount(8);
				for (int i = 1; i <= 8; i++) {
					CTTableColumn column = columns.addNewTableColumn();
					column.setId(i);
					column.setName("Column" + i);
				}
			}
			
			String rgbS = "709c2c";
			byte[] rgbB = null;
			try {
				rgbB = Hex.decodeHex(rgbS);
			} catch (DecoderException e) {
				logger.error("error in decoding hex - " + e.getMessage());
			}
			XSSFColor color = new XSSFColor(rgbB, null);

			XSSFCellStyle borderStyle = workbook.createCellStyle();
			borderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			borderStyle.setFillForegroundColor(color);

			XSSFRow row0 = sheet.createRow(0);
			row0.setHeightInPoints((short) 10);
			for (int i = 0; i <= 9; i++) {
				XSSFCell cell = row0.createCell(i);
				cell.setCellStyle(borderStyle);
			}

			XSSFRow row1 = sheet.createRow(1);
			row1.setHeightInPoints((short) 70);
			for (int i = 0; i <= 9; i++) {
				XSSFCell cell = row1.createCell(i);
				if (i == 0 || i == 9) {
					cell.setCellStyle(borderStyle);
				}
			}

			int inputImagePictureID = workbook.addPicture(inputImageBytes, Workbook.PICTURE_TYPE_PNG);
			XSSFDrawing drawing = sheet.createDrawingPatriarch();
			XSSFPicture pic = drawing.createPicture(new XSSFClientAnchor(219075, 228600, 1781175, 666750, 1, 1, 2, 2),
					inputImagePictureID);
			pic.resize();
			setHyperlinkToPicture(drawing, pic, websiteUrl);

			XSSFDrawing patriarch = sheet.createDrawingPatriarch();
			XSSFTextBox textbox1 = patriarch.createTextbox(new XSSFClientAnchor(0, 0, 0, 0, 2, 1, 3, 2));
			XSSFRichTextString text1 = new XSSFRichTextString("Current Local Branch Availability");
			Font text1Font = workbook.createFont();
			text1Font.setFontName(TEXT_FONT);
			text1Font.setColor(HSSFColorPredefined.GREY_80_PERCENT.getIndex());
			text1Font.setBold(true);
			text1Font.setFontHeightInPoints((short) 18);
			text1.applyFont(text1Font);
			textbox1.setText(text1);
			textbox1.setLeftInset(50);
			textbox1.setVerticalAlignment(VerticalAlignment.CENTER);

			XSSFTextBox textbox2 = patriarch.createTextbox(new XSSFClientAnchor(0, 0, 0, 0, 3, 1, 6, 2));
			XSSFRichTextString text2 = new XSSFRichTextString("Prices and availability are subject to change");
			Font text2Font = workbook.createFont();
			text2Font.setFontName(TEXT_FONT);
			text2Font.setColor(HSSFColorPredefined.GREY_80_PERCENT.getIndex());
			text2Font.setFontHeightInPoints((short) 12);
			text2.applyFont(text2Font);
			textbox2.setText(text2);
			textbox2.setLeftInset(-50);
			textbox2.setVerticalAlignment(VerticalAlignment.CENTER);
			
			createHeaderRow(workbook, sheet, borderStyle, color);
			
			if(!nurseryInventoryMapList.isEmpty()) {
				createDataRows(nurseryInventoryMapList, workbook, sheet, borderStyle);
			}
			
			try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
				workbook.write(outputStream);
			} catch (IOException e) {
				logger.error("error in writing workbook - " + e.getMessage());
			}
		} catch (IOException e1) {
			logger.error("error in closing workbook - " + e1.getMessage());
		}
	}

	private void createDataRows(List<Map<Integer, String>> nurseryInventoryMapList, XSSFWorkbook workbook, XSSFSheet sheet, XSSFCellStyle borderStyle) {
		Font contentFont = workbook.createFont();
		contentFont.setFontName(TEXT_FONT);
		contentFont.setBold(false);
		contentFont.setFontHeightInPoints((short) 12);

		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setWrapText(true);

		CellStyle contentStyleL = workbook.createCellStyle();
		contentStyleL.setAlignment(HorizontalAlignment.LEFT);
		contentStyleL.setIndention((short) 1);
		contentStyleL.setVerticalAlignment(VerticalAlignment.CENTER);
		contentStyleL.setFont(contentFont);
		contentStyleL.setWrapText(true);

		Font linkFont = workbook.createFont();
		linkFont.setFontName("Calibri");
		linkFont.setBold(false);
		linkFont.setUnderline(Font.U_SINGLE);
		linkFont.setColor(HSSFColorPredefined.BLUE.getIndex());
		linkFont.setFontHeightInPoints((short) 12);

		CellStyle linkStyle = workbook.createCellStyle();
		linkStyle.setAlignment(HorizontalAlignment.CENTER);
		linkStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		linkStyle.setFont(linkFont);
		
		int rownum = 3;
		for (Map<Integer, String> NurseryData : nurseryInventoryMapList) {
			XSSFRow dataRow = sheet.createRow(rownum);
			dataRow.setHeightInPoints((short) 28);
			
			XSSFCell dataCell0 = dataRow.createCell(0);
			dataCell0.setCellStyle(borderStyle);
			
			XSSFCell dataCell1 = dataRow.createCell(1);
			dataCell1.setCellStyle(contentStyleL);
			dataCell1.setCellValue(NurseryData.get(Integer.valueOf(0)));			
			
			XSSFCell dataCell2 = dataRow.createCell(2);
			dataCell2.setCellStyle(contentStyleL);
			dataCell2.setCellValue(NurseryData.get(Integer.valueOf(1)));
			
			XSSFCell dataCell3 = dataRow.createCell(3);
			dataCell3.setCellStyle(contentStyle);
			dataCell3.setCellValue(Integer.parseInt(NurseryData.get(Integer.valueOf(2))));
			
			XSSFCell dataCell4 = dataRow.createCell(4);
			Hyperlink link = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			link.setAddress(NurseryData.get(Integer.valueOf(3)));
			dataCell4.setHyperlink(link);
			dataCell4.setCellStyle(linkStyle);
			dataCell4.setCellValue("View Online");
			
			XSSFCell dataCell5 = dataRow.createCell(5);
			dataCell5.setCellStyle(contentStyle);
			dataCell5.setCellValue(NurseryData.get(Integer.valueOf(4)));
			
			XSSFCell dataCell6 = dataRow.createCell(6);
			dataCell6.setCellStyle(contentStyle);
			dataCell6.setCellValue(NurseryData.get(Integer.valueOf(5)));
			
			XSSFCell dataCell7 = dataRow.createCell(7);
			dataCell7.setCellStyle(contentStyle);
			dataCell7.setCellValue(NurseryData.get(Integer.valueOf(6)));
			
			XSSFCell dataCell8 = dataRow.createCell(8);
			dataCell8.setCellStyle(contentStyle);
			dataCell8.setCellValue(NurseryData.get(Integer.valueOf(7)));
			
			XSSFCell dataCell9 = dataRow.createCell(9);
			dataCell9.setCellStyle(borderStyle);
			
			rownum++;
		}
	}

	private void createHeaderRow(XSSFWorkbook workbook, XSSFSheet sheet, XSSFCellStyle borderStyle, XSSFColor color) {
		Font headerFont = workbook.createFont();
		headerFont.setFontName(TEXT_FONT);
		headerFont.setColor(HSSFColorPredefined.WHITE.getIndex());
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);

		XSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFillForegroundColor(color);
		headerStyle.setFont(headerFont);

		XSSFCellStyle headerStyleL = workbook.createCellStyle();
		headerStyleL.setAlignment(HorizontalAlignment.LEFT);
		headerStyleL.setIndention((short) 1);
		headerStyleL.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyleL.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyleL.setFillForegroundColor(color);
		headerStyleL.setFont(headerFont);
		
		XSSFRow row = sheet.createRow(2);
		row.setHeightInPoints((short) 28);
		XSSFCell cell0 = row.createCell(0);
		cell0.setCellStyle(borderStyle);
		int cellnum = 1;
		for (String header : NURSERY_INVENTORY_FILE_HEADER) {
			XSSFCell cell = row.createCell(cellnum);
			if (cellnum == 1 || cellnum == 2) {
				cell.setCellStyle(headerStyleL);
			} else {
				cell.setCellStyle(headerStyle);
			}
			cell.setCellValue(header);
			cellnum++;
		}
		XSSFCell cell9 = row.createCell(9);
		cell9.setCellStyle(borderStyle);
	}

	private void setHyperlinkToPicture(XSSFDrawing drawing, XSSFPicture pic, String hyperlinkurl) {
		PackageRelationship packagerelationship = drawing.getPackagePart().addExternalRelationship(hyperlinkurl,
				PackageRelationshipTypes.HYPERLINK_PART);
		String rid = packagerelationship.getId();

		CTPicture ctpicture = pic.getCTPicture();
		CTPictureNonVisual ctpicturenonvisual = ctpicture.getNvPicPr();
		if (ctpicturenonvisual == null)
			ctpicturenonvisual = ctpicture.addNewNvPicPr();
		CTNonVisualDrawingProps ctnonvisualdrawingprops = ctpicturenonvisual.getCNvPr();
		if (ctnonvisualdrawingprops == null)
			ctnonvisualdrawingprops = ctpicturenonvisual.addNewCNvPr();
		CTHyperlink cthyperlink = ctnonvisualdrawingprops.getHlinkClick();
		if (cthyperlink == null)
			cthyperlink = ctnonvisualdrawingprops.addNewHlinkClick();
		cthyperlink.setId(rid);
	}

}
