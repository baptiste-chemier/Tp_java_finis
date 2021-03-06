/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Controleur.Demineur;
import Modele.ModeleJeu;
import static Modele.ModeleJeu.etat.*;
import javax.swing.BorderFactory;

/**
 * Vue de la case
 * @author Chemier Baptiste et Nguyen Aisi
 */
public class VueCase extends JPanel implements Observer
{
    private ModeleJeu modele;
    private int id;
    private JLabel label;
    private ImageIcon[] icones;
    private int taille;
    private Color couleur;
    private boolean entered;

    /**
     * COnstructuteur
     * @param m
     * @param i
     * @param icones 
     */
    public VueCase(ModeleJeu m, int i, ImageIcon[] icones)
    {
        this.modele = m;
        this.id = i;
        this.icones = icones;
        this.taille = 30;
        this.label = new JLabel();
        this.couleur = new Color(255, 255, 255);
        this.setPreferredSize(new Dimension(taille, taille));     
        this.entered = false;
        this.addMouseListener(new java.awt.event.MouseAdapter() {         
            @Override
            public void mouseReleased(MouseEvent e) {
                caseMouseClicked(e);
            }
        });
        
        this.add(label);     
        this.setBackground(couleur);     
        this.setBorder(BorderFactory.createRaisedBevelBorder());   
    }
    
    @Override
    /**
     * Mise à jour des cases
     */
    public void update(Observable o, Object arg)
    {
        if(arg instanceof Boolean && !(boolean)arg){
            if(modele.getEtatPartie() == Perdu || modele.getEtatPartie() == Gagner)
            {
                if(modele.getGrille().getCases()[id].isPiege())
                    afficherBombe();
                else if(id == modele.getGrille().getDerniereCase() || modele.getEtatPartie() == Gagner)
                    afficherNbPieges();
                else
                    dropFlag(); 
            }
            else if(id < modele.getGrille().getCases().length && modele.getGrille().getCases()[id].isDecouverte())
            {
                if(modele.getGrille().getCases()[id].isPiege())
                    afficherBombe();
                else
                    afficherNbPieges();                
                          
            }
            else if(id<modele.getGrille().getCases().length && modele.getGrille().getCases()[id].isFlag())
                afficherDrapeau();
            else
                dropFlag();
            
            repaint();
        }
    }  
    
    /**
     * Affiche le nombre de nombe
     */
    public void afficherBombe()
    {
        label.setIcon(icones[0]);
        if(id == modele.getGrille().getDerniereCase()){
            this.setBackground(Color.white);
            label.setIcon(icones[2]);
        }
    }
    
    /**
     * Affiche un drapeau 
     */
    public void afficherDrapeau()
    {
        label.setIcon(icones[1]);
    }
   
    /**
     * Affiche le nombre de case piégée
     */
    public void afficherNbPieges()
    {
        int nbPieges = modele.getGrille().getNbPieges(modele.getGrille().getCases()[id]);
        label.setText(Integer.toString(nbPieges));  
        label.setIcon(null);
        this.setBackground(new Color(206,206,206));
                
        if(nbPieges == 0)
        {
            label.setText("");
        }
        else if(nbPieges == 1)
        {
            label.setForeground(Color.blue);
        }
        else if(nbPieges == 2)
        {
            label.setForeground(Color.green);
        }
        else if(nbPieges == 3)
        {
            label.setForeground(Color.red);
        }       
        else if(nbPieges == 4)
        {
            label.setForeground(Color.orange);
        }
        else if(nbPieges == 5)
        {
            label.setForeground(Color.cyan);
        }
        else if(nbPieges == 6)
        {
            label.setForeground(Color.pink);
        }
        else if(nbPieges == 7)
        {
            label.setForeground(Color.magenta);
        }
        else if(nbPieges == 8)
        {
            label.setForeground(Color.black);
        }
        else
        {
            label.setForeground(Color.black);
        }      
    }
    
    /**
     * Supprime un drapeau
     */
    public void dropFlag()
    {
        label.setIcon(null);
    }
    
    /**
     * Gère le click et appelle la fonction correspondant
     * @param e 
     */
    public void caseMouseClicked(MouseEvent e)
    {
        Demineur c = new Demineur(modele);
        if(SwingUtilities.isLeftMouseButton(e))
            c.leftClick(id);
        if(SwingUtilities.isRightMouseButton(e)) 
            c.rightClick(id);
    }
}
