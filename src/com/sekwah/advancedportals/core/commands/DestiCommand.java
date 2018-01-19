package com.sekwah.advancedportals.core.commands;

import com.sun.corba.se.impl.activation.CommandHandler;

import java.util.List;

public class DestiCommand implements CommandTemplate {

    @Override
    public void onCommand(CommandHandler sender, String[] args) {

    }

    @Override
    public List<String> onTabComplete(CommandHandler sender, String[] args) {
        return null;
    }
}
