import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NorthPanel extends JPanel {

    private JTextField ipTF = new JTextField(20);
    private JButton listenBtn = new JButton("listen");
    private JButton connectBtn = new JButton("connect");
    public void changeListenBtnState(boolean enable){
        listenBtn.setEnabled(enable);
    }
    public void changeConnectBtnState(boolean enable){
        connectBtn.setEnabled(enable);
    }

    public NorthPanel() {
        setLayout(new FlowLayout());
        add(listenBtn);
        add(ipTF);
        ipTF.setText("localhost");
        add(connectBtn);
        addListener();
    }

    private void addListener() {
        listenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.c.beginListen();
            }
        });

        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipTF.getText();
                Vars.c.beginConnect(ip);
            }
        });
    }
}
