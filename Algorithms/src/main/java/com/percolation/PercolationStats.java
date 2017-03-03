package com.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

private double[] xValues;
private double vMean;
private double vStdDev;

public PercolationStats(int iiN, int iiT) {
  // perform trials independent experiments on an n-by-n grid
  if (iiN <= 0  || iiT <= 0) {
   throw new java.lang.IllegalArgumentException();
  }

xValues = new double[iiT];

for (int i = 0; i < iiT; ++i) {
int count = 0;
Percolation p = new Percolation(iiN);
boolean bPercolate = false;

while (!bPercolate) {
int pI = StdRandom.uniform(1, iiN + 1);
int pJ = StdRandom.uniform(1, iiN + 1);
if (!p.isOpen(pI, pJ)) {
p.open(pI,  pJ);
++count;
bPercolate = p.percolates();
}
}
xValues[i] = (double) count / (double) (iiN * iiN);
}

vMean = StdStats.mean(xValues);
vStdDev = StdStats.stddev(xValues);

}

public double mean() {
// sample mean of percolation threshold
return vMean;
}

public double stddev() {
// sample standard deviation of percolation threshold
return vStdDev;
}

public double confidenceLo() {
// low  endpoint of 95% confidence interval
return 0;
}

public double confidenceHi() {
// high endpoint of 95% confidence interval
return 1;
}

private double confidenceLo(double common) {
// low  endpoint of 95% confidence interval
return mean() - common;
}
private double confidenceHi(double common) {
// high endpoint of 95% confidence interval
return mean() + common;
}

public static void main(String[] args) {   // test client, described below

if (args.length < 2) {
  throw new java.lang.IllegalArgumentException();
}
int iiN = Integer.parseInt(args[0]);
int iiT = Integer.parseInt(args[1]);

PercolationStats ps = new PercolationStats(iiN, iiT);
double mean = ps.mean();
double stddev = ps.stddev();

double common = 1.96 * stddev / Math.sqrt((double) iiT);

StdOut.println("mean                    = " + Double.toString(mean));
StdOut.println("stddev                  = " + Double.toString(stddev));
StdOut.println("95% confidence interval = " + ps.confidenceLo(common) + ", " + ps.confidenceHi(common));
 }
}