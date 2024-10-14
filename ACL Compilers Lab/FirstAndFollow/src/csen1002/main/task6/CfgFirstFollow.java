package csen1002.main.task6;
import java.util.*;





/**
 * Write your info here
 * 
 * @name Omar Alaa
 * @id 49-9719
 * @labNumber t-19
 */


class CFG {
    List<String> variables;
    List<String> terminals;
    Map<String, List<String>> rules;

   public CFG() {
       variables = new ArrayList<>();
       terminals = new ArrayList<>();
       rules = new LinkedHashMap<>();
   }

   public List<String> getVariables() {
       return variables;
   }

   public List<String> getTerminals() {
       return terminals;
   }

   public Map<String, List<String>> getRules() {
       return rules;
   }

   public void setVariables(List<String> variables) {
       this.variables = variables;
   }

   public void setTerminals(List<String> terminals) {
       this.terminals = terminals;
   }

   public void addRule(String variable, List<String> rule) {
       if (!rules.containsKey(variable)) {
           rules.put(variable, new ArrayList<>());
       }
       rules.get(variable).addAll(rule);
   }

   public void removeRule(String variable, String rule) {
       if (rules.containsKey(variable)) {
           rules.get(variable).remove(rule);
       }
   }

   

   public CFG copyCFG(CFG original) {
       CFG copy = new CFG();
       copy.variables.addAll(original.variables);
       copy.terminals.addAll(original.terminals);

       for (Map.Entry<String, List<String>> entry : original.rules.entrySet()) {
           copy.rules.put(entry.getKey(), new ArrayList<>(entry.getValue()));
       }

       return copy;
   }


}




public class CfgFirstFollow {
	
	public static CFG cfg;
	
	public static CFG constructCFG(String cfgString) {
        CFG cfg = new CFG();
        
        String[] parts = cfgString.split("#");
        String variablesString = parts[0];
        String terminalsString = parts[1];
        String rulesString = parts[2];
        String[] variablesArray = variablesString.split(";");
        for (String variable : variablesArray) {
            cfg.getVariables().add(variable);
        }
        
        String[] terminalsArray = terminalsString.split(";");
        for (String terminal : terminalsArray) {
            cfg.getTerminals().add(terminal);
        }
        String[] rulesArray = rulesString.split(";");
        for (String rule : rulesArray) {
            String[] ruleParts = rule.split("/");
            String variable = ruleParts[0];
            String[] productionsArray = ruleParts[1].split(",");
            
            for (String production : productionsArray) {
                cfg.addRule(variable, Arrays.asList(production));
            }
        }
        
        return cfg;
    }
	
	
	
	/**
	 * Constructs a Context Free Grammar
	 * 
	 * @param cfg A formatted string representation of the CFG. The string
	 *            representation follows the one in the task description
	 */
	
	public CfgFirstFollow(String cfgString) {
        this.cfg = constructCFG(cfgString);
	}

	/**
	 * Calculates the First Set of each variable in the CFG.
	 * 
	 * @return A string representation of the First of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public static String first() {
	    Map<String, List<String>> rules = cfg.getRules();
	    List<String> variables = cfg.getVariables();
	    List<String> terminals = cfg.getTerminals();
	    boolean firstFlag = false;
	    System.out.println(rules);
	    System.out.println(variables);
	    System.out.println(terminals);

//	    LinkedHashMap<String, List<String>> firstList = new LinkedHashMap<>();
//
//	    Set<String> visited = new HashSet<>();
//
//	    for (String variable : variables) {
//	        if (!firstList.containsKey(variable)) {
//	            calculateFirst(variable, rules, firstList, visited);
//	        	
//	        }
//	    }
//
//	    System.out.println(firstList);
	    calculateFirst(rules,variables,terminals);
	    return null;
	}


//	public static void calculateFirst(String variable, Map<String, List<String>> rules, LinkedHashMap<String, List<String>> firstList, Set<String> visited) {
//	    if (visited.contains(variable)) {
//	        return;
//	    }
//	    visited.add(variable);
//
//	    List<String> productions = rules.get(variable);
//	    if (productions != null) {
//	        List<String> firstSet = new ArrayList<>();
//	        for (String production : productions) {
//	            char firstSymbol = production.charAt(0);
//	            if (Character.isLowerCase(firstSymbol)) {
//	                if (!firstSet.contains(String.valueOf(firstSymbol))) {
//	                    firstSet.add(String.valueOf(firstSymbol));
//	                }
//	            } else if (Character.isUpperCase(firstSymbol)) {
//	                if (!firstList.containsKey(String.valueOf(firstSymbol))) {
//	                    calculateFirst(String.valueOf(firstSymbol), rules, firstList, visited);
//	                }
//	                List<String> firstOfFirstSymbol = firstList.get(String.valueOf(firstSymbol));
//	                if (firstOfFirstSymbol != null) {
//	                    for (String symbol : firstOfFirstSymbol) {
//	                        if (!firstSet.contains(symbol)) {
//	                            firstSet.add(symbol);
//	                        }
//	                    }
//	                    //System.out.println(firstOfFirstSymbol);
//	                    if (firstOfFirstSymbol.contains("e")) {
//	                    	//System.out.println(firstSymbol);
//	                        int index = productions.indexOf(production);
////	                        System.out.println(productions);
//	                        for (int i = index + 1; i < production.length(); i++) {
//	                            char nextProduction = production.charAt(i);
////	                            System.out.println(nextProduction + "AHO");
////	                            System.out.println(production + " " + nextProduction);
//	                            char nextFirstSymbol = nextProduction;
//	                            if (Character.isLowerCase(nextFirstSymbol) && nextFirstSymbol != 'e' && !firstSet.contains(String.valueOf(nextFirstSymbol))) {
//	    	                    	//System.out.println("HENA" + firstOfFirstSymbol + variable);
//
//	                                firstSet.add(String.valueOf(nextFirstSymbol));
//	                            }
//	                            else if (Character.isUpperCase(nextFirstSymbol)) {
//	    	                    	//System.out.println("HENA" + firstOfFirstSymbol + variable);
//
//	                                List<String> firstOfNextFirstSymbol = firstList.get(String.valueOf(nextFirstSymbol));
//	                                if (firstOfNextFirstSymbol != null) {
//	                                    for (String symbol : firstOfNextFirstSymbol) {
//	                                        if (!firstSet.contains(symbol)) {
//	                                            firstSet.add(symbol);
//	                                        }
//	                                    }
//	                                }
//	                            }
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	        firstList.put(variable, firstSet);
//	    }
//	}




    public static Map<String, Set<Character>> calculateFirst(Map<String, List<String>> rules, List<String> variables, List<String> terminals) {
        // Initialize the First sets for each variable
        Map<String, Set<Character>> firstSets = new HashMap<>();
        for (String variable : variables) {
            firstSets.put(variable, new HashSet<>());
        }

        // Step 1: Initialize First sets for terminals
        for (String terminal : terminals) {
            firstSets.put(terminal, new HashSet<>(Collections.singletonList(terminal.charAt(0))));
        }

        boolean change = true;
        while (change) {
            change = false;
            for (Map.Entry<String, List<String>> entry : rules.entrySet()) {
                String variable = entry.getKey();
                List<String> productions = entry.getValue();

                for (String production : productions) {
                    char[] symbols = production.toCharArray();

                    // Step 8: Iterate over each production
                    boolean addToFirst = true;
                    for (char symbol : symbols) {
                        // Step 9: Check if epsilon is in First of all symbols
                        if (symbol != 'e' && !firstSets.get(String.valueOf(symbol)).contains('e')) {
                            addToFirst = false;
                            break;
                        }
                    }

                    // Step 10: Add epsilon to First of variable if necessary
                    if (addToFirst && !firstSets.get(variable).contains('e')) {
                        firstSets.get(variable).add('e');
                        change = true;
                    }

                    // Step 13: Iterate over each symbol in production
                    for (int i = 0; i < symbols.length; i++) {
                        char symbol = symbols[i];
                        if (Character.isUpperCase(symbol)) {
                            Set<Character> firstOfSymbol = new HashSet<>(firstSets.get(String.valueOf(symbol)));
                            // Step 14: Check if epsilon is in First of previous symbols
                            if (i > 0) {
                                boolean epsilonInPrevious = true;
                                for (int j = 0; j < i; j++) {
                                    if (!firstSets.get(String.valueOf(symbols[j])).contains('e')) {
                                        epsilonInPrevious = false;
                                        break;
                                    }
                                }
                                if (epsilonInPrevious) {
                                    // Step 15: Add First of symbol excluding epsilon to First of variable
                                    Set<Character> symbolFirstWithoutEpsilon = new HashSet<>(firstOfSymbol);
                                    symbolFirstWithoutEpsilon.remove('e');
                                    if (!firstSets.get(variable).containsAll(symbolFirstWithoutEpsilon)) {
                                        firstSets.get(variable).addAll(symbolFirstWithoutEpsilon);
                                        change = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(firstSets);
        return firstSets;
    }




	/**
	 * Calculates the Follow Set of each variable in the CFG.
	 * 
	 * @return A string representation of the Follow of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String follow() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	   public static void main(String[] args) {
	        //CFG cfg = new CFG();

	        String input = "S;T;L#a;b;c;d;i#S/ScT,T;T/aSb,iaLb,e;L/SdL,S";
//	        S;Z;R;D;E;U;O#i;l;m;p;q;u;x#S/x,E;Z/EOl,RRO,U;R/SxDx,ZSxUp,U,Ux,Z,S;D/iZpZ,UxO,R,e;E/ZZOq,Ox;U/uOE,pEZmO,e,U;O/U,p,m,ORE
//	        S;H;V;I;G#c;l;s;y#S/VH,sHI;H/cSSGI,HIVG;V/Vy,c,sHI,sIG,SH,SySy;I/lSSI,IV,s,IVI;G/IH,Hy,GHVy,yG,GI
//	        S;T;L#a;b;c;d;i#S/ScT,T;T/aSb,iaLb,e;L/SdL,S
			CfgFirstFollow cfg1 = new CfgFirstFollow(input);
			constructCFG(input);
			first();
			
	    }
}
