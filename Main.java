package FoodDelivery;

import Admin.Manager;
import Admin.Order;
import Users.Customer;
import Delivery.DeliveryPerson;
import Delivery.DeliverySystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DeliverySystem system = new DeliverySystem();
        Manager manager = new Manager("M1", "Admin");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.Register Customer\n2.Register Delivery Person\n3.Show Menu & Place Order\n4.Add Food Item\n5.Restock Food Item\n6.Show All Delivery Personnel\n7.Exit");
            int choice = Integer.parseInt(sc.nextLine());
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter ID, Name: ");
                        String[] uc = sc.nextLine().split(",");
                        system.registerCustomer(new Customer(uc[0].trim(), uc[1].trim()));
                        break;
                    case 2:
                        System.out.print("Enter ID, Name: ");
                        String[] dp = sc.nextLine().split(",");
                        system.registerDeliveryPerson(new DeliveryPerson(dp[0].trim(), dp[1].trim()));
                        break;
                    case 3:
                        system.displayMenu();
                        System.out.println("Enter items as name:qty comma separated:");
                        String[] entries = sc.nextLine().split(",");
                        Map<String, Integer> req = new HashMap<>();
                        for (String e : entries) {
                            var p = e.split(":");
                            req.put(p[0].trim(), Integer.parseInt(p[1].trim()));
                        }
                        System.out.print("Enter Customer ID: ");
                        String cid = sc.nextLine().trim();
                        Customer cust = system.customers.stream()
                                .filter(c -> c.id.equals(cid)).findFirst().orElse(null);
                        if (cust == null) {
                            System.out.println("Customer not found"); break;
                        }
                        Order order = system.placeOrder(cust, req);
                        System.out.println(order.orderDetails());
                        break;
                    case 4:
                        System.out.print("Enter Name, Price, Quantity: ");
                        String[] ai = sc.nextLine().split(",");
                        manager.addNewItem(system.inventory, ai[0].trim(), Double.parseDouble(ai[1].trim()), Integer.parseInt(ai[2].trim()));
                        break;
                    case 5:
                        System.out.print("Enter Name, Quantity: ");
                        String[] ri = sc.nextLine().split(",");
                        manager.restockItem(system.inventory, ri[0].trim(), Integer.parseInt(ri[1].trim()));
                        break;
                    case 6:
                        system.showAllDeliveryPersons();
                        break;
                    case 7:
                        System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
 }
}
}
