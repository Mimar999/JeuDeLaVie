package jeudelavie;

public abstract class Visiteur {
    protected JeuDeLaVie jeu;

    public Visiteur(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    public abstract void visiteCelluleVivante(Cellule cellule);
    public abstract void visiteCelluleMorte(Cellule cellule);
}
