/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.Inspiration;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneInspiration SiteOneInspiration}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneInspiration extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOneInspiration.gallery</code> attribute **/
	public static final String GALLERY = "gallery";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(GALLERY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInspiration.gallery</code> attribute.
	 * @return the gallery
	 */
	public List<Inspiration> getGallery(final SessionContext ctx)
	{
		List<Inspiration> coll = (List<Inspiration>)getProperty( ctx, GALLERY);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneInspiration.gallery</code> attribute.
	 * @return the gallery
	 */
	public List<Inspiration> getGallery()
	{
		return getGallery( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInspiration.gallery</code> attribute. 
	 * @param value the gallery
	 */
	public void setGallery(final SessionContext ctx, final List<Inspiration> value)
	{
		setProperty(ctx, GALLERY,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneInspiration.gallery</code> attribute. 
	 * @param value the gallery
	 */
	public void setGallery(final List<Inspiration> value)
	{
		setGallery( getSession().getSessionContext(), value );
	}
	
}
