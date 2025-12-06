/**
 *
 */
package com.siteone.core.cronjob.service;

import com.siteone.core.model.FindDuplicateSequenceCronJobModel;


/**
 * @author LR03818
 *
 */
public interface FindDuplicateSequenceCronJobService
{
	void findAndFixDuplicateSequence(FindDuplicateSequenceCronJobModel model);
}
