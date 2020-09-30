import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by TheTHINGYEEEE on 9/30/20.
 */
public class Cooldown {

    private static int minutes;
    private static int seconds;
    private static HashMap<User, Integer> cooldown = new HashMap<>();

    public Cooldown(int minutes, int seconds) {
        Cooldown.minutes = minutes;
        Cooldown.seconds = seconds;
    }
    public static int getMinutes() {
        return minutes;
    }
    public static int getSeconds() {
        return seconds;
    }
    public static int getTotalOnSeconds() {
        return minutes * 60 + seconds;
    }
    public void startCooldown() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                if(cooldown.isEmpty()) return;


                for(User user : cooldown.keySet()) {
                    int timeleft = cooldown.get(user);
                    System.out.println(user.getName() + ": " + Cooldown.getCooldown().get(user));
                    if(timeleft == 0) {
                        cooldown.remove(user);
                    } else {
                        cooldown.put(user, timeleft - 1);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    public static HashMap<User, Integer> getCooldown() {
        return cooldown;
    }
}
// To-do for tomorrow.
// Add more features
// Add useful stuff

