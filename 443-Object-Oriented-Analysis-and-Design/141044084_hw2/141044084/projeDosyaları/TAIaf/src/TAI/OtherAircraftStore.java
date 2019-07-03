package TAI;

public class OtherAircraftStore extends AircraftStore {
    /**
     * Yurt içi ve avrupa olmayan uçak oluşturan fabrika
     * @param type aircraftTypes
     * @return Aircraft
     */
    @Override
    protected Aircraft createAircraft(aircraftTypes type) {

        AircraftIngredientFactory IngredientFactory = new OtherIngredientFactory();// DOLDUR
        if(type.equals(type.TPX100)){
            return new tpx100(IngredientFactory);
        }else if(type.equals(type.TPX200)){
            return new tpx200(IngredientFactory);
        }else if(type.equals(type.TPX300)){
            return new tpx300(IngredientFactory);
        }else
            return null;
    }
}
