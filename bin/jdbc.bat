@SET basePath=%cd%
rem weblogic server EnvCmdConfig
@SET env="D:\Dev\Oracle\wls12\user_projects\domains\test_weblogic12\bin\setDomainEnv.cmd"
rem weblogic server WlstCmdConfig
@SET wlst="D:\Dev\Oracle\wls12\oracle_common\common\bin\wlst.cmd"
rem weblogic doCmd

call %env%
call %wlst% %basePath%\temp\config.py
cd /d %basePath%
