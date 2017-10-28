import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
   private static String filename = "file1.txt";
    static ArrayList<Production> productions = new ArrayList<Production>();
    static List<String> facts;
    static char goal;
    static String production;
    static char consistent;
    static ArrayList<String> antecedents;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line;
        br.readLine();  // Studente Aliona Lisovskaja, KM, 4, 1. Data
        br.readLine();  // 1 testas. Paskaitos pavyzdys
        br.readLine();  // 1) Taisykles
        try {
            while ((line = br.readLine()) != null) {
                if (line.equals("2) Faktai")){
                    line = br.readLine();
                    facts = new ArrayList<String>(Arrays.asList(line.split(" ")));
                }else if (line.equals("3) Tikslas")){
                    line = br.readLine();
                    goal = line.charAt(0);
                }else{
                    // getting all productions
                    production = line.substring(0, line.indexOf("/"));
                    consistent = production.charAt(0);
                    antecedents = new ArrayList<String>(Arrays.asList((production.substring(2)).split(" ")));
                    productions.add(new Production(consistent, antecedents));
                }
            }
        } finally {
            br.close();
        }
        System.out.println("1 DALIS. Duomenys");
        System.out.println();
        System.out.println("  1) Taisyklės");
        for(int i = 0; i < productions.size(); i++){
            System.out.println("     R" + (i + 1) + ": " + productions.get(i).printAntecendents() + "-> " + productions.get(i).getConsistent());
        }
        System.out.println();
        System.out.println("  2) Faktai");
        System.out.print("     ");
        printFacts();
        System.out.println();
        System.out.println("  3) Tikslas");
        System.out.print("     " + goal + "\n\n");
        System.out.println("2 DALIS. Vykdymas");
        System.out.println();
    }

    public static void printFacts(){
        for(int i = 0; i < facts.size() - 1; i++){
            System.out.print(facts.get(i) + ", ");
        }
        System.out.print(facts.get(facts.size() - 1) + " \n");
    }

}
