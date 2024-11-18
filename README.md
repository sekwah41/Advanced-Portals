![Advanced portals](https://i.imgur.com/UIF6cQR.png)

Need to write a new readme for this.

Also I will set up release please to work with the below types for pretty changelogs.

The re-code is based off a mix of the original version, and the original re-code that was abandoned [see here](https://github.com/sekwah41/Advanced-Portals/tree/recode/src/main/java/com/sekwah/advancedportals).

Part of this are currently a mess in terms of package organising, though any API changes will be documented in the changelog as well as the main registry classes should be relatively solid.

The goal of this rewrite is to make it easier to port to other platforms as well as add extra tags.

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
