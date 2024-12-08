---
sidebar_position: 3
description: Information on the optional WorldEdit integration in Advanced Portals, including usage and configuration
---

# WorldEdit Integration

**V2.0.0+ / recode info**

The current documentation is related to versions below 2.0.0. While most should apply to the latest version, some features may have changed or be missing.
The documentation will be updated soon.

You can use the `/portals convert` command to port your portals to the latest version. Do not worry. The original data will not be deleted, and you can revert to older versions if you run into problems.

This feature is completely missing from the recode currently, and will be added back in the future.

:::

You can configure AdvancedPortals to use WorldEdit instead of the Selection Tool from AdvancedPortals.

To enable the integration (add and) set the config option `WorldEditIntegration` to "true".

**This will disable the commands `/portal wand` (or `/portal selector`) and will use cuboid selections from WorldEdit instead.**

## Additional Commands

### Select Portal as WorldEdit selection

You can use `/portal we-selection` with a portal name to select the portal as cuboid worldedit selection.
This command becomes available when WorldEdit is installed.
