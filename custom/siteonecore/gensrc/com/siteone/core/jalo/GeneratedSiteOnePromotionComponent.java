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
import de.hybris.platform.promotions.jalo.AbstractPromotion;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOnePromotionComponent SiteOnePromotionComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOnePromotionComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOnePromotionComponent.promotion</code> attribute **/
	public static final String PROMOTION = "promotion";
	/** Qualifier of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(PROMOTION, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionComponent.getButtonLabel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel()
	{
		return getAllButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionComponent.setButtonLabel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final Map<Language,String> value)
	{
		setAllButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionComponent.promotion</code> attribute.
	 * @return the promotion - Promotion rendered  in this component.
	 */
	public AbstractPromotion getPromotion(final SessionContext ctx)
	{
		return (AbstractPromotion)getProperty( ctx, PROMOTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionComponent.promotion</code> attribute.
	 * @return the promotion - Promotion rendered  in this component.
	 */
	public AbstractPromotion getPromotion()
	{
		return getPromotion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionComponent.promotion</code> attribute. 
	 * @param value the promotion - Promotion rendered  in this component.
	 */
	public void setPromotion(final SessionContext ctx, final AbstractPromotion value)
	{
		setProperty(ctx, PROMOTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionComponent.promotion</code> attribute. 
	 * @param value the promotion - Promotion rendered  in this component.
	 */
	public void setPromotion(final AbstractPromotion value)
	{
		setPromotion( getSession().getSessionContext(), value );
	}
	
}
