package com.ufrn.ppgti.iot_sc_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocsOpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                // Título reflete o propósito central: gerenciar e autenticar dispositivos IoT via Blockchain.
                                .title("API para Gerenciamento e Autenticação de Dispositivos IoT em Blockchain")
                                // Descrição detalha a função da API como ponte para o smart contract e seus objetivos.
                                .description("API RESTful que serve como interface para um smart contract na blockchain, " +
                                        "destinada ao registro e à verificação de autenticidade de dispositivos IoT. " +
                                        "O sistema garante a integridade e o controle de acesso dos dispositivos conectados, " +
                                        "permitindo que apenas entidades autorizadas registrem novos dispositivos e que " +
                                        "a autenticidade de qualquer dispositivo seja validada contra o registro imutável da blockchain.")
                                .version("v1")
                );
    }
}