package zirhSan;

public class Flamethrower extends weapon {
    suit suit;
    /**
     * Flamethrower kurucu methodu
     * @param suit
     */
    public Flamethrower(suit suit) {
        this.suit = suit;
    }

    /**
     * Ekipmanın kıyafete eklenmesini sağlar
     * @return String Description
     */
    public String getDescription() {
        return suit.getDescription() + ", Flamethrower";
    }
    /**
     * Ekipmanın ağırlığı
     * @return double Ekipmanın ağırlığı
     */
    @Override
    public double weight() {
        return 2 + suit.weight();
    }
    /**
     * Ekipmanın fiyatı
     * @return double Ekipmanın fiyatı
     */
    @Override
    public double cost() {
        return 50000 + suit.cost();
    }
}
