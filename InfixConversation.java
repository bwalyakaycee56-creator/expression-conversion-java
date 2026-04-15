import java.util.Stack;
import java.util.Scanner;

public class InfixConverter {

    // ========================
    // Helper Method: Check if character is operand
    // ========================
    private static boolean isOperand(char ch) {
        return Character.isLetterOrDigit(ch);
    }

    // ========================
    // Helper Method: Get precedence of operator
    // ========================
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    // ========================
    // Convert Infix to Postfix
    // ========================
    public static String infixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            // Skip spaces
            if (ch == ' ') {
                continue;
            }

            // If operand, add to output
            if (isOperand(ch)) {
                postfix.append(ch);
            }
            // If '(', push to stack
            else if (ch == '(') {
                stack.push(ch);
            }
            // If ')', pop until '('
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Remove '('
                }
            }
            // If operator
            else {
                while (!stack.isEmpty() && 
                       precedence(stack.peek()) >= precedence(ch) &&
                       stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        // Pop all remaining operators
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    // ========================
    // Convert Infix to Prefix
    // ========================
    public static String infixToPrefix(String infix) {
        // Step 1: Reverse the infix expression
        StringBuilder reversed = new StringBuilder(infix).reverse();

        // Step 2: Swap '(' and ')'
        for (int i = 0; i < reversed.length(); i++) {
            if (reversed.charAt(i) == '(') {
                reversed.setCharAt(i, ')');
            } else if (reversed.charAt(i) == ')') {
                reversed.setCharAt(i, '(');
            }
        }

        // Step 3: Convert to postfix
        String postfix = infixToPostfix(reversed.toString());

        // Step 4: Reverse the postfix to get prefix
        return new StringBuilder(postfix).reverse().toString();
    }

    // ========================
    // Main Method
    // ========================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  INFIX TO POSTFIX & PREFIX CONVERTER  ");
        System.out.println("========================================\n");

        System.out.print("Enter infix expression: ");
        String infix = scanner.nextLine();

        // Validate input
        if (infix.isEmpty()) {
            System.out.println("Error: Empty expression!");
            scanner.close();
            return;
        }

        // Convert and display results
        String postfix = infixToPostfix(infix);
        String prefix = infixToPrefix(infix);

        System.out.println("\n----------------------------------------");
        System.out.println("RESULTS:");
        System.out.println("----------------------------------------");
        System.out.println("Infix:   " + infix);
        System.out.println("Postfix: " + postfix);
        System.out.println("Prefix:  " + prefix);
        System.out.println("----------------------------------------");

        scanner.close();
    }
}
