@echo off
REM ---------------------------------------------------------------
REM  Sports Manager - launcher
REM
REM  Requirements on the host computer:
REM    * A recent JRE or JDK (Java 21 or newer) on the PATH
REM      so that "java" can be invoked from a command prompt.
REM
REM  Everything else (JavaFX, SQLite JDBC, ...) is shipped inside
REM  the target\lib folder produced by  "mvn clean package".
REM ---------------------------------------------------------------

REM Move to the directory where this BAT file lives so the
REM relative paths below resolve correctly no matter where the
REM script is launched from.
cd /d "%~dp0"

REM If the project hasn't been built yet, build it first.
if not exist "target\Sports-Manager-1.0-SNAPSHOT.jar" (
    echo Building the project with Maven...
    call mvn -q clean package
    if errorlevel 1 (
        echo.
        echo Maven build failed. Please check the errors above.
        pause
        exit /b 1
    )
)

REM Run the JAR. JavaFX modules are loaded from target\lib via the
REM module path, so no separate JavaFX SDK installation is needed.
java ^
    --module-path "target\lib" ^
    --add-modules javafx.controls,javafx.fxml,javafx.media ^
    -jar "target\Sports-Manager-1.0-SNAPSHOT.jar"

if errorlevel 1 (
    echo.
    echo The application exited with an error.
    pause
)
