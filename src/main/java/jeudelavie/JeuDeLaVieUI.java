package jeudelavie;

import javax.swing.*;
import java.awt.*;

public class JeuDeLaVieUI extends JFrame implements Observateur {
    private JeuDeLaVie jeu;
    private GrillePanel grillePanel; // panneau o`u on dessine
    private Timer timer; //chrono pour l'animation du panneau

    public JeuDeLaVieUI(JeuDeLaVie jeu){
        this.jeu = jeu;
        this.setTitle("Le Jeu de la Vie");
        this.setLayout(new BorderLayout()); // fenêtre divisée en zones
 
        // Zone dessin (au milieu)
        grillePanel = new GrillePanel();
        this.add(grillePanel, BorderLayout.CENTER);

        // Zone de contrôle (en bas)
        JPanel controlPanel = new JPanel();
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");
        JButton btnNext = new JButton("Avancer (1 génération)");

        // Slider fluctuant entre 10 et 1000ms mais mit à défaut à 1000ms 
        JSlider speedSlider = new JSlider(10, 1000, 100);

        // Timer 
        timer = new Timer(speedSlider.getValue(), e -> jeu.calculerGenerationSuivante());

        // Actions des boutons / Listeners

        btnPlay.addActionListener(e -> timer.start());
        btnPause.addActionListener(e -> timer.stop());

        // Bouton next qui marche que si le jeu est en pause 
        btnNext.addActionListener(e -> {
            if(!timer.isRunning()){
                jeu.calculerGenerationSuivante();
            }
        });

        // Si on bouge le slider cela change la vitesse du timer
        speedSlider.addChangeListener(e -> timer.setDelay(speedSlider.getValue()));

        // ajout sur le panneau du bas 
        controlPanel.add(btnPlay);
        controlPanel.add(btnPause);
        controlPanel.add(btnNext);
        controlPanel.add(new JLabel("Vitesse de l'animation"));
        controlPanel.add(speedSlider);
        this.add(controlPanel, BorderLayout.SOUTH);

        // Config de la fenêtre 
        this.setSize(jeu.getxMax() * 10 + 20, jeu.getyMax() * 10 + 80);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actualise(){
        grillePanel.repaint();
    }

    // classe interner pour le dessin
    private class GrillePanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLACK);

            for(int x = 0; x < jeu.getxMax(); x++){
                for(int y = 0; y < jeu.getyMax(); y++){
                    if(jeu.getGrilleXY(x, y).estVivante()){
                        g.fillOval(x * 10, y * 10, 8, 8);
                    }
                }
            }
        }
    }
}
