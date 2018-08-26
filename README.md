Advanced Portals ![Build Status](https://travis-ci.org/Plugineers/Advanced-Portals.svg?branch=portals-old)
================
<h2>Build Instructions</h2>
<p>(This part is mostly aimed for beginners in case they haven't seen a maven project)
<p>You must have maven installed then run "mvn clean install". Once the build is complete you will have a useable jar file in the folder named "target" listed as Advanced-Portals-x.x.x-snapshot.jar</p>

<h2>Plugin Info</h2>
<p><b>NOTE:</b> This is the old version of Advanced Portals, look in the appropriate branch for the newer versions.</p>

<p>An advanced portals plugin for bukkit made by sekwah41 designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations. This plugin not only enable normal teleportation but also cross server teleportation for networks using bungee.</p>

<a href="https://github.com/Plugineers/Advanced-Portals/wiki/Commands">List Of Commands</a>

<a href="https://www.spigotmc.org/resources/advanced-portals.14356/">Spigot Page</a>

<a href="http://dev.bukkit.org/bukkit-plugins/advanced-portals/">Bukkit Page</a>
<br>
<br>
<p>Usage Data</p>

<img src="http://i.mcstats.org/AdvancedPortals/Global+Statistics.borderless.png" alt="Global Plugin Stats" title="Global Plugin Stats">


The api isn't implemented in this version, sorry for any inconvenience. Check the recode tree for possibly a working recode at some point.

<p>To get the needed bukkit versions download the spigot buildtools and run the appropriate commands for the versions listed in the pom file.</p>
<p>e.g. "java -jar BuildTools.jar --rev 1.10.2" (Enter this into git bash)</p>

<p>Use the versions to test code before you turn it into reflection or suggest it to be turned into reflection.</p>

<p>You should never use direct craftbukkit references in public builds as it would only work in one spigot version</p>
