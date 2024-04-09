public class EmailMessage {
    private double price;
    private int hour;

    public EmailMessage(double price, int hour) {
        this.price = price;
        this.hour = hour;
    }

    public String getTitle() {
        if (price < -5) {
            return "Pörssisähkö on pian todella halpaa!";
        }

        return "Pörssisähkö on pian halpaa!";
    }

    public String getMessage() {
        String htmlMessage = "<html><body style=\"background-color: #FFD1DC; text-align: center;\">";
        htmlMessage += "<br/>";
        htmlMessage += "<h1 style=\"color: #FF1493;\">Pörssisähkö on halpaa seuraavan tunnin!</h1>";
        htmlMessage += "<p>Spot-hinta klo " + hour + "-" + (hour + 1) + " " + price + " snt/kWh.</p>";
        htmlMessage += "<p>On oikea aika käynnistää koneet.</p>";
        htmlMessage += "<br/>";
        htmlMessage += "</body></html>";

        return htmlMessage;
    }
}