package main;

public class Flower {

    private static final String alleles = "YyBbFfSsTtDd";
    private static final String[] phenotypes = new String[]{
        "yellow petals", "orange petals", "brown floral disk", "black floral disk",
        "5 petals", "6 petals", "short stem", "tall stem",
        "2 leaves", "3 leaves", "dark green leaves", "light green leaves"
    };

    private final String[] genotype;

    public Flower(String genes) {
        for (int i = 0; i < 6; i++) {
            char c1 = genes.charAt(i * 2);
            char c2 = genes.charAt(i * 2 + 1);
            if (Character.isLowerCase(c1)) {
                genes = genes.substring(0, i * 2) + c2 + c1 + genes.substring(i * 2 + 2);
            }
        }
        genotype = new String[genes.length() / 2];
        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = genes.substring(i * 2, 2 + i * 2);
        }
    }

    public static Flower generate() {
        String genes = "";
        for (int i = 0; i < 12; i++) {
            int j = 2 * (i / 2) + (int) (2 * Math.random());
            genes += alleles.substring(j, j + 1);
        }
        return new Flower(genes);
    }

    public static Flower breed(Flower f1, Flower f2) {
        String s = "";
        for (int i = 0; i < f1.genotype.length; i++) {
            s += punnetSquare(f1.genotype[i], f2.genotype[i]);
        }
        return new Flower(s);
    }

    public static float probability(Flower child, Flower f1, Flower f2) {
        float p = 1.0f;
        for (int i = 0; i < child.genotype.length; i++) {
            p *= prob(child.genotype[i], f1.genotype[i], f2.genotype[i]);
        }
        return p;
    }

    private static float prob(String c, String f1, String f2) {
        int matches = 0;
        for (int i = 0; i < 4; i++) {
            char a = f1.charAt(i / 2);
            char b = f2.charAt(i % 2);
            char c0 = c.charAt(0);
            char c1 = c.charAt(1);
            if (a == c0 && b == c1 || b == c0 && a == c1) {
                matches++;
            }
        }
        return matches / 4.f;
    }

    private static String punnetSquare(String g1, String g2) {
        String g3 = "" + g1.charAt((int) (2 * Math.random())) + g2.charAt((int) (2 * Math.random()));
        if (Character.isLowerCase(g3.charAt(0))) {
            return "" + g3.charAt(1) + g3.charAt(0);
        }
        return g3;
    }

    @Override
    public String toString() {
        String toRet = "";
        for (String s : genotype) {
            toRet += s;
        }
        return toRet;
    }
    
    public String phenotype() {
        String phenotype = "";
        for (int i = 0; i < genotype.length; i++) {
            phenotype += phenotypes[alleles.indexOf(genotype[i].charAt(0))] + ",";
        }
        return phenotype.substring(0, phenotype.length() - 1);
    }
}
