/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneArticle;
import com.siteone.core.jalo.SiteOneArticleCategory;
import com.siteone.core.jalo.SiteOneArticleContent;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneArticle SiteOneArticle}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneArticle extends GenericItem
{
	/** Qualifier of the <code>SiteOneArticle.articleCode</code> attribute **/
	public static final String ARTICLECODE = "articleCode";
	/** Qualifier of the <code>SiteOneArticle.articleName</code> attribute **/
	public static final String ARTICLENAME = "articleName";
	/** Qualifier of the <code>SiteOneArticle.shortDesc</code> attribute **/
	public static final String SHORTDESC = "shortDesc";
	/** Qualifier of the <code>SiteOneArticle.articleMedia</code> attribute **/
	public static final String ARTICLEMEDIA = "articleMedia";
	/** Qualifier of the <code>SiteOneArticle.publisherName</code> attribute **/
	public static final String PUBLISHERNAME = "publisherName";
	/** Qualifier of the <code>SiteOneArticle.articleContents</code> attribute **/
	public static final String ARTICLECONTENTS = "articleContents";
	/** Qualifier of the <code>SiteOneArticle.articleTags</code> attribute **/
	public static final String ARTICLETAGS = "articleTags";
	/** Qualifier of the <code>SiteOneArticle.prevArticle</code> attribute **/
	public static final String PREVARTICLE = "prevArticle";
	/** Qualifier of the <code>SiteOneArticle.nextArticle</code> attribute **/
	public static final String NEXTARTICLE = "nextArticle";
	/** Qualifier of the <code>SiteOneArticle.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteOneArticle.isIndexable</code> attribute **/
	public static final String ISINDEXABLE = "isIndexable";
	/** Qualifier of the <code>SiteOneArticle.fullPagePath</code> attribute **/
	public static final String FULLPAGEPATH = "fullPagePath";
	/** Qualifier of the <code>SiteOneArticle.articleCategory</code> attribute **/
	public static final String ARTICLECATEGORY = "articleCategory";
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n ARTICLECATEGORY's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedSiteOneArticle> ARTICLECATEGORYHANDLER = new BidirectionalOneToManyHandler<GeneratedSiteOneArticle>(
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
		tmp.put(ARTICLECODE, AttributeMode.INITIAL);
		tmp.put(ARTICLENAME, AttributeMode.INITIAL);
		tmp.put(SHORTDESC, AttributeMode.INITIAL);
		tmp.put(ARTICLEMEDIA, AttributeMode.INITIAL);
		tmp.put(PUBLISHERNAME, AttributeMode.INITIAL);
		tmp.put(ARTICLECONTENTS, AttributeMode.INITIAL);
		tmp.put(ARTICLETAGS, AttributeMode.INITIAL);
		tmp.put(PREVARTICLE, AttributeMode.INITIAL);
		tmp.put(NEXTARTICLE, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(ISINDEXABLE, AttributeMode.INITIAL);
		tmp.put(FULLPAGEPATH, AttributeMode.INITIAL);
		tmp.put(ARTICLECATEGORY, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleCategory</code> attribute.
	 * @return the articleCategory
	 */
	public SiteOneArticleCategory getArticleCategory(final SessionContext ctx)
	{
		return (SiteOneArticleCategory)getProperty( ctx, ARTICLECATEGORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleCategory</code> attribute.
	 * @return the articleCategory
	 */
	public SiteOneArticleCategory getArticleCategory()
	{
		return getArticleCategory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleCategory</code> attribute. 
	 * @param value the articleCategory
	 */
	public void setArticleCategory(final SessionContext ctx, final SiteOneArticleCategory value)
	{
		ARTICLECATEGORYHANDLER.addValue( ctx, value, this  );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleCategory</code> attribute. 
	 * @param value the articleCategory
	 */
	public void setArticleCategory(final SiteOneArticleCategory value)
	{
		setArticleCategory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleCode</code> attribute.
	 * @return the articleCode - Attribute for the code of article
	 */
	public String getArticleCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTICLECODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleCode</code> attribute.
	 * @return the articleCode - Attribute for the code of article
	 */
	public String getArticleCode()
	{
		return getArticleCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleCode</code> attribute. 
	 * @param value the articleCode - Attribute for the code of article
	 */
	public void setArticleCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTICLECODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleCode</code> attribute. 
	 * @param value the articleCode - Attribute for the code of article
	 */
	public void setArticleCode(final String value)
	{
		setArticleCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleContents</code> attribute.
	 * @return the articleContents - Attribute for the content of article
	 */
	public List<SiteOneArticleContent> getArticleContents(final SessionContext ctx)
	{
		List<SiteOneArticleContent> coll = (List<SiteOneArticleContent>)getProperty( ctx, ARTICLECONTENTS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleContents</code> attribute.
	 * @return the articleContents - Attribute for the content of article
	 */
	public List<SiteOneArticleContent> getArticleContents()
	{
		return getArticleContents( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleContents</code> attribute. 
	 * @param value the articleContents - Attribute for the content of article
	 */
	public void setArticleContents(final SessionContext ctx, final List<SiteOneArticleContent> value)
	{
		setProperty(ctx, ARTICLECONTENTS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleContents</code> attribute. 
	 * @param value the articleContents - Attribute for the content of article
	 */
	public void setArticleContents(final List<SiteOneArticleContent> value)
	{
		setArticleContents( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleMedia</code> attribute.
	 * @return the articleMedia - Attribute for the media of article
	 */
	public Media getArticleMedia(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, ARTICLEMEDIA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleMedia</code> attribute.
	 * @return the articleMedia - Attribute for the media of article
	 */
	public Media getArticleMedia()
	{
		return getArticleMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleMedia</code> attribute. 
	 * @param value the articleMedia - Attribute for the media of article
	 */
	public void setArticleMedia(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, ARTICLEMEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleMedia</code> attribute. 
	 * @param value the articleMedia - Attribute for the media of article
	 */
	public void setArticleMedia(final Media value)
	{
		setArticleMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleName</code> attribute.
	 * @return the articleName - Attribute for the name of article
	 */
	public String getArticleName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.getArticleName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ARTICLENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleName</code> attribute.
	 * @return the articleName - Attribute for the name of article
	 */
	public String getArticleName()
	{
		return getArticleName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleName</code> attribute. 
	 * @return the localized articleName - Attribute for the name of article
	 */
	public Map<Language,String> getAllArticleName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ARTICLENAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleName</code> attribute. 
	 * @return the localized articleName - Attribute for the name of article
	 */
	public Map<Language,String> getAllArticleName()
	{
		return getAllArticleName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleName</code> attribute. 
	 * @param value the articleName - Attribute for the name of article
	 */
	public void setArticleName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.setArticleName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ARTICLENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleName</code> attribute. 
	 * @param value the articleName - Attribute for the name of article
	 */
	public void setArticleName(final String value)
	{
		setArticleName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleName</code> attribute. 
	 * @param value the articleName - Attribute for the name of article
	 */
	public void setAllArticleName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ARTICLENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleName</code> attribute. 
	 * @param value the articleName - Attribute for the name of article
	 */
	public void setAllArticleName(final Map<Language,String> value)
	{
		setAllArticleName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleTags</code> attribute.
	 * @return the articleTags - Attribute for the Tags of article
	 */
	public List<String> getArticleTags(final SessionContext ctx)
	{
		List<String> coll = (List<String>)getProperty( ctx, ARTICLETAGS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.articleTags</code> attribute.
	 * @return the articleTags - Attribute for the Tags of article
	 */
	public List<String> getArticleTags()
	{
		return getArticleTags( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleTags</code> attribute. 
	 * @param value the articleTags - Attribute for the Tags of article
	 */
	public void setArticleTags(final SessionContext ctx, final List<String> value)
	{
		setProperty(ctx, ARTICLETAGS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.articleTags</code> attribute. 
	 * @param value the articleTags - Attribute for the Tags of article
	 */
	public void setArticleTags(final List<String> value)
	{
		setArticleTags( getSession().getSessionContext(), value );
	}
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		ARTICLECATEGORYHANDLER.newInstance(ctx, allAttributes);
		return super.createItem( ctx, type, allAttributes );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.fullPagePath</code> attribute.
	 * @return the fullPagePath - Attribute for the full page path of article
	 */
	public String getFullPagePath(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.getFullPagePath requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, FULLPAGEPATH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.fullPagePath</code> attribute.
	 * @return the fullPagePath - Attribute for the full page path of article
	 */
	public String getFullPagePath()
	{
		return getFullPagePath( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.fullPagePath</code> attribute. 
	 * @return the localized fullPagePath - Attribute for the full page path of article
	 */
	public Map<Language,String> getAllFullPagePath(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,FULLPAGEPATH,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.fullPagePath</code> attribute. 
	 * @return the localized fullPagePath - Attribute for the full page path of article
	 */
	public Map<Language,String> getAllFullPagePath()
	{
		return getAllFullPagePath( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.fullPagePath</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.setFullPagePath requires a session language", 0 );
		}
		setLocalizedProperty(ctx, FULLPAGEPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setFullPagePath(final String value)
	{
		setFullPagePath( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setAllFullPagePath(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,FULLPAGEPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.fullPagePath</code> attribute. 
	 * @param value the fullPagePath - Attribute for the full page path of article
	 */
	public void setAllFullPagePath(final Map<Language,String> value)
	{
		setAllFullPagePath( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.isIndexable</code> attribute.
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public Boolean isIsIndexable(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISINDEXABLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.isIndexable</code> attribute.
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public Boolean isIsIndexable()
	{
		return isIsIndexable( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.isIndexable</code> attribute. 
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public boolean isIsIndexableAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsIndexable( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.isIndexable</code> attribute. 
	 * @return the isIndexable - Flag for deciding on indexing
	 */
	public boolean isIsIndexableAsPrimitive()
	{
		return isIsIndexableAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISINDEXABLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final Boolean value)
	{
		setIsIndexable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final SessionContext ctx, final boolean value)
	{
		setIsIndexable( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.isIndexable</code> attribute. 
	 * @param value the isIndexable - Flag for deciding on indexing
	 */
	public void setIsIndexable(final boolean value)
	{
		setIsIndexable( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.nextArticle</code> attribute.
	 * @return the nextArticle - Attribute for the next article
	 */
	public SiteOneArticle getNextArticle(final SessionContext ctx)
	{
		return (SiteOneArticle)getProperty( ctx, NEXTARTICLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.nextArticle</code> attribute.
	 * @return the nextArticle - Attribute for the next article
	 */
	public SiteOneArticle getNextArticle()
	{
		return getNextArticle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.nextArticle</code> attribute. 
	 * @param value the nextArticle - Attribute for the next article
	 */
	public void setNextArticle(final SessionContext ctx, final SiteOneArticle value)
	{
		setProperty(ctx, NEXTARTICLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.nextArticle</code> attribute. 
	 * @param value the nextArticle - Attribute for the next article
	 */
	public void setNextArticle(final SiteOneArticle value)
	{
		setNextArticle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.prevArticle</code> attribute.
	 * @return the prevArticle - Attribute for the previous article
	 */
	public SiteOneArticle getPrevArticle(final SessionContext ctx)
	{
		return (SiteOneArticle)getProperty( ctx, PREVARTICLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.prevArticle</code> attribute.
	 * @return the prevArticle - Attribute for the previous article
	 */
	public SiteOneArticle getPrevArticle()
	{
		return getPrevArticle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.prevArticle</code> attribute. 
	 * @param value the prevArticle - Attribute for the previous article
	 */
	public void setPrevArticle(final SessionContext ctx, final SiteOneArticle value)
	{
		setProperty(ctx, PREVARTICLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.prevArticle</code> attribute. 
	 * @param value the prevArticle - Attribute for the previous article
	 */
	public void setPrevArticle(final SiteOneArticle value)
	{
		setPrevArticle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.publisherName</code> attribute.
	 * @return the publisherName - Attribute for the publisher name of article
	 */
	public String getPublisherName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.getPublisherName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PUBLISHERNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.publisherName</code> attribute.
	 * @return the publisherName - Attribute for the publisher name of article
	 */
	public String getPublisherName()
	{
		return getPublisherName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.publisherName</code> attribute. 
	 * @return the localized publisherName - Attribute for the publisher name of article
	 */
	public Map<Language,String> getAllPublisherName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PUBLISHERNAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.publisherName</code> attribute. 
	 * @return the localized publisherName - Attribute for the publisher name of article
	 */
	public Map<Language,String> getAllPublisherName()
	{
		return getAllPublisherName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.publisherName</code> attribute. 
	 * @param value the publisherName - Attribute for the publisher name of article
	 */
	public void setPublisherName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.setPublisherName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PUBLISHERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.publisherName</code> attribute. 
	 * @param value the publisherName - Attribute for the publisher name of article
	 */
	public void setPublisherName(final String value)
	{
		setPublisherName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.publisherName</code> attribute. 
	 * @param value the publisherName - Attribute for the publisher name of article
	 */
	public void setAllPublisherName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PUBLISHERNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.publisherName</code> attribute. 
	 * @param value the publisherName - Attribute for the publisher name of article
	 */
	public void setAllPublisherName(final Map<Language,String> value)
	{
		setAllPublisherName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.shortDesc</code> attribute.
	 * @return the shortDesc - Attribute for the Short Description of article
	 */
	public String getShortDesc(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.getShortDesc requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHORTDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.shortDesc</code> attribute.
	 * @return the shortDesc - Attribute for the Short Description of article
	 */
	public String getShortDesc()
	{
		return getShortDesc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.shortDesc</code> attribute. 
	 * @return the localized shortDesc - Attribute for the Short Description of article
	 */
	public Map<Language,String> getAllShortDesc(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHORTDESC,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.shortDesc</code> attribute. 
	 * @return the localized shortDesc - Attribute for the Short Description of article
	 */
	public Map<Language,String> getAllShortDesc()
	{
		return getAllShortDesc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.shortDesc</code> attribute. 
	 * @param value the shortDesc - Attribute for the Short Description of article
	 */
	public void setShortDesc(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.setShortDesc requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHORTDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.shortDesc</code> attribute. 
	 * @param value the shortDesc - Attribute for the Short Description of article
	 */
	public void setShortDesc(final String value)
	{
		setShortDesc( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.shortDesc</code> attribute. 
	 * @param value the shortDesc - Attribute for the Short Description of article
	 */
	public void setAllShortDesc(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHORTDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.shortDesc</code> attribute. 
	 * @param value the shortDesc - Attribute for the Short Description of article
	 */
	public void setAllShortDesc(final Map<Language,String> value)
	{
		setAllShortDesc( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.title</code> attribute.
	 * @return the title - Title of the Article
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.title</code> attribute.
	 * @return the title - Title of the Article
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.title</code> attribute. 
	 * @return the localized title - Title of the Article
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticle.title</code> attribute. 
	 * @return the localized title - Title of the Article
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.title</code> attribute. 
	 * @param value the title - Title of the Article
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticle.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.title</code> attribute. 
	 * @param value the title - Title of the Article
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.title</code> attribute. 
	 * @param value the title - Title of the Article
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticle.title</code> attribute. 
	 * @param value the title - Title of the Article
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
