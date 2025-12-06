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
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.Inspiration Inspiration}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedInspiration extends GenericItem
{
	/** Qualifier of the <code>Inspiration.inspirationCode</code> attribute **/
	public static final String INSPIRATIONCODE = "inspirationCode";
	/** Qualifier of the <code>Inspiration.inspirationMedia</code> attribute **/
	public static final String INSPIRATIONMEDIA = "inspirationMedia";
	/** Qualifier of the <code>Inspiration.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>Inspiration.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>Inspiration.designedBy</code> attribute **/
	public static final String DESIGNEDBY = "designedBy";
	/** Qualifier of the <code>Inspiration.snippetOfTheStory</code> attribute **/
	public static final String SNIPPETOFTHESTORY = "snippetOfTheStory";
	/** Qualifier of the <code>Inspiration.snippetOfTheStoryNew</code> attribute **/
	public static final String SNIPPETOFTHESTORYNEW = "snippetOfTheStoryNew";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(INSPIRATIONCODE, AttributeMode.INITIAL);
		tmp.put(INSPIRATIONMEDIA, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(DESIGNEDBY, AttributeMode.INITIAL);
		tmp.put(SNIPPETOFTHESTORY, AttributeMode.INITIAL);
		tmp.put(SNIPPETOFTHESTORYNEW, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.designedBy</code> attribute.
	 * @return the designedBy
	 */
	public String getDesignedBy(final SessionContext ctx)
	{
		return (String)getProperty( ctx, DESIGNEDBY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.designedBy</code> attribute.
	 * @return the designedBy
	 */
	public String getDesignedBy()
	{
		return getDesignedBy( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.designedBy</code> attribute. 
	 * @param value the designedBy
	 */
	public void setDesignedBy(final SessionContext ctx, final String value)
	{
		setProperty(ctx, DESIGNEDBY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.designedBy</code> attribute. 
	 * @param value the designedBy
	 */
	public void setDesignedBy(final String value)
	{
		setDesignedBy( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.inspirationCode</code> attribute.
	 * @return the inspirationCode
	 */
	public String getInspirationCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, INSPIRATIONCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.inspirationCode</code> attribute.
	 * @return the inspirationCode
	 */
	public String getInspirationCode()
	{
		return getInspirationCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.inspirationCode</code> attribute. 
	 * @param value the inspirationCode
	 */
	public void setInspirationCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, INSPIRATIONCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.inspirationCode</code> attribute. 
	 * @param value the inspirationCode
	 */
	public void setInspirationCode(final String value)
	{
		setInspirationCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.inspirationMedia</code> attribute.
	 * @return the inspirationMedia
	 */
	public Media getInspirationMedia(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, INSPIRATIONMEDIA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.inspirationMedia</code> attribute.
	 * @return the inspirationMedia
	 */
	public Media getInspirationMedia()
	{
		return getInspirationMedia( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.inspirationMedia</code> attribute. 
	 * @param value the inspirationMedia
	 */
	public void setInspirationMedia(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, INSPIRATIONMEDIA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.inspirationMedia</code> attribute. 
	 * @param value the inspirationMedia
	 */
	public void setInspirationMedia(final Media value)
	{
		setInspirationMedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.location</code> attribute.
	 * @return the location
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.location</code> attribute.
	 * @return the location
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.snippetOfTheStory</code> attribute.
	 * @return the snippetOfTheStory
	 */
	public String getSnippetOfTheStory(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SNIPPETOFTHESTORY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.snippetOfTheStory</code> attribute.
	 * @return the snippetOfTheStory
	 */
	public String getSnippetOfTheStory()
	{
		return getSnippetOfTheStory( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.snippetOfTheStory</code> attribute. 
	 * @param value the snippetOfTheStory
	 */
	public void setSnippetOfTheStory(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SNIPPETOFTHESTORY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.snippetOfTheStory</code> attribute. 
	 * @param value the snippetOfTheStory
	 */
	public void setSnippetOfTheStory(final String value)
	{
		setSnippetOfTheStory( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.snippetOfTheStoryNew</code> attribute.
	 * @return the snippetOfTheStoryNew - Inspiration long snippet theory
	 */
	public String getSnippetOfTheStoryNew(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SNIPPETOFTHESTORYNEW);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.snippetOfTheStoryNew</code> attribute.
	 * @return the snippetOfTheStoryNew - Inspiration long snippet theory
	 */
	public String getSnippetOfTheStoryNew()
	{
		return getSnippetOfTheStoryNew( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.snippetOfTheStoryNew</code> attribute. 
	 * @param value the snippetOfTheStoryNew - Inspiration long snippet theory
	 */
	public void setSnippetOfTheStoryNew(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SNIPPETOFTHESTORYNEW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.snippetOfTheStoryNew</code> attribute. 
	 * @param value the snippetOfTheStoryNew - Inspiration long snippet theory
	 */
	public void setSnippetOfTheStoryNew(final String value)
	{
		setSnippetOfTheStoryNew( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.title</code> attribute.
	 * @return the title
	 */
	public String getTitle(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Inspiration.title</code> attribute.
	 * @return the title
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Inspiration.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
}
