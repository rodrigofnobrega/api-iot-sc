package com.ufrn.ppgti.iot_sc_api.config;

import com.ufrn.ppgti.iot_sc_api.contracts.HumiditySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.MotionSensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.ProximitySensorManager;
import com.ufrn.ppgti.iot_sc_api.contracts.TemperatureSensorManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;


import java.math.BigInteger;

@Configuration
public class BlockchainConfig {

    @Value("${blockchain.admin.private-key}")
    private String privateKey;

    @Value("${blockchain.node.url}")
    private String nodeUrl;

    @Value("${blockchain.gas.price}")
    private BigInteger gasPrice;

    @Value("${blockchain.gas.limit}")
    private BigInteger gasLimit;

    @Value("${blockchain.contract.humidity}")
    private String humidityContractAddress;

    @Value("${blockchain.contract.motion}")
    private String motionContractAddress;

    @Value("${blockchain.contract.proximity}")
    private String proximityContractAddress;

    @Value("${blockchain.contract.temperature}")
    private String temperatureContractAddress;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(nodeUrl));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }

    @Bean
    public ContractGasProvider contractGasProvider() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }

    // Carregar warapper dos contratos
    @Bean
    public HumiditySensorManager humiditySensorManager(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return HumiditySensorManager.load(humidityContractAddress, web3j, credentials, gasProvider);
    }

    @Bean
    public MotionSensorManager motionSensorManager(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return MotionSensorManager.load(motionContractAddress, web3j, credentials, gasProvider);
    }

    @Bean
    public ProximitySensorManager proximitySensorManager(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return ProximitySensorManager.load(proximityContractAddress, web3j, credentials, gasProvider);
    }

    @Bean
    public TemperatureSensorManager temperatureSensorManager(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return TemperatureSensorManager.load(temperatureContractAddress, web3j, credentials, gasProvider);
    }
}