@SET basePath=%cd%
rem weblogic server EnvCmdConfig
@SET env="D:\Dev\Oracle\wls10\Middleware\user_projects\domains\base_domain\bin\setDomainEnv.cmd"
rem weblogic server WlstCmdConfig
@SET wlst="D:\Dev\Oracle\wls10\Middleware\wlserver_10.3\common\bin\wlst.cmd"
rem weblogic doCmd

call %env%
call %wlst% %basePath%\temp\config.py
cd /d %basePath%
