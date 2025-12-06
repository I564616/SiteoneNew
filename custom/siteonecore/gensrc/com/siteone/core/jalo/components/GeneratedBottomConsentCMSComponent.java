/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo.components;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.CMSParagraphComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.components.BottomConsentCMSComponent BottomConsentCMSComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedBottomConsentCMSComponent extends CMSParagraphComponent
{
	/** Qualifier of the <code>BottomConsentCMSComponent.url</code> attribute **/
	public static final String URL = "url";
	/** Qualifier of the <code>BottomConsentCMSComponent.text</code> attribute **/
	public static final String TEXT = "text";
	/** Qualifier of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute **/
	public static final String BUTTONLABEL = "buttonLabel";
	/** Qualifier of the <code>BottomConsentCMSComponent.buttonURL</code> attribute **/
	public static final String BUTTONURL = "buttonURL";
	/** Qualifier of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute **/
	public static final String BUTTONLABELPOLICY = "buttonLabelPolicy";
	/** Qualifier of the <code>BottomConsentCMSComponent.buttonURLPolicy</code> attribute **/
	public static final String BUTTONURLPOLICY = "buttonURLPolicy";
	/** Qualifier of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute **/
	public static final String MOBILECONTENT = "mobilecontent";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CMSParagraphComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(URL, AttributeMode.INITIAL);
		tmp.put(TEXT, AttributeMode.INITIAL);
		tmp.put(BUTTONLABEL, AttributeMode.INITIAL);
		tmp.put(BUTTONURL, AttributeMode.INITIAL);
		tmp.put(BUTTONLABELPOLICY, AttributeMode.INITIAL);
		tmp.put(BUTTONURLPOLICY, AttributeMode.INITIAL);
		tmp.put(MOBILECONTENT, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.getButtonLabel requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute.
	 * @return the buttonLabel
	 */
	public String getButtonLabel()
	{
		return getButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute. 
	 * @return the localized buttonLabel
	 */
	public Map<Language,String> getAllButtonLabel()
	{
		return getAllButtonLabel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.setButtonLabel requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setButtonLabel(final String value)
	{
		setButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabel</code> attribute. 
	 * @param value the buttonLabel
	 */
	public void setAllButtonLabel(final Map<Language,String> value)
	{
		setAllButtonLabel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute.
	 * @return the buttonLabelPolicy
	 */
	public String getButtonLabelPolicy(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.getButtonLabelPolicy requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BUTTONLABELPOLICY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute.
	 * @return the buttonLabelPolicy
	 */
	public String getButtonLabelPolicy()
	{
		return getButtonLabelPolicy( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute. 
	 * @return the localized buttonLabelPolicy
	 */
	public Map<Language,String> getAllButtonLabelPolicy(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BUTTONLABELPOLICY,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute. 
	 * @return the localized buttonLabelPolicy
	 */
	public Map<Language,String> getAllButtonLabelPolicy()
	{
		return getAllButtonLabelPolicy( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute. 
	 * @param value the buttonLabelPolicy
	 */
	public void setButtonLabelPolicy(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.setButtonLabelPolicy requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BUTTONLABELPOLICY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute. 
	 * @param value the buttonLabelPolicy
	 */
	public void setButtonLabelPolicy(final String value)
	{
		setButtonLabelPolicy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute. 
	 * @param value the buttonLabelPolicy
	 */
	public void setAllButtonLabelPolicy(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BUTTONLABELPOLICY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonLabelPolicy</code> attribute. 
	 * @param value the buttonLabelPolicy
	 */
	public void setAllButtonLabelPolicy(final Map<Language,String> value)
	{
		setAllButtonLabelPolicy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonURL</code> attribute.
	 * @return the buttonURL
	 */
	public String getButtonURL()
	{
		return getButtonURL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonURL</code> attribute. 
	 * @param value the buttonURL
	 */
	public void setButtonURL(final String value)
	{
		setButtonURL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonURLPolicy</code> attribute.
	 * @return the buttonURLPolicy
	 */
	public String getButtonURLPolicy(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BUTTONURLPOLICY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.buttonURLPolicy</code> attribute.
	 * @return the buttonURLPolicy
	 */
	public String getButtonURLPolicy()
	{
		return getButtonURLPolicy( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonURLPolicy</code> attribute. 
	 * @param value the buttonURLPolicy
	 */
	public void setButtonURLPolicy(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BUTTONURLPOLICY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.buttonURLPolicy</code> attribute. 
	 * @param value the buttonURLPolicy
	 */
	public void setButtonURLPolicy(final String value)
	{
		setButtonURLPolicy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute.
	 * @return the mobilecontent - Indicates the global message for mobile view.
	 */
	public String getMobilecontent(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.getMobilecontent requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, MOBILECONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute.
	 * @return the mobilecontent - Indicates the global message for mobile view.
	 */
	public String getMobilecontent()
	{
		return getMobilecontent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute. 
	 * @return the localized mobilecontent - Indicates the global message for mobile view.
	 */
	public Map<Language,String> getAllMobilecontent(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,MOBILECONTENT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute. 
	 * @return the localized mobilecontent - Indicates the global message for mobile view.
	 */
	public Map<Language,String> getAllMobilecontent()
	{
		return getAllMobilecontent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.setMobilecontent requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MOBILECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setMobilecontent(final String value)
	{
		setMobilecontent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setAllMobilecontent(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,MOBILECONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.mobilecontent</code> attribute. 
	 * @param value the mobilecontent - Indicates the global message for mobile view.
	 */
	public void setAllMobilecontent(final Map<Language,String> value)
	{
		setAllMobilecontent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.text</code> attribute.
	 * @return the text
	 */
	public String getText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.getText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.text</code> attribute.
	 * @return the text
	 */
	public String getText()
	{
		return getText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.text</code> attribute. 
	 * @return the localized text
	 */
	public Map<Language,String> getAllText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.text</code> attribute. 
	 * @return the localized text
	 */
	public Map<Language,String> getAllText()
	{
		return getAllText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.text</code> attribute. 
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
			throw new JaloInvalidParameterException("GeneratedBottomConsentCMSComponent.setText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setText(final String value)
	{
		setText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setAllText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.text</code> attribute. 
	 * @param value the text
	 */
	public void setAllText(final Map<Language,String> value)
	{
		setAllText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.url</code> attribute.
	 * @return the url
	 */
	public String getUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>BottomConsentCMSComponent.url</code> attribute.
	 * @return the url
	 */
	public String getUrl()
	{
		return getUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>BottomConsentCMSComponent.url</code> attribute. 
	 * @param value the url
	 */
	public void setUrl(final String value)
	{
		setUrl( getSession().getSessionContext(), value );
	}
	
}
