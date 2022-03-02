public class Shipment {

    private static int quantity;
    private static int cycle_of_arrival = new Main().getCycle() + 2;
    private static Station receiver;

    public Shipment(int quantity, Station receiver) {
        this.quantity = quantity;
        this.receiver = receiver;
        new Main().shipments.add(this);
    }

    public static void print() {
        System.out.println("-----Shipment-----");
        System.out.println("-> " + receiver.type);
        System.out.println("Quantity: " + quantity);
        System.out.println("ETA: " + cycle_of_arrival);
        System.out.println("---------------");
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCycle_of_arrival() {
        return cycle_of_arrival;
    }

    public Station getReceiver() {
        return receiver;
    }

    public void processShipment() {
        this.receiver.setInventory_level(this.receiver.getInventory_level() + this.getQuantity());
    }

}
