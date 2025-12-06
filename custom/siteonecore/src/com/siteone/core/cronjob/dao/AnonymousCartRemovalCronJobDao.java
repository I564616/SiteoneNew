package com.siteone.core.cronjob.dao;

public interface AnonymousCartRemovalCronJobDao {

    void removeObsoleteEmptyAnonymousCarts();
}
