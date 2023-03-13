import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReplayChessFrame extends JFrame {

    public int count = 0;
    JPanel btnPanel = new JPanel();
    JButton next = new JButton("下一步");
    JButton last = new JButton("上一步");

    public ReplayChessFrame(){
        this.setTitle("複盤");
        this.setLocation(100, 100);
        this.setSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());
        this.setVisible(false);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        btnPanel.setLayout(new GridLayout(1, 2, 10, 10));
        btnPanel.add(last);
        btnPanel.add(next);
        this.getContentPane().add(Vars.rcp, BorderLayout.CENTER);
        this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    public void startReplay(ArrayList<ChessPoint> history){
        this.setVisible(true);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(count >= Vars.m.getChessCount()){
                    return;
                }
                Vars.rcp.rePutChess(history.get(count).row, history.get(count).col, history.get(count).color);
                Vars.rcp.repaint();
                count++;
            }
        });
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(count <= 0){
                    return;
                }
                count--;
                Vars.rcp.rePutChess(history.get(count).row, history.get(count).col, Model.SPACE);
                Vars.rcp.repaint();
            }
        });
    }

}
