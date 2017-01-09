import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Exception;
import java.util.Arrays;
import java.util.Random;

public class Percolation {

    private int[][] grid;
    private int size;
    private int nOpen = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        grid = new int[n][n];
        size = n;
    }  		
        
    // open site (row, col) if it is not open already
    public void open(int row, int col){
        if (row>size || col>size){
           throw new IndexOutOfBoundsException();
        }
        else if (row<=0 || col <=0){
            throw new IllegalArgumentException();
        }
        row = row -1;
        col = col - 1;
        if (grid[row][col] == 0){
            grid[row][col] = row*size + col + 1;
            nOpen = nOpen + 1;
        }
    }	

    // is site (row, col) open?	
    public boolean isOpen(int row, int col){
        if (row>size || col>size){
           throw new IndexOutOfBoundsException();
        }
        else if (row<=0 || col <=0){
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        if (grid[row][col] != 0){
            return true;
        }
        else{
            return false;
        }
    } 

    // is site (row, col) full?
    public boolean isFull(int row, int col){
        return !isOpen(row, col);
    }  		

    // number of open sites
    public int numberOfOpenSites(){
        return nOpen;
    }

    private int[] ge(int i){
        int row;
        int col;
        int[] output = new int[3];
        if (i%size==0){
            col = size;
        }
        else{
            col = i%size;
        }
        row = (i - col)/size + 1;
        output[0] = grid[row-1][col-1];
        output[1] = row;
        output[2] = col;
        return output;
    }

    // does the system percolate?
    public boolean percolates() {
        WeightedQuickUnionUF sys = new WeightedQuickUnionUF(size*size+2);
        int[] tree = new int[size*size+2];
        int row;
        int col;
        //Two virtual nodes at the top and bottom
        tree[0] = -1;
        tree[size*size+1] = -2;

        for (int i=1;i<size*size+1;i++){
            tree[i] = i;
            row = ge(i)[1];
            col = ge(i)[2];

            if (ge(i)[0]!=0 && col>1){
                if (ge(i-1)[0]!=0){
                    sys.union(i-1, i);
                    tree[i] = i-1;
                }
            }

            if (ge(i)[0]!=0 && row>1){
                if (ge(i-size)[0]!=0){
                    sys.union(i-size, i);
                    tree[i] = i - size;
                }
            }
        }   

            for (int i=1;i<size+1;i++){
                if (ge(i)[0]!=0){
                    sys.union(0, i);
                    tree[i] = tree[0];      
                }
            }    

            for (int i=size*size-size+1;i<=size*size;i++){
                if (ge(i)[0]!=0){
                    sys.union(size*size+1, i);
                    tree[i] = tree[size*size+1];
                }
            }       

            return sys.connected(0, size*size+1);  		
        }

    public static void main(String[] args) {
        Percolation A = new Percolation(3);
        A.open(2,1);
        A.open(1,1);
        A.open(3,1);
        System.out.println(A.percolates());
        //     int n = 4;
        //     Percolation system = new Percolation(n);
        //     boolean flag = false;
        //     Random rand = new Random();
        //     while(flag==false){
        //         int x = rand.nextInt(n) + 1;
        //         int y = rand.nextInt(n) + 1;
        //         system.open(1,1);
        //         system.open(2,1);
        //         system.open(3,1);
        //         flag = system.percolates();
        //         flag = true;
        //         System.out.println(system.percolates());
        // }
}
}
