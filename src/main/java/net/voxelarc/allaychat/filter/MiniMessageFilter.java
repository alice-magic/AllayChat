package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class MiniMessageFilter implements ChatFilter {

    private final Pattern allPattern = Pattern.compile("(?i).*(<|&[0-9a-fk-or])(click|hover|insert|gradient|rainbow|transition|score|selector|key|lang|[#x][0-9a-f]{6}|red|blue|green|yellow|white|black|gold|gray|aqua|dark_|light_|bold|italic|underlined|strikethrough|obfuscated|reset).*");
    private final Pattern functionalPattern = Pattern.compile("(?i).*(<|&[0-9a-fk-or])(click|hover|insert|score|selector|key|lang|bold|italic|underlined|strikethrough|obfuscated|reset).*");

    private final AllayChatPlugin plugin;

    private boolean enabled = true;
    private boolean blockAll = true;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("minimessage.enabled", true);
        blockAll = plugin.getFilterConfig().getBoolean("minimessage.block-all", true);
        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("minimessage.message", "<prefix> <red>You can not send MiniMessage tags."));
    }

    @Override
    public boolean checkMessage(Player player, String message) {
        if (!enabled) return false;
        if (player.hasPermission("allaychat.bypass.minimessage")) return false;

        if (blockAll && allPattern.matcher(message).find()) {
            ChatUtils.sendMessage(player, blockedMessage);
            return true;
        } else if (functionalPattern.matcher(message).find()) {
            ChatUtils.sendMessage(player, blockedMessage);
            return true;
        }

        return false;
    }

}
