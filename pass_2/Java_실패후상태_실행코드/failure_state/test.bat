@echo off
setlocal
cd /d "%~dp0"
chcp 65001 >nul
where javac >nul 2>nul
if errorlevel 1 (
  echo JDK 17 or later is required. Check javac and PATH.
  pause
  exit /b 1
)
javac -encoding UTF-8 -d out Main.java
if errorlevel 1 (
  pause
  exit /b 1
)
java -Dfile.encoding=UTF-8 -cp out Main --test
pause
