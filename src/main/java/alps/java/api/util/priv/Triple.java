package alps.java.api.util.priv;

public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;
    public Object Predicate;
    public Object OObject;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }
}

