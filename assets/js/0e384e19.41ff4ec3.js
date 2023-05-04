"use strict";(self.webpackChunkadvanced_portals_docs=self.webpackChunkadvanced_portals_docs||[]).push([[671],{3905:function(e,t,r){r.d(t,{Zo:function(){return u},kt:function(){return m}});var n=r(7294);function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function a(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?a(Object(r),!0).forEach((function(t){o(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):a(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function l(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},a=Object.keys(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var p=n.createContext({}),c=function(e){var t=n.useContext(p),r=t;return e&&(r="function"==typeof e?e(t):i(i({},t),e)),r},u=function(e){var t=c(e.components);return n.createElement(p.Provider,{value:t},e.children)},s={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},d=n.forwardRef((function(e,t){var r=e.components,o=e.mdxType,a=e.originalType,p=e.parentName,u=l(e,["components","mdxType","originalType","parentName"]),d=c(r),m=o,f=d["".concat(p,".").concat(m)]||d[m]||s[m]||a;return r?n.createElement(f,i(i({ref:t},u),{},{components:r})):n.createElement(f,i({ref:t},u))}));function m(e,t){var r=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var a=r.length,i=new Array(a);i[0]=d;var l={};for(var p in t)hasOwnProperty.call(t,p)&&(l[p]=t[p]);l.originalType=e,l.mdxType="string"==typeof e?e:o,i[1]=l;for(var c=2;c<a;c++)i[c]=r[c];return n.createElement.apply(null,i)}return n.createElement.apply(null,r)}d.displayName="MDXCreateElement"},9881:function(e,t,r){r.r(t),r.d(t,{frontMatter:function(){return l},contentTitle:function(){return p},metadata:function(){return c},toc:function(){return u},default:function(){return d}});var n=r(7462),o=r(3366),a=(r(7294),r(3905)),i=["components"],l={sidebar_position:1,description:"Quick introduction to Advanced Portals."},p="Tutorial Intro",c={unversionedId:"intro",id:"intro",title:"Tutorial Intro",description:"Quick introduction to Advanced Portals.",source:"@site/docs/intro.md",sourceDirName:".",slug:"/intro",permalink:"/docs/intro",editUrl:"https://github.com/sekwah41/Advanced-Portals/edit/website/docs/intro.md",tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,description:"Quick introduction to Advanced Portals."},sidebar:"tutorialSidebar",next:{title:"Commands",permalink:"/docs/commands"}},u=[],s={toc:u};function d(e){var t=e.components,r=(0,o.Z)(e,i);return(0,a.kt)("wrapper",(0,n.Z)({},s,r,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h1",{id:"tutorial-intro"},"Tutorial Intro"),(0,a.kt)("p",null,"Here is a YouTube video made by ",(0,a.kt)("a",{parentName:"p",href:"https://www.youtube.com/channel/UCZvGH5UFnZGHL7t11RLhg2w"},"LtJim007")," explaining the basics."),(0,a.kt)("iframe",{width:"560",height:"315",src:"https://www.youtube-nocookie.com/embed/nkOeMUkYz3Y",title:"YouTube video player",frameborder:"0",allow:"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture",allowfullscreen:!0}),(0,a.kt)("p",null,"In summary: you can create destinations for your portals with ",(0,a.kt)("inlineCode",{parentName:"p"},"/desti"),", and create portals to them with ",(0,a.kt)("inlineCode",{parentName:"p"},"/portal create (tags...)"),". You will need to provide a ",(0,a.kt)("em",{parentName:"p"},"trigger element")," for your portals, for example water / lava / nether swirls, and replace the inside of your portal with it for the portal to function."),(0,a.kt)("p",null,"Here's a step by step guide."),(0,a.kt)("ol",null,(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("p",{parentName:"li"},"Create your fancy portal in a standard Minecraft fashion. Leave the portion where the portal itself will be empty.")),(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("p",{parentName:"li"},"Go to the location you want your portal to transport players to. Run ",(0,a.kt)("inlineCode",{parentName:"p"},"/desti create name-of-destination"),".")),(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("p",{parentName:"li"},"Go back to your portal. Take an iron axe (the special portal tool, by default: configurable). Left-click in the upper left of the portal, and right-click in the bottom right of the portal.")),(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("p",{parentName:"li"},"Run ",(0,a.kt)("inlineCode",{parentName:"p"},"/portal create name:name-of-your-portal desti:name-of-destination triggerblock:name-of-trigger-element"),". This is a basic example - more options can be found on the ",(0,a.kt)("a",{parentName:"p",href:"/docs/portal-tags"},"tags page"),".")),(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("p",{parentName:"li"},"Replace the empty air in your portal with your trigger element by running ",(0,a.kt)("inlineCode",{parentName:"p"},"/fill bottom-right-coords upper-right-coords trigger-element"),". The coordinates should have shown up in chat when you left-and-right-clicked with the iron axe."))),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},"If your portal isn't rectangular: try ",(0,a.kt)("inlineCode",{parentName:"li"},"/fill"),"ing the area with glass or another block, breaking the glass in the shape of the portal, and then ",(0,a.kt)("inlineCode",{parentName:"li"},"/fill")," the area again with your transportation trigger block. Then break the remaining glass.")),(0,a.kt)("p",null,"If you mess up, you can run ",(0,a.kt)("inlineCode",{parentName:"p"},"/desti remove name-of-destination")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"/portal remove name-of-portal")," to remove a destination and a portal, respectively."))}d.isMDXComponent=!0}}]);