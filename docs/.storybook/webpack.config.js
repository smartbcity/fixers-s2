const renderer = `
import React from 'react'
import {mdx} from '/node_modules/@mdx-js/react'
`

module.exports = ({config}) => {
//    config.module.rules[2].use[1].options = {...config.module.rules[2].use[1].options, renderer}
    const assetRule = config.module.rules.find(({ test }) => test.test('.svg'));
    const assetLoader = {
        loader: assetRule.loader,
        options: assetRule.options || assetRule.query,
    };
    config.module.rules.unshift({
        test: /\.svg$/,
        use: ['@svgr/webpack', assetLoader],
    });
    return config;
}
