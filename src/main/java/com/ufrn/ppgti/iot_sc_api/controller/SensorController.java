package com.ufrn.ppgti.iot_sc_api.controller;

import com.ufrn.ppgti.iot_sc_api.service.SensorService;
import com.ufrn.ppgti.iot_sc_api.dtos.ErrorResponseDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorAuthenticResponseDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sensor", description = "Endpoints para registro e autenticação de sensores de dispositivos IoT")
@RestController
@RequestMapping("/api/v1/sensor/")
@Slf4j
public class SensorController {
    @Autowired
    private SensorService sensorService;



    // --- ENDPOINTS DE REGISTRO ---

    @Operation(summary = "Registra um novo sensor de umidade",
            description = "Envia uma transação para o smart contract de umidade para registrar um novo dispositivo. Requer permissão de administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor registrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflito: Dispositivo já foi registrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso Negado: Somente admin pode realizar essa operação.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição Inválida: Erro de lógica no contrato.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/register/humidity")
    public ResponseEntity<SensorRegisterResponseDto> registerHumiditySensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        SensorRegisterResponseDto response = sensorService.registerHumiditySensor(sensorRegisterDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um novo sensor de movimento",
            description = "Envia uma transação para o smart contract de movimento para registrar um novo dispositivo. Requer permissão de administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor registrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflito: Dispositivo já foi registrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso Negado: Somente admin pode realizar essa operação.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição Inválida: Erro de lógica no contrato.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/register/motion")
    public ResponseEntity<SensorRegisterResponseDto> registerMotionSensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        SensorRegisterResponseDto response = sensorService.registerMotionSensor(sensorRegisterDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um novo sensor de proximidade",
            description = "Envia uma transação para o smart contract de proximidade para registrar um novo dispositivo. Requer permissão de administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor registrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflito: Dispositivo já foi registrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso Negado: Somente admin pode realizar essa operação.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição Inválida: Erro de lógica no contrato.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/register/proximity")
    public ResponseEntity<SensorRegisterResponseDto> registerProximitySensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        SensorRegisterResponseDto response = sensorService.registerProximitySensor(sensorRegisterDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um novo sensor de temperatura",
            description = "Envia uma transação para o smart contract de temperatura para registrar um novo dispositivo. Requer permissão de administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor registrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflito: Dispositivo já foi registrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso Negado: Somente admin pode realizar essa operação.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição Inválida: Erro de lógica no contrato.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/register/temperature")
    public ResponseEntity<SensorRegisterResponseDto> registerTemperatureSensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        SensorRegisterResponseDto response = sensorService.registerTemperatureSensor(sensorRegisterDto);
        return ResponseEntity.ok(response);
    }


    // --- ENDPOINTS DE VERIFICAÇÃO DE AUTENTICIDADE ---

    @Operation(summary = "Verifica a autenticidade de um sensor de umidade",
            description = "Consulta o smart contract para verificar se um sensor de umidade (pelo seu UID) é autêntico e válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor é autêntico",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorAuthenticResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor (ex: sensor não autenticado ou outro erro inesperado)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/authentic/humidity/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isHumiditySensorAuthentic(@PathVariable String uid) throws Exception {
        SensorAuthenticResponseDto response = sensorService.isHumiditySensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Verifica a autenticidade de um sensor de movimento",
            description = "Consulta o smart contract para verificar se um sensor de movimento (pelo seu UID) é autêntico e válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor é autêntico",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorAuthenticResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor (ex: sensor não autenticado ou outro erro inesperado)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/authentic/motion/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isMotionSensorAuthentic(@PathVariable String uid) throws Exception {
        SensorAuthenticResponseDto response = sensorService.isMotionSensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Verifica a autenticidade de um sensor de proximidade",
            description = "Consulta o smart contract para verificar se um sensor de proximidade (pelo seu UID) é autêntico e válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor é autêntico",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorAuthenticResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/authentic/proximity/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isProximitySensorAuthentic(@PathVariable String uid) throws Exception {
        SensorAuthenticResponseDto response = sensorService.isProximitySensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Verifica a autenticidade de um sensor de temperatura",
            description = "Consulta o smart contract para verificar se um sensor de temperatura (pelo seu UID) é autêntico e válido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor é autêntico",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorAuthenticResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro Interno do Servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/authentic/temperature/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isTemperatureSensorAuthentic(@PathVariable String uid) throws Exception {
        SensorAuthenticResponseDto response = sensorService.isTemperatureSensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }
}
