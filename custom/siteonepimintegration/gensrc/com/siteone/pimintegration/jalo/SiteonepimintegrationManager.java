/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Nov 28, 2025, 2:14:16 PM                    ---
 * ----------------------------------------------------------------
 */
package com.siteone.pimintegration.jalo;

import com.siteone.pimintegration.constants.SiteonepimintegrationConstants;
import com.siteone.pimintegration.jalo.SiteOneBrochures;
import com.siteone.pimintegration.jalo.SiteOneCategoryAttributes;
import com.siteone.pimintegration.jalo.SiteOneCategoryLevel;
import com.siteone.pimintegration.jalo.SiteOneCategoryPayload;
import com.siteone.pimintegration.jalo.SiteOneDataSheet;
import com.siteone.pimintegration.jalo.SiteOneInstallationInstructions;
import com.siteone.pimintegration.jalo.SiteOneOwnersManual;
import com.siteone.pimintegration.jalo.SiteOnePartsDiagram;
import com.siteone.pimintegration.jalo.SiteOneProductImage;
import com.siteone.pimintegration.jalo.SiteOneProductItemImages;
import com.siteone.pimintegration.jalo.SiteOneProductLabelSheet;
import com.siteone.pimintegration.jalo.SiteOneProductSafetyDataSheet;
import com.siteone.pimintegration.jalo.SiteOneProductSavingsCenterAttribute;
import com.siteone.pimintegration.jalo.SiteOneProductSpecificationAttribute;
import com.siteone.pimintegration.jalo.SiteOneProductSpecificationSheet;
import com.siteone.pimintegration.jalo.SiteOneProductTransformation;
import com.siteone.pimintegration.jalo.SiteOneTechnicalDrawing;
import com.siteone.pimintegration.jalo.SiteOneWarrantyInformation;
import de.hybris.platform.directpersistence.annotation.SLDSafe;
import de.hybris.platform.inboundservices.jalo.InboundRequest;
import de.hybris.platform.integrationservices.jalo.MonitoredRequest;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.extension.ExtensionManager;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.util.PartOfHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type <code>SiteonepimintegrationManager</code>.
 */
@SuppressWarnings({"unused","cast"})
@SLDSafe
public class SiteonepimintegrationManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("specificationAttribute", AttributeMode.INITIAL);
		tmp.put("inventoryUPCID", AttributeMode.INITIAL);
		tmp.put("baseUPCCode", AttributeMode.INITIAL);
		tmp.put("productKind", AttributeMode.INITIAL);
		tmp.put("itemImage", AttributeMode.INITIAL);
		tmp.put("lifeStyle", AttributeMode.INITIAL);
		tmp.put("swatchImage", AttributeMode.INITIAL);
		tmp.put("brandLogo", AttributeMode.INITIAL);
		tmp.put("specialImageType", AttributeMode.INITIAL);
		tmp.put("sheets", AttributeMode.INITIAL);
		tmp.put("savingsCenterAttribute", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.product.Product", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("batchPayload", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.inboundservices.jalo.InboundRequest", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.baseUPCCode</code> attribute.
	 * @return the baseUPCCode
	 */
	public String getBaseUPCCode(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.BASEUPCCODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.baseUPCCode</code> attribute.
	 * @return the baseUPCCode
	 */
	public String getBaseUPCCode(final Product item)
	{
		return getBaseUPCCode( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.baseUPCCode</code> attribute. 
	 * @param value the baseUPCCode
	 */
	public void setBaseUPCCode(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.BASEUPCCODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.baseUPCCode</code> attribute. 
	 * @param value the baseUPCCode
	 */
	public void setBaseUPCCode(final Product item, final String value)
	{
		setBaseUPCCode( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InboundRequest.batchPayload</code> attribute.
	 * @return the batchPayload
	 */
	public String getBatchPayload(final SessionContext ctx, final GenericItem item)
	{
		return (String)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.InboundRequest.BATCHPAYLOAD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>InboundRequest.batchPayload</code> attribute.
	 * @return the batchPayload
	 */
	public String getBatchPayload(final InboundRequest item)
	{
		return getBatchPayload( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InboundRequest.batchPayload</code> attribute. 
	 * @param value the batchPayload
	 */
	public void setBatchPayload(final SessionContext ctx, final GenericItem item, final String value)
	{
		item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.InboundRequest.BATCHPAYLOAD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>InboundRequest.batchPayload</code> attribute. 
	 * @param value the batchPayload
	 */
	public void setBatchPayload(final InboundRequest item, final String value)
	{
		setBatchPayload( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.brandLogo</code> attribute.
	 * @return the brandLogo
	 */
	public List<SiteOneProductItemImages> getBrandLogo(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductItemImages> coll = (List<SiteOneProductItemImages>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.BRANDLOGO);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.brandLogo</code> attribute.
	 * @return the brandLogo
	 */
	public List<SiteOneProductItemImages> getBrandLogo(final Product item)
	{
		return getBrandLogo( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.brandLogo</code> attribute. 
	 * @param value the brandLogo
	 */
	public void setBrandLogo(final SessionContext ctx, final Product item, final List<SiteOneProductItemImages> value)
	{
		new PartOfHandler<List<SiteOneProductItemImages>>()
		{
			@Override
			protected List<SiteOneProductItemImages> doGetValue(final SessionContext ctx)
			{
				return getBrandLogo( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductItemImages> _value)
			{
				final List<SiteOneProductItemImages> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.BRANDLOGO,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.brandLogo</code> attribute. 
	 * @param value the brandLogo
	 */
	public void setBrandLogo(final Product item, final List<SiteOneProductItemImages> value)
	{
		setBrandLogo( getSession().getSessionContext(), item, value );
	}
	
	public SiteOneBrochures createSiteOneBrochures(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneBrochures");
			return (SiteOneBrochures)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneBrochures : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneBrochures createSiteOneBrochures(final Map attributeValues)
	{
		return createSiteOneBrochures( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryAttributes createSiteOneCategoryAttributes(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneCategoryAttributes");
			return (SiteOneCategoryAttributes)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryAttributes : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryAttributes createSiteOneCategoryAttributes(final Map attributeValues)
	{
		return createSiteOneCategoryAttributes( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryLevel createSiteOneCategoryLevel(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneCategoryLevel");
			return (SiteOneCategoryLevel)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryLevel : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryLevel createSiteOneCategoryLevel(final Map attributeValues)
	{
		return createSiteOneCategoryLevel( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneCategoryPayload createSiteOneCategoryPayload(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneCategoryPayload");
			return (SiteOneCategoryPayload)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneCategoryPayload : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneCategoryPayload createSiteOneCategoryPayload(final Map attributeValues)
	{
		return createSiteOneCategoryPayload( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneDataSheet createSiteOneDataSheet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneDataSheet");
			return (SiteOneDataSheet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneDataSheet : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneDataSheet createSiteOneDataSheet(final Map attributeValues)
	{
		return createSiteOneDataSheet( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneInstallationInstructions createSiteOneInstallationInstructions(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneInstallationInstructions");
			return (SiteOneInstallationInstructions)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneInstallationInstructions : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneInstallationInstructions createSiteOneInstallationInstructions(final Map attributeValues)
	{
		return createSiteOneInstallationInstructions( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneOwnersManual createSiteOneOwnersManual(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneOwnersManual");
			return (SiteOneOwnersManual)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneOwnersManual : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneOwnersManual createSiteOneOwnersManual(final Map attributeValues)
	{
		return createSiteOneOwnersManual( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOnePartsDiagram createSiteOnePartsDiagram(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOnePartsDiagram");
			return (SiteOnePartsDiagram)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOnePartsDiagram : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOnePartsDiagram createSiteOnePartsDiagram(final Map attributeValues)
	{
		return createSiteOnePartsDiagram( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductImage createSiteOneProductImage(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductImage");
			return (SiteOneProductImage)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductImage : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductImage createSiteOneProductImage(final Map attributeValues)
	{
		return createSiteOneProductImage( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductItemImages createSiteOneProductItemImages(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductItemImages");
			return (SiteOneProductItemImages)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductItemImages : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductItemImages createSiteOneProductItemImages(final Map attributeValues)
	{
		return createSiteOneProductItemImages( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductLabelSheet createSiteOneProductLabelSheet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductLabelSheet");
			return (SiteOneProductLabelSheet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductLabelSheet : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductLabelSheet createSiteOneProductLabelSheet(final Map attributeValues)
	{
		return createSiteOneProductLabelSheet( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductSafetyDataSheet createSiteOneProductSafetyDataSheet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductSafetyDataSheet");
			return (SiteOneProductSafetyDataSheet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductSafetyDataSheet : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductSafetyDataSheet createSiteOneProductSafetyDataSheet(final Map attributeValues)
	{
		return createSiteOneProductSafetyDataSheet( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductSavingsCenterAttribute createSiteOneProductSavingsCenterAttribute(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductSavingsCenterAttribute");
			return (SiteOneProductSavingsCenterAttribute)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductSavingsCenterAttribute : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductSavingsCenterAttribute createSiteOneProductSavingsCenterAttribute(final Map attributeValues)
	{
		return createSiteOneProductSavingsCenterAttribute( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductSpecificationAttribute createSiteOneProductSpecificationAttribute(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductSpecificationAttribute");
			return (SiteOneProductSpecificationAttribute)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductSpecificationAttribute : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductSpecificationAttribute createSiteOneProductSpecificationAttribute(final Map attributeValues)
	{
		return createSiteOneProductSpecificationAttribute( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductSpecificationSheet createSiteOneProductSpecificationSheet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductSpecificationSheet");
			return (SiteOneProductSpecificationSheet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductSpecificationSheet : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductSpecificationSheet createSiteOneProductSpecificationSheet(final Map attributeValues)
	{
		return createSiteOneProductSpecificationSheet( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneProductTransformation createSiteOneProductTransformation(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneProductTransformation");
			return (SiteOneProductTransformation)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneProductTransformation : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneProductTransformation createSiteOneProductTransformation(final Map attributeValues)
	{
		return createSiteOneProductTransformation( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneTechnicalDrawing createSiteOneTechnicalDrawing(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneTechnicalDrawing");
			return (SiteOneTechnicalDrawing)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneTechnicalDrawing : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneTechnicalDrawing createSiteOneTechnicalDrawing(final Map attributeValues)
	{
		return createSiteOneTechnicalDrawing( getSession().getSessionContext(), attributeValues );
	}
	
	public SiteOneWarrantyInformation createSiteOneWarrantyInformation(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType("SiteOneWarrantyInformation");
			return (SiteOneWarrantyInformation)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating SiteOneWarrantyInformation : "+e.getMessage(), 0 );
		}
	}
	
	public SiteOneWarrantyInformation createSiteOneWarrantyInformation(final Map attributeValues)
	{
		return createSiteOneWarrantyInformation( getSession().getSessionContext(), attributeValues );
	}
	
	public static final SiteonepimintegrationManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SiteonepimintegrationManager) em.getExtension(SiteonepimintegrationConstants.EXTENSIONNAME);
	}
	
	@Override
	public String getName()
	{
		return SiteonepimintegrationConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryUPCID</code> attribute.
	 * @return the inventoryUPCID
	 */
	public String getInventoryUPCID(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.INVENTORYUPCID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.inventoryUPCID</code> attribute.
	 * @return the inventoryUPCID
	 */
	public String getInventoryUPCID(final Product item)
	{
		return getInventoryUPCID( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryUPCID</code> attribute. 
	 * @param value the inventoryUPCID
	 */
	public void setInventoryUPCID(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.INVENTORYUPCID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.inventoryUPCID</code> attribute. 
	 * @param value the inventoryUPCID
	 */
	public void setInventoryUPCID(final Product item, final String value)
	{
		setInventoryUPCID( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.itemImage</code> attribute.
	 * @return the itemImage
	 */
	public List<SiteOneProductItemImages> getItemImage(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductItemImages> coll = (List<SiteOneProductItemImages>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.ITEMIMAGE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.itemImage</code> attribute.
	 * @return the itemImage
	 */
	public List<SiteOneProductItemImages> getItemImage(final Product item)
	{
		return getItemImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.itemImage</code> attribute. 
	 * @param value the itemImage
	 */
	public void setItemImage(final SessionContext ctx, final Product item, final List<SiteOneProductItemImages> value)
	{
		new PartOfHandler<List<SiteOneProductItemImages>>()
		{
			@Override
			protected List<SiteOneProductItemImages> doGetValue(final SessionContext ctx)
			{
				return getItemImage( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductItemImages> _value)
			{
				final List<SiteOneProductItemImages> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.ITEMIMAGE,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.itemImage</code> attribute. 
	 * @param value the itemImage
	 */
	public void setItemImage(final Product item, final List<SiteOneProductItemImages> value)
	{
		setItemImage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.lifeStyle</code> attribute.
	 * @return the lifeStyle
	 */
	public List<SiteOneProductItemImages> getLifeStyle(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductItemImages> coll = (List<SiteOneProductItemImages>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.LIFESTYLE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.lifeStyle</code> attribute.
	 * @return the lifeStyle
	 */
	public List<SiteOneProductItemImages> getLifeStyle(final Product item)
	{
		return getLifeStyle( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.lifeStyle</code> attribute. 
	 * @param value the lifeStyle
	 */
	public void setLifeStyle(final SessionContext ctx, final Product item, final List<SiteOneProductItemImages> value)
	{
		new PartOfHandler<List<SiteOneProductItemImages>>()
		{
			@Override
			protected List<SiteOneProductItemImages> doGetValue(final SessionContext ctx)
			{
				return getLifeStyle( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductItemImages> _value)
			{
				final List<SiteOneProductItemImages> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.LIFESTYLE,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.lifeStyle</code> attribute. 
	 * @param value the lifeStyle
	 */
	public void setLifeStyle(final Product item, final List<SiteOneProductItemImages> value)
	{
		setLifeStyle( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productKind</code> attribute.
	 * @return the productKind
	 */
	public String getProductKind(final SessionContext ctx, final Product item)
	{
		return (String)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.PRODUCTKIND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.productKind</code> attribute.
	 * @return the productKind
	 */
	public String getProductKind(final Product item)
	{
		return getProductKind( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productKind</code> attribute. 
	 * @param value the productKind
	 */
	public void setProductKind(final SessionContext ctx, final Product item, final String value)
	{
		item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.PRODUCTKIND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.productKind</code> attribute. 
	 * @param value the productKind
	 */
	public void setProductKind(final Product item, final String value)
	{
		setProductKind( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.savingsCenterAttribute</code> attribute.
	 * @return the savingsCenterAttribute
	 */
	public List<SiteOneProductSavingsCenterAttribute> getSavingsCenterAttribute(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductSavingsCenterAttribute> coll = (List<SiteOneProductSavingsCenterAttribute>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.SAVINGSCENTERATTRIBUTE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.savingsCenterAttribute</code> attribute.
	 * @return the savingsCenterAttribute
	 */
	public List<SiteOneProductSavingsCenterAttribute> getSavingsCenterAttribute(final Product item)
	{
		return getSavingsCenterAttribute( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.savingsCenterAttribute</code> attribute. 
	 * @param value the savingsCenterAttribute
	 */
	public void setSavingsCenterAttribute(final SessionContext ctx, final Product item, final List<SiteOneProductSavingsCenterAttribute> value)
	{
		new PartOfHandler<List<SiteOneProductSavingsCenterAttribute>>()
		{
			@Override
			protected List<SiteOneProductSavingsCenterAttribute> doGetValue(final SessionContext ctx)
			{
				return getSavingsCenterAttribute( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductSavingsCenterAttribute> _value)
			{
				final List<SiteOneProductSavingsCenterAttribute> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.SAVINGSCENTERATTRIBUTE,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.savingsCenterAttribute</code> attribute. 
	 * @param value the savingsCenterAttribute
	 */
	public void setSavingsCenterAttribute(final Product item, final List<SiteOneProductSavingsCenterAttribute> value)
	{
		setSavingsCenterAttribute( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.sheets</code> attribute.
	 * @return the sheets
	 */
	public SiteOneDataSheet getSheets(final SessionContext ctx, final Product item)
	{
		return (SiteOneDataSheet)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.SHEETS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.sheets</code> attribute.
	 * @return the sheets
	 */
	public SiteOneDataSheet getSheets(final Product item)
	{
		return getSheets( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.sheets</code> attribute. 
	 * @param value the sheets
	 */
	public void setSheets(final SessionContext ctx, final Product item, final SiteOneDataSheet value)
	{
		new PartOfHandler<SiteOneDataSheet>()
		{
			@Override
			protected SiteOneDataSheet doGetValue(final SessionContext ctx)
			{
				return getSheets( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final SiteOneDataSheet _value)
			{
				final SiteOneDataSheet value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.SHEETS,value);
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.sheets</code> attribute. 
	 * @param value the sheets
	 */
	public void setSheets(final Product item, final SiteOneDataSheet value)
	{
		setSheets( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specialImageType</code> attribute.
	 * @return the specialImageType
	 */
	public List<SiteOneProductItemImages> getSpecialImageType(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductItemImages> coll = (List<SiteOneProductItemImages>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.SPECIALIMAGETYPE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specialImageType</code> attribute.
	 * @return the specialImageType
	 */
	public List<SiteOneProductItemImages> getSpecialImageType(final Product item)
	{
		return getSpecialImageType( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specialImageType</code> attribute. 
	 * @param value the specialImageType
	 */
	public void setSpecialImageType(final SessionContext ctx, final Product item, final List<SiteOneProductItemImages> value)
	{
		new PartOfHandler<List<SiteOneProductItemImages>>()
		{
			@Override
			protected List<SiteOneProductItemImages> doGetValue(final SessionContext ctx)
			{
				return getSpecialImageType( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductItemImages> _value)
			{
				final List<SiteOneProductItemImages> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.SPECIALIMAGETYPE,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specialImageType</code> attribute. 
	 * @param value the specialImageType
	 */
	public void setSpecialImageType(final Product item, final List<SiteOneProductItemImages> value)
	{
		setSpecialImageType( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationAttribute</code> attribute.
	 * @return the specificationAttribute
	 */
	public List<SiteOneProductSpecificationAttribute> getSpecificationAttribute(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductSpecificationAttribute> coll = (List<SiteOneProductSpecificationAttribute>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.SPECIFICATIONATTRIBUTE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.specificationAttribute</code> attribute.
	 * @return the specificationAttribute
	 */
	public List<SiteOneProductSpecificationAttribute> getSpecificationAttribute(final Product item)
	{
		return getSpecificationAttribute( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationAttribute</code> attribute. 
	 * @param value the specificationAttribute
	 */
	public void setSpecificationAttribute(final SessionContext ctx, final Product item, final List<SiteOneProductSpecificationAttribute> value)
	{
		new PartOfHandler<List<SiteOneProductSpecificationAttribute>>()
		{
			@Override
			protected List<SiteOneProductSpecificationAttribute> doGetValue(final SessionContext ctx)
			{
				return getSpecificationAttribute( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductSpecificationAttribute> _value)
			{
				final List<SiteOneProductSpecificationAttribute> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.SPECIFICATIONATTRIBUTE,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.specificationAttribute</code> attribute. 
	 * @param value the specificationAttribute
	 */
	public void setSpecificationAttribute(final Product item, final List<SiteOneProductSpecificationAttribute> value)
	{
		setSpecificationAttribute( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.swatchImage</code> attribute.
	 * @return the swatchImage
	 */
	public List<SiteOneProductItemImages> getSwatchImage(final SessionContext ctx, final Product item)
	{
		List<SiteOneProductItemImages> coll = (List<SiteOneProductItemImages>)item.getProperty( ctx, SiteonepimintegrationConstants.Attributes.Product.SWATCHIMAGE);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Product.swatchImage</code> attribute.
	 * @return the swatchImage
	 */
	public List<SiteOneProductItemImages> getSwatchImage(final Product item)
	{
		return getSwatchImage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.swatchImage</code> attribute. 
	 * @param value the swatchImage
	 */
	public void setSwatchImage(final SessionContext ctx, final Product item, final List<SiteOneProductItemImages> value)
	{
		new PartOfHandler<List<SiteOneProductItemImages>>()
		{
			@Override
			protected List<SiteOneProductItemImages> doGetValue(final SessionContext ctx)
			{
				return getSwatchImage( ctx, item );
			}
			@Override
			protected void doSetValue(final SessionContext ctx, final List<SiteOneProductItemImages> _value)
			{
				final List<SiteOneProductItemImages> value = _value;
				item.setProperty(ctx, SiteonepimintegrationConstants.Attributes.Product.SWATCHIMAGE,value == null || !value.isEmpty() ? value : null );
			}
		}.setValue( ctx, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Product.swatchImage</code> attribute. 
	 * @param value the swatchImage
	 */
	public void setSwatchImage(final Product item, final List<SiteOneProductItemImages> value)
	{
		setSwatchImage( getSession().getSessionContext(), item, value );
	}
	
}
