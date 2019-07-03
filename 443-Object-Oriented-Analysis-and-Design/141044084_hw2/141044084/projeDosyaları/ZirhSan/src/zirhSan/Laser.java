package zirhSan;

public class Laser extends weapon {
    suit suit;
    /**
     * Lazer kurucu methodu
     * @param suit
     */
    public Laser(suit suit) {
        this.suit = suit;
    }

    public String getDescription() {
        return suit.getDescription() + ", Laser";
    }
    /**
     * Ekipmanın ağırlığı
     * @return double Ekipmanın ağırlığı
     */
    @Override
    public double weight() {
        return 5.5 + suit.weight();
    }
    /**
     * Ekipmanın fiyatı
     * @return double Ekipmanın fiyatı
     */
    @Override
    public double cost() {
        return 200000 + suit.cost();
    }
}