package com.ufrn.ppgti.iot_sc_api.dtos;

import java.time.LocalDateTime;

public record RegisterDeviceResponseOkDto(LocalDateTime registrationDate,
                                          LocalDateTime expirationDate) {
}
