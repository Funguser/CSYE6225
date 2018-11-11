package com.csye6225.lab.ziyao.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class lambdaTest implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
        String output = "Hello, " + input + "!";
        return output;
    }
}
