/**
 *
 */
package com.siteone.core.facade;

import java.text.ParseException;


/**
 * @author SM04392
 *
 */
public interface PimPayloadsDeleteFacade
{
	public void deletePayloadsOlderThanTwoWeeks() throws ParseException;
}