package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.Opcodes;

import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.CLASS_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.FIELD_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.METHOD_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.MODULE_EXPORTS_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.MODULE_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.MODULE_OPENS_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.MODULE_REQUIRES_INSTANCE;
import static dev.turingcomplete.asmtestkit.assertion.representation.AccessRepresentation.PARAMETER_INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("createTestToStringOfArguments")
  void testToStringOf(AccessRepresentation accessRepresentation, int access, String expectedToReadableRepresentation) {
    assertThat(accessRepresentation.toStringOf(access))
            .isEqualTo("(" + access + ")" + " " + expectedToReadableRepresentation);

    assertThat(accessRepresentation.toJavaSourceCodeRepresentation(access))
            .isEqualTo(expectedToReadableRepresentation);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> createTestToStringOfArguments() {
    return Stream.of(Arguments.of(CLASS_INSTANCE, Opcodes.ACC_PUBLIC + Opcodes.ACC_INTERFACE, "public interface"),
                     Arguments.of(CLASS_INSTANCE, Opcodes.ACC_INTERFACE + Opcodes.ACC_PUBLIC, "public interface"), // Test sort
                     Arguments.of(FIELD_INSTANCE, Opcodes.ACC_TRANSIENT + Opcodes.ACC_PUBLIC, "public transient"),
                     Arguments.of(METHOD_INSTANCE, Opcodes.ACC_NATIVE + Opcodes.ACC_PUBLIC, "public native"),
                     Arguments.of(PARAMETER_INSTANCE, Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED, "synthetic mandated"),
                     Arguments.of(MODULE_INSTANCE, Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED, "open mandated"),
                     Arguments.of(MODULE_REQUIRES_INSTANCE, Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE, "transitive static_phase"),
                     Arguments.of(MODULE_EXPORTS_INSTANCE, Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED, "synthetic mandated"),
                     Arguments.of(MODULE_OPENS_INSTANCE, Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED, "synthetic mandated"));
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
