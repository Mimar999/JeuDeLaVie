package jeudelavie;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class JeuDeLaVieUI extends JFrame implements Observateur {
    private JeuDeLaVie jeu;
    private GrillePanel grillePanel; // panneau o`u on dessine
    private Timer timer; //chrono pour l'animation du panneau

    private Color[][] couleurs;
    private boolean modeMulticolore = false; // Noir par défaut

    private JLabel lblGeneration;
    private JLabel lblVivantes;
    private JLabel lblMessage;

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
        JButton btnCouleur = new JButton("Couleurs OFF");
        JButton btnMenu = new JButton("Retour Menu");

        // Slider fluctuant entre 10 et 1000ms mais mit à défaut à 1000ms 
        JSlider speedSlider = new JSlider(10, 1000, 100);


        // Timer 
        timer = new Timer(speedSlider.getValue(), e -> jeu.calculerGenerationSuivante());

        // Actions des boutons / Listeners

        btnPlay.addActionListener(e -> timer.start());
        btnPause.addActionListener(e -> timer.stop());

        // Catalogue / ComboBox pour regrouper les boutons
        String[] modeles = {"-- Catalogue --", "Vider la grille", "Planeur Simple", "Canon de Gosper"};
        JComboBox<String> comboModeles = new JComboBox<>(modeles);

        comboModeles.addActionListener(e -> {
            String choix = (String) comboModeles.getSelectedItem();
            if(choix.equals("Vider la grille")){
                timer.stop();
                jeu.viderGrille();
            }
            else if(choix.equals("Planeur Simple")){
                timer.stop();
                jeu.dessinerPlaneur();
            }
            else if(choix.equals("Canon de Gosper")){
                timer.stop();
                jeu.dessinerCanonGosper();
            }
            // Remise du titre par défaut sur le menu déroulant
            comboModeles.setSelectedIndex(0);
        });

        // Bouton next qui marche que si le jeu est en pause 
        btnNext.addActionListener(e -> {
            if(!timer.isRunning()){
                jeu.calculerGenerationSuivante();
            }
        });

        btnCouleur.addActionListener(e-> {
            modeMulticolore = !modeMulticolore;
            btnCouleur.setText(modeMulticolore ? "Couleurs : ON" : "Couleurs OFF");
            actualise();
        });

        btnMenu.addActionListener(e -> {
            timer.stop();
            this.dispose();
            new MenuPrincipalUI(); // renvoie au menu d'accueil
        });

        // Si on bouge le slider cela change la vitesse du timer
        speedSlider.addChangeListener(e -> timer.setDelay(speedSlider.getValue()));


        // ajout sur le panneau du bas 
        controlPanel.add(btnPlay);
        controlPanel.add(btnPause);
        controlPanel.add(btnNext);
        controlPanel.add(new JLabel("Vitesse de l'animation"));
        controlPanel.add(speedSlider);
        controlPanel.add(btnCouleur);
        controlPanel.add(comboModeles);
        controlPanel.add(btnMenu);
        
        this.add(controlPanel, BorderLayout.SOUTH);

        // Panneau latéral
        JPanel rightPanel = new JPanel();
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        rightPanel.setPreferredSize(new Dimension(220, 0));

        lblGeneration = new JLabel("Génération : 0");
        lblVivantes = new JLabel("Vivantes : 0");
        lblMessage = new JLabel("<html><b>Message système :</b><br>Prêt.</html>");

        rightPanel.add(new JLabel("<html><h3> STATISTIQUES</h3></html>"));
        rightPanel.add(Box.createVerticalStrut(10)); // Espace vide
        rightPanel.add(lblGeneration);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(lblVivantes);
        rightPanel.add(Box.createVerticalStrut(30)); // Grand espace vide
        rightPanel.add(new JLabel("<html><h3> SYSTÈME</h3></html>"));
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(lblMessage);

        this.add(rightPanel, BorderLayout.EAST);

        // Config de la fenêtre 
        this.setSize(jeu.getxMax() * 10 + 20, jeu.getyMax() * 10 + 80);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actualise(){
        lblGeneration.setText("Génération : " + jeu.getGeneration());
        lblVivantes.setText("Cellules vivantes : " + jeu.getNbCellulesVivantes());
        
        lblMessage.setText("<html><b>Message système :</b><br><font color='red'>" + jeu.getMessageSysteme() + "</font></html>");
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
