package vehiclepartspro.support.exception.purchaseException;

import vehiclepartspro.entities.Product;

public class QuantityProductNotAvaiableException extends Exception
{
    public QuantityProductNotAvaiableException(Product p)
    {
        super("QUANTITY_PRODUCT_NOT_AVAIABLE"+"-"+p.getId());
    }

    public QuantityProductNotAvaiableException()
    {}
}
