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
 * Generated class for type {@link com.siteone.core.jalo.SiteOneFeedFileInfo SiteOneFeedFileInfo}.
 */
@SuppressWarnings({"deprecation","unused","cast"})
public abstract class GeneratedSiteOneFeedFileInfo extends GenericItem
{
	/** Qualifier of the <code>SiteOneFeedFileInfo.fileName</code> attribute **/
	public static final String FILENAME = "fileName";
	/** Qualifier of the <code>SiteOneFeedFileInfo.fileType</code> attribute **/
	public static final String FILETYPE = "fileType";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(FILENAME, AttributeMode.INITIAL);
		tmp.put(FILETYPE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeedFileInfo.fileName</code> attribute.
	 * @return the fileName
	 */
	public String getFileName(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeedFileInfo.fileName</code> attribute.
	 * @return the fileName
	 */
	public String getFileName()
	{
		return getFileName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeedFileInfo.fileName</code> attribute. 
	 * @param value the fileName
	 */
	public void setFileName(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeedFileInfo.fileName</code> attribute. 
	 * @param value the fileName
	 */
	public void setFileName(final String value)
	{
		setFileName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeedFileInfo.fileType</code> attribute.
	 * @return the fileType
	 */
	public String getFileType(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FILETYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneFeedFileInfo.fileType</code> attribute.
	 * @return the fileType
	 */
	public String getFileType()
	{
		return getFileType( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeedFileInfo.fileType</code> attribute. 
	 * @param value the fileType
	 */
	public void setFileType(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FILETYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneFeedFileInfo.fileType</code> attribute. 
	 * @param value the fileType
	 */
	public void setFileType(final String value)
	{
		setFileType( getSession().getSessionContext(), value );
	}
	
}
