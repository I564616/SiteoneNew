/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import java.io.DataInputStream;


/**
 * @author SR02012
 *
 */
public class SiteonePIMBatchFailureReportEvent extends AbstractCommerceUserEvent
{
	private DataInputStream failedBatchDataStream;
	private String fileName;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(final String fileName)
	{
		this.fileName = fileName;
	}

	public DataInputStream getFailedBatchDataStream()
	{
		return failedBatchDataStream;
	}

	public void setFailedBatchDataStream(final DataInputStream failedBatchDataStream)
	{
		this.failedBatchDataStream = failedBatchDataStream;
	}

}
