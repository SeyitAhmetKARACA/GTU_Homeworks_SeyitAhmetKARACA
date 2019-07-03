package zirhSan;

public class RocketLauncher extends weapon {
    suit suit;
    /**
     * AuroRifle kurucu methodu
     * @param suit
     */
    public RocketLauncher(suit suit) {
        this.suit = suit;
    }
    /**
     * Ekipmanın kıyafete eklenmesini sağlar
     * @return Description
     */
    public String getDescription() {
        return suit.getDescription() + ", RocketLauncher";
    }
    /**
     * Ekipmanın ağırlığı
     * @return double Ekipmanın ağırlığı
     */
    @Override
    public double weight() {
        return 7.5 + suit.weight();
    }
    /**
     * Ekipmanın fiyatı
     * @return double Ekipmanın fiyatı
     */
    @Override
    public double cost() {
        return 150000 + suit.cost();
    }
}