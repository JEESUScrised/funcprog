@echo off
call lein deps
if errorlevel 1 exit /b 1
call lein uberjar
if errorlevel 1 exit /b 1
echo.
echo Сборка завершена!
echo Запуск сервера: java -jar target\url-shortener-0.1.0-standalone.jar 8080
echo Запуск клиента: java -jar target\url-shortener-0.1.0-standalone.jar
