package jeudelavie;

public class JeuDeLaVie {
    private Cellule[][] grille;
    private int xMax;
    private int yMax;

    // CONSTR

    public JeuDeLaVie(int xMax, int yMax){
        this.xMax = xMax;
        this.yMax = yMax;
        this.grille = new Cellule[xMax][yMax];
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
        // pour éviter les IndexOutOfBoundsException sur les bords de la grille 
        if (x >= 0 && x < xMax && y >= 0 && y < yMax){
            return grille[x][y];
        }
        return null; // si case en dehors de la grille
    }


    // GETTERS
    public int getxMax(){
        return xMax;
    }

    public int getyMax(){
        return yMax;
    }
}
