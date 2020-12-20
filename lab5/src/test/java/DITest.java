import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Singleton;

import static org.assertj.core.api.Assertions.assertThat;


class InstanceProviderTest {

    public interface InstanceProvider {
        <T> T getInstance(Class<T> type);
    }

    public static class Example {
    }

    @Singleton
    public static class SingletonExample {
    }

    private DI DI;

    @BeforeEach
    void setUp() {
        DI = new DI();
    }

    @Test
    void instanceShouldNotBeNull() {
        DI.bindProvider(InstanceProvider.class, () -> DI::getInstance);
        final InstanceProvider provider = DI.getInstance(InstanceProvider.class);

        final Example example = provider.getInstance(Example.class);

        assertThat(example).isNotNull();
    }

    @Test
    void singletonsShouldBeSame() {
        DI.bindProvider(InstanceProvider.class, () -> DI::getInstance);
        final InstanceProvider provider = DI.getInstance(InstanceProvider.class);

        final SingletonExample singleton1 = provider.getInstance(SingletonExample.class);
        final SingletonExample singleton2 = provider.getInstance(SingletonExample.class);
        final SingletonExample singleton3 = DI.getInstance(SingletonExample.class);

        assertThat(singleton1).isSameAs(singleton2);
        assertThat(singleton2).isSameAs(singleton3);
    }
}
