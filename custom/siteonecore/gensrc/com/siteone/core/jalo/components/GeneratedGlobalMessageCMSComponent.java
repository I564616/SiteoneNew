/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo.components;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
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
 * Generated class for type {@link com.siteone.core.jalo.components.GlobalMessageCMSComponent GlobalMessageCMSComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedGlobalMessageCMSComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>GlobalMessageCMSComponent.video</code> attribute **/
	public static final String VIDEO = "video";
	/** Qualifier of the <code>GlobalMessageCMSComponent.content</code> attribute **/
	public static final String CONTENT = "content";
	/** Qualifier of the <code>GlobalMessageCMSComponent.url</code> attribute **/
	public static final String URL = "url";
	/** Qualifier of the <code>GlobalMessageCMSComponent.text</code> attribute **/
	public static final String TEXT = "text";
	/** Qualifier of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute **/
	public static final String MOBILECONTENT = "mobilecontent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(VIDEO, AttributeMode.INITIAL);
		tmp.put(CONTENT, AttributeMode.INITIAL);
		tmp.put(URL, AttributeMode.INITIAL);
		tmp.put(TEXT, AttributeMode.INITIAL);
		tmp.put(MOBILECONTENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.content</code> attribute.
	 * @return the content - Indicates the global message.
	 */
	public String getContent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.getContent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.content</code> attribute.
	 * @return the content - Indicates the global message.
	 */
	public String getContent()
	{
		return getContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.content</code> attribute. 
	 * @return the localized content - Indicates the global message.
	 */
	public Map<Language,String> getAllContent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.content</code> attribute. 
	 * @return the localized content - Indicates the global message.
	 */
	public Map<Language,String> getAllContent()
	{
		return getAllContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.content</code> attribute. 
	 * @param value the content - Indicates the global message.
	 */
	public void setContent(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.setContent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.content</code> attribute. 
	 * @param value the content - Indicates the global message.
	 */
	public void setContent(final String value)
	{
		setContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.content</code> attribute. 
	 * @param value the content - Indicates the global message.
	 */
	public void setAllContent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.content</code> attribute. 
	 * @param value the content - Indicates the global message.
	 */
	public void setAllContent(final Map<Language,String> value)
	{
		setAllContent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute.
	 * @return the mobilecontent - Indicates the global message for mobile view.
	 */
	public String getMobilecontent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.getMobilecontent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, MOBILECONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute.
	 * @return the mobilecontent - Indicates the global message for mobile view.
	 */
	public String getMobilecontent()
	{
		return getMobilecontent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute. 
	 * @return the localized mobilecontent - Indicates the global message for mobile view.
	 */
	public Map<Language,String> getAllMobilecontent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,MOBILECONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute. 
	 * @return the localized mobilecontent - Indicates the global message for mobile view.
	 */
	public Map<Language,String> getAllMobilecontent()
	{
		return getAllMobilecontent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setMobilecontent(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.setMobilecontent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MOBILECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setMobilecontent(final String value)
	{
		setMobilecontent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setAllMobilecontent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,MOBILECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setAllMobilecontent(final Map<Language,String> value)
	{
		setAllMobilecontent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.text</code> attribute.
	 * @return the text
	 */
	public String getText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.getText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.text</code> attribute.
	 * @return the text
	 */
	public String getText()
	{
		return getText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.text</code> attribute. 
	 * @return the localized text
	 */
	public Map<Language,String> getAllText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.text</code> attribute. 
	 * @return the localized text
	 */
	public Map<Language,String> getAllText()
	{
		return getAllText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setText(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.setText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setText(final String value)
	{
		setText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setAllText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setAllText(final Map<Language,String> value)
	{
		setAllText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.url</code> attribute.
	 * @return the url
	 */
	public String getUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.url</code> attribute.
	 * @return the url
	 */
	public String getUrl()
	{
		return getUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final String value)
	{
		setUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.video</code> attribute.
	 * @return the video - Indicates the message with video.
	 */
	public Media getVideo(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.getVideo requires a session language", 0 );
		}
		return (Media)getLocalizedProperty( ctx, VIDEO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.video</code> attribute.
	 * @return the video - Indicates the message with video.
	 */
	public Media getVideo()
	{
		return getVideo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.video</code> attribute. 
	 * @return the localized video - Indicates the message with video.
	 */
	public Map<Language,Media> getAllVideo(final SessionContext ctx)
	{
		return (Map<Language,Media>)getAllLocalizedProperties(ctx,VIDEO,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>GlobalMessageCMSComponent.video</code> attribute. 
	 * @return the localized video - Indicates the message with video.
	 */
	public Map<Language,Media> getAllVideo()
	{
		return getAllVideo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.video</code> attribute. 
	 * @param value the video - Indicates the message with video.
	 */
	public void setVideo(final SessionContext ctx, final Media value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedGlobalMessageCMSComponent.setVideo requires a session language", 0 );
		}
		setLocalizedProperty(ctx, VIDEO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.video</code> attribute. 
	 * @param value the video - Indicates the message with video.
	 */
	public void setVideo(final Media value)
	{
		setVideo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.video</code> attribute. 
	 * @param value the video - Indicates the message with video.
	 */
	public void setAllVideo(final SessionContext ctx, final Map<Language,Media> value)
	{
		setAllLocalizedProperties(ctx,VIDEO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>GlobalMessageCMSComponent.video</code> attribute. 
	 * @param value the video - Indicates the message with video.
	 */
	public void setAllVideo(final Map<Language,Media> value)
	{
		setAllVideo( getSession().getSessionContext(), value );
	}
	
}
