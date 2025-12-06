def MAX_LOG_FILE_AGE_IN_MILLIS = 120*24 * 60 * 60 * 1000 
def deleteDate = System.currentTimeMillis() - MAX_LOG_FILE_AGE_IN_MILLIS  
def logDir = "/NFS_DATA/hybris-media/sys_master/cronjob"  
def logs = []  // build list of files 
new File(logDir).eachFileRecurse { 
file ->     if (file.isFile() && (file.lastModified() < deleteDate)) {         
	logs << file     } 
}  // sort by last modified (ascending) 
def sortedLogs = logs.toSorted { a,b -> a.lastModified() <=> b.lastModified() }  // N most recent are skipped  
def n = 5 
def delFiles = sortedLogs.dropRight(n)  // delete remaining 
delFiles.each { 
delFile ->     println "deleting File $delFile.absolutePath"     
delFile.delete() 
}
logs = []
new File(logDir).eachFileRecurse { 
emptyFolder ->     if (emptyFolder.isDirectory() && emptyFolder.list().length == 0) {
	logs << emptyFolder     } 
}
def delEmptyFolders = logs
delEmptyFolders.each { 
delEmptyFolder ->     
def deleted = delEmptyFolder.delete() 
if  (deleted == true)
println "deleting Empty Folder $delEmptyFolder.absolutePath"    
}
