package Payment;

public class ModernToTurboPaymentAdapter implements TurboPayment {

    ModernPayment mp;
    public ModernToTurboPaymentAdapter(ModernPayment _mp){
        mp = _mp;
    }

    /**
     *
     * turboPayment adapter , TurboPayment niteliklerini modern paymente dönüştürüyor
     * @param turboCardNo kredi karti numarasi
     * @param turboAmount tutar
     * @param destinationTurboOfCourse hedef kart numarasi
     * @param installmentsButInTurbo taksit sayisi
     * @return tutar
     */
    @Override
    public int payInTurbo(String turboCardNo, float turboAmount, String destinationTurboOfCourse, String installmentsButInTurbo) {
        System.out.println("Adapter Modern -> Turbo");
        return mp.pay(turboCardNo,turboAmount,destinationTurboOfCourse,installmentsButInTurbo);
    }
}
