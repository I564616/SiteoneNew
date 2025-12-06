/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.CMSParagraphComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOnePartnerPerkParagraphComponent SiteOnePartnerPerkParagraphComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOnePartnerPerkParagraphComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOnePartnerPerkParagraphComponent.content</code> attribute **/
	public static final String CONTENT = "content";
	/** Qualifier of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteOnePartnerPerkParagraphComponent.headlineContents</code> attribute **/
	public static final String HEADLINECONTENTS = "headlineContents";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CONTENT, AttributeMode.INITIAL);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(HEADLINECONTENTS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.content</code> attribute.
	 * @return the content
	 */
	public CMSParagraphComponent getContent(final SessionContext ctx)
	{
		return (CMSParagraphComponent)getProperty( ctx, CONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.content</code> attribute.
	 * @return the content
	 */
	public CMSParagraphComponent getContent()
	{
		return getContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final SessionContext ctx, final CMSParagraphComponent value)
	{
		setProperty(ctx, CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final CMSParagraphComponent value)
	{
		setContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePartnerPerkParagraphComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute.
	 * @return the headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute. 
	 * @return the localized headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePartnerPerkParagraphComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.headline</code> attribute. 
	 * @param value the headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.headlineContents</code> attribute.
	 * @return the headlineContents
	 */
	public String getHeadlineContents(final SessionContext ctx)
	{
		return (String)getProperty( ctx, HEADLINECONTENTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePartnerPerkParagraphComponent.headlineContents</code> attribute.
	 * @return the headlineContents
	 */
	public String getHeadlineContents()
	{
		return getHeadlineContents( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.headlineContents</code> attribute. 
	 * @param value the headlineContents
	 */
	public void setHeadlineContents(final SessionContext ctx, final String value)
	{
		setProperty(ctx, HEADLINECONTENTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePartnerPerkParagraphComponent.headlineContents</code> attribute. 
	 * @param value the headlineContents
	 */
	public void setHeadlineContents(final String value)
	{
		setHeadlineContents( getSession().getSessionContext(), value );
	}
	
}
