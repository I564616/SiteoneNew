/**
 *
 */
package com.siteone.core.batch.adapter;

import de.hybris.platform.jalo.Item;


/**
 * @author 1085284
 *
 */
public interface SpecialOpeningOrClosingAdapter
{

	void performImport(String cellValue, Item processedItem);

}
