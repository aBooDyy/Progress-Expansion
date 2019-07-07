package me.aBooDyy.progressexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProgressExpansion extends PlaceholderExpansion implements Configurable {

    private String completed, inProgress, remaining, full;
    private int progress, length, max, barLength;
    private double placeholder, amtPerSymbol;

    @Override
    public String getIdentifier() {
        return "progress";
    }

    @Override
    public String getAuthor() {
        return "aBooDyy";
    }

    @Override
    public String getVersion() {
        return "1.4";
    }

    @Override
    public Map<String, Object> getDefaults() {
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("full", "&aCompleted");
        defaults.put("completed", "&a\u25A0");
        defaults.put("in_progress", "&e\u25A0");
        defaults.put("remaining", "&7\u25A0");
        defaults.put("length", 10);
        defaults.put("maximum_value", 100);
        return defaults;
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) return null;
        if (identifier.startsWith("bar_")) {
            barLength = 0;
            full = this.getString("full", "&aCompleted");
            completed = this.getString("completed", "&a\u25A0");
            inProgress = this.getString("in_progress", "&e\u25A0");
            remaining = this.getString("remaining", "&7\u25A0");
            length = this.getInt("length", 10);
            max = this.getInt("maximum_value", 100);

            identifier = PlaceholderAPI.setBracketPlaceholders(p, identifier);
            String[] args = identifier.replace("bar_", "").split("_");
            if (!NumberUtils.isNumber(args[0]) && !args[0].toLowerCase().matches("[a-z]")) {
                return "";
            }
            if (NumberUtils.isNumber(args[0])) placeholder = Double.valueOf(args[0]);
            else placeholder = Character.getNumericValue(args[0].charAt(0)) - 9;
            for (String argument : args) {
                if (argument.equals(args[0])) continue;
                String[] arg = argument.split(":", 2);
                switch (arg[0]) {
                    case "c":
                        completed = arg[1];
                        break;
                    case "i":
                    case "p":
                        inProgress = arg[1];
                        break;
                    case "r":
                        remaining = arg[1];
                        break;
                    case "fb":
                    case "fullbar":
                        full = arg[1];
                        break;
                    case "l":
                        if (NumberUtils.isNumber(arg[1])) {
                            length = Integer.parseInt(arg[1]);
                        }
                        break;
                    case "m":
                        if (NumberUtils.isNumber(arg[1])) {
                            max = Integer.parseInt(arg[1]);
                        }
                        break;
                }
            }
            StringBuilder bar = new StringBuilder();
            amtPerSymbol = (double) max / length;
            progress = (int) Math.floor(placeholder / amtPerSymbol);
            if (placeholder >= max)
                return full;
            while (barLength < progress) {
                bar.append(completed);
                barLength++;
            }
            if (barLength != length) {
                bar.append(inProgress);
                barLength++;
            }
            while (barLength < length) {
                bar.append(remaining);
                barLength++;
            }
            return bar.toString();
        }
        return null;
    }
}