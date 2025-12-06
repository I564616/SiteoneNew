/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.acceleratorcms.jalo.components.SimpleResponsiveBannerComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOnePromotionalResponsiveBannerComponent SiteOnePromotionalResponsiveBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOnePromotionalResponsiveBannerComponent extends SimpleResponsiveBannerComponent
{
	/** Qualifier of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute **/
	public static final String PROMOTIONALTEXT = "promotionalText";
	/** Qualifier of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute **/
	public static final String BUTTONNAME = "buttonName";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleResponsiveBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PROMOTIONALTEXT, AttributeMode.INITIAL);
		tmp.put(BUTTONNAME, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute.
	 * @return the buttonName - Section Heading
	 */
	public String getButtonName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionalResponsiveBannerComponent.getButtonName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute.
	 * @return the buttonName - Section Heading
	 */
	public String getButtonName()
	{
		return getButtonName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute. 
	 * @return the localized buttonName - Section Heading
	 */
	public Map<Language,String> getAllButtonName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute. 
	 * @return the localized buttonName - Section Heading
	 */
	public Map<Language,String> getAllButtonName()
	{
		return getAllButtonName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName - Section Heading
	 */
	public void setButtonName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionalResponsiveBannerComponent.setButtonName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName - Section Heading
	 */
	public void setButtonName(final String value)
	{
		setButtonName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName - Section Heading
	 */
	public void setAllButtonName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName - Section Heading
	 */
	public void setAllButtonName(final Map<Language,String> value)
	{
		setAllButtonName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionalResponsiveBannerComponent.getPromotionalText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PROMOTIONALTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute.
	 * @return the promotionalText
	 */
	public String getPromotionalText()
	{
		return getPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PROMOTIONALTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute. 
	 * @return the localized promotionalText
	 */
	public Map<Language,String> getAllPromotionalText()
	{
		return getAllPromotionalText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionalResponsiveBannerComponent.setPromotionalText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setPromotionalText(final String value)
	{
		setPromotionalText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PROMOTIONALTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionalResponsiveBannerComponent.promotionalText</code> attribute. 
	 * @param value the promotionalText
	 */
	public void setAllPromotionalText(final Map<Language,String> value)
	{
		setAllPromotionalText( getSession().getSessionContext(), value );
	}
	
}
