package TAI;

public class Main {

    public static void main(String[] args) {
        AircraftStore storeEU = new EurasiaAircraftStore();
        System.out.println("");
        Aircraft ucak =storeEU.orderAircraft(AircraftStore.aircraftTypes.TPX200);
        ucak.showProperty();
        System.out.println("");
        AircraftStore storeEU2 = new OtherAircraftStore();
        ucak =storeEU2.orderAircraft(AircraftStore.aircraftTypes.TPX200);
        ucak.showProperty();
        System.out.println("");
        AircraftStore storeEU3 = new DomesticAircraftStore();
        ucak =storeEU3.orderAircraft(AircraftStore.aircraftTypes.TPX200);
        ucak.showProperty();
    }
}
