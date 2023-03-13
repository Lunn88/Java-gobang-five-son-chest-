import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnexpectedException;

public class Net {

    public static final int PORT = 9999;
    private Socket s = null;
    protected PrintWriter out;
    protected BufferedReader in;

    public void listen() {
        new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(PORT);
                    s = ss.accept();
                    out = new PrintWriter(s.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    startReadThread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void connect(final String ip) {
        new Thread() {
            @Override
            public void run() {
                try {
                    s = new Socket(ip, PORT);
                    out = new PrintWriter(s.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    startReadThread();
                } catch (UnexpectedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected void startReadThread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String line = in.readLine();
                        if (line == null) {
                            return;
                        }
                        parseMessage(line);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    protected void parseMessage(String line) {
        if (line.startsWith("putchess:")) {
            parsePutChess(line);
        } else if (line.startsWith("chat:")) {
            parseChat(line);
        } else if (line.startsWith("surrender:")) {
            parseSurrender(line);
        } else if (line.startsWith("can redo?")) {
            parseAskRedo(line);
        } else if (line.startsWith("redo:")) {
            parseRedo(line);
        } else if(line.startsWith("has left:")){
            parseLeft(line);
        }
    }

    private void parseLeft(String line) {
        Vars.c.remoteHasLeft();
    }

    public void sendChess(int row, int col) {
        out.println("putchess:" + row + "," + col);
    }

    private void parsePutChess(String line) {
        line = line.substring("putchess:".length());
        String[] he = line.split(",");
        int row = Integer.parseInt(he[0]);
        int col = Integer.parseInt(he[1]);
        Vars.c.remotePutChess(row, col);
    }

    public void sendChatMessage(String text) {
        out.println("chat:" + text);
    }

    private void parseChat(String line) {
        line = line.substring("chat:".length());
        Vars.c.remoteSaySomething(line);
    }

    public void sendAskRedoMessage() {
        out.println("can redo?");
    }

    private void parseAskRedo(String line) {
        if (Vars.c.remoteAskCanRedo()) {
            Vars.c.redo();
        }
    }

    public void sendRedoMessage() {
        out.println("redo:");
    }

    private void parseRedo(String line) {
        Vars.c.netRedo();
    }

    public void sendSurrenderMessage() {
        out.println("surrender:");
    }

    private void parseSurrender(String line) {
        Vars.c.netSurrender();
    }

    public void sendLeftMessage() {
        out.println("has left:");
    }
}
