package com.example.task.hazelcast.exClient;

import com.example.task.hazelcast.Client;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.tempuri.*;

@Component
public class CalculatorClient extends Client {
    protected IMap<String, String> addMap;
    protected IMap<String, String> divideMap;
    protected IMap<String, String> multiplyMap;
    protected IMap<String, String> subtractMap;

    @Override
    public void initIMap() {
        this.addMap = client.getMap("Add");
        this.divideMap = client.getMap("Divide");
        this.multiplyMap = client.getMap("Multiply");
        this.subtractMap = client.getMap("Subtract");
    }

    @Override
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

    @Override
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

    public IMap<String, String> getSubtractMap() {
        return subtractMap;
    }

    public IMap<String, String> getMultiplyMap() {
        return multiplyMap;
    }

    public IMap<String, String> getDivideMap() {
        return divideMap;
    }

    public IMap<String, String> getAddMap() {
        return addMap;
    }
}
