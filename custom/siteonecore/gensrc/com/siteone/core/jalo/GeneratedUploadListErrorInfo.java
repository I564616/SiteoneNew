/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.core.jalo;

import com.siteone.core.constants.SiteoneCoreConstants;
import com.siteone.core.jalo.UploadErrorProductDetail;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.siteone.core.jalo.UploadListErrorInfo UploadListErrorInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedUploadListErrorInfo extends GenericItem
{
	/** Qualifier of the <code>UploadListErrorInfo.errorId</code> attribute **/
	public static final String ERRORID = "errorId";
	/** Qualifier of the <code>UploadListErrorInfo.errorData</code> attribute **/
	public static final String ERRORDATA = "errorData";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ERRORID, AttributeMode.INITIAL);
		tmp.put(ERRORDATA, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadListErrorInfo.errorData</code> attribute.
	 * @return the errorData
	 */
	public List<UploadErrorProductDetail> getErrorData(final SessionContext ctx)
	{
		List<UploadErrorProductDetail> coll = (List<UploadErrorProductDetail>)getProperty( ctx, ERRORDATA);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadListErrorInfo.errorData</code> attribute.
	 * @return the errorData
	 */
	public List<UploadErrorProductDetail> getErrorData()
	{
		return getErrorData( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadListErrorInfo.errorData</code> attribute. 
	 * @param value the errorData
	 */
	public void setErrorData(final SessionContext ctx, final List<UploadErrorProductDetail> value)
	{
		setProperty(ctx, ERRORDATA,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadListErrorInfo.errorData</code> attribute. 
	 * @param value the errorData
	 */
	public void setErrorData(final List<UploadErrorProductDetail> value)
	{
		setErrorData( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadListErrorInfo.errorId</code> attribute.
	 * @return the errorId
	 */
	public String getErrorId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ERRORID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadListErrorInfo.errorId</code> attribute.
	 * @return the errorId
	 */
	public String getErrorId()
	{
		return getErrorId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadListErrorInfo.errorId</code> attribute. 
	 * @param value the errorId
	 */
	public void setErrorId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ERRORID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadListErrorInfo.errorId</code> attribute. 
	 * @param value the errorId
	 */
	public void setErrorId(final String value)
	{
		setErrorId( getSession().getSessionContext(), value );
	}
	
}
