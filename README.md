
# API de Gerenciamento de Sensores IoT com Blockchain

## 1. Resumo do Projeto

Este projeto consiste em uma API RESTful desenvolvida em **Java com Spring Boot** que serve como um backend para interagir com um conjunto de **Smart Contracts** na blockchain Ethereum. A finalidade da API é gerenciar o ciclo de vida de dispositivos IoT (Sensores de Umidade, Movimento, Proximidade e Temperatura), utilizando a blockchain para garantir a autenticidade, integridade e rastreabilidade do registro desses dispositivos.

A arquitetura foi desenhada para ser modular e extensível, com uma clara separação de responsabilidades entre o controller, o serviço e a camada de interação com a blockchain.

## 2. Principais Funcionalidades

* **Registro de Dispositivos:** Permite que um administrador registre diferentes tipos de sensores na blockchain. Cada registro é uma transação que, se bem-sucedida, emite um evento com os detalhes do registro.
* **Autenticidade Verificável:** Fornece endpoints para verificar se um dispositivo, identificado por seu UID, está atualmente registrado e dentro do seu período de validade no contrato inteligente correspondente.
* **Tratamento de Erros Centralizado:** Implementa um sistema robusto de tratamento de exceções que retorna mensagens de erro padronizadas em JSON, com códigos de status HTTP semanticamente corretos (ex: 409 para conflitos, 403 para acesso negado).
* **Documentação Interativa:** Utiliza Swagger (OpenAPI 3) para gerar uma documentação de API interativa, facilitando testes e a integração com outras aplicações.

## 3. Arquitetura e Tecnologias Utilizadas

### Backend
* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3.x
* **Comunicação com Blockchain:** Web3j
* **Dependências:** Lombok, Spring Web, Swagger (springdoc-openapi)
* **Build:** Maven

### Blockchain
* **Linguagem do Contrato:** Solidity (^0.8.20)
* **Rede de Testes:** Ganache (Nó Ethereum local)
* **Padrão dos Contratos:** Cada tipo de sensor possui seu próprio contrato `Manager`, que lida com o registro e a verificação de autenticidade.

## 4. Endpoints da API

A API base está disponível em `http://localhost:8080`.

### 4.1. Registro de Sensores

Estes endpoints são usados para enviar uma transação e registrar um novo sensor.

* **Método:** `POST`
* **Paths:**
 * `/api/v1/sensor/register/humidity`
 * `/api/v1/sensor/register/motion`
 * `/api/v1/sensor/register/proximity`
 * `/api/v1/sensor/register/temperature`
* **Corpo da Requisição (`Request Body`):**
    ```json
    {
      "id": "sensor-h-001",
      "macAddress": "A1:B2:C3:D4:E5:F6"
    }
    ```
* **Resposta de Sucesso (200 OK):**
    ```json
    {
      "uid": "sensor-h-001",
      "macAddress": "A1:B2:C3:D4:E5:F6",
      "measurementType": "humidity",
      "registeredAt": "2025-08-10T00:30:00Z",
      "expiresAt": "2025-08-10T00:32:00Z"
    }
    ```
* **Respostas de Erro Comuns:**
 * **409 Conflict:** Se o dispositivo já estiver registrado.
 * **403 Forbidden:** Se a transação for enviada por uma conta que não é a de administrador.
 * **500 Internal Server Error:** Para outras falhas inesperadas.

### 4.2. Verificação de Autenticidade

Estes endpoints são usados para consultar o estado de um sensor na blockchain.

* **Método:** `GET`
* **Paths:**
 * `/api/v1/sensor/authentic/humidity/{uid}`
 * `/api/v1/sensor/authentic/motion/{uid}`
 * `/api/v1/sensor/authentic/proximity/{uid}`
 * `/api/v1/sensor/authentic/temperature/{uid}`
* **Parâmetro de URL:** `{uid}` é o identificador único do sensor a ser verificado.
* **Resposta de Sucesso (200 OK):**
    ```json
    {
      "isAuthentic": true
    }
    ```
* **Resposta de Erro Comum:**
 * **500 Internal Server Error:** Se o contrato indicar que o sensor não é autêntico (a `RuntimeException` é capturada).

## 5. Configuração e Execução

### Pré-requisitos
1.  Java JDK 21 ou superior.
2.  Maven 3.8 ou superior.
3.  Ganache ou outro cliente Ethereum local em execução.
4.  Web3j via terminal

### Passos
1.  **Implantar os Smart Contracts:** Realize o deploy dos quatro contratos `...Manager.sol` na sua instância do Ganache. Anote o endereço de cada contrato implantado e a chave privada da conta que utilizada para o deploy (a conta "admin").

2.  **Gerar os Wrappers:** Se os wrappers Java ainda não foram gerados, execute o seguinte comando para cada contrato para gerar os Wrappers a partir dos arquivos JSON dos contratos compilados.
```
web3j generate truffle --truffle-json=<LocalizaçãoArquivoJSON> --outputDir=<DiretórioDeDestino> --package=<NomeDoPacoteJavaDoProjeto>
```


3.  **Configurar a Aplicação:** Edite o arquivo `src/main/resources/application.properties` e preencha com os valores corretos:
    ```properties
    # URL do seu nó Ganache
    blockchain.node.url=[http://127.0.0.1:7545](http://127.0.0.1:7545)
    
    # Chave privada da conta que fez o deploy (NUNCA coloque em produção)
    blockchain.admin.private-key=SUA_CHAVE_PRIVADA_DA_CONTA_ADMIN_AQUI
    
    # Endereços dos contratos implantados
    blockchain.contract.humidity-address=ENDERECO_DO_CONTRATO_DE_UMIDADE
    blockchain.contract.motion-address=ENDERECO_DO_CONTRATO_DE_MOVIMENTO
    blockchain.contract.proximity-address=ENDERECO_DO_CONTRATO_DE_PROXIMIDADE
    blockchain.contract.temperature-address=ENDERECO_DO_CONTRATO_DE_TEMPERATURA
    
    # Configurações de Gas
    blockchain.gas.price=20000000000
    blockchain.gas.limit=3000000
    ```

4.  **Executar a API Via Maven:** Abra um terminal na raiz do projeto e execute o comando Maven:
    ```bash
    mvn spring-boot:run
    ```
5. **Executar a API Via Docker:** Abra um terminal na raiz do projeto e execute o comando para buildar a imagem Docker:
   ```bash
   docker build -t api_iot_sc .
   ```
   Para executar utilize o comando:
   ```bash
   docker run -p 8080:8080 --network host api_iot_sc:latest
   ```
