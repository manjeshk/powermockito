package com.manjeshk;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.manjeshk.*")
public class PowerMockTest {

    @Test
    public void testFinalMethods() throws Exception {

        String message = "test case for final method";

        //Mock an instance
        ClassWithFinalMethods mockObject = PowerMockito.mock(ClassWithFinalMethods.class);

        //whenNew() method makes sure that whenever an instance of this class is made using the new keyword
        //by invoking a no argument constructor, this mock instance is returned instead of the real object
        PowerMockito.whenNew(ClassWithFinalMethods.class).withNoArguments().thenReturn(mockObject);

        //Invoke no argument constructor to make an instance
        ClassWithFinalMethods object = new ClassWithFinalMethods();

        //Verify that the no argument constructor was actually involved during the last step
        PowerMockito.verifyNew(ClassWithFinalMethods.class).withNoArguments();

        //Set an expected String when the final method is called
        PowerMockito.when(object.printMessage(message)).thenReturn(message);

        //printMessage(…) is invoked.
        String helloPowerMockito = object.printMessage(message);
        System.out.println(helloPowerMockito);

        //Verify that the final method was actually called
        Mockito.verify(object).printMessage(message);

        Assert.assertEquals(message, helloPowerMockito);
    }

    @Test
    public void testStaticMethod() {

        String message = "test case for static method";
        String expectation = "Expectation";

        //Prepare ClassWithStaticMethod for static method test
        PowerMockito.mockStatic(ClassWithStaticMethod.class);

        //Preparing expectations when the static method will be invoked
        PowerMockito.when(ClassWithStaticMethod.printMessage(message)).thenReturn(expectation);

        String actual = ClassWithStaticMethod.printMessage(message);
        Assert.assertEquals(expectation, actual);
    }

    @Test
    public void testPrivateMethods() throws Exception {

        String message = "test case for private method";
        String expectation = "Expectation";

        //Mocking object
        ClassWithPrivateMethods mock = PowerMockito.spy(new ClassWithPrivateMethods());

        //Make use of Reflection API by providing method name as a String parameter to when(…) method
        PowerMockito.doReturn(expectation).when(mock, "printMessage", message);

        //Finally, we invoke the public method which in turn invoked the private method
        //and we verify our results using assertEquals(…) method.
        String actual = mock.privateCall(message);
        System.out.println(actual);
        Assert.assertEquals(expectation, actual);
    }

}
