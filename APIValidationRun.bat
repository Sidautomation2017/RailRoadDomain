set projectLocation=C:\MMCS_Workspace\Automation\MMCS_ISS_APITesting
cd %projectLocation%
mvn clean test -DsuiteXmlFile=pom.xml
pause
