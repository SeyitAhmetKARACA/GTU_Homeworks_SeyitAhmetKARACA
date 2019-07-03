package tai;

public class TPXStore extends AircraftStore {

    /**
     * Parametre olarak verilen uçak tipi ile
     * uçak oluşturup return eder.
     * @param type aircraftTypes
     * @return tai.Aircraft
     */
    @Override
    protected Aircraft createAircraft(aircraftTypes type) {
        if(type.equals(type.TPX100)){
            return new tpx100();
        }else if(type.equals(type.TPX200)){
            return new tpx200();
        }else if(type.equals(type.TPX300)){
            return new tpx300();
        }else
            return null;
    }
}
