import { html, LitElement, customElement } from 'lit-element';
import '@vaadin/vaadin-app-layout/src/vaadin-app-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-number-field.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

@customElement('vogel-approximation-method-view')
export class VogelApproximationMethodView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }
  render() {
    return html`
<vaadin-app-layout>
 <vaadin-horizontal-layout class="header" style="flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: var(--lumo-contrast-10pct); align-self: stretch; justify-content: center; flex-grow: 0;" id="header">
  <h1 style="flex-grow: 0; align-self: stretch; flex-shrink: 0;">Welcome To Vogel's Approximation Solver</h1>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="flex-grow: 1; flex-shrink: 1; flex-basis: auto; align-self: center; min-height: 75%; flex-direction: row; align-items: flex-start; justify-content: flex-start;">
  <vaadin-vertical-layout class="sidebar" style="flex-basis: calc(7*var(--lumo-size-s)); flex-shrink: 1; background-color: var(--lumo-contrast-5pct); flex-grow: 0; align-self: stretch; padding: var(--lumo-space-xl); width: 100%; align-items: center; flex-direction: column;">
   <h5 style="flex-grow: 0; margin-bottom: var(--lumo-space-xl); align-self: flex-start;">Fill the following to continue</h5>
   <vaadin-number-field style="width: 80%; align-self: center;" placeholder="for supply" label="Enter No Of Rows" id="supplyTextField" prevent-invalid-input></vaadin-number-field>
   <vaadin-number-field style="align-self: center; flex-grow: 0; flex-shrink: 1; width: 80%;" placeholder="for demand" label="Enter No of Columns" id="demandTextField"></vaadin-number-field>
   <vaadin-button id="createtableButton" style="margin-top: var(--lumo-space-m); align-self: flex-end; margin-right: var(--lumo-space-l);">
    <iron-icon icon="lumo:arrow-right" slot="suffix"></iron-icon> Next 
   </vaadin-button>
  </vaadin-vertical-layout>
  <div style="width: 100%; flex-grow: 0; align-self: flex-start;">
   <vaadin-vertical-layout style="flex-grow: 1; justify-content: flex-start; align-items: flex-start;">
    <h4 id="supplyheading" style="align-self: flex-start;">Supply :</h4>
    <vaadin-horizontal-layout id="supplyLayout" style="flex-grow: 0; flex-shrink: 1; align-self: flex-start; align-items: center; justify-content: center;"></vaadin-horizontal-layout>
   </vaadin-vertical-layout>
   <vaadin-vertical-layout style="flex-grow: 1; align-items: flex-start; justify-content: flex-start;">
    <h4 id="demandheading">Demand </h4>
    <vaadin-horizontal-layout style="flex-grow: 0; align-self: flex-start; align-items: center; justify-content: center;" id="demandLayout"></vaadin-horizontal-layout>
   </vaadin-vertical-layout>
   <hr>
   <vaadin-vertical-layout id="addTo" style="flex-grow: 1; align-items: flex-start; justify-content: flex-start; align-content: center; width: 100%; height: 100%;"></vaadin-vertical-layout>
   <vaadin-horizontal-layout id="outputLayout" style="align-items: center; justify-content: center;"></vaadin-horizontal-layout>
  </div>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="footer" style="flex-basis: var(--lumo-size-l); flex-shrink: 1; background-color: var(--lumo-contrast-10pct); align-self: center; flex-grow: 0; justify-content: center; align-items: stretch; width: 100%;">
  <h4 style="margin-right: var(--lumo-space-xs); align-self: stretch; flex-grow: 0;">Created By </h4>
  <a href="https://github.com/" style="align-self: center;"> OneEyedOwl</a>
 </vaadin-horizontal-layout>
</vaadin-app-layout>
`;
  }
}
