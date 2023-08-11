call mvn clean package
rmdir /Q /S ".\dist\"
md ".\dist\Open Wrestling Conversion Tool"
xcopy ".\target\openwrestling-1.0-SNAPSHOT.jar" ".\dist\Open Wrestling Conversion Tool" /y
rename  ".\dist\Open Wrestling Conversion Tool\openwrestling-1.0-SNAPSHOT.jar" "Open Wrestling Conversion Tool.jar"
xcopy ".\src\Open Wrestling Conversion Tool.bat" ".\dist\Open Wrestling Conversion Tool" /y
xcopy ".\src\Open Wrestling Conversion Tool (debug).bat" ".\dist\Open Wrestling Conversion Tool" /y
7z a -tzip .\dist\OpenWrestlingConversionTool.zip ".\dist\Open Wrestling Conversion Tool"