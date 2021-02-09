import React from "react";
// @ts-ignore
import { Edge, Network, Node } from "@lifeomic/react-vis-network";
// @ts-ignore
import {city} from "-did-domain";


export interface AutomateProps {
  height?: number;
  automate: city.smartb.s2.dsl.automate.S2Automate;
}

const options = {
  "physics": {
    "enabled": true,
    "solver": "repulsion",
    "repulsion": {
      "damping": 0.9,
      "springConstant": 0.1,
      "springLength": 300,
      "nodeDistance": 100
    }
  },
  "manipulation": {
    "enabled": false
  },
  "interaction": {
    "zoomView":false
  }
};


export const Automate = ({ automate, height = 700 }: AutomateProps) => {
  let nodes: Node[] = [];
  let nbNodes = 0;

  const transitions = [automate.init, ...automate.transitions]
  // @ts-ignore
  let edges: Node[] = transitions.map((trans: city.smartb.s2.dsl.automate.S2Transition, index) => {
    const from = !!trans.from ? trans.from.position+1 : 0
    const to = trans.to.position +1
    nbNodes = Math.max(nbNodes, Math.max(to + 1));
    return (<Edge key={index} id={index} from={from} to={to} label={trans.role.toString() + ": " + trans.command.toString()} arrows="to"/>)
  });

  for (let i = 0; i < nbNodes; i++) {
    nodes.push(<Node key={i} id={i} label={i.toString()}/>);
  }
  return <div style={{"height": height}}>
    <Network key={automate.name} options={options}>{nodes}{edges}</Network>
  </div>;
};
