package com.siteone.pcm.task;

import java.io.File;

import org.apache.log4j.Logger;

import com.siteone.core.model.SiteOneFeedFileInfoModel;
import com.siteone.pcm.constants.SiteonepcmConstants;

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.HeaderSetupTask;
import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.model.ModelService;

public class SiteOneHeaderSetupTask extends HeaderSetupTask{
	
	private static final Logger LOG = Logger.getLogger(SiteOneHeaderSetupTask.class);
	
	private ModelService modelService;
	
	public BatchHeader execute(final File file) {

		try {
			// Insert file meta data information
			String feedType = "";
			if (file.getName().contains("_")) {
				SiteOneFeedFileInfoModel feedFileInfo = getModelService().create(SiteOneFeedFileInfoModel.class);
				feedType = file.getName().split("_")[0];
				feedFileInfo.setFileType(feedType);
				feedFileInfo.setFileName(file.getName());
				getModelService().save(feedFileInfo);
			}
		} catch (Exception ex) {
			LOG.error("Error occured while saving SiteOneFeedFileInfoMode fileName -" + file.getName(), ex);
		}
		return super.execute(file);
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService() {
		modelService = (ModelService) Registry.getApplicationContext().getBean(SiteonepcmConstants.MODELSERVICE);
		return this.modelService;
	}

	/**
	 * @param modelService the modelService to set
	 */
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

}
