package com.ufrn.ppgti.iot_sc_api;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Device", description = "Endpoints para registro e autenticação de dispositivos IoT")
@RestController
@RequestMapping("/api/v1/device/")
@Slf4j
public class SensorController {
    @Autowired
    private SensorService sensorService;

    // --- REGISTRAR SENSOR DE MOVIMENTO ---
    @Operation(summary = "Registra um novo sensor de humidade",
            description = "Recebe os dados de um sensor dispositivo e envia uma transação para o smart contract para efetuar o registro na blockchain. Esta é uma operação sensível que deve ser realizada por um administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor registrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensorRegisterResponseDto.class))
            )
    })
    @PostMapping("register/humidity")
    public ResponseEntity<SensorRegisterResponseDto> registerHumiditySensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Requisição para registrar novo dispositivo. ID: {}", sensorRegisterDto.id());
        SensorRegisterResponseDto response = sensorService.registerHumiditySensor(sensorRegisterDto);
        log.info("Dispositivo registrado com sucesso. ID: {}", sensorRegisterDto.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // --- REGISTRAR SENSOR DE MOVIMENTO ---
    @Operation(summary = "Registra um novo sensor de movimento",
            description = "Recebe os dados de um sensor e envia uma transação para o smart contract de movimento para efetuar o registro na blockchain.")
    @ApiResponse(responseCode = "200", description = "Sensor de movimento registrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class)))
    @PostMapping("/register/motion")
    public ResponseEntity<SensorRegisterResponseDto> registerMotionSensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Requisição para registrar novo sensor de MOVIMENTO. ID: {}", sensorRegisterDto.id());
        SensorRegisterResponseDto response = sensorService.registerMotionSensor(sensorRegisterDto);
        log.info("Sensor de MOVIMENTO registrado com sucesso. ID: {}", sensorRegisterDto.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // --- REGISTRAR SENSOR DE PROXIMIDADE ---
    @Operation(summary = "Registra um novo sensor de proximidade",
            description = "Recebe os dados de um sensor e envia uma transação para o smart contract de proximidade para efetuar o registro na blockchain.")
    @ApiResponse(responseCode = "200", description = "Sensor de proximidade registrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class)))
    @PostMapping("/register/proximity")
    public ResponseEntity<SensorRegisterResponseDto> registerProximitySensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Requisição para registrar novo sensor de PROXIMIDADE. ID: {}", sensorRegisterDto.id());
        SensorRegisterResponseDto response = sensorService.registerProximitySensor(sensorRegisterDto);
        log.info("Sensor de PROXIMIDADE registrado com sucesso. ID: {}", sensorRegisterDto.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // --- REGISTRAR SENSOR DE TEMPERATURA ---
    @Operation(summary = "Registra um novo sensor de temperatura",
            description = "Recebe os dados de um sensor e envia uma transação para o smart contract de temperatura para efetuar o registro na blockchain.")
    @ApiResponse(responseCode = "200", description = "Sensor de temperatura registrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SensorRegisterResponseDto.class)))
    @PostMapping("/register/temperature")
    public ResponseEntity<SensorRegisterResponseDto> registerTemperatureSensor(@RequestBody SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Requisição para registrar novo sensor de TEMPERATURA. ID: {}", sensorRegisterDto.id());
        SensorRegisterResponseDto response = sensorService.registerTemperatureSensor(sensorRegisterDto);
        log.info("Sensor de TEMPERATURA registrado com sucesso. ID: {}", sensorRegisterDto.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // --- VERIFICAR AUTENTICAÇÃO SENSOR DE HUMIDADE ---
    @Operation(summary = "Verifica a autenticidade de um sensor de umidade",
            description = "Consulta o smart contract para verificar se um sensor de umidade (pelo seu UID) é autêntico e válido.")
    @ApiResponse(responseCode = "200", description = "Sensor é autêntico")
    @GetMapping("/authentic/humidity/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isHumiditySensorAuthentic(@PathVariable String uid) throws Exception {
        log.info("Requisição para verificar autenticidade do sensor de umidade: {}", uid);
        SensorAuthenticResponseDto response = sensorService.isHumiditySensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }

    // --- VERIFICAR AUTENTICAÇÃO SENSOR DE MOVIMENTO ---
    @Operation(summary = "Verifica a autenticidade de um sensor de movimento",
            description = "Consulta o smart contract para verificar se um sensor de movimento (pelo seu UID) é autêntico e válido.")
    @ApiResponse(responseCode = "200", description = "Sensor é autêntico")
    @GetMapping("/authentic/motion/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isMotionSensorAuthentic(@PathVariable String uid) throws Exception {
        log.info("Requisição para verificar autenticidade do sensor de movimento: {}", uid);
        SensorAuthenticResponseDto response = sensorService.isMotionSensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }

    // --- VERIFICAR AUTENTICAÇÃO SENSOR DE PROXIMIDADE ---
    @Operation(summary = "Verifica a autenticidade de um sensor de proximidade",
            description = "Consulta o smart contract para verificar se um sensor de proximidade (pelo seu UID) é autêntico e válido.")
    @ApiResponse(responseCode = "200", description = "Sensor é autêntico")
    @GetMapping("/authentic/proximity/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isProximitySensorAuthentic(@PathVariable String uid) throws Exception {
        log.info("Requisição para verificar autenticidade do sensor de proximidade: {}", uid);
        SensorAuthenticResponseDto response = sensorService.isProximitySensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }

    // --- VERIFICAR AUTENTICAÇÃO SENSOR DE TEMPERATURA ---
    @Operation(summary = "Verifica a autenticidade de um sensor de temperatura",
            description = "Consulta o smart contract para verificar se um sensor de temperatura (pelo seu UID) é autêntico e válido.")
    @ApiResponse(responseCode = "200", description = "Sensor é autêntico")
    @GetMapping("/authentic/temperature/{uid}")
    public ResponseEntity<SensorAuthenticResponseDto> isTemperatureSensorAuthentic(@PathVariable String uid) throws Exception {
        log.info("Requisição para verificar autenticidade do sensor de temperatura: {}", uid);
        SensorAuthenticResponseDto response = sensorService.isTemperatureSensorAuthentic(uid);
        return ResponseEntity.ok(response);
    }
}
