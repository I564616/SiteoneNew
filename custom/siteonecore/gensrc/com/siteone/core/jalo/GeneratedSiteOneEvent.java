/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.SiteOneEventType;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.c2l.Region;
import de.hybris.platform.jalo.media.Media;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.SiteOneEvent SiteOneEvent}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneEvent extends GenericItem
{
	/** Qualifier of the <code>SiteOneEvent.division</code> attribute **/
	public static final String DIVISION = "division";
	/** Qualifier of the <code>SiteOneEvent.code</code> attribute **/
	public static final String CODE = "code";
	/** Qualifier of the <code>SiteOneEvent.name</code> attribute **/
	public static final String NAME = "name";
	/** Qualifier of the <code>SiteOneEvent.shortDescription</code> attribute **/
	public static final String SHORTDESCRIPTION = "shortDescription";
	/** Qualifier of the <code>SiteOneEvent.longDescription</code> attribute **/
	public static final String LONGDESCRIPTION = "longDescription";
	/** Qualifier of the <code>SiteOneEvent.eventStartDate</code> attribute **/
	public static final String EVENTSTARTDATE = "eventStartDate";
	/** Qualifier of the <code>SiteOneEvent.eventEndDate</code> attribute **/
	public static final String EVENTENDDATE = "eventEndDate";
	/** Qualifier of the <code>SiteOneEvent.time</code> attribute **/
	public static final String TIME = "time";
	/** Qualifier of the <code>SiteOneEvent.location</code> attribute **/
	public static final String LOCATION = "location";
	/** Qualifier of the <code>SiteOneEvent.venue</code> attribute **/
	public static final String VENUE = "venue";
	/** Qualifier of the <code>SiteOneEvent.document</code> attribute **/
	public static final String DOCUMENT = "document";
	/** Qualifier of the <code>SiteOneEvent.image</code> attribute **/
	public static final String IMAGE = "image";
	/** Qualifier of the <code>SiteOneEvent.type</code> attribute **/
	public static final String TYPE = "type";
	/** Qualifier of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute **/
	public static final String ISPARTNERPROGRAMEVENT = "isPartnerProgramEvent";
	/** Qualifier of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute **/
	public static final String EVENTREGISTRATIONURL = "eventRegistrationUrl";
	/** Qualifier of the <code>SiteOneEvent.title</code> attribute **/
	public static final String TITLE = "title";
	/** Qualifier of the <code>SiteOneEvent.registrationMessage</code> attribute **/
	public static final String REGISTRATIONMESSAGE = "registrationMessage";
	/** Qualifier of the <code>SiteOneEvent.registrationStartDate</code> attribute **/
	public static final String REGISTRATIONSTARTDATE = "registrationStartDate";
	/** Qualifier of the <code>SiteOneEvent.registrationEndDate</code> attribute **/
	public static final String REGISTRATIONENDDATE = "registrationEndDate";
	/** Qualifier of the <code>SiteOneEvent.learnMoreUrl</code> attribute **/
	public static final String LEARNMOREURL = "learnMoreUrl";
	/** Qualifier of the <code>SiteOneEvent.state</code> attribute **/
	public static final String STATE = "state";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(DIVISION, AttributeMode.INITIAL);
		tmp.put(CODE, AttributeMode.INITIAL);
		tmp.put(NAME, AttributeMode.INITIAL);
		tmp.put(SHORTDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(LONGDESCRIPTION, AttributeMode.INITIAL);
		tmp.put(EVENTSTARTDATE, AttributeMode.INITIAL);
		tmp.put(EVENTENDDATE, AttributeMode.INITIAL);
		tmp.put(TIME, AttributeMode.INITIAL);
		tmp.put(LOCATION, AttributeMode.INITIAL);
		tmp.put(VENUE, AttributeMode.INITIAL);
		tmp.put(DOCUMENT, AttributeMode.INITIAL);
		tmp.put(IMAGE, AttributeMode.INITIAL);
		tmp.put(TYPE, AttributeMode.INITIAL);
		tmp.put(ISPARTNERPROGRAMEVENT, AttributeMode.INITIAL);
		tmp.put(EVENTREGISTRATIONURL, AttributeMode.INITIAL);
		tmp.put(TITLE, AttributeMode.INITIAL);
		tmp.put(REGISTRATIONMESSAGE, AttributeMode.INITIAL);
		tmp.put(REGISTRATIONSTARTDATE, AttributeMode.INITIAL);
		tmp.put(REGISTRATIONENDDATE, AttributeMode.INITIAL);
		tmp.put(LEARNMOREURL, AttributeMode.INITIAL);
		tmp.put(STATE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.code</code> attribute.
	 * @return the code - Indicates the event code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.code</code> attribute.
	 * @return the code - Indicates the event code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.code</code> attribute. 
	 * @param value the code - Indicates the event code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.code</code> attribute. 
	 * @param value the code - Indicates the event code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.division</code> attribute.
	 * @return the division - Indicates the event division
	 */
	public String getDivision(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getDivision requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DIVISION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.division</code> attribute.
	 * @return the division - Indicates the event division
	 */
	public String getDivision()
	{
		return getDivision( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.division</code> attribute. 
	 * @return the localized division - Indicates the event division
	 */
	public Map<Language,String> getAllDivision(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DIVISION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.division</code> attribute. 
	 * @return the localized division - Indicates the event division
	 */
	public Map<Language,String> getAllDivision()
	{
		return getAllDivision( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.division</code> attribute. 
	 * @param value the division - Indicates the event division
	 */
	public void setDivision(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setDivision requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DIVISION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.division</code> attribute. 
	 * @param value the division - Indicates the event division
	 */
	public void setDivision(final String value)
	{
		setDivision( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.division</code> attribute. 
	 * @param value the division - Indicates the event division
	 */
	public void setAllDivision(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DIVISION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.division</code> attribute. 
	 * @param value the division - Indicates the event division
	 */
	public void setAllDivision(final Map<Language,String> value)
	{
		setAllDivision( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.document</code> attribute.
	 * @return the document - Indicates the event details document
	 */
	public Media getDocument(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, DOCUMENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.document</code> attribute.
	 * @return the document - Indicates the event details document
	 */
	public Media getDocument()
	{
		return getDocument( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.document</code> attribute. 
	 * @param value the document - Indicates the event details document
	 */
	public void setDocument(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, DOCUMENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.document</code> attribute. 
	 * @param value the document - Indicates the event details document
	 */
	public void setDocument(final Media value)
	{
		setDocument( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventEndDate</code> attribute.
	 * @return the eventEndDate - Indicates the event date
	 */
	public Date getEventEndDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, EVENTENDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventEndDate</code> attribute.
	 * @return the eventEndDate - Indicates the event date
	 */
	public Date getEventEndDate()
	{
		return getEventEndDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventEndDate</code> attribute. 
	 * @param value the eventEndDate - Indicates the event date
	 */
	public void setEventEndDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, EVENTENDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventEndDate</code> attribute. 
	 * @param value the eventEndDate - Indicates the event date
	 */
	public void setEventEndDate(final Date value)
	{
		setEventEndDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute.
	 * @return the eventRegistrationUrl - Indicates the event url
	 */
	public String getEventRegistrationUrl(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getEventRegistrationUrl requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, EVENTREGISTRATIONURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute.
	 * @return the eventRegistrationUrl - Indicates the event url
	 */
	public String getEventRegistrationUrl()
	{
		return getEventRegistrationUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute. 
	 * @return the localized eventRegistrationUrl - Indicates the event url
	 */
	public Map<Language,String> getAllEventRegistrationUrl(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,EVENTREGISTRATIONURL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute. 
	 * @return the localized eventRegistrationUrl - Indicates the event url
	 */
	public Map<Language,String> getAllEventRegistrationUrl()
	{
		return getAllEventRegistrationUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute. 
	 * @param value the eventRegistrationUrl - Indicates the event url
	 */
	public void setEventRegistrationUrl(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setEventRegistrationUrl requires a session language", 0 );
		}
		setLocalizedProperty(ctx, EVENTREGISTRATIONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute. 
	 * @param value the eventRegistrationUrl - Indicates the event url
	 */
	public void setEventRegistrationUrl(final String value)
	{
		setEventRegistrationUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute. 
	 * @param value the eventRegistrationUrl - Indicates the event url
	 */
	public void setAllEventRegistrationUrl(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,EVENTREGISTRATIONURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventRegistrationUrl</code> attribute. 
	 * @param value the eventRegistrationUrl - Indicates the event url
	 */
	public void setAllEventRegistrationUrl(final Map<Language,String> value)
	{
		setAllEventRegistrationUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventStartDate</code> attribute.
	 * @return the eventStartDate - Indicates the event date
	 */
	public Date getEventStartDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, EVENTSTARTDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.eventStartDate</code> attribute.
	 * @return the eventStartDate - Indicates the event date
	 */
	public Date getEventStartDate()
	{
		return getEventStartDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventStartDate</code> attribute. 
	 * @param value the eventStartDate - Indicates the event date
	 */
	public void setEventStartDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, EVENTSTARTDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.eventStartDate</code> attribute. 
	 * @param value the eventStartDate - Indicates the event date
	 */
	public void setEventStartDate(final Date value)
	{
		setEventStartDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.image</code> attribute.
	 * @return the image - Indicates the event image
	 */
	public Media getImage(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.image</code> attribute.
	 * @return the image - Indicates the event image
	 */
	public Media getImage()
	{
		return getImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.image</code> attribute. 
	 * @param value the image - Indicates the event image
	 */
	public void setImage(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.image</code> attribute. 
	 * @param value the image - Indicates the event image
	 */
	public void setImage(final Media value)
	{
		setImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute.
	 * @return the isPartnerProgramEvent
	 */
	public Boolean isIsPartnerProgramEvent(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ISPARTNERPROGRAMEVENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute.
	 * @return the isPartnerProgramEvent
	 */
	public Boolean isIsPartnerProgramEvent()
	{
		return isIsPartnerProgramEvent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute. 
	 * @return the isPartnerProgramEvent
	 */
	public boolean isIsPartnerProgramEventAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIsPartnerProgramEvent( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute. 
	 * @return the isPartnerProgramEvent
	 */
	public boolean isIsPartnerProgramEventAsPrimitive()
	{
		return isIsPartnerProgramEventAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute. 
	 * @param value the isPartnerProgramEvent
	 */
	public void setIsPartnerProgramEvent(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ISPARTNERPROGRAMEVENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute. 
	 * @param value the isPartnerProgramEvent
	 */
	public void setIsPartnerProgramEvent(final Boolean value)
	{
		setIsPartnerProgramEvent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute. 
	 * @param value the isPartnerProgramEvent
	 */
	public void setIsPartnerProgramEvent(final SessionContext ctx, final boolean value)
	{
		setIsPartnerProgramEvent( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.isPartnerProgramEvent</code> attribute. 
	 * @param value the isPartnerProgramEvent
	 */
	public void setIsPartnerProgramEvent(final boolean value)
	{
		setIsPartnerProgramEvent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.learnMoreUrl</code> attribute.
	 * @return the learnMoreUrl
	 */
	public String getLearnMoreUrl(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getLearnMoreUrl requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, LEARNMOREURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.learnMoreUrl</code> attribute.
	 * @return the learnMoreUrl
	 */
	public String getLearnMoreUrl()
	{
		return getLearnMoreUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.learnMoreUrl</code> attribute. 
	 * @return the localized learnMoreUrl
	 */
	public Map<Language,String> getAllLearnMoreUrl(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,LEARNMOREURL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.learnMoreUrl</code> attribute. 
	 * @return the localized learnMoreUrl
	 */
	public Map<Language,String> getAllLearnMoreUrl()
	{
		return getAllLearnMoreUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.learnMoreUrl</code> attribute. 
	 * @param value the learnMoreUrl
	 */
	public void setLearnMoreUrl(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setLearnMoreUrl requires a session language", 0 );
		}
		setLocalizedProperty(ctx, LEARNMOREURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.learnMoreUrl</code> attribute. 
	 * @param value the learnMoreUrl
	 */
	public void setLearnMoreUrl(final String value)
	{
		setLearnMoreUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.learnMoreUrl</code> attribute. 
	 * @param value the learnMoreUrl
	 */
	public void setAllLearnMoreUrl(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,LEARNMOREURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.learnMoreUrl</code> attribute. 
	 * @param value the learnMoreUrl
	 */
	public void setAllLearnMoreUrl(final Map<Language,String> value)
	{
		setAllLearnMoreUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.location</code> attribute.
	 * @return the location - Indicates the event location
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.location</code> attribute.
	 * @return the location - Indicates the event location
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.location</code> attribute. 
	 * @param value the location - Indicates the event location
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.location</code> attribute. 
	 * @param value the location - Indicates the event location
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.longDescription</code> attribute.
	 * @return the longDescription - Indicates the event description
	 */
	public String getLongDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getLongDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, LONGDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.longDescription</code> attribute.
	 * @return the longDescription - Indicates the event description
	 */
	public String getLongDescription()
	{
		return getLongDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.longDescription</code> attribute. 
	 * @return the localized longDescription - Indicates the event description
	 */
	public Map<Language,String> getAllLongDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,LONGDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.longDescription</code> attribute. 
	 * @return the localized longDescription - Indicates the event description
	 */
	public Map<Language,String> getAllLongDescription()
	{
		return getAllLongDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.longDescription</code> attribute. 
	 * @param value the longDescription - Indicates the event description
	 */
	public void setLongDescription(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setLongDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, LONGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.longDescription</code> attribute. 
	 * @param value the longDescription - Indicates the event description
	 */
	public void setLongDescription(final String value)
	{
		setLongDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.longDescription</code> attribute. 
	 * @param value the longDescription - Indicates the event description
	 */
	public void setAllLongDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,LONGDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.longDescription</code> attribute. 
	 * @param value the longDescription - Indicates the event description
	 */
	public void setAllLongDescription(final Map<Language,String> value)
	{
		setAllLongDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.name</code> attribute.
	 * @return the name - Indicates the event name
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.name</code> attribute.
	 * @return the name - Indicates the event name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.name</code> attribute. 
	 * @return the localized name - Indicates the event name
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.name</code> attribute. 
	 * @return the localized name - Indicates the event name
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.name</code> attribute. 
	 * @param value the name - Indicates the event name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.name</code> attribute. 
	 * @param value the name - Indicates the event name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.name</code> attribute. 
	 * @param value the name - Indicates the event name
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.name</code> attribute. 
	 * @param value the name - Indicates the event name
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationEndDate</code> attribute.
	 * @return the registrationEndDate - Indicates the event date
	 */
	public Date getRegistrationEndDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, REGISTRATIONENDDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationEndDate</code> attribute.
	 * @return the registrationEndDate - Indicates the event date
	 */
	public Date getRegistrationEndDate()
	{
		return getRegistrationEndDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationEndDate</code> attribute. 
	 * @param value the registrationEndDate - Indicates the event date
	 */
	public void setRegistrationEndDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, REGISTRATIONENDDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationEndDate</code> attribute. 
	 * @param value the registrationEndDate - Indicates the event date
	 */
	public void setRegistrationEndDate(final Date value)
	{
		setRegistrationEndDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationMessage</code> attribute.
	 * @return the registrationMessage
	 */
	public String getRegistrationMessage(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getRegistrationMessage requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, REGISTRATIONMESSAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationMessage</code> attribute.
	 * @return the registrationMessage
	 */
	public String getRegistrationMessage()
	{
		return getRegistrationMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationMessage</code> attribute. 
	 * @return the localized registrationMessage
	 */
	public Map<Language,String> getAllRegistrationMessage(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,REGISTRATIONMESSAGE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationMessage</code> attribute. 
	 * @return the localized registrationMessage
	 */
	public Map<Language,String> getAllRegistrationMessage()
	{
		return getAllRegistrationMessage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationMessage</code> attribute. 
	 * @param value the registrationMessage
	 */
	public void setRegistrationMessage(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setRegistrationMessage requires a session language", 0 );
		}
		setLocalizedProperty(ctx, REGISTRATIONMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationMessage</code> attribute. 
	 * @param value the registrationMessage
	 */
	public void setRegistrationMessage(final String value)
	{
		setRegistrationMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationMessage</code> attribute. 
	 * @param value the registrationMessage
	 */
	public void setAllRegistrationMessage(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,REGISTRATIONMESSAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationMessage</code> attribute. 
	 * @param value the registrationMessage
	 */
	public void setAllRegistrationMessage(final Map<Language,String> value)
	{
		setAllRegistrationMessage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationStartDate</code> attribute.
	 * @return the registrationStartDate - Indicates the event date
	 */
	public Date getRegistrationStartDate(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, REGISTRATIONSTARTDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.registrationStartDate</code> attribute.
	 * @return the registrationStartDate - Indicates the event date
	 */
	public Date getRegistrationStartDate()
	{
		return getRegistrationStartDate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationStartDate</code> attribute. 
	 * @param value the registrationStartDate - Indicates the event date
	 */
	public void setRegistrationStartDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, REGISTRATIONSTARTDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.registrationStartDate</code> attribute. 
	 * @param value the registrationStartDate - Indicates the event date
	 */
	public void setRegistrationStartDate(final Date value)
	{
		setRegistrationStartDate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.shortDescription</code> attribute.
	 * @return the shortDescription - Indicates the event description
	 */
	public String getShortDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getShortDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHORTDESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.shortDescription</code> attribute.
	 * @return the shortDescription - Indicates the event description
	 */
	public String getShortDescription()
	{
		return getShortDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.shortDescription</code> attribute. 
	 * @return the localized shortDescription - Indicates the event description
	 */
	public Map<Language,String> getAllShortDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHORTDESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.shortDescription</code> attribute. 
	 * @return the localized shortDescription - Indicates the event description
	 */
	public Map<Language,String> getAllShortDescription()
	{
		return getAllShortDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.shortDescription</code> attribute. 
	 * @param value the shortDescription - Indicates the event description
	 */
	public void setShortDescription(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setShortDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHORTDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.shortDescription</code> attribute. 
	 * @param value the shortDescription - Indicates the event description
	 */
	public void setShortDescription(final String value)
	{
		setShortDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.shortDescription</code> attribute. 
	 * @param value the shortDescription - Indicates the event description
	 */
	public void setAllShortDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHORTDESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.shortDescription</code> attribute. 
	 * @param value the shortDescription - Indicates the event description
	 */
	public void setAllShortDescription(final Map<Language,String> value)
	{
		setAllShortDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.state</code> attribute.
	 * @return the state
	 */
	public Region getState(final SessionContext ctx)
	{
		return (Region)getProperty( ctx, STATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.state</code> attribute.
	 * @return the state
	 */
	public Region getState()
	{
		return getState( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final SessionContext ctx, final Region value)
	{
		setProperty(ctx, STATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.state</code> attribute. 
	 * @param value the state
	 */
	public void setState(final Region value)
	{
		setState( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.time</code> attribute.
	 * @return the time - Indicates the event timings
	 */
	public String getTime(final SessionContext ctx)
	{
		return (String)getProperty( ctx, TIME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.time</code> attribute.
	 * @return the time - Indicates the event timings
	 */
	public String getTime()
	{
		return getTime( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.time</code> attribute. 
	 * @param value the time - Indicates the event timings
	 */
	public void setTime(final SessionContext ctx, final String value)
	{
		setProperty(ctx, TIME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.time</code> attribute. 
	 * @param value the time - Indicates the event timings
	 */
	public void setTime(final String value)
	{
		setTime( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.title</code> attribute.
	 * @return the title - Default value
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.title</code> attribute.
	 * @return the title - Default value
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.title</code> attribute. 
	 * @return the localized title - Default value
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.title</code> attribute. 
	 * @return the localized title - Default value
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.title</code> attribute. 
	 * @param value the title - Default value
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if ( ctx == null) 
		{
			throw new JaloInvalidParameterException( "ctx is null", 0 );
		}
		if( ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedSiteOneEvent.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.title</code> attribute. 
	 * @param value the title - Default value
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.title</code> attribute. 
	 * @param value the title - Default value
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.title</code> attribute. 
	 * @param value the title - Default value
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.type</code> attribute.
	 * @return the type - Indicates the event type
	 */
	public SiteOneEventType getType(final SessionContext ctx)
	{
		return (SiteOneEventType)getProperty( ctx, TYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.type</code> attribute.
	 * @return the type - Indicates the event type
	 */
	public SiteOneEventType getType()
	{
		return getType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.type</code> attribute. 
	 * @param value the type - Indicates the event type
	 */
	public void setType(final SessionContext ctx, final SiteOneEventType value)
	{
		setProperty(ctx, TYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.type</code> attribute. 
	 * @param value the type - Indicates the event type
	 */
	public void setType(final SiteOneEventType value)
	{
		setType( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.venue</code> attribute.
	 * @return the venue - Indicates the event venue
	 */
	public String getVenue(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VENUE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneEvent.venue</code> attribute.
	 * @return the venue - Indicates the event venue
	 */
	public String getVenue()
	{
		return getVenue( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.venue</code> attribute. 
	 * @param value the venue - Indicates the event venue
	 */
	public void setVenue(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VENUE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneEvent.venue</code> attribute. 
	 * @param value the venue - Indicates the event venue
	 */
	public void setVenue(final String value)
	{
		setVenue( getSession().getSessionContext(), value );
	}
	
}
