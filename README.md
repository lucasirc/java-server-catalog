Server Catalog
==
model: http://yuml.me/da68ba32

## Instalação

### Ambiente
       
#### Gradle
    ```
       $ curl -s get.gvmtool.net | bash
       $ gvm install gradle
       $ cd {root_project}
       $ gradle war
       # deploy {root_project}/build/libs/servercatalog-1.0.war no tomcat
    ```
    

## Aplicação

### Listar
```sh
curl -v  http://servercatalog-env.elasticbeanstalk.com/api/v1/applications
```
*Paginação*

```sh
curl -v  http://servercatalog-env.elasticbeanstalk.com/api/v1/applications?offset=4
```
*Paginação com limite*
```sh
curl -v  "http://servercatalog-env.elasticbeanstalk.com/api/v1/applications?offset=4&max=4"
```
### Criar
```sh
curl -v -d "{\"name\":\"game\", \"owner\": \"lucasirc@gmail.com\"}" -X POST -H "Content-Type: application/json" http://servercatalog-env.elasticbeanstalk.com/api/v1/applications
```

### Atualizar
```sh
curl -v -d "{\"name\":\"game 1\", \"owner\": \"lucasirc43@gmail.com\"}" -X PUT -H "Content-Type: application/json" http://servercatalog-env.elasticbeanstalk.com/api/v1/applications/{app_id}
```

### Ler
```
curl -v http://servercatalog-env.elasticbeanstalk.com/api/v1/applications/{app_id}
```
### Remover
```sh
curl -v -X DELETE http://servercatalog-env.elasticbeanstalk.com/api/v1/applications/{app_id}
```

## Servidores
### Listar
```sh
curl -v  http://servercatalog-env.elasticbeanstalk.com/api/v1/servers
```
*Paginação*
```sh
curl -v  http://servercatalog-env.elasticbeanstalk.com/api/v1/servers?offset=4
```
*Paginação com limite*
```sh
curl -v  "http://servercatalog-env.elasticbeanstalk.com/api/v1/servers?offset=4&max=4"
```
### Criar
```sh
curl -v -d "{\"hostname\":\"gameserver\"}" -X POST -H "Content-Type: application/json" http://servercatalog-env.elasticbeanstalk.com/api/v1/servers
```
### Atualizar
```sh
curl -v -d "{\"hostname\":\"gameserver 123\"}" -X PUT -H "Content-Type: application/json" http://servercatalog-env.elasticbeanstalk.com/api/v1/servers/{server_id}
```

### Ler
```
curl -v http://servercatalog-env.elasticbeanstalk.com/api/v1/servers/{server_id}
```
### Remover
```sh
curl -v -X DELETE http://servercatalog-env.elasticbeanstalk.com/api/v1/servers/{server_id}
```