package com.example.task.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import javax.ws.rs.core.Response;
import org.tempuri.*;

/*
 * Интерфейс контроллера для будущих реализаций контроллеров
 */
public interface IController {

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SubtractResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
    })
    @GetMapping(value = "Add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adding two numbers", response = AddResponse.class)
    Response Add(Add add);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SubtractResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
    })
    @GetMapping(value = "Divine", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Dividing two numbers", response = DivideResponse.class)
    Response Divide(Divide divide);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SubtractResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
    })
    @GetMapping(value = "Multiply", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Multiplication of two numbers", response = MultiplyResponse.class)
    Response Multiply(Multiply multiply);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SubtractResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
    })
    @GetMapping(value = "Subtract", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Subtracting two numbers", response = SubtractResponse.class)
    Response Subtract(Subtract subtract);
}
