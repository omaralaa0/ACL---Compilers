package csen1002.main.task4;

import java.util.*;



/**
 * Write your info here
 * 
 * @name Omar Alaa
 * @id 49-9719
 * @labNumber t-19
 */



class CFG {
    String[] variables;
    String[] terminals;
    String[][] rules;
    String[]rulesArray;
    String variableRule;
    String ruleChar;
    
    public CFG clone() {
        CFG clonedCfg = new CFG();
        clonedCfg.variables = this.variables.clone();
        clonedCfg.terminals = this.terminals.clone();
        clonedCfg.rules = new String[this.rules.length][];
        for (int i = 0; i < this.rules.length; i++) {
            clonedCfg.rules[i] = this.rules[i].clone();
        }
        return clonedCfg;
    }

}












public class CfgEpsUnitElim {
	static String[] rulesArray=null;

    
	public static CFG CfgReturn(String cfg) {
	    String[] parts = cfg.split("#");

	    String[] variables1 = parts[0].split(";");
	    String[] terminals1 = parts[1].split(";");
	    String[] rules1 = parts[2].split(";");
	    String[][] rul = new String[rules1.length][];
	    HashSet<String> printedVariables = new HashSet<>();

	    
	    
	    for (int i = 0; i < rules1.length; i++) {
	        String[] ruleParts = rules1[i].split("/");
	        String variable = ruleParts[0];
	        rulesArray = ruleParts[1].split(",");
	        rul[i] = rulesArray;
	        

	            for (int j = 0; j < rulesArray.length; j++) {
	            }
	            printedVariables.add(variable);
	        
	    }

	    CFG cfg1 = new CFG();
	    cfg1.variables = variables1;
	    cfg1.terminals = terminals1;
	    cfg1.rules = rul;
	    cfg1.rulesArray = rulesArray;

	    return cfg1;
	}



	static CFG go;
	String cfg;
	public CfgEpsUnitElim(String cfg) {
		this.cfg = cfg;
	}

	@Override
	public String toString() {


		
		return cfg;
	}

	
	public static CFG substituteWithEpsilon(CFG cfg) {
	    boolean hasEpsilon = false;
	    int epsilonVariableIndex;
	    for (int i = 0; i < cfg.rules.length; i++) {
	        for (String rule : cfg.rules[i]) {
	            if (rule.equals("e")) {
	                epsilonVariableIndex = i;
	                String variableContEpsilon = cfg.variables[i];
	                for (int j = 0; j < cfg.rules.length; j++) {
	                    for (int k = 0; k < cfg.rules[j].length; k++) {
	                        if (cfg.rules[j][k].equals(variableContEpsilon)) {
	                            boolean containsEpsilon = false;
	                            for (String term : cfg.rules[j]) {
	                                if (term.equals("e")) {
	                                    containsEpsilon = true;
	                                    break;
	                                }
	                            }
	                            if (!containsEpsilon) {	                                
	                            	String[] updatedRule = new String[cfg.rules[j].length + 1];
	                                System.arraycopy(cfg.rules[j], 0, updatedRule, 0, cfg.rules[j].length);
	                                updatedRule[cfg.rules[j].length] = "e";
	                                cfg.rules[j] = updatedRule;

	                                hasEpsilon = true;
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	    for (int i = 0; i < cfg.rules.length; i++) {
	    }
	    return cfg;
	}


	
	public static String[] generateStringsWithEpsilon(String input, String variableWithEpsilon) {
	    Set<String> resultsSet = new HashSet<>();
	    if (!input.contains(variableWithEpsilon)) {
	        resultsSet.add(input);
	        return resultsSet.toArray(new String[0]);
	    }

	    int index = input.indexOf(variableWithEpsilon);
	    String leftPart = input.substring(0, index);
	    String rightPart = input.substring(index + variableWithEpsilon.length());

	    resultsSet.add(input);
	    String[] subResults = generateStringsWithEpsilon(rightPart, variableWithEpsilon);
	    
	    for (String subResult : subResults) {
	        resultsSet.add(leftPart + subResult);
	        resultsSet.add(leftPart + variableWithEpsilon + subResult);


	    }
	    
	    return resultsSet.toArray(new String[0]);
	}



	
	
	
	public static String[] concatenateArray(String[] arr1, String[] arr2) {
	    String[] newArr = Arrays.copyOf(arr1, arr1.length + arr2.length);
	    return newArr;
	}
	
    public static void sortCFGRules(String[][] cfgRules) {
        for (String[] rule : cfgRules) {
            Arrays.sort(rule);
        }
    }
	
	public void eliminateEpsilonRules() {
		boolean checker = false;
		String[] innerArray;
		ArrayList<String> arrayList = new ArrayList<String>();

		CFG cfg1 = CfgReturn(cfg);
		CFG cfgEpsilonHelper = substituteWithEpsilon(cfg1);
		ArrayList<String> epsilonVariableList = new ArrayList<String>();
		while (!checker) {
			checker = true;
			for (int i = 0; i < cfgEpsilonHelper.rules.length; i++) {
				for (String rule : cfgEpsilonHelper.rules[i]) {
					if (rule.contains("e") && !epsilonVariableList.contains(cfgEpsilonHelper.variables[i])) {
						checker = false;
						String variableContEpsilon = cfgEpsilonHelper.variables[i];
						epsilonVariableList.add(variableContEpsilon);

						for (int j = 0; j < cfgEpsilonHelper.rules.length; j++) {
							String[] subArray = cfgEpsilonHelper.rules[j];
							String[] newArray = null;
							for (int k = 0; k < subArray.length; k++) {
								String element = subArray[k];
								for (String adder : cfgEpsilonHelper.rules[j]) {
									if (!arrayList.contains(adder))
										arrayList.add(adder);

								}

								if (element.contains(variableContEpsilon)) {

									String[] results = generateStringsWithEpsilon(element, variableContEpsilon);

									for (String adder1 : results) {
										if (!adder1.isEmpty() && !arrayList.contains(adder1)
												&& (!epsilonVariableList.contains(cfgEpsilonHelper.variables[j])
														|| !adder1.contains("e"))) {
											arrayList.add(adder1);
										}
									}
								}

							}
							newArray = arrayList.toArray(new String[0]);
							cfgEpsilonHelper.rules[j] = newArray.clone();
							arrayList.clear();

						}
					}
				}
			}
			 cfgEpsilonHelper = substituteWithEpsilon(cfgEpsilonHelper);

		}
		for (int i = 0; i < cfgEpsilonHelper.rules.length; i++) {
		    for (int j = 0; j < cfgEpsilonHelper.rules[i].length; j++) {
		        String[] innerArray1 = cfgEpsilonHelper.rules[i];
		        List<String> filteredList = new ArrayList<>();
		        for (String element : innerArray1) {
		            if (!element.contains("e")) {
		            	
		                filteredList.add(element);
		            }
		        }
		        Collections.sort(filteredList);
		        cfgEpsilonHelper.rules[i] = filteredList.toArray(new String[0]);
		    }
		}
		
		
		
		
		for (int i = 0; i < cfgEpsilonHelper.rules.length; i++) {
		    for (int j = 0; j < cfgEpsilonHelper.rules[i].length; j++) {
		    }

		}

		
		cfg = naming(cfgEpsilonHelper);
	    
		

	}
	public String naming(CFG c) {
	    String[] parts = cfg.split("#");

	    String r = "";

	    
	    r+= parts[0] + "#" + parts[1]+"#";
	    
	    for(int i = 0; i <c.rules.length; i++) {
	    	r+=c.variables[i]+"/";
	    	for (String a : c.rules[i]) {
	    		r+= a +",";
				
			}
	    	r = r.substring(0,r.length()-1) + ";";
	    	
	    }
	    return r.substring(0,r.length()-1);
	    
	    
	}

    
	public void eliminateUnitRules() {
	    CFG cfg1 = CfgReturn(cfg);
	    CFG cfgUnitHelper = cfg1.clone();
	    boolean checker = false;
	    HashSet<String> removedUnit = new HashSet<String>();

		while (!checker) {
			checker = true;
			
	        for (int i = 0; i < cfgUnitHelper.rules.length; i++) {
	            for (String rule : cfgUnitHelper.rules[i]) {	
	                if (rule.length() == 1 &&  Character.isUpperCase(rule.charAt(0))) {
	                	for (int j = 0; j < cfgUnitHelper.variables.length; j++) {
	                	if (cfgUnitHelper.variables[j].equals(rule)) {
//	                		String left = cfgUnitHelper.variables[i];
//	                		String right = rule.charAt(0) + "";
//	                		removedUnit.add(left+right);
	                	    for(int k = 0; k<cfgUnitHelper.rules[i].length;k++) {
	                		    if(cfgUnitHelper.rules[i][k].equals(rule)) {
	                			        List<String> filteredList = new ArrayList<>();
	                	
	                			        for (String element : cfgUnitHelper.rules[i]) {
	                			        	
	                			        	if (!filteredList.contains(element)) {
	                			                filteredList.add(element);
	                			        	}
	                			        	for (String item : cfgUnitHelper.rules[j]) {
	                			        	if (!filteredList.contains(item)) {
	                			                filteredList.add(item);


	                			        	}
	                			        	
	                			        	}
	                			        	
	                			        	
	                	
	                			        }
	                	
	                	

	                			        
	                			    Collections.sort(filteredList);

	                			        cfgUnitHelper.rules[i] = filteredList.toArray(new String[0]);
	                	
	                		    }
	                	    
	                		    
	                	    
	                		}

	                	
	                	}
	                	
	                	}

	                }

	            }
	            
	        }
	        
		}
	    
	        
	        for(int i = 0; i<cfgUnitHelper.rules.length; i++) {
	        	ArrayList<String> finalOut = new ArrayList<>();
	        	for(String element : cfgUnitHelper.rules[i]) {
	        		if(element.length() == 1 && Character.isUpperCase(element.charAt(0)))
	        				continue;
	        		finalOut.add(element);
	        		
	        	}
	        	cfgUnitHelper.rules[i] = finalOut.toArray(new String[0]);
	        }



	    for (String[] rule : cfgUnitHelper.rules) {
	    }

	    cfg = naming(cfgUnitHelper);
	}


	
	public static void main(String[] args) {
		String input = "S;T;L#a;b;c;d;i#S/ScTi,La,Ti,b;T/aSb,LabS,i;L/SdL,Si";
		

		CfgEpsUnitElim cfg = new CfgEpsUnitElim(input);
		cfg.eliminateEpsilonRules();
		cfg.eliminateUnitRules();
		//S;G;P;E;I#l;v;z#S/GlIlP,IPl,Sz,lIz,lPG,vGGl;G/GE,SEvP,lEE,zES;P/PG,zE;E/GlIlP,Sz,vGGl;I/GGv,vPlS,vSv,zIzG
		//S;G;P;E;I#l;v;z#S/GlIlP,IPl,PG,Sz,lIz,lPG,vGGl,zE;G/GE,SEvP,lEE,zES;P/PG,zE;E/GlIlP,Sz,vGGl;I/GGv,vPlS,vSv,zIzG

		
		
	}

}

