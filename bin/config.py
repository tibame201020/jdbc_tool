adminUsername="weblogic"
adminPassword="weblogic123"
adminURL="t3://localhost:7001"
dsName="CoreDSName"
dsJNDIName="etestnndiName"
dsURL="jdbc:oracle:thin:@host:port/dbname"
dsUsername="dbUser"
dsPassword="dbPwd"
dsTargetName="AdminServer"



print("*** Trying to Connect.... *****")
connect(adminUsername,adminPassword,adminURL)
print("*** Connected *****")

edit()
startEdit()

cd('/')
cmo.createJDBCSystemResource(dsName)

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName)
cmo.setName(dsName)

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDataSourceParams/' + dsName)
set('JNDINames',jarray.array([String(dsJNDIName)], String))

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName)
cmo.setUrl(dsURL)
cmo.setDriverName('oracle.jdbc.xa.client.OracleXADataSource')
en = encrypt(dsPassword)
set('PasswordEncrypted',en)


cd('/JDBCSystemResources/' + dsName+ '/JDBCResource/' + dsName + '/JDBCConnectionPoolParams/' + dsName)
cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n')

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName + '/Properties/' + dsName)
cmo.createProperty('user')

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDriverParams/' + dsName + '/Properties/' + dsName + '/Properties/user')
cmo.setValue(dsUsername)

cd('/JDBCSystemResources/' + dsName + '/JDBCResource/' + dsName + '/JDBCDataSourceParams/' + dsName)
cmo.setGlobalTransactionsProtocol('TwoPhaseCommit')

cd('/SystemResources/'+dsName)
set('Targets',jarray.array([ObjectName('com.bea:Name='+dsTargetName+',Type=Server')], ObjectName))

save()
# activate()

edit()
startEdit()
