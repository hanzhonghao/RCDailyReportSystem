title 第二步 启动youyaboot管理后台 重启需要关掉已经启动过的实例

@call redis.bat

set JAVA_HOME=%CD%\..\..\jdk\jdk1.8.0_45
set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;
cd %CD%\youyaboot-admin\target

java -jar youyaboot-admin.jar --spring.profiles.active=localhost

pause
