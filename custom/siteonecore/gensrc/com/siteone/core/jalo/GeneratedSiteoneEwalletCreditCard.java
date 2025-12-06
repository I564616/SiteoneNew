/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.b2b.jalo.B2BCustomer;
import de.hybris.platform.b2b.jalo.B2BUnit;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Utilities;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneEwalletCreditCard SiteoneEwalletCreditCard}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneEwalletCreditCard extends GenericItem
{
	/** Qualifier of the <code>SiteoneEwalletCreditCard.custtreeNodeCreditCardId</code> attribute **/
	public static final String CUSTTREENODECREDITCARDID = "custtreeNodeCreditCardId";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.vaultToken</code> attribute **/
	public static final String VAULTTOKEN = "vaultToken";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.last4Digits</code> attribute **/
	public static final String LAST4DIGITS = "last4Digits";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.nickName</code> attribute **/
	public static final String NICKNAME = "nickName";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.creditCardType</code> attribute **/
	public static final String CREDITCARDTYPE = "creditCardType";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.expDate</code> attribute **/
	public static final String EXPDATE = "expDate";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.creditCardZip</code> attribute **/
	public static final String CREDITCARDZIP = "creditCardZip";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.nameOnCard</code> attribute **/
	public static final String NAMEONCARD = "nameOnCard";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.streetAddress</code> attribute **/
	public static final String STREETADDRESS = "streetAddress";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.active</code> attribute **/
	public static final String ACTIVE = "active";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.unitUID</code> attribute **/
	public static final String UNITUID = "unitUID";
	/** Relation ordering override parameter constants for ShipTo2WalletRelation from ((siteonecore))*/
	protected static String SHIPTO2WALLETRELATION_SRC_ORDERED = "relation.ShipTo2WalletRelation.source.ordered";
	protected static String SHIPTO2WALLETRELATION_TGT_ORDERED = "relation.ShipTo2WalletRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for ShipTo2WalletRelation from ((siteonecore))*/
	protected static String SHIPTO2WALLETRELATION_MARKMODIFIED = "relation.ShipTo2WalletRelation.markmodified";
	/** Qualifier of the <code>SiteoneEwalletCreditCard.customerUID</code> attribute **/
	public static final String CUSTOMERUID = "customerUID";
	/** Relation ordering override parameter constants for Customer2WalletRelation from ((siteonecore))*/
	protected static String CUSTOMER2WALLETRELATION_SRC_ORDERED = "relation.Customer2WalletRelation.source.ordered";
	protected static String CUSTOMER2WALLETRELATION_TGT_ORDERED = "relation.Customer2WalletRelation.target.ordered";
	/** Relation disable markmodifed parameter constants for Customer2WalletRelation from ((siteonecore))*/
	protected static String CUSTOMER2WALLETRELATION_MARKMODIFIED = "relation.Customer2WalletRelation.markmodified";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CUSTTREENODECREDITCARDID, AttributeMode.INITIAL);
		tmp.put(VAULTTOKEN, AttributeMode.INITIAL);
		tmp.put(LAST4DIGITS, AttributeMode.INITIAL);
		tmp.put(NICKNAME, AttributeMode.INITIAL);
		tmp.put(CREDITCARDTYPE, AttributeMode.INITIAL);
		tmp.put(EXPDATE, AttributeMode.INITIAL);
		tmp.put(CREDITCARDZIP, AttributeMode.INITIAL);
		tmp.put(NAMEONCARD, AttributeMode.INITIAL);
		tmp.put(STREETADDRESS, AttributeMode.INITIAL);
		tmp.put(ACTIVE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.active</code> attribute.
	 * @return the active
	 */
	public Boolean isActive(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ACTIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.active</code> attribute.
	 * @return the active
	 */
	public Boolean isActive()
	{
		return isActive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.active</code> attribute. 
	 * @return the active
	 */
	public boolean isActiveAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isActive( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.active</code> attribute. 
	 * @return the active
	 */
	public boolean isActiveAsPrimitive()
	{
		return isActiveAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ACTIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final Boolean value)
	{
		setActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final SessionContext ctx, final boolean value)
	{
		setActive( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.active</code> attribute. 
	 * @param value the active
	 */
	public void setActive(final boolean value)
	{
		setActive( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.creditCardType</code> attribute.
	 * @return the creditCardType
	 */
	public String getCreditCardType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDTYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.creditCardType</code> attribute.
	 * @return the creditCardType
	 */
	public String getCreditCardType()
	{
		return getCreditCardType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.creditCardType</code> attribute. 
	 * @param value the creditCardType
	 */
	public void setCreditCardType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDTYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.creditCardType</code> attribute. 
	 * @param value the creditCardType
	 */
	public void setCreditCardType(final String value)
	{
		setCreditCardType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.creditCardZip</code> attribute.
	 * @return the creditCardZip
	 */
	public String getCreditCardZip(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CREDITCARDZIP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.creditCardZip</code> attribute.
	 * @return the creditCardZip
	 */
	public String getCreditCardZip()
	{
		return getCreditCardZip( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.creditCardZip</code> attribute. 
	 * @param value the creditCardZip
	 */
	public void setCreditCardZip(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CREDITCARDZIP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.creditCardZip</code> attribute. 
	 * @param value the creditCardZip
	 */
	public void setCreditCardZip(final String value)
	{
		setCreditCardZip( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.customerUID</code> attribute.
	 * @return the customerUID
	 */
	public Set<B2BCustomer> getCustomerUID(final SessionContext ctx)
	{
		final List<B2BCustomer> items = getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			"B2BCustomer",
			null,
			false,
			false
		);
		return new LinkedHashSet<B2BCustomer>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.customerUID</code> attribute.
	 * @return the customerUID
	 */
	public Set<B2BCustomer> getCustomerUID()
	{
		return getCustomerUID( getSession().getSessionContext() );
	}
	
	public long getCustomerUIDCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			"B2BCustomer",
			null
		);
	}
	
	public long getCustomerUIDCount()
	{
		return getCustomerUIDCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.customerUID</code> attribute. 
	 * @param value the customerUID
	 */
	public void setCustomerUID(final SessionContext ctx, final Set<B2BCustomer> value)
	{
		setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.customerUID</code> attribute. 
	 * @param value the customerUID
	 */
	public void setCustomerUID(final Set<B2BCustomer> value)
	{
		setCustomerUID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customerUID. 
	 * @param value the item to add to customerUID
	 */
	public void addToCustomerUID(final SessionContext ctx, final B2BCustomer value)
	{
		addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to customerUID. 
	 * @param value the item to add to customerUID
	 */
	public void addToCustomerUID(final B2BCustomer value)
	{
		addToCustomerUID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customerUID. 
	 * @param value the item to remove from customerUID
	 */
	public void removeFromCustomerUID(final SessionContext ctx, final B2BCustomer value)
	{
		removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.CUSTOMER2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from customerUID. 
	 * @param value the item to remove from customerUID
	 */
	public void removeFromCustomerUID(final B2BCustomer value)
	{
		removeFromCustomerUID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.custtreeNodeCreditCardId</code> attribute.
	 * @return the custtreeNodeCreditCardId
	 */
	public String getCusttreeNodeCreditCardId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CUSTTREENODECREDITCARDID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.custtreeNodeCreditCardId</code> attribute.
	 * @return the custtreeNodeCreditCardId
	 */
	public String getCusttreeNodeCreditCardId()
	{
		return getCusttreeNodeCreditCardId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.custtreeNodeCreditCardId</code> attribute. 
	 * @param value the custtreeNodeCreditCardId
	 */
	public void setCusttreeNodeCreditCardId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CUSTTREENODECREDITCARDID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.custtreeNodeCreditCardId</code> attribute. 
	 * @param value the custtreeNodeCreditCardId
	 */
	public void setCusttreeNodeCreditCardId(final String value)
	{
		setCusttreeNodeCreditCardId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, EXPDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.expDate</code> attribute.
	 * @return the expDate
	 */
	public String getExpDate()
	{
		return getExpDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, EXPDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.expDate</code> attribute. 
	 * @param value the expDate
	 */
	public void setExpDate(final String value)
	{
		setExpDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * @deprecated since 2011, use {@link Utilities#getMarkModifiedOverride(de.hybris.platform.jalo.type.RelationType)
	 */
	@Override
	@Deprecated(since = "2105", forRemoval = true)
	public boolean isMarkModifiedDisabled(final Item referencedItem)
	{
		ComposedType relationSecondEnd0 = TypeManager.getInstance().getComposedType("B2BUnit");
		if(relationSecondEnd0.isAssignableFrom(referencedItem.getComposedType()))
		{
			return Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED);
		}
		ComposedType relationSecondEnd1 = TypeManager.getInstance().getComposedType("B2BCustomer");
		if(relationSecondEnd1.isAssignableFrom(referencedItem.getComposedType()))
		{
			return Utilities.getMarkModifiedOverride(CUSTOMER2WALLETRELATION_MARKMODIFIED);
		}
		return true;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.last4Digits</code> attribute.
	 * @return the last4Digits
	 */
	public String getLast4Digits(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LAST4DIGITS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.last4Digits</code> attribute.
	 * @return the last4Digits
	 */
	public String getLast4Digits()
	{
		return getLast4Digits( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.last4Digits</code> attribute. 
	 * @param value the last4Digits
	 */
	public void setLast4Digits(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LAST4DIGITS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.last4Digits</code> attribute. 
	 * @param value the last4Digits
	 */
	public void setLast4Digits(final String value)
	{
		setLast4Digits( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.nameOnCard</code> attribute.
	 * @return the nameOnCard
	 */
	public String getNameOnCard(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NAMEONCARD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.nameOnCard</code> attribute.
	 * @return the nameOnCard
	 */
	public String getNameOnCard()
	{
		return getNameOnCard( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.nameOnCard</code> attribute. 
	 * @param value the nameOnCard
	 */
	public void setNameOnCard(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NAMEONCARD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.nameOnCard</code> attribute. 
	 * @param value the nameOnCard
	 */
	public void setNameOnCard(final String value)
	{
		setNameOnCard( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.nickName</code> attribute.
	 * @return the nickName
	 */
	public String getNickName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NICKNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.nickName</code> attribute.
	 * @return the nickName
	 */
	public String getNickName()
	{
		return getNickName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.nickName</code> attribute. 
	 * @param value the nickName
	 */
	public void setNickName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NICKNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.nickName</code> attribute. 
	 * @param value the nickName
	 */
	public void setNickName(final String value)
	{
		setNickName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.streetAddress</code> attribute.
	 * @return the streetAddress
	 */
	public String getStreetAddress(final SessionContext ctx)
	{
		return (String)getProperty( ctx, STREETADDRESS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.streetAddress</code> attribute.
	 * @return the streetAddress
	 */
	public String getStreetAddress()
	{
		return getStreetAddress( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.streetAddress</code> attribute. 
	 * @param value the streetAddress
	 */
	public void setStreetAddress(final SessionContext ctx, final String value)
	{
		setProperty(ctx, STREETADDRESS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.streetAddress</code> attribute. 
	 * @param value the streetAddress
	 */
	public void setStreetAddress(final String value)
	{
		setStreetAddress( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.unitUID</code> attribute.
	 * @return the unitUID
	 */
	public Set<B2BUnit> getUnitUID(final SessionContext ctx)
	{
		final List<B2BUnit> items = getLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			"B2BUnit",
			null,
			false,
			false
		);
		return new LinkedHashSet<B2BUnit>(items);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.unitUID</code> attribute.
	 * @return the unitUID
	 */
	public Set<B2BUnit> getUnitUID()
	{
		return getUnitUID( getSession().getSessionContext() );
	}
	
	public long getUnitUIDCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			"B2BUnit",
			null
		);
	}
	
	public long getUnitUIDCount()
	{
		return getUnitUIDCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.unitUID</code> attribute. 
	 * @param value the unitUID
	 */
	public void setUnitUID(final SessionContext ctx, final Set<B2BUnit> value)
	{
		setLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			null,
			value,
			false,
			false,
			Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.unitUID</code> attribute. 
	 * @param value the unitUID
	 */
	public void setUnitUID(final Set<B2BUnit> value)
	{
		setUnitUID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to unitUID. 
	 * @param value the item to add to unitUID
	 */
	public void addToUnitUID(final SessionContext ctx, final B2BUnit value)
	{
		addLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to unitUID. 
	 * @param value the item to add to unitUID
	 */
	public void addToUnitUID(final B2BUnit value)
	{
		addToUnitUID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from unitUID. 
	 * @param value the item to remove from unitUID
	 */
	public void removeFromUnitUID(final SessionContext ctx, final B2BUnit value)
	{
		removeLinkedItems( 
			ctx,
			false,
			SiteoneCoreConstants.Relations.SHIPTO2WALLETRELATION,
			null,
			Collections.singletonList(value),
			false,
			false,
			Utilities.getMarkModifiedOverride(SHIPTO2WALLETRELATION_MARKMODIFIED)
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from unitUID. 
	 * @param value the item to remove from unitUID
	 */
	public void removeFromUnitUID(final B2BUnit value)
	{
		removeFromUnitUID( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.vaultToken</code> attribute.
	 * @return the vaultToken
	 */
	public String getVaultToken(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VAULTTOKEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneEwalletCreditCard.vaultToken</code> attribute.
	 * @return the vaultToken
	 */
	public String getVaultToken()
	{
		return getVaultToken( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.vaultToken</code> attribute. 
	 * @param value the vaultToken
	 */
	public void setVaultToken(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VAULTTOKEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneEwalletCreditCard.vaultToken</code> attribute. 
	 * @param value the vaultToken
	 */
	public void setVaultToken(final String value)
	{
		setVaultToken( getSession().getSessionContext(), value );
	}
	
}
