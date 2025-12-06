package com.siteone.core.services.impl;

import com.siteone.core.cronjob.dao.AnonymousCartRemovalCronJobDao;
import com.siteone.core.services.SiteoneAnonymousCartRemovalCronJobService;

public class DefaultAnonymousCartRemovalCronJobService implements SiteoneAnonymousCartRemovalCronJobService {
    private AnonymousCartRemovalCronJobDao siteoneAnonymousCartRemovalCronJobDao;

    @Override
    public void removeObsoleteEmptyAnonymousCarts() {
        siteoneAnonymousCartRemovalCronJobDao.removeObsoleteEmptyAnonymousCarts();
    }

    public AnonymousCartRemovalCronJobDao getSiteoneAnonymousCartRemovalCronJobDao() {
        return siteoneAnonymousCartRemovalCronJobDao;
    }

    public void setSiteoneAnonymousCartRemovalCronJobDao(AnonymousCartRemovalCronJobDao siteoneAnonymousCartRemovalCronJobDao) {
        this.siteoneAnonymousCartRemovalCronJobDao = siteoneAnonymousCartRemovalCronJobDao;
    }


}
