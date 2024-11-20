package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

import DAO.AccountDAO;

public class MessageService {

    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
   
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if(message.getMessage_text().length() != 0 && message.getMessage_text().length() <= 255 && accountDAO.getAccountById(message.getPosted_by()) != null){
            return messageDAO.insertMessage(message);
        } else return null;

    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);        
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessageById(int message_id, String message_text_ToUpdate) {
        if (message_text_ToUpdate.length() != 0 && message_text_ToUpdate.length() <= 255){
            return messageDAO.updateMessageById(message_id, message_text_ToUpdate);

        }else return null;       
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
        return messageDAO.getAllMessageByAccountId(account_id);
    }
    
}
