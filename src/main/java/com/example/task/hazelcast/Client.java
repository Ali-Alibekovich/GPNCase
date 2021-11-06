package com.example.task.hazelcast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.util.ClientStateListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.LifecycleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.hazelcast.core.LifecycleEvent.LifecycleState.CLIENT_DISCONNECTED;

/*
 * Тут происходит подключение клиента к серверу HazelCast и слушание изменений состояния сервера
 */
@Component
public class Client {
    public boolean isConnected = false;
    public static String HOST = "127.0.0.1";
    public static String PORT = "5701";
    protected final Logger logger = LoggerFactory.getLogger(Client.class);
    public HazelcastInstance connection;
    ClientStateListener clientStateListener;

    public Client() {
        initClient();
    }

    private void initClient(){
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().getAddresses().clear();
        config.getNetworkConfig().getAddresses().add(HOST + ":" + PORT);
        config.getConnectionStrategyConfig().setAsyncStart(true).getConnectionRetryConfig().setInitialBackoffMillis(10000);
        clientStateListener = new ClientStateListener(config);
        connection = HazelcastClient.newHazelcastClient(config);
        isConnected = clientStateListener.isConnected();
        if(isConnected){
            logger.info("This connection because application start after server");
        }
        connection.getLifecycleService().addLifecycleListener(event -> {
                    if (event.getState() == LifecycleEvent.LifecycleState.CLIENT_CONNECTED) {
                        logger.info("This connection because server start after application");
                        isConnected = true;
                    }
                    if (event.getState() == CLIENT_DISCONNECTED) {
                        logger.info("Disconnected");
                        isConnected = false;
                    }
                }
        );
    }
}
