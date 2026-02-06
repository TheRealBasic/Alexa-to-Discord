package net.bancey.services;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * Created by Bancey on 11/12/2016.
 */
public class DiscordApp {

    private JDA jda = null;

    public DiscordApp(String token) {
        if (token == null || token.trim().isEmpty()) {
            return;
        }

        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES))
                    .setMemberCachePolicy(MemberCachePolicy.NONE)
                    .build()
                    .awaitReady();
            jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.playing("I am a non sentient being."));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            jda = null;
        } catch (LoginException e) {
            jda = null;
        }
    }

    public ArrayList<TextChannel> getTextChannelsInGuild(String guildId) {
        Guild guild = getGuildById(guildId);
        if (guild == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(guild.getTextChannels());
    }

    public ArrayList<VoiceChannel> getVoiceChannelsInGuild(String guildId) {
        Guild guild = getGuildById(guildId);
        if (guild == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(guild.getVoiceChannels());
    }

    public ArrayList<GuildChannel> getAllChannelsInGuild(String guildId) {
        Guild guild = getGuildById(guildId);
        if (guild == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(guild.getChannels());
    }

    public ArrayList<Guild> getGuilds() {
        if (jda == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(jda.getGuilds());
    }

    public Guild getGuildById(String guildId) {
        if (jda == null || guildId == null || guildId.trim().isEmpty()) {
            return null;
        }
        return jda.getGuildById(guildId);
    }

    public ArrayList<Role> getRolesFromGuild(String guildId) {
        Guild guild = getGuildById(guildId);
        if (guild == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(guild.getRoles());
    }
}
