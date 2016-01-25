
import test.company.com.NotNullClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestNotNullClass {

    @Test(expected=NullPointerException.class)
    public void testNullCallParameter() {
        new NotNullClass().doNothingSingleNotNull(null, 42);
    }

    @Test
    public void testNullCallParameterOK() {
        new NotNullClass().doNothingSingleNotNull("foo", 42);
    }

    @Test(expected=NullPointerException.class)
    public void testNullReturnValue() {
        new NotNullClass().getNullString(null);
    }

    @Test
    public void testNonNullReturnValue() {
        new NotNullClass().getNullString("foo");
    }

    @Test
    public void testTricky() {
        new NotNullClass().trickyMethod();
    }

    @Test(expected=NullPointerException.class)
    public void doNothingEndNotNull() {
        new NotNullClass().doNothingEndNotNull(null, null, null);
    }

    @Test
    public void doNothingEndNotNullOK() {
        new NotNullClass().doNothingEndNotNull(null, null, "foo");
    }
}
