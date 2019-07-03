package tai;

public abstract class AircraftStore {
    Aircraft ac;

    enum aircraftTypes{
        TPX100,TPX200,TPX300
    }

    /**
     * Parametre olarak uçak tipi alıp, aldığı tipe göre
     * uçak oluşturuyor ve hazırlıyor.
     * @param type aircratfType
     * @return tai.Aircraft
     */
    public Aircraft orderAircraft(aircraftTypes type){
        ac = createAircraft(type);
        ac.prepare();
        return ac;
    }
    protected abstract Aircraft createAircraft(aircraftTypes type);
}