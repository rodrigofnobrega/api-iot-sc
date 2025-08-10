package com.ufrn.ppgti.iot_sc_api.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufrn.ppgti.iot_sc_api.dtos.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.web3j.protocol.exceptions.TransactionException;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("Erro inesperado de RuntimeException: {}", ex.getMessage(), ex); // Loga o erro completo com stack trace

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<ErrorResponseDto> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        log.error("Erro de estado ilegal: {}", ex.getMessage(), ex);

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TransactionException.class)
    public final ResponseEntity<ErrorResponseDto> handleTransactionException(TransactionException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = "A transação foi revertida pelo smart contract.";

        try {
            Map<String, Object> details = new ObjectMapper().readValue(ex.getMessage(), new TypeReference<>() {});
            String reason = (String) details.get("reason");

            if (reason != null && !reason.isBlank()) {
                errorMessage = reason;

                switch (reason) {
                    case "Device already registered":
                        status = HttpStatus.CONFLICT; // 409: Conflito, o recurso já existe
                        errorMessage = "Dispositivo já foi registrado.";
                        break;
                    case "Only admin can perform this action.":
                        status = HttpStatus.FORBIDDEN; // 403: Proibido, o usuário não tem permissão
                        errorMessage = "Somente admin pode realizar essa operação.";
                        break;
                    // Adicione outros casos para diferentes 'require' do seu contrato
                    default:
                        status = HttpStatus.BAD_REQUEST; // 400: Requisição inválida para outros erros de lógica
                        errorMessage = "Ocorreu um erro desconhecido.";
                        break;
                }
            }
        } catch (Exception parseException) {
            log.error("Não foi possível analisar a mensagem da TransactionException: {}", ex.getMessage(), parseException);
            errorMessage = "Ocorreu um erro durante a execução da transação na blockchain.";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.error("Erro na transação com a blockchain: Status {}, Motivo: {}", status, errorMessage);

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                errorMessage,
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Uma exceção genérica ocorreu: {}", ex.getMessage(), ex);

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocorreu um erro inesperado",
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}