package jeudelavie;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        // Grille 100x70
        JeuDeLaVie jeu = new JeuDeLaVie(100, 70);
        JeuDeLaVieUI fenetre = new JeuDeLaVieUI(jeu);
        jeu.attacheObservateur(fenetre);

        System.out.println("Démarrage du jeu de la vie.");

        // Boucle/simulation
        while(true){
            jeu.calculerGenerationSuivante();

            //Petite pause entre chaque génération
            Thread.sleep(500);
        }
    }
}