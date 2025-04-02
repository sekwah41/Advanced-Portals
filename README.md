[//]: # (This is a link to the raw location so that the image can be displayed from pages like Modrinth)
![Advanced portals](https://raw.githubusercontent.com/sekwah41/Advanced-Portals/refs/heads/main/docs/logo.png)

[![Discord](https://img.shields.io/discord/168282484037910528.svg?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/fAJ3xJg)
[![](https://img.shields.io/github/contributors/sekwah41/Advanced-Portals.svg?style=for-the-badge&logo=github)](https://github.com/sekwah41/Advanced-Portals/graphs/contributors)
[![](https://img.shields.io/github/issues/sekwah41/Advanced-Portals.svg?style=for-the-badge&logo=github)](https://github.com/sekwah41/Advanced-Portals/issues)
[![](https://img.shields.io/github/issues-pr/sekwah41/Advanced-Portals.svg?style=for-the-badge&logo=github)](https://github.com/sekwah41/Advanced-Portals/pulls)
[![](https://img.shields.io/github/forks/sekwah41/Advanced-Portals.svg?style=for-the-badge&logo=github)](https://github.com/sekwah41/Advanced-Portals/network/members)
[![](https://img.shields.io/github/stars/sekwah41/Advanced-Portals.svg?style=for-the-badge&logo=github)](https://github.com/sekwah41/Advanced-Portals/stargazers)
[![](https://img.shields.io/github/license/sekwah41/Advanced-Portals.svg?logo=github&style=for-the-badge)](https://github.com/sekwah41/Advanced-Portals/blob/master/LICENSE.md)

Advanced Portals
==============
An advanced portals plugin designed to have a wide range of features which are easy to use. It adds a bunch of commands to create and edit portals and destinations.

# Usage

Check out the [Tutorial](https://advancedportals.sekwah.com/docs/intro), [List of Commands](https://advancedportals.sekwah.com/docs/commands), and [List of Portal Tags](https://advancedportals.sekwah.com/docs/portal-tags).

To be clear, the main target for this plugin is 1.13+ spigot. If you find any issues or inconsistencies with other versions please raise an issue!

# Download

- [Modrinth](https://modrinth.com/plugin/advanced-portals)
- [Bukkit](https://dev.bukkit.org/projects/advanced-portals)
- [Spigot](https://www.spigotmc.org/resources/advanced-portals.14356/)
- [Curseforge](https://www.curseforge.com/minecraft/bukkit-plugins/advanced-portals)
- [GitHub (Source Code)](https://github.com/sekwah41/Advanced-Portals/releases)

# Environment Configuration
The `.env.example` file is used to specify environments like `spigot`, `legacyspigot`, `forge-1-18`, and `neoforge-1-21`. Copy this file and rename it to `.env`, then uncomment the environments you want to run locally.

# Pre-commit Checks
Pre-commit checks are configured in `.github/workflows/pre-commit-check.yaml`. The `pre-commit` tool is used for code consistency. If this is not run it will be automatically applied once pr's are merged so please do not make reformatting pr's or reformat any files you are working on heavily.

# Release Process
The release process is managed using `release-please` as configured in `.github/workflows/release-please.yml`.

# Snapshot Builds
Snapshot builds are defined in the workflow `.github/workflows/snapshots.yml`.

# Code Formatting
Code formatting is handled using `.clang-format` and `.clang-format-ignore` for ignored files. Though there seem some odd consistencies on different operating systems currently.

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
