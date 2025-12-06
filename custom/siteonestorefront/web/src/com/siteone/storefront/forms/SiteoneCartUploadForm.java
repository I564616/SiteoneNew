/**
 *
 */
package com.siteone.storefront.forms;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author SJ08640
 *
 */
public class SiteoneCartUploadForm
{
	private MultipartFile csvFile;

	public MultipartFile getCsvFile()
	{
		return csvFile;
	}

	public void setCsvFile(final MultipartFile csvFile)
	{
		this.csvFile = csvFile;
	}
}
