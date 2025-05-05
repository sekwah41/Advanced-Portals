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
        var input = context.getInput().split(" ");
        var command = input[0];
        var args = new String[input.length - 1];
        System.arraycopy(input, 1, args, 0, input.length - 1);
        commandExecutor.onCommand(new ForgeCommandSenderContainer(context.getSource()), command, args);

        return Command.SINGLE_SUCCESS;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
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

        var index = builder.getInput().lastIndexOf(' ') + 1;

        if(builder.getInput().endsWith(" ")) {
            index = builder.getInput().trim().length() + 1;
        }

        builder = builder.createOffset(index);

        if (results == null) {
            return Suggestions.empty();
        }

        for (String s : results) {
            builder.suggest(s);
        }

        return builder.buildFuture();
    }

    // The commands themselves are not locked off, so we always return true. But the underlying commands may be.
    @Override
    public boolean test(CommandSourceStack commandSourceStack) {
        return true;
    }
}
