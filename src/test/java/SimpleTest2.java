

import org.testng.annotations.Test;

/**
 * Created by Arka on 5/14/18.
 */
public class SimpleTest2 extends TestInstrument {

    @Test()
    public void simpleTest() {
        bukalapak.homePage().tapOnOkeButtonOnboarding();
    }
}
