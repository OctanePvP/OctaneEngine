package com.octanepvp.splityosis.commandsystem.arguments;

import com.octanepvp.splityosis.commandsystem.SYSArgument;
import com.octanepvp.splityosis.commandsystem.SYSCommand;
import com.octanepvp.splityosis.octaneengine.oplayer.OPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OPlayerArgument extends SYSArgument {


    @Override
    public boolean isValid(String input) {
        return OPlayer.getOPlayer(input) != null;
    }

    @Override
    public List<String> getInvalidInputMessage(String input) {
        return Arrays.asList("&cPlayer '"+input+"' was never online.");
    }


    @Override
    @NonNull
    public List<String> tabComplete(CommandSender sender, SYSCommand command, String input) {
        List<String> names = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getName().toLowerCase().startsWith(input))
                names.add(onlinePlayer.getName());
        }
        Collections.sort(names);
        return names;
    }
}
