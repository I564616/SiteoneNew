/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteoneParagraphComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteonePartnerPerkBottomParagraphComponent SiteonePartnerPerkBottomParagraphComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteonePartnerPerkBottomParagraphComponent extends SiteoneParagraphComponent
{
	/** Qualifier of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute **/
	public static final String ISEXTERNALCONTENT = "isExternalContent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SiteoneParagraphComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ISEXTERNALCONTENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute.
	 * @return the isExternalContent
	 */
	public Boolean isIsExternalContent(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISEXTERNALCONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute.
	 * @return the isExternalContent
	 */
	public Boolean isIsExternalContent()
	{
		return isIsExternalContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute. 
	 * @return the isExternalContent
	 */
	public boolean isIsExternalContentAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsExternalContent( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute. 
	 * @return the isExternalContent
	 */
	public boolean isIsExternalContentAsPrimitive()
	{
		return isIsExternalContentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute. 
	 * @param value the isExternalContent
	 */
	public void setIsExternalContent(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISEXTERNALCONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute. 
	 * @param value the isExternalContent
	 */
	public void setIsExternalContent(final Boolean value)
	{
		setIsExternalContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute. 
	 * @param value the isExternalContent
	 */
	public void setIsExternalContent(final SessionContext ctx, final boolean value)
	{
		setIsExternalContent( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteonePartnerPerkBottomParagraphComponent.isExternalContent</code> attribute. 
	 * @param value the isExternalContent
	 */
	public void setIsExternalContent(final boolean value)
	{
		setIsExternalContent( getSession().getSessionContext(), value );
	}
	
}
