package com.example.task.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;

public class Server {

    /*
     * Сервер Hazelcast (Запускается отдельно).
     */
    public static void main(String[] args) {
        Config config = new Config();
        config.getNetworkConfig().getRestApiConfig().setEnabled(true);
        Hazelcast.newHazelcastInstance(config);
    }
}