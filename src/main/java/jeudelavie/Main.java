package jeudelavie;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        // Grille 100x70
        JeuDeLaVie jeu = new JeuDeLaVie(100, 70);

        // HighLife
        // jeu.setVisiteur(new VisiteurHighLife(jeu));
        // HighLife

        JeuDeLaVieUI fenetre = new JeuDeLaVieUI(jeu);
        ObservateurConsole console = new ObservateurConsole(jeu);

        jeu.attacheObservateur(fenetre);
        jeu.attacheObservateur(console);

        System.out.println("Démarrage du jeu de la vie.");
    }
}