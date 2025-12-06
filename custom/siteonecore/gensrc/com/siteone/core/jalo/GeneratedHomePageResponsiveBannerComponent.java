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
 * Generated class for type {@link com.siteone.core.jalo.HomePageResponsiveBannerComponent HomePageResponsiveBannerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHomePageResponsiveBannerComponent extends SimpleResponsiveBannerComponent
{
	/** Qualifier of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute **/
	public static final String BUTTONNAME = "buttonName";
	/** Qualifier of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute **/
	public static final String BANNERTEXT = "bannerText";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleResponsiveBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(BUTTONNAME, AttributeMode.INITIAL);
		tmp.put(BANNERTEXT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute.
	 * @return the bannerText
	 */
	public String getBannerText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageResponsiveBannerComponent.getBannerText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BANNERTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute.
	 * @return the bannerText
	 */
	public String getBannerText()
	{
		return getBannerText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute. 
	 * @return the localized bannerText
	 */
	public Map<Language,String> getAllBannerText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BANNERTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute. 
	 * @return the localized bannerText
	 */
	public Map<Language,String> getAllBannerText()
	{
		return getAllBannerText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute. 
	 * @param value the bannerText
	 */
	public void setBannerText(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageResponsiveBannerComponent.setBannerText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BANNERTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute. 
	 * @param value the bannerText
	 */
	public void setBannerText(final String value)
	{
		setBannerText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute. 
	 * @param value the bannerText
	 */
	public void setAllBannerText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BANNERTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.bannerText</code> attribute. 
	 * @param value the bannerText
	 */
	public void setAllBannerText(final Map<Language,String> value)
	{
		setAllBannerText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute.
	 * @return the buttonName
	 */
	public String getButtonName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageResponsiveBannerComponent.getButtonName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute.
	 * @return the buttonName
	 */
	public String getButtonName()
	{
		return getButtonName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute. 
	 * @return the localized buttonName
	 */
	public Map<Language,String> getAllButtonName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute. 
	 * @return the localized buttonName
	 */
	public Map<Language,String> getAllButtonName()
	{
		return getAllButtonName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setButtonName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomePageResponsiveBannerComponent.setButtonName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setButtonName(final String value)
	{
		setButtonName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setAllButtonName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageResponsiveBannerComponent.buttonName</code> attribute. 
	 * @param value the buttonName
	 */
	public void setAllButtonName(final Map<Language,String> value)
	{
		setAllButtonName( getSession().getSessionContext(), value );
	}
	
}
