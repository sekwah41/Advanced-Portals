package com.sekwah.advancedportals.forge.connector.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.forge.connector.container.ForgeCommandSenderContainer;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class ForgeCommandHandler implements Command<CommandSourceStack>, SuggestionProvider<CommandSourceStack>, Predicate<CommandSourceStack> {
    private final CommandTemplate commandExecutor;

    public ForgeCommandHandler(CommandTemplate commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return Command.SINGLE_SUCCESS;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        var endsWithSpace = context.getInput().endsWith(" ");

        var args = new ArrayList<>(Arrays.stream(context.getInput().split(" ")).toList());

        args.removeFirst();

        if(endsWithSpace) {
            args.add("");
        }

        List<String> results = commandExecutor.onTabComplete(
                new ForgeCommandSenderContainer(context.getSource()),
                args.toArray(new String[0])
        );

        builder = builder.createOffset(builder.getInput().lastIndexOf(' ') + 1);

        if (results == null) {
            return Suggestions.empty();
        }

        for (String s : results) {
            builder.suggest(s);
        }

        return builder.buildFuture();
    }

    @Override
    public boolean test(CommandSourceStack commandSourceStack) {
        // TODO check if the command sender has permission to execute the command
        return true;
    }
}
