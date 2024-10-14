package csen1002.main.task1;

/**
 * Write your info here
 * 
 * @name Omar Alaa
 * @id 49-9719
 * @labNumber t-19
 */

import java.util.*;

class NFAarch {
    ArrayList<String> alph;
    ArrayList<Integer> s;
    ArrayList<TransitionLine> transition;
    int startState;
    int acceptState;

   
    public ArrayList<String> getAlphabet() {
        return alph;
    }

    public void setAlphabet(ArrayList<String> alph) {
        this.alph = alph;
    }

    public ArrayList<Integer> getStates() {
        return s;
    }

    public void setStates(ArrayList<Integer> s) {
        this.s = s;
    }

    public ArrayList<TransitionLine> getTransition() {
        return transition;
    }

    public void setTransition(ArrayList<TransitionLine> transition_functions) {
        this.transition = transition_functions;
    }

    public int getStartState() {
        return startState;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public int getAcceptState() {
        return acceptState;
    }

    public void setAcceptState(int f) {
        acceptState = f;
    }

    
    


}

class TransitionLine implements Comparable {
    int a;
    int z;
    String n;

    
    
    public TransitionLine(int a, String n, int z) {
        this.a = a;
        this.z = z;
        this.n = n;
    }
    public int compareTo(Object object) {
        if (!(object instanceof TransitionLine)) {
            throw new IllegalArgumentException("Object is not an instance of TransitionLine");
        }
        TransitionLine other = (TransitionLine) object;
        int compareA = Integer.compare(this.a, other.a);
        if (compareA != 0) {
            return compareA;
        }
        int compareN = this.n.compareTo(other.n);
        if (compareN != 0) {
            return compareN;
        }
        return Integer.compare(this.z, other.z);
    }


    
}

public class RegExToNfa {
    static Stack<NFAarch> l = new Stack<>();
    static ArrayList<String> nfaStack = new ArrayList<>();
    static int i = -1;
    
    public static NFAarch union(NFAarch n1, NFAarch n2) {
        NFAarch temp = new NFAarch();
        ArrayList<TransitionLine> combinedTransitions = new ArrayList<>(n1.getTransition());
        ArrayList<Integer> combinedStates = new ArrayList<>(n1.getStates());
        ArrayList<TransitionLine> n2Transitions = n2.getTransition();
        for (TransitionLine transition : n2Transitions) {
            combinedTransitions.add(transition);
        }

        ArrayList<Integer> n2States = n2.getStates();
        for (Integer state : n2States) {
            combinedStates.add(state);
        }
        combinedStates.add(++i);
        combinedStates.add(++i);

        TransitionLine a = new TransitionLine(combinedStates.get(combinedStates.size() - 2), "e", n1.getStartState());
        TransitionLine b = new TransitionLine(combinedStates.get(combinedStates.size() - 2), "e", n2.getStartState());
        TransitionLine c = new TransitionLine(n1.getAcceptState(), "e", combinedStates.get(combinedStates.size() - 1));
        TransitionLine d = new TransitionLine(n2.getAcceptState(), "e", combinedStates.get(combinedStates.size() - 1));

        TransitionLine[] transitions = {a, b, c, d};
        for (TransitionLine transition : transitions) {
            combinedTransitions.add(transition);
        }

        temp.setAlphabet(n1.getAlphabet());
        temp.setStates(combinedStates);
        temp.setTransition(combinedTransitions);
        temp.setStartState(combinedStates.get(combinedStates.size() - 2));
        temp.setAcceptState(combinedStates.get(combinedStates.size() - 1));

        return temp;
    }

    public static NFAarch star(NFAarch n) {
        NFAarch temp = new NFAarch();
        
        ArrayList<TransitionLine> combinedTransitions = new ArrayList<>(n.getTransition());
        ArrayList<Integer> combinedStates = new ArrayList<>(n.getStates());
        
        ArrayList<Integer> newStates = new ArrayList<>();
        newStates.add(++i);
        newStates.add(++i);
        combinedStates.addAll(newStates);
        
        TransitionLine a = new TransitionLine(newStates.get(0), "e", n.getStartState());
        TransitionLine b = new TransitionLine(n.getAcceptState(), "e", newStates.get(1));
        TransitionLine c = new TransitionLine(newStates.get(0), "e", newStates.get(1));
        TransitionLine d = new TransitionLine(n.getAcceptState(), "e", n.getStartState());
        
        TransitionLine[] transitions = {a, b, c, d};
        for (TransitionLine transition : transitions) {
            combinedTransitions.add(transition);
        }
        
        temp.setAlphabet(n.getAlphabet());
        temp.setStates(combinedStates);
        temp.setTransition(combinedTransitions);
        temp.setStartState(newStates.get(0));
        temp.setAcceptState(newStates.get(1));
        return temp;
    }

    public static NFAarch concatination(NFAarch n1, NFAarch n2) {
        NFAarch temp = new NFAarch();

        ArrayList<TransitionLine> combinedTransitions = new ArrayList<>(n1.getTransition());
        for (TransitionLine transition : n2.getTransition()) {
            combinedTransitions.add(transition);
        }
        ArrayList<Integer> combinedStates = new ArrayList<>(n1.getStates());
        for (Integer state : n2.getStates()) {
            combinedStates.add(state);
        }

        for (int i = 0; i < combinedTransitions.size(); i++) {
            TransitionLine currentTransition = combinedTransitions.get(i);
            if (currentTransition.a == n2.getStartState()) {
                TransitionLine newTransition = new TransitionLine(n1.getAcceptState(), currentTransition.n, currentTransition.z);
                combinedTransitions.set(i, newTransition);
            }
            if (currentTransition.z == n2.getStartState()) {
                TransitionLine epsilonTransition = new TransitionLine(currentTransition.a, "e", n1.getAcceptState());
                combinedTransitions.add(i, epsilonTransition);
            }
        }
        combinedStates.remove(Integer.valueOf(n2.getStartState()));
        temp.setAlphabet(n1.getAlphabet());
        temp.setStates(combinedStates);
        temp.setTransition(combinedTransitions);
        temp.setStartState(n1.getStartState());
        temp.setAcceptState(n2.getAcceptState());
        return temp;
    }



    public static NFAarch generator(String x) {
        NFAarch temp = new NFAarch();

        ArrayList<Integer> tempstates = new ArrayList<>();
        tempstates.add(++i);
        tempstates.add(++i);
        ArrayList<TransitionLine> temptransitions = new ArrayList<>();
        temptransitions.add(new TransitionLine(tempstates.get(0), x, tempstates.get(1)));
        ArrayList<String> tempalphabet = new ArrayList<>();
        tempalphabet.add(x);

        temp.setAlphabet(tempalphabet);
        temp.setStates(tempstates);
        temp.setTransition(temptransitions);
        temp.setStartState(tempstates.get(0));
        temp.setAcceptState(tempstates.get(1));
        
        
        return temp;
    }

    public static String postfix(String y) {
        for (int i = 0; i < y.length(); i++) {
            char currentChar = y.charAt(i);
            switch (currentChar) {
            
            	case '|':
            		unionHandler();
                    break;
                    
                case '*':
                    starHandler();
                    break;
                    
                case '.':
                    concHandler();
                    break;
                default:
                    def(currentChar);
                    break;
            }
        }
        NFAarch n = l.pop();
        n.setAlphabet(extractor(y));
        return outputstructure(n);
    }

    
    private static void unionHandler() {
        String temp = nfaStack.get(nfaStack.size() - 1);
        nfaStack.remove(nfaStack.size() - 1);
        String temp2 = nfaStack.get(nfaStack.size() - 1);
        nfaStack.remove(nfaStack.size() - 1);
        nfaStack.add(temp2 + "|" + temp);
        NFAarch temp3 = l.pop();
        NFAarch temp4 = l.pop();
        l.push(union(temp4, temp3));
    }
    
    private static void starHandler() {
        String temp = nfaStack.get(nfaStack.size() - 1);
        nfaStack.remove(nfaStack.size() - 1);
        nfaStack.add(temp + "*");
        NFAarch temp2 = l.pop();
        l.push(star(temp2));
    }
    private static void concHandler() {
        String temp = nfaStack.get(nfaStack.size() - 1);
        nfaStack.remove(nfaStack.size() - 1);
        String temp2 = nfaStack.get(nfaStack.size() - 1);
        nfaStack.remove(nfaStack.size() - 1);
        nfaStack.add(temp2 + "." + temp);
        NFAarch temp3 = l.pop();
        NFAarch temp4 = l.pop();
        l.push(concatination(temp4, temp3));
    }



    private static void def(char currentChar) {
        nfaStack.add(String.valueOf(currentChar));
        NFAarch temp = generator(String.valueOf(currentChar));
        l.push(temp);
    }
    

    private static ArrayList<String> extractor(String input) {
        String temp = "";
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '*' && input.charAt(i) != '.' && input.charAt(i) != '|' && input.charAt(i) != 'e') {
                temp += input.charAt(i) + ";";
            }
        }
        String[] temp2 = temp.split(";");
        for (int i = 0; i < temp2.length; i++) {
            for (int j = i + 1; j < temp2.length; j++) {
                if (temp2[i].equals(temp2[j])) {
                    temp2[j] = "";
                }
            }
        }
        temp = "";
        for (int i = 0; i < temp2.length; i++) {
            if (!temp2[i].equals("")) {
                temp += temp2[i] + ";";
            }
        }
        String[] temp3 = temp.split(";");
        ArrayList<String> temp4 = new ArrayList<>();
        for (int i = 0; i < temp3.length; i++) {
            temp4.add(temp3[i]);
        }
        return temp4;
    }
    public static String outputstructure(NFAarch n) {
        StringBuilder temp = new StringBuilder();
        appendStates(temp, n);
        appendAlphabet(temp, n);
        appendTransitions(temp, n);
        appendStartAndAcceptStates(temp, n);
        return temp.toString();
    }

    
    private static void appendStates(StringBuilder temp, NFAarch n) {
        for (Integer state : n.getStates()) {
            temp.append(state).append(";");
        }
        if (temp.length() > 0) {
            temp.deleteCharAt(temp.length() - 1);
        }
        temp.append("#");
    }

    
    
    private static void appendAlphabet(StringBuilder temp, NFAarch n) {
        Collections.sort(n.getAlphabet());
        for (String letter : n.getAlphabet()) {
            temp.append(letter).append(";");
        }
        if (temp.length() > 0) {
            temp.deleteCharAt(temp.length() - 1);
        }
        temp.append("#");
    }

    private static void appendTransitions(StringBuilder temp, NFAarch n) {
        Collections.sort(n.getTransition());
        for (TransitionLine transition : n.getTransition()) {
            temp.append(transition.a).append(",")
                    .append(transition.n).append(",")
                    .append(transition.z).append(";");
        }
        if (temp.length() > 0) {
            temp.deleteCharAt(temp.length() - 1);
        }
        temp.append("#");
    }
    private static void appendStartAndAcceptStates(StringBuilder temp, NFAarch n) {
        temp.append(n.getStartState()).append("#")
                .append(n.getAcceptState());
    }

    String t;

    public RegExToNfa(String t) {
        t = t.substring(t.lastIndexOf("#") + 1, t.length());
        this.t = t;
    }
    public String toString() {
        String s = postfix(this.t);
        i = -1;
        l = new Stack<>();
        nfaStack = new ArrayList<>();
        i = -1;
        return s;
    }

}

