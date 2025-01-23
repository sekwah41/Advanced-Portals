---
sidebar_position: 3
description: Information on all portal tags included in Advanced Portals.
---

# Portal Tags

:::info

**V2.0.0+ / recode info**

The re-code should detect that you have the old data and create copies in the new format, however you will need to re-do the config.

If the portals to not import for any reason, you can manually trigger the import by running `/portals import`.

Do not worry. The original data will not be deleted, and you can revert to older versions if you run into problems.

:::

Below is a list of currently accepted tags for portals. Only the name: tag is necessary though other tags may be needed to give the portal's functionality.

Example use: `/portal create name:testportal desti:somedesti triggerblock:WATER`

## Necessary Tags

### `name:(name for portal)`

Sets the name for the portal. These have to be unique across the whole server.

## Available Tags

These add or alter specific functionality for the portals.

### `destination:(destination name)` or `desti:(destination name)`

Sets the desired destination for the portal. If this is defined if the portal is triggered, the player will be teleported to the desired location.

### `portalEvent:true`
Alias: `delayed:true`

The portal uses the portal event rather than movement events. Will trigger with a delay on portal events. While this does the normal delayed behaviour for nether portals (instant if you are in creative), the end portals will trigger instantly anyway as that is normal behaviour for them.

### `triggerblock:(material)`

Sets the material of the trigger block of the portal. This is the material that you need to be inside to trigger the portal. Suggestions/Tab complete will work for the names though they are in block caps and use the spigot names 


[A full list of materials for 1.13+ can be found here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)

[And a 1.12 and below list here](https://helpch.at/docs/1.12.2/org/bukkit/Material.html)

The most common types generally used are `NETHER_PORTAL`, `END_PORTAL`, `END_GATEWAY` and `WATER`

### `bungee:(server_name)`

:::note

This should work for both bungee and velocity, however make sure you have bungee-plugin-message-channel set to true for it to work on velocity.

:::

Specifies which server to send the player too if connected through bungee. It needs to be the same as whatever you would use for `/server (server_name)` to swap manually.

### `proxy:(server_name)`

This uses the proxy plugin to communicate so does not require on the bungee channel. This also allows for the use of desti: along with the proxy tag to teleport to a destination on another server.

### `message:(word)` or `message:"(some sentence you want)"`

Sets the warp message for the portal. Colour codes can be used e.g. &c is red. [See here for color codes](https://minecraft.gamepedia.com/Formatting_codes).

### `permission:(some.custom.test.perms)`

Sets what permission you need to use the portal(can be customised to anything)

### `cooldown:(time in seconds)`

Sets the cooldown for that specific portal in seconds. This is per player and stored between logins so can be used set long delays.

### `command:(command) or command:"(multi-word command)"`

Whatever is typed in here the player will execute as a command, there are several flags you can use listed below to alter the execution, however only one can be used at once.

You can also use `@player` to place the teleporting user's name into the command.

Example: `command:"#say @player has triggered a console command portal"`

A common request is how to rtp players.
We recommend using [BetterRTP](https://www.spigotmc.org/resources/betterrtp-random-wild-teleport.36081/) and using the `rtp player` command.
This can be specified using the tag `command:"#rtp player @player (worldname)"`. This will automatically insert the player name and run the command in the console.

### Command Tag Flags

You can only use one of these at the start of a command tag.

`!` Execute command as op

`^` Execute command with * permission

`#` Execute command as console

`%` Send command to the proxy (requires the proxy plugin to be installed)

### `conditions:(comparison)`

For this tag you must have the [placeholder api](https://www.spigotmc.org/resources/placeholderapi.6245/) installed.

Example: `conditions:%player_food_level%>=5`

There are three kinds of comparisons that can be made: boolean, string and numeric.

If both sides are numeric, then you can use the following operators: `==`, `<`, `>`, `<=`, `>=`. Otherwise, an equal comparison will be made to check if both sides are the same.

You can find a list of placeholders [here](https://wiki.placeholderapi.com/users/placeholder-list/). However, some plugins may add their own custom placeholders, so that may not be all of them.
