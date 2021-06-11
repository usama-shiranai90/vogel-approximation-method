package com.example.application;

import com.example.application.Asset.VogelSolution;

import java.util.ArrayList;

public class MainTest {

    public static void main(String[] args) {

        ArrayList<Integer> supply = new ArrayList<>();
        supply.add(55);
        supply.add(45);
        supply.add(30);

        ArrayList<Integer> deamnd = new ArrayList<>();
        deamnd.add(40);
        deamnd.add(20);
        deamnd.add(50);
        deamnd.add(20);

        ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
        cost.add(new ArrayList<>());
        cost.add(new ArrayList<>());
        cost.add(new ArrayList<>());


        cost.get(0).add(12);
        cost.get(0).add(4);
        cost.get(0).add(9);
        cost.get(0).add(5);

        cost.get(1).add(8);
        cost.get(1).add(1);
        cost.get(1).add(6);
        cost.get(1).add(6);


        cost.get(2).add(1);
        cost.get(2).add(12);
        cost.get(2).add(4);
        cost.get(2).add(7);

        VogelSolution vogelSolution = new VogelSolution(supply,deamnd,cost);
        vogelSolution.solve();



    }

}
