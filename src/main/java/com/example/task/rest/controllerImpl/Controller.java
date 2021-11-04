package com.example.task.rest.controllerImpl;

import com.example.task.rest.IController;
import com.example.task.services.Operation;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.Add;
import org.tempuri.Divide;
import org.tempuri.Multiply;
import org.tempuri.Subtract;

import javax.ws.rs.core.Response;

import static com.example.task.utils.Validator.validateValues;


/*
 * REST контроллер для внешнего взаимодействия
 */
@RestController
@RequestMapping("/")
@Api(value = "CalculatorAPI")
public class Controller implements IController {
    final Operation operation;
    public Controller(Operation operation) {
        this.operation = operation;
    }

    @Override
    public Response Add(Add add) {
        return Response.ok(operation.add(add)).build();
    }

    @Override
    public Response Divide(Divide divide) {
        validateValues(divide);
        return Response.ok(operation.divide(divide)).build();
    }

    @Override
    public Response Multiply(Multiply multiply) {
        return Response.ok(operation.multiply(multiply)).build();
    }

    @Override
    public Response Subtract(Subtract subtract) {
        return Response.ok(operation.subtract(subtract)).build();
    }
}