package com.example.task.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.tempuri.Add;
import org.tempuri.Divide;
import org.tempuri.Multiply;
import org.tempuri.Subtract;

/*
 * Values validator
 */
public class Validator {
    public static void validateValues(Object object) {
        if (object instanceof Add) {

        } else if (object instanceof Divide) {
            if(((Divide) object).getIntB() == 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Деление на 0");
            }
        } else if (object instanceof Multiply) {

        } else if (object instanceof Subtract) {

        }
    }

}
