package vehiclepartspro.support.exception.purchaseException;

import vehiclepartspro.entities.Product;

public class QuantityProductNotAvaiableException extends Exception
{
    public QuantityProductNotAvaiableException(Product p)
    {
        super(messageCostruction(p));
    }

    public QuantityProductNotAvaiableException()
    {}


    private static String messageCostruction(Product p)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("QuantityProductNotAvaiableException \n");
        sb.append("Product name: ").append(p.getName()).append("\n");
        sb.append("Product barCode: ").append(p.getBarCode()).append("\n");
        sb.append("is not avaiable");
        return sb.toString();

    }
}
