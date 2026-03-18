package jeudelavie;
/* Une cellule possède un état et redirige les appels de méthodes vers cet état  */

public class Cellule {
    private int x;
    private int y;
    private CelluleEtat etat;

    // Constr
    public Cellule(int x, int y, CelluleEtat etat){
        this.x = x;
        this.y = y;
        this.etat = etat;
    }

    public void vit(){
        this.etat = this.etat.vit();
    }

    public void meurt(){
        this.etat = this.etat.meurt();
    }

    public boolean estVivante(){
        return this.etat.estVivante();
    }

    // Calcul du nb de voisines vivantes
    public int nombreVoisinesVivantes(JeuDeLaVie jeu){
        int count = 0;
        // Parcourt des 8 cases autour de la case (+ la case elle-même)
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                if(i == this.x && j == this.y) continue;

                Cellule voisine = jeu.getGrilleXY(i, j);

                if(voisine != null && voisine.estVivante()){
                    count++;
                }
            }
        }
        return count;
    }

    public void accepte(Visiteur visiteur){
        this.etat.accepte(visiteur, this);
    }
}
