public class Proposition {
    public enum Relation {
        EQUALITY,           //lhs == rhs
        INEQUALITY,         //lhs != rhs
        LESS_THAN,          //lhs <  rhs
        LESS_THAN_OR_EQUAL, //lhs <= rhs
    }
    
    Relation relation;
    IntegerExpression lhs;
    IntegerExpression rhs;
    String lhsText, rhsText;

    public Proposition(Relation relation, String lhsText, String rhsText, IntegerExpression lhs, IntegerExpression rhs) {
        this.relation = relation;
        this.lhsText = lhsText;
        this.rhsText = rhsText;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public long getLhsAtN(int n) {
        return lhs.expression(n);
    }

    public long getRhsAtN(int n) {
        return rhs.expression(n);
    }

    public IntegerExpression getLhs(int n) {
        return lhs;
    }

    public IntegerExpression getRhs(int n) {
        return rhs;
    }

    public String getLhsText() {
        return lhsText;
    }

    public void setLhsText(String lhsText) {
        this.lhsText = lhsText;
    }

    public String getRhsText() {
        return rhsText;
    }

    public void setRhsText(String rhsText) {
        this.rhsText = rhsText;
    }

    public Relation getRelation() {
        return relation;
    }

    @Override
    public String toString() {
        char relationChar = '?';

        switch (this.relation) {
            case EQUALITY:
                relationChar = '=';
            break;
            case INEQUALITY:
                relationChar = '≠';
            break;
            case LESS_THAN:
                relationChar = '<';
            break;
            case LESS_THAN_OR_EQUAL:
                relationChar = '≤';
            break;
            default:
            break;
        }

        return this.lhsText + " " + relationChar + " " + this.rhsText;
    }

    public String writeValueAtN(int n) {
        return "n = " + n + ":\n\tLHS = " + lhs.expression(n) + "\n\tRHS = " + rhs.expression(n);
    }

    public boolean isTrueAtN(int n) {
        boolean isTrue = false;

        switch (this.relation) {
            case EQUALITY:
                if (lhs.expression(n) == rhs.expression(n)) {
                    isTrue = true;
                }
            break;
            case INEQUALITY:
                if (lhs.expression(n) != rhs.expression(n)) {
                    isTrue = true;
                }
            break;
            case LESS_THAN:
                if (lhs.expression(n) < rhs.expression(n)) {
                    isTrue = true;
                }
            break;
            case LESS_THAN_OR_EQUAL:
                if (lhs.expression(n) <= rhs.expression(n)) {
                    isTrue = true;
                }
            break;
        }

        return isTrue;
    }
}
