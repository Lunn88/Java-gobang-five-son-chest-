import java.io.Serializable;

public class ChessPoint implements Serializable {
    public int row;
    public int col;
    public int color;

    public ChessPoint(int row, int col, int color) {
        super();
        this.row = row;
        this.col = col;
        this.color = color;
    }
}
