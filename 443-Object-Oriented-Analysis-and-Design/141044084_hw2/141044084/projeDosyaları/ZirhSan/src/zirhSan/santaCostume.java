package zirhSan;

public class santaCostume extends suit {
    public santaCostume() {
        description = "HoHoHo";
    }

    @Override
    public double weight() {
        return 1;
    }

    public double cost() {
        return 3;
    }
}
