import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Order> orders = new ArrayList<Order>();
    public static ArrayList<Shipment> shipments = new ArrayList<Shipment>();

    private static Scanner in = new Scanner(System.in);

    public static void processOrders(int current_cycle) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getCycle_of_arrival() == current_cycle) {
                orders.get(i).processOrder();
                orders.get(i).print();
                if (orders.get(i).receiver.type.equals("wholesaler")) {
                    wholesaler.placeOrder(orders.get(i).getQuantity(), distributor, current_cycle);
                } else if (orders.get(i).receiver.type.equals("distributor")) {
                    distributor.placeOrder(orders.get(i).getQuantity(), manufacturer, current_cycle);
                } else if (orders.get(i).receiver.type.equals("manufacturer")) {
                    manufacturer.manufacture(orders.get(i).getQuantity(), current_cycle);
                }
                orders.remove(i);
            }
        }
    }

    public static void processShipments(int current_cycle) {
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getCycle_of_arrival() == current_cycle) {
                shipments.get(i).processShipment();
                shipments.get(i).print();
                shipments.remove(i);
            }
        }
    }

    public static int getDemand(String d, int n, int i) {
        int r = 0;
        if (d.equals("random")) {
            r = (int) (Math.random() * 20);
        } else if (d.equals("linear")) {
            r = n;
        } else if (d.equals("curve")) {
            r = (int) (2.5 * Math.cos(2.0 * Math.PI / 20.0 * (i)) + 7.5);
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

        int i = 1;

        while (i <= 20) {

            System.out.println("\n    Cycle " + i);

            int demand = getDemand("random", 3, i);

            System.out.println("IL vor order/shipment: " + retailer.inventory_level);

            for (int j = 0; j < 20; j++) {
                processShipments(i);
                processOrders(i);
            }

            System.out.println("IL nach order/shipment: " + retailer.inventory_level);

            retailer.inventory_level -= demand;

            System.out.println("IL nach demand abzug: " + retailer.inventory_level);

            System.out.println("Demand: " + demand + "\nWhat amount do you want to order?");
            retailer.placeOrder(in.nextInt(), wholesaler, i);

            retailer.calculateCost();
            wholesaler.calculateCost();
            distributor.calculateCost();
            manufacturer.calculateCost();

            i++;

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
