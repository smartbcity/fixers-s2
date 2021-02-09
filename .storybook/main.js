module.exports = {
  "stories": [
    "../docs/**/*.stories.mdx",
    "../docs/**/*.stories.@(js|jsx|ts|tsx)"
  ],
  addons: [
    {
      name: "@storybook/addon-links",
    },
    {
      name: "@storybook/addon-docs",
      options: {
        configureJSX: true,
        transcludeMarkdown: true,
      },
    },
  ],
}