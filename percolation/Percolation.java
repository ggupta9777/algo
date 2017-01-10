import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Exception;
import java.util.Random;

public class Percolation {

    private int[][] grid;
    private int size;
    private int nOpen = 0;
    private int[] tree;
    private WeightedQuickUnionUF sys;
    private int xlow;
    private int xupp;
    private int ylow;
    private int yupp;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        grid = new int[n][n];
        initializeTree(n);
    }  		
        
    // open site (row, col) if it is not open already
    public void open(int row, int col){
        if (row > size || col > size){
           throw new IndexOutOfBoundsException();
        }
        else if (row <= 0 || col <= 0){
            throw new IllegalArgumentException();
        }
        row = row -1;
        col = col - 1;
        if (grid[row][col] == 0){
            grid[row][col] = row*size + col + 1;
            nOpen = nOpen + 1;
            makeConnection(row+1, col+1);
        }
    }	

    // is site (row, col) open?	
    public boolean isOpen(int row, int col){
        if (row > size || col > size){
           throw new IndexOutOfBoundsException();
        }
        else if (row <=0 || col <=0){
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        if (grid[row][col] != 0)
            return true;
        else
            return false;
    } 

    // is site (row, col) full?
    public boolean isFull(int row, int col){
         if (row > size || col > size){
           throw new IndexOutOfBoundsException();
        }
        else if (row <=0 || col <=0){
            throw new IllegalArgumentException();
        }
        return sys.connected((row-1)*size+col, 0);
    }  		

    // number of open sites
    public int numberOfOpenSites(){
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return sys.connected(0, size*size+1);	
        }

    private void initializeTree(int n){
        size = n;
        tree = new int[size*size+2];
        tree[0] = -1;
        tree[size*size+1] = -2;
        sys = new WeightedQuickUnionUF(size*size+2);
    }

    private void makeConnection(int row, int col){
        ylow = row - 1;
        yupp = row + 1;
        xlow = col - 1;
        xupp = col + 1;

        if (xlow  >  0 && grid[row-1][xlow-1] != 0){
            sys.union((row-1)*size+col, (row-1)*size+xlow);
        }
        if (xupp <= size && grid[row-1][xupp-1] != 0){
            sys.union((row-1)*size+col, (row-1)*size+xupp);
        }
        if (ylow > 0 && grid[ylow-1][col-1] != 0){
                sys.union((row-1)*size+col, (ylow-1)*size+col);
        }
        if (yupp <=size && grid[yupp-1][col-1] != 0){
                sys.union((row-1)*size+col, (yupp-1)*size+col);
        }
         if (ylow == 0 ){
                sys.union((row-1)*size+col, 0);
        }
        if (yupp == size+1 ){
                sys.union((row-1)*size+col, size*size+1);
        }
    }

    public static void main(String[] args) {
        Percolation A = new Percolation(10);
        A.open(12,6);
        A.open(1,1);
        A.open(2,2);
        A.open(3,3);
        System.out.println(A.percolates());
        System.out.println(A.isFull(2,2));
        System.out.println(A.isFull(3,3));
}

}
