package com.octanepvp.splityosis.octaneengine.actiontypes;

import dev.splityosis.configsystem.configsystem.actionsystem.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageIteratorActionType extends ActionType {
    public MessageIteratorActionType() {
        super("MESSAGE_ITERATOR", "MESSAGEITERATOR");
    }

    @Override
    public void run(@Nullable Player player, @NotNull List<String> list, @NotNull Map<String, String> map) {
        if (map == null) {
            return;
        }
        if (player == null) {
            return;
        }
        list = replace(list, map);
        list = applyPlaceholderAPI(player, list);
        list = colorize(list);
        String iterationsString = map.get("%iterations%");
        int iterations = 1;
        if (iterationsString == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(colorize("&cMessageIteratorActionType used, but placeholders does not have a value for 'iterations'."));
        }
        try {
            iterations = Integer.parseInt(iterationsString);
        }catch (Exception e){
            Bukkit.getServer().getConsoleSender().sendMessage(colorize("&cMessageIteratorActionType used, but 'iterations' value not an integer."));
        }

        for (int i = 0; i < iterations; i++) {
            List<String> message = list;
            for (String placeHolder : map.keySet()) {
                String replacement = map.get(placeHolder);
                Pattern pattern = Pattern.compile("%([a-zA-Z-_]+)(\\d+)%");
                Matcher matcher = pattern.matcher(placeHolder);

                if (!matcher.matches()) {
                    continue;
                }
                String numberPart = matcher.group(2);
                int number = Integer.parseInt(numberPart);
                if (number != i){
                    continue;
                }
                String wordPart = matcher.group(1);
                message = replace(message, "%"+wordPart+"%", replacement);
            }
            for (String s : message) {
                player.sendMessage(s);
            }
        }
    }
}
