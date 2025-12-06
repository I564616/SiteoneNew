/**
 *
 */
package com.siteone.core.cronjob.service;

import java.io.IOException;

import com.siteone.core.model.NoVisibleUomCronJobModel;


/**
 * @author HR03708
 *
 */
public interface NoVisibleUomCronJobService
{

	public void getNoVisibleUomProducts(NoVisibleUomCronJobModel model) throws IOException;

}
