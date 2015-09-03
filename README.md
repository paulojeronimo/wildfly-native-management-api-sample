# Aplicação de teste da API de gerenciamento nativa do Wildfly

## Pré-requisitos
Instale o Maven.

Extraia o wildfly-8.2.0.Final.zip. Configure e exporte a variável WILDFLY_HOME apontando-a para o local da extração. Exemplo:

```
curl -LOC - http://download.jboss.org/wildfly/8.2.0.Final/wildfly-8.2.0.Final.tar.gz
tar xvfz wildfly-8.2.0.Final.tar.gz
export WILDFLY_HOME=$PWD/wildfly-8.2.0.Final
```

## Geração dos fontes

```
./run generate
```

## Configuração do Wildfly

```
./run config
```

## Variáveis (utilizadas pelo script run e que você pode configurar)

1. ``WILDFLY_HOSTNAME``: nome (ou ip) do host em que o Wildfly fará o bind.
1. ``WILDFLY_PORT_OFFSET``: altera ``jboss.socket.binding.port-offset``.
1. ``WILDFLY_PORT``: porta de gerenciamento (usualmente, 9990).
1. ``WILDFLY_USERNAME``: nome do usuário com acesso a interface adminstrativa.
1. ``WILDFLY_PASSWORD``: senha do usuário com acesso a interface administrativa.

## Criação do usuário de administração do Wildfly

```
./run add-user
```

## Inicialização do Wildfly 

```
./run start
```

## Aplicação de teste

### Empacotamento

```
./run package
```

### Deploy

```
./run deploy
```

### Teste de execução

#### No servidor

Acesse a URL http://localhost:8080/wildfly-native-management-api-sample/.

#### Local

```
./run test
```

## Finalização do Wildfly

```
./run stop
```

## Limpeza

```
./run clean
```

## Passos para teste de execução da aplicação num Wildfly rodando no Docker (e Docker Machine)

### Criação da VM e configuração do shell para uso do Docker

```
docker-machine create --driver virtualbox dm1
export WILDFLY_HOSTNAME=$(docker-machine ip dm1)
eval "$(docker-machine env dm1)"
```

### Testes da aplicação

```
export WILDFLY_USERNAME=fulano
export WILDFLY_PASSWORD=senha
docker run --name wildfly -d -p 8080:8080 -p 9990:9990 jboss/wildfly:8.2.0.Final /opt/jboss/wildfly/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 -bmanagement 0.0.0.0
docker logs -f wildfly &
docker exec wildfly /opt/jboss/wildfly/bin/add-user.sh -s -u $WILDFLY_USERNAME -p $WILDFLY_PASSWORD
./run deploy
firefox http://$WILDFLY_HOSTNAME:9990 http://$WILDFLY_HOSTNAME:8080/wildfly-native-management-api-sample
./run test
WILDFLY_USERNAME=beltrano ./run test
docker exec wildfly /opt/jboss/wildfly/bin/jboss-cli.sh -c :shutdown
docker ps -a
docker rm wildfly
!-2
```

### Testes de backup e recuperação da imagem

```
docker save -o wildfly-8.2.0.Final.docker-image.tar jboss/wildfly:8.2.0.Final
du -hsc wildfly-8.2.0.Final.docker-image.tar
docker rmi jboss/wildfly:8.2.0.Final
docker images
docker load -i wildfly-8.2.0.Final.docker-image.tar 
!-2
```

### Destruição da VM

```
docker-machine rm dm1
```
