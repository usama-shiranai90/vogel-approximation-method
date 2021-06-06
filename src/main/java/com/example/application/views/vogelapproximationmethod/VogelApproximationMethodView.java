package com.example.application.views.vogelapproximationmethod;

import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

@Route(value = "vam")
@RouteAlias(value = "")
@PageTitle("Vogel Approximation Method")
@Tag("vogel-approximation-method-view")
@JsModule("./views/vogelapproximationmethod/vogel-approximation-method-view.ts")
public class VogelApproximationMethodView extends LitTemplate {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    public VogelApproximationMethodView() {
    }
}
