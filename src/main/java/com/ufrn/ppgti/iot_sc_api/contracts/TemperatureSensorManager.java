package com.ufrn.ppgti.iot_sc_api.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class TemperatureSensorManager extends Contract {
    public static final String BINARY = "0x6080604052348015600f57600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610f708061005f6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80630342a4d114610051578063887a504214610081578063ac4a9919146100b5578063f851a440146100d1575b600080fd5b61006b600480360381019061006691906107aa565b6100ef565b604051610078919061080e565b60405180910390f35b61009b600480360381019061009691906107aa565b61028d565b6040516100ac9594939291906108c1565b60405180910390f35b6100cf60048036038101906100ca9190610922565b6103f6565b005b6100d961062c565b6040516100e691906109db565b60405180910390f35b6000806001836040516101029190610a32565b90815260200160405180910390206040518060a001604052908160008201805461012b90610a78565b80601f016020809104026020016040519081016040528092919081815260200182805461015790610a78565b80156101a45780601f10610179576101008083540402835291602001916101a4565b820191906000526020600020905b81548152906001019060200180831161018757829003601f168201915b505050505081526020016001820180546101bd90610a78565b80601f01602080910402602001604051908101604052809291908181526020018280546101e990610a78565b80156102365780601f1061020b57610100808354040283529160200191610236565b820191906000526020600020905b81548152906001019060200180831161021957829003601f168201915b5050505050815260200160028201548152602001600382015481526020016004820160009054906101000a900460ff161515151581525050905080608001518015610285575080606001514211155b915050919050565b6001818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546102c690610a78565b80601f01602080910402602001604051908101604052809291908181526020018280546102f290610a78565b801561033f5780601f106103145761010080835404028352916020019161033f565b820191906000526020600020905b81548152906001019060200180831161032257829003601f168201915b50505050509080600101805461035490610a78565b80601f016020809104026020016040519081016040528092919081815260200182805461038090610a78565b80156103cd5780601f106103a2576101008083540402835291602001916103cd565b820191906000526020600020905b8154815290600101906020018083116103b057829003601f168201915b5050505050908060020154908060030154908060040160009054906101000a900460ff16905085565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610484576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161047b90610b1b565b60405180910390fd5b6001826040516104949190610a32565b908152602001604051809103902060040160009054906101000a900460ff16156104f3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104ea90610b87565b60405180910390fd5b600042905060006078826105079190610bd6565b90506040518060a001604052808481526020016040518060400160405280600b81526020017f74656d70657261747572650000000000000000000000000000000000000000008152508152602001838152602001828152602001600115158152506001856040516105789190610a32565b9081526020016040518091039020600082015181600001908161059b9190610db6565b5060208201518160010190816105b19190610db6565b50604082015181600201556060820151816003015560808201518160040160006101000a81548160ff0219169083151502179055509050507fd426d3b7fcb40b4aeaa310290c0b02ad9ccf00fe0d2a1e7f1ff2c904495661b18484848460405161061e9493929190610ed4565b60405180910390a150505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6106b78261066e565b810181811067ffffffffffffffff821117156106d6576106d561067f565b5b80604052505050565b60006106e9610650565b90506106f582826106ae565b919050565b600067ffffffffffffffff8211156107155761071461067f565b5b61071e8261066e565b9050602081019050919050565b82818337600083830152505050565b600061074d610748846106fa565b6106df565b90508281526020810184848401111561076957610768610669565b5b61077484828561072b565b509392505050565b600082601f83011261079157610790610664565b5b81356107a184826020860161073a565b91505092915050565b6000602082840312156107c0576107bf61065a565b5b600082013567ffffffffffffffff8111156107de576107dd61065f565b5b6107ea8482850161077c565b91505092915050565b60008115159050919050565b610808816107f3565b82525050565b600060208201905061082360008301846107ff565b92915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610863578082015181840152602081019050610848565b60008484015250505050565b600061087a82610829565b6108848185610834565b9350610894818560208601610845565b61089d8161066e565b840191505092915050565b6000819050919050565b6108bb816108a8565b82525050565b600060a08201905081810360008301526108db818861086f565b905081810360208301526108ef818761086f565b90506108fe60408301866108b2565b61090b60608301856108b2565b61091860808301846107ff565b9695505050505050565b600080604083850312156109395761093861065a565b5b600083013567ffffffffffffffff8111156109575761095661065f565b5b6109638582860161077c565b925050602083013567ffffffffffffffff8111156109845761098361065f565b5b6109908582860161077c565b9150509250929050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006109c58261099a565b9050919050565b6109d5816109ba565b82525050565b60006020820190506109f060008301846109cc565b92915050565b600081905092915050565b6000610a0c82610829565b610a1681856109f6565b9350610a26818560208601610845565b80840191505092915050565b6000610a3e8284610a01565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610a9057607f821691505b602082108103610aa357610aa2610a49565b5b50919050565b7f4f6e6c792061646d696e2063616e20706572666f726d2074686973206163746960008201527f6f6e2e0000000000000000000000000000000000000000000000000000000000602082015250565b6000610b05602383610834565b9150610b1082610aa9565b604082019050919050565b60006020820190508181036000830152610b3481610af8565b9050919050565b7f44657669636520616c7265616479207265676973746572656400000000000000600082015250565b6000610b71601983610834565b9150610b7c82610b3b565b602082019050919050565b60006020820190508181036000830152610ba081610b64565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b6000610be1826108a8565b9150610bec836108a8565b9250828201905080821115610c0457610c03610ba7565b5b92915050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302610c6c7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610c2f565b610c768683610c2f565b95508019841693508086168417925050509392505050565b6000819050919050565b6000610cb3610cae610ca9846108a8565b610c8e565b6108a8565b9050919050565b6000819050919050565b610ccd83610c98565b610ce1610cd982610cba565b848454610c3c565b825550505050565b600090565b610cf6610ce9565b610d01818484610cc4565b505050565b5b81811015610d2557610d1a600082610cee565b600181019050610d07565b5050565b601f821115610d6a57610d3b81610c0a565b610d4484610c1f565b81016020851015610d53578190505b610d67610d5f85610c1f565b830182610d06565b50505b505050565b600082821c905092915050565b6000610d8d60001984600802610d6f565b1980831691505092915050565b6000610da68383610d7c565b9150826002028217905092915050565b610dbf82610829565b67ffffffffffffffff811115610dd857610dd761067f565b5b610de28254610a78565b610ded828285610d29565b600060209050601f831160018114610e205760008415610e0e578287015190505b610e188582610d9a565b865550610e80565b601f198416610e2e86610c0a565b60005b82811015610e5657848901518255600182019150602085019450602081019050610e31565b86831015610e735784890151610e6f601f891682610d7c565b8355505b6001600288020188555050505b505050505050565b7f74656d7065726174757265000000000000000000000000000000000000000000600082015250565b6000610ebe600b83610834565b9150610ec982610e88565b602082019050919050565b600060a0820190508181036000830152610eee818761086f565b90508181036020830152610f02818661086f565b90508181036040830152610f1581610eb1565b9050610f2460608301856108b2565b610f3160808301846108b2565b9594505050505056fea2646970667358221220ca0e54310373a4b475451b84ca5a0ec3f680e8263827cde34bfccbf8cec79db964736f6c634300081c0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_ISTEMPERATURESENSORAUTHENTIC = "isTemperatureSensorAuthentic";

    public static final String FUNC_REGISTERTEMPERATURESENSOR = "registerTemperatureSensor";

    public static final String FUNC_SENSORS = "sensors";

    public static final Event SENSORREGISTERED_EVENT = new Event("SensorRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected TemperatureSensorManager(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TemperatureSensorManager(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TemperatureSensorManager(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TemperatureSensorManager(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<SensorRegisteredEventResponse> getSensorRegisteredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SENSORREGISTERED_EVENT, transactionReceipt);
        ArrayList<SensorRegisteredEventResponse> responses = new ArrayList<SensorRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SensorRegisteredEventResponse typedResponse = new SensorRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.uid = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.macAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.measurementType = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.registeredAt = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SensorRegisteredEventResponse getSensorRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SENSORREGISTERED_EVENT, log);
        SensorRegisteredEventResponse typedResponse = new SensorRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.uid = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.macAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.measurementType = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.registeredAt = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<SensorRegisteredEventResponse> sensorRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSensorRegisteredEventFromLog(log));
    }

    public Flowable<SensorRegisteredEventResponse> sensorRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENSORREGISTERED_EVENT));
        return sensorRegisteredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> admin() {
        final Function function = new Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isTemperatureSensorAuthentic(String _uid) {
        final Function function = new Function(FUNC_ISTEMPERATURESENSORAUTHENTIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerTemperatureSensor(String _uid,
            String _macAddress) {
        final Function function = new Function(
                FUNC_REGISTERTEMPERATURESENSOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_uid), 
                new org.web3j.abi.datatypes.Utf8String(_macAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, Boolean>> sensors(
            String param0) {
        final Function function = new Function(FUNC_SENSORS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple5<String, String, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple5<String, String, BigInteger, BigInteger, Boolean> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, BigInteger, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    @Deprecated
    public static TemperatureSensorManager load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemperatureSensorManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TemperatureSensorManager load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemperatureSensorManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TemperatureSensorManager load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TemperatureSensorManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TemperatureSensorManager load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TemperatureSensorManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TemperatureSensorManager> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemperatureSensorManager.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<TemperatureSensorManager> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemperatureSensorManager.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<TemperatureSensorManager> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemperatureSensorManager.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<TemperatureSensorManager> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemperatureSensorManager.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

//    public static void linkLibraries(List<Contract.LinkReference> references) {
//        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
//    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class SensorRegisteredEventResponse extends BaseEventResponse {
        public String uid;

        public String macAddress;

        public String measurementType;

        public BigInteger registeredAt;

        public BigInteger expiresAt;
    }
}
