public class Shipment {

    private int quantity;
    private int cycle_of_arrival = new Main().getCycle() + 2;
    private Station receiver;

    public Shipment(int quantity, Station receiver) {
        this.quantity = quantity;
        this.receiver = receiver;
        new Main().shipments.add(this);
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
