import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {

    BufferedImage Image = null;

    public Main() throws HeadlessException, IOException {

        JPanel jp = (JPanel) this.getContentPane();
        jp.add(Vars.cp, BorderLayout.CENTER);
        jp.add(Vars.np, BorderLayout.NORTH);
        jp.add(Vars.ep, BorderLayout.EAST);
        jp.add(Vars.chatp, BorderLayout.SOUTH);

        this.setSize(700, 900);
        this.setLocation(100, 100);
        this.setTitle("五子棋");
        Image = ImageIO.read(new File("./IMG/3.jpeg"));
        this.setIconImage(Image);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public static void main(String[] args) throws IOException {
        Main m = new Main();
    }

}
