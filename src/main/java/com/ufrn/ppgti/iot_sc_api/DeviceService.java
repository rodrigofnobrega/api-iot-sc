package com.ufrn.ppgti.iot_sc_api;

import com.ufrn.ppgti.iot_sc_api.dtos.DeviceAuthenticateResponseDto;
import com.ufrn.ppgti.iot_sc_api.dtos.RegisterDeviceByCategoryDto;
import com.ufrn.ppgti.iot_sc_api.dtos.RegisterDeviceDto;
import com.ufrn.ppgti.iot_sc_api.dtos.RegisterDeviceResponseOkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DeviceService {

    /**
     * Registra um novo dispositivo no sistema com base nas informações fornecidas.
     * <p>
     * Este método recebe os dados básicos de um dispositivo, realiza a validação
     * necessária, persiste as informações e retorna uma confirmação de sucesso.
     *
     * @param registerDeviceDto DTO contendo os dados básicos para o registro do dispositivo,
     * como ID, tipo e modelo.
     * @return DTO com a confirmação e os dados do dispositivo registrado, incluindo o status
     * e a data de registro.
     */
    public RegisterDeviceResponseOkDto registerDevice(RegisterDeviceDto registerDeviceDto) {
        log.info("Iniciando processo de registro para o dispositivo ID: {}", registerDeviceDto.id());
        return this.fingirQueChamouSc(registerDeviceDto);
    }

    /**
     * Registra um novo dispositivo utilizando uma estrutura de dados detalhada baseada em categorias.
     * <p>
     * Este método permite um registro especificando não apenas o tipo,
     * mas também a categoria e a função do sensor, o que facilita a organização e
     * busca de dispositivos no sistema.
     *
     * @param registerDeviceByCategoryDto DTO com informações detalhadas para o registro,
     * incluindo categoria, tipo, função e modelo do sensor.
     * @return DTO com a confirmação e os dados do dispositivo registrado.
     */
    public RegisterDeviceResponseOkDto registerDeviceByCategory(RegisterDeviceByCategoryDto registerDeviceByCategoryDto) {
        log.info("Iniciando processo de registro por categoria para o dispositivo ID: {}", registerDeviceByCategoryDto.id());
        return this.fingirQueChamouSc(registerDeviceByCategoryDto);
    }

    /**
     * Verifica o status de autenticação de um dispositivo específico com base em seu ID.
     * <p>
     * O método consulta o sistema para determinar se o dispositivo está atualmente
     * considerado autêntico e autorizado a operar. É útil para validar a atividade
     * de um dispositivo antes de aceitar seus dados.
     *
     * @param id O identificador único (ID) do dispositivo a ser verificado.
     * @return DTO contendo o status da autenticação (<code>true</code> ou <code>false</code>)
     * e informações adicionais, como a data da verificação.
     */
    public DeviceAuthenticateResponseDto verifyDeviceAuthenticate(String id) {
        log.info("Iniciando processo de verificação de autenticidade para o ID: {}", id);
        return this.fingirQueChamouSc(id);
    }


    // Metodos para fingir que ta chamando o contrato inteligente

    private RegisterDeviceResponseOkDto fingirQueChamouSc(RegisterDeviceDto registerDeviceDto) {
        log.info("Simulando chamada ao Smart Contract para registro padrão...");
        log.debug("Dados recebidos para simulação: {}", registerDeviceDto); // DEBUG para não poluir o log

        LocalDateTime agora = LocalDateTime.now();
        RegisterDeviceResponseOkDto response = new RegisterDeviceResponseOkDto(agora, agora.plusHours(3));

        log.info("Simulação de registro padrão concluída com sucesso.");
        return response;
    }

    private RegisterDeviceResponseOkDto fingirQueChamouSc(RegisterDeviceByCategoryDto registerDeviceDto) {
        log.info("Simulando chamada ao Smart Contract para registro por categoria...");
        log.debug("Dados recebidos para simulação: {}", registerDeviceDto);

        LocalDateTime agora = LocalDateTime.now();
        RegisterDeviceResponseOkDto response = new RegisterDeviceResponseOkDto(agora, agora.plusHours(6));

        log.info("Simulação de registro por categoria concluída com sucesso.");
        return response;
    }

    private DeviceAuthenticateResponseDto fingirQueChamouSc(String id) {
        log.info("Simulando chamada ao Smart Contract para autenticar o ID: {}", id);
        DeviceAuthenticateResponseDto response = new DeviceAuthenticateResponseDto(id, Boolean.TRUE, LocalDateTime.now());
        log.info("Simulação de autenticação concluída. Dispositivo considerado autêntico.");
        return response;
    }
}
