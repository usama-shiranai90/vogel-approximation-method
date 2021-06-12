package com.example.application.views.vogelapproximationmethod;

import com.example.application.Asset.VogelSolution;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
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


    private TextField balancedCheckTextField = null; // used for checking if demand and supply are balanced or not.

    private final HorizontalLayout staticDemandLayout; // is Layout ma D1 sa Dn tk static field add hoti hai.
    private int forRow;
    private int forColumn;

    //  I Think its redundant , will change later.
//    private ArrayList<NumberField> supplyFieldsList;
//    private ArrayList<NumberField> demandFieldsList;

    private Button performButton;

    // Try to implement Threads or TimerUnit
    private ArrayList<NumberField> rowsPenalty;
    private ArrayList<NumberField> columnsPenalty;

    // ArrayList to perform action on
    private ArrayList<Integer> supply;
    private ArrayList<Integer> demand;
    private ArrayList<ArrayList<Integer>> cost;
    @Id("outputLayout")
    private HorizontalLayout outputLayout;

    public VogelApproximationMethodView() {
        forRow = 3;
        forColumn = 4;

        staticDemandLayout = new HorizontalLayout();

        changeHeadingVisibility(demandheading, false);
        changeHeadingVisibility(supplyheading, false);

        setTextFieldValue(supplyTextField, forRow);
        setTextFieldValue(demandTextField, forColumn);

        supplyTextField.addValueChangeListener(changeEvent -> {
            forRow = changeEvent.getValue().intValue();
        });
        demandTextField.addValueChangeListener(valueChangeEvent -> {
            forColumn = valueChangeEvent.getValue().intValue();
        });

        createtableButton.addClickListener(this::performGridCreation);
        createSubmitButton();
        performButton.addClickListener(this::performSubmitButtonAction);
    }

    private void performGridCreation(ClickEvent<Button> buttonClickEvent) {
        declaringAllArrayList();
        clearAllLayout();

        createSupplyFields();
        createDemandFields();

        changeHeadingVisibility(supplyheading, true);
        changeHeadingVisibility(demandheading, true);

        createStaticDemand_RowPenalty();
        createCostTable();
        createOptimalSolutionLayout();

    }

    private void declaringAllArrayList() {
//        supplyFieldsList = new ArrayList<>();
//        demandFieldsList = new ArrayList<>();
        supply = new ArrayList<>();
        demand = new ArrayList<>();
        cost = new ArrayList<>();
        rowsPenalty = new ArrayList<>();
        columnsPenalty = new ArrayList<>();
    }

    private void createOptimalSolutionLayout() {

        HorizontalLayout layout = new HorizontalLayout();
//        layout.getStyle().set( , );

    }

    private void performSubmitButtonAction(ClickEvent<Button> buttonClickEvent) {
        /**
         * understanding thread
         for (int i = 0; i < 3; i++) {
         getUI().get().access(new Command() {
        @Override public void execute() {
        try {
        for (int i1 = 0; i1 < 5; i1++) {
        Thread.sleep(1000);
        System.out.print(i1 + "\n" + "..");
        rowsPenalty.get(0).setValue((i1 * 200.0));
        }

        } catch (InterruptedException e) {
        System.out.println(" interrupted");
        }
        }
        });
         }*/

        VogelSolution vogelSolution = new VogelSolution(supply, demand, cost);
        vogelSolution.passPenaltyTextFields(rowsPenalty, columnsPenalty);
        vogelSolution.solve();

        VerticalLayout layout =  new VerticalLayout();
        H3 tableHead =  new H3("Final Table");
        TextArea textArea = new TextArea();
        textArea.setValue(vogelSolution.finalTableToPass());

        textArea.getStyle().set("width", "20em");
        textArea.getStyle().set("height", "9em");

        layout.add(tableHead, textArea);
        outputLayout.add(layout);
        layout.getStyle().set("width", "auto");
        Span result = new Span(vogelSolution.optimalEquation());
        Label label =new Label();
        label.setText(String.valueOf(vogelSolution.getTotalCost()));

        outputLayout.add(result , label);
    }

    private void createCostTable() {

        for (int row = 0; row < forRow + 1; row++) { // plus 1 for Row pen
            HorizontalLayout layout = new HorizontalLayout();
            TextField supplyStaticTF;
            cost.add(new ArrayList<>());

            for (int column = 0; column < forColumn + 2; column++) { //  +1 for supply and +1 for column pen.

                // static header for Supply and  row penalty.
                if (column == 0) {
                    if (row == forRow) {  // row-pen
                        TextField penHeading = new TextField();
                        penHeading.setValue("Column Penalty");
                        penHeading.setEnabled(false);
                        penHeading.getStyle().set("width", "8em");
                        penHeading.getStyle().set("text-align-last", "center");
                        layout.add(penHeading);
                    } else {  // supplies
                        supplyStaticTF = new TextField();
                        supplyStaticTF.setValue("S" + (row + 1));
                        supplyStaticTF.setEnabled(false);
                        supplyStaticTF.getStyle().set("width", "8em");
                        supplyStaticTF.getStyle().set("text-align-last", "center");
                        layout.add(supplyStaticTF);
                    }
                } else { // column not zero.
                    NumberField costTextField = null;

                    if (column < forColumn + 1) { // add cost field and column-pen fields.

                        if (row == forRow) {  // add column penalty fields.
                            costTextField = new NumberField();
//                            costTextField.setPlaceholder("column-pen");
                            costTextField.setValue(0.0);
                            costTextField.setEnabled(false);
                            costTextField.getStyle().set("text-align-last", "center");
                            columnsPenalty.add(costTextField);

                        } else {  // cost fields.
                            costTextField = new NumberField();
                            costTextField.setPlaceholder("cost section " + column);
                            costTextField.setEnabled(true);
                            costTextField.getStyle().set("text-align-last", "center");
//                        layout.add(costTextField);
                            cost.get(row).add(column - 1, 0);

                            int finalRow = row;
                            int finalColumn = column;
                            costTextField.addValueChangeListener(valueChangeEvent -> {
                                System.out.printf("finalRow = %d \t\t finalColumn = %d \t\t value = %d \n"
                                        , finalRow, finalColumn - 1, valueChangeEvent.getValue().intValue());
                                cost.get(finalRow).set(finalColumn - 1, valueChangeEvent.getValue().intValue());
                            });

                        }

                    } else if (column == forColumn + 1) {  // add row penalties fields.


                        if (row == forRow){
                            balancedCheckTextField =  new TextField();
                            balancedCheckTextField.setHelperText("Total Supplies & Demands");
                            balancedCheckTextField.setEnabled(false);
                            balancedCheckTextField.getStyle().set("text-align-last", "center");
                            balancedCheckTextField.getStyle().set("width", "8em");
                            layout.add(balancedCheckTextField);
                        }
                        else
                        {
                            costTextField = new NumberField();
                            costTextField.setPlaceholder("Row-pen");
                            costTextField.setEnabled(false);
                            costTextField.getStyle().set("text-align-last", "center");
                        }
                        if (balancedCheckTextField ==null) {
                            rowsPenalty.add(costTextField);
                        }
                    }

                    if (balancedCheckTextField ==null){
                        layout.add(costTextField);
                    }

                }
            }  // c
            addTo.add(layout);

        } // r

        addTo.add(performButton);



    }

    private void createStaticDemand_RowPenalty() {
        for (int i = 0; i < forColumn; i++) {

            TextField field = new TextField();
            field.setValue("D" + (i + 1));
            field.setEnabled(false);
            field.getStyle().set("width", "8em");
            field.getStyle().set("text-align-last", "center");

            staticDemandLayout.add(field);
            if (i == forColumn - 1) {
                TextField penHeading = new TextField();
                penHeading.setValue("Row Penalty");
                penHeading.setEnabled(false);
                penHeading.getStyle().set("width", "8em");
                penHeading.getStyle().set("text-align-last", "center");
                staticDemandLayout.add(penHeading);

            }
        }
        staticDemandLayout.getStyle().set("justify-content", "flex-start");
        staticDemandLayout.getStyle().set("align-items", "flex-start");
        staticDemandLayout.getStyle().set("margin-left", "12%");

        addTo.add(staticDemandLayout);

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
//            demandFieldsList.add(staticField);
            demandLayout.add(staticField);

            int finalI = i;
            demand.add(0);
            staticField.addValueChangeListener(event -> {
                demand.set(finalI, event.getValue().intValue());

                int s_total =  supply.stream().mapToInt(a->a).sum();
                int d_total =  demand.stream().mapToInt(a->a).sum();

                balancedCheckTextField.setValue(String.valueOf(s_total == d_total));
            });

        }
    }

    private void createSupplyFields() {
        for (int i = 0; i < forRow; i++) {
            NumberField staticField = new NumberField();
            staticField.setPlaceholder("S" + (i + 1));
            staticField.setEnabled(true);
            staticField.setId("supply" + (i + 1));
            staticField.getStyle().set("text-align-last", "center");

            supply.add(0);
            int finalI = i;
            staticField.addValueChangeListener(event -> {
                supply.set(finalI, event.getValue().intValue());

                int s_total =  supply.stream().mapToInt(a->a).sum();
                int d_total =  demand.stream().mapToInt(a->a).sum();

                balancedCheckTextField.setValue(String.valueOf(s_total == d_total));
            });
//            supplyFieldsList.add(staticField);
            supplyLayout.add(staticField);
        }
    }

    private void createSubmitButton() {
        performButton = new Button("Submit");
        performButton.setId("submit_button");
        performButton.getStyle().set("background-color", "#D1F8FF");
        performButton.getStyle().set("color", "#00000");
        performButton.getStyle().set("align-self", "flex-start");
    }

    private void clearAllLayout() {
        addTo.removeAll();
        staticDemandLayout.removeAll();
        demandLayout.removeAll();
        supplyLayout.removeAll();
        outputLayout.removeAll();
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
