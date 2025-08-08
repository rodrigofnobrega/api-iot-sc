package com.ufrn.ppgti.iot_sc_api.dtos;

import java.time.LocalDateTime;

public record DeviceAuthenticateResponseDto(String deviceId,
                                            Boolean authenticated,
                                            LocalDateTime checkedAt) {
}
