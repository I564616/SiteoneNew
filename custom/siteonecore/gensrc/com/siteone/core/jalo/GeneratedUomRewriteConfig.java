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
 * Generated class for type {@link com.siteone.core.jalo.UomRewriteConfig UomRewriteConfig}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedUomRewriteConfig extends GenericItem
{
	/** Qualifier of the <code>UomRewriteConfig.indexName</code> attribute **/
	public static final String INDEXNAME = "indexName";
	/** Qualifier of the <code>UomRewriteConfig.regex</code> attribute **/
	public static final String REGEX = "regex";
	/** Qualifier of the <code>UomRewriteConfig.replacement</code> attribute **/
	public static final String REPLACEMENT = "replacement";
	/** Qualifier of the <code>UomRewriteConfig.seqNo</code> attribute **/
	public static final String SEQNO = "seqNo";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(INDEXNAME, AttributeMode.INITIAL);
		tmp.put(REGEX, AttributeMode.INITIAL);
		tmp.put(REPLACEMENT, AttributeMode.INITIAL);
		tmp.put(SEQNO, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.indexName</code> attribute.
	 * @return the indexName
	 */
	public String getIndexName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INDEXNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.indexName</code> attribute.
	 * @return the indexName
	 */
	public String getIndexName()
	{
		return getIndexName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.indexName</code> attribute. 
	 * @param value the indexName
	 */
	public void setIndexName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INDEXNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.indexName</code> attribute. 
	 * @param value the indexName
	 */
	public void setIndexName(final String value)
	{
		setIndexName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.regex</code> attribute.
	 * @return the regex
	 */
	public String getRegex(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REGEX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.regex</code> attribute.
	 * @return the regex
	 */
	public String getRegex()
	{
		return getRegex( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.regex</code> attribute. 
	 * @param value the regex
	 */
	public void setRegex(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REGEX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.regex</code> attribute. 
	 * @param value the regex
	 */
	public void setRegex(final String value)
	{
		setRegex( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.replacement</code> attribute.
	 * @return the replacement
	 */
	public String getReplacement(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REPLACEMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.replacement</code> attribute.
	 * @return the replacement
	 */
	public String getReplacement()
	{
		return getReplacement( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.replacement</code> attribute. 
	 * @param value the replacement
	 */
	public void setReplacement(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REPLACEMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.replacement</code> attribute. 
	 * @param value the replacement
	 */
	public void setReplacement(final String value)
	{
		setReplacement( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.seqNo</code> attribute.
	 * @return the seqNo
	 */
	public Integer getSeqNo(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, SEQNO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.seqNo</code> attribute.
	 * @return the seqNo
	 */
	public Integer getSeqNo()
	{
		return getSeqNo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.seqNo</code> attribute. 
	 * @return the seqNo
	 */
	public int getSeqNoAsPrimitive(final SessionContext ctx)
	{
		Integer value = getSeqNo( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UomRewriteConfig.seqNo</code> attribute. 
	 * @return the seqNo
	 */
	public int getSeqNoAsPrimitive()
	{
		return getSeqNoAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, SEQNO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final Integer value)
	{
		setSeqNo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final SessionContext ctx, final int value)
	{
		setSeqNo( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UomRewriteConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final int value)
	{
		setSeqNo( getSession().getSessionContext(), value );
	}
	
}
