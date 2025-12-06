/**
 *
 */
package com.siteone.core.services;

import java.util.List;


/**
 * @author SM04392
 *
 */
public interface PimPayloadsDeleteService
{
	public List<String> getBlobPath(String containerName, String filePath);

	public void deleteBlobs(List<String> folders);
}