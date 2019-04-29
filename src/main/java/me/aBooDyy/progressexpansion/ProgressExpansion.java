package me.aBooDyy.progressexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProgressExpansion extends PlaceholderExpansion implements Configurable {

    private final String VERSION = getClass().getPackage().getImplementationVersion();

    private String completed, inProgress, remaining, full;
    private int progress, length, max, barLength;
    private Double placeholder, amtPerSymbol;

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
        return VERSION;
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
            full = this.getString("full", "&aCompleted");
            completed = this.getString("completed", "&a\u25A0");
            inProgress = this.getString("in_progress", "&e\u25A0");
            remaining = this.getString("remaining", "&7\u25A0");
            length = this.getInt("length", 10);
            max = this.getInt("maximum_value", 100);

            identifier = PlaceholderAPI.setBracketPlaceholders(p, identifier);
            String[] args = identifier.replace("bar_", "").split("_");
            if (!NumberUtils.isNumber(args[0])) {
                return "";
            }
            placeholder = Double.valueOf(args[0]);
            for (String arg : args) {
                if (arg.startsWith("c:")) {
                    completed = arg.replace("c:", "");
                } else if (arg.startsWith("p:")) {
                    inProgress = arg.replace("p:", "");
                } else if (arg.startsWith("r:")) {
                    remaining = arg.replace("r:", "");
                } else if (arg.startsWith("fullbar:")) {
                    full = arg.replace("fullbar:", "");
                } else if (arg.startsWith("l:")) {
                    arg = arg.replace("l:", "");
                    if (NumberUtils.isNumber(arg)) {
                        if (Integer.valueOf(arg) >= 1074) {
                            length = 1073;
                        } else {
                            length = Integer.parseInt(arg);
                        }
                    }
                } else if (arg.startsWith("m:")) {
                    arg = arg.replace("m:", "");
                    if (NumberUtils.isNumber(arg)) {
                        max = Integer.parseInt(arg);
                    }
                }
            }
            StringBuilder bar = new StringBuilder();
            amtPerSymbol = (double) max / length;
            progress = (int) Math.floor(placeholder / amtPerSymbol);
            if (placeholder >= max) {
                return full;
            }
            for (int i = 0; i < progress; i++) {
                bar.append(completed);
            }
            if (bar.length() / completed.length() != length) {
                bar.append(inProgress);
            }
            barLength = (bar.toString().contains(inProgress) ? (bar.length() - inProgress.length()) + completed.length() : bar.length()) / completed.length();
            for (int i = barLength; i < length; i++) {
                bar.append(remaining);
            }
            return bar.toString();
        }
        return null;
    }
}