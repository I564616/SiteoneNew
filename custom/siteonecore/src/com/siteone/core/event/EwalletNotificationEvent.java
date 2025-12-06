package com.siteone.core.event;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

import java.util.Map;


/**
 * @author pelango
 *
 */
public class EwalletNotificationEvent extends AbstractEvent
{

	private BaseSiteModel site;
	private LanguageModel language;
	private String firstName;
	private String email;
	private String subject;
	private String cardNumber;
	private String cardType;
	private String operationType;
	private Map<String, String> ccMail;
	private String nickName;


	/**
	 * @return the site
	 */
	public BaseSiteModel getSite()
	{
		return site;
	}

	/**
	 * @param site
	 *           the site to set
	 */
	public void setSite(final BaseSiteModel site)
	{
		this.site = site;
	}

	/**
	 * Parameterized Constructor
	 *
	 * @param eWalletCreditCard
	 */
	public EwalletNotificationEvent()
	{
		super();
	}

	/**
	 * @return the language
	 */
	public LanguageModel getLanguage()
	{
		return language;
	}

	/**
	 * @param language
	 *           the language to set
	 */
	public void setLanguage(final LanguageModel language)
	{
		this.language = language;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *           the email to set
	 */
	public void setEmail(final String email)
	{
		this.email = email;
	}

	/**
	 * @return the subject
	 */
	public String getSubject()
	{
		return subject;
	}

	/**
	 * @param subject
	 *           the subject to set
	 */
	public void setSubject(final String subject)
	{
		this.subject = subject;
	}

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *           the cardNumber to set
	 */
	public void setCardNumber(final String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType()
	{
		return cardType;
	}

	/**
	 * @param cardType
	 *           the cardType to set
	 */
	public void setCardType(final String cardType)
	{
		this.cardType = cardType;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType()
	{
		return operationType;
	}

	/**
	 * @param operationType
	 *           the operationType to set
	 */
	public void setOperationType(final String operationType)
	{
		this.operationType = operationType;
	}

	/**
	 * @return the ccMail
	 */
	public Map<String, String> getCcMail()
	{
		return ccMail;
	}

	/**
	 * @param ccMail
	 *           the ccMail to set
	 */
	public void setCcMail(final Map<String, String> ccMail)
	{
		this.ccMail = ccMail;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName()
	{
		return nickName;
	}

	/**
	 * @param nickName
	 *           the nickName to set
	 */
	public void setNickName(final String nickName)
	{
		this.nickName = nickName;
	}

}