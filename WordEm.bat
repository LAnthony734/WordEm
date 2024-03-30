:: This batch file creates and runs a .jar of all byte code in the "wordem" package.
@echo off
jar cfm WordEm.jar manifest.txt -C ./bin wordem
start javaw -jar -Dsun.java2d.uiScale=1.0 .\WordEm.jar