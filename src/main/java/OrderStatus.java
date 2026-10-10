public enum OrderStatus {
    CREATED,
    ASSIGNED,
    IN_DELIVERY,
    DELIVERED,
    CANCELLED,
    SELF_PICKUP,
    PICKED_BY_BUYER;

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
}
