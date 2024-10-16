package studio.xstream.keyallx.PAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.xstream.keyallx.KeyAllX;
import studio.xstream.keyallx.KeyAllXManager;

import java.util.concurrent.TimeUnit;

public class KeyAllXPlaceholder extends PlaceholderExpansion {
    /*
        %keyallx_timer%: Displays the remaining time until the next key distribution.(This is a combined placeholder for all the following)
        %keyallx_days%: Displays the remaining days until next key
        %keyallx_hours%: Displays the remaining hours until next key
        %keyallx_mins%: Displays the remaining mins until next key
        %keyallx_secs%: Displays the remaining secs until next key
     */

    private final KeyAllX plugin;
    private final KeyAllXManager manager;

    public KeyAllXPlaceholder(KeyAllX plugin, KeyAllXManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }


    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "keyallx";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        long timerIntervalInSeconds = manager.getTimerInterval();
        long days = TimeUnit.SECONDS.toDays(timerIntervalInSeconds);
        long hours = TimeUnit.SECONDS.toHours(timerIntervalInSeconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(timerIntervalInSeconds) % 60;
        long seconds = timerIntervalInSeconds % 60;

        switch (identifier.toLowerCase()) {
            case "timer":
                if (days > 0)
                    return String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
                else if (hours > 0)
                    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
                else if (minutes > 0)
                    return String.format("%02d:%02d", minutes, seconds);
                else
                    return String.format("%02d", seconds);
            case "days":
                return String.valueOf(days);
            case "hours":
                return String.valueOf(hours);
            case "mins":
                return String.valueOf(minutes);
            case "secs":
                return String.valueOf(seconds);
            default:
                return null;
        }
    }
}
