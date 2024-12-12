---
sidebar_position: 3
description: Information on the optional WorldEdit integration in Advanced Portals, including usage and configuration
---

# WorldEdit Integration

:::info

**V2.0.0+ / recode info**

The re-code should detect that you have the old data and create copies in the new format, however you will need to re-do the config.

If the portals to not import for any reason, you can manually trigger the import by running `/portals import`.

Do not worry. The original data will not be deleted, and you can revert to older versions if you run into problems.

:::

:::warning

This feature is completely missing from the recode currently, and will be added back in the future.

:::

You can configure AdvancedPortals to use WorldEdit instead of the Selection Tool from AdvancedPortals.

To enable the integration (add and) set the config option `WorldEditIntegration` to "true".

**This will disable the commands `/portal wand` (or `/portal selector`) and will use cuboid selections from WorldEdit instead.**

## Additional Commands

### Select Portal as WorldEdit selection

You can use `/portal we-selection` with a portal name to select the portal as cuboid worldedit selection.
This command becomes available when WorldEdit is installed.
