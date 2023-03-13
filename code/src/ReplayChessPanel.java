import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReplayChessPanel extends JPanel {

    int sx = 10, sy = 10, unit = 50, gap = 10;
    int[][] replayData = new int [Model.WIDTH][Model.WIDTH];
    BufferedImage bgImage = null;

    public ReplayChessPanel() {
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
                sx = (w - unit*(Model.WIDTH - 1)) / 2;
                sy = (h - unit * (Model.WIDTH - 1)) / 2;
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawChessPanel(g);
        reDrawChess(g);
    }

    public void rePutChess(int row, int col, int color){
        replayData[row][col] = color;
    }

    private void reDrawChess(Graphics g) {
        for (int row = 0; row < Model.WIDTH; row++) {
            for (int col = 0; col < Model.WIDTH; col++) {
                int color = getChess(row, col);
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

    public int getChess(int row, int col){
        return replayData[row][col];
    }

}
