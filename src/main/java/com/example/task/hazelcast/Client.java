package com.example.task.hazelcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tempuri.*;


@Component
public class Client {
    protected IMap<String, String> addMap;
    protected IMap<String, String> divideMap;
    protected IMap<String, String> multiplyMap;
    protected IMap<String, String> subtractMap;
    final Logger logger = LoggerFactory.getLogger(Client.class);
    ObjectMapper objectMapper = new ObjectMapper();

    /*
     * Создание клиента и подключение к HazelCast server локально.
     * maps - хранилище операций
     */
    @Bean
    public void startClient() {
        ClientConfig config = new ClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        this.addMap = client.getMap("Add");
        this.divideMap = client.getMap("Divide");
        this.multiplyMap = client.getMap("Multiply");
        this.subtractMap = client.getMap("Subtract");
    }

    /*
     * Добавляет в соответствующий IMAP запрос и результат в виде строки
     */
    @SneakyThrows
    public void addOperation(Object request, Object response) {
        String key = objectMapper.writeValueAsString(request);
        String value = objectMapper.writeValueAsString(response);
        logger.info("Insert into cache key: {} value: {}", key, value);
        if (request instanceof Add) {
            this.addMap.set(key, value);
        }
        if (request instanceof Divide) {
            this.divideMap.set(key, value);
        }
        if (request instanceof Multiply) {
            this.multiplyMap.set(key, value);
        }
        if (request instanceof Subtract) {
            this.subtractMap.set(key, value);
        }
    }

    /*
     * Берет из IMAP значение по ключу (ключ: объект -> строка) и преобразует его в соответствующий тип
     */
    @SneakyThrows
    public Object getOperation(Object request) {
        String key = objectMapper.writeValueAsString(request);
        logger.info("Getting {} from cache", key);
        if (request instanceof Add) {
            return objectMapper.readValue(this.addMap.get(key), AddResponse.class);
        }
        if (request instanceof Divide) {
            return objectMapper.readValue(this.divideMap.get(key), DivideResponse.class);
        }
        if (request instanceof Multiply) {
            return objectMapper.readValue(this.multiplyMap.get(key), MultiplyResponse.class);
        }
        if (request instanceof Subtract) {
            return objectMapper.readValue(this.subtractMap.get(key), SubtractResponse.class);
        }
        return null;
    }

    public IMap<String, String> getAddMap() {
        return addMap;
    }

    public IMap<String, String> getDivideMap() {
        return divideMap;
    }

    public IMap<String, String> getMultiplyMap() {
        return multiplyMap;
    }

    public IMap<String, String> getSubtractMap() {
        return subtractMap;
    }
}
