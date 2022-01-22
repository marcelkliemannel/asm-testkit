package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelNameLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator.AbstractWithLabelNamesAsmComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.WithLabelNamesAsmComparator;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class WithLabelNamesAsmComparatorAdapter<T> extends AbstractWithLabelNamesAsmComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Comparator<? super T> delegate;
  private final LabelNameLookup       labelNameLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected WithLabelNamesAsmComparatorAdapter(Comparator<? super T> delegate, LabelNameLookup labelNameLookup) {
    this.delegate = Objects.requireNonNull(delegate);
    this.labelNameLookup = Objects.requireNonNull(labelNameLookup);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static <U> Comparator<U> wrapIfNeeded(Comparator<U> customComparator, LabelNameLookup labelNameLookup) {
    if (customComparator instanceof WithLabelNamesAsmComparator) {
      return new WithLabelNamesAsmComparatorAdapter<>(customComparator, labelNameLookup);
    }

    return customComparator;
  }

  public static <U> WithLabelNamesAsmComparator<U> wrap(Comparator<U> customComparator, LabelNameLookup labelNameLookup) {
    return new WithLabelNamesAsmComparatorAdapter<>(customComparator, labelNameLookup);
  }

  @Override
  public <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
    Objects.requireNonNull(keyExtractor);
    Objects.requireNonNull(keyComparator);

    return thenComparing(WithLabelNamesAsmComparator.comparing(keyExtractor, keyComparator, labelNameLookup));
  }

  @Override
  public Comparator<T> thenComparing(Comparator<? super T> other) {
    Objects.requireNonNull(other);

    return new ThenComparingWithLabelNamesAsmComparator<>(this, other, labelNameLookup);
  }

  @Override
  protected int doCompare(T first, T second) {
    return doCompare(first, second, labelNameLookup);
  }

  @Override
  protected int doCompare(T first, T second, LabelNameLookup labelNameLookup) {
    if (delegate instanceof WithLabelNamesAsmComparator) {
      //noinspection unchecked
      return ((WithLabelNamesAsmComparator<T>) delegate).compare(first, second, labelNameLookup);
    }

    return delegate.compare(first, second);
  }

  @Override
  public String description() {
    return delegate.getClass().getSimpleName();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class ThenComparingWithLabelNamesAsmComparator<T> extends WithLabelNamesAsmComparatorAdapter<T> {

    private final WithLabelNamesAsmComparator<T> parent;
    private final Comparator<? super T>          other;
    private final LabelNameLookup                labelNameLookup;

    ThenComparingWithLabelNamesAsmComparator(WithLabelNamesAsmComparator<T> parent,
                                             Comparator<? super T> other,
                                             LabelNameLookup labelNameLookup) {
      super(other, labelNameLookup);

      this.parent = Objects.requireNonNull(parent);
      this.other = Objects.requireNonNull(other);
      this.labelNameLookup = Objects.requireNonNull(labelNameLookup);
    }

    @Override
    public int doCompare(T first, T second) {
      return compare(first, second, labelNameLookup);
    }

    @Override
    public int doCompare(T first, T second, LabelNameLookup labelNameLookup) {
      int result = parent.compare(first, second, labelNameLookup);
      return result != 0 ? result : other.compare(first, second);
    }
  }
}
