@echo off
chcp 65001 >nul
setlocal

set "JDK_DIR=C:\Users\AISW-510-125\.jdks\ms-21.0.10"
cd /d "%~dp0"

if not exist ".build" mkdir ".build"

"%JDK_DIR%\bin\javac.exe" -encoding UTF-8 -d ".build" Student.java StudentManager.java Main.java
if errorlevel 1 (
    echo.
    echo Java compilation failed.
    pause
    exit /b 1
)

"%JDK_DIR%\bin\java.exe" -cp ".build" Main

endlocal
