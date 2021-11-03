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
public abstract class Client {
    public static boolean isConnected = false;
    public static String HOST = "127.0.0.1";
    public static String PORT = "5701";
    protected final Logger logger = LoggerFactory.getLogger(Client.class);
    protected ObjectMapper objectMapper = new ObjectMapper();
    public HazelcastInstance client;
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
                initIMap();
                isConnected=true;
            }
            if(event.getState() == CLIENT_DISCONNECTED){
                isConnected=false;
            }
        });
    }

    public abstract void initIMap();


    public abstract void addOperation(Object request, Object response);


    public abstract Object getOperation(Object request);

}
