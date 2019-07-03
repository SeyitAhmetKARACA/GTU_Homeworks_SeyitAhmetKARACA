package zirhSan;

public class ora extends suit {

    public ora() {
        description = "ora";
    }

    /**
     * Ora'nın ağırlığı
     * @return double Ora'nın ağırlığı
     */
    @Override
    public double weight() {
        return 30;
    }

    /**
     * Ora'nın fiyatı
     * @return double Ora'nın fiyatı
     */
    @Override
    public double cost() {
        return 1500000;
    }
}
