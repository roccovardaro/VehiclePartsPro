package vehiclepartspro.support.exception.purchaseException;

import vehiclepartspro.entities.Product;

public class NotNegativeQuantityException extends Exception
{
    public NotNegativeQuantityException(){}
    public NotNegativeQuantityException(String message){
        super(message);
    }



}
