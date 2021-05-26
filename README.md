![Advanced portals](https://i.imgur.com/UIF6cQR.png)

Advanced Portals [![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=recode)](https://travis-ci.org/sekwah41/Advanced-Portals/branches)
================
An advanced portals plugin for bukkit made by sekwah41 designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations. This plugin not only enable normal teleportation but also cross server teleportation for networks using bungee.

**NOTE:** This is the rewrite that is in progress to also allow multiple platforms of server mods, if you are looking for the old version or this version isn't finished check the branch [portals-old](https://github.com/sekwah41/Advanced-Portals/tree/portals-old)

# Module Layout
We are still planning on how to distribute the files though are planning to make one jar at least run spigot and the proxies.
Originally the API was also supposed to be a separate module though due to possible forge versions and other reasons decided
it would probably be best to not separate it out.
## Common
* **core**: Functional code with an abstraction layer to create consistent behavior between platforms as well as making addons.
* **lang**: Translations of the plugin available by default.

## Implementations
* **spigot**: All functionality for spigot to connect the abstraction layer.

More will be coming once we have finished the recode.

## Proxy's
* **bungee**: Bungee plugin to allow secure connections between severs.
* **velocity**: Velocity proxy plugin,

# Help
[Command Documentation & Guides](https://www.guilded.gg/Sekwah/groups/MDqAZyrD/channels/72ffdaa3-9273-4722-bf47-b75408b371af/docs/1807463914)

[List Of Commands](https://github.com/sekwah41/Advanced-Portals/wiki/Commands)

[Spigot Page](https://www.spigotmc.org/resources/advanced-portals.14356/)

[Bukkit Page](http://dev.bukkit.org/bukkit-plugins/advanced-portals/)

# Usage Data
Usage stats can be found here https://bstats.org/plugin/bukkit/AdvancedPortals

# Supported Platforms

 * Spigot/Craftbukkit (planned for release of recode)
 * Forge (possibly, tho not priority)

# API
Once the API is fully sorted we will look into adding auto deploying of the API with updates.

# Contributing
Please ensure that your commits are in the following style for PR's

https://www.conventionalcommits.org/en/v1.0.0/

Accepted tags mostly follow the Angular style and are meant to only loosely be followed.
When commits close an issue refer in the commit description in the following style (Refs #1, #2, #3)
## Types available
* **build**: Changes that affect the build system or external dependencies
* **ci**: Changes to our CI configuration files and scripts
* **docs**: Documentation only changes
* **feat**: A new feature
* **fix**: A bug fix
* **perf**: A code change that improves performance
* **refactor**: A code change that neither fixes a bug nor adds a feature
* **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
<!---
We don't currently do tests. But in case.
 * **test**: Adding missing tests or correcting existing tests
-->

## Scopes available
The scopes available should be the specific modules being worked on. E.g. core, spigot, docs
