import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // P_1(n): (1*2) + (2*3) + (3*4) + ... + n(n+1) = n(n+1)(n+2)/3
        Proposition p1 = new Proposition(
            Proposition.Relation.EQUALITY,
            "+i·i+1",
            "n(n+1)(n+2)/3",
            n -> {
                long sum = 0;

                for (int i = 1; i <= n; i++) {
                    sum += i * (i + 1);
                }

                return sum;
            },
            n -> {
                return (n * (n + 1) * (n + 2)) / 3;
            }
        );

        // P_2(n): 1 + 4 + 7 + ... + (3n - 2) = n(3n - 1)/2
        Proposition p2 = new Proposition(
            Proposition.Relation.EQUALITY,
            "+3i~2",
            "n(3n - 1)/2",
            n -> {
                long sum = 0;

                for (int i = 1; i <= n; i++) {
                    sum += (3 * i) - 2;
                }

                return sum;
            },
            n -> {
                return (n * ((3 * n) - 1))/2;
            }
        );

        // P_3(n): 2(2^1) + 3(2^2) + 4(2^3) + ... + (n + 1)(2^n) = n(2^(n+1))
        Proposition p3 = new Proposition(
            Proposition.Relation.EQUALITY,
            "+i+1·2^i",
            "n·2^(n+1)",
            n -> {
                long sum = 0;

                for (int i = 1; i <= n; i++) {
                    sum += (i + 1) * (long)Math.pow(2,i);
                }

                return sum;
            },
            n -> {
                return n * (long)Math.pow(2, n + 1);
            }
        );

        // P_4(n): 1^2 + 2^2 + 3^2 + ... + n^2 = n*(n + 1)*(2*n + 1)/6
        Proposition p4 = new Proposition(
            Proposition.Relation.EQUALITY,
            "+i^2",
            "n(n + 1)(2n + 1)/6",
            n -> {
                long sum = 0;

                for (int i = 1; i <= n; i++) {
                    sum += i*i;
                }

                return sum;
            },
            n -> {
                return (n * (n + 1) * ((2 * n) + 1))/6;
            }
        );

        // P_5(n): 2 + 4 + 6 + ... + 2*n = n*(2*n + 2)/2
        Proposition p5 = new Proposition(
            Proposition.Relation.EQUALITY,
            "+2i",
            "n(2n + 2)/2",
            n -> {
                long sum = 0;

                for (int i = 1; i <= n; i++) {
                    sum += 2*i;
                }

                return sum;
            },
            n -> {
                return (n * ((2 * n) + 2))/2;
            }
        );

        // P_6(n): ∀n≥4(2^n < n!)
        Proposition p6 = new Proposition(
            Proposition.Relation.LESS_THAN,
            "·2",
            "n!",
            n -> {
                return (long)Math.pow(2, n);
            },
            n -> {
                long product = 1;

                // n!
                for (int i = 1; i <= n; i++) {
                    product *= i;
                }

                return product;
            }
        );
        
        // image resources
        Image[] descriptionImages = {
            new Image("p1.png"),
            new Image("p2.png"),
            new Image("p3.png"),
            new Image("p4.png"),
            new Image("p5.png"),
            new Image("p6.png"),
        };

        Image[] proofImages = {
            new Image("p1_proof.png"),
            new Image("p2_proof.png"),
            new Image("p3_proof.png"),
            new Image("p4_proof.png"),
            new Image("p5_proof.png"),
            new Image("p6_proof.png"),
        };

        Image[] relationImages = {
            new Image("equals.png"),
            new Image("not_equal.png"),
            new Image("less_than.png"),
            new Image("greater_than.png"),
            new Image("less_than_or_equal.png"),
            new Image("greater_than_or_equal.png"),
            new Image("empty.png")
        };

        // ImageViews
        ImageView descriptionImage = new ImageView();
        ImageView proofImage = new ImageView();
        ImageView relationImage = new ImageView();
        ImageView valueRelationImage = new ImageView();

        // label
        Label nLabel = new Label("n = ");

        // TextAreas
        Font font = new Font("Times new Roman", 18);

        TextArea lhsText = new TextArea("");
        lhsText.fontProperty().set(font);
        lhsText.wrapTextProperty().set(true);
        lhsText.prefHeightProperty().set(200);
        lhsText.prefWidthProperty().set(400);
        lhsText.setEditable(false);
        
        TextArea rhsText = new TextArea("");
        rhsText.fontProperty().set(font);
        rhsText.setEditable(false);
        rhsText.prefHeightProperty().set(200);
        rhsText.prefWidthProperty().set(400);
        
        TextArea valueText = new TextArea("");
        valueText.fontProperty().set(font);
        valueText.setEditable(false);
        valueText.prefHeightProperty().set(200);
        valueText.prefWidthProperty().set(400);

        // root node & object containing root node
        PropositionsListDisplayer<BorderPane> pld = new PropositionsListDisplayer<BorderPane>(
            new BorderPane(),
            lhsText,
            rhsText,
            descriptionImage,
            proofImage,
            relationImage,
            valueRelationImage,
            descriptionImages,
            proofImages,
            relationImages,
            p1,p2,p3,p4,p5,p6
        );

        updateRelationSymbols(pld,1,relationImages,valueText);

        // Spinners
        Spinner<Integer> nSpinner = new Spinner<Integer>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99,1));
        nSpinner.valueProperty().addListener(event -> {
            int spinnerValue = nSpinner.getValue();

            pld.updatePropositionText(spinnerValue);
            updateRelationSymbols(pld,spinnerValue,relationImages,valueText);
        });

        pld.setCurrentPage(0);

        // next/last page buttons
        Button prevButton = new Button("< prev");
        prevButton.setOnAction(event -> {
            pld.setCurrentPage(pld.getCurrentPage() - 1);
            pld.updatePropositionText(nSpinner.getValue());

            updateRelationSymbols(pld,nSpinner.getValue(),relationImages,valueText);
           
            if (pld.propositions.get(pld.getCurrentPage()).getRelation() == Proposition.Relation.EQUALITY) {
                valueText.setText(pld.propositions.get(pld.getCurrentPage()).getLhsAtN(nSpinner.getValue())+"");
            }
            else {
                valueText.setText(
                    "LHS = " + pld.propositions.get(pld.getCurrentPage()).getLhsAtN(nSpinner.getValue()) +
                    "\nRHS = " + pld.propositions.get(pld.getCurrentPage()).getRhsAtN(nSpinner.getValue())
                    );
            }
        });
        prevButton.setAlignment(Pos.CENTER_LEFT);

        Button nextButton = new Button("next >");
        nextButton.setOnAction(event -> {
            pld.setCurrentPage(pld.getCurrentPage() + 1);
            pld.updatePropositionText(nSpinner.getValue());

            updateRelationSymbols(pld,nSpinner.getValue(),relationImages,valueText);

            if (pld.propositions.get(pld.getCurrentPage()).getRelation() == Proposition.Relation.EQUALITY) {
                valueText.setText(pld.propositions.get(pld.getCurrentPage()).getLhsAtN(nSpinner.getValue())+"");
            }
            else {
                valueText.setText(
                    "LHS = " + pld.propositions.get(pld.getCurrentPage()).getLhsAtN(nSpinner.getValue()) +
                    "\nRHS = " + pld.propositions.get(pld.getCurrentPage()).getRhsAtN(nSpinner.getValue())
                    );
            }
        });
        nextButton.setAlignment(Pos.CENTER_RIGHT);

        HBox topRoot = new HBox(15,prevButton, descriptionImage, nextButton);
        topRoot.setAlignment(Pos.TOP_CENTER);

        HBox leftRoot = new HBox(nLabel,nSpinner);
        leftRoot.setAlignment(Pos.BOTTOM_LEFT);
        leftRoot.setPadding(new Insets(10));

        HBox bottomRoot = new HBox(lhsText,relationImage,rhsText,valueRelationImage,valueText);
        bottomRoot.setAlignment(Pos.BOTTOM_CENTER);

        // add everything to root node
        pld.getRoot().setTop(topRoot);
        pld.getRoot().setLeft(leftRoot);
        pld.getRoot().setBottom(bottomRoot);
        pld.getRoot().setCenter(proofImage);
        pld.getRoot().setPadding(new Insets(20));

        Scene scene = new Scene(pld.getRoot());
        stage.setScene(scene);
        stage.setTitle("Comp-Induction");
        stage.show();
    }

    public static void updateRelationSymbols(
        PropositionsListDisplayer pld, int spinnerValue,
        Image[] relationImages, TextArea valueText
    ) {
        long lhsValue = pld.getProposition(pld.getCurrentPage()).getLhsAtN(spinnerValue);
        long rhsValue = pld.getProposition(pld.getCurrentPage()).getRhsAtN(spinnerValue);
        
        if (pld.getProposition(pld.getCurrentPage()).getRelation() == Proposition.Relation.EQUALITY) {
            valueText.setText(lhsValue+"");
            pld.getRelationImageView().setImage(relationImages[0]);
            pld.getValueRelationImageView().setImage(relationImages[0]);
        }
        else {
            valueText.setText("LHS = " + lhsValue + "\nRHS = " + rhsValue );
            pld.getValueRelationImageView().setImage(relationImages[6]);
            
            if (lhsValue == rhsValue) {
                pld.getRelationImageView().setImage(relationImages[0]);
            }
            else if (lhsValue > rhsValue) {
                pld.getRelationImageView().setImage(relationImages[3]);
            }
            else if (lhsValue < rhsValue) {
                pld.getRelationImageView().setImage(relationImages[2]);
            }
        }
    }
}