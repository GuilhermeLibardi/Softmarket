@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  client startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and CLIENT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\client-0.1.jar;%APP_HOME%\lib\javafx-fxml-11.0.2-win.jar;%APP_HOME%\lib\javafx-controls-11.0.2-win.jar;%APP_HOME%\lib\javafx-controls-11.0.2.jar;%APP_HOME%\lib\javafx-graphics-11.0.2-win.jar;%APP_HOME%\lib\javafx-graphics-11.0.2.jar;%APP_HOME%\lib\javafx-base-11.0.2-win.jar;%APP_HOME%\lib\javafx-base-11.0.2.jar;%APP_HOME%\lib\jasperreports-6.7.1.jar;%APP_HOME%\lib\commons-digester-2.1.jar;%APP_HOME%\lib\commons-beanutils-1.9.3.jar;%APP_HOME%\lib\castor-xml-1.3.3.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\castor-core-1.3.3.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\itext-2.1.7.js6.jar;%APP_HOME%\lib\jfreechart-1.0.19.jar;%APP_HOME%\lib\jcommon-1.0.23.jar;%APP_HOME%\lib\ecj-4.4.2.jar;%APP_HOME%\lib\jackson-databind-2.9.5.jar;%APP_HOME%\lib\jackson-core-2.9.5.jar;%APP_HOME%\lib\jackson-annotations-2.9.5.jar;%APP_HOME%\lib\icu4j-57.1.jar;%APP_HOME%\lib\bcprov-jdk15on-1.52.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\stax-1.2.0.jar;%APP_HOME%\lib\stax-api-1.0-2.jar;%APP_HOME%\lib\stax-api-1.0.1.jar

@rem Execute client
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %CLIENT_OPTS%  -classpath "%CLASSPATH%" app.Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable CLIENT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%CLIENT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
