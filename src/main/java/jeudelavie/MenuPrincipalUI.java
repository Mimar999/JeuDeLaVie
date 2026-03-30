package jeudelavie;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalUI extends JFrame {
    public MenuPrincipalUI(){
        this.setTitle("Configuration | Jeu de la Vie");
        this.setSize(350, 300);
        this.setLayout(new GridLayout(5, 1, 10, 10)); // grille de 4 lignes
        this.setLocationRelativeTo(null); // centre la fenêtre sur l'écran

        // titre
        JLabel lblTitre = new JLabel("Paramètres", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(lblTitre);

        // Configuration de la largeur de la grille

        JPanel pnlX = new JPanel();
        pnlX.add(new JLabel("Largeur de la grille :"));
        JTextField txtX = new JTextField("100", 5);
        pnlX.add(txtX);
        this.add(pnlX);

        // COnfiguration de la longueur de la grille
        JPanel pnlY = new JPanel();
        pnlY.add(new JLabel("Hauteur de la grille :"));
        JTextField txtY = new JTextField("60", 5);
        pnlY.add(txtY);
        this.add(pnlY);

        // menu déroulant pour les règles (highlife, day and night)
        JPanel pnlRegles = new JPanel();
        pnlRegles.add(new JLabel("Règles du jeu : "));
        String[] choixRegles = {"Classique (Conway)", "HighLife", "Day & Night"};
        JComboBox<String> comboRegles = new JComboBox<>(choixRegles);
        pnlRegles.add(comboRegles);
        this.add(pnlRegles);

        // Bouton start
        JButton btnStart = new JButton("LANCER LA SIMULATON");
        btnStart.setBackground(new Color(46, 204, 113));
        btnStart.setForeground(Color.WHITE);
        btnStart.setFocusPainted(false);

        btnStart.addActionListener(e -> {
            try{
                int x = Integer.parseInt(txtX.getText());
                int y = Integer.parseInt(txtY.getText());
                
                if(x < 10 || y < 10) throw new Exception ("Taille trop petite.");

                // Lancement du jeu 
                JeuDeLaVie jeu = new JeuDeLaVie(x, y);

                // application de la règle choisie
                String regleChoisie = (String) comboRegles.getSelectedItem();
                if(regleChoisie.equals("HighLife")){
                    jeu.setVisiteur(new VisiteurHighLife(jeu));
                    jeu.setMessageSysteme("Mode : HighLife");
                }
                else if(regleChoisie.equals("Day & Night")){
                    jeu.setVisiteur(new VisiteurDayAndNight(jeu));
                    jeu.setMessageSysteme("Mode : Day & Night");
                }
                else{
                    jeu.setMessageSysteme("Mode : Classique");
                }
                
                
                ObservateurConsole console = new ObservateurConsole(jeu);
                jeu.attacheObservateur(console);
                
                JeuDeLaVieUI fenetre = new JeuDeLaVieUI(jeu);
                jeu.attacheObservateur(fenetre);
                
                // Ferme le menu d'accueil
                this.dispose();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres entiers valides (min 10).", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel pnlBtn = new JPanel();
        pnlBtn.add(btnStart);
        this.add(pnlBtn);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
