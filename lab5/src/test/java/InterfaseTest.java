import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;


class InterfaceTest {

    interface A {
    }

    interface B {
    }

    public static class FirstExample implements A {
    }

    public static class SecondExample implements A, B {
    }

    @Singleton
    interface CheckIfSingleton {
    }

    public static class NonSingleton implements CheckIfSingleton {
    }

    private DI DI;

    @BeforeEach
    void setUp() {
        DI = new DI();
    }

    @Test
    void shouldBind() {
        DI.bindInterface(A.class, FirstExample.class);

        final A instance = DI.getInstance(A.class);
        assertThat(instance).isNotNull().isInstanceOf(FirstExample.class);
    }

    @Test
    void shouldOverrideLastBinding() {
        DI.bindInterface(A.class, FirstExample.class);
        DI.bindInterface(A.class, SecondExample.class);

        final A instance = DI.getInstance(A.class);

        assertThat(instance).isNotNull().isInstanceOf(SecondExample.class);
    }

    @Test
    void singletonInterfacesShouldNotBeSame() {
        DI.bindInterface(CheckIfSingleton.class, NonSingleton.class);

        final CheckIfSingleton firstInstance = DI.getInstance(CheckIfSingleton.class);
        final CheckIfSingleton secondInstance = DI.getInstance(CheckIfSingleton.class);

        assertThat(firstInstance).isNotSameAs(secondInstance);
    }
}