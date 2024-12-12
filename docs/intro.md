---
sidebar_position: 1
description: Quick introduction to Advanced Portals.
---

# Tutorial Intro

:::info

**V2.0.0+ / recode info**

The re-code should detect that you have the old data and create copies in the new format, however you will need to re-do the config.

If the portals to not import for any reason, you can manually trigger the import by running `/portals import`.

Do not worry. The original data will not be deleted, and you can revert to older versions if you run into problems.

:::

Here is a YouTube video made by [LtJim007](https://www.youtube.com/channel/UCZvGH5UFnZGHL7t11RLhg2w) explaining the basics.

:::info
The video is for older versions so if you are using 2.0.0+ and a command does not work, please refer to the [commands page](./commands.md) for the latest information.
:::

<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/nkOeMUkYz3Y" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

In summary: you can create destinations for your portals with `/desti`, and create portals to them with `/portal create (tags...)`. You will need to provide a *trigger element* for your portals, for example water / lava / nether swirls, and replace the inside of your portal with it for the portal to function.

Here's a step by step guide.

1. Create your fancy portal in a standard Minecraft fashion. Leave the portion where the portal itself will be empty.

2. Go to the location you want your portal to transport players to. Run `/desti create name-of-destination`.

2. Go back to your portal. Take an iron axe (the special portal tool, by default: configurable). Left-click in the upper left of the portal, and right-click in the bottom right of the portal.

3. Run `/portal create name:name-of-your-portal desti:name-of-destination triggerblock:name-of-trigger-element`. This is a basic example - more options can be found on the [tags page](./portal-tags.md).

If you mess up, you can run `/desti remove name-of-destination` and `/portal remove name-of-portal` to remove a destination and a portal, respectively.
