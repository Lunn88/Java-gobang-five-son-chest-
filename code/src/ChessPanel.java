import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessPanel extends JPanel {

    int sx = 30, sy = 30, unit = 50, gap = 30;
    BufferedImage bgImage = null;


    public ChessPanel() {
        addMouseEventListener();
        addSizeListener();
        try {
            bgImage = ImageIO.read(new File("./IMG/bgImage.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSizeListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                int w = getWidth();
                int h = getHeight();
                int min = Math.min(w, h);
                unit = (min - gap * 2) / (Model.WIDTH - 1);
                //sx = (w - unit*(Model.WIDTH - 1)) / 2;
                sy = (h - unit * (Model.WIDTH - 1)) / 2;
                repaint();
            }
        });
    }

    private void addMouseEventListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int row = (e.getY() - sy) / unit;
                if ((e.getY() - sy) % unit > unit / 2) {
                    row++;
                }

                int col = ((e.getX() - sx) / unit);
                if ((e.getX() - sx) % unit > unit / 2) {
                    col++;
                }

                Vars.c.localPutChess(row, col);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawChessPanel(g);
        drawChess(g);
    }

    private void drawChess(Graphics g) {
        for (int row = 0; row < Model.WIDTH; row++) {
            for (int col = 0; col < Model.WIDTH; col++) {
                int color = Vars.m.getChess(row, col);
                if (color == Model.BLACK) {
                    g.setColor(Color.BLACK);
                    g.fillOval(sx + unit * col - unit / 2, sy + row * unit - unit / 2, unit, unit);
                } else if (color == Model.WHITE) {
                    g.setColor(Color.WHITE);
                    g.fillOval(sx + unit * col - unit / 2, sy + row * unit - unit / 2, unit, unit);
                }
            }
        }
    }


    private void drawChessPanel(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < Model.WIDTH; i++) {
            g.drawLine(sx, sy + unit * i, sx + unit * (Model.WIDTH - 1), sy + unit * i);
            g.drawLine(sx + unit * i, sy, sx + unit * i, sy + unit * (Model.WIDTH - 1));
        }
    }

    private void drawBackgroundImage(Graphics g) {
        g.drawImage(bgImage, sx, sy, unit * (Model.WIDTH - 1), unit * (Model.WIDTH - 1),  this);
    }
}
