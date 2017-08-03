import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.stickers.Sticker;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class SimpleBot extends TelegramLongPollingBot {

    private User currentUser;
    private String currentChatId;
    private boolean autoPilot = true;
    private String SergeyChatId = "273255483";
    private String LiyaChatId = "245460656";
    private String LeysanChatId = "105809881";
    private String MaksimChatId = "109658541";


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new SimpleBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "C3PO_robot";
    }

    @Override
    public String getBotToken() {
        return "437059944:AAE5nWNglEX92Q9B5My9IOOdH62GhLBcaXE";
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        if (message != null && message.hasText()) {
            currentUser = message.getFrom();
            System.out.println(message.toString());
            System.out.println(message.getFrom().getFirstName()+" "+message.getText());
            if (autoPilot) {
                if (message.getText().equals("/help")) {
                    sendMsg(message, "Привет "+ currentUser.getFirstName() +", я робот C3PO");
                    sendMsgToMe(SergeyChatId,message);
                }
                else if (message.getText().equals("привет")) {
                    sendMsg(message, "Привет "+ currentUser.getFirstName() +", я робот C3PO");
                    sendMsgToMe(SergeyChatId,message);
                }
                else if (message.getText().equals("автопилот")) {
                    if (autoPilot) {
                        autoPilot = false;
                    }
                    else autoPilot = true;
                }
                else {
                    sendMsg(message, "Я не знаю что это значит, обратитесь к Сергею");
                    sendMsgToMe(SergeyChatId,message);
                }
            }
            else {
                if (!(currentUser.getFirstName().equals("Serega"))) {
                    sendMsgToMe(SergeyChatId,message);
                    currentChatId = String.valueOf(message.getChatId());
                }
                if (currentUser.getFirstName().equals("Serega")) {
                    sendMsgToUser(currentChatId,message);
                }
            }

        }
    }

    private void sendMsgToUser(String currentChatId, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(currentChatId);

        sendMessage.setText(message.getText());
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgToMe(String chatId, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);

        sendMessage.setText(currentChatId +" "+ message.getFrom().getFirstName() +": " + message.getText());
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPrivateMsg(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        sendMessage.setText(answer);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMyMsg(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        sendMessage.setText(answer);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        //sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}