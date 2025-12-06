/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

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
 * Generated class for type {@link com.siteone.core.jalo.SiteonefaqComponent SiteoneFAQ}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteonefaqComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>SiteoneFAQ.question</code> attribute **/
	public static final String QUESTION = "question";
	/** Qualifier of the <code>SiteoneFAQ.answer</code> attribute **/
	public static final String ANSWER = "answer";
	/** Qualifier of the <code>SiteoneFAQ.image</code> attribute **/
	public static final String IMAGE = "image";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(QUESTION, AttributeMode.INITIAL);
		tmp.put(ANSWER, AttributeMode.INITIAL);
		tmp.put(IMAGE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.answer</code> attribute.
	 * @return the answer - Text of answer
	 */
	public String getAnswer(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteonefaqComponent.getAnswer requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ANSWER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.answer</code> attribute.
	 * @return the answer - Text of answer
	 */
	public String getAnswer()
	{
		return getAnswer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.answer</code> attribute. 
	 * @return the localized answer - Text of answer
	 */
	public Map<Language,String> getAllAnswer(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ANSWER,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.answer</code> attribute. 
	 * @return the localized answer - Text of answer
	 */
	public Map<Language,String> getAllAnswer()
	{
		return getAllAnswer( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.answer</code> attribute. 
	 * @param value the answer - Text of answer
	 */
	public void setAnswer(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteonefaqComponent.setAnswer requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ANSWER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.answer</code> attribute. 
	 * @param value the answer - Text of answer
	 */
	public void setAnswer(final String value)
	{
		setAnswer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.answer</code> attribute. 
	 * @param value the answer - Text of answer
	 */
	public void setAllAnswer(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ANSWER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.answer</code> attribute. 
	 * @param value the answer - Text of answer
	 */
	public void setAllAnswer(final Map<Language,String> value)
	{
		setAllAnswer( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.image</code> attribute.
	 * @return the image - Media
	 */
	public Media getImage(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.image</code> attribute.
	 * @return the image - Media
	 */
	public Media getImage()
	{
		return getImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.image</code> attribute. 
	 * @param value the image - Media
	 */
	public void setImage(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.image</code> attribute. 
	 * @param value the image - Media
	 */
	public void setImage(final Media value)
	{
		setImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.question</code> attribute.
	 * @return the question - Text of question
	 */
	public String getQuestion(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteonefaqComponent.getQuestion requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, QUESTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.question</code> attribute.
	 * @return the question - Text of question
	 */
	public String getQuestion()
	{
		return getQuestion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.question</code> attribute. 
	 * @return the localized question - Text of question
	 */
	public Map<Language,String> getAllQuestion(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,QUESTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteoneFAQ.question</code> attribute. 
	 * @return the localized question - Text of question
	 */
	public Map<Language,String> getAllQuestion()
	{
		return getAllQuestion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.question</code> attribute. 
	 * @param value the question - Text of question
	 */
	public void setQuestion(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteonefaqComponent.setQuestion requires a session language", 0 );
		}
		setLocalizedProperty(ctx, QUESTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.question</code> attribute. 
	 * @param value the question - Text of question
	 */
	public void setQuestion(final String value)
	{
		setQuestion( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.question</code> attribute. 
	 * @param value the question - Text of question
	 */
	public void setAllQuestion(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,QUESTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteoneFAQ.question</code> attribute. 
	 * @param value the question - Text of question
	 */
	public void setAllQuestion(final Map<Language,String> value)
	{
		setAllQuestion( getSession().getSessionContext(), value );
	}
	
}
