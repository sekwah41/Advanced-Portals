Advanced Portals <a href="https://travis-ci.org/sekwah41/Advanced-Portals/branches">![Build Status](https://travis-ci.org/sekwah41/Advanced-Portals.svg?branch=master)</a>
================

<p>An advanced portals plugin for bukkit made by sekwah41 designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations. This plugin not only enable normal teleportation but also cross server teleportation for networks using bungee.</p>

<p><b>NOTE:</b> This is the rewrite that is in progress to also allow multiple platforms of server mods, if you are looking for the old version or this version isn't finished check the branch <a href="https://github.com/sekwah41/Advanced-Portals/tree/portals-old">portals-old</a></p>

<h1>Branch Layout</h1>
<ul>
    <li><a href="https://github.com/sekwah41/Advanced-Portals/">master</a> (core code)</li>
    <li><a href="https://github.com/sekwah41/Advanced-Portals/tree/spigot">spigot</a> (Spigot/Craftbukkit) (still needs to be made)</li>
    <li><a href="https://github.com/sekwah41/Advanced-Portals/tree/sponge">sponge</a> (Sponge Powered) (still needs to be made)</li>
</ul>

<h1>Help</h1>

<a href="https://github.com/sekwah41/Advanced-Portals/wiki/Commands">List Of Commands</a>

<a href="https://www.spigotmc.org/resources/advanced-portals.14356/">Spigot Page</a>

<a href="http://dev.bukkit.org/bukkit-plugins/advanced-portals/">Bukkit Page</a>
<br>

<h1>Supported Platforms</h1>
<ul>
    <li>Spigot/Craftbukkit (planned for release of recode)</li>
    <li>Sponge Powered (planned)</li>
</ul>

<h1>API</h1>
Still needs major work on the documentation however javadocs will be made available on my website on release.

May be out of date but I will keep whatever javadocs there are updated every now and then here
http://www.sekwah.com/javadocs/advancedportals/

<h1>Usage Data</h1>

<img src="http://i.mcstats.org/AdvancedPortals/Global+Statistics.borderless.png" alt="Global Plugin Stats" title="Global Plugin Stats">


The api isn't implemented in this version, sorry for any inconvenience. Check the recode tree for possibly a working recode at some point.

<p>To get the needed bukkit versions download the spigot buildtools and run the appropriate commands for the versions listed in the pom file.</p>
<p>e.g. "java -jar BuildTools.jar --rev 1.10.2" (Enter this into git bash)</p>

<p>Use the versions to test code before you turn it into reflection or suggest it to be turned into reflection.</p>

<p>You should never use direct craftbukkit references in public builds as it would only work in one spigot version</p>
