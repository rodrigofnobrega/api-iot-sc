package com.ufrn.ppgti.iot_sc_api.dtos;

import java.time.Instant;

/**
 * DTO padrão para respostas de erro da API.
 *
 * @param timestamp Data e hora em que o erro ocorreu.
 * @param status O código de status HTTP.
 * @param error Uma breve descrição do status HTTP (ex: "Bad Request").
 * @param message Uma mensagem clara e legível descrevendo o erro.
 * @param path O caminho do endpoint que foi chamado.
 */
public record ErrorResponseDto(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
}