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
    


    // GETTERS
    public int getxMax(){
        return xMax;
    }

    public int getyMax(){
        return yMax;
    }
}
