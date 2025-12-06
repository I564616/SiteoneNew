/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.acceleratorcms.jalo.components.SimpleBannerComponent;
import de.hybris.platform.cms2.jalo.contents.components.CMSParagraphComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.HomePageCustomerEventsComponent HomePageCustomerEventsComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHomePageCustomerEventsComponent extends SimpleBannerComponent
{
	/** Qualifier of the <code>HomePageCustomerEventsComponent.content</code> attribute **/
	public static final String CONTENT = "content";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleBannerComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CONTENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageCustomerEventsComponent.content</code> attribute.
	 * @return the content
	 */
	public CMSParagraphComponent getContent(final SessionContext ctx)
	{
		return (CMSParagraphComponent)getProperty( ctx, CONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomePageCustomerEventsComponent.content</code> attribute.
	 * @return the content
	 */
	public CMSParagraphComponent getContent()
	{
		return getContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageCustomerEventsComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final SessionContext ctx, final CMSParagraphComponent value)
	{
		setProperty(ctx, CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomePageCustomerEventsComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final CMSParagraphComponent value)
	{
		setContent( getSession().getSessionContext(), value );
	}
	
}
