def call(seqPath, tsVersion) {
   echo "Running test $seqPath with TestStand $tsVersion"
   def seqEditorPath = "C:\\Program Files (x86)\\National Instruments\\TestStand ${tsVersion}\\Bin\\SeqEdit.exe"
   def sequencePath = "${WORKSPACE}\\${seqPath}"
   def nipkgFilePath = readFile file: "build_log"

   bat "C:\\github-release\\elevate-1.3.0-x86-64\\elevate.exe -k commonbuild\\resources\\installNipkg.bat \"${nipkgFilePath}\""
   bat "\"${seqEditorPath}\" /outputToStdIO /run MainSequence \"${sequencePath}\" /quit"
}