import javax.swing.*;
import java.awt.*;

public class EastPanel extends JPanel {

    //從Vars定義並調用就不行
    BtnPanel bp = new BtnPanel();
    JLabel time = new JLabel();
    TimeThread timeThread = new TimeThread();
    int sec = 100;

    public class TimeThread extends Thread{
        @Override
        public void run() {
            while(Vars.c.runing){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time.setText(Integer.toString(sec--));

                if(sec <= 0 ){
                    Vars.c.surrender();
                }
            }
        }
    }

    public void begin(){
        TimeThread timeThread = new TimeThread();
        timeThread.start();
    }

    public EastPanel() {
        this.setLayout(new BorderLayout());
        time.setFont(new Font("標楷體", Font.BOLD, 72));
        this.add(time, BorderLayout.NORTH);
        timeThread.start();
        this.add(bp, BorderLayout.SOUTH);
    }
}
