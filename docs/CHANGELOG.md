# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

For the release changelogs see [CHANGELOG.md](CHANGELOG.md)  
For the snapshot changelogs see [SNAPSHOT_CHANGELOG.md](SNAPSHOT_CHANGELOG.md)

## 0.6.0 (2021-05-19)


### Features

* **proxy:** Added a ForceEnableProxySupport config option ([99c810e](https://github.com/sekwah41/Advanced-Portals/commit/99c810e1beeee743734ec451ffe5df312eec8726))
* **proxy:** Added Velocity support ([b243b4d](https://github.com/sekwah41/Advanced-Portals/commit/b243b4d889b8039cb800d981d44d85da06ff62d5))
* **proxy:** Modern forwarding will be automatically detected. ([f3c8f73](https://github.com/sekwah41/Advanced-Portals/commit/f3c8f73975857a4e5d31a6a21111eee8b7888bdd))
* Added configurable proxy teleport delay ([a1121ad](https://github.com/sekwah41/Advanced-Portals/commit/a1121adc10addfcce515d1358d1274232109fdfd))

### 0.5.12

 * Added support for Velocity.
 * Also fixed some issues with entity teleporting.

### 0.5.11

 * Missing changelogs

### 0.5.10

 * Missing changelogs

### 0.5.10

 * Added fix for command portals spam triggering if they didn't teleport you out.
 * Made portals not activate if you were teleported into them by another portal (to allow linking zones like a star trek warp pad)

### 0.5.9

 * Missing changelogs

### 0.5.8

 * Missing changelogs

### 0.5.7


 * Extra checks added by @tmantti to fix slow connections to new servers from activating the destination location too quick.

### 0.5.6
* Fixed packet exploit affecting destinations (only effecting versions 0.5.0 to 0.5.5).

### 0.5.5
 * Added support for 1.16
 * Reworked chat menus to better use Spigot API
 * Changed edit menu to have Activate instead of Teleport to destination
 * Compat code changed. You must now use Spigot rather than CraftBukkit.

### 0.5.4
 * Added bungee backup methods to ensure bungee and desti work correctly together
 * Fixed protection region issue
 * Reworked the warp command and fixed the surrounding permissions
 * Disabling gateway beams is now enabled for placing the blocks as well as by a few other means

### 0.5.3

 * Fixed destination bug.

### 0.5.2

 * Fixed issue with bungee destinations.

### 0.5.1

 * Fixed warp permission info

### 0.5.0

 * Added command:
 * Fix for bungee warps

### 0.4.0

 * Individual portal cooldown added
 * Bungee improvements

### Earlier

 * See github releases and spigot pages for more info.
