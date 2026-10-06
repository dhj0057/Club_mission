@echo off
setlocal
cd /d "%~dp0"
chcp 65001 >nul
where java >nul 2>nul
if errorlevel 1 (
  echo Install JDK 17 or later and check PATH.
  pause
  exit /b 1
)
java -Dfile.encoding=UTF-8 Main.java --test
pause
