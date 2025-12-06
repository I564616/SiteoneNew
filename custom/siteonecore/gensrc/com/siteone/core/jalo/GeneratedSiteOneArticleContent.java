/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.jalo.GenericItem;
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
 * Generated class for type {@link com.siteone.core.jalo.SiteOneArticleContent SiteOneArticleContent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneArticleContent extends GenericItem
{
	/** Qualifier of the <code>SiteOneArticleContent.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>SiteOneArticleContent.image</code> attribute **/
	public static final String IMAGE = "image";
	/** Qualifier of the <code>SiteOneArticleContent.contentNew</code> attribute **/
	public static final String CONTENTNEW = "contentNew";
	/** Qualifier of the <code>SiteOneArticleContent.alignment</code> attribute **/
	public static final String ALIGNMENT = "alignment";
	/** Qualifier of the <code>SiteOneArticleContent.pullQuote</code> attribute **/
	public static final String PULLQUOTE = "pullQuote";
	/** Qualifier of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute **/
	public static final String ARTICLEPARAGRAPHNEW = "articleParagraphNew";
	/** Qualifier of the <code>SiteOneArticleContent.headLine</code> attribute **/
	public static final String HEADLINE = "headLine";
	/** Qualifier of the <code>SiteOneArticleContent.headLineType</code> attribute **/
	public static final String HEADLINETYPE = "headLineType";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(IMAGE, AttributeMode.INITIAL);
		tmp.put(CONTENTNEW, AttributeMode.INITIAL);
		tmp.put(ALIGNMENT, AttributeMode.INITIAL);
		tmp.put(PULLQUOTE, AttributeMode.INITIAL);
		tmp.put(ARTICLEPARAGRAPHNEW, AttributeMode.INITIAL);
		tmp.put(HEADLINE, AttributeMode.INITIAL);
		tmp.put(HEADLINETYPE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.alignment</code> attribute.
	 * @return the alignment - Attribute for the alignment of the article
	 */
	public String getAlignment(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ALIGNMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.alignment</code> attribute.
	 * @return the alignment - Attribute for the alignment of the article
	 */
	public String getAlignment()
	{
		return getAlignment( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.alignment</code> attribute. 
	 * @param value the alignment - Attribute for the alignment of the article
	 */
	public void setAlignment(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ALIGNMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.alignment</code> attribute. 
	 * @param value the alignment - Attribute for the alignment of the article
	 */
	public void setAlignment(final String value)
	{
		setAlignment( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute.
	 * @return the articleParagraphNew - Attribute for the paragraph of article
	 */
	public String getArticleParagraphNew(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.getArticleParagraphNew requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ARTICLEPARAGRAPHNEW);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute.
	 * @return the articleParagraphNew - Attribute for the paragraph of article
	 */
	public String getArticleParagraphNew()
	{
		return getArticleParagraphNew( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute. 
	 * @return the localized articleParagraphNew - Attribute for the paragraph of article
	 */
	public Map<Language,String> getAllArticleParagraphNew(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ARTICLEPARAGRAPHNEW,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute. 
	 * @return the localized articleParagraphNew - Attribute for the paragraph of article
	 */
	public Map<Language,String> getAllArticleParagraphNew()
	{
		return getAllArticleParagraphNew( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute. 
	 * @param value the articleParagraphNew - Attribute for the paragraph of article
	 */
	public void setArticleParagraphNew(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.setArticleParagraphNew requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ARTICLEPARAGRAPHNEW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute. 
	 * @param value the articleParagraphNew - Attribute for the paragraph of article
	 */
	public void setArticleParagraphNew(final String value)
	{
		setArticleParagraphNew( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute. 
	 * @param value the articleParagraphNew - Attribute for the paragraph of article
	 */
	public void setAllArticleParagraphNew(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ARTICLEPARAGRAPHNEW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.articleParagraphNew</code> attribute. 
	 * @param value the articleParagraphNew - Attribute for the paragraph of article
	 */
	public void setAllArticleParagraphNew(final Map<Language,String> value)
	{
		setAllArticleParagraphNew( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.code</code> attribute.
	 * @return the code - Attribute for the code of article Content
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.code</code> attribute.
	 * @return the code - Attribute for the code of article Content
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.code</code> attribute. 
	 * @param value the code - Attribute for the code of article Content
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.code</code> attribute. 
	 * @param value the code - Attribute for the code of article Content
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.contentNew</code> attribute.
	 * @return the contentNew - Attribute for the content of article
	 */
	public String getContentNew(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.getContentNew requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CONTENTNEW);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.contentNew</code> attribute.
	 * @return the contentNew - Attribute for the content of article
	 */
	public String getContentNew()
	{
		return getContentNew( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.contentNew</code> attribute. 
	 * @return the localized contentNew - Attribute for the content of article
	 */
	public Map<Language,String> getAllContentNew(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CONTENTNEW,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.contentNew</code> attribute. 
	 * @return the localized contentNew - Attribute for the content of article
	 */
	public Map<Language,String> getAllContentNew()
	{
		return getAllContentNew( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.contentNew</code> attribute. 
	 * @param value the contentNew - Attribute for the content of article
	 */
	public void setContentNew(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.setContentNew requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CONTENTNEW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.contentNew</code> attribute. 
	 * @param value the contentNew - Attribute for the content of article
	 */
	public void setContentNew(final String value)
	{
		setContentNew( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.contentNew</code> attribute. 
	 * @param value the contentNew - Attribute for the content of article
	 */
	public void setAllContentNew(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CONTENTNEW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.contentNew</code> attribute. 
	 * @param value the contentNew - Attribute for the content of article
	 */
	public void setAllContentNew(final Map<Language,String> value)
	{
		setAllContentNew( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.headLine</code> attribute.
	 * @return the headLine - headline of content
	 */
	public String getHeadLine(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.getHeadLine requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, HEADLINE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.headLine</code> attribute.
	 * @return the headLine - headline of content
	 */
	public String getHeadLine()
	{
		return getHeadLine( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.headLine</code> attribute. 
	 * @return the localized headLine - headline of content
	 */
	public Map<Language,String> getAllHeadLine(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,HEADLINE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.headLine</code> attribute. 
	 * @return the localized headLine - headline of content
	 */
	public Map<Language,String> getAllHeadLine()
	{
		return getAllHeadLine( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.headLine</code> attribute. 
	 * @param value the headLine - headline of content
	 */
	public void setHeadLine(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.setHeadLine requires a session language", 0 );
		}
		setLocalizedProperty(ctx, HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.headLine</code> attribute. 
	 * @param value the headLine - headline of content
	 */
	public void setHeadLine(final String value)
	{
		setHeadLine( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.headLine</code> attribute. 
	 * @param value the headLine - headline of content
	 */
	public void setAllHeadLine(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,HEADLINE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.headLine</code> attribute. 
	 * @param value the headLine - headline of content
	 */
	public void setAllHeadLine(final Map<Language,String> value)
	{
		setAllHeadLine( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.headLineType</code> attribute.
	 * @return the headLineType - headline type of content
	 */
	public String getHeadLineType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, HEADLINETYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.headLineType</code> attribute.
	 * @return the headLineType - headline type of content
	 */
	public String getHeadLineType()
	{
		return getHeadLineType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.headLineType</code> attribute. 
	 * @param value the headLineType - headline type of content
	 */
	public void setHeadLineType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, HEADLINETYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.headLineType</code> attribute. 
	 * @param value the headLineType - headline type of content
	 */
	public void setHeadLineType(final String value)
	{
		setHeadLineType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.image</code> attribute.
	 * @return the image - Attribute for the image of article
	 */
	public Media getImage(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.image</code> attribute.
	 * @return the image - Attribute for the image of article
	 */
	public Media getImage()
	{
		return getImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.image</code> attribute. 
	 * @param value the image - Attribute for the image of article
	 */
	public void setImage(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.image</code> attribute. 
	 * @param value the image - Attribute for the image of article
	 */
	public void setImage(final Media value)
	{
		setImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.pullQuote</code> attribute.
	 * @return the pullQuote - Attribute for the pull quote of article
	 */
	public String getPullQuote(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.getPullQuote requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, PULLQUOTE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.pullQuote</code> attribute.
	 * @return the pullQuote - Attribute for the pull quote of article
	 */
	public String getPullQuote()
	{
		return getPullQuote( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.pullQuote</code> attribute. 
	 * @return the localized pullQuote - Attribute for the pull quote of article
	 */
	public Map<Language,String> getAllPullQuote(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,PULLQUOTE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneArticleContent.pullQuote</code> attribute. 
	 * @return the localized pullQuote - Attribute for the pull quote of article
	 */
	public Map<Language,String> getAllPullQuote()
	{
		return getAllPullQuote( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.pullQuote</code> attribute. 
	 * @param value the pullQuote - Attribute for the pull quote of article
	 */
	public void setPullQuote(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneArticleContent.setPullQuote requires a session language", 0 );
		}
		setLocalizedProperty(ctx, PULLQUOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.pullQuote</code> attribute. 
	 * @param value the pullQuote - Attribute for the pull quote of article
	 */
	public void setPullQuote(final String value)
	{
		setPullQuote( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.pullQuote</code> attribute. 
	 * @param value the pullQuote - Attribute for the pull quote of article
	 */
	public void setAllPullQuote(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,PULLQUOTE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneArticleContent.pullQuote</code> attribute. 
	 * @param value the pullQuote - Attribute for the pull quote of article
	 */
	public void setAllPullQuote(final Map<Language,String> value)
	{
		setAllPullQuote( getSession().getSessionContext(), value );
	}
	
}
