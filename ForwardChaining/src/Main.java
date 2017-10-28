import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static String filename = "file1.txt";
    private static ArrayList<Production> productions = new ArrayList<Production>();
    private static List<String> facts;
    private static String goal;
    private static String production;
    private static char consistent;
    private static ArrayList<String> antecedents;
    private static ArrayList<String> GDB = new ArrayList<String>();
    private static boolean isFlag1 = false;
    private static int counter = 0;
    private static String missingAntecedent = "";
    private static Boolean consistentInFacts = false;
    private static Boolean isFlag2 = false;
    private static ArrayList<Integer> path = new ArrayList<Integer>();

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
                    GDB = (ArrayList<String>) facts;
                }else if (line.equals("3) Tikslas")){
                    line = br.readLine();
                    goal = String.valueOf(line.charAt(0));
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
            System.out.println("     R" + (i + 1) + ": " + productions.get(i).printAntecendents() + " -> " + productions.get(i).getConsistent());
        }
        System.out.println();
        System.out.println("  2) Faktai");
        System.out.print("     ");
        printFacts();
        System.out.println("\n");
        System.out.println("  3) Tikslas");
        System.out.print("     " + goal + "\n\n");
        System.out.println("2 DALIS. Vykdymas");
        forwardChaining();
        if(GDB.contains(goal)) {
            System.out.println("   Tikslas gautas.");
            System.out.println("\n3 DALIS. Rezultatai");
            System.out.println("    1) Tikslas " + goal + " išvestas.");
            System.out.print("    2) Kelias: ");
        }else{
            System.out.println("   Tikslas negautas.");
        }
        for (int i = 0; i < path.size(); i++) {
            if (i != path.size() - 1) {
                System.out.print("R" + path.get(i) + ", ");
            } else {
                System.out.print("R" + path.get(i) + ".");
            }
        }
    }

    public static void printFacts(){
        for(int i = 0; i < facts.size() - 1; i++){
            System.out.print(facts.get(i) + ", ");
        }
        System.out.print(facts.get(facts.size() - 1));
    }

    public static void forwardChaining(){
        if((!GDB.contains(goal))) {
            System.out.println("\n " + (++counter) + " ITERACIJA");
            for (int i = 0; i < productions.size(); i++) {
                if ((!GDB.contains(goal))) {
                    isFlag1 = false;
                    isFlag2 = false;
                    consistentInFacts = false;
                    if (canApply(i)) {
                        productions.get(i).setFlag1(true);  // pakeliame - true
                        GDB.add(String.valueOf(productions.get(i).getConsistent()));
                        System.out.print("   R" + (i + 1) + ":" + productions.get(i).printAntecendents() + "->" + productions.get(i).getConsistent() + " taikome. Pakeliame flag1. Faktai ");
                        printFacts();
                        System.out.println(".");
                        path.add(i + 1);
                        forwardChaining();
                    } else if (isFlag1) {
                        System.out.println("   R" + (i + 1) + ":" + productions.get(i).printAntecendents() + "->" + productions.get(i).getConsistent() + " praleidžiame, nes pakelta flag1.");
                    } else if (consistentInFacts) {
                        System.out.println("   R" + (i + 1) + ":" + productions.get(i).printAntecendents() + "->" + productions.get(i).getConsistent() + " netaikome, nes konsekventas faktuose. Pakeliame flag2.");
                    } else if (isFlag2) {
                        System.out.println("   R" + (i + 1) + ":" + productions.get(i).printAntecendents() + "->" + productions.get(i).getConsistent() + " praleidžiame, nes pakelta flag2.");
                    } else {
                        System.out.println("   R" + (i + 1) + ":" + productions.get(i).printAntecendents() + "->" + productions.get(i).getConsistent() + " netaikome, nes trūksta " + missingAntecedent + ".");
                    }
                }
            }
        }
    }

    public static boolean canApply(int i){   // FLAGUS TIKRINTI
        int counter = 0;
        for(int j = 0; j < productions.get(i).getAntecedents().size(); j++){
            if(productions.get(i).getFlag1()){
                isFlag1 = true;
                return false;
            }
            if(productions.get(i).getFlag2()){
                isFlag2 = true;
                return false;
            }
            if(GDB.contains(productions.get(i).getAntecedents().get(j))) {
                counter++;
            }else{
                //System.out.println("TRUKSTA " + productions.get(i).getAntecedents().get(j));
                missingAntecedent = productions.get(i).getAntecedents().get(j);
                return false;
            }
            if(facts.contains(String.valueOf(productions.get(i).getConsistent()))){
                consistentInFacts = true;
                productions.get(i).setFlag2(true);
                return false;
            }
        }
        if(counter == productions.get(i).getAntecedents().size()){
            return true;
        }
        return false;
    }
}
