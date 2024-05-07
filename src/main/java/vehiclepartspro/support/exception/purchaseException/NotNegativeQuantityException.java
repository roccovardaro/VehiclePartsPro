package vehiclepartspro.support.exception.purchaseException;

import vehiclepartspro.entities.Product;

public class NotNegativeQuantityException extends Exception
{
    public NotNegativeQuantityException(){}
    public NotNegativeQuantityException(String message){
        super(message);
    }

    public NotNegativeQuantityException(Product p)
    {
        super(messageCostruction(p));

    }

    private static String messageCostruction(Product p)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("NotNegativeQuantityException" +"\n");
        sb.append("Product name: ").append(p.getName()).append("\n");
        sb.append("Product barCode: ").append(p.getBarCode()).append("\n");
        sb.append("Quantity must be greater than 0");
        return sb.toString();

    }


}
