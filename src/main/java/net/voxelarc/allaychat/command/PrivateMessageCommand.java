package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Join;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(value = "msg", alias = {"pm", "tell", "whisper", "message"})
public class PrivateMessageCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.msg")
    public void onMessage(Player player, @Suggestion("online-players") String target, @Join String message) {
        if (message == null || message.isBlank()) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.empty-message",
                            "Could not find key in msg.yml: messages.empty-message")
            ));
            return;
        }

        plugin.getChatManager().handlePrivateMessage(player, target, message);
    }

}
