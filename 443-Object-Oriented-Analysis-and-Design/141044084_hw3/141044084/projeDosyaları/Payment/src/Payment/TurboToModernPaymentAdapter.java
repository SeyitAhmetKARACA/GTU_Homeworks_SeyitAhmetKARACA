package Payment;

public class TurboToModernPaymentAdapter implements ModernPayment{
    TurboPayment tp;
    public TurboToModernPaymentAdapter(TurboPayment _tp){
        tp = _tp;
    }

    /**
     * ModernPayment adapter , ModernPayment niteliklerini turbo paymente dönüştürüyor
     * @param cardNo kredi karti numarasi
     * @param amount tutar
     * @param destination hedef kredi karti numarasi
     * @param installments taksit
     * @return tutar
     */
    @Override
    public int pay(String cardNo, float amount, String destination, String installments) {
        System.out.println("Adapter turbo -> Modern");
        return tp.payInTurbo(cardNo,amount,destination,installments);
    }
}
