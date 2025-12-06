/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2lib.components.BannerComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneGalleryHeaderComponent SiteOneGalleryHeaderComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneGalleryHeaderComponent extends BannerComponent
{
	/** Qualifier of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute **/
	public static final String ISIMAGERIGHTALIGNED = "isImageRightAligned";
	/** Qualifier of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>SiteOneGalleryHeaderComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(BannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ISIMAGERIGHTALIGNED, AttributeMode.INITIAL);
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
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderComponent.getButtonLabel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel()
	{
		return getAllButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneGalleryHeaderComponent.setButtonLabel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final Map<Language,String> value)
	{
		setAllButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute.
	 * @return the isImageRightAligned
	 */
	public Boolean isIsImageRightAligned(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISIMAGERIGHTALIGNED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute.
	 * @return the isImageRightAligned
	 */
	public Boolean isIsImageRightAligned()
	{
		return isIsImageRightAligned( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute. 
	 * @return the isImageRightAligned
	 */
	public boolean isIsImageRightAlignedAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsImageRightAligned( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute. 
	 * @return the isImageRightAligned
	 */
	public boolean isIsImageRightAlignedAsPrimitive()
	{
		return isIsImageRightAlignedAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute. 
	 * @param value the isImageRightAligned
	 */
	public void setIsImageRightAligned(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISIMAGERIGHTALIGNED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute. 
	 * @param value the isImageRightAligned
	 */
	public void setIsImageRightAligned(final Boolean value)
	{
		setIsImageRightAligned( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute. 
	 * @param value the isImageRightAligned
	 */
	public void setIsImageRightAligned(final SessionContext ctx, final boolean value)
	{
		setIsImageRightAligned( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneGalleryHeaderComponent.isImageRightAligned</code> attribute. 
	 * @param value the isImageRightAligned
	 */
	public void setIsImageRightAligned(final boolean value)
	{
		setIsImageRightAligned( getSession().getSessionContext(), value );
	}
	
}
