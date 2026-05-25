@echo off
if exist lein.bat (
  set LEIN_CMD=lein.bat
) else (
  set LEIN_CMD=lein
)
call %LEIN_CMD% deps
if errorlevel 1 exit /b 1
call %LEIN_CMD% uberjar
if errorlevel 1 exit /b 1
echo.
echo Сборка завершена!
echo Запуск сервера: java -jar target\url-shortener-0.1.0-standalone.jar 8080
echo Запуск клиента: java -jar target\url-shortener-0.1.0-standalone.jar
