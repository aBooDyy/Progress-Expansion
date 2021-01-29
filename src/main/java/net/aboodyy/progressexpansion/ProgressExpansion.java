/*

    Progress Expansion - A PlaceholderAPI expansion that allows you to create a progress bar placeholder based on your inputs
    Copyright (C) 2019-2020 aBooDyy

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/

package net.aboodyy.progressexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ProgressExpansion extends PlaceholderExpansion implements Configurable {

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
        return "2.0";
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
        defaults.put("decimal", 2);
        defaults.put("replace_placeholder", new String[]{",;."});
        return defaults;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (player == null) return null;

        String completed;
        String inProgress;
        String remaining;
        String full;

        int progress;
        int length;
        int barLength;
        int decimal;

        double placeholder;
        double max;
        double amtPerSymbol;

        identifier = PlaceholderAPI.setPlaceholders(player, identifier.replaceAll("\\$\\((.*?)\\)\\$", "%$1%"));
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);

        if (identifier.startsWith("percentage_")) {
            max = this.getDouble("maximum_value", 100);
            decimal = this.getInt("decimal", 2);

            String[] args = identifier.replace("percentage_", "").split("_");
            placeholder = getNumber(args[0]);

            for (String argument : args) {
                if (argument.equals(args[0])) continue;
                String[] arg = argument.split(":", 2);
                switch (arg[0]) {
                    case "m":
                        max = getNumber(arg[1]);
                        break;
                    case "d":
                        if (NumberUtils.isNumber(arg[1]))
                            decimal = Integer.parseInt(arg[1]);
                }
            }
            if (placeholder >= max) return "100";
            if (max == 0) return "0";

            StringBuilder f = new StringBuilder("#");
            if (decimal > 0) {
                f.append(".");
                for (int i = 0; i < decimal; i++) {
                    f.append("0");
                }
            }
            double percentage = (placeholder / max) * 100;
            DecimalFormat format = new DecimalFormat(f.toString());

            return format.format(percentage);
        }

        if (identifier.startsWith("bar_")) {
            barLength = 0;
            full = this.getString("full", "&aCompleted");
            completed = this.getString("completed", "&a\u25A0");
            inProgress = this.getString("in_progress", "&e\u25A0");
            remaining = this.getString("remaining", "&7\u25A0");
            length = this.getInt("length", 10);
            max = this.getDouble("maximum_value", 100);

            String[] args = identifier.replace("bar_", "").split("_");
            placeholder = getNumber(args[0]);

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
                        if (NumberUtils.isNumber(arg[1]))
                            length = Integer.parseInt(arg[1]);
                        break;
                    case "m":
                        max = getNumber(arg[1]);
                }
            }
            StringBuilder bar = new StringBuilder();
            amtPerSymbol = max / length;
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

    private double getNumber(String text) {
        if (text.toLowerCase().matches("[a-z]"))
            return Character.getNumericValue(text.charAt(0)) - 9;

        text = replaceNumbers(text);

        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            Bukkit.getLogger().info("[Progress] Couldn't get the number from " + text + ". The progress will be 0%.");
            return 0;
        }
    }

    private String replaceNumbers(String number) {
        String[] replace;

        for (String element : getStringList("replace_placeholder")) {
            replace = element.split(";", 2);
            if (replace.length != 2) continue;

            number = number.replace(replace[0], replace[1]);
        }

        return number;
    }
}