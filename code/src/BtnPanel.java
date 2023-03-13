import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtnPanel extends JPanel {
    public JButton startBtn = new JButton("單人開局");
    public JButton redoBtn = new JButton("悔棋");
    public JButton replayBtn = new JButton("複盤");
    public JButton overBtn = new JButton("結束此局");
    public JButton surrenderBtn = new JButton("投降");

    public BtnPanel() {
        this.setLayout(new GridLayout(5, 1, 5, 25));
        startBtn.setPreferredSize(new Dimension(110, 55));
        redoBtn.setPreferredSize(new Dimension(110, 55));
        replayBtn.setPreferredSize(new Dimension(110, 55));
        this.add(startBtn);
        this.add(redoBtn);
        this.add(replayBtn);
        this.add(overBtn);
        this.add(surrenderBtn);
        surrenderBtn.setEnabled(false);
        addListener();
    }

    private void addListener() {

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.c.start();
            }
        });

        redoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Vars.c.oneplayer) {
                    Vars.c.redo();
                } else {
                    Vars.c.askCanRedo();
                }
            }
        });

        replayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.c.replay();
            }
        });

        overBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.c.over();
            }
        });

        surrenderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.c.surrender();
            }
        });
    }

    public void changeOverBtnState(boolean b) {
        overBtn.setEnabled(b);
    }

    public void changeSurrenderBtnState(boolean b) {
        surrenderBtn.setEnabled(b);
    }

    public void setRedoBtnState(boolean b) {
        redoBtn.setEnabled(b);
    }
}
