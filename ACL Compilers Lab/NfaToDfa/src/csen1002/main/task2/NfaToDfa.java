

package csen1002.main.task2;
/**
 * Write your info here
 * 
 * @name Omar Alaa
 * @id 49-9719
 * @labNumber t-19
 */
import java.util.*;



class NFA{
    String[] alphabet;
     ArrayList<Integer> finalState;
     int initialState;
    int[]states;
    String[] nfaTransitions;

	 public NFA() {
	        
	 }

	 
	 
	 
}

public class NfaToDfa {
 String output, nfa;

	public NfaToDfa(String nfa) {
	    ArrayList<Integer>[][] transitions;
	    ArrayList<Character> alphabet;
	    int initialState;
	    this.nfa = nfa;
	    ArrayList<Integer> finalState = new ArrayList<Integer>();
	    String[] parts = nfa.split("#");
	    String[] states = parts[0].split(";");
	    int[] states1 = new int[states.length];
	    for (int i = 0; i < states.length; i++) {
	        states1[i] = Integer.parseInt(states[i]);
	    }
	    String[] alpha = parts[1].split(";");
	    String[] transitionsString = parts[2].split(";");
	    initialState = Integer.parseInt(parts[3]);
	    String [] accState = parts[4].split(";");
	    for(int i=0; i<accState.length;i++) {
	    	finalState.add(Integer.parseInt(accState[i]));
	    }
	    
	    
	    ArrayList<String> transitionSet = new ArrayList<>(Arrays.asList(transitionsString));
	    
	    NFA nfa1 = new NFA();
	    nfa1.states = states1;
	    nfa1.alphabet = alpha;
	    nfa1.nfaTransitions = transitionSet.toArray(new String[0]);
	    nfa1.initialState = initialState;
	    nfa1.finalState = finalState;
	}
	
	public static NFA NfaReturn(String nfa){
	    ArrayList<Integer>[][] transitions;
	    ArrayList<Character> alphabet;
	    int initialState;
	    ArrayList<Integer> finalState = new ArrayList<Integer>();
	    String[] parts = nfa.split("#");
	    String[] states = parts[0].split(";");
	    int[] states1 = new int[states.length];
	    for (int i = 0; i < states.length; i++) {
	        states1[i] = Integer.parseInt(states[i]);
	    }
	    String[] alpha = parts[1].split(";");
	    String[] transitionsString = parts[2].split(";");
	    initialState = Integer.parseInt(parts[3]);
	    String [] accState = parts[4].split(";");
	    for(int i=0; i<accState.length;i++) {
	    	finalState.add(Integer.parseInt(accState[i]));
	    }
	    
	    ArrayList<String> transitionSet = new ArrayList<>(Arrays.asList(transitionsString));
	    NFA nfa1 = new NFA();
	    nfa1.states = states1;
	    nfa1.alphabet = alpha;
	    nfa1.nfaTransitions = transitionSet.toArray(new String[0]);
	    nfa1.initialState = initialState;
	    nfa1.finalState = finalState;
	    return nfa1;
	}

	


    public static ArrayList<ArrayList<Integer>> epsilonClosure(NFA nfa1) {
        ArrayList<ArrayList<Integer>> epsilonClosureList = new ArrayList<>();
        Map<Integer, Set<Integer>> epsilonTransitionsMap = new HashMap<>();
        for (String transition : nfa1.nfaTransitions) {
            String[] parts = transition.split(",");
            int fromState = Integer.parseInt(parts[0]);
            char symbol = parts[1].charAt(0);
            int toState = Integer.parseInt(parts[2]);

            if (symbol == 'e') {
                epsilonTransitionsMap.putIfAbsent(fromState, new HashSet<>());
                epsilonTransitionsMap.get(fromState).add(toState);
            }
        }

        for (int state : nfa1.states) {
            ArrayList<Integer> closure = new ArrayList<>();
            calculateEpsilonClosure(state, epsilonTransitionsMap, closure);
            epsilonClosureList.add(closure);
        }

        return epsilonClosureList;
    }

    private static void calculateEpsilonClosure(int currentState, Map<Integer, Set<Integer>> epsilonTransitionsMap, ArrayList<Integer> closure) {
        closure.add(currentState);

        Set<Integer> epsilonTransitions = epsilonTransitionsMap.get(currentState);
        if (epsilonTransitions != null) {
            for (int nextState : epsilonTransitions) {
                if (!closure.contains(nextState)) {
                    calculateEpsilonClosure(nextState, epsilonTransitionsMap, closure);
                }
            }
        }
    }
    //NEWLYADDED
    private static int compareLists(ArrayList<Integer> a, ArrayList<Integer> b) {
        int minSize = Math.min(a.size(), b.size());
        for (int i = 0; i < minSize; i++) {
            int cmp = Integer.compare(a.get(i), b.get(i));
            if (cmp != 0) {
                return cmp;
            }
        }
        return Integer.compare(a.size(), b.size());
    }

    private static String listToString(ArrayList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            sb.append("/");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }


    public  String convertToDFA(NFA nfa, ArrayList<ArrayList<Integer>> epsilonClosureList) {
        //output = "";
        int initialState = nfa.initialState;
        ArrayList<Integer> acceptState = nfa.finalState;
        String [] alphabet = nfa.alphabet;
        ArrayList<Integer> initialStateClosure = new ArrayList<Integer>(epsilonClosureList.get(initialState));
        
        DFA dfa = new DFA(initialStateClosure, alphabet);


        dfa.setStartState(initialStateClosure);
        System.out.println(dfa.acceptStates);
        boolean f = true;
        int c = 0;
       
        while(f) {
        	f = false;
        	ArrayList<Integer> state = dfa.states.get(c);
        	Collections.sort(state);
        	//System.out.println(state);

        	for(String a:nfa.alphabet) {
        		ArrayList<Integer> x = new ArrayList<Integer>(); 
        		for(Integer i: state) {
	        		
	        		for(int j = 0; j<nfa.nfaTransitions.length;j++) {
	            		String[] t = nfa.nfaTransitions[j].split(",");
	            		
	            		if(Integer.parseInt(t[0]) == i && t[1].equals(a)) {
	            			ArrayList<String> transition = new ArrayList<String>();


	            			for(Integer n : epsilonClosureList.get(Integer.parseInt(t[2]))) {
	            				if(!x.contains(n))
	            					x.add(n);
	            				Collections.sort(x);
	            				listToString(x);
	            				
	            				
	            				
	            			}
	            			
	            			
	            			
	            		}
	            		
	            	}

	        	
        	}
          		if(x.isEmpty()) {
        			x.add(-1);
        			
        		}
        		if(!(x.isEmpty()) && !dfa.states.contains(x)) {

        			dfa.states.add(x);
        			
        			f = true;
        		}


    			String state1 = "";
    			String state2 = "";
    			for(int k = 0; k<state.size();k++) {
    				if(k<state.size()-1)
    					state1 += state.get(k)+"/";
    				else
        				state1 += state.get(k);
    			
    			}

    			
    			for(int k = 0; k<x.size();k++) {
    				if(k<x.size()-1)
    					state2 += x.get(k)+"/";
    				else
    					state2 += x.get(k);
    			}
    			ArrayList<String> b = new ArrayList<String>();
    			b.add(state1);
    			b.add(a);
    			b.add(state2);
    			if(!dfa.transitionFunction.contains(b)) {
    				dfa.transitionFunction.add(b);
    			}
    			
    			
    			

    			
    			
    			
    			
        		
        	
        }
        	
        	
        	c++;
        	if(c<dfa.states.size() && f == false) {
        		f=true;
        	}

        }
        

		 Collections.sort(dfa.states, new Comparator<ArrayList<Integer>>() {
	            @Override
	            public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
	                return compareLists(a, b);
	                
	            }
	            
		 });
		 
 
		
		 Collections.sort(dfa.transitionFunction, new CompareTransitions());


	        
        
        
        System.out.println("DFA Transitions" + dfa.transitionFunction);
        
        
        
        
        ArrayList<String> statesAsString = new ArrayList<>();

     for (ArrayList<Integer> state : dfa.states) {
         StringBuilder stateString = new StringBuilder();
         
         for (int i = 0; i < state.size(); i++) {
             stateString.append(state.get(i));

             if (i < state.size() - 1) {
                 stateString.append("/");
             }
         }

         statesAsString.add(stateString.toString());
     }

     System.out.println("DFA States" + statesAsString);
     
     
   
     
     
     ArrayList<Integer> startState = dfa.startState;
     StringBuilder startStateString = new StringBuilder();

     for (int i = 0; i < startState.size(); i++) {
         startStateString.append(startState.get(i));

         if (i < startState.size() - 1) {
             startStateString.append("/");
         }
     }

  
     System.out.println("DFA Start State" + startStateString.toString());
     
     

     

     dfa.acceptStates = new ArrayList<ArrayList<Integer>>();

     for (ArrayList<Integer> state : dfa.states) {
    	 for(int i : nfa.finalState) {
	         if (state.contains(i) && !dfa.acceptStates.contains(state)) {
	             dfa.acceptStates.add(state);

	         }
    	 }
     }

     StringBuilder transitionFunctionString = new StringBuilder();
     for (ArrayList<String> transition : dfa.transitionFunction) {
         transitionFunctionString.append(String.join(",", transition));
         if(dfa.transitionFunction.indexOf(transition)<dfa.transitionFunction.size()-1)
        	 transitionFunctionString.append(";");
     }


     ArrayList<String> acceptAsString = new ArrayList<>();


     for (ArrayList<Integer> acceptstate : dfa.acceptStates) {
         StringBuilder acceptStateString = new StringBuilder();
         
         for (int i = 0; i < acceptstate.size(); i++) {
       	  acceptStateString.append(acceptstate.get(i));

             if (i < acceptstate.size() - 1) {
           	  acceptStateString.append("/");
             }
         }
         
         acceptAsString.add(acceptStateString.toString());
     }

     System.out.println("DFA Accept States" + acceptAsString);
     
     String dfaAlphabet = String.join(";", nfa.alphabet);
     System.out.println("DFA Alphabet" + dfaAlphabet);
     this.output = String.join(";", statesAsString) + "#" + dfaAlphabet + "#" + transitionFunctionString.toString() + "#" + startStateString.toString() + "#" + String.join(";", acceptAsString);

     System.out.println(output);




        

        return output;
    }
    
    @Override
    public String toString() {
        NFA nfa2 = NfaReturn(nfa);
        ArrayList<ArrayList<Integer>> epsilonClosureList = epsilonClosure(nfa2);
        System.out.println(epsilonClosureList);
        System.out.println(Arrays.toString(nfa2.alphabet));
        convertToDFA(nfa2, epsilonClosureList);
        return output;
    }

    
    
    





//    public static void main(String[] args) {
//        String input = "0;1;2;3;4;5;6;7;8;9;10;11#a;f;h;w#0,a,1;1,e,6;1,e,8;2,w,3;3,e,7;4,w,5;5,e,7;6,e,2;6,e,4;7,e,10;8,f,9;9,e,10;10,h,11#0#11";
//        NFA nfa2 = NfaReturn(input);
//        ArrayList<ArrayList<Integer>> epsilonClosureList = epsilonClosure(nfa2);
//        System.out.println(epsilonClosureList);
//        System.out.println(Arrays.toString(nfa2.alphabet));
//
//
//    }


}





class DFA {
     ArrayList<Integer> startState;
     ArrayList<ArrayList<String>> transitionFunction;
     String[] alphabet;
     ArrayList<ArrayList<Integer>> states;
     //Collections.sort(states);
     //Set<Integer> acceptStates;
     ArrayList<ArrayList<Integer>> acceptStates;

     public DFA(ArrayList<Integer> startState, String[] alphabet) {
    	    this.startState = startState;
    	    //this.acceptStates = acceptState;
    	    this.transitionFunction = new ArrayList<ArrayList<String>>();
    	    this.alphabet = alphabet;
    	    this.states = new ArrayList<ArrayList<Integer>>();
    	    this.states.add(startState);


    	}



    public ArrayList<Integer> getStartState() {
        return startState;
    }

    public void setStartState(ArrayList<Integer> combinedInitialState) {
        this.startState = combinedInitialState;
    }

    public ArrayList<ArrayList<String>>  getTransitionFunction() {
        return transitionFunction;
    }

    public void setTransitionFunction(ArrayList<ArrayList<String>>  transitionFunction) {
        this.transitionFunction = transitionFunction;
    }

    public String[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String[] alphabet) {
        this.alphabet = alphabet;
    }

    public ArrayList<ArrayList<Integer>> getStates() {
        return states;
    }

    public void setStates(ArrayList<ArrayList<Integer>> states) {
        this.states = states;
    }

    public ArrayList<ArrayList<Integer>> getAcceptStates() {
        return acceptStates;
    }

    public void setAcceptStates(ArrayList<ArrayList<Integer>> acceptStates) {
        this.acceptStates = acceptStates;
    }
}





class CompareTransitions implements Comparator<ArrayList<String>> {
    
    public int compare(ArrayList<String> s1, ArrayList<String>  s2) {

        int firstPartComparison = compareParts(s1.get(0),s2.get(0));
        if (firstPartComparison != 0) return firstPartComparison;

        int secondPartComparison = s1.get(1).compareTo(s2.get(1));
        if (secondPartComparison != 0) return secondPartComparison;

        return compareParts(s1.get(2),s2.get(2));
    }

    private int compareParts(String part1, String part2) {
        String[] subParts1 = part1.split("/");
        String[] subParts2 = part2.split("/");
        
        int minLength = Math.min(subParts1.length, subParts2.length);
        for (int i = 0; i < minLength; i++) {
            int num1 = Integer.parseInt(subParts1[i]);
            int num2 = Integer.parseInt(subParts2[i]);
            if (num1 != num2) {
                return num1 - num2;
            }
        }
        return subParts1.length - subParts2.length;
    }
}
