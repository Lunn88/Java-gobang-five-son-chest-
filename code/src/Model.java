import java.util.ArrayList;

public class Model {
    public static final int BLACK = 1;
    public static final int WHITE = -1;
    public static final int SPACE = 0;
    public static final int WIDTH = 19;
    private ArrayList<ChessPoint> history = new ArrayList<ChessPoint>();
    private int[][] data = new int [WIDTH][WIDTH];
    private int lastRow, lastCol, lastColor;

    public boolean putChess(int row, int col, int color){
        if(row >= 0 && row < WIDTH && col >=0 && col < WIDTH){
            if(data[row][col] == Model.BLACK || data[row][col] == Model.WHITE){
                return false;
            }
            data[row][col] = color;
            history.add(new ChessPoint(row, col, color));
            lastRow = row;
            lastCol = col;
            lastColor = color;
            return true;
        }
        return false;
    }

    public int getChess(int row, int col){
        return data[row][col];
    }

    public int nextChessColor(int row, int col, int x, int y) {
        if (row + x < WIDTH && col + y < WIDTH && row + x >= 0 && col + y >= 0) {
            return data[row + x][col + y];
        } else
            return SPACE;
    }

    public int whoWin(){
        int count = 0;
        int i = 1;
        while (lastColor == nextChessColor(lastRow, lastCol, i, i)) {
            count++;
            i++;
        }
        i = 1;
        while (lastColor == nextChessColor(lastRow, lastCol, -i, -i)) {
            count++;
            i++;
        }
        if (count >= 4) {
            return lastColor;
        }
        i = 1;
        count = 0;

        while (lastColor == nextChessColor(lastRow, lastCol, i, -i)) {
            count++;
            i++;
        }
        i = 1;
        while (lastColor == nextChessColor(lastRow, lastCol, -i, i)) {
            count++;
            i++;
        }
        if (count >= 4) {
            return lastColor;
        }
        i = 1;
        count = 0;

        while (lastColor == nextChessColor(lastRow, lastCol, i, 0)) {
            count++;
            i++;
        }
        i = 1;
        while (lastColor == nextChessColor(lastRow, lastCol, -i, 0)) {
            count++;
            i++;
        }
        if (count >= 4) {
            return lastColor;
        }
        i = 1;
        count = 0;

        while (lastColor == nextChessColor(lastRow, lastCol, 0, i)) {
            count++;
            i++;
        }
        i = 1;
        while (lastColor == nextChessColor(lastRow, lastCol, 0, -i)) {
            count++;
            i++;
        }
        if (count >= 4) {
            return lastColor;
        }

        return SPACE;
    }

    public int getChessCount(){
        return history.size();
    }

    public ChessPoint getChessPoint(int index){
        return history.get(index);
    }

    public boolean redo() {
        if(history.isEmpty() || Vars.c.isOver){
            return false;
        }
        data[history.get(history.size() - 1).row][history.get(history.size() - 1).col] = SPACE;
        history.remove(history.size() - 1);
        seeIfCanRedo();
        return true;
    }

    public void seeIfCanRedo() {
        int chessColor = data[getChessPoint(history.size() - 1).row][getChessPoint(history.size() - 1).col];
        if(chessColor == Vars.c.localColor){
            Vars.ep.bp.setRedoBtnState(true);
        }
        else {
            Vars.ep.bp.setRedoBtnState(false);
        }
    }

    public boolean replay() {
        if(!Vars.c.isOver){
            return false;
        }
        Vars.rcf.startReplay(history);
        return true;
    }

    public void cleanChessPanel() {
        for(int i = 0; i < history.size(); i++){
            data[history.get(i).row][history.get(i).col] = SPACE;
        }
    }

    public void start() {
        Vars.c.isOver = false;
        history.clear();
    }

    public void cleanHistory(){
        history.clear();
    }
}
