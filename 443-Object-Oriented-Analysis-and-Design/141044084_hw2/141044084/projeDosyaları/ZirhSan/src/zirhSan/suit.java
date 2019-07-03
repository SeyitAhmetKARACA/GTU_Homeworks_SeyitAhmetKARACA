package zirhSan;

public abstract class suit {
    String description = "Unknown suit";

    /**
     * Bu kıyafetin üzerine giyilebilecek
     * ekipmanları tutar.
     * @return string Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Kıyafet sınfının soyut methodları
     * @return weight
     */
    public abstract double weight();
    public abstract double cost();
}
