package com.example.task.services.operationService.operationServiceImpl;

import com.example.task.hazelcast.Client;
import com.example.task.services.cacheService.cacheServiceImpl.CalculatorCacheClient;
import com.example.task.services.cacheService.ICacheClientService;
import com.example.task.rest.controllerImpl.Controller;
import com.example.task.services.operationService.OperationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.tempuri.*;

import java.net.MalformedURLException;
import java.net.URL;


/*
 * Тут основная логика которая делегируется контроллером
 *  + проверка на наличие в кеше значений
 *  + проверка наличия соединения с кешем
 *  + если нет соединения запросы идут сразу на сервер
 */
@Service
public class Operation implements OperationService {
    public final String SOAP_URL = "http://www.dneonline.com/calculator.asmx";
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
    //Клиент для подключения к кешу
    private final Client client;
    //Интерфейс для взаимодействия с кешем
    private final ICacheClientService cacheClientService;
    //прочее
    public final ObjectFactory objectFactory = new ObjectFactory();
    public final ObjectMapper objectMapper = new ObjectMapper();

    public Operation(Client client, CalculatorCacheClient cacheClientService) {
        this.client = client;
        this.cacheClientService = cacheClientService;
    }

    /*
     * Add/Divine/Multiply/Subtract - проверяют есть ли в кеше ключ, если есть - берут из кеша ответ,
     * если нет - обращаются к веб сервису SOAP за ответом, ответ сохраняется в кеш.
     */
    @SneakyThrows
    public AddResponse add(Add add) {
        AddResponse addResponse = objectFactory.createAddResponse();
        if (client.isConnected()) {
            if (cacheClientService.getMap(addResponse.getClass().getName()).containsKey(objectMapper.writeValueAsString(add))) {
                return (AddResponse) getFromCache(add, addResponse);
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
        if (client.isConnected()) {
            if (cacheClientService.getMap(divideResponse.getClass().getName()).containsKey(objectMapper.writeValueAsString(divide))) {
                return (DivideResponse) getFromCache(divide, divideResponse);
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
        if (client.isConnected()) {
            if (cacheClientService.getMap(multiplyResponse.getClass().getName()).containsKey(objectMapper.writeValueAsString(multiply))) {
                return (MultiplyResponse) getFromCache(multiply, multiplyResponse);
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
        if (client.isConnected()) {
            if (cacheClientService.getMap(subtractResponse.getClass().getName()).containsKey(objectMapper.writeValueAsString(subtract))) {
                return (SubtractResponse) getFromCache(subtract, subtractResponse);
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
        cacheClientService.addOperation(request, response);
    }

    /*
     * Обращение к Клиенту Hazelcast для взятия объекта из кеша
     */
    public Object getFromCache(Object request, Object response) {
        response = cacheClientService.getOperation(request,response);
        return response;
    }

}
