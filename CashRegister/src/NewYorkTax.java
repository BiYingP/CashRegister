public class NewYorkTax implements ITax {
    double taxValue = 8.875;
    long l = (long)taxValue;

    @Override
    public long calculateTax(long purchasePrice) {return purchasePrice * this.l /100;}

    @Override
    public long applyTaxToPurchase(long purchasePrice) {return purchasePrice + calculateTax(purchasePrice);}
}
