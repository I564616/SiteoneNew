/**
 *
 */
package com.siteone.facades.constants;

import java.util.Map;


/**
 * @author pelango
 *
 */
public class CreditCardNameMapping
{

	private Map<String, String> cardTypeName;

	private Map<String, String> cardTypeShortName;

	/**
	 * @return the cardTypeName
	 */
	public Map<String, String> getCardTypeName()
	{
		return cardTypeName;
	}

	/**
	 * @param cardTypeName
	 *           the cardTypeName to set
	 */
	public void setCardTypeName(final Map<String, String> cardTypeName)
	{
		this.cardTypeName = cardTypeName;
	}

	/**
	 * @return the cardTypeShortName
	 */
	public Map<String, String> getCardTypeShortName()
	{
		return cardTypeShortName;
	}

	/**
	 * @param cardTypeShortName
	 *           the cardTypeShortName to set
	 */
	public void setCardTypeShortName(final Map<String, String> cardTypeShortName)
	{
		this.cardTypeShortName = cardTypeShortName;
	}

}
