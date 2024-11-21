import javax.swing.*;

public class Card {
    private String cardName;
    private ImageIcon cardImage;

    public Card(String cardName, ImageIcon cardImage) {
        this.cardName = cardName;
        this.cardImage = cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public ImageIcon getCardImage() {
        return cardImage;
    }

    public void setCardImage(ImageIcon cardImage) {
        this.cardImage = cardImage;
    }

    @Override
    public String toString() {
        return cardName;
    }
}