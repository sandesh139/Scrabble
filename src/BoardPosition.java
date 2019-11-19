/**
 * this class provides the representation for the x- and y- coordinates of the board
 *
 */
public class BoardPosition {

    /*initiating a private integer variable x */
    private int x;

    /*initiating a private integer variable y */
    private int y;

    /**
     * constructor
     * when object point is created we assign parameter passed to global variable
     */
    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * this method returns the x value of the node
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * this method returns the y - value of the node
     *
     * @return
     */
    public int getY() {
        return y;
    }


    /**
     * this exchange the x and y values of the board position
     *
     * @param x
     * @param y
     */
    public void exchange(int x, int y) {
        this.x = y;
        this.y = x;
    }

}
