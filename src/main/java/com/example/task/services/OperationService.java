package com.example.task.services;

import com.example.task.hazelcast.Client;
import com.example.task.hazelcast.Clients.CalculatorClient;
import com.example.task.rest.controllerImpl.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.tempuri.*;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.task.hazelcast.Client.isConnected;


@Service
public class OperationService {
    public static final String SOAP_URL = "http://www.dneonline.com/calculator.asmx";
    public URL url = null;

    {
        try {
            url = new URL(SOAP_URL);
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Controller.class.getName())
                    .log(java.util.logging.Level.INFO,
                            "Can not initialize the default url {0}", "http://www.dneonline.com/calculator.asmx");
        }
    }

    public final Calculator calculator = new Calculator(url);

    private final CalculatorClient client;
    public final ObjectFactory objectFactory = new ObjectFactory();
    public final ObjectMapper objectMapper = new ObjectMapper();

    public OperationService(CalculatorClient client) {
        this.client = client;
    }

    /*
     * Add/Divine/Multiply/Subtract - проверяют есть ли в кеше ключ, если есть - берут из кеша ответ,
     * если нет - обращаются к веб сервису SOAP за ответом, ответ сохраняется в кеш.
     */
    @SneakyThrows
    public AddResponse add(Add add) {
        AddResponse addResponse = objectFactory.createAddResponse();
        if (isConnected) {
            if (client.getAddMap().containsKey(objectMapper.writeValueAsString(add))) {
                return (AddResponse) getFromCache(add);
            } else {
                addResponse.setAddResult(calculator.getCalculatorSoap().add(add.getIntA(), add.getIntB()));
                putInCache(add, addResponse);
            }
        } else {
            addResponse.setAddResult(calculator.getCalculatorSoap().add(add.getIntA(), add.getIntB()));
        }
        return addResponse;
    }

    @SneakyThrows
    public DivideResponse divide(Divide divide) {
        DivideResponse divideResponse = objectFactory.createDivideResponse();
        if (isConnected) {
            if (client.getDivideMap().containsKey(objectMapper.writeValueAsString(divide))) {
                return (DivideResponse) getFromCache(divide);
            } else {
                divideResponse.setDivideResult(calculator.getCalculatorSoap().add(divide.getIntA(), divide.getIntB()));
                putInCache(divide, divideResponse);
            }
        } else {
            divideResponse.setDivideResult(calculator.getCalculatorSoap().add(divide.getIntA(), divide.getIntB()));
        }
        return divideResponse;
    }

    @SneakyThrows
    public MultiplyResponse multiply(Multiply multiply) {
        MultiplyResponse multiplyResponse = objectFactory.createMultiplyResponse();
        if (isConnected) {
            if (client.getMultiplyMap().containsKey(objectMapper.writeValueAsString(multiply))) {
                return (MultiplyResponse) getFromCache(multiply);
            } else {
                multiplyResponse.setMultiplyResult(calculator.getCalculatorSoap().add(multiply.getIntA(), multiply.getIntB()));
                putInCache(multiply, multiplyResponse);
            }
        } else {
            multiplyResponse.setMultiplyResult(calculator.getCalculatorSoap().add(multiply.getIntA(), multiply.getIntB()));
        }
        return multiplyResponse;
    }

    @SneakyThrows
    public SubtractResponse subtract(Subtract subtract) {
        SubtractResponse subtractResponse = objectFactory.createSubtractResponse();
        if (isConnected) {
            if (client.getSubtractMap().containsKey(objectMapper.writeValueAsString(subtract))) {
                return (SubtractResponse) getFromCache(subtract);
            } else {
                subtractResponse.setSubtractResult(calculator.getCalculatorSoap().add(subtract.getIntA(), subtract.getIntB()));
                putInCache(subtract,subtractResponse);
            }
        } else {
            subtractResponse.setSubtractResult(calculator.getCalculatorSoap().add(subtract.getIntA(), subtract.getIntB()));
        }
        return subtractResponse;
    }


    /*
     * Обращение к Клиенту Hazelcast для сохранения объекта в кеш
     */
    public void putInCache(Object request, Object response) {
        client.addOperation(request, response);
    }

    /*
     * Обращение к Клиенту Hazelcast для взятия объекта из кеша
     */
    public Object getFromCache(Object request) {
        return client.getOperation(request);
    }

}
