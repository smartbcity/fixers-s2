import React from 'react';
// also exported from '@storybook/react' if you can deal with breaking changes in 6.1
import { Story, Meta } from '@storybook/react/types-6-0';
import {Automate, AutomateProps} from "./index";
import {getDidS2} from "fixers-did-domain";


export default {
  title: 'S2/Automate',
  component: Automate,
  argTypes: {
    backgroundColor: { control: 'color' },
  },
} as Meta;

const Template: Story<AutomateProps> = (args) => <Automate {...args} />;



export const DidS2 = Template.bind({});
DidS2.args = {
 automate: getDidS2()
};
