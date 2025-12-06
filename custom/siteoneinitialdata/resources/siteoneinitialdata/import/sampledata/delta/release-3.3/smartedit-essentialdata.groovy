import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource
import de.hybris.platform.servicelayer.impex.ImportConfig

def importService = spring.getBean("importService")
def mediaService = spring.getBean("mediaService")

def importCSVFromResources(fileName) {
    final def config = new ImportConfig()
    config.setScript(new StreamBasedImpExResource(new File(fileName).newInputStream(), "utf-8"))
    config.setEnableCodeExecution(true)
    // Now use Config object
    final def result = importService.importData(config)
    if (result.hasUnresolvedLines()) {
        println "***** " + fileName + " hasUnresolvedLines!!!"
        def importResults = new String(mediaService.getDataFromMedia(result.getUnresolvedLines()));
        println importResults
    }
    println "Import of " + fileName + " was successful: " + result.isSuccessful() + "    with error: " + result.isError()
}


def rootFolder = "/opt/hybris/bin"


importCSVFromResources(rootFolder + "/modules/smartedit/smartedit/resources/impex/essentialdata_smartedit.impex");

importCSVFromResources(rootFolder + "/modules/smartedit/cmssmartedit/resources/impex/essentialdata_cmssmartedit.impex");

importCSVFromResources(rootFolder + "/modules/web-services-commons/permissionswebservices/resources/impex/essentialdata-permissionswebservices.impex");

importCSVFromResources(rootFolder + "/modules/web-content-management-system/previewwebservices/resources/impex/essentialdata-previewwebservices.impex");

importCSVFromResources(rootFolder + "/modules/smartedit/smarteditwebservices/resources/impex/essentialdata_smarteditwebservices.impex");

importCSVFromResources(rootFolder + "/modules/rule-engine/ruleengine/resources/ruleengine/import/essentialdata-mediafolder.impex");
