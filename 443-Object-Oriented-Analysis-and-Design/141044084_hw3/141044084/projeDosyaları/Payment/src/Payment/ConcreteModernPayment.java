package Payment;

public class ConcreteModernPayment implements ModernPayment {
    /**
     * ModernPayment soyut sinifi
     * @param cardNo kredi karti numarasi
     * @param amount tutar
     * @param destination hedef kredi karti numarasi
     * @param installments taksit
     * @return tutar
     */
    @Override
    public int pay(String cardNo, float amount, String destination, String installments) {
        System.out.println(">>> Modern Payment'");
        System.out.println("cardNo: "+cardNo+"\ndestination :"+destination+"\ninstallments :"+installments);
        return (int) amount;
    }
}
