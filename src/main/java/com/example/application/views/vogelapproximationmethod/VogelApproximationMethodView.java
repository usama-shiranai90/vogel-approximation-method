package com.example.application.views.vogelapproximationmethod;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.JsModule;

import java.util.ArrayList;
import java.util.Locale;

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

    @Id("demandLayout")
    private HorizontalLayout demandLayout;
    @Id("demandheading")
    private H4 demandheading;
    @Id("supplyheading")
    private H4 supplyheading;
    @Id("addTo")
    private VerticalLayout addTo;

    @Id("supplyLayout")
    private HorizontalLayout supplyLayout;

    private HorizontalLayout staticDlayout ;
    private int forRow;
    private int forColumn;
    private ArrayList<NumberField> supplyFieldsList;
    private ArrayList<NumberField> demandFieldsList;



    public VogelApproximationMethodView() {

        staticDlayout = new HorizontalLayout();

        changeHeadingVisibility(demandheading, false);
        changeHeadingVisibility(supplyheading, false);

        supplyFieldsList = new ArrayList<NumberField>();
        demandFieldsList = new ArrayList<NumberField>();

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
        clearAllLayout();

        createSupplyFields();
        createDemandFields();

        changeHeadingVisibility(supplyheading, true);
        changeHeadingVisibility(demandheading, true);

        createStaticDemand();
        createCostTable();




    }


    private void createCostTable() {

        for (int row = 0; row < forRow; row++) {
            HorizontalLayout layout = new HorizontalLayout();

            for (int column = 0; column < forColumn + 1; column++) {

                if (column == 0) {
                    TextField field = new TextField();
                    field.setValue("S" + (row + 1));
                    field.setEnabled(false);
                    field.getStyle().set("width", "16%");
                    field.getStyle().set("text-align-last", "center");
                    layout.add(field);
                } else {
                    NumberField field = new NumberField();
                    field.setPlaceholder("");
                    field.setEnabled(true);
                    field.getStyle().set("text-align-last", "center");
                    layout.add(field);
                }
            }
            addTo.add(layout);
        }

    }

    private void createStaticDemand() {
        for (int i = 0; i < forColumn; i++) {

            TextField field = new TextField();
            System.out.println("i = " + i);
            field.setValue("D" + (i + 1));
            field.setEnabled(false);
            field.getStyle().set("width", "16%");
            field.getStyle().set("text-align-last", "center");
            staticDlayout.add(field);
        }
        staticDlayout.getStyle().set("justify-content","flex-start");
        staticDlayout.getStyle().set("align-items","flex-start");
        staticDlayout.getStyle().set("margin-left", "10%");

        System.out.println("staticDlayout = " + staticDlayout.getChildren().count());
        addTo.add(staticDlayout);

    }

    private void setTextFieldValue(NumberField textField, int defaultvalue) {
        textField.setMin(2);
        textField.setMax(10);
        textField.setValue((double) defaultvalue);

    }

    private void createDemandFields() {
        for (int i = 0; i < forColumn; i++) {
            NumberField staticField = new NumberField();
            staticField.setPlaceholder("D" + (i + 1));
            staticField.setEnabled(true);
            staticField.getStyle().set("text-align-last", "center");

            demandFieldsList.add(staticField);
            demandLayout.add(staticField);

        }
    }

    private void createSupplyFields() {
        for (int i = 0; i < forRow; i++) {
            NumberField staticField = new NumberField();
            staticField.setPlaceholder("S" + (i + 1));
            staticField.setEnabled(true);
            staticField.setId("supply" + (i + 1));
            staticField.getStyle().set("text-align-last", "center");

            supplyFieldsList.add(staticField);
            supplyLayout.add(staticField);
        }
    }

    private void clearAllLayout() {
        addTo.removeAll();
        staticDlayout.removeAll();
        demandLayout.removeAll();
        supplyLayout.removeAll();
    }

    public void changeHeadingVisibility(H4 allHeading, boolean b) {
        allHeading.setVisible(b);


    }

    private void findComponentById(ArrayList<NumberField> supplyFieldsList, String id) {
        for (Component child : supplyFieldsList) {
            String s = child.getId().get().toLowerCase(Locale.ROOT);
            if (id.equals(s)) {
                System.out.println("foudnit");


            } else if (child instanceof HasComponents) { // recursively go through all children that themselves have children
                System.out.println("Not found");
            }
        }
//        return null; // none was found
    }


}
