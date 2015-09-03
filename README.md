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

### Execução

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
