// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package AXE170009;
import java.util.LinkedList;
import java.util.Iterator;

public class Num implements Comparable < Num > {

    static long defaultBase = (long)Math.pow(2, 31);
    long base = defaultBase; // Change as needed
    LinkedList<Long> number; // using LinkedList to store large no's instead of array for dynamic sizing.
    boolean isNegative; // boolean flag to represent negative numbers
    int len; // actual number of elements of array that are used;  number is stored in arr[0..len-1]
    
    //Start of constructors
    public Num() {
    	number = new LinkedList<Long>();
    }
    public Num(String s) {
    	this(s, defaultBase);
    }

    public Num(long x) {
    	this(x, defaultBase);
    }
    
    //constructor for initializing base with input as string
    public Num(String s, long base) {
		this();
    	this.base = base;
    	s = s.trim();//remove all the extra spaces.
    	//checks whether the string is empty.
    	if(s.equals(""))return;
    	
    	try {
    		char[] string = s.toCharArray();
    		Num res = new Num("", base);
    		int i = 0;
    		if(string[i] == '-') {
    			this.isNegative = true;
    			i++;
    		}
    		for(;i<s.length();i++) {
    			res = add(product(res, 10), new Num(Long.parseLong(string[i]+""), base));
    		}
    		this.number = res.number;
    	}
    	catch(Exception e) {
    		System.out.println("Unrecognized character Exception: " + " at Value: " + s);
    	}
	}
    
    //constructor for initializing base with input as long.
    public Num(long x, long base) {
		this();
		this.base = base;
		
		if(x < 0)this.isNegative = true;
		x = Math.abs(x);
		
		if(x == 0) {
			this.number.add(x);
		}
		else {
			long digit;
			while(x > 0) {
				digit = x % this.base;
				this.number.add(digit);
				x /= this.base;
			}
		}
	}
    //End of constructors
	
    public static Num add(Num a, Num b) {
        return null;
    }

    public static Num subtract(Num a, Num b) {
        return null;
    }

    public static Num product(Num a, Num b) {
        return null;
    }
    
    //product of a Num and long
    private Num product(Num n, long base) {
    	
	}
    
    // Use divide and conquer
    public static Num power(Num a, long n) {
        return null;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        return null;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        return null;
    }

    // Use binary search
    public static Num squareRoot(Num a) {
        return null;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {}

    // Return number to a string in base 10
    public String toString() {
        return null;
    }

    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        return null;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        return null;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        return null;
    }


    public static void main(String[] args) {
        Num x = new Num(999);
        Num y = new Num("8");
        Num z = Num.add(x, y);
        System.out.println(z);
        Num a = Num.power(x, 8);
        System.out.println(a);
        if (z != null) z.printList();
    }
}