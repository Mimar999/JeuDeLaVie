package jeudelavie;

public class Test1 {
    public static void main(String[] args){
        // Création cellule morte
        Cellule c = new Cellule(CelluleEtatMort.getInstance());
        System.out.println("Cellule vivante ? " + c.estVivante());
        
        // naissance cellule 
        c.vit();
        System.out.println("Cellule vivante ? " + c.estVivante());

        // vérif singleton
        Cellule c2 = new Cellule(CelluleEtatVivant.getInstance());
        c2.vit(); // ne change rien normalement
    }
}
