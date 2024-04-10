import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JobScheduler {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        long initialDelay = calculateInitialDelayToOneAtNight();
        
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Scraper scraper = new Scraper();
                Day day = scraper.scrapeData();
                if (day.getHasCheapHours()) {
                    for (Hour hour : day.getHours()) {
                        if (hour.getIsCheap()) {
                            scheduleEmailSending(hour);
                        }
                    }
                }
            }
        }, initialDelay, 24 * 60 * 60, TimeUnit.SECONDS);

        Server server = new Server();
        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scheduleEmailSending(Hour hour) {
        ScheduledExecutorService emailScheduler = Executors.newScheduledThreadPool(1);

        long currentTime = System.currentTimeMillis();
        long desiredTime = hour.getStartHour() * 60 * 60 * 1000;
        long emailTime = desiredTime - TimeUnit.MINUTES.toMillis(7);
        long delay = emailTime - currentTime;

        emailScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                EmailMessage message = new EmailMessage(hour.getPrice(), hour.getStartHour());
                Mailer mailer = new Mailer(message.getTitle(), message.getMessage());
                mailer.sendEmail();
            }
        }, delay, TimeUnit.SECONDS);
    }
    
    private static long calculateInitialDelayToOneAtNight() {
        Calendar time = Calendar.getInstance();

        time.set(Calendar.HOUR_OF_DAY, 01);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        
        long currentTime = System.currentTimeMillis();
        long desiredTime = time.getTimeInMillis();
        
        long initialDelay = desiredTime - currentTime;
        return initialDelay / 1000;
    }
}
