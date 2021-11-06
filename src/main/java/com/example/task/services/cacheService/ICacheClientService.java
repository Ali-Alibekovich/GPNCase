package com.example.task.services.cacheService;

import com.hazelcast.map.IMap;


/*
 * Интерфейс для будущих реализаций взаимодействия клиента с кешем на сервере
 */
public interface ICacheClientService {

    void addOperation(Object request, Object response);


    Object getOperation(Object request, Object response);

    IMap<Object, Object> getMap(String name);

    boolean isConnected();
}
