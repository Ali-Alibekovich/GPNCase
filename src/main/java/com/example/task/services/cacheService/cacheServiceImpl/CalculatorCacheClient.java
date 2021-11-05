package com.example.task.services.cacheService.cacheServiceImpl;

import com.example.task.hazelcast.Client;
import com.example.task.services.cacheService.ICacheClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/*
 * Тут происходит взаимодействие с кешем сервера
 */
@Service
public class CalculatorCacheClient implements ICacheClientService {
    private final Client client;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(CalculatorCacheClient.class.getName());
    private IMap<Object, Object> map;

    CalculatorCacheClient(Client client) {
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

    boolean isConnected(){
        return this.client.isConnected();
    }

}
