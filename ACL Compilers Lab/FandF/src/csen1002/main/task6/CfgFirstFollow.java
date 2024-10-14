package csen1002.main.task6;
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



/////////////////////////////////



public class CfgFirstFollow {
	public CFG cfg;

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
	
	 Map<String, ArrayList<String>> firstList = new LinkedHashMap<>();
	ArrayList<String> firstAdder = new ArrayList<>();
	 Map<String, ArrayList<String>> followList = new LinkedHashMap<>();
	ArrayList<String> followAdder = new ArrayList<>();

	
	public  String first() {
		
		Map<String, ArrayList<String>> firstList=calculateFirst();
	    List<String> variables = cfg.getVariables();
	    String result = "";
	    System.out.println(firstList);

		for(String variable : variables) {
			ArrayList<String> first = firstList.get(variable);
			Collections.sort(first);
			result += variable+"/";
			for(String firstItem : first) {
				result += firstItem;
			}
			result+=";";
			
			
		}
		result = result.substring(0,result.length()-1);
		System.out.println(result);
		return result;
		
	}
	public  Map<String, ArrayList<String>> calculateFirst() {
	    Map<String, List<String>> rules = cfg.getRules();
	    System.out.println(rules);
	    List<String> variables = cfg.getVariables();
	    Map<String, ArrayList<String>> firstList = new HashMap<>();

	    for (String variable : variables) {
	        firstList.put(variable, new ArrayList<>());
	    }

	    boolean firstFlag;
	    do {
        	System.out.println(firstList + "FIRST");

	    	firstFlag = false;

	        for (String variable : variables) {
	            List<String> variableRules = rules.get(variable);

	            for (String rule : variableRules) {
	                char firstSymbol = rule.charAt(0);
	                int counter = 0;

	                for(String ruleItem : rule.split("")) {
	                	char ruleChar = ruleItem.charAt(0);
	                
	                	if(Character.isUpperCase(ruleChar)) {
	                		counter++;
	                	}
	                	
	                }
	                if(counter == rule.length()) {
	                	int counter2 = 0;
	                	for(String ruleItem : rule.split("")) {
	                		if(firstList.get(ruleItem).contains("e")) {
	                			counter2++;
	                		}
	                		
	                	}
	                	if(counter2 == rule.length()) {
	                		if (!firstList.get(variable).contains("e")) {
	                		firstList.get(variable).add("e");
	                	
	                		firstFlag = true;
	                	}
	                		}
	                }
	                if (Character.isLowerCase(firstSymbol)) {
	                    if (!firstList.get(variable).contains(String.valueOf(firstSymbol))) {
	                        firstList.get(variable).add(String.valueOf(firstSymbol));
	                        firstFlag = true;
	                    }
	                }

	
	                
	                else if (Character.isUpperCase(firstSymbol)) {

	                    List<String> firstOfFirstSymbol =firstList.get(String.valueOf(firstSymbol));
	                    System.out.println(firstOfFirstSymbol + (firstSymbol+" ")+rule);
	                    List<String> firstOfVariable = firstList.get(variable);


                    	System.out.println(firstOfFirstSymbol + "DEEE" + firstSymbol);

	                    for (int j = 0;j< firstOfFirstSymbol.size();j++) {
	                    	String symbol = firstOfFirstSymbol.get(j);
	                    	System.out.println(symbol + " " + firstSymbol + "--->");
	                    	boolean sayed = true;
	            
	                    	
	                    		sayed =false;
	                        if (symbol.equals("e")) {
	                            // 
	                        	System.out.println(rule+"'''''''''''''''''''''''''");
	                            for (int i = 0; i < rule.length(); i++) {
	                                char nextSymbol = rule.charAt(i);
	                                
		                        	System.out.println("next "+nextSymbol+"'''''''''''''''''''''''''");
	                            	
		                        	System.out.println(firstOfFirstSymbol+"         "+firstOfVariable);

	                                if (Character.isLowerCase(nextSymbol) && nextSymbol != 'e') {
	                                	
	                                    if (!firstOfVariable.contains(String.valueOf(nextSymbol))) {
	                                    	System.out.println(nextSymbol + "NNN");
	                                        firstOfVariable.add(String.valueOf(nextSymbol));
	                                        firstFlag = true;
	                                        
	                                    }
	                            
	                                    	
	                                    	break;
	                                }
	                                else if(Character.isUpperCase(nextSymbol)) {
	                                	ArrayList<String> nextSymbolFirst = firstList.get(nextSymbol+"");
	                                	for(String abc : nextSymbolFirst) {
	                                        if (!firstOfVariable.contains(abc) && !abc.equals("e")) {
		                                    	System.out.println(nextSymbol + "NNN");
		                                        firstOfVariable.add(abc);
		                                        firstFlag = true;
		                                    }
	                                	}
	               
	                                	
	                                	
	                                	if(firstList.get(nextSymbol+"").contains("e")) {
	                                	
	                                	
	                                	}else {
	                                		break;
	                                		
	                                		
	                                	}
	                                	

	                                }
	                            
	                            }
	                        }
	                        
	                    	
	                        else {
	                            if (!firstOfVariable.contains(symbol)) {
                                	System.out.println(symbol + "AAA");

	                                firstOfVariable.add(symbol);
	                                firstFlag = true;
	                            }
	                        }
	                    	
	                    	
	                    }
	                    
	                }


	            }
	        }
	    } while (firstFlag);

	    System.out.println(firstList);
	    return firstList;
	}


	/**
	 * Calculates the Follow Set of each variable in the CFG.
	 * 
	 * @return A string representation of the Follow of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	
	
	public  void calculateFollow(Map<String, ArrayList<String>> followList,String variable,String rest,ArrayList<String> followOfVariable2,String variable2) {
		   Map<String, List<String>> rules = cfg.getRules();
		    System.out.println(rules);
		    List<String> variables = cfg.getVariables();
		    Map<String, ArrayList<String>> firstList=calculateFirst();
		    System.out.println(variable2 +"???????????????????????????????????????????");

		
		for(String character : rest.split("")) {
			if(variables.contains(character)) {
				ArrayList<String> firstOfCharacters = firstList.get(character+"");
				for(String followAdder : firstOfCharacters) {
					if(!followAdder.equals("e") && !followList.get(variable).contains(followAdder)) {
						followList.get(variable).add(followAdder);
						f3 = true;
					}
				}
				if(!firstOfCharacters.contains("e")) {
					return;
				}
				
				
			}else{
				if(!followList.get(variable).contains(character)) {
					followList.get(variable).add(character);
					f3 = true;
					return;
				}
			}
		}

///////////
	

//////////
		
		
		
	}
	 boolean f3 = true;

	public  String follow() {
		Map<String, ArrayList<String>> firstList=calculateFirst();

		
		   Map<String, List<String>> rules = cfg.getRules();
		    System.out.println(rules);
		    List<String> variables = cfg.getVariables();
		    Map<String, ArrayList<String>> followList = new HashMap<>();

		    // Initialize firstList with empty lists for each variable
		    for (String variable : variables) {
		        followList.put(variable, new ArrayList<>());
		    }

		    followList.get(variables.get(0)).add("$");
		    System.out.println(followList);
		    while(f3) {
		    	f3 = false;
		    	for(String variable : variables) {
		    		for(String variable2 : variables) {
		    			List<String> variableRules = rules.get(variable2);
		    			
		    			for (String rule : variableRules) {
		    				if(rule.contains(variable)) {
		    					
		    					
		    					for(int i = 0; i<rule.length()-1;i++) {
		    						char character = rule.charAt(i);
		    						char nextCharacter = rule.charAt(i+1);
		    						
		    						if((character+"").equals(variable)) {
		    							if(Character.isLowerCase(nextCharacter)) {
		    								if(!followList.get(variable).contains(nextCharacter+"")) {
		    									followList.get(variable).add(nextCharacter+"");
		    									f3 = true;
		    									
		    								}
		    							}
		    							else if(Character.isUpperCase(nextCharacter)) {
		    								ArrayList<String> firstOfCharacters = firstList.get(nextCharacter+"");
		    								for(String followAdder : firstOfCharacters) {
		    									if(!followAdder.equals("e") && !followList.get(variable).contains(followAdder)) {
		    										followList.get(variable).add(followAdder);
		    										f3 = true;
		    									}
		    								}
		    								if(firstList.get(nextCharacter+"").contains("e")) {
		    									ArrayList<String> followOfVariable2 = followList.get(variable2+"");
		    								    System.out.println(variable2);
		    								    if(i+1 == rule.length()-1) {
		    										for(String followAdder : followOfVariable2) {
		    											if(!followAdder.equals("e") && !followList.get(variable).contains(followAdder)) {
		    												System.out.println("----------------------------------------------------------------");
		    												followList.get(variable).add(followAdder);
		    												f3 = true;
		    											}
		    										}
		    								    }else {
		    								    	
		    								    	calculateFollow(followList,variable,rule.substring(i),followOfVariable2,variable2);
		    								    }

		    								}
		    							}
		    						}
		    					}	            			
		    					
		    					if((rule.charAt(rule.length()-1)+"").equals(variable)) {
		    						ArrayList<String> followOfVariable2 = followList.get(variable2+"");
		    						
		    						for(String followAdder : followOfVariable2) {
		    							if(!followAdder.equals("e") && !followList.get(variable).contains(followAdder)) {
		    								followList.get(variable).add(followAdder);
		    								f3 = true;
		    							}
		    						}
		    					}
		    				}
		    				
		    			}
		    		}
		    	}
		    }
		    String result = "";
		    
		    
			for(String variable : variables) {
				ArrayList<String> first = followList.get(variable);
				Collections.sort(first);
				result += variable+"/";
				for(String firstItem : first) {
					result += firstItem;
				}
				result+=";";
				
				
			}
			result = result.substring(0,result.length()-1);
			System.out.println(result);
		    System.out.println(followList);

			return result;
	}
	
	
	
	public  CFG constructCFG(String cfgString) {
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
	   public static void main(String[] args) {
	        //CFG cfg = new CFG();

	        String input = "S;V;Q;E;J;T;A#a;c;l;n;q;s;x#S/lSnV,QQSSE,Vn,SSs,Q,AAl;V/Jl,QAcE,SqSsS,e,V;Q/c,VSnE;E/x,VQ,V,aEcJs,E,S,Q;J/TJ,qTlQ,e;T/lVQ,q,lVEQV,x,e;A/a,T";
//	        S;Z;R;D;E;U;O#i;l;m;p;q;u;x#S/x,E;Z/EOl,RRO,U;R/SxDx,ZSxUp,U,Ux,Z,S;D/iZpZ,UxO,R,e;E/ZZOq,Ox;U/uOE,pEZmO,e,U;O/U,p,m,ORE
//	        S;H;V;I;G#c;l;s;y#S/VH,sHI;H/cSSGI,HIVG;V/Vy,c,sHI,sIG,SH,SySy;I/lSSI,IV,s,IVI;G/IH,Hy,GHVy,yG,GI
//	        S;T;L#a;b;c;d;i#S/ScT,T;T/aSb,iaLb,e;L/SdL,S
//	        S;Y;X;C;A;B;L#a;l;m;o;p;r;t#S/aYX,l;Y/CY,BlLY,SlC,C;X/rCtAp,CmX,ASXp;C/ACA,A,e,S,X;A/SYm,XSA,e,A;B/BCSXS,CXYoA,m,Yt;L/BC,Y,AX,LBC,oSX
			CfgFirstFollow cfg1 = new CfgFirstFollow(input);
			//constructCFG(input);
			//first();
			
	    }

}
