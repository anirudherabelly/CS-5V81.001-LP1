// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package AXE170009;

public class Num implements Comparable < Num > {

    static long defaultBase = (long)Math.pow(2, 31);
    long base = defaultBase; // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative; // boolean flag to represent negative numbers
    int len; // actual number of elements of array that are used;  number is stored in arr[0..len-1]
    int sizeAllotted; 
    
    //Start of constructors
    public Num(String s) {
    	this(s, defaultBase);
    }

    public Num(long x) {
    	this(x, defaultBase);
    }
    
    //constructor for initializing base with input as string
    private Num(String s, long base) {
    	//remove all the extra spaces.
    	s = s.trim();
    	
    	this.base = base;
    	
    	int size = s.length();
    	double logOfBase  = Math.log10(base); 
    	this.sizeAllotted =  (int)Math.ceil(((size+1)/logOfBase)+1);
		arr = new long[sizeAllotted];
    	
		try {
    		char[] string = s.toCharArray();
    		Num res = new Num("", base);
		Num ten= new Num(10);
    		int i = 0;
    		if(string[i] == '-') {
    			this.isNegative = true;
    			i++;
    		}
    		for(;i<s.length();i++) {
    			res = add(product(res, ten), new Num(Long.parseLong(string[i]+""), base));
    		}
    		this.arr = res.arr;
    	}
    	catch(Exception e) {
    		System.out.println("Unrecognized character Exception: " + " at Value: " + s);
    	}
	}
    
    //constructor for initializing base with input as long.
    public Num(long x, long base) {
    	this.base = base;
		
    	int size = String.valueOf(x).length();
    	double logOfBase  = Math.log10(base); 
    	this.sizeAllotted =  (int)Math.ceil(((size+1)/logOfBase)+1);
    	arr = new long[this.sizeAllotted];
		
		if(x < 0) {
			this.isNegative = true;
			x = Math.abs(x);
		}
		
		if(x == 0) {
			this.arr[len] = x;
			len++;
		}
		else {
			long digit;
			while(x > 0) {
				digit = x % this.base;
				this.arr[len] = digit;
				len++;
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
    	if(base == 0) {
    		return new Num(0l, base);
    	}
    	
    	int index = 0;
    	long carry = 0l, sum;
    	Num res = new Num("", n.base);
    	
    	while(index < n.len || carry > 0) {
    		sum = (n.arr[index] * base + carry);
    		index++;
    		res.arr[res.len] = sum % res.base;
    		res.len++;
    		carry = sum / res.base;
    	}
    	return res;
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
	if(this.isNegative && !other.isNegative) {
        	return -1;
        } else if(!this.isNegative && other.isNegative) {
        	return 1;
        } else {
        	if(this.len < other.len) return -1;
        	else if(this.len > other.len) return 1;
        	else {
        		for(int i=(this.len-1); i>=0; i--) {
        			if(this.arr[i]>other.arr[i]) {
        				return 1;
        			} else if(this.arr[i]<other.arr[i]) {
        				return -1;
        			}
        		}
        	}
        }
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    	System.out.print(base + ": ");
    	for(int i=0; i<len; i++) {
    		System.out.print(arr[i]+" ");
    	}
    	System.out.println();
    }

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
