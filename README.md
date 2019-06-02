![Advanced Portals](https://i.imgur.com/UIF6cQR.png)

Advanced Portals [![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=master)](https://travis-ci.org/sekwah41/Advanced-Portals/branches)
==============
An advanced portals plugin for bukkit made by sekwah41 designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations. This plugin not only enable normal teleportation but also cross server teleportation for networks using bungee.

# Branch Layout
 * [master](https://github.com/sekwah41/Advanced-Portals/) (Release Build) [![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=master)](https://travis-ci.org/sekwah41/Advanced-Portals/branches)  
 * [dev](https://github.com/sekwah41/Advanced-Portals/tree/dev) (Dev Build) [![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=dev)](https://travis-ci.org/sekwah41/Advanced-Portals/branches)  
 * [recode](https://github.com/sekwah41/Advanced-Portals/tree/recode) (Recode) [![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=recode)](https://travis-ci.org/sekwah41/Advanced-Portals/branches)  

Once the recode is done the master branch will be releases and the dev branch will be where work is done.

# Build Instructions
You must have gradle installed then run "gradle build". Once the build is complete you will have a useable jar file in the folder named "build/lib" listed as Advanced-Portals-x.x.x-snapshot.jar

# Help
[List Of Commands](https://github.com/sekwah41/Advanced-Portals/wiki/Commands)

[Spigot Page](https://www.spigotmc.org/resources/advanced-portals.14356/)

[Bukkit Page](http://dev.bukkit.org/bukkit-plugins/advanced-portals/)

# Usage Data
![Global Plugin Stats](http://i.mcstats.org/AdvancedPortals/Global+Statistics.borderless.png)

The api isn't implemented in this version, sorry for any inconvenience. Check the recode tree for possibly a working recode at some point.

To get the needed bukkit versions download the spigot buildtools and run the appropriate commands for the versions listed in the pom file.
e.g. "java -jar BuildTools.jar --rev 1.10.2" (Enter this into git bash)

Use the versions to test code before you turn it into reflection or suggest it to be turned into reflection.

You should never use direct craftbukkit references in public builds as it would only work in one spigot version
