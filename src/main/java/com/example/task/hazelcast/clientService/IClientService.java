package com.example.task.hazelcast.clientService;

import com.hazelcast.map.IMap;

public interface IClientService {

    void addOperation(Object request, Object response);


    Object getOperation(Object request, Object response);

    IMap<Object, Object> getMap(String name);
}
