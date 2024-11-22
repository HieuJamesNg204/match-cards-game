import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MatchCards {
    // track card names
    String[] cardList = {
            "darkness", "double", "fairy",
            "fighting", "fire", "grass",
            "lightning", "metal", "psychic",
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

    JFrame frame = new JFrame("Match Cards");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartGamePanel = new JPanel();
    JButton restartButton = new JButton("Restart Game");

    int mistakes = 0;
    ArrayList<JButton> board;

    Timer hideCardTimer;
    boolean gameReady = false;

    JButton firstSelectedCard;
    JButton secondSelectedCard;

    public MatchCards() {
        setupCards();
        shuffleCards();
        setupUI();
        start();
    }

    public void start() {
        hideCardTimer = new Timer(1500, e -> hideCards());
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();
    }

    public void setupUI() {
        frame.setLayout(new BorderLayout());
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
        for (Card card : cardSet) {
            JButton cardButton = new JButton();
            cardButton.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            cardButton.setOpaque(true);
            cardButton.setIcon(card.getCardImage());
            cardButton.setFocusable(false);
            cardButton.addActionListener(e -> {
                if (!gameReady) {
                    return;
                }
                JButton selectedCard = (JButton) e.getSource();
                if (selectedCard.getIcon().equals(cardBackImageIcon)) {
                    if (firstSelectedCard == null) {
                        firstSelectedCard = selectedCard;
                        int index = board.indexOf(firstSelectedCard);
                        firstSelectedCard.setIcon(cardSet.get(index).getCardImage());
                    } else if (secondSelectedCard == null) {
                        secondSelectedCard = selectedCard;
                        int index = board.indexOf(secondSelectedCard);
                        secondSelectedCard.setIcon(cardSet.get(index).getCardImage());

                        if (!firstSelectedCard.getIcon().equals(secondSelectedCard.getIcon())) {
                            mistakes++;
                            textLabel.setText("Mistakes: " + mistakes);
                            hideCardTimer.start();
                        } else {
                            firstSelectedCard = null;
                            secondSelectedCard = null;
                        }
                    }
                }
            });
            board.add(cardButton);
            boardPanel.add(cardButton);
        }
        frame.add(boardPanel);

        // Restart game button
        restartButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        restartButton.setPreferredSize(new Dimension(BOARD_WIDTH, 30));
        restartButton.setFocusable(false);
        restartButton.setEnabled(false);
        restartButton.addActionListener(e -> {
            if (!gameReady) {
                return;
            }

            gameReady = false;
            restartButton.setEnabled(false);
            firstSelectedCard = null;
            secondSelectedCard = null;
            shuffleCards();

            // Reassign buttons with new cards
            for (int i = 0; i < board.size(); i++) {
                board.get(i).setIcon(cardSet.get(i).getCardImage());
            }

            mistakes = 0;
            textLabel.setText("Mistakes: " + mistakes);
            hideCardTimer.start();
        });
        restartGamePanel.add(restartButton);

        frame.add(restartGamePanel, BorderLayout.SOUTH);

        frame.pack();

        Image programIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/card-icon.png")))
                .getImage();
        frame.setIconImage(programIcon);

        frame.setLocationRelativeTo(null);
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
        cardSet.addAll(new ArrayList<>(cardSet));

        Image cardBackImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/back.jpg")))
                .getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH));
    }

    public void shuffleCards() {
        for (int i = 0; i < cardSet.size(); i++) {
            int j = (int) (Math.random() * cardSet.size()); // Pick a random index
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
    }

    public void hideCards() {
        if (gameReady && firstSelectedCard != null && secondSelectedCard != null) {
            firstSelectedCard.setIcon(cardBackImageIcon);
            firstSelectedCard = null;
            secondSelectedCard.setIcon(cardBackImageIcon);
            secondSelectedCard = null;
        } else {
            for (JButton cardButton : board) {
                cardButton.setIcon(cardBackImageIcon);
            }
            gameReady = true;
            restartButton.setEnabled(true);
        }
    }
}