# Executando a Aplicação

## Via docker
1. **Construa a imagem da aplicação:** A partir da raiz do projeto, execute o comando abaixo para criar a imagem Docker. 
 ```docker build -t api_iot_sc . ```

3. **Execute o container:** 
Use o comando a seguir para iniciar um container com a aplicação.
```docker run --name api-iot -p 8080:8080 api_iot_sc:latest ```

## Via Maven
Utilize o comando abaixo para rodar a aplicação
Na raiz do projeto, utilize o wrapper do Maven para iniciar a aplicação. 
```./mvnw spring-boot:run ```

Após a inicialização, a aplicação estará disponível em `http://localhost:8080`.

