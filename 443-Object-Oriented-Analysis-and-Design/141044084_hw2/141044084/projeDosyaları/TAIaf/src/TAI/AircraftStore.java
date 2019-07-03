package TAI;

public abstract class AircraftStore {
    Aircraft ac;

    /**
     * aircraftTypes tanımlamasıdır.
     */
    enum aircraftTypes{
        TPX100,TPX200,TPX300
    }

    /**
     * aircraftTypes parametresi ile gelen parametreye uygun
     * aircraft oluşturup , hazırlanmasını sağlayıp return etmektedir.
     * @param type aircraftTpes
     * @return Aircraft
     */
    public Aircraft orderAircraft(aircraftTypes type){
        ac = createAircraft(type);
        ac.prepare();
        return ac;
    }
    protected abstract Aircraft createAircraft(aircraftTypes type);
}