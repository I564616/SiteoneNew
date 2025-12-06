/**
 *
 */
package com.siteone.facade.email;

import com.siteone.facades.exceptions.PdfNotAvailableException;
import com.siteone.facades.exceptions.ServiceUnavailableException;


/**
 * @author VenkatB
 *
 */
public interface BriteVerifyFacade
{

	String validateEmailId(String email);

}
