package com.octanepvp.splityosis.commandsystem.arguments;

import com.octanepvp.splityosis.commandsystem.SYSArgument;

import java.util.List;

public class StringArgument extends SYSArgument {
    @Override
    public boolean isValid(String input) {
        return true;
    }

    @Override
    public List<String> getInvalidInputMessage(String input) {
        return null;
    }
}