package com.ufrn.ppgti.iot_sc_api;

import com.ufrn.ppgti.iot_sc_api.contracts.HumiditySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.MotionSensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.ProximitySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.TemperatureSensorManager;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

@Service
@Slf4j
public class DeviceService {
    private final HumiditySensorManager humiditySensorManager;
    private final MotionSensorManager motionSensorManager;
    private final ProximitySensorManager proximitySensorManager;
    private final TemperatureSensorManager temperatureSensorManager;

    public DeviceService(HumiditySensorManager humiditySensorManager,
                         MotionSensorManager motionSensorManager,
                         ProximitySensorManager proximitySensorManager,
                         TemperatureSensorManager temperatureSensorManager) {
        this.humiditySensorManager = humiditySensorManager;
        this.motionSensorManager = motionSensorManager;
        this.proximitySensorManager = proximitySensorManager;
        this.temperatureSensorManager = temperatureSensorManager;
    }

    // --- REGISTRAR SENSOR DE HUMIDADE ---
    public SensorRegisterResponseDto registerHumiditySensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor UID: {}", sensorRegisterDto.id());

        // Envia os dados para a blockchain
        TransactionReceipt transactionReceipt = humiditySensorManager
                .registerHumiditySensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                .send();

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro! Status: " + transactionReceipt.getStatus());
        }

        log.info("Dispositivo registrado com sucesso! Hash da Transação: {}", transactionReceipt.getTransactionHash());

        // --- CAPTURANDO O EVENTO GERADO PELO CONTRATO INTELIGENTE ---

        // 2. Use o método gerado pelo wrapper para extrair os eventos do recibo
        List<HumiditySensorManager.SensorRegisteredEventResponse> events = humiditySensorManager.getSensorRegisteredEvents(transactionReceipt);

        // 3. Verifique se o evento foi encontrado e processe os dados
        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de movimento não encontrado!");
        }
        HumiditySensorManager.SensorRegisteredEventResponse eventResponse = events.get(0); // Pega o primeiro evento da lista

        // Convertendo Timestamps vêm como BigInteger (uint256) para Instant
        java.time.Instant registeredAt = java.time.Instant.ofEpochSecond(eventResponse.registeredAt.longValue());
        java.time.Instant expiresAt = java.time.Instant.ofEpochSecond(eventResponse.expiresAt.longValue());


        log.info("Evento SensorRegistered capturado:");
        log.info("  -> UID: {}", eventResponse.uid);
        log.info("  -> MAC Address: {}", eventResponse.macAddress);
        log.info("  -> Tipo de Medida: {}", eventResponse.measurementType);
        log.info("  -> Registrado em: {}", registeredAt);
        log.info("  -> Expira em: {}", expiresAt);

        return new SensorRegisterResponseDto(eventResponse.uid, eventResponse.macAddress, eventResponse.measurementType,
                registeredAt,expiresAt);
    }

    // --- REGISTRAR SENSOR DE MOVIMENTO ---
    public SensorRegisterResponseDto registerMotionSensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de MOVIMENTO UID: {}", sensorRegisterDto.id());

        // Envia os dados para a blockchain
        TransactionReceipt transactionReceipt = motionSensorManager
                .registerMotionSensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                .send();

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro de movimento! Status: " + transactionReceipt.getStatus());
        }

        // --- CAPTURANDO O EVENTO GERADO PELO CONTRATO INTELIGENTE ---
        // 2. Use o método gerado pelo wrapper para extrair os eventos do recibo
        List<MotionSensorManager.SensorRegisteredEventResponse> events = motionSensorManager.getSensorRegisteredEvents(transactionReceipt);

        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de movimento não encontrado!");
        }

        MotionSensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        // Convertendo Timestamps vêm como BigInteger (uint256) para Instant
        java.time.Instant registeredAt = java.time.Instant.ofEpochSecond(eventResponse.registeredAt.longValue());
        java.time.Instant expiresAt = java.time.Instant.ofEpochSecond(eventResponse.expiresAt.longValue());


        log.info("Evento SensorRegistered capturado:");
        log.info("  -> UID: {}", eventResponse.uid);
        log.info("  -> MAC Address: {}", eventResponse.macAddress);
        log.info("  -> Tipo de Medida: {}", eventResponse.measurementType);
        log.info("  -> Registrado em: {}", registeredAt);
        log.info("  -> Expira em: {}", expiresAt);

        return new SensorRegisterResponseDto(
                eventResponse.uid, eventResponse.macAddress, eventResponse.measurementType, registeredAt, expiresAt
        );
    }

    // --- REGISTRAR SENSOR DE PROXIMIDADE ---
    public SensorRegisterResponseDto registerProximitySensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de PROXIMIDADE UID: {}", sensorRegisterDto.id());

        TransactionReceipt transactionReceipt = proximitySensorManager
                .registerProximitySensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                .send();

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro de proximidade! Status: " + transactionReceipt.getStatus());
        }

        // --- CAPTURANDO O EVENTO GERADO PELO CONTRATO INTELIGENTE ---
        // 2. Use o método gerado pelo wrapper para extrair os eventos do recibo
        List<ProximitySensorManager.SensorRegisteredEventResponse> events = proximitySensorManager.getSensorRegisteredEvents(transactionReceipt);

        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de proximidade não encontrado!");
        }

        ProximitySensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        // Convertendo Timestamps vêm como BigInteger (uint256) para Instant
        java.time.Instant registeredAt = java.time.Instant.ofEpochSecond(eventResponse.registeredAt.longValue());
        java.time.Instant expiresAt = java.time.Instant.ofEpochSecond(eventResponse.expiresAt.longValue());

        return new SensorRegisterResponseDto(
                eventResponse.uid, eventResponse.macAddress, eventResponse.measurementType, registeredAt, expiresAt
        );
    }

    // --- REGISTRAR SENSOR DE TEMPERATURA ---
    public SensorRegisterResponseDto registerTemperatureSensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de TEMPERATURA UID: {}", sensorRegisterDto.id());

        TransactionReceipt transactionReceipt = temperatureSensorManager
                .registerTemperatureSensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                .send();

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro de temperatura! Status: " + transactionReceipt.getStatus());
        }

        // --- CAPTURANDO O EVENTO GERADO PELO CONTRATO INTELIGENTE ---
        // 2. Use o método gerado pelo wrapper para extrair os eventos do recibo
        List<TemperatureSensorManager.SensorRegisteredEventResponse> events = temperatureSensorManager.getSensorRegisteredEvents(transactionReceipt);

        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de temperatura não encontrado!");
        }

        TemperatureSensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        // Convertendo Timestamps vêm como BigInteger (uint256) para Instant
        java.time.Instant registeredAt = java.time.Instant.ofEpochSecond(eventResponse.registeredAt.longValue());
        java.time.Instant expiresAt = java.time.Instant.ofEpochSecond(eventResponse.expiresAt.longValue());

        return new SensorRegisterResponseDto(
                eventResponse.uid, eventResponse.macAddress, eventResponse.measurementType, registeredAt, expiresAt
        );
    }
}
