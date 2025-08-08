package com.ufrn.ppgti.iot_sc_api;

import com.ufrn.ppgti.iot_sc_api.dtos.DeviceAuthenticateResponseDto;
import com.ufrn.ppgti.iot_sc_api.dtos.RegisterDeviceByCategoryDto;
import com.ufrn.ppgti.iot_sc_api.dtos.RegisterDeviceDto;
import com.ufrn.ppgti.iot_sc_api.dtos.RegisterDeviceResponseOkDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Registra um novo dispositivo IoT",
            description = "Recebe os dados de um novo dispositivo, os valida e envia uma transação para o smart contract para efetuar o registro na blockchain. Esta é uma operação sensível que deve ser realizada por um administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo registrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterDeviceResponseOkDto.class))
            )
    })
    @PostMapping("register")
    public ResponseEntity<RegisterDeviceResponseOkDto> registerDevice(@RequestBody RegisterDeviceDto registerDeviceDto) {
        log.info("Requisição para registrar novo dispositivo. ID: {}", registerDeviceDto.id());
        RegisterDeviceResponseOkDto response = deviceService.registerDevice(registerDeviceDto);
        log.info("Dispositivo registrado com sucesso. ID: {}", registerDeviceDto.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Registra um novo dispositivo IoT pela classe do dispositivo",
            description = "Registra um dispositivo associando-o a uma categoria pré-definida no smart contract (ex: 'sensor', 'atuador'). Simplifica o processo de registro para tipos de dispositivos comuns.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo registrado com sucesso na categoria especificada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterDeviceResponseOkDto.class))
            )
    })
    @PostMapping("register/category")
    public ResponseEntity<RegisterDeviceResponseOkDto> registerDeviceByCategory(
            @RequestBody RegisterDeviceByCategoryDto registerDeviceByCategoryDto) {
        log.info("Requisição para registrar por categoria. Categoria: {}, ID: {}",
                registerDeviceByCategoryDto.category(), registerDeviceByCategoryDto.id());
        RegisterDeviceResponseOkDto response = deviceService.registerDeviceByCategory(registerDeviceByCategoryDto);
        log.info("Dispositivo registrado com sucesso. ID: {}", registerDeviceByCategoryDto.id());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Verifica a autenticidade de um dispositivo",
            description = "Consulta a blockchain para verificar se um dispositivo com o ID especificado está registrado e é autêntico. É uma operação de leitura, rápida e de baixo custo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeviceAuthenticateResponseDto.class))
            )
    })
    @GetMapping("{id}/authenticate")
    public ResponseEntity<DeviceAuthenticateResponseDto> verifyDeviceAuthenticate(
            @Parameter(description = "Identificador único do dispositivo a ser verificado.")
            @PathVariable String id) {
        log.info("Requisição para verificar autenticidade do. ID: {}.", id);
        DeviceAuthenticateResponseDto response = deviceService.verifyDeviceAuthenticate(id);
        log.info("Verificação concluída. ID: {}, Status de autenticado: {}", id, response.authenticated());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}