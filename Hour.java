public class Hour {
    private double price;
    private int startHour;
    private boolean isCheap;

    public Hour(double price, int startHour) {
        this.price = price;
        this.startHour = startHour;
        this.isCheap = price < 0;
    }

    public boolean getIsCheap() {
        return isCheap;
    }

    public int getStartHour() {
        return startHour;
    }

    public double getPrice() {
        return price;
    }
}