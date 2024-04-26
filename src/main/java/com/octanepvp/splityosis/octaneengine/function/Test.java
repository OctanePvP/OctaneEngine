package com.octanepvp.splityosis.octaneengine.function;

import com.octanepvp.splityosis.octaneengine.function.mathfunction.MathFunction;

public class Test {

    public static void main(String[] args) {

        String[] data = {
                "level*rawdamage",
        };

        Function function = new MathFunction(data)
                .variables("level", "rawdamage")
                .compile();


        function.setVariable("level", 5);
        function.setVariable("rawdamage", 5);
        double damageBooster = function.evaluate();
        System.out.println(damageBooster);

    }
}