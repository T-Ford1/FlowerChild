package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FlowerChild {

    private static HashMap<String, ArrayList<Flower>> flowers;

    public static void main(String[] args) {
        print("Welcome to FlowerChild.");
        print("Enter a command or type help.");
        flowers = new HashMap();
        flowers.put("P", new ArrayList());
        for (int i = 1; i < 10; i++) {
            flowers.put("F" + i, new ArrayList());
        }
        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            try {
                parse(s.nextLine());
            } catch (Exception e) {
                print("error");
            }
        }
    }

    private static final String generation = "(P|F\\d)";
    private static final String flower = "(P\\d|F\\d,\\d+)";
    private static final String genotype = "(\\w{12})";
    private static final String and = "( and )";
    private static final String to = "( to )";
    private static final String from = "( from )";

    private static void parse(String input) {
        if (input.matches("help")) {
            print("syntax settings:");
            print("[generation]=P or F plus number");
            print("[generation]=example:P F1 or F7");
            print("[genotype]=6 pairs of two alleles");
            print("[genotype]=example: YyBbFfSSTtdd");
            print("[flower]=[generation] + number (may require a comma)");
            print("[flower]=example:P1 F1,2 or F7,4");
            print("--------------------------------");
            print("available commands:");
            print("exit");
            print("list genotype of [generation]");
            print("list phenotype of [generation]");
            print("print genotype of [flower]");
            print("print phenotype of [flower]");
            print("generate [generation]");
            print("add [genotype] to [generation]");
            print("add [flower] to [generation]");
            print("remove [genotype] from [generation]");
            print("remove [flower] from [generation]");
            print("breed [flower] and [flower]");
            print("breed [genotype] and [genotype]");
            print("probability [genotype] from [flower] and [flower]");
            print("probability [genotype] from [genotype] and [genotype]");
            print("probability [flower] from [flower] and [flower]");
            print("probability [flower] from [genotype] and [genotype]");
        } else if (input.matches("exit")) {
            System.exit(0);
        } else if (input.matches("generate " + generation)) {
            addFlower(Flower.generate(), input.substring(9));
        } else if (input.matches("add " + genotype + to + generation)) {
            Scanner read = new Scanner(input.substring(4));
            read.useDelimiter(to);
            addFlower(makeFlower(read.next()), read.next());
        } else if (input.matches("add " + flower + to + generation)) {
            Scanner read = new Scanner(input.substring(4));
            read.useDelimiter(to);
            addFlower(getFlower(read.next()), read.next());
        } else if (input.matches("remove " + genotype + from + generation)) {
            Scanner read = new Scanner(input.substring(7));
            read.useDelimiter(from);
            removeFlower(makeFlower(read.next()), read.next());
        } else if (input.matches("remove " + flower + from + generation)) {
            Scanner read = new Scanner(input.substring(7));
            read.useDelimiter(from);
            removeFlower(getFlower(read.next()), read.next());
        } else if (input.matches("list phenotype of " + generation)) {
            listPhenotype(input.substring(18));
        } else if (input.matches("list genotype of " + generation)) {
            listGenotype(input.substring(17));
        } else if (input.matches("print phenotype of " + flower)) {
            print("flower " + input.substring(19) + ":" + getFlower(input.substring(19)).phenotype());
        } else if (input.matches("print genotype of " + flower)) {
            print("flower " + input.substring(18) + ":" + getFlower(input.substring(18)).toString());
        } else if (input.matches("breed " + flower + and + flower)) {
            Scanner read = new Scanner(input.substring(6));
            read.useDelimiter(and);
            breed(getFlower(read.next()), getFlower(read.next()));
        } else if (input.matches("breed " + genotype + and + genotype)) {
            Scanner read = new Scanner(input.substring(6));
            read.useDelimiter(and);
            breed(makeFlower(read.next()), makeFlower(read.next()));
        } else if (input.matches("probability " + genotype + from + flower + and + flower)) {
            Scanner read = new Scanner(input.substring(12));
            read.useDelimiter(from + "|" + and);
            probability(makeFlower(read.next()), getFlower(read.next()), getFlower(read.next()));
        } else if (input.matches("probability " + genotype + from + genotype + and + genotype)) {
            Scanner read = new Scanner(input.substring(12));
            read.useDelimiter(from + "|" + and);
            probability(makeFlower(read.next()), makeFlower(read.next()), makeFlower(read.next()));
        } else if (input.matches("probability " + flower + from + flower + and + flower)) {
            Scanner read = new Scanner(input.substring(12));
            read.useDelimiter(from + "|" + and);
            probability(getFlower(read.next()), getFlower(read.next()), getFlower(read.next()));
        } else if (input.matches("probability " + flower + from + genotype + and + genotype)) {
            Scanner read = new Scanner(input.substring(12));
            read.useDelimiter(from + "|" + and);
            probability(getFlower(read.next()), makeFlower(read.next()), makeFlower(read.next()));
        } else {
            print("does not match");
        }
    }

    private static Flower getFlower(String flower) {
        if (flower.charAt(0) == 'P') {
            return flowers.get("P").get(Integer.parseInt(flower.substring(1)) - 1);
        } else {
            return flowers.get(flower.substring(0, 2)).get(Integer.parseInt(flower.substring(3)) - 1);
        }
    }

    private static Flower makeFlower(String genes) {
        return new Flower(genes);
    }

    private static void addFlower(Flower f, String list) {
        print("adding " + list + ":" + f);
        flowers.get(list).add(f);
    }

    private static void removeFlower(Flower f, String list) {
        print("removing " + list + ":" + f);
        ArrayList l = flowers.get(list);
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).toString().equals(f.toString())) {
                l.remove(i);
                break;
            }
        }
    }

    private static void listGenotype(String list) {
        ArrayList<Flower> a = flowers.get(list);
        for (int i = 0; i < a.size(); i++) {
            print("flower " + (i + 1) + ":" + a.get(i).toString());
        }
    }
    
    private static void listPhenotype(String list) {
        ArrayList<Flower> a = flowers.get(list);
        for (int i = 0; i < a.size(); i++) {
            print("flower " + list + (list.charAt(0) == 'P' ? "" : ",") + (i + 1) + ":" + a.get(i).phenotype());
        }
    }

    private static void probability(Flower child, Flower f1, Flower f2) {
        String decimal = String.format("%.4f", 100 * Flower.probability(child, f1, f2));
        print("probability flower " + child.toString() + ":" + decimal + "%");
    }

    private static void breed(Flower f1, Flower f2) {
        print("breeding flowers " + f1 + " and " + f2);
        Flower breed = Flower.breed(f1, f2);
        print("created flower " + breed.toString());
    }

    public static void print(String print) {
        System.out.println(print);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }
}
