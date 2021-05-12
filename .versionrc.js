let versionRegex = /(\nversion=)([0-9.-]+)/;

const tracker = {
  filename: 'gradle.properties',
  updater: {
    'readVersion': (contents) => {
      return versionRegex.exec(contents)[2];
    },
    'writeVersion': (contents, version) => {
      return contents.replace(versionRegex, `$1${version}`);
    }
  }
}

module.exports = {
  bumpFiles: [tracker],
  packageFiles: [tracker]
}
