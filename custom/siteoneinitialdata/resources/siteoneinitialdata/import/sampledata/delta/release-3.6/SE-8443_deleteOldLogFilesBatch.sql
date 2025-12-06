delete FROM joblogs  WHERE ( p_cronjob  in (SELECT pk FROM cronjobs)) and    DAYS_BETWEEN (TO_TIMESTAMP ( createdTs ), TO_TIMESTAMP (NOW())) > 18 and not pk in (
SELECT pk
FROM (
    SELECT pk,createdTs,p_cronjob, ROW_NUMBER() 
      OVER (Partition BY p_cronjob
            ORDER BY createdTS DESC ) AS NotToDeleteRecords 
    FROM joblogs  where  DAYS_BETWEEN (TO_TIMESTAMP ( createdTs ), TO_TIMESTAMP (NOW())) > 18 and  p_cronjob  in (SELECT pk FROM cronjobs)
    ) rs WHERE NotToDeleteRecords <= 5 ) 

delete FROM medias  WHERE ( OwnerPkString  in (SELECT pk FROM cronjobs)) and   DAYS_BETWEEN (TO_TIMESTAMP ( createdTs ), TO_TIMESTAMP (NOW())) > 18 and not pk in (
SELECT pk
FROM (
    SELECT pk,createdTs,OwnerPkString, ROW_NUMBER() 
      OVER (Partition BY OwnerPkString
            ORDER BY createdTS DESC ) AS NotToDeleteRecords
    FROM medias  where DAYS_BETWEEN (TO_TIMESTAMP ( createdTs ), TO_TIMESTAMP (NOW())) > 18   and  OwnerPkString  in (SELECT pk FROM cronjobs)
    ) rs WHERE NotToDeleteRecords <= 5  ) 

