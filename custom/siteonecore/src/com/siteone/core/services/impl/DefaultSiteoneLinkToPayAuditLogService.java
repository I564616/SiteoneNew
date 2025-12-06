/**
 *
 */
package com.siteone.core.services.impl;

import de.hybris.platform.servicelayer.model.ModelService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.siteone.core.model.LinkToPayAuditLogModel;
import com.siteone.core.model.LinkToPayCayanResponseModel;
import com.siteone.core.model.LinkToPayPaymentModel;
import com.siteone.core.model.SiteoneCCPaymentAuditLogModel;
import com.siteone.core.order.dao.SiteOneOrderDao;
import com.siteone.core.services.SiteoneLinkToPayAuditLogService;


/**
 * @author HR03708
 *
 */
public class DefaultSiteoneLinkToPayAuditLogService implements SiteoneLinkToPayAuditLogService
{
	private ModelService modelService;
	private SiteOneOrderDao siteOneOrderDao;



	@SuppressWarnings("boxing")
	@Override
	public void saveAuditLog(final LinkToPayCayanResponseModel cayanResponse)
	{
		final LinkToPayAuditLogModel linkToPayAuditLog = (LinkToPayAuditLogModel) getModelService()
				.create(LinkToPayAuditLogModel.class);
		final LinkToPayPaymentModel linkToPayPaymentInformation = new LinkToPayPaymentModel();
		linkToPayPaymentInformation.setAuthcode(cayanResponse.getAuthCode());
		linkToPayPaymentInformation.setCardName(cayanResponse.getCardholder());
		linkToPayPaymentInformation.setLastFourDigits(cayanResponse.getLast4Digits());
		linkToPayAuditLog.setOrderNumber(cayanResponse.getOrderNumber());
		linkToPayAuditLog.setOrderTotal(cayanResponse.getAmountCharged());
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		final LocalDateTime now = LocalDateTime.now();
		linkToPayAuditLog.setTimestamp(dtf.format(now));
		linkToPayAuditLog.setKountStatus(cayanResponse.getTransactionStatus());
		linkToPayAuditLog.setTokenReceived(cayanResponse.getToken());
		linkToPayAuditLog.setPaymentInformation(linkToPayPaymentInformation);
		getModelService().save(linkToPayAuditLog);

	}

	@Override
	public void saveSiteoneCCAuditLog(final LinkToPayCayanResponseModel cayanResponse)
	{
		SiteoneCCPaymentAuditLogModel auditDetailsModel = null;
		if(null!= cayanResponse.getOrderNumber() && null != cayanResponse.getCartID())
		{
			auditDetailsModel = siteOneOrderDao.getCCAuditDetails(cayanResponse.getOrderNumber(),
				cayanResponse.getCartID());
		}
		if (null != auditDetailsModel)
		{
			final Integer value = auditDetailsModel.getDeclineCount();
			auditDetailsModel.setDeclineCount(value + 1);
			getModelService().save(auditDetailsModel);
			getModelService().refresh(auditDetailsModel);
		}
		else
		{
			final SiteoneCCPaymentAuditLogModel auditModel = (SiteoneCCPaymentAuditLogModel) getModelService()
					.create(SiteoneCCPaymentAuditLogModel.class);
			auditModel.setOrderNumber(cayanResponse.getOrderNumber());
			auditModel.setOrderTotal(cayanResponse.getOrderAmount());
			auditModel.setCustomerEmailId(cayanResponse.getToEmails());
			auditModel.setLastFourDigits(cayanResponse.getLast4Digits());
			auditModel.setZipcode(cayanResponse.getCreditCardZip());
			auditModel.setAccountNumber("");
			auditModel.setBranchMgrEmailId("");
			auditModel.setAccountMgrEmailId("");
			auditModel.setCartID(cayanResponse.getCartID());
			getModelService().save(auditModel);
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public void resetSiteoneCCAuditLog(final String cartId)
	{
		final SiteoneCCPaymentAuditLogModel auditDetailsModel = siteOneOrderDao.getCCAuditDetails(cartId, cartId);
		if (null != auditDetailsModel)
		{
			auditDetailsModel.setDeclineCount(0);
			getModelService().save(auditDetailsModel);
			getModelService().refresh(auditDetailsModel);
		}
	}


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	/**
	 * @return the siteOneOrderDao
	 */
	public SiteOneOrderDao getSiteOneOrderDao()
	{
		return siteOneOrderDao;
	}


	/**
	 * @param siteOneOrderDao
	 *           the siteOneOrderDao to set
	 */
	public void setSiteOneOrderDao(final SiteOneOrderDao siteOneOrderDao)
	{
		this.siteOneOrderDao = siteOneOrderDao;
	}

}
