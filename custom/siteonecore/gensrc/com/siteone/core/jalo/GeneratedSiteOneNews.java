/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneNews;
import de.hybris.platform.cms2.jalo.contents.components.CMSParagraphComponent;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneNews SiteOneNews}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneNews extends GenericItem
{
	/** Qualifier of the <code>SiteOneNews.newsCode</code> attribute **/
	public static final String NEWSCODE = "newsCode";
	/** Qualifier of the <code>SiteOneNews.newsMedia</code> attribute **/
	public static final String NEWSMEDIA = "newsMedia";
	/** Qualifier of the <code>SiteOneNews.newsURL</code> attribute **/
	public static final String NEWSURL = "newsURL";
	/** Qualifier of the <code>SiteOneNews.targetWindow</code> attribute **/
	public static final String TARGETWINDOW = "targetWindow";
	/** Qualifier of the <code>SiteOneNews.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteOneNews.shortDesc</code> attribute **/
	public static final String SHORTDESC = "shortDesc";
	/** Qualifier of the <code>SiteOneNews.newsBrief</code> attribute **/
	public static final String NEWSBRIEF = "newsBrief";
	/** Qualifier of the <code>SiteOneNews.newsPublishDate</code> attribute **/
	public static final String NEWSPUBLISHDATE = "newsPublishDate";
	/** Qualifier of the <code>SiteOneNews.longDescription</code> attribute **/
	public static final String LONGDESCRIPTION = "longDescription";
	/** Qualifier of the <code>SiteOneNews.prevNews</code> attribute **/
	public static final String PREVNEWS = "prevNews";
	/** Qualifier of the <code>SiteOneNews.nextNews</code> attribute **/
	public static final String NEXTNEWS = "nextNews";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(NEWSCODE, AttributeMode.INITIAL);
		tmp.put(NEWSMEDIA, AttributeMode.INITIAL);
		tmp.put(NEWSURL, AttributeMode.INITIAL);
		tmp.put(TARGETWINDOW, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(SHORTDESC, AttributeMode.INITIAL);
		tmp.put(NEWSBRIEF, AttributeMode.INITIAL);
		tmp.put(NEWSPUBLISHDATE, AttributeMode.INITIAL);
		tmp.put(LONGDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(PREVNEWS, AttributeMode.INITIAL);
		tmp.put(NEXTNEWS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.longDescription</code> attribute.
	 * @return the longDescription - News long description
	 */
	public String getLongDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneNews.getLongDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, LONGDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.longDescription</code> attribute.
	 * @return the longDescription - News long description
	 */
	public String getLongDescription()
	{
		return getLongDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.longDescription</code> attribute. 
	 * @return the localized longDescription - News long description
	 */
	public Map<Language,String> getAllLongDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,LONGDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.longDescription</code> attribute. 
	 * @return the localized longDescription - News long description
	 */
	public Map<Language,String> getAllLongDescription()
	{
		return getAllLongDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.longDescription</code> attribute. 
	 * @param value the longDescription - News long description
	 */
	public void setLongDescription(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneNews.setLongDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, LONGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.longDescription</code> attribute. 
	 * @param value the longDescription - News long description
	 */
	public void setLongDescription(final String value)
	{
		setLongDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.longDescription</code> attribute. 
	 * @param value the longDescription - News long description
	 */
	public void setAllLongDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,LONGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.longDescription</code> attribute. 
	 * @param value the longDescription - News long description
	 */
	public void setAllLongDescription(final Map<Language,String> value)
	{
		setAllLongDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsBrief</code> attribute.
	 * @return the newsBrief - Localized news Content/Brief of the  News component.
	 */
	public CMSParagraphComponent getNewsBrief(final SessionContext ctx)
	{
		return (CMSParagraphComponent)getProperty( ctx, NEWSBRIEF);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsBrief</code> attribute.
	 * @return the newsBrief - Localized news Content/Brief of the  News component.
	 */
	public CMSParagraphComponent getNewsBrief()
	{
		return getNewsBrief( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsBrief</code> attribute. 
	 * @param value the newsBrief - Localized news Content/Brief of the  News component.
	 */
	public void setNewsBrief(final SessionContext ctx, final CMSParagraphComponent value)
	{
		setProperty(ctx, NEWSBRIEF,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsBrief</code> attribute. 
	 * @param value the newsBrief - Localized news Content/Brief of the  News component.
	 */
	public void setNewsBrief(final CMSParagraphComponent value)
	{
		setNewsBrief( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsCode</code> attribute.
	 * @return the newsCode - Localized News unique code.
	 */
	public String getNewsCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NEWSCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsCode</code> attribute.
	 * @return the newsCode - Localized News unique code.
	 */
	public String getNewsCode()
	{
		return getNewsCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsCode</code> attribute. 
	 * @param value the newsCode - Localized News unique code.
	 */
	public void setNewsCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NEWSCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsCode</code> attribute. 
	 * @param value the newsCode - Localized News unique code.
	 */
	public void setNewsCode(final String value)
	{
		setNewsCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsMedia</code> attribute.
	 * @return the newsMedia - Localized newsMedia of the  News component.
	 */
	public Media getNewsMedia(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, NEWSMEDIA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsMedia</code> attribute.
	 * @return the newsMedia - Localized newsMedia of the  News component.
	 */
	public Media getNewsMedia()
	{
		return getNewsMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsMedia</code> attribute. 
	 * @param value the newsMedia - Localized newsMedia of the  News component.
	 */
	public void setNewsMedia(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, NEWSMEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsMedia</code> attribute. 
	 * @param value the newsMedia - Localized newsMedia of the  News component.
	 */
	public void setNewsMedia(final Media value)
	{
		setNewsMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsPublishDate</code> attribute.
	 * @return the newsPublishDate - Localized newsPublishDate of the  News component.
	 */
	public Date getNewsPublishDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, NEWSPUBLISHDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsPublishDate</code> attribute.
	 * @return the newsPublishDate - Localized newsPublishDate of the  News component.
	 */
	public Date getNewsPublishDate()
	{
		return getNewsPublishDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsPublishDate</code> attribute. 
	 * @param value the newsPublishDate - Localized newsPublishDate of the  News component.
	 */
	public void setNewsPublishDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, NEWSPUBLISHDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsPublishDate</code> attribute. 
	 * @param value the newsPublishDate - Localized newsPublishDate of the  News component.
	 */
	public void setNewsPublishDate(final Date value)
	{
		setNewsPublishDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsURL</code> attribute.
	 * @return the newsURL - Localized News URL.
	 */
	public String getNewsURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, NEWSURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.newsURL</code> attribute.
	 * @return the newsURL - Localized News URL.
	 */
	public String getNewsURL()
	{
		return getNewsURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsURL</code> attribute. 
	 * @param value the newsURL - Localized News URL.
	 */
	public void setNewsURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, NEWSURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.newsURL</code> attribute. 
	 * @param value the newsURL - Localized News URL.
	 */
	public void setNewsURL(final String value)
	{
		setNewsURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.nextNews</code> attribute.
	 * @return the nextNews - Next News
	 */
	public SiteOneNews getNextNews(final SessionContext ctx)
	{
		return (SiteOneNews)getProperty( ctx, NEXTNEWS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.nextNews</code> attribute.
	 * @return the nextNews - Next News
	 */
	public SiteOneNews getNextNews()
	{
		return getNextNews( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.nextNews</code> attribute. 
	 * @param value the nextNews - Next News
	 */
	public void setNextNews(final SessionContext ctx, final SiteOneNews value)
	{
		setProperty(ctx, NEXTNEWS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.nextNews</code> attribute. 
	 * @param value the nextNews - Next News
	 */
	public void setNextNews(final SiteOneNews value)
	{
		setNextNews( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.prevNews</code> attribute.
	 * @return the prevNews - Previous News
	 */
	public SiteOneNews getPrevNews(final SessionContext ctx)
	{
		return (SiteOneNews)getProperty( ctx, PREVNEWS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.prevNews</code> attribute.
	 * @return the prevNews - Previous News
	 */
	public SiteOneNews getPrevNews()
	{
		return getPrevNews( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.prevNews</code> attribute. 
	 * @param value the prevNews - Previous News
	 */
	public void setPrevNews(final SessionContext ctx, final SiteOneNews value)
	{
		setProperty(ctx, PREVNEWS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.prevNews</code> attribute. 
	 * @param value the prevNews - Previous News
	 */
	public void setPrevNews(final SiteOneNews value)
	{
		setPrevNews( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.shortDesc</code> attribute.
	 * @return the shortDesc - Localized News short description.
	 */
	public String getShortDesc(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneNews.getShortDesc requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHORTDESC);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.shortDesc</code> attribute.
	 * @return the shortDesc - Localized News short description.
	 */
	public String getShortDesc()
	{
		return getShortDesc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.shortDesc</code> attribute. 
	 * @return the localized shortDesc - Localized News short description.
	 */
	public Map<Language,String> getAllShortDesc(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHORTDESC,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.shortDesc</code> attribute. 
	 * @return the localized shortDesc - Localized News short description.
	 */
	public Map<Language,String> getAllShortDesc()
	{
		return getAllShortDesc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.shortDesc</code> attribute. 
	 * @param value the shortDesc - Localized News short description.
	 */
	public void setShortDesc(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneNews.setShortDesc requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHORTDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.shortDesc</code> attribute. 
	 * @param value the shortDesc - Localized News short description.
	 */
	public void setShortDesc(final String value)
	{
		setShortDesc( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.shortDesc</code> attribute. 
	 * @param value the shortDesc - Localized News short description.
	 */
	public void setAllShortDesc(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHORTDESC,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.shortDesc</code> attribute. 
	 * @param value the shortDesc - Localized News short description.
	 */
	public void setAllShortDesc(final Map<Language,String> value)
	{
		setAllShortDesc( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.targetWindow</code> attribute.
	 * @return the targetWindow
	 */
	public EnumerationValue getTargetWindow(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, TARGETWINDOW);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.targetWindow</code> attribute.
	 * @return the targetWindow
	 */
	public EnumerationValue getTargetWindow()
	{
		return getTargetWindow( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.targetWindow</code> attribute. 
	 * @param value the targetWindow
	 */
	public void setTargetWindow(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, TARGETWINDOW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.targetWindow</code> attribute. 
	 * @param value the targetWindow
	 */
	public void setTargetWindow(final EnumerationValue value)
	{
		setTargetWindow( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.title</code> attribute.
	 * @return the title - Localized News title.
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneNews.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.title</code> attribute.
	 * @return the title - Localized News title.
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.title</code> attribute. 
	 * @return the localized title - Localized News title.
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneNews.title</code> attribute. 
	 * @return the localized title - Localized News title.
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.title</code> attribute. 
	 * @param value the title - Localized News title.
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneNews.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.title</code> attribute. 
	 * @param value the title - Localized News title.
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.title</code> attribute. 
	 * @param value the title - Localized News title.
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneNews.title</code> attribute. 
	 * @param value the title - Localized News title.
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
}
