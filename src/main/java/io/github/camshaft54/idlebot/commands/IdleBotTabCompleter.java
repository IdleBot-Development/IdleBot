package io.github.camshaft54.idlebot.commands;

import io.github.camshaft54.idlebot.util.enums.CommandTabCompletion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class IdleBotTabCompleter implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 1) {
            for (CommandTabCompletion cmd : CommandTabCompletion.values()) {
                completions.add(cmd.name().toLowerCase());
            }
        } else if (args.length == 2) {
            if (CommandTabCompletion.get(args[0]) != null && CommandTabCompletion.get(args[0]).getArgs().length > 0) {
                completions.addAll(Arrays.asList(CommandTabCompletion.get(args[0]).getArgs()[0]));
            }
        } else if (args.length == 3) {
            if (CommandTabCompletion.get(args[0]) != null && CommandTabCompletion.get(args[0]).getArgs().length > 1) {
                completions.addAll(Arrays.asList(CommandTabCompletion.get(args[0]).getArgs()[1]));
            }
        }
        final List<String> finalCompletions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[args.length-1], completions, finalCompletions);
        Collections.sort(finalCompletions);
        return finalCompletions;
    }
}
