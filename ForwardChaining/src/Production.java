import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Production {
    private char consistent;
    private ArrayList<String> antecedents;
    private boolean flag1 = false;

    Production(char consistent, ArrayList<String> antecedents){
        this.consistent = consistent;
        this.antecedents = antecedents;
        flag1 = false;
    }

    public String printAntecendents(){
        String text = "";
        for(int i = 0; i < antecedents.size() - 1; i++){
            text += antecedents.get(i) + ", ";
        }
        text += antecedents.get(antecedents.size() - 1) + " ";
        return text;
    }

    public char getConsistent(){
        return consistent;
    }
}
