package net.bancey.intents;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import net.bancey.AlexaToDiscord;
import net.bancey.services.DiscordApp;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;

/**
 *
 * Created by Bancey on 13/12/2016.
 */
public class SelectGuildIntent extends AlexaDiscordIntent {

    private String selectedGuild;

    public SelectGuildIntent(String name) {
        super(name);
    }

    @Override
    public SpeechletResponse handle(String guild) {
        DiscordApp discordApp = AlexaToDiscord.getDiscordInstance();
        ArrayList<Guild> guilds = discordApp.getGuilds();

        String speechText;
        String repromptText;
        if (guild != null) {
            try {
                int guildNumber = Integer.parseInt(guild);
                int index = guildNumber - 1;
                if (index >= 0 && index < guilds.size()) {
                    speechText = "The guild you have selected is " + guilds.get(index).getName() + ".";
                    repromptText = "What would you like to do now?";
                    selectedGuild = guilds.get(index).getId();
                } else {
                    speechText = "Sorry, that guild number is not valid. Please say get guilds to hear the list again.";
                    repromptText = "Please try again.";
                }
            } catch (NumberFormatException e) {
                speechText = "Sorry, I couldn't understand that guild number. Please try again.";
                repromptText = "Please try again.";
            }
        } else {
            speechText = "Sorry I didn't pick up a guild number. If you are unsure of the number please say get guilds first.";
            repromptText = "Please try again.";
        }

        SimpleCard card = new SimpleCard();
        card.setTitle("Guild selection");
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    public String getSelectedGuild() {
        return selectedGuild;
    }
}
