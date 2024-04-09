public class Day {
    private Hour[] hours;
    private boolean hasCheapHours;
    
    public Day(Hour[] hours) {
        this.hours = hours;
        this.hasCheapHours = false;
        
        for (Hour hour : hours) {
            if (hour.getIsCheap()) {
                this.hasCheapHours = true;
                break;
            }
        }
    }

    public Hour[] getHours() {
        return hours;
    }
    
    public boolean getHasCheapHours() {
        return hasCheapHours;
    }
}