package jeudelavie;

import java.awt.Graphics;

// Permet en utilisant la bibliothèque Swing de créer une fenêtre graphique

import javax.swing.JFrame;

public class JeuDeLaVieUI extends JFrame implements Observateur {
    private JeuDeLaVie jeu;

    public JeuDeLaVieUI(JeuDeLaVie jeu){
        this.jeu = jeu;
        this.setTitle("Le jeu de la vie");

        // Dimensions fenêtre adapté à dimensions grille (10 pix par celulle)
        this.setSize(jeu.getxMax() * 10 + 20, jeu.getyMax() * 10 + 40);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ferme le prog en quittant
        this.setVisible(true); //affiche la fenêtre
    }

    @Override
    public void actualise(){
        repaint(); // 'redessine' la fenêtre/l'actualise donc 
    }

    @Override
    public void paint(Graphics g){
        super.paint(g); // nettoie l'écran précédent
        
        // Parcourt et dessine un ovale plein pour chaque cellule vivante
        for(int x = 0; x < jeu.getxMax(); x++){
            for(int y = 0; y < jeu.getyMax(); y++){
                if(jeu.getGrilleXY(x, y).estVivante()){
                    g.fillOval(x * 10 + 10, y * 10 + 30, 8, 8);
                }
            }
        }
    }
}
