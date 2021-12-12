// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'Advanced Portals',
  tagline: 'An advanced portals plugin for bukkit',
  url: 'https://advancedportals.sekwah.com/',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/favicon.ico',
  organizationName: 'sekwah41', // Usually your GitHub org/user name.
  projectName: 'Advanced-Portals', // Usually your repo name.

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          // Please change this to your repo.
          editUrl: 'https://github.com/sekwah41/Advanced-Portals/edit/website/',
        },
        blog: false,
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      navbar: {
        title: 'Advanced Portals',
        logo: {
          alt: 'Advanced Portals Logo',
          src: 'img/advancedportals.png',
        },
        items: [
          {
            to: '/docs/intro',
            position: 'left',
            label: 'Tutorial',
          },
          //{to: '/blog', label: 'Blog', position: 'left'},
          {to: '/docs/commands', label: 'Commands', position: 'left'},
          {to: '/docs/portal-tags', label: 'Portal Tags', position: 'left'},
          {
            href: 'https://github.com/sekwah41/Advanced-Portals',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        links: [
          {
            title: 'Downloads',
            items: [
              {
                label: 'Bukkit.org',
                href: 'https://dev.bukkit.org/projects/advanced-portals',
              },
              {
                label: 'GitHub',
                href: 'https://github.com/sekwah41/Advanced-Portals/releases',
              },
            ],
          },
          {
            title: 'Community',
            items: [
              {
                label: 'Discord',
                href: 'https://discord.sekwah.com/',
              },
              {
                label: 'Twitter',
                href: 'https://twitter.com/sekwah',
              },
            ],
          },
          {
            title: 'More',
            items: [
              {
                label: 'Changelog',
                href: 'https://github.com/sekwah41/Advanced-Portals/blob/spigot-1.13-1.16/CHANGELOG.md',
              },
              {
                label: 'Source Code',
                href: 'https://github.com/sekwah41/Advanced-Portals',
              },
            ],
          },
        ],
        copyright: `Copyright © ${new Date().getFullYear()} Sekwah.`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
      },
    }),
};

module.exports = config;
