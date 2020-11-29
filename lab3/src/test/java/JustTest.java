import org.junit.Test;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.junit.Assert.*;

public class JustTest {

    final private Map<Object, List<?>> testvar;

    public JustTest() {
        testvar = new HashMap<>();
        testvar.put(String.class, Arrays.asList("qbd", "qzc", "acc", "dab", "dbc"));
        testvar.put(Float.class, Arrays.asList(1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.2f, 4.5f));
        testvar.put(Integer.class, Arrays.asList(1, 2, 3, 4, 5));
    }

    @Test
    public void floatFilter() {
        Predicate<Float> bigger = f -> f > 3.2 ;
        List<Float> ext = Arrays.asList(3.5f, 4f, 4.2f, 4.5f);
        List<Float> act = (List<Float>)
                JustMain.filterList((List<Float>) testvar.get(Float.class), bigger);

        difference(ext, act);
    }

    @Test
    public void stringFilter() {
        Predicate<String> startsWithQ = str -> str.charAt(0) == 'q';
        List<String> exp = Arrays.asList("qbd", "qzc");
        List<String> act = (List<String>)
                JustMain.filterList((List<String>) testvar.get(String.class), startsWithQ);

        difference(exp, act);
    }

    @Test
    public void intFilter() {
        Predicate<Integer> Number = integer -> integer%2 == 0;
        List<Integer> exp = Arrays.asList(2, 4);
        List<Integer> act = (List<Integer>)
                JustMain.filterList((List<Integer>) testvar.get(Integer.class), Number);

        difference(exp, act);
    }


    @Test
    public void changeInt() {
        Function<Integer, Integer> multiplied = a -> a * 2;
        List<Integer> exp = Arrays.asList(2, 4, 6, 8, 10);
        List<Integer> act = (List<Integer>)
                JustMain.changeList((List<Integer>) testvar.get(Integer.class), multiplied);

        difference(exp, act);
    }

    @Test
    public void changeFloat() {
        Function<Float, Float> Floats = a -> Math.round((a - 0.5f) * 10f) / 10f;
        List<Float> exp = Arrays.asList(0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 3.7f, 4f);
        List<Float> act = (List<Float>)
                JustMain.changeList((List<Float>) testvar.get(Float.class), Floats);

        difference(exp, act);
    }

    @Test
    public void changeString() {
        Function<String, String> newStrings = s -> s + "w";
        List<String> expected = Arrays.asList("qbdw", "qzcw", "accw", "dabw", "dbcw");
        List<String> actual = (List<String>)
                JustMain.changeList((List<String>) testvar.get(String.class), newStrings);

        difference(expected, actual);
    }

    private <T> void difference(List<T> first, List<T> second) {
        assertTrue(first.size() == second.size() && first.containsAll(second) && second.containsAll(first));
    }

}
