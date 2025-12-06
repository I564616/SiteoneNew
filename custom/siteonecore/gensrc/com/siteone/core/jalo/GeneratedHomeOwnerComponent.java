/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import de.hybris.platform.cms2.jalo.contents.components.CMSParagraphComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.HomeOwnerComponent HomeOwnerComponent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedHomeOwnerComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>HomeOwnerComponent.content</code> attribute **/
	public static final String CONTENT = "content";
	/** Qualifier of the <code>HomeOwnerComponent.bottomPara</code> attribute **/
	public static final String BOTTOMPARA = "bottomPara";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(SimpleCMSComponent.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CONTENT, AttributeMode.INITIAL);
		tmp.put(BOTTOMPARA, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerComponent.bottomPara</code> attribute.
	 * @return the bottomPara - Bottom paragraph below the Submit button.
	 */
	public String getBottomPara(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomeOwnerComponent.getBottomPara requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, BOTTOMPARA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerComponent.bottomPara</code> attribute.
	 * @return the bottomPara - Bottom paragraph below the Submit button.
	 */
	public String getBottomPara()
	{
		return getBottomPara( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerComponent.bottomPara</code> attribute. 
	 * @return the localized bottomPara - Bottom paragraph below the Submit button.
	 */
	public Map<Language,String> getAllBottomPara(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,BOTTOMPARA,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerComponent.bottomPara</code> attribute. 
	 * @return the localized bottomPara - Bottom paragraph below the Submit button.
	 */
	public Map<Language,String> getAllBottomPara()
	{
		return getAllBottomPara( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerComponent.bottomPara</code> attribute. 
	 * @param value the bottomPara - Bottom paragraph below the Submit button.
	 */
	public void setBottomPara(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedHomeOwnerComponent.setBottomPara requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BOTTOMPARA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerComponent.bottomPara</code> attribute. 
	 * @param value the bottomPara - Bottom paragraph below the Submit button.
	 */
	public void setBottomPara(final String value)
	{
		setBottomPara( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerComponent.bottomPara</code> attribute. 
	 * @param value the bottomPara - Bottom paragraph below the Submit button.
	 */
	public void setAllBottomPara(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,BOTTOMPARA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerComponent.bottomPara</code> attribute. 
	 * @param value the bottomPara - Bottom paragraph below the Submit button.
	 */
	public void setAllBottomPara(final Map<Language,String> value)
	{
		setAllBottomPara( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerComponent.content</code> attribute.
	 * @return the content
	 */
	public CMSParagraphComponent getContent(final SessionContext ctx)
	{
		return (CMSParagraphComponent)getProperty( ctx, CONTENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>HomeOwnerComponent.content</code> attribute.
	 * @return the content
	 */
	public CMSParagraphComponent getContent()
	{
		return getContent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final SessionContext ctx, final CMSParagraphComponent value)
	{
		setProperty(ctx, CONTENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>HomeOwnerComponent.content</code> attribute. 
	 * @param value the content
	 */
	public void setContent(final CMSParagraphComponent value)
	{
		setContent( getSession().getSessionContext(), value );
	}
	
}
