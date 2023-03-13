
import javax.swing.*;

public class Controler {

    int localColor = Model.BLACK;
    int remoteColor = Model.WHITE;
    boolean oneplayer = false;
    boolean openDoor = false;
    boolean isOver = false;
    boolean runing = false;

    public void localPutChess(int row, int col) {
        if (!openDoor) {
            return;
        }
        boolean success = Vars.m.putChess(row, col, localColor);
        if (success) {
            Vars.cp.repaint();
            if (oneplayer) {
                localColor = -localColor;
            }
            else {
                Vars.n.sendChess(row, col);
                Vars.m.seeIfCanRedo();
                openDoor = false;
            }
            int winner = Vars.m.whoWin();
            whoWin(winner);
            runing = false;
        }
    }

    public void remotePutChess(int row, int col) {
        boolean success = Vars.m.putChess(row, col, remoteColor);
        if (success) {
            Vars.cp.repaint();
            int winner = Vars.m.whoWin();
            whoWin(winner);
            openDoor = true;
            runing = true;
            Vars.ep.begin();
        }
        Vars.m.seeIfCanRedo();
    }

    public void whoWin(int winner) {
        if (oneplayer) {
            Object[] overOptions = {"給爺開！", "不玩了..."};
            if (winner == Model.BLACK) {
                JOptionPane.showMessageDialog(null, "黑棋勝", "勝負已分", JOptionPane.INFORMATION_MESSAGE);
                int option = JOptionPane.showOptionDialog(null, "是否開啟下一局？", "棋局結束", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, overOptions, overOptions[0]);
                gameOver();
                onePlayerContinue(option);
            } else if (winner == Model.WHITE) {
                JOptionPane.showMessageDialog(null, "白棋勝", "勝負已分", JOptionPane.INFORMATION_MESSAGE);
                int option = JOptionPane.showOptionDialog(null, "是否開啟下一局？", "棋局結束", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, overOptions, overOptions[0]);
                gameOver();
                onePlayerContinue(option);
            }
        }
        else{
            Object[] overOptions = {"來！", "不玩了..."};
            if (winner == Model.BLACK) {
                JOptionPane.showMessageDialog(null, "黑棋勝", "勝負已分", JOptionPane.INFORMATION_MESSAGE);
                int option = JOptionPane.showOptionDialog(null, "是否再來一局？", "棋局結束", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, overOptions, overOptions[0]);
                ifContinue(option);
            } else if (winner == Model.WHITE) {
                JOptionPane.showMessageDialog(null, "白棋勝", "勝負已分", JOptionPane.INFORMATION_MESSAGE);
                int option = JOptionPane.showOptionDialog(null, "是否開啟下一局？", "棋局結束", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, overOptions, overOptions[0]);
                ifContinue(option);
            }
        }
    }

    private void onePlayerContinue(int option) {
        if (option == JOptionPane.YES_OPTION) {
            Vars.np.changeListenBtnState(true);
            Vars.np.changeConnectBtnState(true);
        } else if (option == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    private void ifContinue(int option) {
        Vars.m.cleanChessPanel();
        Vars.cp.repaint();
        if (option == JOptionPane.YES_OPTION) {
            isOver = true;
            Vars.ep.sec = 100;
            Vars.np.changeListenBtnState(false);
            Vars.np.changeConnectBtnState(false);
        } else if (option == JOptionPane.NO_OPTION) {
            Vars.n.sendLeftMessage();
            System.exit(0);
        }
    }

    public void gameOver() {
        isOver = true;
        Vars.m.cleanChessPanel();
        Vars.cp.repaint();
        Vars.np.changeListenBtnState(true);
        Vars.np.changeConnectBtnState(true);
    }

    public void beginListen() {
        Vars.n.listen();
        Vars.np.changeListenBtnState(false);
        Vars.np.changeConnectBtnState(false);
        openDoor = true;
        oneplayer = false;
        isOver = false;
        Vars.ep.bp.changeOverBtnState(false);
        Vars.ep.bp.changeSurrenderBtnState(true);
    }

    public void beginConnect(String ip) {
        Vars.n.connect(ip);
        Vars.np.changeListenBtnState(false);
        Vars.np.changeConnectBtnState(false);
        openDoor = false;
        oneplayer = false;
        isOver = false;
        localColor = Model.WHITE;
        remoteColor = Model.BLACK;
        Vars.ep.bp.changeOverBtnState(false);
        Vars.ep.bp.changeSurrenderBtnState(true);
    }

    public void start() {
        openDoor = true;
        oneplayer = true;
        Vars.m.start();
        Vars.np.changeListenBtnState(false);
        Vars.np.changeConnectBtnState(false);
        Vars.ep.bp.changeOverBtnState(true);
        Vars.ep.bp.changeSurrenderBtnState(false);
    }

    public void redo() {
        if (oneplayer) {
            if (Vars.m.redo()) {
                localColor = -localColor;
                Vars.cp.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "悔棋無效！");
            }
        } else {
            if (Vars.m.redo()) {
                Vars.cp.repaint();
                openDoor = false;
                Vars.n.sendRedoMessage();
                Vars.m.seeIfCanRedo();
            } else {
                JOptionPane.showMessageDialog(null, "悔棋無效！");
            }
        }
    }

    public void netRedo() {
        if (Vars.m.redo()) {
            runing = true;
            Vars.ep.begin();
            Vars.cp.repaint();
            openDoor = true;
            Vars.m.seeIfCanRedo();
        } else {
            JOptionPane.showMessageDialog(null, "悔棋無效！");
        }
    }

    public void askCanRedo() {
        Vars.n.sendAskRedoMessage();
    }

    public boolean remoteAskCanRedo() {
        Vars.c.runing = false;
        Object[] redoOptions = {"讓你", "想得美"};
        int option = JOptionPane.showOptionDialog(null, "對手詢問是否能悔棋？", "有人想賴皮囉！", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, redoOptions, redoOptions[0]);
        if (option == JOptionPane.YES_OPTION) {
            return true;
        } else {
            Vars.c.runing = true;
            Vars.ep.begin();
            return false;
        }
    }

    public void replay() {
        if (Vars.m.replay()) {
            openDoor = false;
        } else {
            JOptionPane.showMessageDialog(null, "沒有上一局的資料");
        }
    }

    public void over() {
        gameOver();
    }

    public void surrender() {
        whoWin(remoteColor);
        gameOver();
        Vars.n.sendSurrenderMessage();
    }

    public void netSurrender() {
        whoWin(localColor);
        gameOver();
    }

    public void sendChatMessage(String text) {
        Vars.n.sendChatMessage(text);
        Vars.chatp.addChatMessage("Meeeee:" + text);
    }

    public void remoteSaySomething(String line) {
        Vars.chatp.addChatMessage("Remote:" + line);
    }

    public void remoteHasLeft() {
        JOptionPane.showMessageDialog(null, "對方離開了...", "對手逃跑了！", JOptionPane.INFORMATION_MESSAGE);
        openDoor = false;
    }
}
