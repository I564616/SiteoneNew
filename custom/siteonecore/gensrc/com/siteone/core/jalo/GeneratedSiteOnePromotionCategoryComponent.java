/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.promotions.jalo.AbstractPromotion;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOnePromotionCategoryComponent SiteOnePromotionCategoryComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOnePromotionCategoryComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute **/
	public static final String CATEGORYNAME = "categoryName";
	/** Qualifier of the <code>SiteOnePromotionCategoryComponent.Image</code> attribute **/
	public static final String IMAGE = "Image";
	/** Qualifier of the <code>SiteOnePromotionCategoryComponent.promotionList</code> attribute **/
	public static final String PROMOTIONLIST = "promotionList";
	/** Qualifier of the <code>SiteOnePromotionCategoryComponent.promotionCategory</code> attribute **/
	public static final String PROMOTIONCATEGORY = "promotionCategory";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CATEGORYNAME, AttributeMode.INITIAL);
		tmp.put(IMAGE, AttributeMode.INITIAL);
		tmp.put(PROMOTIONLIST, AttributeMode.INITIAL);
		tmp.put(PROMOTIONCATEGORY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute.
	 * @return the categoryName - Promotions For Category rendered  in this component.
	 */
	public String getCategoryName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionCategoryComponent.getCategoryName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CATEGORYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute.
	 * @return the categoryName - Promotions For Category rendered  in this component.
	 */
	public String getCategoryName()
	{
		return getCategoryName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute. 
	 * @return the localized categoryName - Promotions For Category rendered  in this component.
	 */
	public Map<Language,String> getAllCategoryName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CATEGORYNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute. 
	 * @return the localized categoryName - Promotions For Category rendered  in this component.
	 */
	public Map<Language,String> getAllCategoryName()
	{
		return getAllCategoryName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute. 
	 * @param value the categoryName - Promotions For Category rendered  in this component.
	 */
	public void setCategoryName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOnePromotionCategoryComponent.setCategoryName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CATEGORYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute. 
	 * @param value the categoryName - Promotions For Category rendered  in this component.
	 */
	public void setCategoryName(final String value)
	{
		setCategoryName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute. 
	 * @param value the categoryName - Promotions For Category rendered  in this component.
	 */
	public void setAllCategoryName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CATEGORYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.categoryName</code> attribute. 
	 * @param value the categoryName - Promotions For Category rendered  in this component.
	 */
	public void setAllCategoryName(final Map<Language,String> value)
	{
		setAllCategoryName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.Image</code> attribute.
	 * @return the Image
	 */
	public Media getImage(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.Image</code> attribute.
	 * @return the Image
	 */
	public Media getImage()
	{
		return getImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.Image</code> attribute. 
	 * @param value the Image
	 */
	public void setImage(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.Image</code> attribute. 
	 * @param value the Image
	 */
	public void setImage(final Media value)
	{
		setImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.promotionCategory</code> attribute.
	 * @return the promotionCategory
	 */
	public Category getPromotionCategory(final SessionContext ctx)
	{
		return (Category)getProperty( ctx, PROMOTIONCATEGORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.promotionCategory</code> attribute.
	 * @return the promotionCategory
	 */
	public Category getPromotionCategory()
	{
		return getPromotionCategory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.promotionCategory</code> attribute. 
	 * @param value the promotionCategory
	 */
	public void setPromotionCategory(final SessionContext ctx, final Category value)
	{
		setProperty(ctx, PROMOTIONCATEGORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.promotionCategory</code> attribute. 
	 * @param value the promotionCategory
	 */
	public void setPromotionCategory(final Category value)
	{
		setPromotionCategory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.promotionList</code> attribute.
	 * @return the promotionList
	 */
	public List<AbstractPromotion> getPromotionList(final SessionContext ctx)
	{
		List<AbstractPromotion> coll = (List<AbstractPromotion>)getProperty( ctx, PROMOTIONLIST);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOnePromotionCategoryComponent.promotionList</code> attribute.
	 * @return the promotionList
	 */
	public List<AbstractPromotion> getPromotionList()
	{
		return getPromotionList( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.promotionList</code> attribute. 
	 * @param value the promotionList
	 */
	public void setPromotionList(final SessionContext ctx, final List<AbstractPromotion> value)
	{
		setProperty(ctx, PROMOTIONLIST,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOnePromotionCategoryComponent.promotionList</code> attribute. 
	 * @param value the promotionList
	 */
	public void setPromotionList(final List<AbstractPromotion> value)
	{
		setPromotionList( getSession().getSessionContext(), value );
	}
	
}
