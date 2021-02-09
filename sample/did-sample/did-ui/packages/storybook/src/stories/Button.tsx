import React from 'react';
import './button.css';
// @ts-ignore
import { Network, Node, Edge } from '@lifeomic/react-vis-network';

export interface ButtonProps {
  primary?: boolean;
  backgroundColor?: string;
  size?: 'small' | 'medium' | 'large';
  label: string;
  onClick?: () => void;
}

export const Button: React.FC<ButtonProps> = ({
  primary = false,
  size = 'medium',
  backgroundColor,
  label,
  ...props
}) => {
  const mode = primary ? 'storybook-button--primary' : 'storybook-button--secondary';
  return (
      <Network>
        <Node id="vader" label="Darth Vader" />
        <Node id="luke" label="Luke Skywalker" />
        <Node id="leia" label="Leia Organa" />
        <Edge id="1" from="vader" to="luke" />
        <Edge id="2" from="vader" to="leia" />
      </Network>
  );
};
