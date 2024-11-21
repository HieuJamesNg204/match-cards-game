import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MatchCards {
    // track card names
    String[] cardList = {
            "darkness",
            "double",
            "fairy",
            "fighting",
            "fire",
            "grass",
            "lightning",
            "metal",
            "psychic",
            "water"
    };

    final int ROWS = 4;
    final int COLUMNS = 5;
    final int CARD_WIDTH = 90;
    final int CARD_HEIGHT = 128;

    ArrayList<Card> cardSet; // Create a deck of cards with card names and card images
    ImageIcon cardBackImageIcon;

    final int BOARD_WIDTH = COLUMNS * CARD_WIDTH; // 5 * 128 = 640px
    final int BOARD_HEIGHT = ROWS * CARD_HEIGHT; // 4 * 90 = 360px

    JFrame frame = new JFrame("Pokemon Match Cards");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    int mistakes = 0;
    ArrayList<JButton> board;

    public MatchCards() {
        setupCards();
        shuffleCards();

        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Mistakes: " + mistakes);

        textPanel.setPreferredSize(new Dimension(BOARD_WIDTH, 30));
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Card game board
        board = new ArrayList<>();
        boardPanel.setLayout(new GridLayout(ROWS, COLUMNS));
        for (int i = 0; i < cardSet.size(); i++) {
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            tile.setOpaque(true);
            tile.setIcon(cardSet.get(i).getCardImage());
            tile.setFocusable(false);
            board.add(tile);
            boardPanel.add(tile);
        }
        frame.add(boardPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public void setupCards() {
        cardSet = new ArrayList<>();
        for (String cardName : cardList) {
            // Load each card image
            Image cardImg = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/" + cardName + ".jpg")
            )).getImage();
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(
                    CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH
            ));

            // Create card object and add to cardSet
            Card card = new Card(cardName, cardImageIcon);
            cardSet.add(card);
        }

        Image cardBackImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/back.jpg")))
                .getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH));
    }


    public void shuffleCards() {
        for (int i = 0; i < cardSet.size(); i++) {
            int j = (int) (Math.random() * cardSet.size());
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
    }
}