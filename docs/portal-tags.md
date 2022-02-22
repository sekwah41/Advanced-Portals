---
sidebar_position: 3
description: Information on all portal tags included in Advanced Portals.
---

# Portal Tags

Below is a list of currently accepted tags for portals. Only the name: tag is necessary though other tags may be needed to give the portal's functionality.

Example use: `/portal create name:testportal desti:somedesti triggerblock:WATER`

## Necessary Tags

### `name:(name for portal)`

Sets the name for the portal. These have to be unique across the whole server.

## Available Tags

These add or alter specific functionality for the portals.

### `destination:(destination name)` or `desti:(destination name)`

Sets the desired destination for the portal. If this is defined if the portal is triggered, the player will be teleported to the desired location.

### `delayed:true`

The portal uses the portal event rather than movement events. Will trigger with a delay on portal events. While this does the normal delayed behaviour for nether portals (instant if you are in creative), the end portals will trigger instantly anyway as that is normal behaviour for them.

### `triggerblock:(material)`

Sets the material of the trigger block of the portal. This is the material that you need to be inside to trigger the portal. Suggestions/Tab complete will work for the names though they are in block caps and use the spigot names 


[A full list of materials for 1.13+ can be found here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)

[And a 1.12 and below list here](https://helpch.at/docs/1.12.2/org/bukkit/Material.html)

The most common types generally used are `NETHER_PORTAL`, `END_PORTAL`, `END_GATEWAY` and `WATER`

### `bungee:(server_name)`

Specifies which server to send the player too if connected through bungee. It needs to be the same as whatever you would use for `/server (server_name)` to swap manually.

### `message:(word)` or `message:"(some sentence you want)"`

Sets the warp message for the portal. Colour codes can be used e.g. &c is red. [See here for color codes](https://minecraft.gamepedia.com/Formatting_codes).

### `permission:(some.custom.test.perms)`

Sets what permission you need to use the portal(can be customised to anything)

### `command:(command) or command:"(multi-word command)"`

Whatever is typed in here the player will execute as a command, there are several flags you can use listed below to alter the execution, however only one can be used at once.

You can also use `@player` to place the teleporting user's name into the command.

Example: `command:"#say @player has triggered a console command portal"`

A common request is info about how to rtp players.
We recommend using [BetterRTP](https://www.spigotmc.org/resources/betterrtp-random-wild-teleport.36081/) and using the `rtp player` command.
This can be specified using the tag `command:"#rtp player @player (worldname)"`. This will automatically insert the player name and run the command in the console.

### Command Tag Flags

You can only use one of these at the start of a command tag.

`!` Execute command as op

`^` Execute command with * permission

`#` Execute command as console

`%` Send command to bungee
