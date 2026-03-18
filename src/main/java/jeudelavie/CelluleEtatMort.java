package jeudelavie;

/* Implémente l'état mort */

public class CelluleEtatMort implements CelluleEtat{

    private static final CelluleEtatMort instance = new CelluleEtatMort(); 
    
    // Constr
    private CelluleEtatMort() {}

    // Pt d'accès à l'instance 
    public static CelluleEtatMort getInstance(){
        return instance;
    }

    @Override
    public CelluleEtat meurt(){
        return this;
    }

    @Override
    public CelluleEtat vit (){
        return CelluleEtatVivant.getInstance();
    }

    @Override 
    public boolean estVivante(){
        return false;
    }

    @Override
    public void accepte(Visiteur visiteur, Cellule cellule){
        visiteur.visiteCelluleMorte(cellule);
    }
}
