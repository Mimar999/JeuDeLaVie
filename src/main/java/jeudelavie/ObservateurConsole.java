package jeudelavie;

// compte les cellules vivantes et envoie un log dans le terminal
public class ObservateurConsole implements Observateur {
    private JeuDeLaVie jeu;

    public ObservateurConsole(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    @Override
    public void actualise(){
        int vivantes = 0;

        //comptage des cellules
        for(int x = 0; x < jeu.getxMax(); x++){
            for(int y = 0; y < jeu.getyMax(); y++){
                if(jeu.getGrilleXY(x, y).estVivante()){
                    vivantes++;
                }
            }
        }
        //affichage
        System.out.println("Génération : " + jeu.getGeneration() + " | Cellules en vie : " + vivantes);
    }
}
