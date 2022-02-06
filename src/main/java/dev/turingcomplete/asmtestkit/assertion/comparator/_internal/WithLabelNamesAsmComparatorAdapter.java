package dev.turingcomplete.asmtestkit.assertion.comparator._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.assertion.comparator.AbstractWithLabelNamesAsmComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.AsmComparator;
import dev.turingcomplete.asmtestkit.assertion.comparator.WithLabelNamesAsmComparator;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class WithLabelNamesAsmComparatorAdapter<T> extends AbstractWithLabelNamesAsmComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Comparator<? super T> delegate;
  private final LabelIndexLookup      labelIndexLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected WithLabelNamesAsmComparatorAdapter(Comparator<? super T> delegate, LabelIndexLookup labelIndexLookup) {
    super(delegate.getClass(), getComparatorElementClass(delegate));

    this.delegate = Objects.requireNonNull(delegate);
    this.labelIndexLookup = Objects.requireNonNull(labelIndexLookup);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static <U> Comparator<U> wrapIfNeeded(Comparator<U> customComparator, LabelIndexLookup labelIndexLookup) {
    if (customComparator instanceof WithLabelNamesAsmComparator) {
      return new WithLabelNamesAsmComparatorAdapter<>(customComparator, labelIndexLookup);
    }

    return customComparator;
  }

  public static <U> WithLabelNamesAsmComparator<U> wrap(Comparator<U> customComparator, LabelIndexLookup labelIndexLookup) {
    return new WithLabelNamesAsmComparatorAdapter<>(customComparator, labelIndexLookup);
  }

  @Override
  public <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
    Objects.requireNonNull(keyExtractor);
    Objects.requireNonNull(keyComparator);

    return thenComparing(WithLabelNamesAsmComparator.comparing(keyExtractor, keyComparator, labelIndexLookup));
  }

  @Override
  public Comparator<T> thenComparing(Comparator<? super T> other) {
    Objects.requireNonNull(other);

    return new ThenComparingWithLabelNamesAsmComparator<>(this, other, labelIndexLookup);
  }

  @Override
  protected int doCompare(T first, T second) {
    return doCompare(first, second, labelIndexLookup);
  }

  @Override
  protected int doCompare(T first, T second, LabelIndexLookup labelIndexLookup) {
    if (delegate instanceof WithLabelNamesAsmComparator) {
      //noinspection unchecked
      return ((WithLabelNamesAsmComparator<T>) delegate).compare(first, second, labelIndexLookup);
    }

    return delegate.compare(first, second);
  }

  @Override
  public String description() {
    return delegate.getClass().getSimpleName();
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static <T> Class<?> getComparatorElementClass(Comparator<? super T> delegate) {
    if (delegate instanceof AsmComparator) {
      return ((AsmComparator<?>) delegate).elementType();
    }

    return Object.class;
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //

  private static class ThenComparingWithLabelNamesAsmComparator<T> extends WithLabelNamesAsmComparatorAdapter<T> {

    private final WithLabelNamesAsmComparator<T> parent;
    private final Comparator<? super T> other;
    private final LabelIndexLookup      labelIndexLookup;

    ThenComparingWithLabelNamesAsmComparator(WithLabelNamesAsmComparator<T> parent,
                                             Comparator<? super T> other,
                                             LabelIndexLookup labelIndexLookup) {
      super(other, labelIndexLookup);

      this.parent = Objects.requireNonNull(parent);
      this.other = Objects.requireNonNull(other);
      this.labelIndexLookup = Objects.requireNonNull(labelIndexLookup);
    }

    @Override
    public int doCompare(T first, T second) {
      return compare(first, second, labelIndexLookup);
    }

    @Override
    public int doCompare(T first, T second, LabelIndexLookup labelIndexLookup) {
      int result = parent.compare(first, second, labelIndexLookup);
      return result != 0 ? result : other.compare(first, second);
    }

    @Override
    public String description() {
      return other.getClass().getSimpleName();
    }
  }
}
