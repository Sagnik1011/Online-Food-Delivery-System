package Delivery;

import Users.Customer;
import FoodDelivery.FoodItem;
import Admin.InvalidOrderException;
import Admin.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeliverySystem {
    List<Customer> customers = new ArrayList<>();
    List<DeliveryPerson> deliveryPersons = new ArrayList<>();
    List<Order> orders = new ArrayList<>();
    Map<FoodItem, Integer> inventory = new java.util.HashMap<>();

    public void registerCustomer(Customer c) {
        customers.add(c);
    }

    public void registerDeliveryPerson(DeliveryPerson dp) {
        deliveryPersons.add(dp);
    }

    public void addFoodItem(FoodItem item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }

    public void displayMenu() {
        System.out.println("Menu:");
        inventory.forEach((item, qty) -> System.out.println(item + " - Qty: " + qty));
    }

    public Order placeOrder(Customer customer, Map<String, Integer> requestedItems) throws InvalidOrderException {
        return new Order(customer, this, requestedItems);
    }

    public void showAllDeliveryPersons() {
        deliveryPersons.forEach(DeliveryPerson::showProfile);
    }
}

