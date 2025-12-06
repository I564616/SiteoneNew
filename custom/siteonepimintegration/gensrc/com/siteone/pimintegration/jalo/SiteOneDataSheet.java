/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import com.siteone.pimintegration.jalo.SiteOneBrochures;
import com.siteone.pimintegration.jalo.SiteOneInstallationInstructions;
import com.siteone.pimintegration.jalo.SiteOneOwnersManual;
import com.siteone.pimintegration.jalo.SiteOnePartsDiagram;
import com.siteone.pimintegration.jalo.SiteOneProductLabelSheet;
import com.siteone.pimintegration.jalo.SiteOneProductSafetyDataSheet;
import com.siteone.pimintegration.jalo.SiteOneProductSpecificationSheet;
import com.siteone.pimintegration.jalo.SiteOneTechnicalDrawing;
import com.siteone.pimintegration.jalo.SiteOneWarrantyInformation;
import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.util.PartOfHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type SiteOneDataSheet.
 */
@SLDSafe
@SuppressWarnings({"unused","cast"})
public class SiteOneDataSheet extends GenericItem
{
	/** Qualifier of the <code>SiteOneDataSheet.id</code> attribute **/
	public static final String ID = "id";
	/** Qualifier of the <code>SiteOneDataSheet.specificationSheet</code> attribute **/
	public static final String SPECIFICATIONSHEET = "specificationSheet";
	/** Qualifier of the <code>SiteOneDataSheet.safetyDataSheet</code> attribute **/
	public static final String SAFETYDATASHEET = "safetyDataSheet";
	/** Qualifier of the <code>SiteOneDataSheet.labelAsset</code> attribute **/
	public static final String LABELASSET = "labelAsset";
	/** Qualifier of the <code>SiteOneDataSheet.ownersManual</code> attribute **/
	public static final String OWNERSMANUAL = "ownersManual";
	/** Qualifier of the <code>SiteOneDataSheet.installationInstructions</code> attribute **/
	public static final String INSTALLATIONINSTRUCTIONS = "installationInstructions";
	/** Qualifier of the <code>SiteOneDataSheet.technicalDrawing</code> attribute **/
	public static final String TECHNICALDRAWING = "technicalDrawing";
	/** Qualifier of the <code>SiteOneDataSheet.partsDiagram</code> attribute **/
	public static final String PARTSDIAGRAM = "partsDiagram";
	/** Qualifier of the <code>SiteOneDataSheet.warrantyInformation</code> attribute **/
	public static final String WARRANTYINFORMATION = "warrantyInformation";
	/** Qualifier of the <code>SiteOneDataSheet.brochures</code> attribute **/
	public static final String BROCHURES = "brochures";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(ID, AttributeMode.INITIAL);
		tmp.put(SPECIFICATIONSHEET, AttributeMode.INITIAL);
		tmp.put(SAFETYDATASHEET, AttributeMode.INITIAL);
		tmp.put(LABELASSET, AttributeMode.INITIAL);
		tmp.put(OWNERSMANUAL, AttributeMode.INITIAL);
		tmp.put(INSTALLATIONINSTRUCTIONS, AttributeMode.INITIAL);
		tmp.put(TECHNICALDRAWING, AttributeMode.INITIAL);
		tmp.put(PARTSDIAGRAM, AttributeMode.INITIAL);
		tmp.put(WARRANTYINFORMATION, AttributeMode.INITIAL);
		tmp.put(BROCHURES, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.brochures</code> attribute.
	 * @return the brochures
	 */
	public List<SiteOneBrochures> getBrochures(final SessionContext ctx)
	{
		List<SiteOneBrochures> coll = (List<SiteOneBrochures>)getProperty( ctx, "brochures".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.brochures</code> attribute.
	 * @return the brochures
	 */
	public List<SiteOneBrochures> getBrochures()
	{
		return getBrochures( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.brochures</code> attribute. 
	 * @param value the brochures
	 */
	public void setBrochures(final SessionContext ctx, final List<SiteOneBrochures> value)
	{
		new PartOfHandler<List<SiteOneBrochures>>()
		{
			@Override
			protected List<SiteOneBrochures> doGetValue(final SessionContext ctx)
			{
				return getBrochures( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneBrochures> _value)
			{
				final List<SiteOneBrochures> value = _value;
				setProperty(ctx, "brochures".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.brochures</code> attribute. 
	 * @param value the brochures
	 */
	public void setBrochures(final List<SiteOneBrochures> value)
	{
		setBrochures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.id</code> attribute.
	 * @return the id
	 */
	public String getId(final SessionContext ctx)
	{
		return (String)getProperty( ctx, "id".intern());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.id</code> attribute.
	 * @return the id
	 */
	public String getId()
	{
		return getId( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final SessionContext ctx, final String value)
	{
		setProperty(ctx, "id".intern(),value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.id</code> attribute. 
	 * @param value the id
	 */
	public void setId(final String value)
	{
		setId( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.installationInstructions</code> attribute.
	 * @return the installationInstructions
	 */
	public List<SiteOneInstallationInstructions> getInstallationInstructions(final SessionContext ctx)
	{
		List<SiteOneInstallationInstructions> coll = (List<SiteOneInstallationInstructions>)getProperty( ctx, "installationInstructions".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.installationInstructions</code> attribute.
	 * @return the installationInstructions
	 */
	public List<SiteOneInstallationInstructions> getInstallationInstructions()
	{
		return getInstallationInstructions( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.installationInstructions</code> attribute. 
	 * @param value the installationInstructions
	 */
	public void setInstallationInstructions(final SessionContext ctx, final List<SiteOneInstallationInstructions> value)
	{
		new PartOfHandler<List<SiteOneInstallationInstructions>>()
		{
			@Override
			protected List<SiteOneInstallationInstructions> doGetValue(final SessionContext ctx)
			{
				return getInstallationInstructions( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneInstallationInstructions> _value)
			{
				final List<SiteOneInstallationInstructions> value = _value;
				setProperty(ctx, "installationInstructions".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.installationInstructions</code> attribute. 
	 * @param value the installationInstructions
	 */
	public void setInstallationInstructions(final List<SiteOneInstallationInstructions> value)
	{
		setInstallationInstructions( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.labelAsset</code> attribute.
	 * @return the labelAsset
	 */
	public List<SiteOneProductLabelSheet> getLabelAsset(final SessionContext ctx)
	{
		List<SiteOneProductLabelSheet> coll = (List<SiteOneProductLabelSheet>)getProperty( ctx, "labelAsset".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.labelAsset</code> attribute.
	 * @return the labelAsset
	 */
	public List<SiteOneProductLabelSheet> getLabelAsset()
	{
		return getLabelAsset( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.labelAsset</code> attribute. 
	 * @param value the labelAsset
	 */
	public void setLabelAsset(final SessionContext ctx, final List<SiteOneProductLabelSheet> value)
	{
		new PartOfHandler<List<SiteOneProductLabelSheet>>()
		{
			@Override
			protected List<SiteOneProductLabelSheet> doGetValue(final SessionContext ctx)
			{
				return getLabelAsset( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductLabelSheet> _value)
			{
				final List<SiteOneProductLabelSheet> value = _value;
				setProperty(ctx, "labelAsset".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.labelAsset</code> attribute. 
	 * @param value the labelAsset
	 */
	public void setLabelAsset(final List<SiteOneProductLabelSheet> value)
	{
		setLabelAsset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.ownersManual</code> attribute.
	 * @return the ownersManual
	 */
	public List<SiteOneOwnersManual> getOwnersManual(final SessionContext ctx)
	{
		List<SiteOneOwnersManual> coll = (List<SiteOneOwnersManual>)getProperty( ctx, "ownersManual".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.ownersManual</code> attribute.
	 * @return the ownersManual
	 */
	public List<SiteOneOwnersManual> getOwnersManual()
	{
		return getOwnersManual( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.ownersManual</code> attribute. 
	 * @param value the ownersManual
	 */
	public void setOwnersManual(final SessionContext ctx, final List<SiteOneOwnersManual> value)
	{
		new PartOfHandler<List<SiteOneOwnersManual>>()
		{
			@Override
			protected List<SiteOneOwnersManual> doGetValue(final SessionContext ctx)
			{
				return getOwnersManual( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneOwnersManual> _value)
			{
				final List<SiteOneOwnersManual> value = _value;
				setProperty(ctx, "ownersManual".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.ownersManual</code> attribute. 
	 * @param value the ownersManual
	 */
	public void setOwnersManual(final List<SiteOneOwnersManual> value)
	{
		setOwnersManual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.partsDiagram</code> attribute.
	 * @return the partsDiagram
	 */
	public List<SiteOnePartsDiagram> getPartsDiagram(final SessionContext ctx)
	{
		List<SiteOnePartsDiagram> coll = (List<SiteOnePartsDiagram>)getProperty( ctx, "partsDiagram".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.partsDiagram</code> attribute.
	 * @return the partsDiagram
	 */
	public List<SiteOnePartsDiagram> getPartsDiagram()
	{
		return getPartsDiagram( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.partsDiagram</code> attribute. 
	 * @param value the partsDiagram
	 */
	public void setPartsDiagram(final SessionContext ctx, final List<SiteOnePartsDiagram> value)
	{
		new PartOfHandler<List<SiteOnePartsDiagram>>()
		{
			@Override
			protected List<SiteOnePartsDiagram> doGetValue(final SessionContext ctx)
			{
				return getPartsDiagram( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOnePartsDiagram> _value)
			{
				final List<SiteOnePartsDiagram> value = _value;
				setProperty(ctx, "partsDiagram".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.partsDiagram</code> attribute. 
	 * @param value the partsDiagram
	 */
	public void setPartsDiagram(final List<SiteOnePartsDiagram> value)
	{
		setPartsDiagram( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.safetyDataSheet</code> attribute.
	 * @return the safetyDataSheet
	 */
	public List<SiteOneProductSafetyDataSheet> getSafetyDataSheet(final SessionContext ctx)
	{
		List<SiteOneProductSafetyDataSheet> coll = (List<SiteOneProductSafetyDataSheet>)getProperty( ctx, "safetyDataSheet".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.safetyDataSheet</code> attribute.
	 * @return the safetyDataSheet
	 */
	public List<SiteOneProductSafetyDataSheet> getSafetyDataSheet()
	{
		return getSafetyDataSheet( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.safetyDataSheet</code> attribute. 
	 * @param value the safetyDataSheet
	 */
	public void setSafetyDataSheet(final SessionContext ctx, final List<SiteOneProductSafetyDataSheet> value)
	{
		new PartOfHandler<List<SiteOneProductSafetyDataSheet>>()
		{
			@Override
			protected List<SiteOneProductSafetyDataSheet> doGetValue(final SessionContext ctx)
			{
				return getSafetyDataSheet( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductSafetyDataSheet> _value)
			{
				final List<SiteOneProductSafetyDataSheet> value = _value;
				setProperty(ctx, "safetyDataSheet".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.safetyDataSheet</code> attribute. 
	 * @param value the safetyDataSheet
	 */
	public void setSafetyDataSheet(final List<SiteOneProductSafetyDataSheet> value)
	{
		setSafetyDataSheet( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.specificationSheet</code> attribute.
	 * @return the specificationSheet
	 */
	public List<SiteOneProductSpecificationSheet> getSpecificationSheet(final SessionContext ctx)
	{
		List<SiteOneProductSpecificationSheet> coll = (List<SiteOneProductSpecificationSheet>)getProperty( ctx, "specificationSheet".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.specificationSheet</code> attribute.
	 * @return the specificationSheet
	 */
	public List<SiteOneProductSpecificationSheet> getSpecificationSheet()
	{
		return getSpecificationSheet( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.specificationSheet</code> attribute. 
	 * @param value the specificationSheet
	 */
	public void setSpecificationSheet(final SessionContext ctx, final List<SiteOneProductSpecificationSheet> value)
	{
		new PartOfHandler<List<SiteOneProductSpecificationSheet>>()
		{
			@Override
			protected List<SiteOneProductSpecificationSheet> doGetValue(final SessionContext ctx)
			{
				return getSpecificationSheet( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductSpecificationSheet> _value)
			{
				final List<SiteOneProductSpecificationSheet> value = _value;
				setProperty(ctx, "specificationSheet".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.specificationSheet</code> attribute. 
	 * @param value the specificationSheet
	 */
	public void setSpecificationSheet(final List<SiteOneProductSpecificationSheet> value)
	{
		setSpecificationSheet( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.technicalDrawing</code> attribute.
	 * @return the technicalDrawing
	 */
	public List<SiteOneTechnicalDrawing> getTechnicalDrawing(final SessionContext ctx)
	{
		List<SiteOneTechnicalDrawing> coll = (List<SiteOneTechnicalDrawing>)getProperty( ctx, "technicalDrawing".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.technicalDrawing</code> attribute.
	 * @return the technicalDrawing
	 */
	public List<SiteOneTechnicalDrawing> getTechnicalDrawing()
	{
		return getTechnicalDrawing( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.technicalDrawing</code> attribute. 
	 * @param value the technicalDrawing
	 */
	public void setTechnicalDrawing(final SessionContext ctx, final List<SiteOneTechnicalDrawing> value)
	{
		new PartOfHandler<List<SiteOneTechnicalDrawing>>()
		{
			@Override
			protected List<SiteOneTechnicalDrawing> doGetValue(final SessionContext ctx)
			{
				return getTechnicalDrawing( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneTechnicalDrawing> _value)
			{
				final List<SiteOneTechnicalDrawing> value = _value;
				setProperty(ctx, "technicalDrawing".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.technicalDrawing</code> attribute. 
	 * @param value the technicalDrawing
	 */
	public void setTechnicalDrawing(final List<SiteOneTechnicalDrawing> value)
	{
		setTechnicalDrawing( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.warrantyInformation</code> attribute.
	 * @return the warrantyInformation
	 */
	public List<SiteOneWarrantyInformation> getWarrantyInformation(final SessionContext ctx)
	{
		List<SiteOneWarrantyInformation> coll = (List<SiteOneWarrantyInformation>)getProperty( ctx, "warrantyInformation".intern());
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>SiteOneDataSheet.warrantyInformation</code> attribute.
	 * @return the warrantyInformation
	 */
	public List<SiteOneWarrantyInformation> getWarrantyInformation()
	{
		return getWarrantyInformation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.warrantyInformation</code> attribute. 
	 * @param value the warrantyInformation
	 */
	public void setWarrantyInformation(final SessionContext ctx, final List<SiteOneWarrantyInformation> value)
	{
		new PartOfHandler<List<SiteOneWarrantyInformation>>()
		{
			@Override
			protected List<SiteOneWarrantyInformation> doGetValue(final SessionContext ctx)
			{
				return getWarrantyInformation( ctx );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneWarrantyInformation> _value)
			{
				final List<SiteOneWarrantyInformation> value = _value;
				setProperty(ctx, "warrantyInformation".intern(),value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>SiteOneDataSheet.warrantyInformation</code> attribute. 
	 * @param value the warrantyInformation
	 */
	public void setWarrantyInformation(final List<SiteOneWarrantyInformation> value)
	{
		setWarrantyInformation( getSession().getSessionContext(), value );
	}
	
}
