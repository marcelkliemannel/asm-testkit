package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.node.AccessFlags;
import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.Opcodes;

import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.representation.AccessFlagsRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessFlagsRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("createTestToStringOfArguments")
  void testToStringOf(AccessFlags accessFlags, String expectedToReadableRepresentation) {
    assertThat(INSTANCE.toStringOf(accessFlags))
            .isEqualTo("(" + accessFlags.access() + ")" + " " + expectedToReadableRepresentation);

    assertThat(INSTANCE.toJavaSourceCodeRepresentation(accessFlags))
            .isEqualTo(expectedToReadableRepresentation);
  }

  @Test
  void testInvalidAccessToStringOf() {
    assertThat(INSTANCE.toStringOf(AccessFlags.create(10, AccessKind.PARAMETER)))
            .isEqualTo("(10)"); // there should be no appended spaces
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> createTestToStringOfArguments() {
    return Stream.of(Arguments.of(AccessFlags.forClass(Opcodes.ACC_PUBLIC + Opcodes.ACC_INTERFACE), "public interface"),
                     Arguments.of(AccessFlags.forClass(Opcodes.ACC_INTERFACE + Opcodes.ACC_PUBLIC), "public interface"), // Test sort
                     Arguments.of(AccessFlags.forField(Opcodes.ACC_TRANSIENT + Opcodes.ACC_PUBLIC), "public transient"),
                     Arguments.of(AccessFlags.forMethod(Opcodes.ACC_NATIVE + Opcodes.ACC_PUBLIC), "public native"),
                     Arguments.of(AccessFlags.forParameter(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), "synthetic mandated"),
                     Arguments.of(AccessFlags.forModule(Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED), "open mandated"),
                     Arguments.of(AccessFlags.forModuleRequires(Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE), "transitive static_phase"),
                     Arguments.of(AccessFlags.forModuleOpens(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), "synthetic mandated"),
                     Arguments.of(AccessFlags.forModuleExports(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), "synthetic mandated"));
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
