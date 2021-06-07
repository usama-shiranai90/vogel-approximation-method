package com.example.application.views.vogelapproximationmethod;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import java.awt.*;
import java.util.ArrayList;

@Route(value = "vam")
@RouteAlias(value = "")
@PageTitle("Vogel Approximation Method")
@Tag("vogel-approximation-method-view")
@JsModule("./views/vogelapproximationmethod/vogel-approximation-method-view.ts")
public class VogelApproximationMethodView extends LitTemplate {
    @Id("supplyTextField")
    private NumberField supplyTextField;
    @Id("demandTextField")
    private NumberField demandTextField;
    @Id("createtableButton")
    private Button createtableButton;
    @Id("tableLayout")
    private VerticalLayout tableLayout;


    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    private int forRow;
    private int forColumn;
    @Id("staticDemandsection")
    private HorizontalLayout staticDemandsection;
    @Id("staticSupplyNumbers")
    private VerticalLayout staticSupplyNumbers;
    @Id("supplyvericalTextField")
    private VerticalLayout supplyvericalTextField;

    ArrayList<ArrayList<TextField>> costTextFields;
    @Id("costLayout")
    private VerticalLayout costLayout;

    public VogelApproximationMethodView() {
        costTextFields = new ArrayList<>();

        forRow = 3;
        forColumn = 4;
        setTextFieldValue(supplyTextField, forRow);
        setTextFieldValue(demandTextField, forColumn);

        supplyTextField.addValueChangeListener(changeEvent -> {
            forRow = changeEvent.getValue().intValue();
        });
        demandTextField.addValueChangeListener(valueChangeEvent -> {
            forColumn = valueChangeEvent.getValue().intValue();
        });

        createtableButton.addClickListener(this::performGridCreation);

    }

    private void performGridCreation(ClickEvent<Button> buttonClickEvent) {
        staticDemandsection.removeAll();
        staticSupplyNumbers.removeAll();
        costLayout.removeAll();

        Notification.show("These are my values R = " + forRow + "\t C = " + forColumn);

        createStaticHeaderForDemand();
        createStaticHeaderForSupply();

        // cost table
/*        for (int row = 0; row < forRow; row++) {
            costTextFields.add(new ArrayList<>());
            HorizontalLayout rowLayout = new HorizontalLayout();

            for (int column = 0; column < forColumn; column++) {
                TextField field = new TextField();
                getCostTextFields().get(row).add(field);

                rowLayout.add(field);
            }

            costLayout.add(rowLayout);

        }*/


    }

    private void setTextFieldValue(NumberField textField, int defaultvalue) {
        textField.setMin(2);
        textField.setMax(10);
        textField.setValue((double) defaultvalue);

    }

    private void createStaticHeaderForDemand() {
        for (int i = 0; i < forColumn + 1; i++) {  // 1 for supply header

            if (forColumn == i) {
                H4 supplyLabel = new H4("Supply");
                staticDemandsection.add(supplyLabel);
                supplyLabel.setMaxWidth("15%");
                break;
            }
            TextField staticField = new TextField();
            staticField.setValue("D" + (i + 1));
            staticField.setMaxWidth("15%");
            staticField.setEnabled(false);
            staticDemandsection.add(staticField);
        }
    }

    private void createStaticHeaderForSupply() {
        for (int i = 0; i < forRow + 1; i++) {

            if (forRow == i) {
                H4 demandLabel = new H4("Demand");
                staticSupplyNumbers.add(demandLabel);
                break;
            }
            TextField staticField = new TextField();
            staticField.setValue("S" + (i + 1));
            staticField.setEnabled(false);
            staticField.setMaxWidth("15%");
//            staticField.getStyle().set("width", "8%");
            staticField.getStyle().set("text-align-last", "center");
            staticSupplyNumbers.add(staticField);
        }
    }

    public ArrayList<ArrayList<TextField>> getCostTextFields() {
        return costTextFields;
    }

    public void setCostTextFields(ArrayList<ArrayList<TextField>> costTextFields) {
        this.costTextFields = costTextFields;
    }
}
