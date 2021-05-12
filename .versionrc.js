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
const plugin = {
  filename: 'src/main/resources/plugin.yml',
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
  packageFiles: files
}
