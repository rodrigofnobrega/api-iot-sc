package com.ufrn.ppgti.iot_sc_api.dtos;

import java.time.Instant;

public record SensorRegisterResponseDto(
        String uid,
        String macAddress,
        String measurementType,
        Instant registrationDate,
        Instant expirationDate) {
}
