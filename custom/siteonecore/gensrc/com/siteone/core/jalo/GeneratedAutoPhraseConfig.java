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
 * Generated class for type {@link com.siteone.core.jalo.AutoPhraseConfig AutoPhraseConfig}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedAutoPhraseConfig extends GenericItem
{
	/** Qualifier of the <code>AutoPhraseConfig.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>AutoPhraseConfig.indexName</code> attribute **/
	public static final String INDEXNAME = "indexName";
	/** Qualifier of the <code>AutoPhraseConfig.matchPhrase</code> attribute **/
	public static final String MATCHPHRASE = "matchPhrase";
	/** Qualifier of the <code>AutoPhraseConfig.seqNo</code> attribute **/
	public static final String SEQNO = "seqNo";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(INDEXNAME, AttributeMode.INITIAL);
		tmp.put(MATCHPHRASE, AttributeMode.INITIAL);
		tmp.put(SEQNO, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.indexName</code> attribute.
	 * @return the indexName
	 */
	public String getIndexName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INDEXNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.indexName</code> attribute.
	 * @return the indexName
	 */
	public String getIndexName()
	{
		return getIndexName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.indexName</code> attribute. 
	 * @param value the indexName
	 */
	public void setIndexName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INDEXNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.indexName</code> attribute. 
	 * @param value the indexName
	 */
	public void setIndexName(final String value)
	{
		setIndexName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.matchPhrase</code> attribute.
	 * @return the matchPhrase
	 */
	public String getMatchPhrase(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MATCHPHRASE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.matchPhrase</code> attribute.
	 * @return the matchPhrase
	 */
	public String getMatchPhrase()
	{
		return getMatchPhrase( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.matchPhrase</code> attribute. 
	 * @param value the matchPhrase
	 */
	public void setMatchPhrase(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MATCHPHRASE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.matchPhrase</code> attribute. 
	 * @param value the matchPhrase
	 */
	public void setMatchPhrase(final String value)
	{
		setMatchPhrase( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.seqNo</code> attribute.
	 * @return the seqNo
	 */
	public Integer getSeqNo(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, SEQNO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.seqNo</code> attribute.
	 * @return the seqNo
	 */
	public Integer getSeqNo()
	{
		return getSeqNo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.seqNo</code> attribute. 
	 * @return the seqNo
	 */
	public int getSeqNoAsPrimitive(final SessionContext ctx)
	{
		Integer value = getSeqNo( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AutoPhraseConfig.seqNo</code> attribute. 
	 * @return the seqNo
	 */
	public int getSeqNoAsPrimitive()
	{
		return getSeqNoAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, SEQNO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final Integer value)
	{
		setSeqNo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final SessionContext ctx, final int value)
	{
		setSeqNo( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AutoPhraseConfig.seqNo</code> attribute. 
	 * @param value the seqNo
	 */
	public void setSeqNo(final int value)
	{
		setSeqNo( getSession().getSessionContext(), value );
	}
	
}
