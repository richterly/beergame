import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int cycle = 1;
    public static ArrayList<Order> orders = new ArrayList<Order>();
    public static ArrayList<Shipment> shipments = new ArrayList<Shipment>();

    private static Scanner in = new Scanner(System.in);

    public static int getCycle() {
        return cycle;
    }

    public static void processOrders() {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getCycle_of_arrival() == getCycle()) {
                orders.get(i).processOrder();
                orders.get(i).print();
                if (orders.get(i).receiver.type.equals("wholesaler")) {
                    wholesaler.placeOrder(orders.get(i).getQuantity(), distributor);
                } else if (orders.get(i).receiver.type.equals("distributor")) {
                    distributor.placeOrder(orders.get(i).getQuantity(), manufacturer);
                } else if (orders.get(i).receiver.type.equals("manufacturer")) {
                    manufacturer.manufacture(orders.get(i).getQuantity());
                }
                orders.remove(i);
            }
        }
    }

    public static void processShipments() {
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getCycle_of_arrival() == getCycle()) {
                shipments.get(i).processShipment();
                shipments.get(i).print();
                shipments.remove(i);
            }
        }
    }

    public static int getDemand(String d, int n) {
        int r = 0;
        if (d.equals("random")) {
            r = (int) (Math.random() * 20);
        } else if (d.equals("linear")) {
            r = n;
        } else if (d.equals("curve")) {
            r = (int) (2.5 * Math.cos(2.0 * Math.PI / 20.0 * (cycle)) + 7.5);
        }
        return r;
    }

    private static Station retailer = new Station("retailer");
    private static Station wholesaler = new Station("wholesaler");
    private static Station distributor = new Station("distributor");
    private static Station manufacturer = new Station("manufacturer");

    public static void main(String[] args) {

        retailer.setInventory_level(12);
        wholesaler.setInventory_level(12);
        distributor.setInventory_level(12);
        manufacturer.setInventory_level(12);

        while (cycle <= 20) {

            System.out.println("\n    Cycle " + cycle);

            int demand = getDemand("linear", 3);

            processOrders();
            processShipments();

            retailer.inventory_level -= demand;

            System.out.println("Demand: " + demand + "\nWhat amount do you want to order?");
            retailer.placeOrder(in.nextInt(), wholesaler);

            retailer.calculateCost();
            wholesaler.calculateCost();
            distributor.calculateCost();
            manufacturer.calculateCost();

            cycle++;

        }

        System.out.println("Retailer IL " + retailer.getInventory_level());
        System.out.println("Wholesaler IL " + wholesaler.getInventory_level());
        System.out.println("Distributor IL " + distributor.getInventory_level());
        System.out.println("Manufacturer IL " + manufacturer.getInventory_level());

        System.out.println("Retailer holding cost " + retailer.getHolding_cost());
        System.out.println("Retailer stockout cost " + retailer.getStockout_cost());

        System.out.println("Wholesaler holding cost " + wholesaler.getHolding_cost());
        System.out.println("Wholesaler stockout cost " + wholesaler.getStockout_cost());

        System.out.println("Distributor holding cost " + distributor.getHolding_cost());
        System.out.println("Distributor stockout cost " + distributor.getStockout_cost());

        System.out.println("Manufacturer holding cost " + manufacturer.getHolding_cost());
        System.out.println("Manufacturer stockout cost " + manufacturer.getStockout_cost());

    }

}
