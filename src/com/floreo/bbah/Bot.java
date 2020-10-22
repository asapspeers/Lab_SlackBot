package com.floreo.bbah;

import com.floreo.bbah.model.Channel;
import com.floreo.bbah.model.Message;
import com.floreo.bbah.network.Slack;
import com.floreo.bbah.network.responses.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Bot {




    // TODO: implement your bot logic!

    public Bot() {

    }

    /**
     * Sample method: tests the Slack API. Prints a message indicating success or failure.
     */
    public void testApi() {
        Response apiTest = Slack.testApi();
        System.out.println("API OK: " +apiTest.isOk() + "\n");
    }

    /**
     * Sample method: prints all public AccessCode3-3 channel names and ids. Prints an error message on failure.
     */
    public void listChannels() {
        ListChannelsResponse listChannelsResponse = Slack.listChannels();

        if (listChannelsResponse.isOk()) {
            List<Channel> channels = listChannelsResponse.getChannels();

            System.out.println("\nChannels: ");
            for (Channel channel : channels) {
                System.out.println("name: " + channel.getName() + ", id:" + channel.getId());
            }
        } else {
            System.err.print("Error listing channels: " + listChannelsResponse.getError());
        }
    }

    /**
     * Sample method: prints up to the last 100 messages from a given channel. Prints an error message on failure.
     * or failure.
     *
     * @param channelId id of the given channel.
     */
    public void listMessages(String channelId) {
        ListMessagesResponse listMessagesResponse = Slack.listMessages(channelId);

        if (listMessagesResponse.isOk()) {
            List<Message> messages = listMessagesResponse.getMessages();

            System.out.println("\nMessages: ");
            for (Message message : messages) {
                System.out.println();
                System.out.println("Timestamp: " + message.getTs());
                System.out.println("Message: " + message.getText());
            }
        } else {
            System.err.print("Error listing messages: " + listMessagesResponse.getError());
        }
    }

    /**
     * Sample method: sends a plain text message to the #bots channel. Prints a message indicating success or failure.
     *
     * @param text message text.
     */
    public void sendMessageToBotsChannel(String text) {
        SendMessageResponse sendMessageResponse = Slack.sendMessage(text);

        if (sendMessageResponse.isOk()) {
        //    System.out.println("Message sent successfully!");
        } else {
            System.err.print("Error sending message: " + sendMessageResponse.getError());
        }
    }

    /**
     * Sample method: deletes a message from the #bots channel. Prints a message indicating success or failure.
     *
     * @param messageTs unique timestamp of the message to be deleted.
     */
    public void deleteMessageInBotsChannel(String messageTs) {
        DeleteMessageResponse deleteMessageResponse = Slack.deleteMessage(messageTs);

        if (deleteMessageResponse.isOk()) {
            System.out.println("Message deleted successfully!");
        } else {
            System.err.print("Error sending message: " + deleteMessageResponse.getError());
        }
    }

    // Reads URL file from webpage and returns a string of raw JSON
    public String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        StringBuffer buffer;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

        } finally {
            if (reader != null)
                reader.close();
        }
        String returned = buffer.toString();
        return returned;

    }

    //Takes Pokemon number and returns the content of the corresponding pokemon number as a String
    public String findPokemonByNumber (int number) {

        String json = "";
        try {
            json = readUrl("https://pokeapi.co/api/v2/pokemon/" + number);
            } catch (Exception e) {
            System.out.println("Couldn't read the URL");
            }




        //setting raw Json to defined JSON object
        JsonElement element = new JsonParser().parse(json);
        JsonObject object = element.getAsJsonObject();


        //Setting our strings from JSON object
        String id = object.get("id").getAsString();
        String name = object.get("name").getAsString();
        String sprite = object.get("sprites").getAsJsonObject().get("front_default").getAsString();


        String profile = sprite +"\n" +
                "Pokemon Number: " + id + "\n" +
                "Name: " + name +"\n" +
                "More info: " +  "https://pokemon.fandom.com/wiki/" + name;

        return profile;
    }



}
