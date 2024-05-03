package vehiclepartspro.support.exception;

import vehiclepartspro.entities.Product;

public class ProductNotAvaiableException extends Exception
{
    public ProductNotAvaiableException()
    {}
    public ProductNotAvaiableException(Product product)
    {
        super(messageCostruction(product));

    }

    private static String messageCostruction(Product p)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("ProductNotAvaiableException \n");
        sb.append("Product name: ").append(p.getName()).append("\n");
        sb.append("Product barCode: ").append(p.getBarCode()).append("\n");
        sb.append("is not avaiable");
        return sb.toString();

    }
}
