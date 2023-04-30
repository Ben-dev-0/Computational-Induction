import java.util.ArrayList;

public class PropositionsList {
    ArrayList<Proposition> propositions;

    public PropositionsList (Proposition... list) {
        propositions = new ArrayList<>();
        
        for (Proposition p : list) {
            propositions.add(p);
        }
    }

    public Proposition getProposition(int n) {
        return propositions.get(n);
    }
}
