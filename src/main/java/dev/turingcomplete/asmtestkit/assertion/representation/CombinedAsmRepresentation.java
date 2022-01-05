package dev.turingcomplete.asmtestkit.assertion.representation;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Combines various {@link AsmRepresentation} into one {@link Representation}.
 *
 * <p>This {@code Representation} can be set via
 * {@link AbstractAssert#setCustomRepresentation(Representation)} to all AssertJ
 * assertions in order to get a proper representation of ASM classes.
 */
public final class CombinedAsmRepresentation extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  private static final CombinedAsmRepresentation INSTANCE = new CombinedAsmRepresentation();

  private static final List<AsmRepresentation<?>> ASM_REPRESENTATIONS = new ArrayList<>();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  static {
    ASM_REPRESENTATIONS.add(AttributeRepresentation.instance());
    ASM_REPRESENTATIONS.add(AnnotationNodeRepresentation.instance());
    ASM_REPRESENTATIONS.add(TypePathRepresentation.instance());
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Gets a reusable {@link CombinedAsmRepresentation} instance.
   *
   * @return a {@link CombinedAsmRepresentation} instance; never null.
   */
  public static CombinedAsmRepresentation instance() {
    return INSTANCE;
  }

  /**
   * Adds a custom {@link AsmRepresentation} which will be considered in
   * {@link StandardRepresentation#fallbackToStringOf(Object)}.
   *
   * @param asmRepresentation a custom {@link AsmRepresentation}; never null.
   */
  public static void addAsmRepresentation(AsmRepresentation<?> asmRepresentation) {
    ASM_REPRESENTATIONS.add(Objects.requireNonNull(asmRepresentation));
  }

  @Override
  protected String fallbackToStringOf(Object object) {
    for (AsmRepresentation<?> asmRepresentation : ASM_REPRESENTATIONS) {
      if (asmRepresentation.isApplicable(object.getClass())) {
        return asmRepresentation.fallbackToStringOf(object);
      }
    }

    return super.fallbackToStringOf(object);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
