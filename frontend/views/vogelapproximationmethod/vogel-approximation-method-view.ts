import { html, LitElement, customElement } from 'lit-element';



@customElement('vogel-approximation-method-view')
export class VogelApproximationMethodView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
