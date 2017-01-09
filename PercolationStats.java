import java.util.Random;
import static java.lang.Math.sqrt;
public class PercolationStats {

    private int nTrials;
    private double sum = 0;
    private int[] vals;
    private int size = 0;
    // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials){
        Random rand = new Random();
        vals = new int[trials];
        int nOpen;
        nTrials = trials;
        size = n;
        if (n<=0 || trials<=0)
            throw new java.lang.IllegalArgumentException();
        int[] N = new int[trials];
        for (int i=0;i<trials;i++){
            Percolation system = new Percolation(n);
            boolean flag = false;
            while(flag==false){
                int x = rand.nextInt(n) + 1;
                int y = rand.nextInt(n) + 1;
                system.open(x,y);
                flag = system.percolates();
                nOpen = system.numberOfOpenSites();
                if (flag){
                    sum = sum + nOpen;
                    vals[i] = nOpen;
                }
            }
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return (sum/nTrials)/(size*size);
    }              

   // sample standard deviation of percolation threshold
   public double stddev() {
        double avg = mean();
        double sum = 0;
        for (int i=0;i<nTrials;i++){
            sum = sum + (vals[i]/(size*size) - avg)*(vals[i]/(size*size) - avg);
        }
        return sqrt(sum/(nTrials-1));

   }           

   // low  endpoint of 95% confidence interval
    public double confidenceLo(){
        double s = stddev();
        double avg = mean();
        return avg -1.96*s/nTrials;
    }

    // high endpoint of 95% confidence interval 
    public double confidenceHi(){
        double s = stddev();
        double avg = mean();
        return avg -1.96*s/nTrials;
    } 

    // test client (described below)
    public static void main(String[] args){
        if (args.length > 0){
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats stats = new PercolationStats(n, trials);
            System.out.println("Mean=\t\t\t\t"+stats.mean()+"\n"+"Standard deviation=\t\t"+stats.stddev()+"\n"+"95% confidence interval=\t"+stats.confidenceLo()+" "+stats.confidenceHi());
        }    
   }
}