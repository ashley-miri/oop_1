public enum TypeDelivery {
    ORDINARY("Обычная", 300, "3 дня"),
    EXPRESS("Скоростная", 600, "1 дня"),
    SELF_PICKUP("Самовывоз", 0, "готов к выдаче сегодня");

    private final String label;
    private final int price;
    private final String term;

    TypeDelivery(String label, int price, String term) {
        this.label = label;
        this.price = price;
        this.term = term;
    }

    public String getLabel() {
        return label;
    }

    public int getPrice() {
        return price;
    }

    public String getTerm() {
        return term;
    }
}
