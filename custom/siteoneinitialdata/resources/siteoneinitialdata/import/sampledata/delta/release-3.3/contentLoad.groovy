import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource
import de.hybris.platform.servicelayer.impex.ImportConfig

def importService = spring.getBean("importService")
def mediaService = spring.getBean("mediaService")


def importCSVFromResources(file) {
    final def config = new ImportConfig()
    config.setScript(new StreamBasedImpExResource(file.newInputStream(), "utf-8"))
    config.setEnableCodeExecution(true)
    // Now use Config object
    final def result = importService.importData(config)
    if (result.hasUnresolvedLines()) {
        println "***** " + file.name + " hasUnresolvedLines!!!"
        def importResults = new String(mediaService.getDataFromMedia(result.getUnresolvedLines()));
        println importResults
    }
    println "Import of " + file.name + " was successful: " + result.isSuccessful() + "    with error: " + result.isError()
}


def rootFolder = "/opt/hybris/bin/custom/siteoneinitialdata/resources/siteoneinitialdata/import/sampledata/delta/release-3.3/content"

def dir = new File(rootFolder)
dir.eachFile {
    println "Begin - " + it
    importCSVFromResources(it)
    println "End - " + it.name + "\n"
}