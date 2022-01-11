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
 *
 * <p>Note that {@link AccessRepresentation} is not part of this representation
 * because it uses the generic {@link Integer} and not a dedicated ASM type.
 */
public final class CombinedAsmRepresentation extends StandardRepresentation {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //

  /**
   * A reusable {@link CombinedAsmRepresentation} instance.
   */
  public static final CombinedAsmRepresentation INSTANCE = new CombinedAsmRepresentation();

  private static final List<AsmRepresentation<?>> ASM_REPRESENTATIONS = new ArrayList<>();

  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //

  static {
    ASM_REPRESENTATIONS.add(AttributeRepresentation.INSTANCE);
    ASM_REPRESENTATIONS.add(AnnotationNodeRepresentation.INSTANCE);
    ASM_REPRESENTATIONS.add(TypeAnnotationNodeRepresentation.INSTANCE);
    ASM_REPRESENTATIONS.add(TypePathRepresentation.INSTANCE);
    ASM_REPRESENTATIONS.add(TypeRepresentation.INSTANCE);
    ASM_REPRESENTATIONS.add(TypeReferenceRepresentation.INSTANCE);
    ASM_REPRESENTATIONS.add(FieldNodeRepresentation.INSTANCE);
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

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
