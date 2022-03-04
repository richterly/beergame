public class Order {

    private static int quantity;
    public static int cycle_of_arrival;
    public static Station receiver;
    public static Station sender;

    public Order(int quantity, Station receiver, Station sender, int cycle_of_arrival) {
        this.quantity = quantity;
        this.receiver = receiver;
        this.sender = sender;
        this.cycle_of_arrival = cycle_of_arrival + 2;
        new Main().orders.add(this);
    }

    public static void print() {
        System.out.println("-----Order-----");
        System.out.println(sender.type + " -> " + receiver.type);
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

    public void processOrder() {
        int q;
        if (this.quantity > this.receiver.getInventory_level() && this.receiver.getInventory_level() > 0) {
            q = this.receiver.getInventory_level();
            this.receiver.setInventory_level(q - this.quantity);
        } else if (this.receiver.getInventory_level() <= 0) {
            q = 0;
            this.receiver.setInventory_level(this.receiver.getInventory_level() - this.quantity);
        } else {
            q = this.quantity;
        }
        Shipment shipment =  new Shipment(q, this.sender, cycle_of_arrival);
        new Main().shipments.add(shipment);
        System.out.println("Shipment created:");
        shipment.print();
    }

}
