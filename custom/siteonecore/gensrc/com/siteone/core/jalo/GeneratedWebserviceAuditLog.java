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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.WebserviceAuditLog WebserviceAuditLog}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedWebserviceAuditLog extends GenericItem
{
	/** Qualifier of the <code>WebserviceAuditLog.correlationId</code> attribute **/
	public static final String CORRELATIONID = "correlationId";
	/** Qualifier of the <code>WebserviceAuditLog.serviceName</code> attribute **/
	public static final String SERVICENAME = "serviceName";
	/** Qualifier of the <code>WebserviceAuditLog.endPointUrl</code> attribute **/
	public static final String ENDPOINTURL = "endPointUrl";
	/** Qualifier of the <code>WebserviceAuditLog.request</code> attribute **/
	public static final String REQUEST = "request";
	/** Qualifier of the <code>WebserviceAuditLog.requestLog</code> attribute **/
	public static final String REQUESTLOG = "requestLog";
	/** Qualifier of the <code>WebserviceAuditLog.response</code> attribute **/
	public static final String RESPONSE = "response";
	/** Qualifier of the <code>WebserviceAuditLog.responseLog</code> attribute **/
	public static final String RESPONSELOG = "responseLog";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(CORRELATIONID, AttributeMode.INITIAL);
		tmp.put(SERVICENAME, AttributeMode.INITIAL);
		tmp.put(ENDPOINTURL, AttributeMode.INITIAL);
		tmp.put(REQUEST, AttributeMode.INITIAL);
		tmp.put(REQUESTLOG, AttributeMode.INITIAL);
		tmp.put(RESPONSE, AttributeMode.INITIAL);
		tmp.put(RESPONSELOG, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.correlationId</code> attribute.
	 * @return the correlationId
	 */
	public String getCorrelationId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CORRELATIONID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.correlationId</code> attribute.
	 * @return the correlationId
	 */
	public String getCorrelationId()
	{
		return getCorrelationId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.correlationId</code> attribute. 
	 * @param value the correlationId
	 */
	public void setCorrelationId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CORRELATIONID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.correlationId</code> attribute. 
	 * @param value the correlationId
	 */
	public void setCorrelationId(final String value)
	{
		setCorrelationId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.endPointUrl</code> attribute.
	 * @return the endPointUrl
	 */
	public String getEndPointUrl(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ENDPOINTURL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.endPointUrl</code> attribute.
	 * @return the endPointUrl
	 */
	public String getEndPointUrl()
	{
		return getEndPointUrl( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.endPointUrl</code> attribute. 
	 * @param value the endPointUrl
	 */
	public void setEndPointUrl(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ENDPOINTURL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.endPointUrl</code> attribute. 
	 * @param value the endPointUrl
	 */
	public void setEndPointUrl(final String value)
	{
		setEndPointUrl( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.request</code> attribute.
	 * @return the request
	 */
	public String getRequest(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REQUEST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.request</code> attribute.
	 * @return the request
	 */
	public String getRequest()
	{
		return getRequest( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.request</code> attribute. 
	 * @param value the request
	 */
	public void setRequest(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REQUEST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.request</code> attribute. 
	 * @param value the request
	 */
	public void setRequest(final String value)
	{
		setRequest( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.requestLog</code> attribute.
	 * @return the requestLog
	 */
	public String getRequestLog(final SessionContext ctx)
	{
		return (String)getProperty( ctx, REQUESTLOG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.requestLog</code> attribute.
	 * @return the requestLog
	 */
	public String getRequestLog()
	{
		return getRequestLog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.requestLog</code> attribute. 
	 * @param value the requestLog
	 */
	public void setRequestLog(final SessionContext ctx, final String value)
	{
		setProperty(ctx, REQUESTLOG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.requestLog</code> attribute. 
	 * @param value the requestLog
	 */
	public void setRequestLog(final String value)
	{
		setRequestLog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.response</code> attribute.
	 * @return the response
	 */
	public String getResponse(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RESPONSE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.response</code> attribute.
	 * @return the response
	 */
	public String getResponse()
	{
		return getResponse( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.response</code> attribute. 
	 * @param value the response
	 */
	public void setResponse(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RESPONSE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.response</code> attribute. 
	 * @param value the response
	 */
	public void setResponse(final String value)
	{
		setResponse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.responseLog</code> attribute.
	 * @return the responseLog
	 */
	public String getResponseLog(final SessionContext ctx)
	{
		return (String)getProperty( ctx, RESPONSELOG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.responseLog</code> attribute.
	 * @return the responseLog
	 */
	public String getResponseLog()
	{
		return getResponseLog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.responseLog</code> attribute. 
	 * @param value the responseLog
	 */
	public void setResponseLog(final SessionContext ctx, final String value)
	{
		setProperty(ctx, RESPONSELOG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.responseLog</code> attribute. 
	 * @param value the responseLog
	 */
	public void setResponseLog(final String value)
	{
		setResponseLog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.serviceName</code> attribute.
	 * @return the serviceName
	 */
	public String getServiceName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SERVICENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WebserviceAuditLog.serviceName</code> attribute.
	 * @return the serviceName
	 */
	public String getServiceName()
	{
		return getServiceName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.serviceName</code> attribute. 
	 * @param value the serviceName
	 */
	public void setServiceName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SERVICENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WebserviceAuditLog.serviceName</code> attribute. 
	 * @param value the serviceName
	 */
	public void setServiceName(final String value)
	{
		setServiceName( getSession().getSessionContext(), value );
	}
	
}
