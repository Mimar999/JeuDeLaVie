package jeudelavie;

public class VisiteurHighLife extends Visiteur{
    public VisiteurHighLife(JeuDeLaVie jeu){
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule){
        int voisines = cellule.nombreVoisinesVivantes(jeu);

        // même conditions de survie que le visiteur classique
        if(voisines < 2 || voisines > 3){
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule){
        int voisines = cellule.nombreVoisinesVivantes(jeu);

        // Naissance différente : arrive en cas de 3 OU 6 voisins
        if(voisines == 3 || voisines == 6){
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
