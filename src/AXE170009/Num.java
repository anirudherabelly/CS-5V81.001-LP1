// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

/* Developed by :
 * Anirudh Erabelly
 * Sai Krishna Reddy
 * Dinesh Reddy
 * Sreyas Reddy
 * */

// Change following line to your NetId
package AXE170009;
import java.util.Stack;

public class Num implements Comparable < Num > {
	
	static long defaultBase = (long) Math.pow(10, 5); //multiples of 10
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
            this.arr[this.len++] = x;
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
		int max = (a.len > b.len)?a.len : b.len;
		return max;
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
    	
    	int max = maxlen(a, b);
		Num res = new Num(a.base, max-1);
		
		int indexa = 0, indexb = 0;
		long borrow = 0l, diff;
		for(int i = 0; i < max; i++) {
			diff = indexa < a.len ? a.arr[indexa++] : 0;
			diff -= indexb < b.len ? b.arr[indexb++] : 0; 
			diff -= borrow;
			borrow = 0l;
			if(diff < 0) {
				diff += res.base;
				borrow = 1;
			}
			res.arr[res.len++] = diff;
		}
		trimNum(res);
		assignSign(res, isNegative);
		return res;
	}
    
    private static void trimNum(Num a) {
    	int len = 0;
    	for(int i = a.len - 1; i >= 0; i--) {
    		if(a.arr[i] > 0)break;
    		len++;
    	}
    	a.len -= len; 
    }
    
    public static Num product(Num a, Num b) {
    	Num res;
    	if(a.len >= b.len) {
    		res = karatsubasplit(a, b);
    	}
    	else {
    		res = karatsubasplit(b, a);
    	}
    	assignSign(res, a.isNegative ^ b.isNegative);
        return res;
    }

    //product of a Num and long
    private static Num product(Num n, long b) {
    	if(b == 0) {
    		return new Num(0, n.base);
    	}
    	if(b == 1) {
    		return n;
    	}
    	
    	int index = 0;
    	long carry = 0l, sum, temp;
    	Num res = new Num(n.base, n.len);
    	
    	while(index < n.len || carry > 0) {
    		temp = (index < n.len) ? n.arr[index++] : 0;
    		sum = ( temp * b + carry);
    		res.arr[res.len++] = sum % res.base;
    		carry = sum / res.base;
    	}
    	return res;
	}
    
    private static Num karatsubasplit(Num a, Num b) {
    	if(a.len == 0 || b.len == 0){
    		return new Num(0, a.base);
    	}
    	else if(a.len == 1 || b.len == 1) {
    		return b.len == 1 ? product(a, b.arr[0]) : product(b, a.arr[0]);
    	}
    	
    	int k = Math.max(a.len, b.len) / 2;
    	
    	Num aFirstHalf = splitNum(a, 0, k);
    	Num aSecondHalf = splitNum(a, k, a.len - k);
    	
    	Num bFirstHalf = splitNum(b, 0, k);
    	Num bSecondHalf = splitNum(b, k, b.len - k);
    	
    	Num partOne = karatsubasplit(aFirstHalf, bFirstHalf);
    	Num partThree = karatsubasplit(aSecondHalf, bSecondHalf);
    	Num partTwo = karatsubasplit(unsignedAdd(aFirstHalf, aSecondHalf), unsignedAdd(bFirstHalf, bSecondHalf));
    	
    	Num temp = unsignedSubtract(partTwo, unsignedAdd(partThree, partOne), false);
    	leftShift(temp, k);
    	leftShift(partThree, 2 * k);
    	
    	Num res = unsignedAdd(unsignedAdd(partThree,temp) ,partOne);
    	return res;
    }
    
    public static Num splitNum(Num n, int index, int k) {
    	if(k < 0)return new Num(0, n.base);
    	
		Num half = new Num(n.base, k-1);
		for(int i = 0; i < Math.min(k, n.len); i++) {
			half.arr[half.len++] = n.arr[i + index];
		}
		return half;
	}
    
    private static void assignSign(Num a, boolean isNegative) {
    	a.isNegative = a.len == 1 ? (a.arr[0] == 0 ? false : isNegative) : isNegative;
    }
    
    private static void leftShift(Num a, int k) {
    	long[] temp = new long[a.len + k];
    	for(int i = 0; i < k; i++) {
    		temp[i] = 0l;
    	}
    	for(int i = k; i < a.len + k; i++) {
    		temp[i] = a.arr[i-k];
    	}
    	a.arr = temp;
    	a.len += k;
    	a.sizeAllotted = a.len + k;
    }
    
    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
    	Num res;
    	if(unsignedCompareTo(b, ZERO) == 0) {
    		throw new IllegalArgumentException("Divided By Zero exception");
    	}
    	else if(unsignedCompareTo(b, ONE) == 0) {
    		res = a;
    	}
    	else {
    		int isGreater = unsignedCompareTo(a, b);
    		if(isGreater < 0) {
	    		res = ZERO;
	    	}
	    	else if(isGreater == 0) {
	    		res = ONE;
	    	}
	    	else {
	    		res = binarySearch(a, b, ONE, a);
	    	}
    	}
    	for(int i = 0; i < res.len; i++) {
    		System.out.print(res.arr[i]+" ");
    	}
    	System.out.println();
    	
    	assignSign(res, a.isNegative ^ b.isNegative);
        return res;
    }

    private static Num binarySearch(Num a, Num b, Num start, Num end) {
    	Num mid = unsignedAdd(start, end).by2();
		Num left = product(mid, b);
		Num right = unsignedAdd(left,b);
		
		int leftcomp = unsignedCompareTo(left, a);
		int rightcomp = unsignedCompareTo(a, right);
		
		if (leftcomp > 0) {
			return binarySearch(a, b, start, unsignedSubtract(mid, ONE, mid.isNegative));
		}
		else if (leftcomp <= 0 && rightcomp < 0) {
			return mid;
		}
		return binarySearch( a, b, unsignedAdd(mid, ONE), end);
	}
    
    // Divide by 2, for using in binary search
    public Num by2() {
    	Num res = product(this, this.base/2);
    	rightShift(res);
    	res.isNegative = this.isNegative;
        return res;
    }
    
    //right shifting the number by 1.
    private void rightShift(Num res) {
    	long[] tempArr = new long[res.len-1];
		for(int i = 1; i < res.len; i++) {
			tempArr[i-1] = res.arr[i];
		}
		res.arr = tempArr;
		res.len--;
	}
    
    // Use divide and conquer
    public static Num power(Num a, long n) {
		if(a.isZero()) return ZERO;
		if(n==0) return ONE;
		if(n==1) return a;
		
		if(n % 2 == 0) {
			Num res = power(a, n/2);
			return product(res, res);
		}
		return product(a, power(a, n-1));
	}
    
    //returns whether Num is of value 0 or not. 
    public boolean isZero() {
    	for(int i = 0; i < this.len; i++) {
    		if(this.arr[i] != 0) return false;
    	}
    	return true;
    }
    
    // return a%b
    public static Num mod(Num a, Num b){
		if(b.isZero()) {
			throw new ArithmeticException("Divisor - b is 0");
		}
		
		int compResult = a.compareTo(b);
		if(a.isZero() || compResult == 0) {
			return ZERO;
		} else if(compResult == -1) {
			return a;
		}
		return subtract(a, product(divide(a, b), b));
    }

    // Use binary search
    public static Num squareRoot(Num a) {
    	if(a.isNegative){
    		throw new ArithmeticException("Squareroot of negative no. cannnot be calculated.");
    	}
    	
    	if(a.compareTo(ZERO) == 0){
    		return ZERO;
    	}
    	
    	if(a.compareTo(ONE) == 0){
    		return ONE;
    	}
    	
    	// recursively finds the square root
		Num result = squareRootBinarySearch(a, ONE, a.by2());
		return result;
    }


    private static Num squareRootBinarySearch(Num a, Num start, Num end){
    	if(end.compareTo(ZERO) == 0|| end.compareTo(ONE) ==0  ) {
			return 	end;
		}
    	Num result = new Num(0, a.base);
		
		while (start.compareTo(end) == -1 || start.compareTo(end) == 0) {
			Num mid =add (start,end).by2();
			Num prod = product(mid, mid);
			if(prod.compareTo(a) == 0) {
				return mid;
			}
			if(prod.compareTo(a) == -1) {
				start = add(mid,ONE);
				result = mid;
			}
			else {
				end=subtract(mid, ONE);
			}
		}
		return result;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
		if(this.isNegative && !other.isNegative)
		{
	    	return -1;
	    } 
		else if(!this.isNegative && other.isNegative) {
			return 1;
	    }
		return unsignedCompareTo(this, other);
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    	System.out.print(base + ": ");
    	for(int i=0; i < this.len; i++) {
    		System.out.print(this.arr[i]+" ");
    	}
    	System.out.println();
    }

    // Return number to a string in base 10
    public String toString() {
		String res;
		StringBuilder sb = new StringBuilder();
		long baseSize = (int) Math.log10(this.base());
		
		for (int i = 0; i < this.len; i++) {
		      sb.insert(0, String.format("%0" + baseSize + "d", this.arr[i]));
	    }
	    res = sb.toString().replaceFirst("^0+(?!$)", "");
	    if(isNegative)res = "-"+res;
	    return res;
    }
    
    //return base of Num calling method.
    public long base() {
        return this.base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        long oldBase = this.base;
		int size = this.len;
		Num newNum = new Num(0);
		while(size > 0) {
			newNum = add(product(newNum, new Num(oldBase)), new Num(this.arr[--size], newBase));
		}
		newNum.isNegative = this.isNegative;
		return newNum;
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
        	String s = expr[i];
             
            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(isDigit(s)) {
                stack.push(new Num(s)); 
            }	
            	
             
            //  If the scanned character is an operator, pop two
            // elements from stack apply the operator
            else
            {
                Num val1 = stack.pop();
                Num val2 = stack.pop();
                 
                switch(s)
                {
                    case "+":
                    stack.push(add(val2,val1));
                    break;
                     
                    case "-":
                    stack.push(subtract(val2,val1));
                    break;
                     
                    case "*":
                	stack.push(divide(val2,val1));
                    break;
                     
                    case "/":
                    stack.push(product(val2,val1));
                    break;
				
                    case "%":
                    stack.push(mod(val2,val1));
                    break;
				
                    case "^":
                    stack.push(power(val2,Long.parseLong(val1.toString())));
                    break;	
              }
            }
        }
        return stack.pop();    
    }
    private static boolean isDigit(String str) {
        if(str == null || str.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))) {
                return false;
            } 
        }
        return true;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr){
		// Stack for numbers: 'digits' 
		Stack<Num> digits = new Stack<>(); 
		// Stack for Operators: 'operands' 
        Stack<Character> operands = new Stack<Character>();

        for (int i = 0; i < expr.length; i++) 
        {
            // Current token is a number, push it to stack for numbers 
            if (isDigit(expr[i])) 
            { 
                digits.push(new Num(expr[i])); 
            } 
  
            // Current token is an opening brace, push it to 'operands' 
            else if (expr[i].charAt(0) == '(') 
                operands.push(expr[i].charAt(0)); 
  
            // Closing brace encountered, solve entire brace 
            else if (expr[i].charAt(0) == ')') 
            { 
            	// call operation function to perform operations on the numbers popped from digits stack
                while (operands.peek() != '(') 
                  digits.push(operation(operands.pop(), digits.pop(), digits.pop())); 
                operands.pop(); 
            } 
  
            // Current token is an operator. 
            else if (expr[i].charAt(0) == '+' || expr[i].charAt(0) == '-' || 
            		expr[i].charAt(0) == '*' || expr[i].charAt(0) == '/' || expr[i].charAt(0) == '%' || expr[i].charAt(0) == '^') 
            { 
            	// while the peek operand stack has same or greater precedence than the current operator, 
            	//apply peek operand on two elements in the stack
                while (!operands.empty() && hasPrecedence(expr[i].charAt(0), operands.peek())) 
                  digits.push(operation(operands.pop(), digits.pop(), digits.pop())); 
  
                // Push current token to 'operands'. 
                operands.push(expr[i].charAt(0)); 
            } 
        }
        
        while (!operands.empty()) 
            digits.push(operation(operands.pop(), digits.pop(), digits.pop())); 
        	// return top of digits stack 
    	return digits.pop(); 
	} // end of infix evaluation
        
	//helper function for infix evaluation to check precedence of operands
    public static boolean hasPrecedence(char op1, char op2) 
    { 
        if (op2 == '(' || op2 == ')') 
            return false; 
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) 
            return false; 
        else
            return true; 
    } 
	
    //helper function for infix evaluation to apply operation on two numbers
    public static Num operation(char op, Num b, Num a) 
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
    	Num x = new Num(4675243);
		Num y = new Num("8969028");
		x.printList();
		y.printList();
		Num z = Num.subtract(x, y);
		if (z != null) {
			System.out.print("Subtraction result: ");
			z.printList();
			System.out.println(z.toString());
		}

		x = new Num(30893889);
		y = new Num("1207486488920");
		z = Num.subtract(x, y);
		if (z != null) {
			System.out.print("Subtraction result: ");
			z.printList();
			System.out.println(z.toString());
		}

		z = Num.add(x, y);
		if (z != null) {
			System.out.print("Addition result: ");
			z.printList();
			System.out.println(z.toString());
		}

		z = Num.product(x, 30);
		if (z != null) {
			System.out.print("Product(Num, long) result: ");
			z.printList();
			System.out.println(z.toString());
		}

		x = new Num("2324234883768392234288");
		x.by2().printList();


		x = new Num("1008");
		y = new Num("24");
		System.out.println("x/y result 42: " + Num.divide(x, y).toString());
		System.out.println("modulo result: " + Num.mod(x, new Num(5)).toString());

		x = new Num("12345674824890223483094848923");
		y = new Num("76543434345453");
		System.out.println(x.by2().toString());
		System.out.println("x/y result: " + Num.divide(x, y).toString());
		
		Num k= Num.evaluateInfix(new String[] { "(" ,"5" ,"+", "2","^", "2", ")" });
		System.out.println("infix evaluation = " + k.toString());
		Num k1 = Num.squareRoot(new Num("36"));
		System.out.println("Square root = " + k1);
		System.out.println("power of 3 and 999" +  Num.power(new Num(3), 999));
    }
}
