# API de Consulta e manutenção de Dados do Crédito Rural
> Dados relativos aos contratos com informações de Custeio por Região, UF e produto

A aplicação permite bucar e faz a manutenção de dados de crédito rural
obtidos através da API do Banco Central do Brasil

- [Fonte dos dados](https://olinda.bcb.gov.br/olinda/servico/SICOR/versao/v2/aplicacao#!/recursos/CusteioMunicipioProduto#eyJmb3JtdWxhcmlvIjp7IiRmb3JtYXQiOiJqc29uIiwiJHRvcCI6MTAwfX0=)
- [Documentação](https://olinda.bcb.gov.br/olinda/servico/SICOR/versao/v2/documentacao)

## Funcionalidades
Existem 9 endpoints na API para fazer a busca e a manutenção dos dados.

1. **Onboard**: Inseri os primeiros 1000 dados da API do Banco Central do Brasil no banco de dados Local.
2. **Pageable(Busca Paginada)**: O usuario consegue fazer uma busca por paginação, aonde ele informa o numero da pagina desejada
e quantos itens ele deseja que apareça por pagina.
3. **Filter(Busca com filtros)**: O Programa mostrara somente os contratos com os campos que for informado. 
Se nenhum campo for informado, é retornado todos os dados da API(GetAll).
4. **CusteioAno**: Mostrara o custeio total dos seguintes itens: SOJA, MILHO, FEIJÃO, TRIGO e CANA DE AÇUCAR
do ano que o usuario informar.
5. **GetByID(Busca com ID)**: Retornara somente o contrato com o ID que for informado.
6. **DeleteAll(Deletar todas as informações)**: Deletara todos os registros do Banco de dados local
7. **DeleteByID(Deletar por ID)**: Deletara somente o contrato do ID informado.
8. **Update(PUT)**: Atualizara os campos que o usuario passar no corpo da requisição
9. **Insert(POST)**: Adicionara um novo contrato no banco de dados com o corpo que o usuario informar.

## Instalação
Pré-requisitos: 
1. MySQL instalado e configurado adequadamente na maquina local
2. Uma IDE da linguagem JAVA a sua escolha

Configuração:
1. Clonar o repositório do Git para sua máquina local;
```shell
        https://github.ibm.com/Marcos-Albano/Projeto-Credito-Rural.git
```
2. Entrar no application-dev.properties
```shell
       src/main/resources/application-dev.properties
```

3. Colocar o Username e senha do seu Banco de Dados Local nos seguintes campos
 ```shell
        spring.datasource.username="Your usename"
        spring.datasource.password="Your Password"
```

4. Criar um Schema com o nome dados no seu MySQL local

## Versionamento 
- v0.0.1 - Testando Onboarding no Banco H2
- v0.0.2 - Onboarding funcionando no banco MySQL
- v0.0.3 - Tratamento de ObjectNotFound
- v0.0.4 - CRUD implemetnado e testado 
- v1.0 - Todos EndPoints implementados e funcionando
- v1.1 - Criada Exceções personalizadas, e todos os testes unitarios e de integração funcionando
- v1.02 - Codigo otimizado 


## Informações

**Criador**: Marcos Antônio Albano Júnior
**Email**: Marcos.Albano@ibm.com



