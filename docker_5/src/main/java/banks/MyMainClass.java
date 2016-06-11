package banks;

import banks.service.BankService;
import banks.util.yellowpages.IYellowPages;
import banks.util.yellowpages.YellowPagesService;


public class MyMainClass {
    public static void main(String[] args) {
        System.out.println("hallo");
        new BankService();
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/banks", "bank", "bank", "A Fancy Bank Service");
    }
}