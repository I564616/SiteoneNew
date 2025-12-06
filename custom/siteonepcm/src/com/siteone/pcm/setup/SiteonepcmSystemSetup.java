/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 * you entered into with SAP.
 */

package com.siteone.pcm.setup;

import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.siteone.pcm.constants.SiteonepcmConstants;


@SystemSetup(extension = SiteonepcmConstants.EXTENSIONNAME)
public class SiteonepcmSystemSetup extends AbstractSystemSetup
{

	private static final Logger LOG = LoggerFactory.getLogger(SiteonepcmSystemSetup.class);
	
	public static final String IMPORT_LIGHTING = "importLighting";
	public static final String IMPORT_LANDSCAPE = "importlandscape";
	public static final String IMPORT_MAINTENANCE = "importMaintenance";
	public static final String IMPORT_IRRIGATION = "importIrrigation";
	public static final String IMPORT_HARDSCAPE = "importHardscape";
	public static final String IMPORT_NURSERY = "importNursery";
	public static final String IMPORT_SH_SCRIPTS = "importShScripts";
	public static final String IMPORT_PH_SH_MAPPING = "importPhShMapping";
	public static final String IMPORT_GENERIC = "importGeneric";
   public SiteonepcmSystemSetup()
	{

	}

	@SystemSetup(type = Type.PROJECT, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		final boolean importLighting = getBooleanSystemSetupParameter(context, IMPORT_LIGHTING);
		final boolean importLandscape = getBooleanSystemSetupParameter(context, IMPORT_LANDSCAPE);
		final boolean importMaintenance = getBooleanSystemSetupParameter(context, IMPORT_MAINTENANCE);
		final boolean importIrrigation = getBooleanSystemSetupParameter(context, IMPORT_IRRIGATION);
		final boolean importHardscape = getBooleanSystemSetupParameter(context, IMPORT_HARDSCAPE);
		final boolean importNursery = getBooleanSystemSetupParameter(context, IMPORT_NURSERY);
		final boolean importShScripts = getBooleanSystemSetupParameter(context, IMPORT_SH_SCRIPTS);
		final boolean importPhShMapping = getBooleanSystemSetupParameter(context, IMPORT_PH_SH_MAPPING);
		final boolean importGeneric = getBooleanSystemSetupParameter(context, IMPORT_GENERIC);

		
		LOG.info("============  Importing PCM core data ");
		if (importGeneric)
		{
		importImpexFile(context, "/impexsetup/coreAttributeLOV.impex");
		importImpexFile(context, "/impexsetup/impex_for_initial_setup.impex");
		importImpexFile(context, "/impexsetup/pcm_ui_components.impex");
		importImpexFile(context, "/impexsetup/VariantAttributeForAllCategories.csv");
		importImpexFile(context, "/impexsetup/BrandEnum.csv");
		}
		if (importLighting)
		{
		//Lighting Category	
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_UOM_Definition.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_PrimaryHierachy.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_ClassificationAttribute_a.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_LOV_b.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_ClassificationClass_c.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_ClassAttributeAssignment_d.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_Category_e.impex");
		importImpexFile(context, "/impexsetup/PH11_Lighting/PH11_Lighting_ClassificationClassMapping_f.impex");
		}
		if (importLandscape)
		{
		//Landscape Category
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_PrimaryHierachy.impex");
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_ClassificationAttribute_a.impex");
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_LOV_b.impex");
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_ClassificationClass_c.impex");
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_ClassAttributeAssignment_d.impex");
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_Category_e.impex");
		importImpexFile(context, "/impexsetup/PH12_Landscape/PH12_Landscape_ClassificationClassMapping_f.impex");
		}
		if (importMaintenance)
		{
		//Maintenance Category
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_PrimaryHierachy.impex");
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_ClassificationAttribute_a.impex");
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_LOV_b.impex");
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_ClassificationClass_c.impex");
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_ClassAttributeAssignment_d.impex");
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_Category_e.impex");
		importImpexFile(context, "/impexsetup/PH13_Maintenance/PH13_Maintenance_ClassificationClassMapping_f.impex");
		}
		if (importIrrigation)
		{
		//Irrigation Category
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_PrimaryHierachy.impex");
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_ClassificationAttribute_a.impex");
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_LOV_b.impex");
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_ClassificationClass_c.impex");
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_ClassAttributeAssignment_d.impex");
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_Category_e.impex");
		importImpexFile(context, "/impexsetup/PH14_Irrigation/PH14_Irrigation_ClassificationClassMapping_f.impex");
		}
		if (importHardscape)
		{
		//Hardscape Category
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_PrimaryHierachy.impex");
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_ClassificationAttribute_a.impex");
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_LOV_b.impex");
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_ClassificationClass_c.impex");
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_ClassAttributeAssignment_d.impex");
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_Category_e.impex");
		importImpexFile(context, "/impexsetup/PH15_Hardscape/PH15_Hardscape_ClassificationClassMapping_f.impex");
		}
		if (importNursery)
		{
		//Nursery Category
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_PrimaryHierachy.impex");
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_ClassificationAttribute_a.impex");
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_LOV_b.impex");
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_ClassificationClass_c.impex");
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_ClassAttributeAssignment_d.impex");
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_Category_e.impex");
		importImpexFile(context, "/impexsetup/PH16_Nursery/PH16_Nursery_ClassificationClassMapping_f.impex"); 
		}
		if (importShScripts)
		{
		//Sales Hierarchy Scripts
		importImpexFile(context, "/impexsetup/SH_Scripts/SH11_Lighting_SalesHierarchy.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/SH12_Landscape_SalesHierarchy.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/SH13_Maintenance_SalesHierarchy.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/SH14_Irrigation_SalesHierarchy.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/SH15_Hardscape_SalesHierarchy.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/SH16_Nursery_SalesHierarchy.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/SH17_Tools_SalesHierarchy.impex");
		}
		if (importPhShMapping)
		{
		//Sales Hierarchy Ph mapping Scripts
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH11_Lighting_PH_Mapping.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH12_Landscape_PH_Mapping.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH13_Maintenance_PH_Mapping.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH14_Irrigation_PH_Mapping.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH15_Hardscape_PH_Mapping.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH16_Nursery_PH_Mapping.impex");
		importImpexFile(context, "/impexsetup/SH_Scripts/PH_SH_Mapping/SH17_Tools_PH_Mapping.impex");
		}
	}

	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();
		params.add(createBooleanSystemSetupParameter(IMPORT_LIGHTING, "Import Lighting", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_LANDSCAPE, "Import Landscape", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_MAINTENANCE, "Import Maintenance", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_IRRIGATION, "Import Irrigation", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_HARDSCAPE, "Import Hardscape", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_NURSERY, "Import Nursery", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_SH_SCRIPTS, "Import Sh_Scripts", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_PH_SH_MAPPING, "Import Ph_Sh_Mapping", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_GENERIC, "Import Generic", true));
		
		
		return params;
	}

}
