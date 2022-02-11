package dev.turingcomplete.asmtestkit.comparator._internal;

import dev.turingcomplete.asmtestkit.assertion.LabelIndexLookup;
import dev.turingcomplete.asmtestkit.comparator.AbstractWithLabelIndexAsmComparator;
import dev.turingcomplete.asmtestkit.comparator.AsmComparator;
import dev.turingcomplete.asmtestkit.comparator.WithLabelIndexAsmComparator;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class WithLabelIndexAsmComparatorAdapter<T> extends AbstractWithLabelIndexAsmComparator<T> {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final Comparator<? super T> delegate;
  private final LabelIndexLookup      labelIndexLookup;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  protected WithLabelIndexAsmComparatorAdapter(Comparator<? super T> delegate, LabelIndexLookup labelIndexLookup) {
    super(delegate.getClass(), getComparatorElementClass(delegate));

    this.delegate = Objects.requireNonNull(delegate);
    this.labelIndexLookup = Objects.requireNonNull(labelIndexLookup);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  public static <U> Comparator<U> wrapIfNeeded(Comparator<U> customComparator, LabelIndexLookup labelIndexLookup) {
    if (customComparator instanceof WithLabelIndexAsmComparator) {
      return new WithLabelIndexAsmComparatorAdapter<>(customComparator, labelIndexLookup);
    }

    return customComparator;
  }

  public static <U> WithLabelIndexAsmComparator<U> wrap(Comparator<U> customComparator, LabelIndexLookup labelIndexLookup) {
    return new WithLabelIndexAsmComparatorAdapter<>(customComparator, labelIndexLookup);
  }

  @Override
  public <U> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
    Objects.requireNonNull(keyExtractor);
    Objects.requireNonNull(keyComparator);

    return thenComparing(WithLabelIndexAsmComparator.comparing(keyExtractor, keyComparator, labelIndexLookup));
  }

  @Override
  public Comparator<T> thenComparing(Comparator<? super T> other) {
    Objects.requireNonNull(other);

    return new ThenComparingWithLabelIndexAsmComparator<>(this, other, labelIndexLookup);
  }

  @Override
  protected int doCompare(T first, T second) {
    return doCompare(first, second, labelIndexLookup);
  }

  @Override
  protected int doCompare(T first, T second, LabelIndexLookup labelIndexLookup) {
    if (delegate instanceof WithLabelIndexAsmComparator) {
      //noinspection unchecked
      return ((WithLabelIndexAsmComparator<T>) delegate).compare(first, second, labelIndexLookup);
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

  private static class ThenComparingWithLabelIndexAsmComparator<T> extends WithLabelIndexAsmComparatorAdapter<T> {

    private final WithLabelIndexAsmComparator<T> parent;
    private final Comparator<? super T>          other;
    private final LabelIndexLookup               labelIndexLookup;

    ThenComparingWithLabelIndexAsmComparator(WithLabelIndexAsmComparator<T> parent,
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
      if (result != 0) {
        return result;
      }

      if (other instanceof WithLabelIndexAsmComparator) {
        //noinspection unchecked
        return ((WithLabelIndexAsmComparator<? super T>) other).compare(first, second, labelIndexLookup);
      }
      else  {
        return other.compare(first, second);
      }
    }

    @Override
    public String description() {
      return other.getClass().getSimpleName();
    }
  }
}
