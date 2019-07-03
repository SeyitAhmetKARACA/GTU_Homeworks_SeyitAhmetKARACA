package zirhSan;

public class dec extends suit {
    public dec() {
        description = "Dec";
    }

    /**
     * Dec'in ağırlığı
     * @return double Dec'in ağırlığı
     */
    @Override
    public double weight() {
        return 25;
    }

    /**
     * Dec'in fiyatı
     * @return double Dec'in fiyatı
     */
    @Override
    public double cost() {
        return 500000;
    }
}