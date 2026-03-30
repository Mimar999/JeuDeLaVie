package jeudelavie;

public class VisiteurDayAndNight extends Visiteur {
    public VisiteurDayAndNight(JeuDeLaVie jeu){
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule){
        int voisines = cellule.nombreVoisinesVivantes(jeu);

        if(voisines == 0 || voisines == 1 || voisines == 2 || voisines == 5){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule){
        int voisines = cellule.nombreVoisinesVivantes(jeu);

        if(voisines == 3 || voisines == 6 || voisines == 7 || voisines == 8){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
