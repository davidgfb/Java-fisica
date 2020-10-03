package Fisica;

    //<editor-fold defaultstate="collapsed" desc="imports">
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.System.out;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.Timer;
//</editor-fold>

public class CaidaCirculo extends JPanel implements MouseListener,MouseMotionListener,ActionListener {
    
    //<editor-fold defaultstate="collapsed" desc="paintComponent">
    int x = 0, y = 0, r = 50, d = 2*r, tiempo = 0, tiempoCaida = 0;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(KEY_ANTIALIASING, 
                             VALUE_ANTIALIAS_ON);
        dibujaCirculoConRadio(g,d);
        dibujaPuntosCirculo(g);
    }
    //pinta un punto rojo donde toque el raton para comprobar precision
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="procedimientos">
    void dibujaCirculoConRadio(Graphics g, int d) {
        int dY=y+r;
        g.drawOval(x, y, d, d);
        g.drawLine(x+r, dY, x+d, dY);
    }
    
    boolean estaDentroCirculo(int x1,int y1, int r) { 
        //int x = p[0],y=p[1];
        x1-=x+r;
        y1-=y+r;
        //return x*x+y*y<=r*r;
        return x1*x1+y1*y1<r*r+1;
    }
    
    void dibujaPuntosCirculo(Graphics g) {        
        for (int x=0;x<getWidth();x++) {
            for (int y=0;y<getHeight();y++) {
                if (estaDentroCirculo(x,y,r)) {
                    g.drawLine(x, y, x+1, y+1);
                }
            }
        }
    }
    
    void situaCirculoPosRaton(MouseEvent e) {
        x=e.getX()-r;
        y=e.getY()-r;
        repaint();
    }
    
    void cae() {
        if (esta_ArrastrandoCirculo==false) {
            int hMin = getHeight()-d,y1=y+tiempoCaida;
            if (y1<=hMin) {
                y=y1; //y1=y+tiempo formula ley Newton
                tiempoCaida++;
            } else {
                y=hMin;
                estaColisionandoCirculo=true;
                tiempoCaida=0; //reinicio tiempoCaida 
                out.println("se ha reiniciado tiempoCaida");
            }
            repaint();
            out.println("tiempoCaida: "+tiempoCaida+" ms, y: "+y+" pxs");
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="mouse listener">
    boolean esta_ArrastrandoCirculo = false;
    @Override
    public void mousePressed(MouseEvent e) {
        if (estaDentroCirculo(e.getX(),e.getY(),r)) {
            esta_ArrastrandoCirculo = true;
            situaCirculoPosRaton(e);
        } 
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (esta_ArrastrandoCirculo) {
            esta_ArrastrandoCirculo = false;
            estaColisionandoCirculo = false;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="mouse listener sin uso">
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
//</editor-fold>
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="mouse motion listener">
    @Override
    public void mouseDragged(MouseEvent e) {
        if (esta_ArrastrandoCirculo) {
            situaCirculoPosRaton(e);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="mouse motion listener sin uso">
    @Override
    public void mouseMoved(MouseEvent e) {
    }
//</editor-fold>
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPerformed">
    boolean estaColisionandoCirculo = false;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (estaColisionandoCirculo==false) {
            cae();
        }
        tiempo++;
        out.println("tiempo: "+tiempo+" ms");
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="main">
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(800, 600);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        CaidaCirculo cC = new CaidaCirculo();
        cC.setDoubleBuffered(true); //deberia estar activado pdef
        f.add(cC);
        f.addMouseListener(cC);
        f.addMouseMotionListener(cC);
        Timer t = new Timer(17,cC); //17ms = 60fps
        t.start();
        f.setVisible(true);
    }
//</editor-fold>
}
