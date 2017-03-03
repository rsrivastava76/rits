package com.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private boolean[] openSites;
  private int gridN;
  private int n;
  private WeightedQuickUnionUF weightedquickUnionuf;
  private WeightedQuickUnionUF weightedQuickUnionUF2;

  public Percolation(int gridLength) {
    if (gridLength <= 0) {
     throw new java.lang.IllegalArgumentException();
    }
    // create N-by-N grid, with all sites blocked
    this.openSites = new boolean[gridLength * gridLength];
    this.gridN = gridLength;
    this.n = 1;
    for (int i = 0; i < gridLength * gridLength; ++i) {
      openSites[i] = false;
    }
   // add 2 virtual sites
  weightedquickUnionuf = new WeightedQuickUnionUF(gridLength * gridLength + 2);
  weightedQuickUnionUF2 = new WeightedQuickUnionUF(gridLength * gridLength + 1);
  }

  private void indexChecker(int i, int j) {
    if (i < 1 || j < 1 || i > gridN || j > gridN)
      throw new java.lang.IndexOutOfBoundsException();
    }

public void open(int i, int j) {
 // open site (row i, column j) if it is not open already
indexChecker(i, j);

int indexI = i - 1;
int indexJ = j - 1;

int osIndex = indexI * gridN + indexJ;
if (openSites[osIndex])
  return;
openSites[osIndex] = true;

int ufIndex = indexI * gridN + indexJ + 1;
if (indexI == 0) {
  weightedquickUnionuf.union(0,  ufIndex);
  weightedQuickUnionUF2.union(0, ufIndex);
}
if (indexI == gridN - 1) {
  weightedquickUnionuf.union(ufIndex, gridN * gridN + 1);
}

boolean bOpen = false;

// union adjacent open sites
 int leftIndexI = indexI;
 int leftIndexJ = indexJ - 1;
 if (leftIndexJ >= 0) {
   bOpen = isOpen(leftIndexI + 1, leftIndexJ + 1);
   if (bOpen) {
     n++;
     int leftUFIndex = leftIndexI * gridN + leftIndexJ + 1;
     weightedquickUnionuf.union(leftUFIndex, ufIndex);
     weightedQuickUnionUF2.union(leftUFIndex, ufIndex);
   }
 }
  int rightIndexI = indexI;
  int rightIndexJ = indexJ + 1;
  if (rightIndexJ < gridN) {
    bOpen = isOpen(rightIndexI + 1, rightIndexJ + 1);
    if (bOpen) {
     n++;
     int rightUFIndex = rightIndexI * gridN + rightIndexJ + 1;
     weightedquickUnionuf.union(ufIndex,  rightUFIndex);
     weightedQuickUnionUF2.union(ufIndex,  rightUFIndex);
    }
  }

int upIndexI = indexI - 1;
int upIndexJ = indexJ;
if (upIndexI >= 0) {
bOpen = isOpen(upIndexI + 1, upIndexJ + 1);
if (bOpen) {
n++;
int upUFIndex = upIndexI * gridN + upIndexJ + 1;
weightedquickUnionuf.union(upUFIndex, ufIndex);
weightedQuickUnionUF2.union(upUFIndex, ufIndex);
}
}

int downIndexI = indexI + 1;
int downIndexJ = indexJ;
if (downIndexI < gridN) {
bOpen = isOpen(downIndexI + 1, downIndexJ + 1);
if (bOpen) {
n++;
int downUFIndex = downIndexI * gridN + downIndexJ + 1;
weightedquickUnionuf.union(ufIndex, downUFIndex);
weightedQuickUnionUF2.union(ufIndex, downUFIndex);
}
}
}

public boolean isOpen(int i, int j) {
// is site (row i, column j) open?
indexChecker(i, j);
return (openSites[ (i - 1) * gridN + j - 1]);
}

public boolean isFull(int i, int j) {
// is site (row i, column j) full?
indexChecker(i, j);
int indexI = i - 1;
int indexJ = j - 1;

int osIndex = indexI * gridN + indexJ;
int ufIndex = osIndex + 1;

boolean bOpen = isOpen(i, j);
boolean isFull = weightedQuickUnionUF2.connected(0,  ufIndex);
return (bOpen && isFull);
}

  public boolean percolates() {
// does the system percolate?
if (gridN == 1)
 return (openSites[0]);
return weightedquickUnionuf.connected(0,  gridN * gridN + 1);
}

  /**
 * @return the number of opened sites.
 */
  public int numberOfOpenSites() {
    return n;
  }

  public static void main(String[] args) { 
   // test client, described below
   test2();
  }
  
  private static void test2() {
	  
  final Percolation p = new Percolation(6);
  
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(1, 6));
  p.open(1, 6);
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(1, 6));
  System.out.println("p.isFull(1, 6) = " + p.isFull(1, 6));
  System.out.println("p.percolates() = " + p.percolates());
  StdOut.println("openSite" + p.numberOfOpenSites());
  
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(2, 6));
  p.open(2, 6);
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(2, 6));
  System.out.println("p.isFull(1, 6) = " + p.isFull(2, 6));
  System.out.println("p.percolates() = " + p.percolates());
  StdOut.println("openSite" + p.numberOfOpenSites());
  
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(3, 6));
  p.open(3, 6);
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(3, 6));
  System.out.println("p.isFull(1, 6) = " + p.isFull(3, 6));
  System.out.println("p.percolates() = " + p.percolates());
  StdOut.println("openSite" + p.numberOfOpenSites());
  
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(4, 6));
  p.open(4, 6);
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(4, 6));
  System.out.println("p.isFull(1, 6) = " + p.isFull(4, 6));
  System.out.println("p.percolates() = " + p.percolates());
  StdOut.println("openSite" + p.numberOfOpenSites());
  
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(5, 1));
  p.open(5, 1);
  System.out.println("p.isOpen(1, 6) = " + p.isOpen(5, 1));
  System.out.println("p.isFull(1, 6) = " + p.isFull(5, 1));
  System.out.println("p.percolates() = " + p.percolates());
  StdOut.println("openSite" + p.numberOfOpenSites());
  
  }
}