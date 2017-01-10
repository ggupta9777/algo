import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import static java.lang.Math.sqrt;
public class PercolationStats  {

    private int nTrials;
    private int size = 0;
    private double[] vals;
    // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {
        vals = new double[trials];
        int nOpen;
        nTrials = trials;
        size = n;
        if (n <=0 || trials <=0)
            throw new java.lang.IllegalArgumentException();
        int[] N = new int[trials];
        for (int i=0;i <trials;i++) {
            Percolation system = new Percolation(n);
            boolean flag = false;
            while(flag== false) {
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;
                system.open(x,y);
                flag = system.percolates();
                nOpen = system.numberOfOpenSites();
                if (flag) {
                    vals[i] = ((double)(nOpen)/(size*size));
                }
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(vals);
    }              

   // sample standard deviation of percolation threshold
   public double stddev()  {
        return StdStats.stddev(vals);
   }           

   // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = stddev();
        double avg = mean();
        return avg -1.96*s/nTrials;
    }

    // high endpoint of 95% confidence interval 
    public double confidenceHi() {
        double s = stddev();
        double avg = mean();
        return avg -1.96*s/nTrials;
    } 

    // test client (described below)
    public static void main(String[] args) {
        if (args.length > 0) {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats stats = new PercolationStats(n, trials);
            System.out.println("Mean=\t\t\t\t"+stats.mean()+"\n"+"Standard deviation=\t\t"+stats.stddev()+"\n"+"95% confidence interval=\t"+stats.confidenceLo()+" "+stats.confidenceHi());
        }    
   }
}