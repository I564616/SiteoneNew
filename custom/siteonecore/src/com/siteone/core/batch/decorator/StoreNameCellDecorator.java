/**
 *
 */
package com.siteone.core.batch.decorator;

import de.hybris.platform.util.CSVCellDecorator;

import java.util.Map;


/**
 * @author 1099417
 *
 */
public class StoreNameCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map<Integer, String> srcLine)
	{

		final String storeName = srcLine.get(Integer.valueOf(position));

		final String storeId = srcLine.get(Integer.valueOf(1));
		return storeName + " #" + storeId;
	}

}
