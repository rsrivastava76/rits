package com.algo;

public class test {

	
	   /* public static void main (String [] args) {
	        Scanner keyboard = new Scanner(System.in);
	        System.out.print("Type your first integer: ");
	        int first = keyboard.nextInt();
	        System.out.print("Type your seconds integer : ");
	        String second = keyboard.next();
	        System.out.print("The sum of your two integers are:");
	    }*/
	    
	    
	    public static void main(String[] args) {
			  Int2IntArrayMap m = new Int2IntArrayMap(); 
			   
			  m.put( 0, 1 ); 
			  m.put( 1, 2 ); 
			  
			  System.out.println(" 1 Obj == " + m.get(0));
			  System.out.println(" 2 Obj == " + m.get(1));
		}
		
}

