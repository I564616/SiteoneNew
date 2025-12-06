/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteonefaqComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteoneFAQCollectionComponent SiteoneFAQCollectionComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteoneFAQCollectionComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneFAQCollectionComponent.heading</code> attribute **/
	public static final String HEADING = "heading";
	/** Qualifier of the <code>SiteoneFAQCollectionComponent.faq</code> attribute **/
	public static final String FAQ = "faq";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(HEADING, AttributeMode.INITIAL);
		tmp.put(FAQ, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQCollectionComponent.faq</code> attribute.
	 * @return the faq
	 */
	public List<SiteonefaqComponent> getFaq(final SessionContext ctx)
	{
		List<SiteonefaqComponent> coll = (List<SiteonefaqComponent>)getProperty( ctx, FAQ);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQCollectionComponent.faq</code> attribute.
	 * @return the faq
	 */
	public List<SiteonefaqComponent> getFaq()
	{
		return getFaq( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQCollectionComponent.faq</code> attribute. 
	 * @param value the faq
	 */
	public void setFaq(final SessionContext ctx, final List<SiteonefaqComponent> value)
	{
		setProperty(ctx, FAQ,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQCollectionComponent.faq</code> attribute. 
	 * @param value the faq
	 */
	public void setFaq(final List<SiteonefaqComponent> value)
	{
		setFaq( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute.
	 * @return the heading
	 */
	public String getHeading(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneFAQCollectionComponent.getHeading requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute.
	 * @return the heading
	 */
	public String getHeading()
	{
		return getHeading( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute. 
	 * @return the localized heading
	 */
	public Map<Language,String> getAllHeading(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADING,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute. 
	 * @return the localized heading
	 */
	public Map<Language,String> getAllHeading()
	{
		return getAllHeading( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute. 
	 * @param value the heading
	 */
	public void setHeading(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteoneFAQCollectionComponent.setHeading requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute. 
	 * @param value the heading
	 */
	public void setHeading(final String value)
	{
		setHeading( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute. 
	 * @param value the heading
	 */
	public void setAllHeading(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQCollectionComponent.heading</code> attribute. 
	 * @param value the heading
	 */
	public void setAllHeading(final Map<Language,String> value)
	{
		setAllHeading( getSession().getSessionContext(), value );
	}
	
}
