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
 * Generated class for type {@link com.siteone.core.jalo.UploadErrorProductDetail UploadErrorProductDetail}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedUploadErrorProductDetail extends GenericItem
{
	/** Qualifier of the <code>UploadErrorProductDetail.qty</code> attribute **/
	public static final String QTY = "qty";
	/** Qualifier of the <code>UploadErrorProductDetail.productCode</code> attribute **/
	public static final String PRODUCTCODE = "productCode";
	/** Qualifier of the <code>UploadErrorProductDetail.errorException</code> attribute **/
	public static final String ERROREXCEPTION = "errorException";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(QTY, AttributeMode.INITIAL);
		tmp.put(PRODUCTCODE, AttributeMode.INITIAL);
		tmp.put(ERROREXCEPTION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadErrorProductDetail.errorException</code> attribute.
	 * @return the errorException
	 */
	public String getErrorException(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ERROREXCEPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadErrorProductDetail.errorException</code> attribute.
	 * @return the errorException
	 */
	public String getErrorException()
	{
		return getErrorException( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadErrorProductDetail.errorException</code> attribute. 
	 * @param value the errorException
	 */
	public void setErrorException(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ERROREXCEPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadErrorProductDetail.errorException</code> attribute. 
	 * @param value the errorException
	 */
	public void setErrorException(final String value)
	{
		setErrorException( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadErrorProductDetail.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRODUCTCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadErrorProductDetail.productCode</code> attribute.
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return getProductCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadErrorProductDetail.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRODUCTCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadErrorProductDetail.productCode</code> attribute. 
	 * @param value the productCode
	 */
	public void setProductCode(final String value)
	{
		setProductCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadErrorProductDetail.qty</code> attribute.
	 * @return the qty
	 */
	public String getQty(final SessionContext ctx)
	{
		return (String)getProperty( ctx, QTY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UploadErrorProductDetail.qty</code> attribute.
	 * @return the qty
	 */
	public String getQty()
	{
		return getQty( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadErrorProductDetail.qty</code> attribute. 
	 * @param value the qty
	 */
	public void setQty(final SessionContext ctx, final String value)
	{
		setProperty(ctx, QTY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UploadErrorProductDetail.qty</code> attribute. 
	 * @param value the qty
	 */
	public void setQty(final String value)
	{
		setQty( getSession().getSessionContext(), value );
	}
	
}
