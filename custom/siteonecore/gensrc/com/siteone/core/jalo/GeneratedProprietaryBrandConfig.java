/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.ProprietaryBrandConfig ProprietaryBrandConfig}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedProprietaryBrandConfig extends GenericItem
{
	/** Qualifier of the <code>ProprietaryBrandConfig.brandName</code> attribute **/
	public static final String BRANDNAME = "brandName";
	/** Qualifier of the <code>ProprietaryBrandConfig.indexName</code> attribute **/
	public static final String INDEXNAME = "indexName";
	/** Qualifier of the <code>ProprietaryBrandConfig.seqNo</code> attribute **/
	public static final String SEQNO = "seqNo";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(BRANDNAME, AttributeMode.INITIAL);
		tmp.put(INDEXNAME, AttributeMode.INITIAL);
		tmp.put(SEQNO, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.brandName</code> attribute.
	 * @return the brandName
	 */
	public String getBrandName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BRANDNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.brandName</code> attribute.
	 * @return the brandName
	 */
	public String getBrandName()
	{
		return getBrandName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.brandName</code> attribute. 
	 * @param value the brandName
	 */
	public void setBrandName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BRANDNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.brandName</code> attribute. 
	 * @param value the brandName
	 */
	public void setBrandName(final String value)
	{
		setBrandName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.indexName</code> attribute.
	 * @return the indexName
	 */
	public String getIndexName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INDEXNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.indexName</code> attribute.
	 * @return the indexName
	 */
	public String getIndexName()
	{
		return getIndexName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.indexName</code> attribute. 
	 * @param value the indexName
	 */
	public void setIndexName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INDEXNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.indexName</code> attribute. 
	 * @param value the indexName
	 */
	public void setIndexName(final String value)
	{
		setIndexName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.seqNo</code> attribute.
	 * @return the seqNo
	 */
	public Integer getSeqNo(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, SEQNO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.seqNo</code> attribute.
	 * @return the seqNo
	 */
	public Integer getSeqNo()
	{
		return getSeqNo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.seqNo</code> attribute. 
	 * @return the seqNo
	 */
	public int getSeqNoAsPrimitive(final SessionContext ctx)
	{
		Integer value = getSeqNo( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProprietaryBrandConfig.seqNo</code> attribute. 
	 * @return the seqNo
	 */
	public int getSeqNoAsPrimitive()
	{
		return getSeqNoAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, SEQNO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final Integer value)
	{
		setSeqNo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final SessionContext ctx, final int value)
	{
		setSeqNo( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProprietaryBrandConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final int value)
	{
		setSeqNo( getSession().getSessionContext(), value );
	}
	
}
