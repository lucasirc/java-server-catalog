Server Catalog
==
Modelo:

![Alt text](http://yuml.me/da68ba32 "Modelo")

# Documentação

A especifiação da API está em RAML, foi criado um portal (através da Mulesoft) da API  para testes, para acessar basta [ clicar aqui](https://anypoint.mulesoft.com/apiplatform/lucasirc/#/portals/apis/14780/versions/15576/pages/18056). Também, abaixo está listado como utilizar a API com curl.

## Aplicação (Model Application)

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

## Servidores (Model Server)

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


## Instalação

### Ambiente

Alterar as configurações de banco no arquivo config-DEV.properties (em src/main/java/resources), para rodar a aplicação local. Em produção adicionar a Variável de Ambiente ENV = PROD, ou a propriedade de aplicação ENV = PROD e configurar o config-PROD.properties
       
#### Gerar pacote

Instalar o gradle através do gvm e gerar o war. fazer deploy do war em um container.

    ```
       $ curl -s get.gvmtool.net | bash
       $ gvm install gradle
       $ cd {root_project}
       $ gradle war
       # deploy {root_project}/build/libs/servercatalog-1.0.war no tomcat
    ```

