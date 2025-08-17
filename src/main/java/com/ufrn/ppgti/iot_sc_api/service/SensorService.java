package com.ufrn.ppgti.iot_sc_api.service;

import com.ufrn.ppgti.iot_sc_api.contracts.HumiditySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.MotionSensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.ProximitySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.TemperatureSensorManager;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorAuthenticResponseDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class SensorService {
    private final Object adminTransactionLock = new Object();
    private final HumiditySensorManager humiditySensorManager;
    private final MotionSensorManager motionSensorManager;
    private final ProximitySensorManager proximitySensorManager;
    private final TemperatureSensorManager temperatureSensorManager;

    /**
     * Construtor para injeção de dependência dos wrappers de contrato.
     * O Spring injetará automaticamente os beans de cada gerenciador de contrato.
     *
     * @param humiditySensorManager    Wrapper do contrato inteligente para sensores de umidade.
     * @param motionSensorManager      Wrapper do contrato inteligente para sensores de movimento.
     * @param proximitySensorManager   Wrapper do contrato inteligente para sensores de proximidade.
     * @param temperatureSensorManager Wrapper do contrato inteligente para sensores de temperatura.
     */
    public SensorService(HumiditySensorManager humiditySensorManager,
                         MotionSensorManager motionSensorManager,
                         ProximitySensorManager proximitySensorManager,
                         TemperatureSensorManager temperatureSensorManager) {
        this.humiditySensorManager = humiditySensorManager;
        this.motionSensorManager = motionSensorManager;
        this.proximitySensorManager = proximitySensorManager;
        this.temperatureSensorManager = temperatureSensorManager;
    }

    /**
     * Registra um novo sensor de umidade na blockchain.
     * Envia uma transação para o smart contract e processa o evento 'SensorRegistered' resultante.
     *
     * @param sensorRegisterDto DTO contendo o ID e o endereço MAC do sensor a ser registrado.
     * @return Um DTO com os detalhes completos do registro, extraídos do evento do contrato.
     * @throws Exception Se a transação falhar ou o evento de registro não for encontrado.
     */
    public SensorRegisterResponseDto registerHumiditySensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de UMIDADE UID: {}", sensorRegisterDto.id());
        TransactionReceipt transactionReceipt;

        synchronized (adminTransactionLock) {
            log.debug("Adquiriu o lock para enviar transação de umidade.");
            transactionReceipt = humiditySensorManager
                    .registerHumiditySensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                    .send();
            log.debug("Liberou o lock após enviar transação de umidade.");
        }

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro! Status: " + transactionReceipt.getStatus());
        }

        log.info("Dispositivo de UMIDADE registrado com sucesso! Hash da Transação: {}", transactionReceipt.getTransactionHash());

        List<HumiditySensorManager.SensorRegisteredEventResponse> events = humiditySensorManager.getSensorRegisteredEvents(transactionReceipt);

        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de umidade não encontrado!");
        }

        HumiditySensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        return formatAndLogEventResponse(
                eventResponse.uid,
                eventResponse.macAddress,
                eventResponse.measurementType,
                eventResponse.registeredAt,
                eventResponse.expiresAt
        );
    }

    /**
     * Registra um novo sensor de movimento na blockchain.
     * Envia uma transação para o smart contract e processa o evento 'MotionSensorRegistered' resultante.
     *
     * @param sensorRegisterDto DTO contendo o ID e o endereço MAC do sensor a ser registrado.
     * @return Um DTO com os detalhes completos do registro, extraídos do evento do contrato.
     * @throws Exception Se a transação falhar ou o evento de registro não for encontrado.
     */
    public SensorRegisterResponseDto registerMotionSensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de MOVIMENTO UID: {}", sensorRegisterDto.id());
        TransactionReceipt transactionReceipt;

        // MODIFICAÇÃO: Adicionado bloco synchronized para segurança de thread
        synchronized (adminTransactionLock) {
            log.debug("Adquiriu o lock para enviar transação de movimento.");
            transactionReceipt = motionSensorManager
                    .registerMotionSensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                    .send();
            log.debug("Liberou o lock após enviar transação de movimento.");
        }

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro de movimento! Status: " + transactionReceipt.getStatus());
        }

        log.info("Dispositivo de MOVIMENTO registrado com sucesso! Hash da Transação: {}", transactionReceipt.getTransactionHash());

        List<MotionSensorManager.SensorRegisteredEventResponse> events = motionSensorManager.getSensorRegisteredEvents(transactionReceipt);

        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de movimento não encontrado!");
        }

        MotionSensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        return formatAndLogEventResponse(
                eventResponse.uid,
                eventResponse.macAddress,
                eventResponse.measurementType,
                eventResponse.registeredAt,
                eventResponse.expiresAt
        );
    }

    /**
     * Registra um novo sensor de proximidade na blockchain.
     * Envia uma transação para o smart contract e processa o evento 'ProximitySensorRegistered' resultante.
     *
     * @param sensorRegisterDto DTO contendo o ID e o endereço MAC do sensor a ser registrado.
     * @return Um DTO com os detalhes completos do registro, extraídos do evento do contrato.
     * @throws Exception Se a transação falhar ou o evento de registro não for encontrado.
     */
    public SensorRegisterResponseDto registerProximitySensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de PROXIMIDADE UID: {}", sensorRegisterDto.id());
        TransactionReceipt transactionReceipt;

        // MODIFICAÇÃO: Adicionado bloco synchronized para segurança de thread
        synchronized (adminTransactionLock) {
            log.debug("Adquiriu o lock para enviar transação de proximidade.");
            transactionReceipt = proximitySensorManager
                    .registerProximitySensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                    .send();
            log.debug("Liberou o lock após enviar transação de proximidade.");
        }

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro de proximidade! Status: " + transactionReceipt.getStatus());
        }

        log.info("Dispositivo de PROXIMIDADE registrado com sucesso! Hash da Transação: {}", transactionReceipt.getTransactionHash());

        List<ProximitySensorManager.SensorRegisteredEventResponse> events = proximitySensorManager.getSensorRegisteredEvents(transactionReceipt);

        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de proximidade não encontrado!");
        }

        ProximitySensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        return formatAndLogEventResponse(
                eventResponse.uid,
                eventResponse.macAddress,
                eventResponse.measurementType,
                eventResponse.registeredAt,
                eventResponse.expiresAt
        );
    }

    /**
     * Registra um novo sensor de temperatura na blockchain.
     * Envia uma transação para o smart contract e processa o evento 'TemperatureSensorRegistered' resultante.
     *
     * @param sensorRegisterDto DTO contendo o ID e o endereço MAC do sensor a ser registrado.
     * @return Um DTO com os detalhes completos do registro, extraídos do evento do contrato.
     * @throws Exception Se a transação falhar ou o evento de registro não for encontrado.
     */
    public SensorRegisterResponseDto registerTemperatureSensor(SensorRegisterDto sensorRegisterDto) throws Exception {
        log.info("Enviando transação para registrar o sensor de TEMPERATURA UID: {}", sensorRegisterDto.id());
        TransactionReceipt transactionReceipt;

        // MODIFICAÇÃO: Adicionado bloco synchronized para segurança de thread
        synchronized (adminTransactionLock) {
            log.debug("Adquiriu o lock para enviar transação de temperatura.");
            transactionReceipt = temperatureSensorManager
                    .registerTemperatureSensor(sensorRegisterDto.id(), sensorRegisterDto.macAddress())
                    .send();
            log.debug("Liberou o lock após enviar transação de temperatura.");
        }

        if (!transactionReceipt.isStatusOK()) {
            throw new RuntimeException("Falha na transação de registro de temperatura! Status: " + transactionReceipt.getStatus());
        }

        log.info("Dispositivo de TEMPERATURA registrado com sucesso! Hash da Transação: {}", transactionReceipt.getTransactionHash());

        List<TemperatureSensorManager.SensorRegisteredEventResponse> events = temperatureSensorManager.getSensorRegisteredEvents(transactionReceipt);
        if (events.isEmpty()) {
            throw new IllegalStateException("Evento de registro de temperatura não encontrado!");
        }

        TemperatureSensorManager.SensorRegisteredEventResponse eventResponse = events.get(0);

        return formatAndLogEventResponse(
                eventResponse.uid,
                eventResponse.macAddress,
                eventResponse.measurementType,
                eventResponse.registeredAt,
                eventResponse.expiresAt
        );
    }

    /**
     * Verifica se um sensor de umidade existente está autenticado.
     * Chama uma função 'view' no smart contract que retorna um booleano.
     *
     * @param uid O identificador único (UID) do sensor a ser verificado.
     * @return Um DTO confirmando a autenticidade (sempre 'true' se não houver exceção).
     * @throws Exception Se a chamada ao contrato falhar.
     * @throws RuntimeException Se o contrato retornar que o sensor não é autêntico.
     */
    public SensorAuthenticResponseDto isHumiditySensorAuthentic(String uid) throws Exception {
        log.info("Verificando autenticidade do sensor de UMIDADE UID: {}", uid);

        Boolean isAuthentic = humiditySensorManager
                .isHumiditySensorAuthentic(uid)
                .send();

        if (!isAuthentic) {
            throw new RuntimeException("O sensor de UMIDADE não está autenticado");
        }

        return new SensorAuthenticResponseDto(isAuthentic);
    }

    /**
     * Verifica se um sensor de movimento existente está autenticado.
     * Chama uma função 'view' no smart contract que retorna um booleano.
     *
     * @param uid O identificador único (UID) do sensor a ser verificado.
     * @return Um DTO confirmando a autenticidade (sempre 'true' se não houver exceção).
     * @throws Exception Se a chamada ao contrato falhar.
     * @throws RuntimeException Se o contrato retornar que o sensor não é autêntico.
     */
    public SensorAuthenticResponseDto isMotionSensorAuthentic(String uid) throws Exception {
        log.info("Verificando autenticidade do sensor de MOVIMENTO UID: {}", uid);

        Boolean isAuthentic = motionSensorManager
                .isMotionSensorAuthentic(uid)
                .send();

        if (!isAuthentic) {
            throw new RuntimeException("O sensor de MOVIMENTO não está autenticado");
        }

        return new SensorAuthenticResponseDto(isAuthentic);
    }

    /**
     * Verifica se um sensor de proximidade existente está autenticado.
     * Chama uma função 'view' no smart contract que retorna um booleano.
     *
     * @param uid O identificador único (UID) do sensor a ser verificado.
     * @return Um DTO confirmando a autenticidade (sempre 'true' se não houver exceção).
     * @throws Exception Se a chamada ao contrato falhar.
     * @throws RuntimeException Se o contrato retornar que o sensor não é autêntico.
     */
    public SensorAuthenticResponseDto isProximitySensorAuthentic(String uid) throws Exception {
        log.info("Verificando autenticidade do sensor de PROXIMIDADE UID: {}", uid);

        Boolean isAuthentic = proximitySensorManager
                .isProximitySensorAuthentic(uid)
                .send();

        if (!isAuthentic) {
            throw new RuntimeException("O sensor de PROXIMIDADE não está autenticado");
        }

        return new SensorAuthenticResponseDto(isAuthentic);
    }

    /**
     * Verifica se um sensor de temperatura existente está autenticado.
     * Chama uma função 'view' no smart contract que retorna um booleano.
     *
     * @param uid O identificador único (UID) do sensor a ser verificado.
     * @return Um DTO confirmando a autenticidade (sempre 'true' se não houver exceção).
     * @throws Exception Se a chamada ao contrato falhar.
     * @throws RuntimeException Se o contrato retornar que o sensor não é autêntico.
     */
    public SensorAuthenticResponseDto isTemperatureSensorAuthentic(String uid) throws Exception {
        log.info("Verificando autenticidade do sensor de TEMPERATURA UID: {}", uid);

        Boolean isAuthentic = temperatureSensorManager
                .isTemperatureSensorAuthentic(uid)
                .send();

        if (!isAuthentic) {
            throw new RuntimeException("O sensor de TEMPERATURA não está autenticado");
        }

        return new SensorAuthenticResponseDto(isAuthentic);
    }

    /**
     * Método auxiliar privado para formatar, registrar em log e converter os dados de um evento
     * de registro em um DTO de resposta padronizado.
     *
     * @param uid UID do sensor.
     * @param macAddress Endereço MAC do sensor.
     * @param measurementType Tipo de medição do sensor.
     * @param registeredAtTimestamp Timestamp (em segundos) do registro.
     * @param expiresAtTimestamp Timestamp (em segundos) da expiração.
     * @return Um DTO de resposta preenchido.
     */
    private SensorRegisterResponseDto formatAndLogEventResponse(
            String uid,
            String macAddress,
            String measurementType,
            BigInteger registeredAtTimestamp,
            BigInteger expiresAtTimestamp) {

        // Convertendo Timestamps vêm como BigInteger (uint26) para Instant
        java.time.Instant registeredAt = java.time.Instant.ofEpochSecond(registeredAtTimestamp.longValue());
        java.time.Instant expiresAt = java.time.Instant.ofEpochSecond(expiresAtTimestamp.longValue());

        log.info("Evento SensorRegistered capturado:");
        log.info("  -> UID: {}", uid);
        log.info("  -> MAC Address: {}", macAddress);
        log.info("  -> Tipo de Medida: {}", measurementType);
        log.info("  -> Registrado em: {}", registeredAt);
        log.info("  -> Expira em: {}", expiresAt);

        return new SensorRegisterResponseDto(uid, macAddress, measurementType, registeredAt, expiresAt);
    }

}