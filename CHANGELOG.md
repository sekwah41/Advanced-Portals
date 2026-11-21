# Changelog

## [3.0.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.8.0...v3.0.0) (2026-11-21)


### ⚠ BREAKING CHANGES

* This is a complete recode, features may be missing or different, if there are issues please open an issue on github or contact us about it on discord.
* Permissions have been reworked, if you use a permissions manager you may need to change a few of the permissions
* disable proxy detection to avoid vulnerabilities (see full commit for more info)

### Features ✨

* add != ([676fcdc](https://github.com/sekwah41/Advanced-Portals/commit/676fcdcd2a577e94427f4ffe22e38b50ac6771b2))
* add a warning for no name being included ([045a0d9](https://github.com/sekwah41/Advanced-Portals/commit/045a0d9a2583ea5e427eee7423af780cd4bcf4f7))
* add abstracted game scheduler ([11698b2](https://github.com/sekwah41/Advanced-Portals/commit/11698b2a6500b0c5633b7291d8853ce00b338f9b))
* add base repos and portal data ([8e79e35](https://github.com/sekwah41/Advanced-Portals/commit/8e79e35be99aa0592b3fd6941f65f3ac8d6da832))
* add basic data loading and translations ([7fc644f](https://github.com/sekwah41/Advanced-Portals/commit/7fc644f18ae5f05aa6718c590428a67850b6a476))
* add basic desti create and remove ([d9ad211](https://github.com/sekwah41/Advanced-Portals/commit/d9ad21188937e6aac975f149d8301fd69ff23a7b))
* add command tags ([#443](https://github.com/sekwah41/Advanced-Portals/issues/443)) ([132fcf4](https://github.com/sekwah41/Advanced-Portals/commit/132fcf4ab8ebf6c345485b67751627b47429a6c5))
* add config to toggle physics events & add folia version ([#593](https://github.com/sekwah41/Advanced-Portals/issues/593)) ([67b2453](https://github.com/sekwah41/Advanced-Portals/commit/67b2453a4069ecfc552acb93f1a1a38d399d298e))
* add cooldown tag that is stored ([68c9b1e](https://github.com/sekwah41/Advanced-Portals/commit/68c9b1e544b31764142ef658da913cf6c6bdc0d7))
* add cost tag (vault support [#537](https://github.com/sekwah41/Advanced-Portals/issues/537)) ([34e70f8](https://github.com/sekwah41/Advanced-Portals/commit/34e70f848179a0df19db6daefce095d43b530ba6))
* add custom warp message config (messages.yml) ([349cc59](https://github.com/sekwah41/Advanced-Portals/commit/349cc5915fb2dc75620467f3a3890039575f41cd))
* add debug view for selection ([daf4e33](https://github.com/sekwah41/Advanced-Portals/commit/daf4e33e49948006a170e5cd17d03f79b10d3bef))
* add default trigger block if unspecified ([19f4c1b](https://github.com/sekwah41/Advanced-Portals/commit/19f4c1b606627232cc86236b07d1d0f66a5b3809))
* add destination and portal services ([8a34375](https://github.com/sekwah41/Advanced-Portals/commit/8a343756097cb120bd58e01618db40dbf0b55a15))
* Add destination teleport subcommand ([#420](https://github.com/sekwah41/Advanced-Portals/issues/420)) ([bfbc620](https://github.com/sekwah41/Advanced-Portals/commit/bfbc6201711dab40160637bd2578a4d2eae27ccf))
* add disable beacon sub command ([a550d2b](https://github.com/sekwah41/Advanced-Portals/commit/a550d2b3213abc5ec9e6f55d484b1df8bcc0070b))
* add error messages for the destination commands ([976d0c8](https://github.com/sekwah41/Advanced-Portals/commit/976d0c86bb7c1f86e84509338068605cf7c84e98))
* add global cooldown and throwback sounds ([12360e8](https://github.com/sekwah41/Advanced-Portals/commit/12360e8147b1f105b7ad04a634b5b4e8f333b0f5))
* add link to re-code progress ([#432](https://github.com/sekwah41/Advanced-Portals/issues/432)) ([7775682](https://github.com/sekwah41/Advanced-Portals/commit/77756823effb16e5fe2d23f4b1ace22133a78b18))
* add list command & update translation colors ([bb3748a](https://github.com/sekwah41/Advanced-Portals/commit/bb3748a9c5640e079d4d3af035ab23a93648943d))
* add messages and checks for commnad portal permissions ([fcd4d86](https://github.com/sekwah41/Advanced-Portals/commit/fcd4d864917cc7608281c4019d29998e277420e5))
* add multi destination randomisation ([a2dd5d6](https://github.com/sekwah41/Advanced-Portals/commit/a2dd5d6fcc3631b4e1d6651fcf0a4f3ab11a43f1))
* add permission tag & compatible block trigger list ([3bc19d7](https://github.com/sekwah41/Advanced-Portals/commit/3bc19d7c266c048d4c61e3585684392116a9f95d))
* add placeholder api conditions support ([c8e0a33](https://github.com/sekwah41/Advanced-Portals/commit/c8e0a331ac25cdf9204f50d9c4f0f1d5f0c44048))
* add portal and desti commands ([d355493](https://github.com/sekwah41/Advanced-Portals/commit/d355493fade46ef8c75728ac208b08dda2a0f519))
* add portal move command ([6b38910](https://github.com/sekwah41/Advanced-Portals/commit/6b38910c277cda3bb11560304324ef3900ca137a))
* add portal protections ([5a10808](https://github.com/sekwah41/Advanced-Portals/commit/5a108082118f367af995d8a905683b61eb644e31))
* add rename destination sub-command ([#548](https://github.com/sekwah41/Advanced-Portals/issues/548)) ([edff889](https://github.com/sekwah41/Advanced-Portals/commit/edff88984ad4b4421314179348477657e1488a58))
* add show desti command ([b9fa0bd](https://github.com/sekwah41/Advanced-Portals/commit/b9fa0bd0d48c2486af71f61a5bdcb0c90acc2d5e))
* add support for hex colour codes on spigot ([0bccc08](https://github.com/sekwah41/Advanced-Portals/commit/0bccc082e12a7ce85e27200ff75ad7c52dae81da))
* add support for legacy spigot 1.8 - 1.12 ([#520](https://github.com/sekwah41/Advanced-Portals/issues/520)) ([6634891](https://github.com/sekwah41/Advanced-Portals/commit/66348919d71153ac088c5c017541e2223e6778c8))
* add support for uppercase color codes as well as &#rrggbb to lang files ([0ddf838](https://github.com/sekwah41/Advanced-Portals/commit/0ddf8381b7fdb8571221946f0f6c2baa2971e03e))
* add the ability for tags to reject values ([58de375](https://github.com/sekwah41/Advanced-Portals/commit/58de37585efb8a59e455d4819de0c03603e712a2))
* add the ability to disable saving playerData files ([5eb3c88](https://github.com/sekwah41/Advanced-Portals/commit/5eb3c88bd411338ebac0e2732f3c0154c34d6068))
* add the ability to have multiple values for a tag ([f4cf399](https://github.com/sekwah41/Advanced-Portals/commit/f4cf399d6588e8bba0852372bc0e5373dff36892))
* add the ability to split tags based on a splitstring ([c572896](https://github.com/sekwah41/Advanced-Portals/commit/c572896e0a864d42758bba251d80ddbd3e9dcdb3))
* added tag priority sorting and delay tag ([4320a34](https://github.com/sekwah41/Advanced-Portals/commit/4320a34140944af17370edbebfca02eda72f516b))
* adding auto complete for tags ([eecb478](https://github.com/sekwah41/Advanced-Portals/commit/eecb478508230af38ff42281b68e0144d7cfd358))
* adding debug command ([865e219](https://github.com/sekwah41/Advanced-Portals/commit/865e219bfbd6fe60320ab20d20f52f12c768efac))
* auto import the old portals and destinations if the folders don't exist ([1107ee5](https://github.com/sekwah41/Advanced-Portals/commit/1107ee5cbb69842ccab77898993dd2886e32ffd6))
* block physics updates inside portal protection zones ([b899e05](https://github.com/sekwah41/Advanced-Portals/commit/b899e05a86dc2f04cfc3524187972adf79416a1a))
* bungee tags ([#448](https://github.com/sekwah41/Advanced-Portals/issues/448)) ([6fa5a27](https://github.com/sekwah41/Advanced-Portals/commit/6fa5a276213a0bc8993a6b6dcf1f0989f9744dfb))
* can create basic portals with tags ([de91d92](https://github.com/sekwah41/Advanced-Portals/commit/de91d92581335d734e8a2cc8b2d5f91e6d6c7514))
* center titles based on length ([3ab9b83](https://github.com/sekwah41/Advanced-Portals/commit/3ab9b836beae5049b410a6c638da63bb72313b46))
* Check if block/matieral is waterlogged ([#550](https://github.com/sekwah41/Advanced-Portals/issues/550)) ([9ab27cf](https://github.com/sekwah41/Advanced-Portals/commit/9ab27cf5948361584bad2f3fee7b00470906d47e))
* configure portal effects ([0297e05](https://github.com/sekwah41/Advanced-Portals/commit/0297e0524b0e694d19d8133c2c1451154f669cc5))
* desti move command ([52a6dcc](https://github.com/sekwah41/Advanced-Portals/commit/52a6dcc84fc361108b4211c4c657ba1f16f24ddb))
* desti tp another player to a destination ([#549](https://github.com/sekwah41/Advanced-Portals/issues/549)) ([f8ff095](https://github.com/sekwah41/Advanced-Portals/commit/f8ff0956ecd8dd86a063d33a62ed73b87e6fccd9))
* enhance the in portal mitigation ([b578cee](https://github.com/sekwah41/Advanced-Portals/commit/b578cee81db571cc86916e1fee9d5d2f59124a41))
* expose the internal registries and other elements through portals core for addons ([44f19a0](https://github.com/sekwah41/Advanced-Portals/commit/44f19a04be0820be75369e5389e895241563cb10))
* fail commands if the arguments have path escape characters ([#552](https://github.com/sekwah41/Advanced-Portals/issues/552)) ([8eba7c6](https://github.com/sekwah41/Advanced-Portals/commit/8eba7c6cef68efbbbe8bba5fe2710dca05f8580f))
* global portal permission ([#570](https://github.com/sekwah41/Advanced-Portals/issues/570)) ([0d56cd7](https://github.com/sekwah41/Advanced-Portals/commit/0d56cd70569a309600572a1872d6c5d0b536cdf4))
* improve onChunkLoad performance ([#567](https://github.com/sekwah41/Advanced-Portals/issues/567)) ([1682cbb](https://github.com/sekwah41/Advanced-Portals/commit/1682cbbf9cd6d1beedcb0dff6f17f2c65e7d831a))
* make config values import from the old files ([ccc75f2](https://github.com/sekwah41/Advanced-Portals/commit/ccc75f2ed4b8d4761bd251da1fa2d456035d88b4))
* make portal and desti implement the same tab auto complete ([105d793](https://github.com/sekwah41/Advanced-Portals/commit/105d793884d6dd149ffb4ef60848af5e9f32082c))
* make services available as singletons ([57b5045](https://github.com/sekwah41/Advanced-Portals/commit/57b504510251161fa661f5e75378b5c6fa544485))
* message tags & priority sorting ([#447](https://github.com/sekwah41/Advanced-Portals/issues/447)) ([4bba344](https://github.com/sekwah41/Advanced-Portals/commit/4bba3443f40a5a0f71c988fda0e102e3bc05c437))
* new tag parser ([453dfbf](https://github.com/sekwah41/Advanced-Portals/commit/453dfbff5a4da8b871625595dd0fc74fc8fe8933))
* Particle visualising ([#467](https://github.com/sekwah41/Advanced-Portals/issues/467)) ([de354ac](https://github.com/sekwah41/Advanced-Portals/commit/de354ac4e0bd9a4e5f7404df6e93cbbc18d74915))
* portal block placing checks surrounding blocks for orientation ([b6fd0c4](https://github.com/sekwah41/Advanced-Portals/commit/b6fd0c451701c31f51b98d78653fcfb24c5d4bc9))
* portal placer block rotate tool ([be0568c](https://github.com/sekwah41/Advanced-Portals/commit/be0568ce3b679f810735ddfb0db3e135d9044ee8))
* portals now trigger and nether portals are cancelled ([b42f52e](https://github.com/sekwah41/Advanced-Portals/commit/b42f52eda7167501eda31e6632cba8f65e83ba8d))
* prevent sending empty messages to sender/player ([31bb0cf](https://github.com/sekwah41/Advanced-Portals/commit/31bb0cfc767b3fb46729743a699592179d43ad15))
* proxy tag ([#451](https://github.com/sekwah41/Advanced-Portals/issues/451)) ([5e278e3](https://github.com/sekwah41/Advanced-Portals/commit/5e278e30f788e5cea0c6ccff29821388f31dd9c2))
* re-implement and clean up permissions ([#453](https://github.com/sekwah41/Advanced-Portals/issues/453)) ([70fe01c](https://github.com/sekwah41/Advanced-Portals/commit/70fe01c79d0c9683c8a8e2022f1cf8556e30aabc))
* register basic bungee plugin with metrics ([d39e077](https://github.com/sekwah41/Advanced-Portals/commit/d39e0772b947d6848c67f2e62a4cbd153559e445))
* remap and filter tags ([bbee31a](https://github.com/sekwah41/Advanced-Portals/commit/bbee31a305b3208c8ae25043a205ff2cd66f33ab))
* see portal info with /portal info (name) ([aa6bd2d](https://github.com/sekwah41/Advanced-Portals/commit/aa6bd2d4e2b39dfdd54e0dcb67bce0c4b9f2d89f))
* show triggerblocks in debug view ([e81a404](https://github.com/sekwah41/Advanced-Portals/commit/e81a4049de54aa2023d096d8f34ba996ac49d3d4))
* **spigot:** add command for porting portals and destinations to the new format ([3229f6e](https://github.com/sekwah41/Advanced-Portals/commit/3229f6e9be54640944b517e68ab08827ad36bccf))
* switch files over to using yaml ([#427](https://github.com/sekwah41/Advanced-Portals/issues/427)) ([2fbc6ef](https://github.com/sekwah41/Advanced-Portals/commit/2fbc6ef8a7ded837022879c10c78da96e7c5332a))
* switch jar to shadow the dependencies to avoid clashes with other plugins ([2d16c0f](https://github.com/sekwah41/Advanced-Portals/commit/2d16c0f79fb433b25c5b36ca652756f38a1a5482))
* temporary player data is now stored for more perma use ([6e13387](https://github.com/sekwah41/Advanced-Portals/commit/6e13387f464c7da29ca2af72fc4d0491f96cd217))
* trigger a release for the older version ([3f765d3](https://github.com/sekwah41/Advanced-Portals/commit/3f765d3e19fccae34d13abdf4d2c5bd1b7075016))
* update lang command to add missing translation keys ([0438192](https://github.com/sekwah41/Advanced-Portals/commit/043819200d55235d196a0799811d1a70e4051d12))


### Bug Fixes 🐛

* Add Author for Velocity Plugin ([#386](https://github.com/sekwah41/Advanced-Portals/issues/386)) ([dce42f5](https://github.com/sekwah41/Advanced-Portals/commit/dce42f54e08a7dea96bb6f622c6dae29c172cae2))
* add block place null checks ([#530](https://github.com/sekwah41/Advanced-Portals/issues/530)) ([0876584](https://github.com/sekwah41/Advanced-Portals/commit/087658409e7cf5968eb87fa663288d003facf16f))
* Add Selector Alias (Wand) in help message ([#387](https://github.com/sekwah41/Advanced-Portals/issues/387)) ([55cf5f7](https://github.com/sekwah41/Advanced-Portals/commit/55cf5f782c2f4a570f721599e035b3e2bb39936d)), closes [#333](https://github.com/sekwah41/Advanced-Portals/issues/333)
* allow null values for auto complete suggestions ([47b55b0](https://github.com/sekwah41/Advanced-Portals/commit/47b55b0e1eff7e9d0547187a852a25ca9430b5f8))
* apply the rotation and pitch of the offset to a tp ([d941acd](https://github.com/sekwah41/Advanced-Portals/commit/d941acd515c9ffbbd6dda0508c374949baea1d89))
* auto import of old data causing plugin to not load ([#505](https://github.com/sekwah41/Advanced-Portals/issues/505)) ([781d19b](https://github.com/sekwah41/Advanced-Portals/commit/781d19bd6ff5427a83e2317a8571474f2bb4ae37))
* change version and platform jar naming for maven usage ([5d6544d](https://github.com/sekwah41/Advanced-Portals/commit/5d6544d751462ba02cfa1457409cf14425dc3ebc))
* check for world existence ([bc9277a](https://github.com/sekwah41/Advanced-Portals/commit/bc9277aceab80bd9175e8a072900b62817bbf6f7))
* check the player is in the same world ([aec83e7](https://github.com/sekwah41/Advanced-Portals/commit/aec83e7181ecc90e6211d4d937ae6a25bacd6067))
* clean up conditions ([#560](https://github.com/sekwah41/Advanced-Portals/issues/560)) ([e61c69b](https://github.com/sekwah41/Advanced-Portals/commit/e61c69b514e724e962a339efb248fe5c68fd5b3a))
* command portals are being imported from old files with the wrong name ([db50264](https://github.com/sekwah41/Advanced-Portals/commit/db502644d6bddcbf4ed0a67a63772e4dade2aa5f))
* comment out log spam ([#500](https://github.com/sekwah41/Advanced-Portals/issues/500)) ([df41520](https://github.com/sekwah41/Advanced-Portals/commit/df415206ecafb915ebdc6b6082b4b81ed6eeafcf))
* core listener null pointer error for blockPlace ([#554](https://github.com/sekwah41/Advanced-Portals/issues/554)) ([627d0eb](https://github.com/sekwah41/Advanced-Portals/commit/627d0eba4ddbec588039c2c1c02c0be85e73badc))
* destination tags activation stage not triggering ([6967a82](https://github.com/sekwah41/Advanced-Portals/commit/6967a829e927206343e14006afb48730ccb4043b))
* disable proxy detection to avoid vulnerabilities (see full commit for more info) ([fe3fe69](https://github.com/sekwah41/Advanced-Portals/commit/fe3fe690469d72a5f10282662ebd1f5e99ad5dc7))
* disable spectators triggering portals by default ([e75d790](https://github.com/sekwah41/Advanced-Portals/commit/e75d7901edbbe172f3b36113d0ae643fd8cfd270)), closes [#491](https://github.com/sekwah41/Advanced-Portals/issues/491)
* disabling block place protection too ([755a4a5](https://github.com/sekwah41/Advanced-Portals/commit/755a4a56e61e114d5a8b8349be17f75d98c1ee90))
* flagged as fix to trigger a new release pipeline ([94cb820](https://github.com/sekwah41/Advanced-Portals/commit/94cb820287eeb18a217226ab8b370b6ea08c086a))
* folia version was breaking others compiling on lower java ([#594](https://github.com/sekwah41/Advanced-Portals/issues/594)) ([bdd2507](https://github.com/sekwah41/Advanced-Portals/commit/bdd2507d56fa57e0fdfc240063cd4f69561a2d8e))
* improve auto complete for triggerblock ([917ff8f](https://github.com/sekwah41/Advanced-Portals/commit/917ff8f9fac3839d0408f1f2bb3ab3630d305822))
* issue with compiling on java 8 ([779aeaa](https://github.com/sekwah41/Advanced-Portals/commit/779aeaaf88617b261904dbf961bf5c83fac4b7c5))
* issues with tab menu capitalisation ([0abdd7b](https://github.com/sekwah41/Advanced-Portals/commit/0abdd7be0e5a5c5f79bb81f3a8135d4ac527becb))
* language file duplication ([2b7e37f](https://github.com/sekwah41/Advanced-Portals/commit/2b7e37fa82123aa2512406213c2f57942de4655a))
* **legacy-spigot:** add null check to avoid throwing errors in console ([#545](https://github.com/sekwah41/Advanced-Portals/issues/545)) ([d7adc90](https://github.com/sekwah41/Advanced-Portals/commit/d7adc90e896dfe5b9c6fa178911483573acb2173))
* log to the console if the player adding a triggerblock is null ([bef2721](https://github.com/sekwah41/Advanced-Portals/commit/bef27213d27af19fd23c5199cbefe54a4b713cb0))
* lower case the naming across all release files ([cc69d91](https://github.com/sekwah41/Advanced-Portals/commit/cc69d91002979c7b1766e95c013422c967fa22ad))
* lower java requirement to 8 for spigot builds ([#527](https://github.com/sekwah41/Advanced-Portals/issues/527)) ([b33fd17](https://github.com/sekwah41/Advanced-Portals/commit/b33fd17a16d6a41231306742fed08596411c24de))
* null check for DestiTag ([#558](https://github.com/sekwah41/Advanced-Portals/issues/558)) ([769462e](https://github.com/sekwah41/Advanced-Portals/commit/769462e7eecb587cfd9ca1f293037573b30971e3))
* on interact null pointer exceptions ([57eecb2](https://github.com/sekwah41/Advanced-Portals/commit/57eecb2f663bda75b5657641cb1df6277ca06e73))
* permission issue for command portals ([7d88a09](https://github.com/sekwah41/Advanced-Portals/commit/7d88a09f3399d69c7ec8188ffc18082b89ef1bd6))
* Permission Tag ([#418](https://github.com/sekwah41/Advanced-Portals/issues/418)) ([1d00835](https://github.com/sekwah41/Advanced-Portals/commit/1d0083574369b0a5908bff1d0c9fb689896de4f0))
* portal activation issue ([997ca8f](https://github.com/sekwah41/Advanced-Portals/commit/997ca8f93293cd67625919e6c0858597ded7e7c4))
* portal command config reading ([62e323d](https://github.com/sekwah41/Advanced-Portals/commit/62e323dd2e1e73825be1fcc94106124231e47a01))
* portal import command checking yaml files ([9d1bc3c](https://github.com/sekwah41/Advanced-Portals/commit/9d1bc3c3249059c7dedbde05e706c61399cabaac))
* portalProtectionRadius spelt wrong in the config ([e609c10](https://github.com/sekwah41/Advanced-Portals/commit/e609c100adc82eb2a7acd976477e5091a1aa15c6))
* raw type usage in WarpEffectRegistry ([5f8994e](https://github.com/sekwah41/Advanced-Portals/commit/5f8994eaa5eafb854d532785faf81704062d6adb))
* references to services in portal subcommands ([5831f3f](https://github.com/sekwah41/Advanced-Portals/commit/5831f3f160cf0ad020e484bc5a2234a113c9321d))
* remove associated metadata when logging into a new server ([#397](https://github.com/sekwah41/Advanced-Portals/issues/397)) ([9c6943d](https://github.com/sekwah41/Advanced-Portals/commit/9c6943ddd4378671572c57e2d3b63e6bfcbd61ed))
* remove references to old material + make translatable ([5efe3ee](https://github.com/sekwah41/Advanced-Portals/commit/5efe3ee28dc985d80a9e1bc3e6e5c579e61b12c6))
* remove spammy debug logs from tab auto complete ([#476](https://github.com/sekwah41/Advanced-Portals/issues/476)) ([b9f4e7d](https://github.com/sekwah41/Advanced-Portals/commit/b9f4e7d52798c5861055d4a6010b6fe4006088ef))
* remove unneeded extra chat messages when creating a portal ([c97e3f6](https://github.com/sekwah41/Advanced-Portals/commit/c97e3f668dfd8425cd8800a377991cd5d8b3791c))
* remove unneeded type block type fetch in phyics events ([8cb9712](https://github.com/sekwah41/Advanced-Portals/commit/8cb9712a7a3461f23968228a23a8ec040bc4a10c))
* show page numbers on tab complete ([f685c56](https://github.com/sekwah41/Advanced-Portals/commit/f685c56b0751b99091c7d1cd7880edec3b1bb782))
* something in the old metrics class was triggering certain antivirus ([a49205c](https://github.com/sekwah41/Advanced-Portals/commit/a49205c4becd6eeef477ca463517e067067dc52f))
* stop portal packets still being forwarded to the client ([c9cb9e1](https://github.com/sekwah41/Advanced-Portals/commit/c9cb9e1776ad2b5f2c20aa1ffe8ab4ca087566d4))
* string commands were erroring ([#426](https://github.com/sekwah41/Advanced-Portals/issues/426)) ([f9694ca](https://github.com/sekwah41/Advanced-Portals/commit/f9694ca20533b370f7afc7941954a4a15fd55be4))
* subcommand registry boolean order ([8b2de76](https://github.com/sekwah41/Advanced-Portals/commit/8b2de76bd968e0469312e3d0c923e22a078d02da))
* switch all translation blocks to pick up color codes ([75f33cc](https://github.com/sekwah41/Advanced-Portals/commit/75f33ccb289a229d9d351f71898fa3d0ecf395fb))
* switch tab complete to empty arrays from  null returns ([87a4ec4](https://github.com/sekwah41/Advanced-Portals/commit/87a4ec4af76ba10ff521a5939d2d95a5fc2f9164))
* switch tag target config to private scope ([bd5888a](https://github.com/sekwah41/Advanced-Portals/commit/bd5888a6472d2d43b78abfdce5155748a1aa0496))
* tag quotes logic ([6b3938e](https://github.com/sekwah41/Advanced-Portals/commit/6b3938ef5f2ee463fe33f4563758b1d36d69da69))
* teleport call that doesn't check world ([#513](https://github.com/sekwah41/Advanced-Portals/issues/513)) ([78e5037](https://github.com/sekwah41/Advanced-Portals/commit/78e5037ebc9f1270ed32de7e9edab9616f04f4a3))
* trigger block validation ([#510](https://github.com/sekwah41/Advanced-Portals/issues/510)) ([4ac388a](https://github.com/sekwah41/Advanced-Portals/commit/4ac388ab36ecd7800b16bda5b7cebf2f33da906c))
* typo in tag api ([#396](https://github.com/sekwah41/Advanced-Portals/issues/396)) ([0509f2e](https://github.com/sekwah41/Advanced-Portals/commit/0509f2ec189a2e9da69d1fa91ab766783f2b3d85))
* update bstats metrics as it was triggering an antivirus false positive ([6275f2c](https://github.com/sekwah41/Advanced-Portals/commit/6275f2c6c686c407c74f81f6c5a64b8cb01a1ba9))
* update bungeecord-api version and add additional maven repository ([2fab3a5](https://github.com/sekwah41/Advanced-Portals/commit/2fab3a598ae2b3020688875b726e60c941eef838))
* use atomic and non capturing regex groups ([e264dc9](https://github.com/sekwah41/Advanced-Portals/commit/e264dc94be1354b3c660db412c45decacaf6bbb7))
* version number is now set correctly, was 1.0.0 ([c993f2d](https://github.com/sekwah41/Advanced-Portals/commit/c993f2dc41b15ae5d00bfa5db411a9d1c50210b5))


### Changes to Existing Features 🔧

* Show common trigger blocks for tag, then all if none match ([#468](https://github.com/sekwah41/Advanced-Portals/issues/468)) ([3ec80e2](https://github.com/sekwah41/Advanced-Portals/commit/3ec80e23967f05333b653c973bb491bf73c4b7c3))
* update the default visualisation sizes as they are quite small ([277e38e](https://github.com/sekwah41/Advanced-Portals/commit/277e38e7fe33ef8bd53b485a07f7f37271aa85f2))


### Miscellaneous Chores

* switch to using env ([6b8dfec](https://github.com/sekwah41/Advanced-Portals/commit/6b8dfecf9e9b885cb9aa7f9e394f4a285e023162))

## [2.8.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.7.3...v2.8.0) (2026-05-27)


### Features ✨

* expose the internal registries and other elements through portals core for addons ([622da55](https://github.com/sekwah41/Advanced-Portals/commit/622da554ce36eb3b69c7c646227bb77f3f7d6540))

## [2.7.3](https://github.com/sekwah41/Advanced-Portals/compare/v2.7.2...v2.7.3) (2026-05-27)


### Bug Fixes 🐛

* change version and platform jar naming for maven usage ([f058851](https://github.com/sekwah41/Advanced-Portals/commit/f058851a540c6e02b8f41a28434a56fb4e05029b))

## [2.7.2](https://github.com/sekwah41/Advanced-Portals/compare/v2.7.1...v2.7.2) (2026-05-27)


### Bug Fixes 🐛

* lower case the naming across all release files ([a793e0f](https://github.com/sekwah41/Advanced-Portals/commit/a793e0fb6c3f9489068e00cf646f843f38ff330a))

## [2.7.1](https://github.com/sekwah41/Advanced-Portals/compare/v2.7.0...v2.7.1) (2026-05-13)


### Bug Fixes 🐛

* folia version was breaking others compiling on lower java ([#594](https://github.com/sekwah41/Advanced-Portals/issues/594)) ([3fc7548](https://github.com/sekwah41/Advanced-Portals/commit/3fc75487049bb6c975cd4dfd9f1b2d70410b8918))

## [2.7.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.6.1...v2.7.0) (2026-05-13)


### Features ✨

* add config to toggle physics events & add folia version ([#593](https://github.com/sekwah41/Advanced-Portals/issues/593)) ([a0f8ce8](https://github.com/sekwah41/Advanced-Portals/commit/a0f8ce8d3e9bdbab4816ccd632958a5eecce5c96))
* add the ability to disable saving playerData files ([fce0283](https://github.com/sekwah41/Advanced-Portals/commit/fce02833901802c4228e8a3657dda90c3dd7acd4))
* prevent sending empty messages to sender/player ([b5227b6](https://github.com/sekwah41/Advanced-Portals/commit/b5227b6b6caafc7511469f96526e9d237935c64e))


### Bug Fixes 🐛

* issue with compiling on java 8 ([381851f](https://github.com/sekwah41/Advanced-Portals/commit/381851fbb56f856744d979280df51d41da5cd6d7))
* remove unneeded type block type fetch in phyics events ([ec0734e](https://github.com/sekwah41/Advanced-Portals/commit/ec0734ee67bbdb66b1a0a7e5e2db2a653aa69393))

## [2.6.1](https://github.com/sekwah41/Advanced-Portals/compare/v2.6.0...v2.6.1) (2026-01-28)


### Bug Fixes 🐛

* flagged as fix to trigger a new release pipeline ([13f1e8a](https://github.com/sekwah41/Advanced-Portals/commit/13f1e8a138666dff7aad9724136308bde1b83ee9))

## [2.6.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.5.0...v2.6.0) (2025-10-14)


### Features ✨

* add != ([c7f97f0](https://github.com/sekwah41/Advanced-Portals/commit/c7f97f0ba850ab6ca0a4032901bb746a83c22934))
* add portal move command ([923853c](https://github.com/sekwah41/Advanced-Portals/commit/923853c32884ee06313a9f808b4b46e4e71b58b5))
* desti move command ([c86f9bc](https://github.com/sekwah41/Advanced-Portals/commit/c86f9bc3283880b18d05f4e4d4082da59126f57f))
* global portal permission ([#570](https://github.com/sekwah41/Advanced-Portals/issues/570)) ([cf36646](https://github.com/sekwah41/Advanced-Portals/commit/cf366460af5840ed8f2cb83929574bafebe304e9))
* improve onChunkLoad performance ([#567](https://github.com/sekwah41/Advanced-Portals/issues/567)) ([7603215](https://github.com/sekwah41/Advanced-Portals/commit/76032156bbf2f9c436a1db41d827fd5b093742b7))


### Bug Fixes 🐛

* clean up conditions ([#560](https://github.com/sekwah41/Advanced-Portals/issues/560)) ([91072bd](https://github.com/sekwah41/Advanced-Portals/commit/91072bd5130f732c11d73d44acd2baa56832aea3))
* disabling block place protection too ([93167ca](https://github.com/sekwah41/Advanced-Portals/commit/93167caf3ddad69e88bb1dd1b729e0728645c6fe))
* log to the console if the player adding a triggerblock is null ([1b50b17](https://github.com/sekwah41/Advanced-Portals/commit/1b50b17da64e05c4e9ca364061497358cf7e0d5e))
* null check for DestiTag ([#558](https://github.com/sekwah41/Advanced-Portals/issues/558)) ([071197e](https://github.com/sekwah41/Advanced-Portals/commit/071197e4902f1824cabeff30ae1637a942c0329a))
* raw type usage in WarpEffectRegistry ([4c4083c](https://github.com/sekwah41/Advanced-Portals/commit/4c4083c9026edd6420bd6fba05a62b0c4bc03dcd))
* switch tag target config to private scope ([58579a5](https://github.com/sekwah41/Advanced-Portals/commit/58579a5adab5de05712c58aac3813c509e15c048))
* update bungeecord-api version and add additional maven repository ([5c47d68](https://github.com/sekwah41/Advanced-Portals/commit/5c47d68a61c6961bdc17325ccfce961ba0b5e83d))
* use atomic and non capturing regex groups ([0555997](https://github.com/sekwah41/Advanced-Portals/commit/05559973adb51ffcf6e73216d87c15a259f559b8))

## [2.5.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.4.0...v2.5.0) (2025-04-13)


### Features ✨

* add rename destination sub-command ([#548](https://github.com/sekwah41/Advanced-Portals/issues/548)) ([96cdd06](https://github.com/sekwah41/Advanced-Portals/commit/96cdd06f96c1a0c90ebc0108e8f85b607dca4036))
* Check if block/matieral is waterlogged ([#550](https://github.com/sekwah41/Advanced-Portals/issues/550)) ([793f13c](https://github.com/sekwah41/Advanced-Portals/commit/793f13cbc79dcbe89d453861ff2f49b2e581010f))
* desti tp another player to a destination ([#549](https://github.com/sekwah41/Advanced-Portals/issues/549)) ([3eb0f96](https://github.com/sekwah41/Advanced-Portals/commit/3eb0f96467de9ffc4b86fd24353907be5bfec0d9))
* fail commands if the arguments have path escape characters ([#552](https://github.com/sekwah41/Advanced-Portals/issues/552)) ([ed9afd5](https://github.com/sekwah41/Advanced-Portals/commit/ed9afd58e18cd9417470cffe904aff5172716ebc))


### Bug Fixes 🐛

* core listener null pointer error for blockPlace ([#554](https://github.com/sekwah41/Advanced-Portals/issues/554)) ([4d65ea0](https://github.com/sekwah41/Advanced-Portals/commit/4d65ea057d31c3ca41cbd80038f1e0ac4827b9da))
* **legacy-spigot:** add null check to avoid throwing errors in console ([#545](https://github.com/sekwah41/Advanced-Portals/issues/545)) ([3263b56](https://github.com/sekwah41/Advanced-Portals/commit/3263b568925691a7a924ea7f02d32fc9aaf26d32))

## [2.4.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.3.3...v2.4.0) (2025-03-01)


### Features ✨

* add cost tag (vault support [#537](https://github.com/sekwah41/Advanced-Portals/issues/537)) ([d672533](https://github.com/sekwah41/Advanced-Portals/commit/d672533b8fa9acf8db53bc2c30aef3095f6bfca5))

## [2.3.3](https://github.com/sekwah41/Advanced-Portals/compare/v2.3.2...v2.3.3) (2025-02-07)


### Bug Fixes 🐛

* add block place null checks ([#530](https://github.com/sekwah41/Advanced-Portals/issues/530)) ([74bcc7c](https://github.com/sekwah41/Advanced-Portals/commit/74bcc7cb7b2c9324e9df6b622d593775279be048))

## [2.3.2](https://github.com/sekwah41/Advanced-Portals/compare/v2.3.1...v2.3.2) (2025-01-31)


### Bug Fixes 🐛

* lower java requirement to 8 for spigot builds ([#527](https://github.com/sekwah41/Advanced-Portals/issues/527)) ([3014d66](https://github.com/sekwah41/Advanced-Portals/commit/3014d66430309f0dee998cdd53a32332c3199108))

## [2.3.1](https://github.com/sekwah41/Advanced-Portals/compare/v2.3.0...v2.3.1) (2025-01-31)


### Bug Fixes 🐛

* update bstats metrics as it was triggering an antivirus false positive ([71c7362](https://github.com/sekwah41/Advanced-Portals/commit/71c7362caec033be03c665335c61dc6fd6ee9589))

## [2.3.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.2.1...v2.3.0) (2025-01-25)


### Features ✨

* add support for legacy spigot 1.8 - 1.12 ([#520](https://github.com/sekwah41/Advanced-Portals/issues/520)) ([2bdeb5b](https://github.com/sekwah41/Advanced-Portals/commit/2bdeb5b0b6219c1695a8f46268dcb5770e714d9a))

## [2.2.1](https://github.com/sekwah41/Advanced-Portals/compare/v2.2.0...v2.2.1) (2025-01-07)


### Bug Fixes 🐛

* check for world existence ([2528ebf](https://github.com/sekwah41/Advanced-Portals/commit/2528ebfe5141d29bc7762b283d188562e8424c8f))
* teleport call that doesn't check world ([#513](https://github.com/sekwah41/Advanced-Portals/issues/513)) ([e1a9e3d](https://github.com/sekwah41/Advanced-Portals/commit/e1a9e3d86f085e746902c284177944233162a909))
* trigger block validation ([#510](https://github.com/sekwah41/Advanced-Portals/issues/510)) ([ef6f976](https://github.com/sekwah41/Advanced-Portals/commit/ef6f97615f7c177564ac10c4989e386dabd60f80))

## [2.2.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.1.1...v2.2.0) (2025-01-04)


### Features ✨

* add placeholder api conditions support ([38a0d14](https://github.com/sekwah41/Advanced-Portals/commit/38a0d149d667ac09411cb71fe5677c702445452c))


### Bug Fixes 🐛

* auto import of old data causing plugin to not load ([#505](https://github.com/sekwah41/Advanced-Portals/issues/505)) ([c15a33e](https://github.com/sekwah41/Advanced-Portals/commit/c15a33ecde8616a3f80a38912e41e420fa428a2a))
* comment out log spam ([#500](https://github.com/sekwah41/Advanced-Portals/issues/500)) ([3067f92](https://github.com/sekwah41/Advanced-Portals/commit/3067f9241523c3e83139ae917b7c864ff54f570c))
* destination tags activation stage not triggering ([f1d4a9e](https://github.com/sekwah41/Advanced-Portals/commit/f1d4a9e124255886e696c4c96ee1a499d461d5a8))

## [2.1.1](https://github.com/sekwah41/Advanced-Portals/compare/v2.1.0...v2.1.1) (2024-12-18)


### Bug Fixes 🐛

* portalProtectionRadius spelt wrong in the config ([204e4c0](https://github.com/sekwah41/Advanced-Portals/commit/204e4c07e9a539198bb893433fce2c9bc2fa8ded))

## [2.1.0](https://github.com/sekwah41/Advanced-Portals/compare/v2.0.3...v2.1.0) (2024-12-18)


### Features ✨

* make config values import from the old files ([04f1941](https://github.com/sekwah41/Advanced-Portals/commit/04f1941808c855daaaf06064733a7247e550b431))


### Bug Fixes 🐛

* disable spectators triggering portals by default ([f44f3fb](https://github.com/sekwah41/Advanced-Portals/commit/f44f3fb7d6d9c4924cdc1989c7432e45ade0682d)), closes [#491](https://github.com/sekwah41/Advanced-Portals/issues/491)
* on interact null pointer exceptions ([53fad33](https://github.com/sekwah41/Advanced-Portals/commit/53fad3360c7126fc6c866177384b47fd123d3857))

## [2.0.3](https://github.com/sekwah41/Advanced-Portals/compare/v2.0.2...v2.0.3) (2024-12-15)


### Bug Fixes 🐛

* command portals are being imported from old files with the wrong name ([93a7a5f](https://github.com/sekwah41/Advanced-Portals/commit/93a7a5f53e4d5e7099740acb5d43af4a96776851))
* version number is now set correctly, was 1.0.0 ([837faa5](https://github.com/sekwah41/Advanced-Portals/commit/837faa5bcd1b9b0225e3361eb1f99fdc9e46c7a7))

## [2.0.2](https://github.com/sekwah41/Advanced-Portals/compare/v2.0.1...v2.0.2) (2024-12-12)


### Bug Fixes 🐛

* remove unneeded extra chat messages when creating a portal ([12e3191](https://github.com/sekwah41/Advanced-Portals/commit/12e319160a58ee4fed00a4f8824e14ceaf573e25))


### Changes to Existing Features 🔧

* update the default visualisation sizes as they are quite small ([62d48e2](https://github.com/sekwah41/Advanced-Portals/commit/62d48e2cbc70fcd249abc67e1ee49b98ff7f2ed0))

## [2.0.1](https://github.com/sekwah41/Advanced-Portals/compare/v2.0.0...v2.0.1) (2024-12-12)


### Bug Fixes 🐛

* remove spammy debug logs from tab auto complete ([#476](https://github.com/sekwah41/Advanced-Portals/issues/476)) ([46b6156](https://github.com/sekwah41/Advanced-Portals/commit/46b615624158c3c801dbdd1cbb7f0f803adb338d))

## [2.0.0](https://github.com/sekwah41/Advanced-Portals/compare/v1.0.0...v2.0.0) (2024-12-11)


### ⚠ BREAKING CHANGES

* This is a complete recode, features may be missing or different, if there are issues please open an issue on github or contact us about it on discord.
* Permissions have been reworked, if you use a permissions manager you may need to change a few of the permissions

### Features ✨

* add a warning for no name being included ([106a0ae](https://github.com/sekwah41/Advanced-Portals/commit/106a0ae8b48ea66c2afcc8cd12c6c4ca8aa87f67))
* add abstracted game scheduler ([0085733](https://github.com/sekwah41/Advanced-Portals/commit/008573305e10d8d89407721e2112d18632f94c74))
* add base repos and portal data ([d7e6df2](https://github.com/sekwah41/Advanced-Portals/commit/d7e6df25561e09f7c65e4470a30dc12f859ea1c4))
* add basic data loading and translations ([6d2ed12](https://github.com/sekwah41/Advanced-Portals/commit/6d2ed12f36b3f172dfcc8738c7aaacc64c43f122))
* add basic desti create and remove ([c6d644a](https://github.com/sekwah41/Advanced-Portals/commit/c6d644a0b3359de8ed038579abee8d0c7f193225))
* add command tags ([#443](https://github.com/sekwah41/Advanced-Portals/issues/443)) ([833399e](https://github.com/sekwah41/Advanced-Portals/commit/833399e8d06b817b8227f91e8bd173ab0e40c66f))
* add cooldown tag that is stored ([c9d98b3](https://github.com/sekwah41/Advanced-Portals/commit/c9d98b39c3b29762a4274498d4601dfa312bc360))
* add debug view for selection ([dd7c545](https://github.com/sekwah41/Advanced-Portals/commit/dd7c545a750c5b18eb1c7d3dd03a0b10aaf38c54))
* add default trigger block if unspecified ([c905972](https://github.com/sekwah41/Advanced-Portals/commit/c90597228af76f6da5e0941dcf286d8c1ba1fab6))
* add destination and portal services ([55de809](https://github.com/sekwah41/Advanced-Portals/commit/55de8093492ccdb47ea9da1358c7fd77dbdc0de9))
* Add destination teleport subcommand ([#420](https://github.com/sekwah41/Advanced-Portals/issues/420)) ([7c7dc98](https://github.com/sekwah41/Advanced-Portals/commit/7c7dc98edde610907be5b37e5b83b2476dc2e4a2))
* add disable beacon sub command ([57e27a8](https://github.com/sekwah41/Advanced-Portals/commit/57e27a87a1d5d7934873b6e54b43adfb19d13a19))
* add error messages for the destination commands ([13a27ca](https://github.com/sekwah41/Advanced-Portals/commit/13a27cacb35235356b20663b7b27594fa150e6fb))
* add global cooldown and throwback sounds ([9448fd7](https://github.com/sekwah41/Advanced-Portals/commit/9448fd76bf0bdb3d16c7cfdd5316eb5635712809))
* add list command & update translation colors ([33c9221](https://github.com/sekwah41/Advanced-Portals/commit/33c92212db25989052247722eac6bdd8c673e9d1))
* add messages and checks for commnad portal permissions ([4fcb9e5](https://github.com/sekwah41/Advanced-Portals/commit/4fcb9e549abe295aa1d68586e39c04af86946f8e))
* add permission tag & compatible block trigger list ([6386a89](https://github.com/sekwah41/Advanced-Portals/commit/6386a899132ef7a28b08e8e983cc90f7cdc8fc14))
* add portal and desti commands ([154919f](https://github.com/sekwah41/Advanced-Portals/commit/154919f7808f515145b1a08fd8a400d80c6231c9))
* add portal protections ([d568f05](https://github.com/sekwah41/Advanced-Portals/commit/d568f051f85adc7883ac0786324ea72c7a3bd3f1))
* add show desti command ([6629151](https://github.com/sekwah41/Advanced-Portals/commit/6629151fc63929988a20d3ca8b5f07a5c453b0c0))
* add support for hex colour codes on spigot ([94522c2](https://github.com/sekwah41/Advanced-Portals/commit/94522c237f46f96a72b9e336530ae9e7c0420375))
* add support for uppercase color codes as well as &#rrggbb to lang files ([166d48b](https://github.com/sekwah41/Advanced-Portals/commit/166d48bb90de533e71e51645be47ddf73b603d6c))
* add the ability for tags to reject values ([3558629](https://github.com/sekwah41/Advanced-Portals/commit/3558629f8e6e5ea2fc2426de7a6dc5386c54e3f3))
* add the ability to have multiple values for a tag ([cc9fa20](https://github.com/sekwah41/Advanced-Portals/commit/cc9fa203cbf8e28aea71bc02423f0a139a0c8105))
* add the ability to split tags based on a splitstring ([116c45f](https://github.com/sekwah41/Advanced-Portals/commit/116c45f5a56a1165b3a837235f8c1ee0af0484f2))
* added tag priority sorting and delay tag ([0044008](https://github.com/sekwah41/Advanced-Portals/commit/00440081051bda9d1276238b6ba615c13c11a5b9))
* adding auto complete for tags ([c64c342](https://github.com/sekwah41/Advanced-Portals/commit/c64c342a4de26a84ac4a6762feb80983751b5572))
* adding debug command ([f002c8e](https://github.com/sekwah41/Advanced-Portals/commit/f002c8e125e7e8e032ea690d4aeddcb83719d630))
* auto import the old portals and destinations if the folders don't exist ([9f23a61](https://github.com/sekwah41/Advanced-Portals/commit/9f23a617ff6dfc170d6bb83b0011812b64d21186))
* block physics updates inside portal protection zones ([bba1418](https://github.com/sekwah41/Advanced-Portals/commit/bba14185ebab4745029e8e39c3e48a3a4e467bda))
* bungee tags ([#448](https://github.com/sekwah41/Advanced-Portals/issues/448)) ([a70e83b](https://github.com/sekwah41/Advanced-Portals/commit/a70e83b4d1f68e9fa36e3f2729592aa50f908122))
* can create basic portals with tags ([9ccf7b1](https://github.com/sekwah41/Advanced-Portals/commit/9ccf7b1d133449029173e68f71c30bc3823aad44))
* center titles based on length ([8522215](https://github.com/sekwah41/Advanced-Portals/commit/852221533f51bf5cc1074c132fac8ca58a3c545d))
* configure portal effects ([a2f6574](https://github.com/sekwah41/Advanced-Portals/commit/a2f65742240af5767f9fcaa2aec27e48151c5d6a))
* enhance the in portal mitigation ([ffddd87](https://github.com/sekwah41/Advanced-Portals/commit/ffddd87922f5420b6de6558ccd424f29c43ef7ff))
* make portal and desti implement the same tab auto complete ([6921749](https://github.com/sekwah41/Advanced-Portals/commit/69217491287047bb520330bde11999bddf634dbd))
* make services available as singletons ([722ef68](https://github.com/sekwah41/Advanced-Portals/commit/722ef68ed03a01796a3bb7830e6164990554d4ca))
* message tags & priority sorting ([#447](https://github.com/sekwah41/Advanced-Portals/issues/447)) ([e056a68](https://github.com/sekwah41/Advanced-Portals/commit/e056a6836d1cfb175c8bb333a09d8eb27eab168a))
* new tag parser ([030ef3a](https://github.com/sekwah41/Advanced-Portals/commit/030ef3a13525bb2cb1a4aa99e81a5ac7d465e691))
* Particle visualising ([#467](https://github.com/sekwah41/Advanced-Portals/issues/467)) ([0550a4d](https://github.com/sekwah41/Advanced-Portals/commit/0550a4dba45569597f1c13677eedfde0de1f5517))
* portal block placing checks surrounding blocks for orientation ([ad1ef6c](https://github.com/sekwah41/Advanced-Portals/commit/ad1ef6cc40adbae075d7a875e9c9c749c2540963))
* portal placer block rotate tool ([1c7d094](https://github.com/sekwah41/Advanced-Portals/commit/1c7d09469e81c1f1b5609f377d5419d52e577148))
* portals now trigger and nether portals are cancelled ([f9f5a9d](https://github.com/sekwah41/Advanced-Portals/commit/f9f5a9d1f27896b316b30ae62cc347e2a4fd6c0e))
* proxy tag ([#451](https://github.com/sekwah41/Advanced-Portals/issues/451)) ([dd72123](https://github.com/sekwah41/Advanced-Portals/commit/dd72123d4b9409038095eb52f720ed3fe4e63342))
* re-implement and clean up permissions ([#453](https://github.com/sekwah41/Advanced-Portals/issues/453)) ([9030ff5](https://github.com/sekwah41/Advanced-Portals/commit/9030ff53630a83b41708c0cb973cf4af736649f4))
* register basic bungee plugin with metrics ([d665e1a](https://github.com/sekwah41/Advanced-Portals/commit/d665e1a271a17328d34cb14aed041bb6e689a5b2))
* remap and filter tags ([a658e7d](https://github.com/sekwah41/Advanced-Portals/commit/a658e7df8736a9201565d98007149885fb782a60))
* see portal info with /portal info (name) ([e7b0d5e](https://github.com/sekwah41/Advanced-Portals/commit/e7b0d5e4ca1c6a4835342afd24cc1e207f3fdf74))
* show triggerblocks in debug view ([29b2e27](https://github.com/sekwah41/Advanced-Portals/commit/29b2e2734bdfe49e3e6f2544ead00d01ade3109e))
* **spigot:** add command for porting portals and destinations to the new format ([7c1a4ce](https://github.com/sekwah41/Advanced-Portals/commit/7c1a4ceee7a94432c986c9fb1c88e990d486e4be))
* switch files over to using yaml ([#427](https://github.com/sekwah41/Advanced-Portals/issues/427)) ([5f44e24](https://github.com/sekwah41/Advanced-Portals/commit/5f44e24824c4bf14182093d71daac58c05d95519))
* switch jar to shadow the dependencies to avoid clashes with other plugins ([c2b250c](https://github.com/sekwah41/Advanced-Portals/commit/c2b250c2a55072d5e6f80506d145e9ac36098aa4))
* temporary player data is now stored for more perma use ([805e8dc](https://github.com/sekwah41/Advanced-Portals/commit/805e8dc3d0374a97a8d11550b75bffa23270a5b2))
* update lang command to add missing translation keys ([10618e2](https://github.com/sekwah41/Advanced-Portals/commit/10618e2ab03c1f0236b7b8970c414418d73ad1b3))


### Bug Fixes 🐛

* allow null values for auto complete suggestions ([412bc4f](https://github.com/sekwah41/Advanced-Portals/commit/412bc4f07f2bce14deb193ae5c492b302496223e))
* apply the rotation and pitch of the offset to a tp ([ab92ff1](https://github.com/sekwah41/Advanced-Portals/commit/ab92ff104628313d306e9b1119700da7ff7da6d7))
* check the player is in the same world ([8018d68](https://github.com/sekwah41/Advanced-Portals/commit/8018d68e429528602e9a45752f97bbce8a1c52f5))
* improve auto complete for triggerblock ([1292348](https://github.com/sekwah41/Advanced-Portals/commit/12923480fca208bff6dfb423979ec4d7871e954f))
* issues with tab menu capitalisation ([81e8876](https://github.com/sekwah41/Advanced-Portals/commit/81e887607956150a7ffa5aeaf1f8c6697afc5ac4))
* language file duplication ([8dd1094](https://github.com/sekwah41/Advanced-Portals/commit/8dd1094ab6d5e6e22cb827f5bf9f4e5c9e85da69))
* permission issue for command portals ([5395d76](https://github.com/sekwah41/Advanced-Portals/commit/5395d769e1df3260c449788e1bd072f189a3d8b3))
* Permission Tag ([#418](https://github.com/sekwah41/Advanced-Portals/issues/418)) ([69fa988](https://github.com/sekwah41/Advanced-Portals/commit/69fa988f9769bb943a25203c8c499bb6f0ff0545))
* portal activation issue ([ad3f117](https://github.com/sekwah41/Advanced-Portals/commit/ad3f117f049322224620781c5d9dbff0d2a8e9a0))
* portal command config reading ([22cda5d](https://github.com/sekwah41/Advanced-Portals/commit/22cda5d2cc77ef5a2224433640311fd058c09d16))
* portal import command checking yaml files ([7635a13](https://github.com/sekwah41/Advanced-Portals/commit/7635a134217d9673a801c08c0431eeb79a36de79))
* references to services in portal subcommands ([ef9241e](https://github.com/sekwah41/Advanced-Portals/commit/ef9241e2609b84bc3cb0e0e71645e37cb8230219))
* remove references to old material + make translatable ([2cd6452](https://github.com/sekwah41/Advanced-Portals/commit/2cd64528a4c742f1895983988bee7aac7fc8c202))
* show page numbers on tab complete ([b2e19a1](https://github.com/sekwah41/Advanced-Portals/commit/b2e19a1391b372eef3a468f5da4a850477a30bdd))
* string commands were erroring ([#426](https://github.com/sekwah41/Advanced-Portals/issues/426)) ([8afbae5](https://github.com/sekwah41/Advanced-Portals/commit/8afbae50fbaeef3bc65a90d9c3ae67689ba226cc))
* subcommand registry boolean order ([7e3be04](https://github.com/sekwah41/Advanced-Portals/commit/7e3be0456f2b1889957bb784c3e711817066887a))
* switch all translation blocks to pick up color codes ([ebc2196](https://github.com/sekwah41/Advanced-Portals/commit/ebc2196511ffb132971397ac76273352afeaf516))
* switch tab complete to empty arrays from  null returns ([9deb765](https://github.com/sekwah41/Advanced-Portals/commit/9deb765ad566f7ef1f7714a42a691aaaeccd05e4))
* tag quotes logic ([c4cc19a](https://github.com/sekwah41/Advanced-Portals/commit/c4cc19a7f5d4b190806126230b8ab283ce19ddbd))


### Changes to Existing Features 🔧

* Show common trigger blocks for tag, then all if none match ([#468](https://github.com/sekwah41/Advanced-Portals/issues/468)) ([a9ff2b5](https://github.com/sekwah41/Advanced-Portals/commit/a9ff2b5a693d231d27d8bb3cec544de2213cd398))


### Miscellaneous Chores

* switch to using env ([d672954](https://github.com/sekwah41/Advanced-Portals/commit/d672954b4b09ddb547dc073013fd7d7393cbf9fe))

## [1.0.0](https://github.com/sekwah41/Advanced-Portals/compare/v0.11.0...v1.0.0) (2024-12-09)

**This is not the re-code, I released some breaking config changes for servers that used to rely on the automatic proxy detection.**

### 💥 Breaking Change

- fix!: disable proxy detection to avoid vulnerabilities (see full commit for more info) [#436](https://github.com/sekwah41/Advanced-Portals/pull/436) ([@sekwah41](https://github.com/sekwah41))
- fix: stop portal packets still being forwarded to the client [#436](https://github.com/sekwah41/Advanced-Portals/pull/436) ([@sekwah41](https://github.com/sekwah41))

### Authors: 1

- Sekwah ([@sekwah41](https://github.com/sekwah41))
