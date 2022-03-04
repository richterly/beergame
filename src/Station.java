public class Station {

    public int inventory_level;
    private double holding_cost;
    private double stockout_cost;
    public String type;

    public Station(String type) {
        this.type = type;
    }

    public int getInventory_level() {
        return inventory_level;
    }

    public void setInventory_level(int inventory_level) {
        this.inventory_level = inventory_level;
    }

    public double getHolding_cost() {
        return holding_cost;
    }

    public double getStockout_cost() {
        return stockout_cost;
    }

    public void manufacture(int demand, int cycle) {
        Shipment shipment = new Shipment(demand, this, cycle);
        System.out.println("Manufacture Shipment created:");
        shipment.print();
    }

    public void placeOrder(int quantity, Station receiver, int cycle) {
        Order order = new Order(quantity, receiver, this, cycle);
        System.out.println("Order created:");
        order.print();
    }

    public void calculateCost() {
        if (this.getInventory_level() > 0) {
            this.holding_cost += this.getInventory_level() * 0.5;
        } else if (this.getInventory_level() < 0) {
            this.stockout_cost -= this.getInventory_level();
        }
    }

}
