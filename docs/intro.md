---
sidebar_position: 1
description: Quick introduction to Advanced Portals.
---

# Tutorial Intro

Here is a YouTube video made by [LtJim007](https://www.youtube.com/channel/UCZvGH5UFnZGHL7t11RLhg2w) explaining the basics.

<iframe width="560" height="315" src="https://www.youtube-nocookie.com/embed/nkOeMUkYz3Y" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

In summary: you can create destinations for your portals with `/desti`, and create portals to them with `/portal create (tags...)`. You will need to provide a *trigger element* for your portals, for example water / lava / nether swirls, and replace the inside of your portal with it for the portal to function.

Here's a step by step guide.

1. Create your fancy portal in a standard Minecraft fashion. Leave the portion where the portal itself will be empty.

2. Go to the location you want your portal to transport players to. Run `/desti create name-of-destination`.

2. Go back to your portal. Take an iron axe (the special portal tool, by default: configurable). Left-click in the upper left of the portal, and right-click in the bottom right of the portal.

3. Run `/portal create name:name-of-your-portal desti:name-of-destination triggerblock:name-of-trigger-element`. This is a basic example - more options can be found on the [tags page](./portal-tags.md).

4. Replace the empty air in your portal with your trigger element by running `/fill bottom-right-coords upper-right-coords trigger-element`. The coordinates should have shown up in chat when you left-and-right-clicked with the iron axe.
  - If your portal isn't rectangular: try `/fill`ing the area with glass or another block, breaking the glass in the shape of the portal, and then `/fill` the area again with your transportation trigger block. Then break the remaining glass.

If you mess up, you can run `/desti remove name-of-destination` and `/portal remove name-of-portal` to remove a destination and a portal, respectively.
