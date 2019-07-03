package Payment;

public class ConcreteTurboPayment implements TurboPayment {
    /**
     * turboPayment soyut sinifi
     * @param turboCardNo kredi karti numarasi
     * @param turboAmount tutar
     * @param destinationTurboOfCourse hedef kart numarasi
     * @param installmentsButInTurbo taksit sayisi
     * @return tutar
     */
    @Override
    public int payInTurbo(String turboCardNo, float turboAmount, String destinationTurboOfCourse, String installmentsButInTurbo) {
        System.out.println(">> Turbo Payment'");
        System.out.println("turboCardNo :"+turboCardNo+"\ndestinationTurboOfCourse :"+destinationTurboOfCourse+"\ninstallmentsButInTurbo :"+installmentsButInTurbo);
        return (int) turboAmount;
    }
}
