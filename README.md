Advanced Portals <a href="https://travis-ci.org/sekwah41/Advanced-Portals/branches">![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=spigot-dev)</a>
================
An advanced portals plugin for bukkit made by sekwah41 designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations. This plugin not only enable normal teleportation but also cross server teleportation for networks using bungee.

**NOTE:** This is the rewrite that is in progress to also allow multiple platforms of server mods, if you are looking for the old version or this version isn't finished check the branch <a href="https://github.com/sekwah41/Advanced-Portals/tree/portals-old">portals-old</a>

Also please use the markdown and not html for updates to this file, references can be found [here](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)

#Branch Layout
 * [master](https://github.com/sekwah41/Advanced-Portals/) (core code) ![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=master)
 * [core-dev](https://github.com/sekwah41/Advanced-Portals/tree/core-dev) (Core Dev Code) ![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=core-dev)
 * [spigot](https://github.com/sekwah41/Advanced-Portals/tree/spigot) (Spigot/Craftbukkit) ![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=spigot)
 * [spigot-dev](https://github.com/sekwah41/Advanced-Portals/tree/spigot-dev) (Spigot/Craftbukkit Dev Code) ![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=spigot-dev)
 * [sponge](https://github.com/sekwah41/Advanced-Portals/tree/sponge") (Sponge Powered) ![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=sponge)

#Help

[List Of Commands](https://github.com/sekwah41/Advanced-Portals/wiki/Commands)

[Spigot Page](https://www.spigotmc.org/resources/advanced-portals.14356/)

[Bukkit Page](http://dev.bukkit.org/bukkit-plugins/advanced-portals/)

#Supported Platforms
 * Spigot/Craftbukkit (planned for release of recode)
 * Sponge Powered (planned)

#API
Still needs major work on the documentation however javadocs will be made available on my website on release.

May be out of date but I will keep whatever javadocs there are updated every now and then here
http://www.sekwah.com/javadocs/advancedportals/

#Usage Data

![Global Plugin Stats](http://i.mcstats.org/AdvancedPortals/Global+Statistics.borderless.png)

The api isn't implemented in this version, sorry for any inconvenience. Check the recode tree for possibly a working recode at some point.

To get the needed bukkit versions download the spigot buildtools and run the appropriate commands for the versions listed in the pom file.
e.g. "java -jar BuildTools.jar --rev 1.10.2" (Enter this into git bash)

Use the versions to test code before you turn it into reflection or suggest it to be turned into reflection.

You should never use direct craftbukkit references in public builds as it would only work in one spigot version
