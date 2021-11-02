package com.example.task.rest;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tempuri.*;

import java.net.MalformedURLException;
import java.net.URL;


@RestController
@RequestMapping("/")
@Api(value = "CalculatorAPI")
public class Controller {
    public static final String SOAP_URL = "http://www.dneonline.com/calculator.asmx";
    public final URL url = new URL(SOAP_URL);
    public final Calculator calculator = new Calculator(url);
    public final ObjectFactory objectFactory = new ObjectFactory();

    public Controller() throws MalformedURLException {
    }


    @GetMapping(value = "Add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Adding two numbers", response = AddResponse.class)
    public Object Add(@RequestParam("intA") @ApiParam(value = "First value") int a,
                      @RequestParam("intB") @ApiParam(value = "Second value") int b) {
        Add add = objectFactory.createAdd();
        add.setIntA(a);
        add.setIntB(b);
        AddResponse addResponse = objectFactory.createAddResponse();
        addResponse.setAddResult(calculator.getCalculatorSoap().add(a, b));
        return addResponse;
    }


    @GetMapping(value = "Divide", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Dividing two numbers", response = DivideResponse.class)
    public Object Divide(@RequestParam("intA") @ApiParam(value = "First value") int a,
                         @RequestParam("intB") @ApiParam(value = "Second value") int b) {
        Divide divide = objectFactory.createDivide();
        DivideResponse divideResponse = objectFactory.createDivideResponse();
        divide.setIntA(a);
        divide.setIntB(b);
        divideResponse.setDivideResult(calculator.getCalculatorSoap().divide(a, b));
        return divideResponse;
    }


    @GetMapping(value = "Multiply", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Multiplication of two numbers", response = MultiplyResponse.class)
    public Object Multiply(@RequestParam("intA") @ApiParam(value = "First value") int a,
                           @RequestParam("intB") @ApiParam(value = "Second value") int b) {
        Multiply multiply = objectFactory.createMultiply();
        multiply.setIntA(a);
        multiply.setIntB(b);
        MultiplyResponse multiplyResponse = objectFactory.createMultiplyResponse();
        multiplyResponse.setMultiplyResult(calculator.getCalculatorSoap().divide(a, b));
        return multiplyResponse;
    }


    @GetMapping(value = "Subtract", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Subtracting two numbers", response = SubtractResponse.class)
    public Object Subtract(@RequestParam("intA") @ApiParam(value = "First value") int a,
                           @RequestParam("intB") @ApiParam(value = "Second value") int b) {
        Subtract subtract = objectFactory.createSubtract();
        subtract.setIntA(a);
        subtract.setIntB(b);
        SubtractResponse subtractResponse = objectFactory.createSubtractResponse();
        subtractResponse.setSubtractResult(calculator.getCalculatorSoap().subtract(a, b));
        return subtractResponse;
    }


}