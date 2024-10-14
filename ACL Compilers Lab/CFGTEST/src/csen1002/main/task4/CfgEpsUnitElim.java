package csen1002.main.task4;

import java.security.Key;
import java.util.*;

public class CfgEpsUnitElim {

    String variables;
    String characters;

    String Sequences;

    ArrayList<String> sequence = new ArrayList<>();

    ArrayList<String> Prods = new ArrayList<>();


    /**
     * Constructs a Context Free Grammar
     *
     * @param cfg A formatted string representation of the CFG. The string
     *            representation follows the one in the task description
     */
    public CfgEpsUnitElim(String cfg) {

        String[] x = cfg.split("#");
        variables = x[0];
        characters = x[1];
        Sequences = x[2];
        String[] x2 = Sequences.split(";");
        // ArrayList<String> Prods= new ArrayList<>();
        for (int i = 0; i < x2.length; i++) {
            Prods.add(x2[i]);
        }


        //  System.out.println("Prods "+ Prods);
        // System.out.println("awl wahd "+ Prods.get(0));

        //  sequence=new ArrayList<>();
        //System.out.println("ttt "+Arrays.asList(x[2]).get(0));
        //sequence = (ArrayList<String>) Arrays.asList(x[2]);
        // System.out.println("7777 "+ sequence);


        // eliminateEpsilonRules();
        // eliminateUnitRules();


        // TODO Auto-generated constructor stub
    }

    /**
     * @return Returns a formatted string representation of the CFG. The string
     * representation follows the one in the task description
     */
    @Override
    public String toString() {
        Sequences = "";
        for (String s : Prods) {
            Sequences += s + ";";
        }
        Sequences = Sequences.substring(0, Sequences.length() - 1);
        String l = variables + "#" + characters + "#" + Sequences;
        // TODO Auto-generated method stub
        return l;
    }

    /**
     * Eliminates Epsilon Rules from the grammar
     */
    public void eliminateEpsilonRules() {
//        System.out.println("bagarb "+ Prods.get(2));
//        String s= Prods.get(2);
//        s= s.replaceAll(",e","");
//        System.out.println("bagarb 2 " + s);
        System.out.println("lmm : " + Prods);
        ArrayList<String> lhsList = new ArrayList<String>();
        String lhs = "";
        String rhs = "";
        String rules = "";
        String variable = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < Prods.size(); i++) {
            boolean q = false;
            String s = Prods.get(i);
            String[] arr = s.split("/");
            variable = arr[0];
            rules = arr[1];
            if (rules.contains("e")) {
                rules = rules.replaceAll("e", "");
                if (rules.charAt(0) == ',') { //moshkela hena
                    rules = rules.substring(1, rules.length());
                } else if (rules.charAt(rules.length() - 1) == ',') rules = rules.substring(0, rules.length() - 1);
                else if (rules.contains(",,")) rules = rules.replaceAll(",,", ",");

                s = variable + "/" + rules;
                Prods.set(i, s);
                lhsList.add(variable);
            }

        }

//        System.out.println("PRODSS" + Prods);
//        System.out.println("listt" + lhsList);
//        System.out.println("Rulest" + rules);
        for (int n = 0; n < lhsList.size(); n++) {
            String c = lhsList.get(n);
            for (int i = 0; i < Prods.size(); i++) {
                String s = Prods.get(i);
                String[] arr1 = s.split("/");
                variable = arr1[0];
                rules = arr1[1];
                String[] arr2 = rules.split(",");
                ArrayList<String> arrayx = new ArrayList<String>(List.of(arr2));
                for (int j = 0; j < arrayx.size(); j++) {
                    String rule = arrayx.get(j);
                    for (int k = 0; k < rule.length(); k++) {
                        if (c.equals(rule.charAt(k) + "")) {
                            String newrule = rule.substring(0, k) + rule.substring(k + 1, rule.length());
                            if (newrule.equals("")) {
                                if (lhsList.contains(variable)) {
                                    continue;
                                } else lhsList.add(variable);
                            } else if (!arrayx.contains(newrule))
                                arrayx.add(newrule);
                        }

                    }
                }

                Collections.sort(arrayx);
                // System.out.println("arrayx" + arrayx);
                String t = arrayx.toString().replace("[", "").replace("]", "").replace(" ", "");
                t = variable + "/" + t;
                Prods.set(i, t);
            }

        }

    }


    // TODO Auto-generated method stub


    /**
     * Eliminates Unit Rules from the grammar
     */
    public void eliminateUnitRules() {

        String variable = "";
        String rules = "";
        HashMap<String, ArrayList<String>> myMap = new HashMap<>();
        for (int i = 0; i < Prods.size(); i++) {
            ArrayList<String> list1 = new ArrayList<>();
            // boolean q = false;
            String s = Prods.get(i);
            String[] arr = s.split("/");
            variable = arr[0];
            rules = arr[1];
            String[] parts = rules.split(",");
            ArrayList<String> arrayx = new ArrayList<String>(List.of(parts));
            myMap.put(variable, arrayx);
            //   System.out.println("mymapp"+myMap);
        }


        System.out.println("Map " + myMap);

        for (Map.Entry<String, ArrayList<String>> entry : myMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            //myMap.put(key,new ArrayList<>());
            HashSet<String> visited = new HashSet<>();
//            ArrayList<String> valueCopy = new ArrayList<>(value);
            for (int j = 0;  j < value.size(); j++) {
                System.out.println("Old Value " + value);
                System.out.println("Vsisited" + visited);
                String y = value.get(j);
                if (y.length() == 1 && Character.isUpperCase(y.charAt(0))) {
                    //  System.out.println("mym"+y);
                    visited.add(y);
                    if (y.charAt(0) == key.charAt(0)) {
                        value.remove(j);
//                        valueCopy.remove(j);
                        j--;
                    } else {
                        value.remove(j);
//                        valueCopy.remove(j);
                        j--;
                        ArrayList<String> listx = myMap.get(y);

                        System.out.println("New `rule " + listx);
                        for (int i = 0; i < listx.size(); i++) {
                            String z = listx.get(i);
                            System.out.println("z " + z);
                            if (z.equals(key) || value.contains(z)) {
                                continue;
                            } else if (z.length() == 1 && Character.isUpperCase(z.charAt(0))) {
                                if (visited.contains(z)) {
                                    continue;
                                } else {
                                    value.add(z);

                                }
                            } else
                                value.add(z);
                        }

                    }

                }

            }
            Collections.sort(value);
            System.out.println("New Value " + value);

            //  myMap.put(key,value);


            // String[] arr2 = rules.split(",");
            // ArrayList<String> arrayx = new ArrayList<String>(List.of(arr2));
            // String[] arr2 = rules.split(",");
//            for (int i = 0; i < value.size(); i++) {
//                char c = value.get(i).charAt(i);
//                if (Character.isUpperCase(c)) {
//                    System.out.println("Key: " + key + ", Value: " + c);
//                    String y = String.valueOf(c);
//                    for (int j = 0; j < key.length(); j++) {
//                        if (key.equals(y)) {
//                            // String x=myMap.get(key);
//                            // c=value.substring(0)
//                            // String newrule = value.substring(0, i) + value.substring(j + 1, value.length());
//
//                        }
//                    }
//                }

        }

        for (int i = 0; i < Prods.size(); i++) {
            String n = Prods.get(i).charAt(0) + "";

            String t = myMap.get(n).toString().replace("[", "").replace("]", "").replace(" ", "");
            t = n + "/" + t;
            Prods.set(i, t);

        }
        System.out.println("map" + myMap);
    }
//
//
//
//
//


    // TODO Auto-generated method stub


    public static void main(String[] args) {
        String cfg = ("S;A;B#a;b#S/ASABA;A/b,e");

        CfgEpsUnitElim mycfg = new CfgEpsUnitElim(cfg);


        System.out.println("Variables: " + mycfg.variables);
        System.out.println("Characters: " + mycfg.characters);
        System.out.println("Prodsss " + mycfg.Prods);

        // mycfg.eliminateUnitRules();

        //  mycfg.eliminateEpsilonRules();
        System.out.println("my cfg" + mycfg);

        mycfg.eliminateUnitRules();
        System.out.println("Final " + mycfg.toString());


    }
}

