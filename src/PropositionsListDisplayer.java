import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PropositionsListDisplayer <T extends Pane> extends PropositionsList {
    T root; //can be any root node which extends Pane
    int currentPage; //stores the current page/proposition being viewed

    // Images
    Image[] descriptions;
    Image[] proofs;
    Image[] relations;

    // Page-specific components
    ImageView descriptionImageView, proofImageView, relationImageView, valueRelationImageView;
    TextArea lhsTextArea, rhsTextArea;

    // lhs/rhs texts are declared here becuase they change from user input
    String[] lhsTexts;
    String[] rhsTexts;

    public PropositionsListDisplayer(
        T root,
        TextArea lhsTextArea, TextArea rhsTextArea, 
        ImageView descriptionImageView, ImageView proofImageView,
        ImageView relationImageView, ImageView valueRelationImageView,
        Image[] descriptions, Image[] proofs, Image[] relations,
        Proposition... propsList) {

        super(propsList);
        this.root = root;
        this.lhsTextArea = lhsTextArea;
        this.rhsTextArea = rhsTextArea;
        this.descriptionImageView = descriptionImageView;
        this.proofImageView = proofImageView;
        this.relationImageView = relationImageView;
        this.valueRelationImageView = valueRelationImageView;
        this.descriptions = descriptions;
        this.proofs = proofs;
        this.relations = relations;
        this.currentPage = 0;
    }

    public T getRoot() {
        return root;
    }

    public ArrayList<Proposition> getProps() {
        return propositions;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public ImageView getDescriptionImageView() {
        return descriptionImageView;
    }

    public void setDescriptionImageView(ImageView descriptionImageView) {
        this.descriptionImageView = descriptionImageView;
    }

    public ImageView getProofImageView() {
        return proofImageView;
    }

    public void setProofImageView(ImageView proofImageView) {
        this.proofImageView = proofImageView;
    }

    public ImageView getRelationImageView() {
        return relationImageView;
    }

    public void setRelationImageView(ImageView relationImageView) {
        this.relationImageView = relationImageView;
    }

    public ImageView getValueRelationImageView() {
        return valueRelationImageView;
    }

    public void setValueRelationImageView(ImageView valueRelationImageView) {
        this.valueRelationImageView = valueRelationImageView;
    }

    public TextArea getLhsTextArea() {
        return lhsTextArea;
    }

    public void setLhsTextField(TextArea lhsTextField) {
        this.lhsTextArea = lhsTextField;
    }

    public TextArea getRhsTextArea() {
        return rhsTextArea;
    }

    public void setRhsTextField(TextArea rhsTextField) {
        this.rhsTextArea = rhsTextField;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 0 || currentPage >= this.propositions.size()) {
            return;
        }

        this.descriptionImageView.setImage(descriptions[currentPage]);
        this.proofImageView.setImage(proofs[currentPage]);

        this.currentPage = currentPage;
    }

    public void updatePropositionText(int n) {
        String lhsTerm = this.propositions.get(this.currentPage).getLhsText().substring(1);
        char lhsOperand = this.propositions.get(this.currentPage).getLhsText().charAt(0);
        String newLhsText = Arithmetic.simplifyExpression(lhsTerm, 1);
        
        for (int i = 2; i <= n; i++) {
            newLhsText += " " + lhsOperand + " " + Arithmetic.simplifyExpression(lhsTerm, i);
        }

        //newLhsText = newLhsText.substring(2);

        this.lhsTextArea.setText(newLhsText);
        //System.out.println(newLhsText);
        this.rhsTextArea.setText(this.propositions.get(this.currentPage).getRhsText());
    }
}
