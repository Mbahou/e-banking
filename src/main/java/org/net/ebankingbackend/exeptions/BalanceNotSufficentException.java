package org.net.ebankingbackend.exeptions;

public class BalanceNotSufficentException extends Exception{

    public BalanceNotSufficentException(String message ){
        super(message);
    }

}
