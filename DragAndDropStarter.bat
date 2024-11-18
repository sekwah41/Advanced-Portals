REM Use this file for testing cases for different versions, just drag the version you want to test the plugin in and it will start.
@ECHO OFF
:A
java -Xmx4096M -DIReallyKnowWhatIAmDoingISwear=true -jar %1
REM Could add a 32 bit test part but noone uses 32 bit anymore
GOTO A
PAUSE.
