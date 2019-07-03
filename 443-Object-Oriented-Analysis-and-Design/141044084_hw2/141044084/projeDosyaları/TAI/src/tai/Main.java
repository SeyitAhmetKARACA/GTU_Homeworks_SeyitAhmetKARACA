package tai;

public class Main {

    public static void main(String[] args) {
        AircraftStore store = new TPXStore();
        Aircraft ucak;
        ucak = store.orderAircraft(AircraftStore.aircraftTypes.TPX100);
        ucak.showProperty();
        ucak = store.orderAircraft(AircraftStore.aircraftTypes.TPX200);
        ucak.showProperty();
        ucak = store.orderAircraft(AircraftStore.aircraftTypes.TPX300);
        ucak.showProperty();
    }
}
