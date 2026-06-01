[//]: # (This is a link to the raw location so that the image can be displayed from pages like Modrinth)
![Advanced portals](https://raw.githubusercontent.com/sekwah41/Advanced-Portals/refs/heads/main/docs/logo.png)

An advanced portals plugin designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations.

# Usage

Check out the [Tutorial](https://advancedportals.sekwah.com/docs/intro), [List of Commands](https://advancedportals.sekwah.com/docs/commands), and [List of Portal Tags](https://advancedportals.sekwah.com/docs/portal-tags).

# Download
**Note:** Please make sure you download the right version for the right loader and mc version.

Also the proxy plugin for both velocity as well as bungee is contained within the jar files.

- [Modrinth](https://modrinth.com/plugin/advanced-portals)
- [Bukkit](https://dev.bukkit.org/projects/advanced-portals)
- [Spigot](https://www.spigotmc.org/resources/advanced-portals.14356/)
- [Curseforge](https://www.curseforge.com/minecraft/bukkit-plugins/advanced-portals)
- [Codeberg (Source Code)](https://codeberg.org/Sekwah/advanced-portals/)

# Contributing
Please ensure that your commits are in the following style for PR's

https://www.conventionalcommits.org/en/v1.0.0/

Also if you could, please run `pre-commit run --files ...` or `pre-commit run --all-files` to ensure that the code is formatted correctly.
You will need to have clang-format installed for this to work.

## Types available
* **build**: Changes that affect the build system or external dependencies
* **ci**: Changes to our CI configuration files and scripts
* **docs**: Documentation only changes
* **feat**: A new feature
* **fix**: A bug fix
* **perf**: A code change that improves performance
* **refactor**: A code change that neither fixes a bug nor adds a feature
* **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
<!---
We don't currently do tests. But in case.
 * **test**: Adding missing tests or correcting existing tests
-->

## Scopes available
The scopes available should be the specific modules being worked on. E.g. core, spigot, docs

## Documentation
That is handled on the [website](https://github.com/sekwah41/Advanced-Portals/tree/website) branch.

At some point I may merge this onto this branch to try to push for documentation changes with new features, though for now this works.

## CI
If you make any changes to the CI make sure that you test it locally first.

For example testing the snapshots locally is done via `woodpecker-cli exec --pipeline-event push .woodpecker/snapshot.yml`.

Ensure docker is set up locally and the woodpecker cli is installed.

If you are testing on an arm system you may need to set `DOCKER_DEFAULT_PLATFORM=linux/amd64`
