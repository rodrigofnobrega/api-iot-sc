package com.ufrn.ppgti.iot_sc_api.service;

import com.ufrn.ppgti.iot_sc_api.contracts.HumiditySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.MotionSensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.ProximitySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.TemperatureSensorManager;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterDto;
import com.ufrn.ppgti.iot_sc_api.dtos.SensorRegisterResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {
    @Mock
    private HumiditySensorManager humiditySensorManager;
    @Mock
    private MotionSensorManager motionSensorManager;
    @Mock
    private ProximitySensorManager proximitySensorManager;
    @Mock
    private TemperatureSensorManager temperatureSensorManager;

    @InjectMocks
    private SensorService sensorService;

    @Test
    @DisplayName("Deve registrar sensor de umidade com sucesso quando a transação e o evento ocorrem")
    void registerHumiditySensor_shouldReturnSuccess_whenTransactionIsOkAndEventIsFound() throws Exception {
        try {

        // ARRANGE: Preparar o cenário
        SensorRegisterDto inputDto = new SensorRegisterDto("0xf874988c5a717de6c9c42d2139011338a43acafd529be9cba3d06ed589c9e8d1", "00:1A:2B:3C:4D:5E");

        TransactionReceipt successfulTransactionReceipt = mock(TransactionReceipt.class);
        when(successfulTransactionReceipt.isStatusOK()).thenReturn(true);
        when(successfulTransactionReceipt.getTransactionHash()).thenReturn("0xf874988c5a717de6c9c42d2139011338a43acafd529be9cba3d06ed589c9e8d1");

        HumiditySensorManager.SensorRegisteredEventResponse eventResponse = new HumiditySensorManager.SensorRegisteredEventResponse();
        eventResponse.uid = "0xf874988c5a717de6c9c42d2139011338a43acafd529be9cba3d06ed589c9e8d1";
        eventResponse.macAddress = "00:1A:2B:3C:4D:5E";
        eventResponse.measurementType = "Humidity";
        eventResponse.registeredAt = BigInteger.valueOf(Instant.now().getEpochSecond());
        eventResponse.expiresAt = BigInteger.valueOf(Instant.now().plusSeconds(3600).getEpochSecond());

        @SuppressWarnings("unchecked")
        RemoteFunctionCall<TransactionReceipt> remoteCallMock = mock(RemoteFunctionCall.class);
        when(humiditySensorManager.registerHumiditySensor(anyString(), anyString())).thenReturn(remoteCallMock);
        when(remoteCallMock.send()).thenReturn(successfulTransactionReceipt);

        when(humiditySensorManager.getSensorRegisteredEvents(any(TransactionReceipt.class)))
                .thenReturn(List.of(eventResponse));

        // ACT: Executar a ação
        SensorRegisterResponseDto actualResponse = sensorService.registerHumiditySensor(inputDto);

        // ASSERT: Verificar o resultado
        assertNotNull(actualResponse);
        assertEquals(eventResponse.uid, actualResponse.uid());
        assertEquals(eventResponse.macAddress, actualResponse.macAddress());
        assertEquals(eventResponse.measurementType, actualResponse.measurementType());
        // ATENÇÃO: Verifique se os nomes dos métodos no seu DTO são 'registeredAt' e 'expiresAt'.
        // Se você mudou para 'registrationDate' e 'expirationDate', atualize aqui também.
        assertEquals(Instant.ofEpochSecond(eventResponse.registeredAt.longValue()), actualResponse.registrationDate());
        assertEquals(Instant.ofEpochSecond(eventResponse.expiresAt.longValue()), actualResponse.expirationDate());

        // VERIFY: Verificar as interações
        verify(humiditySensorManager, times(1)).registerHumiditySensor(inputDto.id(), inputDto.macAddress());
        // ✅ CORREÇÃO: Use any() aqui para tornar a verificação mais robusta.
        verify(humiditySensorManager, times(1)).getSensorRegisteredEvents(any(TransactionReceipt.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

