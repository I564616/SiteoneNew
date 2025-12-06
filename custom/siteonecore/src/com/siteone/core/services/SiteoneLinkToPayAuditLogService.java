/**
 *
 */
package com.siteone.core.services;

import com.siteone.core.model.LinkToPayCayanResponseModel;


/**
 * @author HR03708
 *
 */
public interface SiteoneLinkToPayAuditLogService
{
	void saveAuditLog(LinkToPayCayanResponseModel cayanResponse);

	void saveSiteoneCCAuditLog(LinkToPayCayanResponseModel cayanResponse);

	void resetSiteoneCCAuditLog(String cartId);
}
