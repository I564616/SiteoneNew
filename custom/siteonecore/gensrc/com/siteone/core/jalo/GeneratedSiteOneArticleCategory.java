/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneArticle;
import com.siteone.core.jalo.SiteoneArticleBannerComponent;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneArticleCategory SiteOneArticleCategory}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneArticleCategory extends GenericItem
{
	/** Qualifier of the <code>SiteOneArticleCategory.articleCategoryCode</code> attribute **/
	public static final String ARTICLECATEGORYCODE = "articleCategoryCode";
	/** Qualifier of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute **/
	public static final String ARTICLECATEGORYNAME = "articleCategoryName";
	/** Qualifier of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute **/
	public static final String DESCRIPTIONHEADING = "descriptionHeading";
	/** Qualifier of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute **/
	public static final String ARTICLECATEGORYCONTENT = "articleCategoryContent";
	/** Qualifier of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute **/
	public static final String NUMBEROFARTICLES = "numberOfArticles";
	/** Qualifier of the <code>SiteOneArticleCategory.heroBanner</code> attribute **/
	public static final String HEROBANNER = "heroBanner";
	/** Qualifier of the <code>SiteOneArticleCategory.featuredArticle</code> attribute **/
	public static final String FEATUREDARTICLE = "featuredArticle";
	/** Qualifier of the <code>SiteOneArticleCategory.isIndexable</code> attribute **/
	public static final String ISINDEXABLE = "isIndexable";
	/** Qualifier of the <code>SiteOneArticleCategory.fullPagePath</code> attribute **/
	public static final String FULLPAGEPATH = "fullPagePath";
	/** Qualifier of the <code>SiteOneArticleCategory.pathingChannel</code> attribute **/
	public static final String PATHINGCHANNEL = "pathingChannel";
	/** Qualifier of the <code>SiteOneArticleCategory.articles</code> attribute **/
	public static final String ARTICLES = "articles";
	/**
	* {@link OneToManyHandler} for handling 1:n ARTICLES's relation attributes from 'many' side.
	**/
	protected static final OneToManyHandler<SiteOneArticle> ARTICLESHANDLER = new OneToManyHandler<SiteOneArticle>(
	SiteoneCoreConstants.TC.SITEONEARTICLE,
	false,
	"articleCategory",
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ARTICLECATEGORYCODE, AttributeMode.INITIAL);
		tmp.put(ARTICLECATEGORYNAME, AttributeMode.INITIAL);
		tmp.put(DESCRIPTIONHEADING, AttributeMode.INITIAL);
		tmp.put(ARTICLECATEGORYCONTENT, AttributeMode.INITIAL);
		tmp.put(NUMBEROFARTICLES, AttributeMode.INITIAL);
		tmp.put(HEROBANNER, AttributeMode.INITIAL);
		tmp.put(FEATUREDARTICLE, AttributeMode.INITIAL);
		tmp.put(ISINDEXABLE, AttributeMode.INITIAL);
		tmp.put(FULLPAGEPATH, AttributeMode.INITIAL);
		tmp.put(PATHINGCHANNEL, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryCode</code> attribute.
	 * @return the articleCategoryCode - Attribute for the category code of article
	 */
	public String getArticleCategoryCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTICLECATEGORYCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryCode</code> attribute.
	 * @return the articleCategoryCode - Attribute for the category code of article
	 */
	public String getArticleCategoryCode()
	{
		return getArticleCategoryCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryCode</code> attribute. 
	 * @param value the articleCategoryCode - Attribute for the category code of article
	 */
	public void setArticleCategoryCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTICLECATEGORYCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryCode</code> attribute. 
	 * @param value the articleCategoryCode - Attribute for the category code of article
	 */
	public void setArticleCategoryCode(final String value)
	{
		setArticleCategoryCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute.
	 * @return the articleCategoryContent - Attribute for the content of article
	 */
	public String getArticleCategoryContent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.getArticleCategoryContent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ARTICLECATEGORYCONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute.
	 * @return the articleCategoryContent - Attribute for the content of article
	 */
	public String getArticleCategoryContent()
	{
		return getArticleCategoryContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute. 
	 * @return the localized articleCategoryContent - Attribute for the content of article
	 */
	public Map<Language,String> getAllArticleCategoryContent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ARTICLECATEGORYCONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute. 
	 * @return the localized articleCategoryContent - Attribute for the content of article
	 */
	public Map<Language,String> getAllArticleCategoryContent()
	{
		return getAllArticleCategoryContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute. 
	 * @param value the articleCategoryContent - Attribute for the content of article
	 */
	public void setArticleCategoryContent(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.setArticleCategoryContent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ARTICLECATEGORYCONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute. 
	 * @param value the articleCategoryContent - Attribute for the content of article
	 */
	public void setArticleCategoryContent(final String value)
	{
		setArticleCategoryContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute. 
	 * @param value the articleCategoryContent - Attribute for the content of article
	 */
	public void setAllArticleCategoryContent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ARTICLECATEGORYCONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryContent</code> attribute. 
	 * @param value the articleCategoryContent - Attribute for the content of article
	 */
	public void setAllArticleCategoryContent(final Map<Language,String> value)
	{
		setAllArticleCategoryContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute.
	 * @return the articleCategoryName - Attribute for the category name of article
	 */
	public String getArticleCategoryName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.getArticleCategoryName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ARTICLECATEGORYNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute.
	 * @return the articleCategoryName - Attribute for the category name of article
	 */
	public String getArticleCategoryName()
	{
		return getArticleCategoryName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute. 
	 * @return the localized articleCategoryName - Attribute for the category name of article
	 */
	public Map<Language,String> getAllArticleCategoryName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ARTICLECATEGORYNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute. 
	 * @return the localized articleCategoryName - Attribute for the category name of article
	 */
	public Map<Language,String> getAllArticleCategoryName()
	{
		return getAllArticleCategoryName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute. 
	 * @param value the articleCategoryName - Attribute for the category name of article
	 */
	public void setArticleCategoryName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.setArticleCategoryName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ARTICLECATEGORYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute. 
	 * @param value the articleCategoryName - Attribute for the category name of article
	 */
	public void setArticleCategoryName(final String value)
	{
		setArticleCategoryName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute. 
	 * @param value the articleCategoryName - Attribute for the category name of article
	 */
	public void setAllArticleCategoryName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ARTICLECATEGORYNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articleCategoryName</code> attribute. 
	 * @param value the articleCategoryName - Attribute for the category name of article
	 */
	public void setAllArticleCategoryName(final Map<Language,String> value)
	{
		setAllArticleCategoryName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articles</code> attribute.
	 * @return the articles
	 */
	public Collection<SiteOneArticle> getArticles(final SessionContext ctx)
	{
		return ARTICLESHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.articles</code> attribute.
	 * @return the articles
	 */
	public Collection<SiteOneArticle> getArticles()
	{
		return getArticles( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articles</code> attribute. 
	 * @param value the articles
	 */
	public void setArticles(final SessionContext ctx, final Collection<SiteOneArticle> value)
	{
		ARTICLESHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.articles</code> attribute. 
	 * @param value the articles
	 */
	public void setArticles(final Collection<SiteOneArticle> value)
	{
		setArticles( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to articles. 
	 * @param value the item to add to articles
	 */
	public void addToArticles(final SessionContext ctx, final SiteOneArticle value)
	{
		ARTICLESHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to articles. 
	 * @param value the item to add to articles
	 */
	public void addToArticles(final SiteOneArticle value)
	{
		addToArticles( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from articles. 
	 * @param value the item to remove from articles
	 */
	public void removeFromArticles(final SessionContext ctx, final SiteOneArticle value)
	{
		ARTICLESHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from articles. 
	 * @param value the item to remove from articles
	 */
	public void removeFromArticles(final SiteOneArticle value)
	{
		removeFromArticles( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute.
	 * @return the descriptionHeading - Attribute for the category name of article
	 */
	public String getDescriptionHeading(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.getDescriptionHeading requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTIONHEADING);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute.
	 * @return the descriptionHeading - Attribute for the category name of article
	 */
	public String getDescriptionHeading()
	{
		return getDescriptionHeading( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute. 
	 * @return the localized descriptionHeading - Attribute for the category name of article
	 */
	public Map<Language,String> getAllDescriptionHeading(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTIONHEADING,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute. 
	 * @return the localized descriptionHeading - Attribute for the category name of article
	 */
	public Map<Language,String> getAllDescriptionHeading()
	{
		return getAllDescriptionHeading( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute. 
	 * @param value the descriptionHeading - Attribute for the category name of article
	 */
	public void setDescriptionHeading(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.setDescriptionHeading requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTIONHEADING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute. 
	 * @param value the descriptionHeading - Attribute for the category name of article
	 */
	public void setDescriptionHeading(final String value)
	{
		setDescriptionHeading( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute. 
	 * @param value the descriptionHeading - Attribute for the category name of article
	 */
	public void setAllDescriptionHeading(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTIONHEADING,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.descriptionHeading</code> attribute. 
	 * @param value the descriptionHeading - Attribute for the category name of article
	 */
	public void setAllDescriptionHeading(final Map<Language,String> value)
	{
		setAllDescriptionHeading( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.featuredArticle</code> attribute.
	 * @return the featuredArticle - Attribute for the category of article
	 */
	public SiteOneArticle getFeaturedArticle(final SessionContext ctx)
	{
		return (SiteOneArticle)getProperty( ctx, FEATUREDARTICLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.featuredArticle</code> attribute.
	 * @return the featuredArticle - Attribute for the category of article
	 */
	public SiteOneArticle getFeaturedArticle()
	{
		return getFeaturedArticle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.featuredArticle</code> attribute. 
	 * @param value the featuredArticle - Attribute for the category of article
	 */
	public void setFeaturedArticle(final SessionContext ctx, final SiteOneArticle value)
	{
		setProperty(ctx, FEATUREDARTICLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.featuredArticle</code> attribute. 
	 * @param value the featuredArticle - Attribute for the category of article
	 */
	public void setFeaturedArticle(final SiteOneArticle value)
	{
		setFeaturedArticle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute.
	 * @return the fullPagePath - Attribute for the full page path of article
	 */
	public String getFullPagePath(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.getFullPagePath requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, FULLPAGEPATH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute.
	 * @return the fullPagePath - Attribute for the full page path of article
	 */
	public String getFullPagePath()
	{
		return getFullPagePath( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute. 
	 * @return the localized fullPagePath - Attribute for the full page path of article
	 */
	public Map<Language,String> getAllFullPagePath(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,FULLPAGEPATH,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute. 
	 * @return the localized fullPagePath - Attribute for the full page path of article
	 */
	public Map<Language,String> getAllFullPagePath()
	{
		return getAllFullPagePath( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setFullPagePath(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.setFullPagePath requires a session language", 0 );
		}
		setLocalizedProperty(ctx, FULLPAGEPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setFullPagePath(final String value)
	{
		setFullPagePath( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setAllFullPagePath(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,FULLPAGEPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setAllFullPagePath(final Map<Language,String> value)
	{
		setAllFullPagePath( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.heroBanner</code> attribute.
	 * @return the heroBanner - Attribute for the category of article
	 */
	public SiteoneArticleBannerComponent getHeroBanner(final SessionContext ctx)
	{
		return (SiteoneArticleBannerComponent)getProperty( ctx, HEROBANNER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.heroBanner</code> attribute.
	 * @return the heroBanner - Attribute for the category of article
	 */
	public SiteoneArticleBannerComponent getHeroBanner()
	{
		return getHeroBanner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.heroBanner</code> attribute. 
	 * @param value the heroBanner - Attribute for the category of article
	 */
	public void setHeroBanner(final SessionContext ctx, final SiteoneArticleBannerComponent value)
	{
		setProperty(ctx, HEROBANNER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.heroBanner</code> attribute. 
	 * @param value the heroBanner - Attribute for the category of article
	 */
	public void setHeroBanner(final SiteoneArticleBannerComponent value)
	{
		setHeroBanner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.isIndexable</code> attribute.
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public Boolean isIsIndexable(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISINDEXABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.isIndexable</code> attribute.
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public Boolean isIsIndexable()
	{
		return isIsIndexable( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.isIndexable</code> attribute. 
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public boolean isIsIndexableAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsIndexable( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.isIndexable</code> attribute. 
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public boolean isIsIndexableAsPrimitive()
	{
		return isIsIndexableAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISINDEXABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final Boolean value)
	{
		setIsIndexable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final SessionContext ctx, final boolean value)
	{
		setIsIndexable( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final boolean value)
	{
		setIsIndexable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute.
	 * @return the numberOfArticles - Attribute for the category of article
	 */
	public Integer getNumberOfArticles(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, NUMBEROFARTICLES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute.
	 * @return the numberOfArticles - Attribute for the category of article
	 */
	public Integer getNumberOfArticles()
	{
		return getNumberOfArticles( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute. 
	 * @return the numberOfArticles - Attribute for the category of article
	 */
	public int getNumberOfArticlesAsPrimitive(final SessionContext ctx)
	{
		Integer value = getNumberOfArticles( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute. 
	 * @return the numberOfArticles - Attribute for the category of article
	 */
	public int getNumberOfArticlesAsPrimitive()
	{
		return getNumberOfArticlesAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute. 
	 * @param value the numberOfArticles - Attribute for the category of article
	 */
	public void setNumberOfArticles(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, NUMBEROFARTICLES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute. 
	 * @param value the numberOfArticles - Attribute for the category of article
	 */
	public void setNumberOfArticles(final Integer value)
	{
		setNumberOfArticles( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute. 
	 * @param value the numberOfArticles - Attribute for the category of article
	 */
	public void setNumberOfArticles(final SessionContext ctx, final int value)
	{
		setNumberOfArticles( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.numberOfArticles</code> attribute. 
	 * @param value the numberOfArticles - Attribute for the category of article
	 */
	public void setNumberOfArticles(final int value)
	{
		setNumberOfArticles( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute.
	 * @return the pathingChannel
	 */
	public String getPathingChannel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.getPathingChannel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PATHINGCHANNEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute.
	 * @return the pathingChannel
	 */
	public String getPathingChannel()
	{
		return getPathingChannel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute. 
	 * @return the localized pathingChannel
	 */
	public Map<Language,String> getAllPathingChannel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PATHINGCHANNEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute. 
	 * @return the localized pathingChannel
	 */
	public Map<Language,String> getAllPathingChannel()
	{
		return getAllPathingChannel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setPathingChannel(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleCategory.setPathingChannel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PATHINGCHANNEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setPathingChannel(final String value)
	{
		setPathingChannel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setAllPathingChannel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PATHINGCHANNEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleCategory.pathingChannel</code> attribute. 
	 * @param value the pathingChannel
	 */
	public void setAllPathingChannel(final Map<Language,String> value)
	{
		setAllPathingChannel( getSession().getSessionContext(), value );
	}
	
}
