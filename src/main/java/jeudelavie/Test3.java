package jeudelavie;

public class Test3 {
    public static void main(String[] args) {

        // créa grille 
        JeuDeLaVie jeu = new JeuDeLaVie(80, 60);

        // créa interface
        JeuDeLaVieUI fenetre = new JeuDeLaVieUI(jeu);

        jeu.attacheObservateur(fenetre);

        System.out.println("Fenêtre créée, fermer pour arrêter le programme");
    }
}