package csen1002.main.task3;

import java.util.*;

/**
 * Write your info here
 * 
 * @name Omar Alaa
 * @id 49-9719
 * @labNumber t-19
 */

class DFA {
    int startState;
    ArrayList<ArrayList<String>> transitionFunction;
    String[] alphabet;
    ArrayList<Integer> states;
    ArrayList<Integer> acceptStates;

    public DFA(int startState, String[] alphabet) {
   	    this.startState = startState;
   	    this.transitionFunction = new ArrayList<ArrayList<String>>();
   	    this.alphabet = alphabet;
   	    this.states = new ArrayList<Integer>();
   	    this.states.add(startState);



   	}



//   public int getStartState() {
//       return startState;
//   }
//
//   public void setStartState(int combinedInitialState) {
//       this.startState = combinedInitialState;
//   }
//
//   public ArrayList<ArrayList<String>>  getTransitionFunction() {
//       return transitionFunction;
//   }
//
//   public void setTransitionFunction(ArrayList<ArrayList<String>>  transitionFunction) {
//       this.transitionFunction = transitionFunction;
//   }
//
//   public String[] getAlphabet() {
//       return alphabet;
//   }
//
//   public void setAlphabet(String[] alphabet) {
//       this.alphabet = alphabet;
//   }
//
//   public ArrayList<Integer> getStates() {
//       return states;
//   }
//
//   public void setStates(ArrayList<Integer> states) {
//       this.states = states;
//   }
//
//   public ArrayList<Integer> getAcceptStates() {
//       return acceptStates;
//   }
//
//   public void setAcceptStates(ArrayList<Integer> acceptStates) {
//       this.acceptStates = acceptStates;
//   }
}





public class FallbackDfa {

String fdfa;
DFA dfa;
	public FallbackDfa(String fdfa) {
		this.dfa = DfaReturn(fdfa);
		this.fdfa = fdfa;
}
	
	
	   public static DFA DfaReturn(String dfa) {
	        ArrayList<ArrayList<String>> transitions = new ArrayList<>();
	        ArrayList<Character> alphabet = new ArrayList<>();
	        int initialState;
	        ArrayList<Integer> finalState = new ArrayList<>();
	        String[] parts = dfa.split("#");
	        String[] states = parts[0].split(";");
	        ArrayList<Integer> states1 = new ArrayList<>();
	        for (String state : states) {
	            states1.add(Integer.parseInt(state));
	        }
	        
	       String[] alpha = parts[1].split(";");
	        String[] transitionsString = parts[2].split(";");
	        for (String transition : transitionsString) {
	            transitions.add(new ArrayList<>(Arrays.asList(transition.split(","))));
	        }

	        initialState = Integer.parseInt(parts[3]);
	        String[] accState = parts[4].split(";");
	        for (String state : accState) {
	            finalState.add(Integer.parseInt(state));
	        }
	        DFA dfa1 = new DFA(initialState, accState);
	        dfa1.states = states1;
	        dfa1.alphabet = alpha;
	        dfa1.transitionFunction = transitions;
	        dfa1.startState = initialState;
	        dfa1.acceptStates = finalState;
	        return dfa1;
	    }
	   
	   public static int getNextState(DFA dfa, int currentState, String t, char inputSymbol) {
		    int inputIndex = -1;
		    for (int i = 0; i < dfa.alphabet.length; i++) {
		        if (dfa.alphabet[i].charAt(0) == inputSymbol) {
		            inputIndex = i;
		            break;
		        }
		    }

		    if (inputIndex == -1) {
		        return -1;
		    }

		    for (ArrayList<String> transition : dfa.transitionFunction) {
		        if (Integer.parseInt(transition.get(0)) == currentState && transition.get(1).charAt(0) == inputSymbol) {
		            return Integer.parseInt(transition.get(2)); 
		        }
		    }

		    return -1;
		}


	   
	   public static String FallBackDfa(String t, DFA dfa) {
		   String s = "";
		   int l = 0;
		   int r = 0;

		   char inputSymbol = 0;
		   int currentState = dfa.startState;
		   Stack<Integer> stack = new Stack<>();


		   while(r<t.length()) {
			   System.out.println("l"+l);
			   System.out.println("r"+r);
			   stack.clear();
			   stack.push(dfa.startState);
			   currentState = dfa.startState;
		   for (l = r; l < t.length(); l++) {
			   System.out.println("stack"+stack);
			     inputSymbol = t.charAt(l);
			    
			    currentState = getNextState(dfa, currentState, t, inputSymbol);
			    stack.push(currentState);
				   System.out.println(inputSymbol + " "+ l);
				   


			    
			    
			   
			   
		   }
		   
		   
		   System.out.println(l+"ss");

		   int id = currentState;
		   int curIndex = l-1;
		   System.out.println(id+"idddd");
		   System.out.println(curIndex+"lllll");

		
			boolean tt= false;
			System.out.println(s+" "+"output");
		   while (!stack.isEmpty()) {
			   int stateChecker;
			   stateChecker = stack.peek();
			    if (dfa.acceptStates.contains(stateChecker)) {
			        s += t.substring(r,l)+","+stateChecker+";";
			        r = l;
			        l =r;
			        tt = true;


			        stack.clear();
//			        currentState = dfa.startState;
				    stack.push(dfa.startState);
				    break;


			    } 
			    else {
			    	System.out.println("  ---------------------------");
			        l--;
			        currentState = stack.pop();
			        stateChecker = currentState;
				    
			    }
			}
  		   if(!tt) {
		   s += t.substring(r,t.length())+","+id;
		   break;
	   }


		   
		   
		   }
  		   if(r == t.length()) {
  			   s= s.substring(0, s.length()-1);
  		   }

	
		  System.out.println(s+" "+"output");
		   return s;
		   
		   
	   }
	   
	   
	   
//	   
//	    public static void main(String[] args) {
//     String input = "0;1;2;3#a;b#0,a,0;0,b,1;1,a,2;1,b,1;2,a,0;2,b,3;3,a,3;3,b,3#0#1;2";
//     String t = "baababb";
//     DFA dfa2 = DfaReturn(input);
//     System.out.println(dfa2.states);
//     System.out.println(Arrays.toString(dfa2.alphabet));
//     System.out.println(dfa2.transitionFunction);
//     System.out.println(dfa2.startState);
//     System.out.println(dfa2.acceptStates);
//     System.out.println("////////////////////////////////");
//     FallBackDfa(t,dfa2);
//     
//
//
//
// }
	
	   public String run(String input) {
		
		return FallBackDfa(input, dfa);
		
	}
	
}
