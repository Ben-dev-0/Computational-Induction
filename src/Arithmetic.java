import java.util.HashSet;

public class Arithmetic {
    public static String simplifyExpression(String expression, int... varInputs) {
        String result = expression.replace(" ","");
        HashSet<Character> varsSet = new HashSet<Character>();
        Character[] varsArray;
        int currentColonIndex;

        //assign variables
        for (int i = 0; i < result.length(); i++) {
            if (Character.isAlphabetic(result.charAt(i))) {
                varsSet.add(result.charAt(i));
            }
        }

        varsArray = varsSet.toArray(new Character[varsSet.size()]);

        //substitution
        for (int i = 0; i < varsArray.length; i++) {
            result = result.replace(varsArray[i]+"", ":" + varInputs[i] + ":");
        }

        currentColonIndex = result.indexOf(":");

        while (currentColonIndex >= 0) {
            if (
                currentColonIndex != 0 && currentColonIndex != result.length()-1 &&
                Character.isDigit(result.charAt(currentColonIndex - 1)) &&
                (Character.isDigit(result.charAt(currentColonIndex + 1)) || result.charAt(currentColonIndex + 1) == '-')
            ) {
                result = result.replaceFirst(":","*");
            }
            else {
                result = result.replaceFirst(":","");
            }

            currentColonIndex = result.indexOf(":");
        }

        result = result.replace("~-","+");                            //remove double negatives
        result = operationStep(result,((x, y) -> {return x*y;}),'*'); //multiply
        result = operationStep(result,((x, y) -> {return x/y;}),'/'); //divide
        result = operationStep(result,((x, y) -> {return x+y;}),'+'); //add
        result = operationStep(result,((x, y) -> {return x-y;}),'~'); //subtract
        
        return result;
    }

    private static String operationStep(String expression, IntegerOperation operation, char connective) {
        String result = expression;
        //System.out.println("formatted:" + result);
        int nextOperationIndex = nextOperationIndex(result,connective);
        //System.out.println("[" + connective + ":" + nextOperationIndex + "]");

        while (nextOperationIndex > 0) {
            int leftIndex = nextOperationIndex(result,connective) - 1;
            int rightIndex = nextOperationIndex(result,connective) + 1;
            String left = "";
            String right = "";
            
            //left side
            while (leftIndex >= 0 && (Character.isDigit(result.charAt(leftIndex)) || result.charAt(leftIndex) == '-')) {
                left = (result.charAt(leftIndex)+"").concat(left);
                
                if (result.charAt(leftIndex) == '-') {
                    leftIndex = -1;
                }
                else {
                    leftIndex--;
                }
            }

            //right side
            while (rightIndex < result.length() && (Character.isDigit(result.charAt(rightIndex)) || result.charAt(rightIndex) == '-')) {
                right += result.charAt(rightIndex);
                rightIndex++;
            }

            result = result.replace(
                left + connective + right,
                operation.calculate(Integer.parseInt(left), Integer.parseInt(right)) + ""
            );
        
            nextOperationIndex = nextOperationIndex(result,connective);
        }
        
        return result;
    }

    private static int nextOperationIndex(String s, char connective) {
        for (int i = 1; i < s.length() - 1; i++) {
            if (Character.isDigit(s.charAt(i-1)) && s.charAt(i) == connective && (Character.isDigit(s.charAt(i+1)) || s.charAt(i+1) == '-')) {
                return i;
            }
        }

        return -1;
    }
    /*
    public static void arithmeticTest() {
        System.out.println(simplifyExpression("12~30"));            // -18
        System.out.println(simplifyExpression("72~55"));            // 17
        System.out.println(simplifyExpression("15*6"));             // 90
        System.out.println(simplifyExpression("5*6"));              // 30
        System.out.println(simplifyExpression("10*2+2"));           // 22
        System.out.println(simplifyExpression("2x", 3));            // 6
        System.out.println(simplifyExpression("6x~245", -10));      // -305
        System.out.println(simplifyExpression("x+3", -1));          // 2
        System.out.println(simplifyExpression("2x~5yz", 3, 2, 4));  // -34
        System.out.println(simplifyExpression("1 ~ -7 + -4 ~ 3"));  // 1
        System.out.println(simplifyExpression("(4i~36)/11~2", 3));  // (-24)/9
        System.out.println(simplifyExpression("3n*n", 45));         // 6075
        System.out.println(simplifyExpression("v*v*v ~ v", 15));    // 3360
        System.out.println(simplifyExpression("-ab", 9,4));         // -36
        System.out.println(simplifyExpression("-ab-c", 9,4,2));     // 72
        System.out.println(simplifyExpression("-ab-c", -9,4,2));    // -72
        System.out.println(simplifyExpression("9*10`10*11`11*12`12*13`13*14"));    // 90`110`132`156`182
        System.out.println(simplifyExpression("140x/4", 3));        //105
        System.out.println(simplifyExpression("-x-y-x-y", 47,25));  //1380625
        System.out.println(simplifyExpression("15/2~8"));           //-1
    }*/
}
