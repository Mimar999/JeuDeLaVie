/* Implémente l'état vivant */

public class CelluleEtatVivant implements CelluleEtat{

    private static final CelluleEtatVivant instance = new CelluleEtatVivant(); 
    
    // Constr
    private CelluleEtatVivant() {}

    // Pt d'accès à l'instance 
    public static CelluleEtatVivant getInstance(){
        return instance;
    }

    @Override
    public CelluleEtat vit(){
        return this;
    }

    @Override
    public CelluleEtat meurt(){
        return CelluleEtatMort.getInstance();
    }

    @Override 
    public boolean estVivante(){
        return true;
    }
}
