/**
 *
 */
package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;


/**
 * @author 1099417
 *
 */
public class PasswordChangedEvent extends AbstractCommerceUserEvent<BaseSiteModel>
{

	/**
	 * Default constructor
	 */
	public PasswordChangedEvent()
	{
		super();
	}


}
