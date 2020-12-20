import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BindTest {

    private static class CustomInterfaceExample implements MyCustomInterface {
    }

    interface MyCustomInterface {
    }

    static abstract class AbstractExample {
    }

    private DI DI;
    private CustomInterfaceExample interfaceExample;
    private AbstractExample abstractExample;

    @BeforeEach
    void setUp() {
        DI = new DI();
        interfaceExample = new CustomInterfaceExample();
        abstractExample = new AbstractExample() {
        };
    }

    @Test
    void instancesFromInterfaceImplementationShouldBeSame() {
        DI.bindInstance(CustomInterfaceExample.class, interfaceExample);

        final CustomInterfaceExample instance = DI.getInstance(CustomInterfaceExample.class);

        assertThat(instance).isSameAs(interfaceExample);
    }

    @Test
    void instancesInterfaceShouldBeSame() {
        DI.bindInstance(MyCustomInterface.class, interfaceExample);

        final MyCustomInterface instance = DI.getInstance(MyCustomInterface.class);

        assertThat(instance).isSameAs(interfaceExample);
    }

    @Test
    void instancesFromAbstractClassShouldBeSame() {
        DI.bindInstance(AbstractExample.class, abstractExample);

        final AbstractExample instance = DI.getInstance(AbstractExample.class);

        assertThat(instance).isSameAs(abstractExample);
    }
}