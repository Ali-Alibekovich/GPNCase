package com.example.task.rest.controllerImpl;

import com.example.task.services.OperationService;
import com.example.task.rest.IController;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    final OperationService operationService;
    public Controller(OperationService operationService) {
        this.operationService = operationService;
    }

    @Override
    public Response Add(Add add) {
        return Response.ok(operationService.add(add)).build();
    }

    @Override
    public Response Divide(Divide divide) {
        if(!validateValues(divide)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Деление на 0");
        }
        return Response.ok(operationService.divide(divide)).build();
    }

    @Override
    public Response Multiply(Multiply multiply) {
        return Response.ok(operationService.multiply(multiply)).build();
    }

    @Override
    public Response Subtract(Subtract subtract) {
        return Response.ok(operationService.subtract(subtract)).build();
    }
}