package jeudelavie;

public class Test2 {
    public static void main(String[] args) {
        System.out.println("=== Initialisation de la grille 5x5 ===");
        JeuDeLaVie jeu = new JeuDeLaVie(5, 5);
        for (int y = 0; y < jeu.getxMax(); y++) {
            for (int x = 0; x < jeu.getyMax(); x++) {
                Cellule c = jeu.getGrilleXY(x, y);
                if (c.estVivante()) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println(); 
        }

        System.out.println("\n=== Test du calcul des voisins ===");
        Cellule centre = jeu.getGrilleXY(2, 2);
        int nbVoisins = centre.nombreVoisinesVivantes(jeu);
        
        System.out.println("La cellule au centre (x=2, y=2) est " + (centre.estVivante() ? "VIVANTE" : "MORTE"));
        System.out.println("L'algorithme a trouvé : " + nbVoisins + " voisine(s) vivante(s) autour d'elle.");
        System.out.println("(Tu peux vérifier en comptant les [X] autour de la case centrale !)");
    }
}