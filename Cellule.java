/* Une cellule possède un état et redirige les appels de méthodes vers cet état  */

public class Cellule {
    private CelluleEtat etat;

    // Constr
    public Cellule(CelluleEtat etatInitial){
        this.etat = etatInitial;
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
}
