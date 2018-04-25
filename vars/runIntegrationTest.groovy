def call(seqPath, tsVersion) {
   echo "Running test $seqPath with TestStand $tsVersion"
   def seqEditorPath = "C:\\Program Files (x86)\\National Instruments\\TestStand ${tsVersion}\\Bin\\SeqEdit.exe"
   def sequencePath = "${WORKSPACE}\\${seqPath}"

   bat "\"${seqEditorPath}\" /outputToStdIO /run MainSequence \"${sequencePath}\" /quit"
}