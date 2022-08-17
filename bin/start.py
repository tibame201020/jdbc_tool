import json
import subprocess


with open('server.json') as serverConfig:
    serverData = json.load(serverConfig)
    adminUsername=serverData['adminUsername']
    adminPassword=serverData['adminPassword']
    adminURL=serverData['adminURL']



with open('data.json') as f:
    data = json.load(f)

for i in data:
    name=i['name']
    jndiName=i['jndiName']
    dsURL=i['dsURL']
    user=i['user']
    password=i['password']
    target=i['target']
    subprocess.call([r'startCopy.bat'])
    
    f = open("temp\config.py", "r")
    strs = f.readline().replace('weblogic',adminUsername)
    strs = strs + f.readline().replace('weblogic123',adminPassword)
    strs = strs + f.readline().replace('t3://localhost:7001',adminURL)
    strs = strs + f.readline().replace('CoreDSName',name)
    strs = strs + f.readline().replace('etestnndiName',jndiName)
    strs = strs + f.readline().replace('jdbc:oracle:thin:@host:port/dbname',dsURL)
    strs = strs + f.readline().replace('dbUser',user)
    strs = strs + f.readline().replace('dbPwd',password)
    strs = strs + f.readline().replace('AdminServer',target)
    print(strs)
    line = f.readline()
 
    while line:
        line = f.readline()
        strs = strs + line
    f.close()
    
    w = open("temp\config.py", "w")
    w.writelines(strs)
    w.close()
    
    subprocess.call([r'jdbc.bat'])