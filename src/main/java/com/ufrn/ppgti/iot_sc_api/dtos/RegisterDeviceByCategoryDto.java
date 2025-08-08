package com.ufrn.ppgti.iot_sc_api.dtos;

public record RegisterDeviceByCategoryDto(String id,
                                          String category,
                                          String sensorType, // Mudar tipo para ENUM
                                          String sensorFunction,
                                          String model) {
}
