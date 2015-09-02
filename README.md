# Aplicação de teste da API de gerenciamento nativa do Wildfly

## Pré-requisitos
* Extraia o wildfly-8.2.0.Final.zip. Configure e exporte a variável WILDFLY_HOME apontando-a para o local da extração.
* Instale o Maven.

## Geração dos fontes

```
./run generate
```

## Configurações (opcionais)

```
export ip=localhost
export username=fulano
export password=senha
```

## Inicialização do Wildfly 

```
./run wildfly-add-user
./run wildfly-start
```

## Aplicação de teste

### Empacotamento

```
./run package
```

### Deploy

```
./run wildfly-deploy
```

### Teste

#### No servidor

* Acesse a URL http://localhost:8080/wildfly-native-management-api-sample/

#### Local

```
./run test
```

## Finalização do Wildfly

```
./run wildfly-stop
```

## Limpeza

```
./run clean
```
