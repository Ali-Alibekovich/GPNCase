package com.example.task.hazelcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.util.ClientStateListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.LifecycleEvent;
import com.hazelcast.map.IMap;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tempuri.*;

import static com.hazelcast.core.LifecycleEvent.LifecycleState.CLIENT_DISCONNECTED;

@Component
public class Client {
    public static boolean isConnected = false;
    public static String HOST = "127.0.0.1";
    public static String PORT = "5701";
    protected IMap<String, String> addMap;
    protected IMap<String, String> divideMap;
    protected IMap<String, String> multiplyMap;
    protected IMap<String, String> subtractMap;
    final Logger logger = LoggerFactory.getLogger(Client.class);
    ObjectMapper objectMapper = new ObjectMapper();
    HazelcastInstance client;
    ClientStateListener clientStateListener;

    public Client() {
        System.out.println(HOST+PORT);
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().getAddresses().clear();
        config.getNetworkConfig().getAddresses().add(HOST+":"+PORT);
        config.getConnectionStrategyConfig().setAsyncStart(true).getConnectionRetryConfig().setInitialBackoffMillis(10000);
        clientStateListener = new ClientStateListener(config);
        client = HazelcastClient.newHazelcastClient(config);
        client.getLifecycleService().addLifecycleListener(event -> {
            if(event.getState() == LifecycleEvent.LifecycleState.CLIENT_CONNECTED){
                initClient();
                isConnected=true;
            }
            if(event.getState() == CLIENT_DISCONNECTED){
                isConnected=false;
            }
        });
    }

    /*
     * Создание клиента и подключение к HazelCast server локально.
     * maps - хранилище операций
     */
    public void initClient() {
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

}
