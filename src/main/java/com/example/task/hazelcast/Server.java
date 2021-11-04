package com.example.task.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.tempuri.AddResponse;

/*
 * Сервер Hazelcast (Запускается отдельно).
 */
public class Server {
    public static void main(String[] args) {
        Config config = new Config();
        config.getNetworkConfig().getRestApiConfig().setEnabled(true);
        config.getNetworkConfig().setPublicAddress(args.length==1?args[0]:"127.0.0.1")
                .setPort(args.length==2?Integer.parseInt(args[1]):5701);
        config.getJetConfig().setEnabled(true);
        HazelcastInstance server = Hazelcast.newHazelcastInstance(config);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }

}
