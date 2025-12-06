/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type SiteOneTechnicalDrawing.
 */
@SLDSafe
@SuppressWarnings({"unused","cast"})
public class SiteOneTechnicalDrawing extends GenericItem
{
	/** Qualifier of the <code>SiteOneTechnicalDrawing.id</code> attribute **/
	public static final String ID = "id";
	/** Qualifier of the <code>SiteOneTechnicalDrawing.mimetype</code> attribute **/
	public static final String MIMETYPE = "mimetype";
	/** Qualifier of the <code>SiteOneTechnicalDrawing.fullpath</code> attribute **/
	public static final String FULLPATH = "fullpath";
	/** Qualifier of the <code>SiteOneTechnicalDrawing.priority</code> attribute **/
	public static final String PRIORITY = "priority";
	/** Qualifier of the <code>SiteOneTechnicalDrawing.code</code> attribute **/
	public static final String CODE = "code";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ID, AttributeMode.INITIAL);
		tmp.put(MIMETYPE, AttributeMode.INITIAL);
		tmp.put(FULLPATH, AttributeMode.INITIAL);
		tmp.put(PRIORITY, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "code".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "code".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.fullpath</code> attribute.
	 * @return the fullpath
	 */
	public String getFullpath(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "fullpath".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.fullpath</code> attribute.
	 * @return the fullpath
	 */
	public String getFullpath()
	{
		return getFullpath( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.fullpath</code> attribute. 
	 * @param value the fullpath
	 */
	public void setFullpath(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "fullpath".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.fullpath</code> attribute. 
	 * @param value the fullpath
	 */
	public void setFullpath(final String value)
	{
		setFullpath( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.id</code> attribute.
	 * @return the id
	 */
	public String getId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "id".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.id</code> attribute.
	 * @return the id
	 */
	public String getId()
	{
		return getId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "id".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final String value)
	{
		setId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.mimetype</code> attribute.
	 * @return the mimetype
	 */
	public String getMimetype(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "mimetype".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.mimetype</code> attribute.
	 * @return the mimetype
	 */
	public String getMimetype()
	{
		return getMimetype( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.mimetype</code> attribute. 
	 * @param value the mimetype
	 */
	public void setMimetype(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "mimetype".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.mimetype</code> attribute. 
	 * @param value the mimetype
	 */
	public void setMimetype(final String value)
	{
		setMimetype( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.priority</code> attribute.
	 * @return the priority
	 */
	public String getPriority(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "priority".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneTechnicalDrawing.priority</code> attribute.
	 * @return the priority
	 */
	public String getPriority()
	{
		return getPriority( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.priority</code> attribute. 
	 * @param value the priority
	 */
	public void setPriority(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "priority".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneTechnicalDrawing.priority</code> attribute. 
	 * @param value the priority
	 */
	public void setPriority(final String value)
	{
		setPriority( getSession().getSessionContext(), value );
	}
	
}
