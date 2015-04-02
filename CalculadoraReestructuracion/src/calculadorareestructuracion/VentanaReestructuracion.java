package calculadorareestructuracion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Edgar
 */
public class VentanaReestructuracion extends JFrame implements ActionListener {
    
    private JMenuItem portadaMI;
    private JMenuItem creditosMI;
    private JMenuItem salirMI;
    
    public VentanaReestructuracion(){
        this.setSize(560, 620);
        addElementos();
        this.setResizable(false);
        this.setLocation(400, 100);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        
    }
    
    public final void addElementos(){
        JMenuBar menu = new JMenuBar();
        JMenu portadaMenu  = new JMenu("Portada");
        portadaMI = new JMenuItem("Ir a la portada");
        salirMI = new JMenuItem("Salir");
        creditosMI = new JMenuItem("Créditos");
        
        setJMenuBar(menu);
        menu.add(portadaMenu);
        portadaMenu.add(portadaMI);
        portadaMenu.add(creditosMI);
        portadaMenu.add(salirMI);
        
        portadaMI.addActionListener(this);
        salirMI.addActionListener(this);
        creditosMI.addActionListener(this);
        
        PanelReestructuracion panel = new PanelReestructuracion();
        this.add(panel);
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        
        if (o == portadaMI) {
            VentanaPortada f = new VentanaPortada();
            f.setVisible(true);
            this.dispose(); 
        }
        
        if (o == salirMI) {
            System.exit(0);
        }
              
        if (o == creditosMI) {
            Creditos creditos = new Creditos(); 
        }
              
    }
    
   
}
