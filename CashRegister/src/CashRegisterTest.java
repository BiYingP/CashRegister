import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class CashRegisterTest  {

    CashRegister cr = new CashRegister(100,10,10,10,10,10,1000,100,100,10);
    BillPack purchaseBills = new BillPack(0, 0, 0, 0, 0, 1);
    CoinPack purchaseCoins = new CoinPack(0, 0, 0, 1);
    Drawer theDrawer = new Drawer();

    @Test
    public void testDrawerValue(){
        CashRegister c = new CashRegister(0,0,0,0,0,0,20,0,0,0);
        assertEquals(0.2, c.drawerValue());
    }
    @Test
    public void testCoinInDrawer(){
        Drawer d = new Drawer(1,1,1,1,1,1,1,1,1,1);
        assertTrue(d.penny() == 1);
    }

    @Test
    public void testCashRegister2() {
        CoinPack cp = new CoinPack(1,1,1,1);
        BillPack bp = new BillPack(0, 0, 0, 0, 1,0);
        Drawer testDrawer = new Drawer(cp, bp);
        CashRegister test3 = new CashRegister(testDrawer);
        assertEquals(test3.drawerValue(), 50.41, 4);
        assertEquals(test3.coinsInDrawer(), cp);
        assertEquals(test3.billsInDrawer(), bp);
    }

    @Test
    public void billsInDrawer(){
        theDrawer.depositBills(purchaseBills);
        assertEquals(1, theDrawer.hundred());
    }
    @Test
    public void testFinalizePurchase(){
        BillPack bp = new BillPack(1,0,0,0,0,0);
        CoinPack cp = new CoinPack(1, 0,0,0);
        assertEquals(cr.finalizePurchase(bp, cp), cr.finalizePurchase(bp, cp));
    }
    @Test(expected = IllegalStateException.class)
    public void finalizePurchase(){
        BillPack bp = new BillPack(0, 0,1, 0,0,0);
        CoinPack cp = new CoinPack(0,0,0,0);

        cr.scanItem(30, "Candy");
        double change = cr.finalizePurchase(bp, cp);
    }

    @Test
    public void testDisplaychange() {
        BillPack bp = new BillPack(0, 0, 1, 0, 0, 0);
        CoinPack cp = new CoinPack(60, 0, 0, 0);
        double change = cr.purchaseItem(10, bp, cp);
        assertEquals(change, 0.00, 4);
    }
    @Test
    public void testOverHundred(){

        BillPack bp1 = new BillPack(0,0,0,0,1,1);
        CoinPack cp1 = new CoinPack(60,0,0,0);
        double change1 = cr.purchaseItem(10, bp1, cp1);
        assertEquals(140.00, change1);
    }
    @Test (expected = IllegalStateException.class)
    public void test(){
        PennsylvaniaTax p = new PennsylvaniaTax();
        CashRegister c = new CashRegister(0,0,0,0,0,0,0,0,0,0, p);
        assertEquals(10.60, c.purchaseItem(10.6, 0,0,0,0,0,0,0,0,0,0));
    }

    @Test
    public void testScanItem(){
        CashRegister expC = new CashRegister();
        assertEquals(expC.scanItem(0, "Candy"), cr.scanItem(0, "Candy"));
        assertEquals(expC.scanItem(1, "Candy"), cr.scanItem(1, "Candy"));
        assertEquals(expC.scanItem(-1,"Candy"), cr.scanItem(-1, "Candy"));

    }
    @Test(expected = IllegalArgumentException.class)
    public void testBillPack(){
        BillPack purchaseBills = new BillPack(-1, 3, 1, 0, 6, 1);
        assertTrue(purchaseBills.hundreds() == 1);
        assertTrue(purchaseBills.fifties() == 6);
        assertTrue(purchaseBills.twenties() == 0);
        assertTrue(purchaseBills.tens()== 1);
        assertTrue(purchaseBills.fives() == 3);
        assertTrue(purchaseBills.ones() == -1);

    }
    @Test (expected = IllegalArgumentException.class)
    public void testCoinPack(){
        CoinPack purchaseCoins = new CoinPack(-1, 0, 0, 1);
        assertTrue(purchaseCoins.quarters() == 1);
        assertTrue(purchaseCoins.dimes() == 0);
        assertTrue(purchaseCoins.nickles() == 0);
        assertTrue(purchaseCoins.pennies() == -1);
    }
    @Test
    public void testCoinPackLength(){
        CoinPack purchaseCoins = new CoinPack();
        assertTrue(purchaseCoins.cents.length == 4);
    }

    @Test
    public void testItemPrices(){
        List<Double> itemPrices = new ArrayList<>();
        itemPrices.add(10.00);
        List<Double> expList = new ArrayList<>();
        expList.add(10.00);
        assertTrue(expList.equals(itemPrices));
        assertTrue(itemPrices.size() == 1);
        assertNotNull(itemPrices);
    }
    @Test
    public void testItemName(){
        List<String> itemNames = new ArrayList<>();
        itemNames.add("Item Name");
        itemNames.add("Candy");
        assertEquals("Item Name", itemNames.get(0));
        assertEquals("Candy", itemNames.get(1));
        assertTrue(itemNames.size() == 2);
        assertNotNull(itemNames);
    }
    @Test(expected = IllegalStateException.class)
    public void testPurchaseItem(){
        CashRegister newCr = new CashRegister(0,0,0,0,0,0,0,0,0,20);
        BillPack purchaseBills = new BillPack(0, 0, 0, 0, 0, 1);
        CoinPack purchaseCoins = new CoinPack(0, 0, 0, 0);
        double change = newCr.purchaseItem(20.00, purchaseBills, purchaseCoins);
    }

    @Test(expected = IllegalStateException.class)
    public void testPurchaseItem2() {
        CashRegister cr = new CashRegister(5,1,1,1,1,0,0,0,0,0);
        double change = cr.purchaseItem(2.25, 0,2, 0 , 0 , 0, 0, 0, 0, 0,1);
    }
    @Test
    public void testMultiItems(){
        BillPack crBills = new BillPack(10, 10, 10, 10, 10, 10);
        CoinPack crCoins = new CoinPack(1000, 100, 100, 100);
        CashRegister multiItems = new CashRegister(crBills, crCoins);
        List<Double> itemPrices = new ArrayList<>();
        List<String> itemNames = new ArrayList<>();

        itemPrices.add(1.00);
        itemNames.add("Expensive Soda");
        itemPrices.add(1.00);
        itemNames.add("Very Good Chocolate");
        itemPrices.add(1.00);
        itemNames.add("Very Bad Chocolate");

        for(int i = 0; i < itemPrices.size(); ++i)
            multiItems.scanItem(itemPrices.get(i), itemNames.get(i));

        BillPack bills = new BillPack(1, 1, 2, 1, 0, 0);
        CoinPack coins = new CoinPack(10, 1, 2, 5);

        double multiChange = multiItems.finalizePurchase(bills, coins);

       // double expMultiChange = 44.42;
        assertEquals(44.42, multiChange);
        assertEquals(1.0, itemPrices.get(0));
        assertEquals(1.0,itemPrices.get(1));
        assertEquals(1.0, itemPrices.get(2));
        assertEquals("Expensive Soda", itemNames.get(0));
        assertEquals("Very Good Chocolate", itemNames.get(1));
        assertEquals("Very Bad Chocolate", itemNames.get(2));
        assertTrue(itemPrices.size() == 3);
        assertTrue(itemNames.size() == 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDepositBills(){

        theDrawer.depositBills(-1,0, 1, 1,1,1);
        assertEquals(0, theDrawer.one());
        assertEquals(0, theDrawer.five());
        assertEquals(1, theDrawer.ten());
        assertEquals(1, theDrawer.twenty());
        assertEquals(1, theDrawer.fifty());
        assertEquals(1, theDrawer.hundred());
    }

    @Test
    public void testRemoveBills(){
        theDrawer.depositBills(1,2, 2, 2,1,0);
        theDrawer.removeBills(1, 1, 1, 2, 1,1);
        assertEquals(0, theDrawer.one());
        assertEquals(1, theDrawer.five());
        assertEquals(1, theDrawer.ten());
        assertEquals(0, theDrawer.twenty());
        assertEquals(0, theDrawer.fifty());
        assertEquals(0, theDrawer.hundred());
    }
    @Test
    public void testDepositChange(){
        theDrawer.depositChange(1,1,1,1);
        assertEquals(1, theDrawer.penny());
//        assertEquals(1, theDrawer.nickle());
//        assertEquals(1, theDrawer.dime());
//        assertEquals(1, theDrawer.quarter());
//        assertEquals(41,theDrawer.drawerTotalInCents());
    }
    @Test (expected = IllegalArgumentException.class)
    public void testRemoveChanges(){
        theDrawer.depositChange(-1,2,2,2);
        theDrawer.removeChange(1,1,1,1);
        assertEquals(1, theDrawer.penny());
        assertEquals(1, theDrawer.nickle());
        assertEquals(1, theDrawer.dime());
        assertEquals(1, theDrawer.quarter());
    }
    @Test
    public void testRemoveChanges2(){
        CashRegister c = new CashRegister(0,0,0,0,0,0,0,0,0,0);
        CoinPack cp = new CoinPack(0,0,0,0);
        theDrawer.removeChange(1,1,1,1);

    }
    @Test
    public void testCentValueFromCoins(){
        assertEquals(6, theDrawer.centValueFromCoins(1, 1, 0, 0));
        assertEquals(0, theDrawer.centValueFromCoins(0, 0, 0,0));
        assertEquals(-1, theDrawer.centValueFromCoins(-1, 0,0,0));
        assertEquals(25, theDrawer.centValueFromCoins(purchaseCoins));
    }
    @Test
    public void testCentValueFromBills(){
        assertEquals(100,theDrawer.centValueFromBills(1, 0, 0, 0, 0, 0));
        assertEquals(0, theDrawer.centValueFromBills(0,0,0,0,0,0));
        assertEquals(-100, theDrawer.centValueFromBills(-1, 0,0,0,0,0));
        assertEquals(10000, theDrawer.centValueFromBills(purchaseBills));
    }

}
