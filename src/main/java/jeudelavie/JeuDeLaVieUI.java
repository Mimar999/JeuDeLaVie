package jeudelavie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

public class JeuDeLaVieUI extends JFrame implements Observateur {
    private JeuDeLaVie jeu;
    private GrillePanel grillePanel; // panneau o`u on dessine
    private Timer timer; //chrono pour l'animation du panneau

    private Color[][] couleurs;
    private boolean modeMulticolore = false; // Noir par défaut

    public JeuDeLaVieUI(JeuDeLaVie jeu){
        this.jeu = jeu;
        this.setTitle("Le Jeu de la Vie");
        this.setLayout(new BorderLayout()); // fenêtre divisée en zones
        
        // initialisation des couleurs
        couleurs = new Color[jeu.getxMax()][jeu.getyMax()];
        for(int x = 0; x < jeu.getxMax(); x++){
            for(int y = 0; y < jeu.getyMax(); y++){
                float teinteAleatoire = (float) Math.random();
                couleurs[x][y] = Color.getHSBColor(teinteAleatoire, 0.8f, 0.9f);
            }
        }

        // Zone dessin (au milieu)
        grillePanel = new GrillePanel();
        this.add(grillePanel, BorderLayout.CENTER);

        // Zone de contrôle (en bas)
        JPanel controlPanel = new JPanel();
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");
        JButton btnNext = new JButton("Avancer (1 génération)");
        JButton btnClear = new JButton("Effacer grille");
        JButton btnPlaneur = new JButton("Planeur");
        JButton btnCouleur = new JButton("Couleurs OFF");
    
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

        btnClear.addActionListener(e -> {
            timer.stop();
            jeu.viderGrille();
        });

        btnPlaneur.addActionListener(e -> {
            timer.stop();
            jeu.dessinerPlaneur();
        });

        btnCouleur.addActionListener(e-> {
            modeMulticolore = !modeMulticolore;
            btnCouleur.setText(modeMulticolore ? "Couleurs : ON" : "Couleurs OFF");
            actualise();
        });
        // Si on bouge le slider cela change la vitesse du timer
        speedSlider.addChangeListener(e -> timer.setDelay(speedSlider.getValue()));

        // ajout sur le panneau du bas 
        controlPanel.add(btnPlay);
        controlPanel.add(btnPause);
        controlPanel.add(btnNext);
        controlPanel.add(new JLabel("Vitesse de l'animation"));
        controlPanel.add(speedSlider);
        controlPanel.add(btnClear);
        controlPanel.add(btnPlaneur);
        controlPanel.add(btnCouleur);

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

        public GrillePanel(){
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    int grilleX = e.getX() / 10;
                    int grilleY = e.getY() / 10;

                    jeu.inverserEtatCellule(grilleX, grilleY);
                }
            });
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            for(int x = 0; x < jeu.getxMax(); x++){
                for(int y = 0; y < jeu.getyMax(); y++){
                    if(jeu.getGrilleXY(x, y).estVivante()){
                        if(modeMulticolore){
                             g.setColor(couleurs[x][y]);
                        }
                        else g.setColor(Color.BLACK);
                        
                        g.fillOval(x * 10, y * 10, 8, 8);
                    }
                }
            }
        }
    }
}
