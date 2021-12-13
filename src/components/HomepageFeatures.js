import React from 'react';
import clsx from 'clsx';
import styles from './HomepageFeatures.module.css';

const FeatureList = [
  {
    title: 'Easy to Use',
    Svg: require('../../static/img/easy-to-use.svg').default,
    description: (
      <>
          Advanced Portals was designed from the ground up to be easily set up yet still allow for detailed customization.
      </>
    ),
  },
  {
    title: 'Customisable Tags',
    Svg: require('../../static/img/custom-tags.svg').default,
    description: (
      <>
          Using the tag system, portals will explicitly do what you ask. Only include the tags you want/need to customise portals.
      </>
    ),
  },
  {
    title: 'Proxy Support',
    Svg: require('../../static/img/proxy-support.svg').default,
    description: (
      <>
          Forks of Bungee and Velocity can be used with support for both modern and legacy forwarding.
      </>
    ),
  },
];

function Feature({Svg, title, description}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center">
        <Svg className={styles.featureSvg} alt={title} />
      </div>
      <div className="text--center padding-horiz--md">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
