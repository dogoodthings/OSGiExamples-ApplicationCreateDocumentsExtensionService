# SAP ECTR OSGi Example - ApplicationFileHandler Service

Setup project in eclipse:

1. File - Import - Maven - Checkout Maven Projects from SCM
2. SCM URL: git, https://github.com/dogoodthings/OSGiExamples-ApplicationCreateDocumentsExtensionService.git
3. wait a while :P
4. open pom.xml and check the property ectr.installation.directory
5. locate your ECTR installation ( in most cases it is C:\Program Files (x86)\SAP\ECTR )
6. edit pom.xml and set the property ectr.installation.directory to this path
7. (do any meaningful changes to the source code)
8. right click on the project - run as - maven - goals: clean package - hit "run"
9. take the generated jar from target folder: OSGi-Examples-ImportOnDemand-1.0.0.jar
10. goto ECTR installation, create a folder(s) OSGi-Examples\basis\plugins inside <ectr_inst_dir>\addons  (mkdir "C:\Program Files (x86)\SAP\ECTR\addons\OSGi-Examples\basis\plugins")
11. put OSGi-Examples-ImportOnDemand-1.0.0.jar inside OSGi-Examples\basis\plugins
12. start ECTR, start some CAD, create some parts in CAD and then start "import on demand" from cad integration
13. done.
14. note - you need at least ECTR version 5.2.0.0 to get the example code to work
