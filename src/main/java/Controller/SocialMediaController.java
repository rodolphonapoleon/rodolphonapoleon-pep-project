package Controller;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * Controller
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);

        return app;
    }

    /* USER REGISTRATION */
    private void postRegisterAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount!=null){
            ctx.json(mapper.writeValueAsString(registeredAccount));
            ctx.status(200);
        }else {
            ctx.status(400);
        }
    }
   
    /* LOGIN */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);
        if(verifiedAccount!=null){
            ctx.json(mapper.writeValueAsString(verifiedAccount));
            ctx.status(200);
        }else {
            ctx.status(401);
        }
    }

    /* CREATE NEW MESSAGE */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage!=null){
            ctx.json(mapper.writeValueAsString(createdMessage));
            ctx.status(200);
        }else {
            ctx.status(400);
        }
    }

    /* GET ALL MESSAGES */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }

    /* GET ONE MESSAGE GIVEN MESSAGE ID */
    private void getMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message messageById = messageService.getMessageById(message_id);
        if(messageById != null){
            ctx.json(messageById);
            ctx.status(200);
        }else {
            ctx.status(200);
        }
    }

    /* DELETE A MESSAGE GIVEN MESSAGE ID */
    private void deleteMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message deletedMessage = messageService.deleteMessageById(message_id);
        if(deletedMessage != null){
            ctx.json(deletedMessage);
            ctx.status(200);
        }else {
            ctx.status(200);
        }
    }

    /* UPDATE MESSAGE GIVEN MESSAGE ID */
    private void updateMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        String message_text_ToUpdate = ctx.bodyAsClass(Message.class).getMessage_text();
        System.out.println(message_text_ToUpdate);
        Message updatedMessage = messageService.updateMessageById(message_id, message_text_ToUpdate);
        if(updatedMessage != null){
            ctx.json(updatedMessage);
            ctx.status(200);
        }else {
            ctx.status(400);
        }
    }

    /* GET ALL MESSAGES FROM USER GIVEN ACCOUNT ID */
    private void getAllMessagesByAccountIdHandler(Context ctx) {
        int account_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        ctx.json(messages);
        ctx.status(200);
        
    }

}

