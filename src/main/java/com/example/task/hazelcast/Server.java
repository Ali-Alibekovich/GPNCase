package com.example.task.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Server {
    /*
     * Сервер Hazelcast (Запускается отдельно).
     */
    public static void main(String[] args) {
        Config config = new Config();
        config.getNetworkConfig().setPublicAddress(args.length==1?args[0]:"127.0.0.1")
                .setPort(args.length==2?Integer.parseInt(args[1]):5701);
        HazelcastInstance server = Hazelcast.newHazelcastInstance(config);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }

}