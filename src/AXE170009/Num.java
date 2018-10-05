// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package AXE170009;
import java.util.Arrays;
import java.util.Stack;

public class Num implements Comparable < Num > {
	
	class Split {
		Num firstHalf;
		Num secondHalf;

		Split() {
			firstHalf = new Num("");
			secondHalf = new Num("");
		}

		public void splitNum(Num n, int k) {
			firstHalf.arr = new long[k];
			firstHalf.base = n.base;
			firstHalf.len = k;
			firstHalf.sizeAllotted = firstHalf.len;
			
			secondHalf.arr = new long[n.len-k];
			secondHalf.base = n.base;
			secondHalf.len = n.len - k;
			secondHalf.sizeAllotted = secondHalf.len;
			
			int nSize = n.len-1, fIndex = 0, sIndex = 0, nIndex = 0;
			
			while (nSize >= 0) {
				if (nIndex < k) {
					firstHalf.arr[fIndex++] = n.arr[nIndex++];
				} else {
					secondHalf.arr[sIndex++] = n.arr[nIndex++];
				}
			}
		}
	}

    static long defaultBase = (long) Math.pow(10, 3); //multiples of 10
    long base = defaultBase; // Change as needed
    long[] arr; // array to store arbitrarily large integers
    boolean isNegative = false; // boolean flag to represent negative numbers
    int len = 0; // actual number of elements of array that are used;  number is stored in arr[0..len-1]
    int sizeAllotted = 0;

    static Num ZERO = new Num(0L);
    static Num ONE = new Num(1L);
    static Num TWO = new Num(2L);
    static Num TEN = new Num(10L);

    //Start of constructors
    public Num(String s) {
        this(s, defaultBase);
    }

    public Num(long x) {
        this(x, defaultBase);
    }
    
    //constructor for initializing base with input as string
    public Num(String s, long base) {
    	//remove all the extra spaces.
        s = s.trim();
        if(s.charAt(0) == '-') {
        	this.isNegative = true;
        	s = s.substring(1);
        }
        
        this.base = base;
        int size = s.length();
        double logOfBase = Math.log10(base);
        this.sizeAllotted = (int) Math.ceil(size / logOfBase);
        this.len = 0;
        arr = new long[this.sizeAllotted];
        
        int start, end = size;
        while(len < sizeAllotted) {
        	start = (end - (int)logOfBase) < 0 ? 0 : (end - (int)logOfBase);
        	arr[len++] = Long.parseLong(s.substring(start, end));
        	end = start;
        }
    }

    //constructor for initializing base with input as long.
    public Num(long x, long base) {
        this.base = base;

        int size = String.valueOf(x).length();
        double logOfBase = Math.log10(base);
        this.sizeAllotted = (int) Math.ceil(size / logOfBase);
        this.len = 0;
        arr = new long[this.sizeAllotted];
        
        if (x < 0) {
            this.isNegative = true;
            x = Math.abs(x);
        }

        if (x == 0) {
            this.arr[len++] = x;
        } else {
            long temp;
            while (x > 0) {
                temp = x % this.base;
                this.arr[len++] = temp;
                x /= this.base;
            }
        }
    }
    //End of constructors
    
    //Start of constructor for result in operations +, -, * ,/
    private Num(long base, int k) {
    	this.arr = new long[k+1];;
    	this.base = base;
    	this.len = 0;
    	this.sizeAllotted = k+1; 
    }
    //End of constructor for result 
    
    //utility function for getting maximum length of two nums
    private static int maxlen(Num a, Num b) {
		int k = (a.len > b.len)?a.len : b.len;
		return k;
 	}
    
    //sum of two signed big integers
    public static Num add(Num a, Num b) {
        if (a.isNegative ^ b.isNegative) {
        	return unsignedCompareTo(a, b) > 0 ? unsignedSubtract(a, b, a.isNegative) : unsignedSubtract(b, a, b.isNegative);
        }
        return unsignedAdd(a, b);
    }

	//sum of two unsigned big integers
    private static Num unsignedAdd(Num a, Num b) {
    	
    	
        Num res = new Num(a.base, maxlen(a, b));
        
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
    
    private static int unsignedCompareTo(Num a, Num b) {
    	if(a.len < b.len) {
    		return -1;
    	}
    	else if(a.len > b.len) {
    		return 1;
    	}
    	return travaerseToCompare(a, b);
    }
    
    private static int travaerseToCompare(Num a, Num b) {
		int indexa = 0, indexb = 0;
		while(indexa < a.len) {
			Long ai = a.arr[indexa++];
			Long bi = b.arr[indexb++];
			if(ai.compareTo(bi) != 0) {
				return ai.compareTo(bi);
			}
		}
		return 0;
	}

	public static Num subtract(Num a, Num b) {
        if(a.isNegative ^ b.isNegative) {
        	return unsignedAdd(a, b);
        }
        return unsignedCompareTo(a, b) > 0 ? unsignedSubtract(a, b, a.isNegative) : unsignedSubtract(b, a, !b.isNegative);
    }

    private static Num unsignedSubtract(Num a, Num b, boolean isNegative) {
    	if(a.len == 0)return b;
    	if(b.len == 0)return a;
    	
		Num res = new Num(a.base, maxlen(a, b));
		
		int indexa = 0, indexb = 0;
		long borrow = 0l, diff;
		
		while(indexa < a.len || indexb < b.len || borrow > 0) {
			diff = indexa < a.len ? a.arr[indexa++] : 0;
			diff -= indexb < b.len ? b.arr[indexb++] : 0; 
			diff -= borrow;
			borrow = 0l;
			if(diff < 0) {
				diff += res.base;
				borrow = 1;
			}
			if(!( !(indexb < a.len) && diff == 0) || (diff == 0)) {
				res.arr[res.len++] = diff;
			}
		}
		//res.trim();
		assignSign(res, isNegative);
		return res;
	}
    
    public static Num product(Num a, Num b) {
    	Num res;
    	if(a.len == 1 || b.len == 1) {
    		res = b.len == 1 ? product(a, b.arr[0]) : product(b, a.arr[0]);
    	}
    	else if(a.len == 0 || b.len == 0){
    		res = new Num("", a.base);
    	}
    	else if(a.len >= b.len) {
    		res = karatsubasplit(a, b);
    	}
    	else {
    		res = karatsubasplit(b, a);
    	}
    	assignSign(res, a.isNegative ^ b.isNegative);
        return res;
    }

    //product of a Num and long
    private static Num product(Num n, long base) {
    	if(base == 0) {
    		return new Num(0l, base);
    	}
    	
    	int index = 0;
    	long carry = 0l, sum;
    	Num res = new Num(n.base, 0);
    	
    	while(index < n.len || carry > 0) {
    		sum = (n.arr[index] * base + carry);
    		index++;
    		res.arr[res.len++] = sum % res.base;
    		carry = sum / res.base;
    	}
    	return res;
	}
    
    private static Num karatsubasplit(Num a, Num b) {
    	int k = (int) b.len / 2;
    	
    	Split sp = a.new Split();
    	sp.splitNum(a, k);
    	Num aFirstHalf = sp.firstHalf;
    	Num aSecondHalf = sp.secondHalf;
    	
    	sp = b.new Split();
    	sp.splitNum(b, k);
    	Num bFirstHalf = sp.firstHalf;
    	Num bSecondHalf = sp.secondHalf;
    	
    	Num partOne = product(aSecondHalf, bSecondHalf);
    	Num partThree = product(aFirstHalf, bFirstHalf);
    	
    	Num sumAfhAsh = add(aFirstHalf, aSecondHalf);
    	Num sumBfhBsh = add(bFirstHalf, bSecondHalf);
    	
    	Num prodAfhshBfhsh = product(sumAfhAsh, sumBfhBsh);
    	Num partTwo = subtract(subtract(prodAfhshBfhsh, partOne), partThree);
    	
    	leftShift(partOne, 2 * k);
    	leftShift(partTwo, k);
    	
    	Num res = add(add(partOne, partTwo), partThree);
    	return res;
    }
    
    private static void assignSign(Num a, boolean isNegative) {
    	a.isNegative = a.len == 1 ? (a.arr[0] == 0 ? false : isNegative) : isNegative;
    }

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
    public static Num divide(Num a, Num b) throws Exception {
    	Num res = new Num("", a.base);
    	
    	if(unsignedCompareTo(b, a) == 0) {
    		throw new UnsupportedOperationException("Divided By Zero");
    	}
    	else if(unsignedCompareTo(b, ONE) == 0) {
    		res = a;
    	}
    	else if(unsignedCompareTo(b, TWO) == 0) {
    		res = a.by2();
    	}
    	
    	int ret = unsignedCompareTo(a, b);
    	if(ret < 0) {
    		res = ZERO;
    	}
    	else if(ret == 0) {
    		res = ONE;
    	}
    	else {
    		res = binarySearch(ONE, a, a, b);
    	}
    	assignSign(res, a.isNegative ^ b.isNegative);
        return res;
    }

    private static Num binarySearch(Num low, Num high, Num a, Num b) {
    	Num temp = unsignedAdd(low, high);
    	Num mid = temp.by2();
		Num left = product(mid, b);
		Num right = unsignedAdd(left,b);
		int leftcomp = unsignedCompareTo(left, a);
		int rightcomp = unsignedCompareTo(a, right);
		
		if (leftcomp > 0) {
			return binarySearch(low, mid, a, b);
		}
		else if (leftcomp <= 0 && rightcomp < 0) {
			return mid;
		}
		else {
			return binarySearch(unsignedAdd(mid, ONE), high, a, b);
		}
	}
    
    private static void leftShift(Num a, int k) {
    	long[] temp = new long[a.sizeAllotted + k];
    	for(int i = 0; i < k; i++) {
    		temp[i] = 0l;
    	}
    	for(int i = k; i < a.sizeAllotted + k; i++) {
    		temp[i] = a.arr[i];
    	}
    	a.arr = temp;
    }
    
    // return a%b
    public static Num mod(Num a, Num b) throws Exception {
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
    	if(a.compareTo((ZERO)) == -1){ // Checks if a is less than zero
    		return null;
    	}
    	if(a.compareTo((ZERO)) == 0){
    		return (new Num(0));
    	}
    	if(a.compareTo((ONE)) == 0){
    		return (new Num(1));
    	}else{
    		Num result = binSquare(a, (TWO), a.by2()); // recursively finds the square root
    		return result;
    	}
    }


    private static Num binSquare(Num a, Num first, Num second){
    	if(second.compareTo(first) != -1){
    		Num diff=subtract(second,first);
    		Num mid = unsignedAdd(first, diff.by2());
			// If the element is present at the middle itself
    		Num product = product(mid, mid);
    		int dec = product.compareTo(a);
			if (dec == 0){
				return mid;
			}
			// If element is smaller than mid, then it can only
			// be present in left subarray
			if (dec != -1) {
				return binSquare(a, first, subtract(mid, ONE));
			}
			// Else the element can only be present in right
			// subarray
			return binSquare(a, add(mid, ONE), second);
    	}
    	return second;
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
    	for(int i=0; i < len; i++) {
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
			newNumber = add(product(newNumber, new Num(oldBase)), new Num(this.arr[--index], newBase));
		}
		newNumber.isNegative = this.isNegative;
		return newNumber;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
    	Num res = product(this, this.base/2);
    	rightShift(res);
        return res;
    }

    private void rightShift(Num res) {
		long arr2[] = new long[arr.length-1];
		System.arraycopy(arr, 1, arr2, 0, arr.length-1);
		arr = arr2;
	}

	// Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) throws Exception {
    	
    	//create a stack
        Stack<Num> stack=new Stack<Num>();
         
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
    public static Num evaluateInfix(String[] expr) throws Exception {
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
    public static Num applyOp(char op, Num b, Num a) throws Exception 
    { 
        switch (op) 
        { 
        case '+': 
            return add(a,b); 
        case '-': 
            return subtract(a,b); 
        case '*': 
            return product(a,b); 
        case '/': 
            return divide(a,b);
        case '%':
        	return mod(a,b);
        case '^':
        	return power(a,Long.parseLong(b.toString()));
        } 
        return ZERO; 
    } 


    public static void main(String[] args) {
        Num x = new Num(999);
        long[] arr = x.arr;
        for(int i = 0; i < arr.length; i++) {
        	System.out.print(arr[i] +" ");
        }
        System.out.println();
        
        Num y = new Num("1888");
        arr = y.arr;
        for(int i = 0; i < arr.length; i++) {
        	System.out.print(arr[i] +" ");
        }
        System.out.println();
        
        
        Num z = Num.subtract(y, x);
        arr = z.arr;
        for(int i = 0; i < z.len; i++) {
        	System.out.print(arr[i] +" ");
        }
        System.out.println(z.isNegative);
        
        //Num a = Num.power(x, 8);
        //System.out.println(a);
        //if (z != null) z.printList();
    }
}
