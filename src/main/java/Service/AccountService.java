package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
   
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    public Account registerAccount(Account account) {
        if(account.getUsername().length() != 0 && account.getPassword().length() >= 4 && accountDAO.getAccountByUserName(account.getUsername()) == null){
            return accountDAO.insertAccount(account);
        } else return null;  
    }

    public Account verifyAccount(Account account) {
        if (account.getUsername().length() != 0 && account.getPassword().length() >= 4){
            Account retrievedAccount = accountDAO.getAccountByUserName(account.getUsername());
            if(retrievedAccount != null && retrievedAccount.getPassword().equals(account.getPassword())){
                return retrievedAccount;
            } else return null;
        } else return null;  
    }
    
}

