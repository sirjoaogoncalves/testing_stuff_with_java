import java.awt.event.*;
import java.awt.*;
import java.applet.*;
import java.lang.*;
import java.lang.reflect.*;

public class Editor extends Applet implements MouseListener, MouseMotionListener, ActionListener {
    int nPontos = 0;
    objecto pontos[];
    Color cor;
    Button b1, b2, b3, b4, b5, b6;
    Button a1, a2, a3, a4, a5;
    int x1, y1, x2, y2;

    public Editor() {
        setLayout(new BorderLayout());

        Panel p = new Panel(new FlowLayout());
        Panel p2 = new Panel(new FlowLayout());

        b1 = new Button("Amarelo");
        b2 = new Button("Azul");
        b3 = new Button("Verde");
        b4 = new Button("Vermelho");
        b5 = new Button("Cinzento");
        b6 = new Button("Limpar");

        a1 = new Button("Linhas");
        a2 = new Button("Oval");
        a3 = new Button("Oval Cheia");
        a4 = new Button("Rectangulo");
        a5 = new Button("Rectangulo Cheio");

        b1.setBackground(Color.yellow);
        b2.setBackground(Color.blue);
        b3.setBackground(Color.green);
        b4.setBackground(Color.red);
        b5.setBackground(Color.gray);

        add(p, BorderLayout.SOUTH);
        add(p2, BorderLayout.NORTH);

        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);
        p.add(b6);

        p2.add(a1);
        p2.add(a2);
        p2.add(a3);
        p2.add(a4);
        p2.add(a5);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        a1.addActionListener(this);
        a2.addActionListener(this);
        a3.addActionListener(this);
        a4.addActionListener(this);
        a5.addActionListener(this);

        Color c = new Color(0xFFAA18);
        setBackground(c);
        addMouseMotionListener(this);
        addMouseListener(this);
        nPontos = 0;
        pontos = new objecto[100];
        cor = Color.yellow;
        pontos[nPontos] = new Linha();
    }

    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        pontos[nPontos - 1].recoloca(x2, y2);
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        e.consume();
        x1 = e.getX();
        y1 = e.getY();
        x2 = x1;
        y2 = y1;
        pontos[nPontos++].recoloca(x1, x2, y1, y2, cor);
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        e.consume();
        try {
            Class<?> c = pontos[nPontos - 1].getClass();
            Constructor<?> cons = c.getConstructors()[0];
            Object param[] = new Object[]{};
            pontos[nPontos] = (objecto) cons.newInstance(param);
        } catch (IllegalAccessException el) {
            System.out.println(el);
        } catch (InstantiationException el) {
            System.out.println(el);
        } catch (InvocationTargetException el) {
            System.out.println(el);
        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (((Button) e.getSource()).getLabel().equals("Amarelo"))
            cor = Color.yellow;
        if (((Button) e.getSource()).getLabel().equals("Azul"))
            cor = Color.blue;
        if (((Button) e.getSource()).getLabel().equals("Verde"))
            cor = Color.green;
        if (((Button) e.getSource()).getLabel().equals("Vermelho"))
            cor = Color.red;
        if (((Button) e.getSource()).getLabel().equals("Cinzento"))
            cor = Color.gray;
        if (((Button) e.getSource()).getLabel().equals("Limpar"))
            nPontos = 0;
        if (((Button) e.getSource()).getLabel().equals("Linhas"))
            pontos[nPontos] = new Linha();
        if (((Button) e.getSource()).getLabel().equals("Oval"))
            pontos[nPontos] = new Oval();
        if (((Button) e.getSource()).getLabel().equals("Oval Cheia"))
            pontos[nPontos] = new OvalCheia();
        if (((Button) e.getSource()).getLabel().equals("Rectangulo"))
            pontos[nPontos] = new Rectangulo();
        if (((Button) e.getSource()).getLabel().equals("Rectangulo Cheio"))
            pontos[nPontos] = new RectanguloCheio();
        repaint();
    }

    public void paint(Graphics g) {
        for (int i = 0; i < nPontos; i++)
            pontos[i].desenha(g);
    }
}

abstract class objecto {
    int x, y, l, a;
    int x1, y1, x2, y2;
    Color cor;

    public objecto() {
        x1 = y1 = x2 = y2 = 0;
        cor = Color.black;
    }

    abstract void desenha(Graphics g);

    public void atualiza() {
        if (x1 < x2) x = x1;
        else x = x2;
        if (y1 < y2) y = y1;
        else y = y2;
        l = Math.abs(x1 - x2);
        a = Math.abs(y1 - y2);
    }

    public void recoloca(int x1, int y1, int x2, int y2, Color c) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        cor = c;
    }

    public void recoloca(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
    }
}

class Linha extends objecto {
    public Linha() {
        super();
    }

    public void desenha(Graphics g) {
        g.setColor(cor);
        g.drawLine(x1, y1, x2, y2);
    }
}

class Oval extends objecto {
    public Oval() {
        super();
    }

    public void desenha(Graphics g) {
        g.setColor(cor);
        super.atualiza();
        g.drawOval(x, y, l, a);
    }
}

class OvalCheia extends objecto {
    public OvalCheia() {
        super();
    }

    public void desenha(Graphics g) {
        g.setColor(cor);
        super.atualiza();
        g.fillOval(x, y, l, a);
    }
}

class Rectangulo extends objecto {
    public Rectangulo() {
        super();
    }

    public void desenha(Graphics g) {
        g.setColor(cor);
        super.atualiza();
        g.drawRect(x, y, l, a);
    }
}

class RectanguloCheio extends objecto {
    public RectanguloCheio() {
        super();
    }

    public void desenha(Graphics g) {
        g.setColor(cor);
        super.atualiza();
        g.fillRect(x, y, l, a);
    }
  
}

