import java.util.ArrayList;

public class Main {

    private static int cycle = 1;
    public static ArrayList<Order> orders = new ArrayList<Order>();
    public static ArrayList<Shipment> shipments = new ArrayList<Shipment>();

    public static int getCycle() {
        return cycle;
    }

    public static void processOrders() {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getCycle_of_arrival() == getCycle()) {
                orders.get(i).processOrder();
                System.out.println("Order " + orders.get(i).getQuantity() + " " + orders.get(i).getReceiver() + " " + orders.get(i).getCycle_of_arrival());
                orders.remove(i);
            }
        }
    }

    public static void processShipments() {
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getCycle_of_arrival() == getCycle()) {
                shipments.get(i).processShipment();
                System.out.println("Shipment " + shipments.get(i).getQuantity() + " " + shipments.get(i).getReceiver());
                shipments.remove(i);
            }
        }
    }

    public static int getDemand(String d, int n) {
        int r = 0;
        switch (d) {
            case "random":
                r = (int) (Math.random() * 19 + 1);
            case "linear":
                r = n;
            case "curve":
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

            int demand = getDemand("random", 3);

            processShipments();
            processOrders();

            System.out.println("Retailer IL " + retailer.getInventory_level());
            System.out.println("Wholesaler IL " + wholesaler.getInventory_level());
            System.out.println("Distributor IL " + distributor.getInventory_level());
            System.out.println("Manufacturer IL " + manufacturer.getInventory_level());

            retailer.inventory_level -= demand;

            retailer.placeOrder(demand, wholesaler);
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getCycle_of_arrival() == cycle) {
                    if (orders.get(i).receiver.type.equals("wholesaler")) {
                        wholesaler.placeOrder(orders.get(i).getQuantity(), distributor);
                    } else if (orders.get(i).receiver.type.equals("distributor")) {
                        distributor.placeOrder(orders.get(i).getQuantity(), manufacturer);
                    } else if (orders.get(i).receiver.type.equals("manufacturer")) {
                        manufacturer.manufacture(orders.get(i).getQuantity());
                    }
                }
            }

            retailer.calculateCost();
            wholesaler.calculateCost();
            distributor.calculateCost();
            manufacturer.calculateCost();

            System.out.println("Retailer holding cost " + retailer.getHolding_cost());
            System.out.println("Retailer stockout cost " + retailer.getStockout_cost());

            System.out.println("Wholesaler holding cost " + wholesaler.getHolding_cost());
            System.out.println("Wholesaler stockout cost " + wholesaler.getStockout_cost());

            System.out.println("Distributor holding cost " + distributor.getHolding_cost());
            System.out.println("Distributor stockout cost " + distributor.getStockout_cost());

            System.out.println("Manufacturer holding cost " + manufacturer.getHolding_cost());
            System.out.println("Manufacturer stockout cost " + manufacturer.getStockout_cost());

            cycle++;

        }

    }

}
