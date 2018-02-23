def call(payloadDir, releaseVersion, stagingPath, lvVersion) {
   
   def controlFields = readProperties file: "control"
   def basePackageName = "${controlFields.get('Package')}"
   def packageName = basePackageName.replaceAll("\\{version\\}", "${lvVersion}")
   def nipmAppPath = "C:\\Program Files\\National Instruments\\NI Package Manager\\nipkg.exe"
   def controlFileText = readFile "control" // Read nipkg control file 

   def xmlFilePaths = bat( returnStdout: true, script: '@echo off & dir /s /b *.xml').trim()
   echo xmlFilePaths
   xmlFilePaths.each { xmlFilePath, index ->
      echo xmlFilePaths[index]
      File xmlFile = new File(xmlFilePath)
      println xmlFile.name.lastIndexOf('.').with {it != -1 ? file.name[0..<it] : file.name}  
   }
      
   // Replace {version} with current lvVersion.
   def newControlFileText = controlFileText.replaceAll("\\{version\\}", "${lvVersion}")

   echo "Building ${packageName} with control file attributes:"
   echo controlFileText

   def newStagingPath = stagingPath.replaceAll("\\{version\\}", "${lvVersion}")
   echo "Staging path: $newStagingPath"

   // Copy package payload to nipkg staging directory. 
   bat "(robocopy \"${payloadDir}\" \"nipkg\\${packageName}\\data\\${newStagingPath}\" /MIR /NFL /NDL /NJH /NJS /nc /ns /np) ^& exit 0"

   writeFile file: "nipkg\\${packageName}\\debian-binary", text: "2.0"      
   writeFile file: "nipkg\\${packageName}\\control\\control", text: newControlFileText

   // Build nipkg using NI Package Manager CLI pack command. 
   bat "\"${nipmAppPath}\" pack nipkg\\${packageName} ${payloadDir}" 

}

