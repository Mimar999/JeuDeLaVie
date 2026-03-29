package jeudelavie;

import java.util.ArrayList;
import java.util.List;

public class JeuDeLaVie implements Observable {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;

    private List<Observateur> observateurs;

    private List<Commande> commandes;

    private Visiteur visiteur;

    private int generation = 0; // compteur de génération

    private String messageSystem = "Prêt";
    // CONSTR

    public JeuDeLaVie(int xMax, int yMax){
        this.xMax = xMax;
        this.yMax = yMax;
        this.grille = new Cellule[xMax][yMax];
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.visiteur = new VisiteurClassique(this);
        initialiseGrille();
    }

    // METHODES 

    public void initialiseGrille(){
        for(int x = 0; x < xMax; x++){
            for(int y = 0; y < yMax; y++){
                // on part sur 30% de chances d'être vivant 
                CelluleEtat etatInitial = (Math.random() < 0.3) ? CelluleEtatVivant.getInstance() : CelluleEtatMort.getInstance();
                
                grille[x][y] = new Cellule(x, y, etatInitial);
            }
        }
    }

    public Cellule getGrilleXY(int x, int y){
        int xTorique = (x + xMax) % xMax;
        int yTorique = (y + yMax) % yMax;

        return grille[xTorique][yTorique]; 
    }

    // méthode pour permettre d'inverser l'état d'une cellule là o`u on clique
    public void inverserEtatCellule(int x, int y){
        Cellule c = getGrilleXY(x, y);
        if(c != null){
            if(c.estVivante()){
                c.meurt();
            }
            else{
                c.vit();
            }
            notifieObservateur();
        }
    }

    @Override
    public void attacheObservateur(Observateur o){
        observateurs.add(o);
    }

    @Override
    public void detacheObservateur(Observateur o){
        observateurs.remove(o);
    }

    @Override
    public void notifieObservateur(){
        for(Observateur o : observateurs){
            o.actualise();
        }
    }

    public void ajouteCommande(Commande c){
        commandes.add(c);
    }

    public void executeCommande(){
        for(Commande c : commandes){
            c.executer();
        }

        commandes.clear(); // vidage de la liste une fois exécutée
    }

    public void distribueVisiteur(){
        for(int x = 0; x < xMax; x++){
            for(int y = 0; y < yMax; y++){
                grille[x][y].accepte(visiteur);
            }
        }
    }

    public void calculerGenerationSuivante(){
        //analyse la grille et crée les commandes 
        distribueVisiteur();

        // exécute les naissances et morts d'un coup 
        executeCommande();

        generation++; //incrémentation du compteur
        // redissine/demande à l'interface graphique de se redessiner 
        notifieObservateur();
    }

    // méthode pour changer les règles (highlife)
    public void setVisiteur(Visiteur visiteur){
        this.visiteur = visiteur;
    }

    //récup du num de la génération
    public int getGeneration(){
        return generation;
    }

    // méthode pour vider la grille totalement
    public void viderGrille(){
        for(int x = 0; x < xMax; x++){
            for(int y = 0; y < yMax; y++){
                if(grille[x][y].estVivante()){
                    grille[x][y].meurt();
                }
            }
        }

        this.generation = 0; // reset à 0 quand on vide la grille
        
        notifieObservateur();
    }

    // fait apparaître un planeur au milieu de l'écran
    public void dessinerPlaneur(){
        viderGrille();
        int cx = xMax / 2;
        int cy = yMax / 2;

        // coordonnées
        getGrilleXY(cx, cy - 1).vit();
        getGrilleXY(cx + 1, cy).vit();
        getGrilleXY(cx - 1, cy + 1).vit();
        getGrilleXY(cx, cy + 1).vit();
        getGrilleXY(cx + 1, cy + 1).vit();

        notifieObservateur();
    }

    // Méthode permettant de générer des planeurs à l'infini
    public void dessinerCanonGosper(){

        // Vérifie sur grille assez grande pour contenir le canon 
        if(xMax < 40 || yMax < 20){
            System.out.println("Erreur : Grille trop petite (Min 40x20) !");
            setMessageSysteme("Erreur : Grille trop petite (Min 40x20) !");
            return;
        }

        viderGrille();

        int[][] canon = {
            {24, 1}, {22, 2}, {24, 2}, {12, 3}, {13, 3}, {20, 3}, {21, 3}, {34, 3}, {35, 3},
            {11, 4}, {15, 4}, {20, 4}, {21, 4}, {34, 4}, {35, 4}, {0, 5}, {1, 5}, {10, 5},
            {16, 5}, {20, 5}, {21, 5}, {0, 6}, {1, 6}, {10, 6}, {14, 6}, {16, 6}, {17, 6},
            {22, 6}, {24, 6}, {10, 7}, {16, 7}, {24, 7}, {11, 8}, {15, 8}, {12, 9}, {13, 9}
        };

        // Centrage du canon
        for(int[] coord : canon){
            getGrilleXY(coord[0] + 5, coord[1] + 5).vit();
        }
        notifieObservateur();
    }

    public String getMessageSysteme(){
        return messageSystem;
    }

    public void setMessageSysteme(String msg){
        this.messageSystem = msg;
        notifieObservateur();
    }

    public int getNbCellulesVivantes(){
        int count = 0;
        for(int x = 0; x < xMax; x++){
            for(int y = 0; y < yMax; y++){
                if(grille[x][y].estVivante()) count++;
            }
        }
        return count;
    }
    


    // GETTERS
    public int getxMax(){
        return xMax;
    }

    public int getyMax(){
        return yMax;
    }
}
