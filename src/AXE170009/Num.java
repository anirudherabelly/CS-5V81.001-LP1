// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package AXE170009;

public class Num implements Comparable < Num > {

    static long defaultBase = (long) Math.pow(2, 31);
    long base = defaultBase; // Change as needed
    long[] arr; // array to store arbitrarily large integers
    boolean isNegative; // boolean flag to represent negative numbers
    int len; // actual number of elements of array that are used;  number is stored in arr[0..len-1]
    int sizeAllotted;
    static Num ZERO = new Num(0L);
    static Num ONE = new Num(1L);

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
        double logOfBase = Math.log10(base);
        this.sizeAllotted = (int) Math.ceil(((size + 1) / logOfBase) + 1);
        arr = new long[sizeAllotted];

        try {
            char[] string = s.toCharArray();
            Num res = new Num("", base);
            Num ten = new Num(10);
            int i = 0;
            if (string[i] == '-') {
                this.isNegative = true;
                i++;
            }
            for (; i < s.length(); i++) {
                res = add(product(res, ten), new Num(Long.parseLong(string[i] + ""), base));
            }
            this.arr = res.arr;
        } catch (Exception e) {
            System.out.println("Unrecognized character Exception: " + " at Value: " + s);
        }
    }

    //constructor for initializing base with input as long.
    public Num(long x, long base) {
        this.base = base;

        int size = String.valueOf(x).length();
        double logOfBase = Math.log10(base);
        this.sizeAllotted = (int) Math.ceil(((size + 1) / logOfBase) + 1);
        arr = new long[this.sizeAllotted];

        if (x < 0) {
            this.isNegative = true;
            x = Math.abs(x);
        }

        if (x == 0) {
            this.arr[len] = x;
            len++;
        } else {
            long digit;
            while (x > 0) {
                digit = x % this.base;
                this.arr[len] = digit;
                len++;
                x /= this.base;
            }
        }
    }
    //End of constructors

    //sum of two signed big integers
    public static Num add(Num a, Num b) {
        if (a.isNegative ^ b.isNegative) {

        } else {
            return unsignedAdd(a, b);
        }
        return null;
    }

    //sum of two unsigned big integers
    private static Num unsignedAdd(Num a, Num b) {
        Num res = new Num("", a.base);
        long carry = 0l;
        int indexa = 0, indexb = 0;
        long sum = 0;
        while (indexa < a.len || indexb < b.len || carry > 0) {
            sum = (indexa < a.len) ? a.arr[indexa++] : 0;
            sum += (indexb < b.len) ? b.arr[indexb++] : 0;
            sum += carry;
            res.arr[res.len++] = (sum % res.base);
            carry = sum / res.base;
        }
        return res;
    }

    public static Num subtract(Num a, Num b) {
        return null;
    }

    public static Num product(Num a, Num b) {
        return null;
    }

    /*//product of a Num and long
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
	}*/

    // Use divide and conquer
    public static Num power(Num a, long n) {
		//System.out.print(Arrays.toString(a.arr) + "\t");
		if(a.isZero()) {
			return ZERO;
		}
		if(n==0) return ONE;
		if(n==1) {
			System.out.print(Arrays.toString(a.arr) + "\t");
			return a;
		}
		Num val = power(a, n/2);
		Num square = product(val, val);
		if(n%2 == 1) {
			return product(square, a);
		} else {
			return square;
		}
	}

    public boolean isZero() {
	for(int i=0; i<this.len; i++) {
		if(this.arr[i]!=0) return false;
	}
	return true;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        return null;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
		if(b.isZero()) {
			throw new ArithmeticException("Divisor b is 0");
		}
		int compResult = a.compareTo(b);
		if(a.isZero() || compResult == 0) {
			return ZERO;
		} else if(compResult == -1) {
			return a;
		} else if(compResult == 1){
			return subtract(a, product(divide(a,b), b));
		}
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
		Num tenBase = this.convertBase(10);
		StringBuilder tenBaseSB = new StringBuilder();
		for(int i=0; i<tenBase.len; i++) {
			tenBaseSB.insert(0, tenBase.arr[i]);
		} 
		if(tenBase.isNegative = true) {
			return "-" + tenBaseSB.toString();
		}
		return tenBaseSB.toString();
    }

    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        long oldBase = this.base;
	int index = this.len;
	Num newNumber = new Num("", newBase);
	while(index>0) {
		newNumber = add(product(newNumber, oldBase), new Num(this.arr[--index], newBase));
	}
	newNumber.isNegative = this.isNegative;
	return newNumber;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        return null;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
    	
    	//create a stack
        Stack<Num> stack=new Stack<>();
         
        // Scan all characters one by one
        for(int i=0;i<expr.length;i++)
        {
        	
            char c=expr[i].charAt(0);
             
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(c)) {
            	Num n=new Num(0l);
            	while(Character.isDigit(c)) {
            		Num x=new Num(expr[i]);
            		n=add(product(n,TEN),x);
            		i++;
            		c=expr[i].charAt(0);
            	}
            	i--;
            	//push the number into stack
            	stack.push(n);
            }
            	
             
            //  If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                Num val1 = stack.pop();
                Num val2 = stack.pop();
                 
                switch(c)
                {
                    case '+':
                    stack.push(add(val2,val1));
                    break;
                     
                    case '-':
                    stack.push(subtract(val2,val1));
                    break;
                     
                    case '/':
                    stack.push(divide(val2,val1));
                    break;
                     
                    case '*':
                    stack.push(product(val2,val1));
                    break;
				
                    case '%':
                    stack.push(mod(val2,val1));
                    break;
				
                    case '^':
                    stack.push(power(val2,Long.parseLong(val1.toString())));
                    break;	
              }
            }
        }
        return stack.pop();    
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
    	 // Stack for numbers: 'valuesStack' 
        Stack<Num> valuesStack = new Stack<>(); 
  
        // Stack for Operators: 'opsStack' 
        Stack<Character> opsStack = new Stack<Character>();

        for (int i = 0; i < expr.length; i++) 
        { 
             // Current token is a whitespace, skip it 
            if (expr[i].charAt(0) == ' ') 
                continue; 
  
            // Current token is a number, push it to stack for numbers 
            if (expr[i].charAt(0) >= '0' && expr[i].charAt(0) <= '9') 
            { 
                StringBuffer sbuf = new StringBuffer(); 
                // There may be more than one digits in number 
                while (i < expr.length && expr[i].charAt(0) >= '0' && expr[i].charAt(0) <= '9') 
                    sbuf.append(expr[i++]); 
                valuesStack.push(new Num(Long.parseLong(sbuf.toString()))); 
            } 
  
            // Current token is an opening brace, push it to 'opsStack' 
            else if (expr[i].charAt(0) == '(') 
                opsStack.push(expr[i].charAt(0)); 
  
            // Closing brace encountered, solve entire brace 
            else if (expr[i].charAt(0) == ')') 
            { 
                while (opsStack.peek() != '(') 
                  valuesStack.push(applyOp(opsStack.pop(), valuesStack.pop(), valuesStack.pop())); 
                opsStack.pop(); 
            } 
  
            // Current token is an operator. 
            else if (expr[i].charAt(0) == '+' || expr[i].charAt(0) == '-' || 
            		expr[i].charAt(0) == '*' || expr[i].charAt(0) == '/') 
            { 
                // While top of 'opsStack' has same or greater precedence to current 
                // token, which is an operator. Apply operator on top of 'opsStack' 
                // to top two elements in valuesStack stack 
                while (!opsStack.empty() && hasPrecedence(expr[i].charAt(0), opsStack.peek())) 
                  valuesStack.push(applyOp(opsStack.pop(), valuesStack.pop(), valuesStack.pop())); 
  
                // Push current token to 'opsStack'. 
                opsStack.push(expr[i].charAt(0)); 
            } 
        }
        
        while (!opsStack.empty()) 
            valuesStack.push(applyOp(opsStack.pop(), valuesStack.pop(), valuesStack.pop())); 
  
        // Top of 'valuesStack' contains result, return it 
        return valuesStack.pop(); 
    } // end of infix evaluation
        //helper function for infix evaluation
        public static boolean hasPrecedence(char op1, char op2) 
        { 
            if (op2 == '(' || op2 == ')') 
                return false; 
            if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) 
                return false; 
            else
                return true; 
        } 
    	//helper function for infix evaluation
        public static Num applyOp(char op, Num b, Num a) 
        { 
            switch (op) 
            { 
            case '+': 
                return add(a,b); 
		break;	
			    
            case '-': 
                return subtract(a,b);
		break;	
			    
            case '*': 
                return product(a,b); 
		break;	
			    
            case '/': 
                if (b == ZERO) 
                    throw new
                    UnsupportedOperationException("Cannot divide by zero"); 
                return divide(a,b);
		break;	
			    
            case '%':
            	return mod(a,b);
		break;	
			    
            case '^':
            	return power(a,Long.parseLong(b.toString()));
		break;
            } 
            return ZERO; 
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
