import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatPanel extends JPanel {

    JTextArea box = new JTextArea();
    JTextField input = new JTextField(50);
    JButton sendBtn = new JButton("send");
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd,  HH:mm:ss , ");

    public void addChatMessage(String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                box.append(dateFormat.format(date) + msg + "\n");
            }
        });
    }

    public ChatPanel(){
        box.setPreferredSize(new Dimension(200, 200));
        input.setPreferredSize(new Dimension(100, 50));
        setLayout(new BorderLayout());
        add(input, BorderLayout.NORTH);
        add(new JScrollPane(box), BorderLayout.CENTER);
        add(sendBtn, BorderLayout.SOUTH);
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vars.c.sendChatMessage(input.getText());
                input.setText("");
            }
        });
    }

}
