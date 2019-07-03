package zirhSan;

public class tor extends suit {
    public tor() {
        description = "tor";
    }

    /**
     * Tor'un ağırlığı
     * @return kıyafet ağırlığı
     */
    @Override
    public double weight() {
        return 50;
    }

    /**
     * Tor'un fiyatı
     * @return kıyafet fiyatı
     */
    @Override
    public double cost() {
        return 5000000;
    }
}