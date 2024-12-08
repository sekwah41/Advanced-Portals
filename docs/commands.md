---
sidebar_position: 2
description: Information on all commands included in Advanced Portals, including usage and permissions.
---

# Commands

:::warning

**V2.0.0+ / recode info**

The current documentation is related to versions below 2.0.0. While most should apply to the latest version, some features may have changed or be missing.
The documentation will be updated soon.

You can use the `/portals convert` command to port your portals to the latest version. Do not worry. The original data will not be deleted, and you can revert to older versions if you run into problems.

:::

All commands included in Advanced Portals are listed below along with their permissions.

**Note:** all `/portal` commands can also be used with `/advancedportals` or `/aportals` instead.

Same goes for all /destination commands can be used with /desti

Also if you want blocks such as nether portals not to break when being placed you will need to define the portal before placing them. This is so the physics updates know not to mess with vanilla portals being broken or other plugins :)


## Portal Command
Usable Alias: `/portal` `/ap` `/portals` `/aportal` `/advancedportals`

### `/portal create (tags...)`

**Permission:** `advancedportals.portal`

This command is used to create a portal. The behaviour of the portal can be determined by the tags given (see list below), but a name must tag must always be given `name:some_name_here`


As a side note, make sure to check the `triggerblock:` has been set if you are not using nether portal blocks. These are blocks that **you need to be INSIDE** so blocks such as water and even cobwebs will work, but cobblestone will not be a suitable triggerblock.

For a list of tags and info, check out the [tags page](./portal-tags.md).

### `/portal selector` or `/portal wand`

**Permission:** `advancedportals.createportal`

This gives you the mighty portal axe, if UseOnlyServerMadeAxe is true then this one will still work, but the normal iron axe will still be available to be used in survival for admins. (instead of always trying to make portals)

### `/portal portalblock`

**Permission:** `advancedportals.portal`

Gives you a portal block that you can build with. (If the rotation is in the wrong place one next to it and then replace it to get the right rotation)

### `/portal endportalblock`

**Permission:** `advancedportals.portal`

Gives you an end portal block that you can build with.

### `/portal gatewayblock`

**Permission:** `advancedportals.portal`

Gives you an end gateway block that you can build with.

### `/portal disablebeacon (portalname)`

**Permission:** `advancedportals.build`

Needs DisableGatewayBeam to be set to true in the config. Though also triggers on create or chunk load. This is just a backup method.

### `/portal select`

**Permission:** `advancedportals.createportal`

After the command is entered, punch inside a portal region, and it will select that portal.

### `/portal unselect`

**Permission:** `advancedportals.createportal`

Use to remove the current portal selection. (as it can mess with certain commands)

### `/portal remove`

**Permission:** `advancedportals.removeportal`

Enter this command to destroy a portal with a set name. If the argument is left blank, it will destroy the currently selected portal.

### `/portal help`

Displays the help message.

## Destination Command

Usable Alias: `/desti` `/destination`

Permission (applies to all): `advancedportals.desti`

### `/desti create`

This the command creates a new destination with the location data from your player (your player position and direction your facing).

### `/desti remove`

Remove a destination with a specific name. (portals will still attempt to warp to this name but say no destination exists)

### `/desti list`

A list of created destinations.

### `/desti warp (desti name)`

Teleport to the named destination.
