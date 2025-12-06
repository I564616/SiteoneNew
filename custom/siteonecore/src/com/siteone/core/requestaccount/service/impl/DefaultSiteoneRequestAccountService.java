/**
 *
 */
package com.siteone.core.requestaccount.service.impl;

import com.google.gson.Gson;
import com.siteone.commerceservices.dto.createCustomer.CreateCustomerResponseWsDTO;
import com.siteone.commerceservices.dto.createCustomer.SiteoneWsUpdateAccountInfoWsDTO;
import com.siteone.integration.constants.SiteoneintegrationConstants;
import com.siteone.integration.services.dataimport.SiteOneBlobDataImportService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.daos.CountryDao;
import de.hybris.platform.servicelayer.i18n.daos.RegionDao;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.siteone.core.model.SiteoneRequestAccountModel;
import com.siteone.core.requestaccount.service.SiteoneRequestAccountService;
import de.hybris.platform.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author SMondal
 *
 */
public class DefaultSiteoneRequestAccountService implements SiteoneRequestAccountService
{
	private ModelService modelService;
	private CountryDao countryDao;
	private RegionDao regionDao;

	/* The configuration service*/
	private ConfigurationService configurationService;

	/* The Blob dataImport service*/
	private SiteOneBlobDataImportService blobDataImportService;

	private static final Logger LOG = Logger.getLogger(DefaultSiteoneRequestAccountService.class);

	@Override
	public void saveSiteoneRequestAccountModel(final SiteoneRequestAccountModel siteoneRequestAccountModel)
	{
		getModelService().save(siteoneRequestAccountModel);
	}

	@Override
	public CountryModel getCountryByCountryCode(final String countryCode)
	{
		final List<CountryModel> countries = countryDao.findCountriesByCode(countryCode);
		return countries.get(0);

	}

	@Override
	public RegionModel getRegionByIsoCode(final CountryModel country, final String stateIsoCode)
	{
		final List<RegionModel> region = regionDao.findRegionsByCountryAndCode(country, stateIsoCode);
		return region.get(0);
	}

	@Override
	public void exportRealtimeLogFile(Object siteoneWsUpdateAccountInfoWsDTO, CreateCustomerResponseWsDTO response) {
		final String realTimeLogContainer= getConfigurationService().getConfiguration().getString(SiteoneintegrationConstants.REALTIME_ACCOUNT_LOG_TARGET_LOCATION);
		Gson gson = new Gson();
		File file = null;
		try {
			file = File.createTempFile(getFileName(siteoneWsUpdateAccountInfoWsDTO),".txt");
		} catch (IOException e)
		{
			LOG.error("Exception while createTempFile " + (Config.getString(SiteoneintegrationConstants.REALTIME_ACCOUNT_LOG_TARGET_LOCATION,
					StringUtils.EMPTY) + getFileName(siteoneWsUpdateAccountInfoWsDTO)), e);
		}

		try (FileWriter fileWriter = new FileWriter(file, true))
		{
			fileWriter.write(gson.toJson(response) + "\n");
			fileWriter.write(gson.toJson(siteoneWsUpdateAccountInfoWsDTO));
		} catch (IOException e)
		{
			LOG.error("Exception occurred writing log file " + (Config.getString(SiteoneintegrationConstants.REALTIME_ACCOUNT_LOG_TARGET_LOCATION,
					StringUtils.EMPTY) + getFileName(siteoneWsUpdateAccountInfoWsDTO)), e);
		}

		// Migration | Write Blob
		getBlobDataImportService().writeBlob(file,realTimeLogContainer);
	}

	private String getFileName(Object siteoneWsUpdateAccountInfoWsDTO) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy_hhmmss");
		String currentServerDate = dateFormat.format(cal.getTime());
		if(siteoneWsUpdateAccountInfoWsDTO instanceof SiteoneWsUpdateAccountInfoWsDTO) {
			return Config.getString("realtimeaccount.fileName", "realtimeaccountlog")
					+ SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate;
      }else {
      	return Config.getString("notifyquoteemail.fileName", "notifyquoteemaillog")
   				+ SiteoneintegrationConstants.SEPARATOR_UNDERSCORE + currentServerDate;
      }
		
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the countryDao
	 */
	public CountryDao getCountryDao()
	{
		return countryDao;
	}

	/**
	 * @param countryDao
	 *           the countryDao to set
	 */
	public void setCountryDao(final CountryDao countryDao)
	{
		this.countryDao = countryDao;
	}

	/**
	 * @return the regionDao
	 */
	public RegionDao getRegionDao()
	{
		return regionDao;
	}

	/**
	 * @param regionDao
	 *           the regionDao to set
	 */
	public void setRegionDao(final RegionDao regionDao)
	{
		this.regionDao = regionDao;
	}

	/**
	 * Getter method for configurationService
	 *
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	/**
	 * Setter method for  configurationService
	 *
	 * @param configurationService
	 *            the configurationService to set
	 */
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	/**
	 * Getter method for blobDataImportService
	 *
	 * @return the blobDataImportService
	 */
	public SiteOneBlobDataImportService getBlobDataImportService() {
		return blobDataImportService;
	}

	/**
	 * Setter method for  blobDataImportService
	 *
	 * @param blobDataImportService
	 *            the blobDataImportService to set
	 */
	public void setBlobDataImportService(SiteOneBlobDataImportService blobDataImportService) {
		this.blobDataImportService = blobDataImportService;
	}

}
