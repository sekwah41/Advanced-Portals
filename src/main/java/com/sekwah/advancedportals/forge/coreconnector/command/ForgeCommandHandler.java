package com.sekwah.advancedportals.forge.coreconnector.command;

import com.sekwah.advancedportals.core.commands.CommandTemplate;
import com.sekwah.advancedportals.forge.coreconnector.container.ForgeCommandSenderContainer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class ForgeCommandHandler extends CommandBase
{

    private final String commandName;
    private final CommandTemplate commandExecutor;

    public ForgeCommandHandler(String commandName, CommandTemplate commandExecutor) {

        this.commandName = commandName;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public String getName() {
        return commandName;
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender sender, String[] strings) throws CommandException {
        this.commandExecutor.onCommand(new ForgeCommandSenderContainer(sender), this.commandName, strings);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer mcServer, ICommandSender sender, String[] strings, @Nullable BlockPos location) {
        return this.commandExecutor.onTabComplete(new ForgeCommandSenderContainer(sender), strings);
    }
}