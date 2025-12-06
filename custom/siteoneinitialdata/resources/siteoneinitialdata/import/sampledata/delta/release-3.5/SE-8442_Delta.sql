# initial cleanup
delete FROM cronjobs WHERE ( PK   not in (SELECT  p_cronjob  FROM triggerscj WHERE ( p_cronjob  is not null) ) and  p_endtime  <= to_date('2020-05-15', 'YYYY-MM-DD')) 
