package Admin;

import Delivery.DeliveryPerson;
import FoodDelivery.FoodItem;
import Users.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RoleCheck(role = "Manager")
public class Manager extends User {
    public Manager(String id, String name) {
        super(id, name);
    }

    @Override
    public void showProfile() {
        System.out.println("Manager ID: " + id + ", Name: " + name);
    }

    public void removeDeliveryPerson(List<DeliveryPerson> list, String dpId) throws Exception {
        // Validate role via reflection
        RoleCheck annotation = this.getClass().getAnnotation(RoleCheck.class);

        if (annotation == null || !"Manager".equals(annotation.role())) {
            throw new IllegalAccessException("User role not authorized to remove delivery personnel");
        }
        list.removeIf(dp -> dp.id.equals(dpId));
    }

    public void restockItem(Map<FoodItem, Integer> inventory, String itemName, int quantity) {
        Optional<FoodItem> existing = inventory.keySet().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst();
        if (existing.isPresent()) {
            inventory.put(existing.get(), inventory.get(existing.get()) + quantity);
            System.out.println("Restocked " + itemName + " by " + quantity);
        } else {
            System.out.println(itemName + " not found. Adding new item.");
            // Example price default; in real case prompt
            addNewItem(inventory, itemName, 0.0, quantity);
        }
    }
    public void addNewItem(Map<FoodItem, Integer> inventory, String name, double price, int quantity) {
        FoodItem item = new FoodItem(name, price);
        inventory.put(item, quantity);
        System.out.println("Added new item: " + item + " Qty: " + quantity);
    }
}
