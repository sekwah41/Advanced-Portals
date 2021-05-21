let versionRegex = /(\nversion:\s)([0-9.-]+)/;
let velocityVersionRegex = /(\sversion\s=\s")([0-9.-]+)("\))/;


const ymlUpdater = {
  updater: {
    'readVersion': (contents) => {
      return versionRegex.exec(contents)[2];
    },
    'writeVersion': (contents, version) => {
      return contents.replace(versionRegex, `$1${version}`);
    }
  }
}

const bungee = {
  filename: 'src/main/resources/bungee.yml',
  ...ymlUpdater,
}

const plugin = {
  filename: 'src/main/resources/plugin.yml',
  ...ymlUpdater,
}

const velocity_plugin = {
  filename: 'src/main/java/com/sekwah/advancedportals/velocity/AdvancedPortalsPlugin.java',
  updater: {
    'readVersion': (contents) => {
      return velocityVersionRegex.exec(contents)[2];
    },
    'writeVersion': (contents, version) => {
      return contents.replace(velocityVersionRegex, `$1${version}$3`);
    }
  }
}

const files = [plugin, velocity_plugin, bungee];

module.exports = {
  bumpFiles: files,
  packageFiles: files,
  // In case you need to force a version change (mostly due to change of scope of the update e.g. major now instead of patch)
  //releaseAs: '1.0.0',
  header:"# Changelog\n" +
      "\n" +
      "All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.\n" +
      "\n" +
      "For the release changelogs see [CHANGELOG.md](CHANGELOG.md)  \n" +
      "For the snapshot changelogs see [SNAPSHOT_CHANGELOG.md](SNAPSHOT_CHANGELOG.md)\n",
}
