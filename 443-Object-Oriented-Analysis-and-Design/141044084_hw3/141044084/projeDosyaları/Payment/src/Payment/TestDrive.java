package Payment;

public class TestDrive {
    public static void main(String[] args) {
        ConcreteModernPayment modern = new ConcreteModernPayment();
        TurboPayment turboPayment = new ModernToTurboPaymentAdapter(modern);

        ConcreteTurboPayment turbo = new ConcreteTurboPayment();
        ModernPayment modernPayment =new TurboToModernPaymentAdapter(turbo);

        modern.pay("141044044",80,"077","No");
        System.out.println("------");
        turbo.payInTurbo("141044088",900,"97","Yes");
        System.out.println("------");

        System.out.println("");
        modernPayment.pay("141044088",900,"97","Yes");
        System.out.println("");
        turboPayment.payInTurbo("141044044",80,"077","No");

    }
}