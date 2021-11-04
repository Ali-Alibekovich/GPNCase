package com.example.task.hazelcast.clientService.clientServiceImpl;

import com.example.task.hazelcast.Client;
import com.example.task.hazelcast.clientService.IClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Тут происходит взаимодействие с кешем сервера
 */
@Component
public class CalculatorClient implements IClientService {
    private final Client client;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(CalculatorClient.class.getName());
    private IMap<Object, Object> map;

    CalculatorClient(Client client) {
        this.client = client;
    }

    public void initMap(String map) {
        this.map = client.connection.getMap(map);
    }

    @Override
    @SneakyThrows
    public void addOperation(Object request, Object response) {
        String key = objectMapper.writeValueAsString(request);
        String value = objectMapper.writeValueAsString(response);
        logger.info("Insert into cache key: {} value: {}", key, value);
        this.map.set(key, value);
    }

    @Override
    @SneakyThrows
    public Object getOperation(Object request, Object response) {
        String key = objectMapper.writeValueAsString(request);
        logger.info("Getting {} from cache", key);
        return objectMapper.readValue((String) this.map.get(key), response.getClass());
    }


    public IMap<Object, Object> getMap(String name) {
        initMap(name);
        return map;
    }

}
