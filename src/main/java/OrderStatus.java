public enum OrderStatus {
    CREATED("Создан"),
    ASSIGNED("Курьер назначен"),
    IN_DELIVERY("В доставке"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменён"),
    SELF_PICKUP("Ожидает самовывоза"),
    PICKED_BY_BUYER("Получен покупателем");

    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case CREATED -> next == ASSIGNED ||
                next == CANCELLED ||
                next == SELF_PICKUP;
            case SELF_PICKUP -> next == PICKED_BY_BUYER ||
                next == CANCELLED ||
                next == CREATED;
            case ASSIGNED -> next == IN_DELIVERY ||
                next == CREATED ||
                next == CANCELLED;
            case IN_DELIVERY -> next == DELIVERED;
            case DELIVERED, CANCELLED, PICKED_BY_BUYER -> false;
        };
    }

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
