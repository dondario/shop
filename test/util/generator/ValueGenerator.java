package util.generator;

import static util.iterator.IterableUtils.sample;

public class ValueGenerator<T> extends Generator<T> {

    private Iterable<T> values;

    public ValueGenerator(Iterable<T> values) {
        this.values = values;
    }

    @Override public T next() {
        return sample(values);
    }
}
