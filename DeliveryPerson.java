package Delivery;

import Users.User;

public class DeliveryPerson extends User {
    private boolean available = true;

    public DeliveryPerson(String id, String name) {
        super(id, name);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public void showProfile() {
        System.out.println("DeliveryPerson ID: " + id + ", Name: " + name + ", Available: " + available);
    }
}

