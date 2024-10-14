package csen1002.main.task5;
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
        rules = new HashMap<>();
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




public class CfgLeftRecElim {


	String cfg;
	public CfgLeftRecElim(String cfg) {
		this.cfg = cfg;
	}

	
	@Override
	public String toString() {
		return cfg;
	}

	
	public String naming(CFG c) {
	    String[] parts = cfg.split("#");
	    String r = "";

	    List<String> variables = c.variables;
	    Map<String, List<String>> rules = c.rules;
	    
	    StringBuilder variableNames = new StringBuilder();
	    for (String v : variables) {
	        variableNames.append(v).append(";");
	    }
	    variableNames.deleteCharAt(variableNames.length() - 1); // Remove the last semicolon
	    parts[0] = variableNames.toString();
	    System.out.println(parts[0] + "DAS");

	    r += parts[0] + "#" + parts[1] + "#";

	    for (String variable : variables) {
	        r += variable + "/";
	        List<String> variableRules = rules.get(variable);
	        if (variableRules != null) {
	            for (String rule : variableRules) {
	                r += rule + ",";
	            }
	        }
	        r = r.substring(0, r.length() - 1) + ";";
	    }
	    System.out.println(r.substring(0, r.length() - 1));
	    return r.substring(0, r.length() - 1);
	}

	
	
	
	public static CFG CfgReturn(String cfg) {
        CFG cfg1 = new CFG();

        String[] parts = cfg.split("#");

        String[] variables1 = parts[0].split(";");
        String[] terminals1 = parts[1].split(";");
        String[] rules1 = parts[2].split(";");

        HashSet<String> printedVariables = new HashSet<>();

        for (String variable : variables1) {
            cfg1.variables.add(variable);
        }
        for (String terminal : terminals1) {
            cfg1.terminals.add(terminal);
        }

        for (String rule : rules1) {
            String[] ruleParts = rule.split("/");
            String variable = ruleParts[0];
            List<String> ruleList = new ArrayList<>();
            String[] symbols = ruleParts[1].split(",");
            for (String symbol : symbols) {
                ruleList.add(symbol);
            }
            if (!cfg1.rules.containsKey(variable)) {
                cfg1.rules.put(variable, new ArrayList<>());
            }
            cfg1.rules.get(variable).addAll(ruleList);
        }

        return cfg1;
    }
	



	
	
	
	
	public String CfgGetter(CFG cfg1) {
		//CFG cfg1 = CfgReturn(cfg);

        StringBuilder result = new StringBuilder( "Variables: ");
       
        result.append(cfg1.variables).append("\n");
        result.append("Terminals: ").append(cfg1.terminals).append("\n");
        result.append("Rules: {");
//        result.append(variables);
//        result.append(terminals);
//        result.append(rules);

        for (String variable : cfg1.variables) {
            result.append(variable).append("=[");
            List<String> variableRules = cfg1.rules.get(variable);
            if (variableRules != null) {
                for (String rule : variableRules) {
                    result.append(rule).append(",");
                }
                // Remove the trailing comma
                result.deleteCharAt(result.length() - 1);
            }
            result.append("], ");
        }

        // Remove the trailing comma and space
        result.delete(result.length() - 2, result.length());

        result.append("}");

        return result.toString();
    }

	
    public void addRule(CFG cfg1, String variable, List<String> rule) {
	    //CFG cfg1 = CfgReturn(cfg);
	    String cfgLR = CfgGetter(cfg1);
        if (!cfg1.rules.containsKey(variable)) {
        	cfg1.rules.put(variable, new ArrayList<>());
        }
        cfg1.rules.get(variable).addAll(rule);
    }
	
    
    public void removeRule(CFG cfg1, String variable, String rule) {
        String cfgLR = CfgGetter(cfg1);
        if (cfg1.rules.containsKey(variable)) {
            List<String> rules = cfg1.rules.get(variable);
            rules.remove(rule);
        }
    }

	
	


	
	public Map<String, List<String>> sortRules(CFG cfg1) {
	    Map<String, List<String>> sortedRules = new LinkedHashMap<>();
	    
	    for (String variable : cfg1.variables) {
	        if (cfg1.rules.containsKey(variable)) {
	            sortedRules.put(variable, cfg1.rules.get(variable));
	        }
	    }
	    
	    return sortedRules;
	}

	public String sortOutput(CFG cfg) {
	    StringBuilder sortedOutput = new StringBuilder();

	    // Iterate over the input variables and build the output in the same order
	    for (String variable : cfg.variables) {
	        sortedOutput.append(variable).append(";");
	    }
	    sortedOutput.append("#");

	    // Add rules for each variable, maintaining the order as per the input
	    for (String variable : cfg.variables) {
	        List<String> variableRules = cfg.rules.get(variable);
	        sortedOutput.append(variable).append("/");
	        if (variableRules != null) {
	            for (String rule : variableRules) {
	                sortedOutput.append(rule).append(",");
	            }
	            // If there are additional elements like 'e', add them at the end
	            if (variableRules.contains("e")) {
	                variableRules.remove("e");
	                sortedOutput.append("e,");
	            }
	            sortedOutput.setLength(sortedOutput.length() - 1); // Remove the trailing comma
	            sortedOutput.append(";");
	        }
	    }

	    return sortedOutput.toString();
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
    List<String> varAdd = new ArrayList<>();

    Map<String, List<String>> dashedRules = new HashMap<>();    
	public  Map<String, List<String>> eliminateImmediateLeftRecursion(CFG cfg1,List<String> variableRules){
//	    CFG cfg1 = CfgReturn(cfg);
	    String cfgLR = CfgGetter(cfg1);
	    //List<String>
	    boolean c=false;
	    //System.out.println(variableRules+"vaRIABLE RULES");
	    List<String> newRules = new ArrayList<>(variableRules);
	    newRules.clear();
	    //System.out.println(variableRules + "vvvvv");
	    String newVariable;
	    List<String> newVariablesToAdd = new ArrayList<>();
	    List<String> rulesToRemove = new ArrayList<>();

	    for (String variable : cfg1.variables) {
		    //List<String> ruleCheck = cfg1.rules.get(variable);

	        variableRules = cfg1.rules.get(variable);
	        List<String> rulesCopy = new ArrayList<>(variableRules);
        	//System.out.println(rulesCopy + "HENAHO");
	        for (String rule : rulesCopy) {

	            if (rule.startsWith(variable)) {
	            	
	                c = true;
	                newVariable = variable + "'";
	                newRules.add(rule.substring(1) + newVariable);
	                addRule(cfg1, newVariable, newRules);
	                newRules.clear();
	                rulesToRemove.add(rule);

	                if (!cfg1.variables.contains(newVariable) && !newVariablesToAdd.contains(newVariable)) {
	                    newVariablesToAdd.add(newVariable);
	                }
	                if (c) {
	                    List<String> newRulesToAdd = new ArrayList<>();
	                    HashSet<String> uniqueRules = new HashSet<>();

	                    for (String r : variableRules) {
	                        if (!r.startsWith(variable) && !r.contains(variable + "'")) {
	                            String newRule = r + variable + "'";
	                            if (uniqueRules.add(newRule)) {
	                                newRulesToAdd.add(newRule);
	                                rulesToRemove.add(r);
	                            }
	                        }
	                    }

	                    addRule(cfg1, variable, newRulesToAdd);
	                    
	                    
	                }

	            } 
	            
	        }


	        for (String ruleToRemove : rulesToRemove) {
	            if (variableRules.contains(ruleToRemove)) {
	                removeRule(cfg1, variable, ruleToRemove);
	                
	            }
	        }
	        rulesToRemove.clear();


    }
	    //cfg1.variables.addAll(newVariablesToAdd);
	    varAdd.addAll(newVariablesToAdd);
	    for (String variable1 : cfg1.variables) {
	    	
	        List<String>varRules = cfg1.rules.get(variable1);
	        if(variable1.contains("'") && !varRules.contains("e")) {
	        	List<String> epsilon = new ArrayList<>();
	        	epsilon.add("e");
	        	addRule(cfg1, variable1, epsilon);
	        }

	    }


	    Map<String, List<String>> sortedRules = sortRules(cfg1);

	    cfg1.rules = sortedRules;
	    for (String variable : cfg1.variables) {
	        List<String> variableRules1 = cfg1.rules.get(variable);
	        
	        Set<String> uniqueRules = new LinkedHashSet<>(variableRules1);
	        
	        variableRules1.clear();
	        variableRules1.addAll(uniqueRules);
	    }

	    
	    
	    
	    
	    
  
    	
    	Map<String, List<String>> ouput = cfg1.rules;
    	
    	//cfg1.variables = varAdd;
	    return ouput;
	}

public Map<String, List<String>> eliminateProductions(List<String> variableRules) {
	CFG cfg1 = CfgReturn(cfg);
	String r;
	//System.out.println(cfg1.rules);
	List<String> d;
	Map<String, List<String>> output;
    for (String variable : cfg1.variables) {
        variableRules = cfg1.rules.get(variable);

        
        cfg1.rules = eliminateImmediateLeftRecursion(cfg1,variableRules);

        //System.out.println(cfg1.rules);



        
    }
    
    List<String> variablesToAdd = new ArrayList<>();
    for (String var : cfg1.rules.keySet()) {
        if (!cfg1.variables.contains(var)) {
            variablesToAdd.add(var);
        }
    }
    cfg1.variables.addAll(variablesToAdd);

    ////////////////////////////////////////
   /////////////////////////////////////////
    List<String> visitedVariablesList = new ArrayList<>();
    Map<String, List<String>> rulesToAdd = new HashMap<>();
    List<String> rulesToRemove = new ArrayList<>();
    boolean flag = false;
    while(!flag) {
    	flag = true;
    	for (String methodVariable : cfg1.variables) {
    		List<String> methodRules = cfg1.rules.get(methodVariable);
    		
    		if (!visitedVariablesList.contains(methodVariable)) {
    			visitedVariablesList.add(methodVariable);
    		}
    		
    		for (String rule : methodRules) {

    			if (visitedVariablesList.stream().anyMatch(rule::startsWith)) {

    				String startingVariable = visitedVariablesList.stream().filter(rule::startsWith).findFirst().orElse(null);
    				
    				String newRule = rule.substring(1);
    				List<String> varRules = cfg1.rules.get(startingVariable);
    				
    				for (String ruleAdder : varRules) {
    					String finalRule = ruleAdder + newRule;
    					rulesToRemove.add(rule);
    					rulesToAdd.computeIfAbsent(methodVariable, k -> new ArrayList<>()).add(finalRule);
    				}
    				
    				
    			}
    			
    		}

    		for (String ruleToRemove : rulesToRemove) {
    			
    			if (methodRules.contains(ruleToRemove)) {
    				flag = false;
    				removeRule(cfg1, methodVariable, ruleToRemove);
    				
    			}
    		}
    		
 
    		
    	}
    }

    
    for (Map.Entry<String, List<String>> entry : rulesToAdd.entrySet()) {
        String variable = entry.getKey();
        List<String> rulesList = entry.getValue();
        addRule(cfg1, variable, rulesList);
    }
    
    
    output = cfg1.rules;
    System.out.println("Initial output value:   " + output);
    for (String varia : cfg1.variables) {
        List<String> variaRules = cfg1.rules.get(varia);
        for(String v : variaRules) {
        	//System.out.println(v + ">>>" + varia);
        }
        //System.out.println("rules for variable:   " + varia + "->   " + variaRules);
        //System.out.println("Final output value:   " + output);
        output = eliminateImmediateLeftRecursion(cfg1,variaRules);
        System.out.println("Final output value:   " + output);

    }
    
    return output;
}

	
	public void eliminateLeftRecursion() {
		CFG cfg1 = CfgReturn(cfg);

		for (Map.Entry<String, List<String>> entry : cfg1.rules.entrySet()) {
		    String variable = entry.getKey(); // Get the variable name
		    List<String> rules = entry.getValue(); // Get the list of rules for the variable

		    // Apply eliminateProductions function to the list of rules
		    eliminateProductions(rules);
		}
		
		for(String variable : varAdd) {
			if(!cfg1.variables.contains(variable))
				cfg1.variables.add(variable);
		}


        //System.out.println(cfg1.rules+"check");
		


		
        System.out.println(cfg1.rules);

        System.out.println(cfg1.variables);

		
	}

	
	

	   public static void main(String[] args) {
	        CFG cfg = new CFG();

	        String input = "S;T;L#a;b;c;d;i#S/ScTi,La,Ti,b;T/aSb,LabS,i;L/SdL,Si";
//	        S;T;L;S';L'#a;b;c;d;i#
//	        S/LaS',TiS',bS';
//	        T/aSb,LabS,i;
//	        L/aSbiS'dLL',iiS'dLL',bS'dLL',aSbiS'iL',iiS'iL',bS'iL';
//	        S'/cTiS',e;
//	        L'/aS'dLL',abSiS'dLL',aS'iL',abSiS'iL',e
			CfgLeftRecElim cfg1 = new CfgLeftRecElim(input);

	        cfg1.eliminateLeftRecursion();
	    }

}




