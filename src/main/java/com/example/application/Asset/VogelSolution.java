package com.example.application.Asset;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.Command;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Demand = columns   , Supply = rows
 * phely column pen , then us ma sa max value .
 * then row pen , then us ma sa max value
 * jo max value bari us ma butabik kaam ,   -> row pen bari then row ma sa choti value. else column ma sa choti.
 */

public class VogelSolution {

    protected ArrayList<Integer> supplies;
    protected ArrayList<Integer> demands;
    protected ArrayList<Integer> rowPenalty;
    protected ArrayList<Integer> columnPenalty;
    protected ArrayList<ArrayList<Cell>> costMatrix;
    private int noOfSupplies;  //
    private int noOfDemands;
    private int totalSupply;
    private int totalDemand;
    private int totalCost;

    private final boolean[] rowToSkim;
    private final boolean[] columnToSkim;

    private int columnMaxValue;
    private int columnMaxIndex;
    private int rowMaxValue;
    private int rowMaxIndex;
    private boolean penaltyCheck; // false for row , true column

    int cellRow;
    int cellColumn;
    int cellMinValue;

    public VogelSolution(ArrayList<Integer> supply, ArrayList<Integer> demand, ArrayList<ArrayList<Integer>> cost) {
        supplies = supply;
        demands = demand;

        noOfDemands = demand.size();
        noOfSupplies = supply.size();
        totalSupply = supply.stream().mapToInt(a -> a).sum();
        totalDemand = demand.stream().mapToInt(a -> a).sum();
        columnPenalty = new ArrayList<>(noOfSupplies);
        rowPenalty = new ArrayList<>(noOfDemands);

        rowToSkim = new boolean[noOfSupplies];
        columnToSkim = new boolean[noOfDemands];

        totalCost = 0;
        columnMaxIndex = 0;
        columnMaxValue = 0;
        rowMaxIndex = 0;
        rowMaxValue = 0;

        costMatrix = new ArrayList<>();
        for (int row = 0; row < cost.size(); row++) {
            costMatrix.add(new ArrayList<>());
            for (int col = 0; col < cost.get(row).size(); col++) {

                Cell cell = new Cell(cost.get(row).get(col));
                costMatrix.get(row).add(cell);

            }
        }

        IntStream.iterate(0, v -> v + 1).limit(noOfSupplies).forEach(value -> {
            rowToSkim[value] = false;
            rowPenalty.add(-1);
        });

        IntStream.iterate(0, v -> v + 1).limit(noOfDemands).forEach(value -> {
            columnToSkim[value] = false;
            columnPenalty.add(-1);
        });
    }

    public void solve() {

        if (totalSupply != totalDemand) {

            Notification errorNotification = new Notification();
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Span label = new Span("The Given Problem is Unbalanced. please check demand and supply Fields");
            Button closeButton = new Button("Close", e -> errorNotification.close());

            errorNotification.setPosition(Notification.Position.MIDDLE);
            errorNotification.add(label, closeButton);

            label.getStyle().set("margin-right", "4.2rem");
            label.getStyle().set("font-size", "0.6em");

            closeButton.getStyle().set("margin-right", "0.9rem");
            closeButton.getStyle().set("font-size", "0.8em");

            errorNotification.open();

        } else {  // balanced.
            System.out.println("im balanced");
            while (true) {
                implementRowPenalty();  // working fine
                implementColumnPenalty();  // working fine.

                for (int i = 0; i < rowPenalty.size(); i++) {
//                    System.out.println("row pen to add = " + rowPenalty.get(i).doubleValue());
                    rowsPenalty_text_field.get(i).setValue(rowPenalty.get(i).doubleValue());
                  /*  rowsPenalty_text_field.get(i).getUI().get().access(new Command() {
                        @Override
                        public void execute() {
                            try {
                                rowsPenalty_text_field.get(i).setValue(rowPenalty.get(i).doubleValue());
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
                }
                for (int i = 0; i < columnPenalty.size(); i++) {
//                    System.out.println("column pen to add = " + columnPenalty.get(i).doubleValue());

                    columnsPenalty_text_field.get(i).setValue(columnPenalty.get(i).doubleValue());
                    /*columnsPenalty_text_field.get(i).getUI().get().access(new Command() {
                        @Override
                        public void execute() {
                            try {
                                columnsPenalty_text_field.get(i).setValue(columnPenalty.get(i).doubleValue());
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });*/
                }


                getRowMaxValue();  //
                getColumnMaxValue();

//                System.out.println("Before : \tcellRow  = " + cellRow + "\t\tcell column" + cellColumn + "\t\t value = " + cellMinValue);
                if (cellRow != -1 && cellColumn != -1) {

                    if (rowMaxValue > columnMaxValue) {  // row penalty apply .
                        System.out.println("performing Row Penalty");
                        penaltyCheck = false;
                        findMinimumCell();

                        System.out.println("cellRow  = " + cellRow + "\t\tcell column" + cellColumn + "\t\t value = " + cellMinValue);
                        if (demands.get(cellColumn) > supplies.get(cellRow)) { // demand ki value bari hai , cell ka min ma supply store karo
                            int min = supplies.get(cellRow);

                            costMatrix.get(cellRow).get(cellColumn).setMinimumValue(min);
                            supplies.set(cellRow, 0);

                            demands.set(cellColumn, (demands.get(cellColumn) - min));
                            System.out.println("row-->Will Skip Row of = " + cellRow + "\t\t" + demands.toString());
                            rowToSkim[cellRow] = true;

                            rowPenalty.set(cellRow, -1);
                        } else {
                            int min = demands.get(cellColumn);
                            costMatrix.get(cellRow).get(cellColumn).setMinimumValue(min);
                            demands.set(cellColumn, 0);
                            supplies.set(cellRow, (supplies.get(cellRow) - min));
                            columnToSkim[cellColumn] = true;
                            System.out.println("row-->Will Skip column of = " + cellColumn + "\t\t" + supplies.toString());

                            columnPenalty.set(cellColumn, -1);
                        }


                    } else {  // column penalty.  if (rowMaxValue < columnMaxValue)
                        System.out.println("performing Column Penalty");
                        penaltyCheck = true;
                        findMinimumCell();

                        System.out.println("cellRow  = " + cellRow + "\t\tcell column" + cellColumn + "\t\t value = " + cellMinValue);

                        if (demands.get(cellColumn) > supplies.get(cellRow)) { // demand ki value bari hai , cell ka min ma supply store karo
                            int min = supplies.get(cellRow);
                            costMatrix.get(cellRow).get(cellColumn).setMinimumValue(min);
                            supplies.set(cellRow, 0);

                            demands.set(cellColumn, (demands.get(cellColumn) - min));
                            System.out.println("col--> Will Skip Row of = " + cellRow + "\t\t" + demands.toString());
                            rowToSkim[cellRow] = true;
                            rowPenalty.set(cellRow, -1);
                        } else {
                            int min = demands.get(cellColumn);
                            costMatrix.get(cellRow).get(cellColumn).setMinimumValue(min);
                            demands.set(cellColumn, 0);
                            supplies.set(cellRow, (supplies.get(cellRow) - min));

                            columnToSkim[cellColumn] = true;
                            System.out.println("col--> Will Skip column of = " + cellColumn + "\t\t" + supplies.toString());

                            columnPenalty.set(cellColumn, -1);
                        }

                    }
                    printtable();
                    System.out.println("\n\n\n");

                    if (areAllTrue(columnToSkim) || areAllTrue(rowToSkim)) {
                        System.out.println("about to break!");
                        break;
                    }

                } else
                    break;
            }
        }
    }

    public String finalTableToPass() {
        StringBuilder builder = new StringBuilder();
        for (ArrayList<Cell> list : costMatrix) {
            for (Cell cellSet : list) {
                builder.append(cellSet.getValuePerCell()).append(" -> ").append(cellSet.getMinimumValue()).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public String optimalEquation() {
        StringBuilder builder = new StringBuilder();

        int c = 0;
        totalCost = 0;
        for (int i = 0; i < costMatrix.size(); i++) {
            for (int j = 0; j < costMatrix.get(i).size(); j++) {

                if (costMatrix.get(i).get(j).getMinimumValue() != -1) {
                    int value = costMatrix.get(i).get(j).getValuePerCell();
                    int minvalue = costMatrix.get(i).get(j).getMinimumValue();

                    builder.append(value).append("*").append(minvalue).append(" + ");
                    c = builder.length();

                    totalCost += value * minvalue;
                }
            }
        }
        builder.deleteCharAt(c - 2);
        return builder.toString();
    }

    private void findMinimumCell() {

        cellMinValue = Integer.MAX_VALUE;
        cellRow = -1;
        cellColumn = -1;
        if (penaltyCheck) { // true for column

            for (int i = 0; i < noOfSupplies; i++) {

                if (rowToSkim[i]) {
                    continue;
                } else {
                    if (cellMinValue > costMatrix.get(i).get(columnMaxIndex).getValuePerCell()) {
                        cellMinValue = costMatrix.get(i).get(columnMaxIndex).getValuePerCell();
                        cellRow = i;
                        cellColumn = columnMaxIndex;
                    }
                }
            }


        } else {

            for (int i = 0; i < noOfDemands; i++) {

                if (columnToSkim[i]) {
//                    System.out.println("columnToSkim[i] = " + columnToSkim[i]);
                    continue;
                } else {
                    if (cellMinValue > costMatrix.get(rowMaxIndex).get(i).getValuePerCell()) {
//                        System.out.println("cellMinValue = " + cellMinValue + "\t\t" + costMatrix.get(rowMaxIndex).get(i).getValuePerCell() + "\t" + "columnMaxIndex = " + columnMaxIndex + "\trow" + i);
                        cellMinValue = costMatrix.get(rowMaxIndex).get(i).getValuePerCell(); // 8
                        cellRow = rowMaxIndex;  // 1
                        cellColumn = i;

                    }
                }
            }


        }


    }

    private void implementRowPenalty() {
        int lowestValue = 0;
        int secondLowestValue = 0;
        for (int row = 0; row < noOfSupplies; row++) {
            lowestValue = Integer.MAX_VALUE;
            secondLowestValue = Integer.MAX_VALUE;
            if (rowToSkim[row]) {
                continue;
            } else {
                for (int col = 0; col < noOfDemands; col++) {
                    if (columnToSkim[col]) {
                        continue;
                    } else {
//                        System.out.println("secondLowestValue = " + secondLowestValue +"costMatrix.getValuePerCell() = " + costMatrix.get(row).get(col).getValuePerCell());
                        if (secondLowestValue >= costMatrix.get(row).get(col).getValuePerCell()) {
                            secondLowestValue = costMatrix.get(row).get(col).getValuePerCell();

                            if (lowestValue >= secondLowestValue) {
                                int temp = lowestValue;
                                lowestValue = secondLowestValue;
                                secondLowestValue = temp;

                            }
                        }
                    }

                }
            }
            if (secondLowestValue == Integer.MAX_VALUE) {
                secondLowestValue = lowestValue;
                lowestValue = 0;
            }
            System.out.println("secondLowestValue = " + secondLowestValue + "\t\tLowestValue = " + lowestValue);

            rowPenalty.set(row, (secondLowestValue - lowestValue));
        }
        System.out.println("rowPenalty = " + rowPenalty.toString());

    }

    private void implementColumnPenalty() {
        int lowestValue = 0;
        int secondLowestValue = 0;

        for (int col = 0; col < noOfDemands; col++) {
            lowestValue = Integer.MAX_VALUE;
            secondLowestValue = Integer.MAX_VALUE;

            if (columnToSkim[col]) {
                continue;
            } else {

                for (int row = 0; row < noOfSupplies; row++) {

                    if (!rowToSkim[row]) {
                        if (secondLowestValue >= costMatrix.get(row).get(col).getValuePerCell()) {
                            secondLowestValue = costMatrix.get(row).get(col).getValuePerCell();

                            if (lowestValue >= secondLowestValue) {
                                int temp = lowestValue;
                                lowestValue = secondLowestValue;
                                secondLowestValue = temp;

                            }
                        }
                    }
                }
            }
            if (secondLowestValue == Integer.MAX_VALUE) {
                secondLowestValue = lowestValue;
                lowestValue = 0;
            }
            columnPenalty.set(col, (secondLowestValue - lowestValue));
        }
        System.out.println("columnPenalty = " + columnPenalty.toString());
    }

    private void getRowMaxValue() {
        rowMaxValue = rowPenalty.get(0);
        for (int i = 0; i < rowPenalty.size(); i++) {
            if (rowPenalty.get(i) >= rowMaxValue) {
                rowMaxValue = rowPenalty.get(i);
                rowMaxIndex = i;
            }
        }

    }

    private void getColumnMaxValue() {
        columnMaxValue = columnPenalty.get(0);
        for (int i = 0; i < columnPenalty.size(); i++) {

            if (columnPenalty.get(i) >= columnMaxValue) {
                columnMaxValue = columnPenalty.get(i);
                columnMaxIndex = i;
            }
        }

    }

    public boolean areAllTrue(boolean[] array) {
        for (boolean b : array)
            if (!b) {
                return false;
            }
        return true;
    }

    public void printtable() {
        System.out.print("Row Skip :   ");

        for (boolean b : rowToSkim) {
            System.out.print(b + "\t");
        }
        System.out.println();

        System.out.print("column Skip :  ");
        for (boolean b : columnToSkim) {
            System.out.print(b + "\t");
        }
        System.out.println();
    }

    private ArrayList<NumberField> rowsPenalty_text_field;
    private ArrayList<NumberField> columnsPenalty_text_field;

    public void passPenaltyTextFields(ArrayList<NumberField> rowsPenalty, ArrayList<NumberField> columnsPenalty) {
        rowsPenalty_text_field = rowsPenalty;
        columnsPenalty_text_field = columnsPenalty;

    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
