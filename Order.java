package Admin;

import FoodDelivery.FoodItem;
import Users.Customer;
import Delivery.DeliveryPerson;
import Delivery.DeliverySystem;

import java.util.Map;
import java.util.Optional;

public class Order {
    private Customer customer;
    private DeliveryPerson deliveryPerson;
    private Map<FoodItem, Integer> itemsOrdered;
    private String status;

    public Order(Customer customer, DeliverySystem system, Map<String, Integer> requestedItems) throws InvalidOrderException {
        this.customer = customer;
        this.itemsOrdered = new java.util.HashMap<>();
        // Validate and decrease inventory
        for (var entry : requestedItems.entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue();
            Optional<FoodItem> optional = system.inventory.keySet().stream()
                    .filter(item -> item.getName().equalsIgnoreCase(name))
                    .findFirst();
            if (optional.isEmpty()) throw new InvalidOrderException(name + " not available");
            FoodItem item = optional.get();
            int stock = system.inventory.get(item);
            if (stock < qty) throw new InvalidOrderException("Insufficient stock for " + name);
            system.inventory.put(item, stock - qty);
            itemsOrdered.put(item, qty);
        }
        // Assign delivery person
        Optional<DeliveryPerson> dp = system.deliveryPersons.stream()
                .filter(DeliveryPerson::isAvailable)
                .findFirst();
        if (dp.isEmpty()) throw new InvalidOrderException("No available delivery personnel");
        this.deliveryPerson = dp.get();
        deliveryPerson.setAvailable(false);
        system.orders.add(this);
        this.status = "Placed";
    }

    public void completeOrder() {
        this.status = "Completed";
        deliveryPerson.setAvailable(true);
    }

    public String orderDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order for ").append(customer.name).append(" - Items:\n");
        itemsOrdered.forEach((item, qty) -> sb.append(item).append(" x").append(qty).append("\n"));
        sb.append("Delivered by: ").append(deliveryPerson.name).append("\n");
        sb.append("Status: ").append(status);
        return sb.toString();
    }
    public String getStatus() {
        return status;
    }
}

