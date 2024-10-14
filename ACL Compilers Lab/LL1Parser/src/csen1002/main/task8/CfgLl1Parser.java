package csen1002.main.task8;

import java.util.*;



/**
 * Write your info here
 * 
 * @name Omar Alaa
 * @id 49-9719
 * @labNumber t-19
 */



/////////////////////////////////



 class CFG {
    List<String> variables;
    List<String> terminals;
    Map<String, List<String>> rules;
    Map<String, List<String>> first;
    Map<String, List<String>> follow;
    Map<String, Map<String, List<String>>> variableRuleFirstMap;

    public CFG() {
        variables = new ArrayList<>();
        terminals = new ArrayList<>();
        rules = new LinkedHashMap<>();
        first = new LinkedHashMap<>();
        follow = new LinkedHashMap<>();
        variableRuleFirstMap = new LinkedHashMap<>();
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

    public Map<String, List<String>> getFirst() {
        return first;
    }

    public Map<String, List<String>> getFollow() {
        return follow;
    }

    public Map<String, Map<String, List<String>>> getVariableRuleFirstMap() {
        return variableRuleFirstMap;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public void addRule(String variable, List<String> rule) {
        if (!rules.containsKey(variable)) {
            rules.put(variable, new ArrayList<>());
        }
        rules.get(variable).addAll(rule);
    }

    public void addFirst(String variable, List<String> firstString) {
        if (!first.containsKey(variable)) {
            first.put(variable, new ArrayList<>());
        }
        first.get(variable).addAll(firstString);
    }

    public void addFollow(String variable, List<String> followString) {
        if (!follow.containsKey(variable)) {
            follow.put(variable, new ArrayList<>());
        }
        follow.get(variable).addAll(followString);
    }


}



/////////////////////////////////



public class CfgLl1Parser {

	public   CFG cfg;
	/**
	 * Constructs a Context Free Grammar
	 * 
	 * @param cfg A formatted string representation of the CFG, the First sets of
	 *            each right-hand side, and the Follow sets of each variable. The
	 *            string representation follows the one in the task description
	 */
	public CfgLl1Parser(String cfgString) {
        this.cfg = constructCFG(cfgString);
	}

	/**
	 * @param input The string to be parsed by the LL(1) CFG.
	 * 
	 * @return A string encoding a left-most derivation.
	 */
	public  String parse(String input) {
		// TODO Auto-generated method stub
		List<String> parser = generateDerivation(input);
		String output = "";
		for(String s : parser) {
			output = output + s + ";";
			
		}
		output = output.substring(0,output.length()-1);
//		System.out.println(output);
		return output;
	}
	


	public  Map<String, Map<String, String>> generateParsingTable(CFG cfgHelper) {
	    Map<String, Map<String, String>> parsingTable = new LinkedHashMap<>();

	    List<String> variables = cfgHelper.getVariables();
	    List<String> terminalsWith$ = cfgHelper.getTerminals();
	    if (!terminalsWith$.contains("$")) {
	        terminalsWith$.add("$");
	    }

	    for (String variable : variables) {
	        Map<String, String> terminalMap = new HashMap<>();
	        for (String terminal : terminalsWith$) {
	            List<String> rules = cfgHelper.getVariableRuleFirstMap().get(variable).getOrDefault(terminal, null);
	            terminalMap.put(terminal, rules != null ? rules.toString() : null);
	        }
	        parsingTable.put(variable, terminalMap);
	    }
	    
	    for (String variable : variables) {
	        Map<String, String> terminalMap = parsingTable.getOrDefault(variable, new HashMap<>());
	        List<String> followSet = cfgHelper.getFollow().getOrDefault(variable, new ArrayList<>());
	        for (String terminal : followSet) {
	            List<String> firstSet = cfgHelper.getVariableRuleFirstMap().getOrDefault(variable, new HashMap<>()).get("e");
	            if (firstSet != null && firstSet.contains("e")) {
	                terminalMap.put(terminal, "e");
	            }
	        }
	        parsingTable.put(variable, terminalMap);
	    }

//	    System.out.println("Parsing Table: " + parsingTable);
	    return parsingTable;
	}
	
	
	
	public  List<String> generateDerivation(String input) {
	    Map<String, Map<String, String>> parsingTable = generateParsingTable(cfg);
//	    System.out.println(parsingTable);
	    List<String> derivation = new ArrayList<>();
	    Stack<String> pda = new Stack<>();
	    pda.push("$");
	    pda.push("S");
	    derivation.add("S");
	    int counter = 0;
	    String s = "";
	    boolean flag = true;
	    
	    for (int i = 0; i < input.length(); i = counter) {

	        if (Character.isUpperCase(pda.peek().charAt(0))) {

	            String variable = pda.pop();
	            String terminal = String.valueOf(input.charAt(i));
	            String rule = parsingTable.get(variable).get(terminal);
//	            System.out.println(rule);
	            
	            if (rule != null) {
	                if (!rule.equals("e")) {
                        for(int j = 0  ; j<derivation.get(derivation.size()-1).length() ; j++ ) {
                        	String chAR = String.valueOf(derivation.get(derivation.size()-1).charAt(j));
                        	if(variable.equals(chAR)) {
                                rule = rule.substring(1,rule.length()-1) ;
                        		s = derivation.get(derivation.size()-1).substring(0,j)  + rule + derivation.get(derivation.size()-1).substring(++j);
                        		derivation.add(s);
                        		
//                        		System.out.println(derivation + "AA");
                        		break;
                        	}
                        }
	                	
	                    for (int j = rule.length() - 1; j >= 0; j--) {
	                        pda.push(String.valueOf(rule.charAt(j)));
	                        
	                        
	                        if (pda.peek().equals("[") || pda.peek().equals("]")) {
	                            pda.pop();
	                        }
//	        	        	System.out.println(pda);
	                        
	                    }
	                }
	                else {
//	                	System.out.println(s.substring(1) + "HENA");
//	                	derivation.add(s.substring(2,s.length()-1));
	                	  for(int j = 0  ; j<derivation.get(derivation.size()-1).length() ; j++ ) {
	                        	String chAR = String.valueOf(derivation.get(derivation.size()-1).charAt(j));
	                        	if(variable.equals(chAR)) {
	                        		s = derivation.get(derivation.size()-1).substring(0,j)  + derivation.get(derivation.size()-1).substring(++j);
	                        		derivation.add(s);
	                        		
//	                        		System.out.println(derivation + "BB");

	                        		break;
	                        	}
	                        }
	                }
	                
	            } 
	            else {
	                derivation.add("ERROR");
	                return derivation;
	                
	            }
	        } 
	        else {
	            if (String.valueOf(input.charAt(i)).equals(pda.peek())) {
	                pda.pop();
	                counter++;
	                if(counter == input.length()) {
	                	for(int count = 0 ; !pda.peek().equals("$") ; count++) {
	                		if(Character.isLowerCase(pda.peek().charAt(0))) {
	                			derivation.add("ERROR");
	                			return derivation;
	                		}
	                		else {
	                			System.out.println(pda.peek()+"AAAAAAAA");
	                			if(!cfg.getFirst().get(pda.peek()).contains("e")) {
	                				derivation.add("ERROR");
	                				return derivation;
	                			}
	                			else {
//	                				pda.pop();
	                				break;
	                				
	                				
	                			}
	                		}
	                	}
	                }
	            } else {
	                derivation.add("ERROR");
	                return derivation;
	            }
	            
	            
	        }
	        
	 
	        System.out.println(pda);
	    }
	    
//	    Stack<String> pda2 = new Stack<>();
//	    
//	    while(!pda.isEmpty()) {
//	    	if(cfg.terminals.contains(pda.peek())) {
//	    		derivation.add("ERROR");
//	    		return derivation;
//	    	}
//	    	pda2.push(pda.pop());
//	    }
	       while(!pda.isEmpty()) {

	    	   
	        	if(cfg.variables.contains(pda.peek())&&cfg.getFirst().get(pda.peek()).contains("e")) {
	        		String t = pda.pop();
//	        		int i = derivation.get(derivation.size()-1).indexOf(pda.peek().charAt(0));
//	        		 s = derivation.get(derivation.size()-1).substring(0,)
	        		if(cfg.terminals.contains(pda.peek())&& !pda.peek().equals("$")) {
	        			continue;
	        		}
	        		String lastDerivationString = derivation.get(derivation.size() - 1);
	        		 String updatedString = lastDerivationString.replaceAll(t, "");
	        		 derivation.add(updatedString);
	        	}
	        	else {
	        		if(!pda.peek().equals("$"))
	        			derivation.add("ERROR");
	        		break;
	        	}
	        }
	    System.out.println(pda+"//////////////");
	    
//	    for (String b : cfg.getVariables()) {
//	    	if(!cfg.getRules().get(b).contains("e")) {
//	    		
//	    		break;
//	    	}
//	    	else if (derivation.get(derivation.size() - 1).contains(b) && !derivation.get(derivation.size() - 1).equals("ERROR")) {
//	        String lastDerivationString = derivation.get(derivation.size() - 1);
//	        String updatedString = lastDerivationString.replaceAll(b, "");
//	        derivation.add(updatedString);
//	    }
//	    }

//	    System.out.println(pda);
//	    System.out.println(derivation);
	    return derivation;
	}


	
    public  CFG constructCFG(String cfgString) {
        CFG cfg = new CFG();

        String[] parts = cfgString.split("#");
        String variablesString = parts[0];
        String terminalsString = parts[1];
        String rulesString = parts[2];
        String firstString = parts[3];
        String followString = parts[4];
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

        String[] firstArray = firstString.split(";");
        for (String first : firstArray) {
            String[] firstParts = first.split("/");
            String variable = firstParts[0];
            String[] productionsArray = firstParts[1].split(",");

            for (String production : productionsArray) {
                cfg.addFirst(variable, Arrays.asList(production));
            }
        }
        

        String[] followArray = followString.split(";");
        for (String follow : followArray) {
            String[] followParts = follow.split("/");
            String variable = followParts[0];
            String[] productionsArray = followParts[1].split(",");
            for (String production : productionsArray) {
                cfg.addFollow(variable, Arrays.asList(production.split("")));
            }
        }
        
        for (String variable : cfg.getVariables()) {
            Map<String, List<String>> ruleFirstMap = new LinkedHashMap<>();

            List<String> variableRules = cfg.getRules().get(variable);
            List<String> variableFirstSet = cfg.getFirst().get(variable);

            if (variableRules != null && variableFirstSet != null) {
                for (int i = 0; i < variableRules.size(); i++) {
                    String production = variableRules.get(i);
                    String[] firstSymbols = variableFirstSet.get(i).split(",");
                    for (String symbol : firstSymbols) {
                        char[] symbols = symbol.toCharArray();
                        for (char c : symbols) {
                            ruleFirstMap.computeIfAbsent(String.valueOf(c), k -> new ArrayList<>()).add(production);
                        }
                    }
                }
            }

            cfg.getVariableRuleFirstMap().put(variable, ruleFirstMap);
        }


        return cfg;
    }
	
	


}
