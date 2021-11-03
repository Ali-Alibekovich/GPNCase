package com.example.task.utils;

import org.tempuri.Add;
import org.tempuri.Divide;
import org.tempuri.Multiply;
import org.tempuri.Subtract;

/*
 * Values validator
 */
public class Validator {
    public static boolean validateValues(Object object) {
        if (object instanceof Add) {
            return true;
        }
        if (object instanceof Divide) {
            return ((Divide) object).getIntB() != 0;
        }
        if (object instanceof Multiply) {
            return true;
        }
        if (object instanceof Subtract) {
            return true;
        }
        return false;
    }

}
