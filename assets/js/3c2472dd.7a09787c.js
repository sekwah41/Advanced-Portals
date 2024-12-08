"use strict";(self.webpackChunkadvanced_portals_docs=self.webpackChunkadvanced_portals_docs||[]).push([[997],{3905:function(e,t,n){n.d(t,{Zo:function(){return p},kt:function(){return u}});var a=n(7294);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function r(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function l(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?r(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):r(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function i(e,t){if(null==e)return{};var n,a,o=function(e,t){if(null==e)return{};var n,a,o={},r=Object.keys(e);for(a=0;a<r.length;a++)n=r[a],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(a=0;a<r.length;a++)n=r[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}var d=a.createContext({}),s=function(e){var t=a.useContext(d),n=t;return e&&(n="function"==typeof e?e(t):l(l({},t),e)),n},p=function(e){var t=s(e.components);return a.createElement(d.Provider,{value:t},e.children)},c={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},m=a.forwardRef((function(e,t){var n=e.components,o=e.mdxType,r=e.originalType,d=e.parentName,p=i(e,["components","mdxType","originalType","parentName"]),m=s(n),u=o,h=m["".concat(d,".").concat(u)]||m[u]||c[u]||r;return n?a.createElement(h,l(l({ref:t},p),{},{components:n})):a.createElement(h,l({ref:t},p))}));function u(e,t){var n=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var r=n.length,l=new Array(r);l[0]=m;var i={};for(var d in t)hasOwnProperty.call(t,d)&&(i[d]=t[d]);i.originalType=e,i.mdxType="string"==typeof e?e:o,l[1]=i;for(var s=2;s<r;s++)l[s]=n[s];return a.createElement.apply(null,l)}return a.createElement.apply(null,n)}m.displayName="MDXCreateElement"},5862:function(e,t,n){n.r(t),n.d(t,{frontMatter:function(){return i},contentTitle:function(){return d},metadata:function(){return s},toc:function(){return p},default:function(){return m}});var a=n(7462),o=n(3366),r=(n(7294),n(3905)),l=["components"],i={sidebar_position:2,description:"Information on all commands included in Advanced Portals, including usage and permissions."},d="Commands",s={unversionedId:"commands",id:"commands",title:"Commands",description:"Information on all commands included in Advanced Portals, including usage and permissions.",source:"@site/docs/commands.md",sourceDirName:".",slug:"/commands",permalink:"/docs/commands",editUrl:"https://github.com/sekwah41/Advanced-Portals/edit/website/docs/commands.md",tags:[],version:"current",sidebarPosition:2,frontMatter:{sidebar_position:2,description:"Information on all commands included in Advanced Portals, including usage and permissions."},sidebar:"tutorialSidebar",previous:{title:"Tutorial Intro",permalink:"/docs/intro"},next:{title:"Portal Tags",permalink:"/docs/portal-tags"}},p=[{value:"Portal Command",id:"portal-command",children:[{value:"<code>/portal create (tags...)</code>",id:"portal-create-tags",children:[],level:3},{value:"<code>/portal selector</code> or <code>/portal wand</code>",id:"portal-selector-or-portal-wand",children:[],level:3},{value:"<code>/portal portalblock</code>",id:"portal-portalblock",children:[],level:3},{value:"<code>/portal endportalblock</code>",id:"portal-endportalblock",children:[],level:3},{value:"<code>/portal gatewayblock</code>",id:"portal-gatewayblock",children:[],level:3},{value:"<code>/portal disablebeacon (portalname)</code>",id:"portal-disablebeacon-portalname",children:[],level:3},{value:"<code>/portal select</code>",id:"portal-select",children:[],level:3},{value:"<code>/portal unselect</code>",id:"portal-unselect",children:[],level:3},{value:"<code>/portal remove</code>",id:"portal-remove",children:[],level:3},{value:"<code>/portal help</code>",id:"portal-help",children:[],level:3}],level:2},{value:"Destination Command",id:"destination-command",children:[{value:"<code>/desti create</code>",id:"desti-create",children:[],level:3},{value:"<code>/desti remove</code>",id:"desti-remove",children:[],level:3},{value:"<code>/desti list</code>",id:"desti-list",children:[],level:3},{value:"<code>/desti warp (desti name)</code>",id:"desti-warp-desti-name",children:[],level:3}],level:2}],c={toc:p};function m(e){var t=e.components,n=(0,o.Z)(e,l);return(0,r.kt)("wrapper",(0,a.Z)({},c,n,{components:t,mdxType:"MDXLayout"}),(0,r.kt)("h1",{id:"commands"},"Commands"),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"V2.0.0+ / recode info")),(0,r.kt)("p",null,"The current documentation is related to versions below 2.0.0. While most should apply to the latest version, some features may have changed or be missing.\nThe documentation will be updated soon."),(0,r.kt)("p",null,"You can use the ",(0,r.kt)("inlineCode",{parentName:"p"},"/portals convert")," command to port your portals to the latest version. Do not worry. The original data will not be deleted, and you can revert to older versions if you run into problems."),(0,r.kt)("p",null,":::"),(0,r.kt)("p",null,"All commands included in Advanced Portals are listed below along with their permissions."),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Note:")," all ",(0,r.kt)("inlineCode",{parentName:"p"},"/portal")," commands can also be used with ",(0,r.kt)("inlineCode",{parentName:"p"},"/advancedportals")," or ",(0,r.kt)("inlineCode",{parentName:"p"},"/aportals")," instead."),(0,r.kt)("p",null,"Same goes for all /destination commands can be used with /desti"),(0,r.kt)("p",null,"Also if you want blocks such as nether portals not to break when being placed you will need to define the portal before placing them. This is so the physics updates know not to mess with vanilla portals being broken or other plugins :)"),(0,r.kt)("h2",{id:"portal-command"},"Portal Command"),(0,r.kt)("p",null,"Usable Alias: ",(0,r.kt)("inlineCode",{parentName:"p"},"/portal")," ",(0,r.kt)("inlineCode",{parentName:"p"},"/ap")," ",(0,r.kt)("inlineCode",{parentName:"p"},"/portals")," ",(0,r.kt)("inlineCode",{parentName:"p"},"/aportal")," ",(0,r.kt)("inlineCode",{parentName:"p"},"/advancedportals")),(0,r.kt)("h3",{id:"portal-create-tags"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal create (tags...)")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.portal")),(0,r.kt)("p",null,"This command is used to create a portal. The behaviour of the portal can be determined by the tags given (see list below), but a name must tag must always be given ",(0,r.kt)("inlineCode",{parentName:"p"},"name:some_name_here")),(0,r.kt)("p",null,"As a side note, make sure to check the ",(0,r.kt)("inlineCode",{parentName:"p"},"triggerblock:")," has been set if you are not using nether portal blocks. These are blocks that ",(0,r.kt)("strong",{parentName:"p"},"you need to be INSIDE")," so blocks such as water and even cobwebs will work, but cobblestone will not be a suitable triggerblock."),(0,r.kt)("p",null,"For a list of tags and info, check out the ",(0,r.kt)("a",{parentName:"p",href:"/docs/portal-tags"},"tags page"),"."),(0,r.kt)("h3",{id:"portal-selector-or-portal-wand"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal selector")," or ",(0,r.kt)("inlineCode",{parentName:"h3"},"/portal wand")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.createportal")),(0,r.kt)("p",null,"This gives you the mighty portal axe, if UseOnlyServerMadeAxe is true then this one will still work, but the normal iron axe will still be available to be used in survival for admins. (instead of always trying to make portals)"),(0,r.kt)("h3",{id:"portal-portalblock"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal portalblock")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.portal")),(0,r.kt)("p",null,"Gives you a portal block that you can build with. (If the rotation is in the wrong place one next to it and then replace it to get the right rotation)"),(0,r.kt)("h3",{id:"portal-endportalblock"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal endportalblock")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.portal")),(0,r.kt)("p",null,"Gives you an end portal block that you can build with."),(0,r.kt)("h3",{id:"portal-gatewayblock"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal gatewayblock")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.portal")),(0,r.kt)("p",null,"Gives you an end gateway block that you can build with."),(0,r.kt)("h3",{id:"portal-disablebeacon-portalname"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal disablebeacon (portalname)")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.build")),(0,r.kt)("p",null,"Needs DisableGatewayBeam to be set to true in the config. Though also triggers on create or chunk load. This is just a backup method."),(0,r.kt)("h3",{id:"portal-select"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal select")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.createportal")),(0,r.kt)("p",null,"After the command is entered, punch inside a portal region, and it will select that portal."),(0,r.kt)("h3",{id:"portal-unselect"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal unselect")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.createportal")),(0,r.kt)("p",null,"Use to remove the current portal selection. (as it can mess with certain commands)"),(0,r.kt)("h3",{id:"portal-remove"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal remove")),(0,r.kt)("p",null,(0,r.kt)("strong",{parentName:"p"},"Permission:")," ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.removeportal")),(0,r.kt)("p",null,"Enter this command to destroy a portal with a set name. If the argument is left blank, it will destroy the currently selected portal."),(0,r.kt)("h3",{id:"portal-help"},(0,r.kt)("inlineCode",{parentName:"h3"},"/portal help")),(0,r.kt)("p",null,"Displays the help message."),(0,r.kt)("h2",{id:"destination-command"},"Destination Command"),(0,r.kt)("p",null,"Usable Alias: ",(0,r.kt)("inlineCode",{parentName:"p"},"/desti")," ",(0,r.kt)("inlineCode",{parentName:"p"},"/destination")),(0,r.kt)("p",null,"Permission (applies to all): ",(0,r.kt)("inlineCode",{parentName:"p"},"advancedportals.desti")),(0,r.kt)("h3",{id:"desti-create"},(0,r.kt)("inlineCode",{parentName:"h3"},"/desti create")),(0,r.kt)("p",null,"This the command creates a new destination with the location data from your player (your player position and direction your facing)."),(0,r.kt)("h3",{id:"desti-remove"},(0,r.kt)("inlineCode",{parentName:"h3"},"/desti remove")),(0,r.kt)("p",null,"Remove a destination with a specific name. (portals will still attempt to warp to this name but say no destination exists)"),(0,r.kt)("h3",{id:"desti-list"},(0,r.kt)("inlineCode",{parentName:"h3"},"/desti list")),(0,r.kt)("p",null,"A list of created destinations."),(0,r.kt)("h3",{id:"desti-warp-desti-name"},(0,r.kt)("inlineCode",{parentName:"h3"},"/desti warp (desti name)")),(0,r.kt)("p",null,"Teleport to the named destination."))}m.isMDXComponent=!0}}]);