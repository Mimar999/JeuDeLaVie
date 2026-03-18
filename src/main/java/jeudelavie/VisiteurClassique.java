package jeudelavie;

public class VisiteurClassique extends Visiteur{
    
    public VisiteurClassique(JeuDeLaVie jeu){
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule){
        int voisines = cellule.nombreVoisinesVivantes(jeu);
        // règle 1 et 2 : meurt si trop isolée (<2 voisines) ou trop peu isolée (>3 voisines)
        if(voisines < 2 || voisines > 3){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
        // Sinon elle reste vivante
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule){
        int voisines = cellule.nombreVoisinesVivantes(jeu);
        // Règle 3 : naissance si 3 voisines
        if(voisines == 3){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
