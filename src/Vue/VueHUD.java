package Vue;

import Modele.ModeleJeu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Vue HUD
 * @author Epulapp
 */
public class VueHUD extends JPanel implements Observer
{ 
    private JLabel bombesRestantes;
    private JPanel panelBombes;
    private JLabel labelBombes;
    //private JLabel labelChrono;
    private final ModeleJeu modele;

    public VueHUD(ModeleJeu m)
    {
        modele = m;
        initComponents();       
    }

    private void initComponents()
    {
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        panelBombes = new JPanel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/bombe2.png"));           	
        Image image = icon.getImage(); 
        bombesRestantes = new JLabel();
        //labelChrono = new JLabel();
        bombesRestantes.setPreferredSize(new Dimension(40,40));
        bombesRestantes.setText(" : " + modele.getGrille().getNbBombesRestantes() + "");       
        labelBombes = new JLabel(new ImageIcon(image));        
        panelBombes.add(new JLabel(" "),BorderLayout.CENTER);
        panelBombes.add(labelBombes,BorderLayout.CENTER);
        panelBombes.add(bombesRestantes,BorderLayout.CENTER);
        //labelChrono.setText("Temps ecoule = " + modele.getChrono().getDureeSec() + " ms");
        
        this.add(panelBombes, BorderLayout.CENTER);
        //this.add(labelChrono, BorderLayout.BEFORE_FIRST_LINE);
    }

    public void update(java.util.Observable o, Object arg) 
    {
        bombesRestantes.setText(" : " + modele.getGrille().getNbBombesRestantes()+"");           
        //labelChrono.setText("Temps ecoule = " + modele.getChrono().getDureeSec() + " ms");
    }
}