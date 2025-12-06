/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneCuratedPLPHSProductComponent SiteOneCuratedPLPHSProductComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneCuratedPLPHSProductComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.productCodes</code> attribute **/
	public static final String PRODUCTCODES = "productCodes";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute **/
	public static final String PRODUCTBANNERDESKTOP = "productBannerDesktop";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute **/
	public static final String PRODUCTBANNERMOBILE = "productBannerMobile";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute **/
	public static final String HEADLINE = "headline";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute **/
	public static final String DESCRIPTION = "description";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>SiteOneCuratedPLPHSProductComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PRODUCTCODES, AttributeMode.INITIAL);
		tmp.put(PRODUCTBANNERDESKTOP, AttributeMode.INITIAL);
		tmp.put(PRODUCTBANNERMOBILE, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(DESCRIPTION, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		tmp.put(BUTTONURL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel - Product Banner Button
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.getButtonLabel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel - Product Banner Button
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel - Product Banner Button
	 */
	public Map<Language,String> getAllButtonLabel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel - Product Banner Button
	 */
	public Map<Language,String> getAllButtonLabel()
	{
		return getAllButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel - Product Banner Button
	 */
	public void setButtonLabel(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.setButtonLabel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel - Product Banner Button
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel - Product Banner Button
	 */
	public void setAllButtonLabel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel - Product Banner Button
	 */
	public void setAllButtonLabel(final Map<Language,String> value)
	{
		setAllButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.buttonURL</code> attribute.
	 * @return the buttonURL - Product Banner Button URL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.buttonURL</code> attribute.
	 * @return the buttonURL - Product Banner Button URL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL - Product Banner Button URL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL - Product Banner Button URL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute.
	 * @return the description - Product Banner Description
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute.
	 * @return the description - Product Banner Description
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute. 
	 * @return the localized description - Product Banner Description
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute. 
	 * @return the localized description - Product Banner Description
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute. 
	 * @param value the description - Product Banner Description
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute. 
	 * @param value the description - Product Banner Description
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute. 
	 * @param value the description - Product Banner Description
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.description</code> attribute. 
	 * @param value the description - Product Banner Description
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute.
	 * @return the headline - Product Banner Headline
	 */
	public String getHeadline(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.getHeadline requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute.
	 * @return the headline - Product Banner Headline
	 */
	public String getHeadline()
	{
		return getHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute. 
	 * @return the localized headline - Product Banner Headline
	 */
	public Map<Language,String> getAllHeadline(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute. 
	 * @return the localized headline - Product Banner Headline
	 */
	public Map<Language,String> getAllHeadline()
	{
		return getAllHeadline( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute. 
	 * @param value the headline - Product Banner Headline
	 */
	public void setHeadline(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.setHeadline requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute. 
	 * @param value the headline - Product Banner Headline
	 */
	public void setHeadline(final String value)
	{
		setHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute. 
	 * @param value the headline - Product Banner Headline
	 */
	public void setAllHeadline(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.headline</code> attribute. 
	 * @param value the headline - Product Banner Headline
	 */
	public void setAllHeadline(final Map<Language,String> value)
	{
		setAllHeadline( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute.
	 * @return the productBannerDesktop - Product Banner Desktop
	 */
	public Media getProductBannerDesktop(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.getProductBannerDesktop requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, PRODUCTBANNERDESKTOP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute.
	 * @return the productBannerDesktop - Product Banner Desktop
	 */
	public Media getProductBannerDesktop()
	{
		return getProductBannerDesktop( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute. 
	 * @return the localized productBannerDesktop - Product Banner Desktop
	 */
	public Map<Language,Media> getAllProductBannerDesktop(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,PRODUCTBANNERDESKTOP,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute. 
	 * @return the localized productBannerDesktop - Product Banner Desktop
	 */
	public Map<Language,Media> getAllProductBannerDesktop()
	{
		return getAllProductBannerDesktop( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute. 
	 * @param value the productBannerDesktop - Product Banner Desktop
	 */
	public void setProductBannerDesktop(final SessionContext ctx, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.setProductBannerDesktop requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PRODUCTBANNERDESKTOP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute. 
	 * @param value the productBannerDesktop - Product Banner Desktop
	 */
	public void setProductBannerDesktop(final Media value)
	{
		setProductBannerDesktop( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute. 
	 * @param value the productBannerDesktop - Product Banner Desktop
	 */
	public void setAllProductBannerDesktop(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,PRODUCTBANNERDESKTOP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerDesktop</code> attribute. 
	 * @param value the productBannerDesktop - Product Banner Desktop
	 */
	public void setAllProductBannerDesktop(final Map<Language,Media> value)
	{
		setAllProductBannerDesktop( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute.
	 * @return the productBannerMobile - Product Banner Mobile
	 */
	public Media getProductBannerMobile(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.getProductBannerMobile requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, PRODUCTBANNERMOBILE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute.
	 * @return the productBannerMobile - Product Banner Mobile
	 */
	public Media getProductBannerMobile()
	{
		return getProductBannerMobile( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute. 
	 * @return the localized productBannerMobile - Product Banner Mobile
	 */
	public Map<Language,Media> getAllProductBannerMobile(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,PRODUCTBANNERMOBILE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute. 
	 * @return the localized productBannerMobile - Product Banner Mobile
	 */
	public Map<Language,Media> getAllProductBannerMobile()
	{
		return getAllProductBannerMobile( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute. 
	 * @param value the productBannerMobile - Product Banner Mobile
	 */
	public void setProductBannerMobile(final SessionContext ctx, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.setProductBannerMobile requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PRODUCTBANNERMOBILE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute. 
	 * @param value the productBannerMobile - Product Banner Mobile
	 */
	public void setProductBannerMobile(final Media value)
	{
		setProductBannerMobile( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute. 
	 * @param value the productBannerMobile - Product Banner Mobile
	 */
	public void setAllProductBannerMobile(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,PRODUCTBANNERMOBILE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productBannerMobile</code> attribute. 
	 * @param value the productBannerMobile - Product Banner Mobile
	 */
	public void setAllProductBannerMobile(final Map<Language,Media> value)
	{
		setAllProductBannerMobile( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productCodes</code> attribute.
	 * @return the productCodes
	 */
	public String getProductCodes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.productCodes</code> attribute.
	 * @return the productCodes
	 */
	public String getProductCodes()
	{
		return getProductCodes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productCodes</code> attribute. 
	 * @param value the productCodes
	 */
	public void setProductCodes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.productCodes</code> attribute. 
	 * @param value the productCodes
	 */
	public void setProductCodes(final String value)
	{
		setProductCodes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute.
	 * @return the title - Product Banner Button
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute.
	 * @return the title - Product Banner Button
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute. 
	 * @return the localized title - Product Banner Button
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute. 
	 * @return the localized title - Product Banner Button
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute. 
	 * @param value the title - Product Banner Button
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneCuratedPLPHSProductComponent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute. 
	 * @param value the title - Product Banner Button
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute. 
	 * @param value the title - Product Banner Button
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneCuratedPLPHSProductComponent.title</code> attribute. 
	 * @param value the title - Product Banner Button
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
