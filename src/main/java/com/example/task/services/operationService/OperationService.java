package com.example.task.services.operationService;

import org.tempuri.*;

public interface OperationService {
    AddResponse add(Add add);

    DivideResponse divide(Divide divide);

    MultiplyResponse multiply(Multiply multiply);

    SubtractResponse subtract(Subtract subtract);
}
