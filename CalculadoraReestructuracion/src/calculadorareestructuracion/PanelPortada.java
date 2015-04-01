package calculadorareestructuracion;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Edgar
 */
public class PanelPortada extends JPanel{
    
    public PanelPortada(){
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        int height = this.getSize().height;
        int width = this.getSize().width;
        ImageIcon img = new ImageIcon(getClass().getResource("/imagen/PortadaPNG.png"));
        
        g.drawImage(img.getImage(), 0, 0, width, height, null);
        super.paintComponents(g);
    }
    
}
