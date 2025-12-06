/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import com.siteone.pimintegration.jalo.SiteOneProductImage;
import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.util.PartOfHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type SiteOneProductItemImages.
 */
@SLDSafe
@SuppressWarnings({"unused","cast"})
public class SiteOneProductItemImages extends GenericItem
{
	/** Qualifier of the <code>SiteOneProductItemImages.image</code> attribute **/
	public static final String IMAGE = "image";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(IMAGE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductItemImages.image</code> attribute.
	 * @return the image
	 */
	public SiteOneProductImage getImage(final SessionContext ctx)
	{
		return (SiteOneProductImage)getProperty( ctx, "image".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneProductItemImages.image</code> attribute.
	 * @return the image
	 */
	public SiteOneProductImage getImage()
	{
		return getImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductItemImages.image</code> attribute. 
	 * @param value the image
	 */
	public void setImage(final SessionContext ctx, final SiteOneProductImage value)
	{
		new PartOfHandler<SiteOneProductImage>()
		{
			@Override
			protected SiteOneProductImage doGetValue(final SessionContext ctx)
			{
				return getImage( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final SiteOneProductImage _value)
			{
				final SiteOneProductImage value = _value;
				setProperty(ctx, "image".intern(),value);
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneProductItemImages.image</code> attribute. 
	 * @param value the image
	 */
	public void setImage(final SiteOneProductImage value)
	{
		setImage( getSession().getSessionContext(), value );
	}
	
}
