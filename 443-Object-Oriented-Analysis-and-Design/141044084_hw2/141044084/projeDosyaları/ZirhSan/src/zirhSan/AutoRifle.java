package zirhSan;

public class AutoRifle extends weapon {
    suit suit;

    /**
     * AuroRifle kurucu methodu
     * @param suit
     */
    public AutoRifle(suit suit) {
        this.suit = suit;
    }

    /**
     * Ekipmanın kıyafete eklenmesini sağlar
     * @return Description
     */
    public String getDescription() {
        return suit.getDescription() + ", AutoRifle";
    }

    /**
     * Ekipmanın ağırlığı
     * @return Ekipman ağırlığı
     */
    @Override
    public double weight() {
        return 1.5 + suit.weight();
    }

    /**
     * Ekipmanın fiyatı
     * @return ekipman fiyatı
     */
    @Override
    public double cost() {
        return 30000 + suit.cost();
    }
}