package dev.turingcomplete.asmtestkit.representation;

import dev.turingcomplete.asmtestkit.asmutils.AccessKind;
import dev.turingcomplete.asmtestkit.node.AccessFlags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Stream;

import static dev.turingcomplete.asmtestkit.representation.AccessFlagsRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class AccessFlagsRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @ParameterizedTest
  @MethodSource("createAccessFlagsTestArguments")
  void testToStringOf(AccessFlags accessFlags, List<String> expected) {
    assertThat(INSTANCE.toStringOf(accessFlags))
            .isEqualTo("[" + accessFlags.access() + ": " + String.join(", ", expected) + "]");
  }

  @ParameterizedTest
  @MethodSource("createAccessFlagsTestArguments")
  void testToJavaSourceCodeRepresentation(AccessFlags accessFlags, List<String> expected) {
    assertThat(INSTANCE.toJavaSourceCodeRepresentation(accessFlags))
            .isEqualTo(String.join(" ", expected));
  }
  
  @Test
  void testInvalidAccessToStringOf() {
    assertThat(INSTANCE.toStringOf(AccessFlags.create(10, AccessKind.PARAMETER)))
            .isEqualTo("[10]");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private static Stream<Arguments> createAccessFlagsTestArguments() {
    return Stream.of(Arguments.of(AccessFlags.forClass(Opcodes.ACC_PUBLIC + Opcodes.ACC_INTERFACE), List.of("public", "interface"),
                                  Arguments.of(AccessFlags.forClass(Opcodes.ACC_INTERFACE + Opcodes.ACC_PUBLIC), List.of("public", "interface")), // Wrong order to test sort
                                  Arguments.of(AccessFlags.forField(Opcodes.ACC_TRANSIENT + Opcodes.ACC_PUBLIC), List.of("public", "transient")),
                                  Arguments.of(AccessFlags.forMethod(Opcodes.ACC_NATIVE + Opcodes.ACC_PUBLIC), List.of("public", "native")),
                                  Arguments.of(AccessFlags.forParameter(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), List.of("synthetic", "mandated")),
                                  Arguments.of(AccessFlags.forModule(Opcodes.ACC_OPEN + Opcodes.ACC_MANDATED), List.of("open", "mandated")),
                                  Arguments.of(AccessFlags.forModuleRequires(Opcodes.ACC_TRANSITIVE + Opcodes.ACC_STATIC_PHASE), List.of("transitive, static_phase")),
                                  Arguments.of(AccessFlags.forModuleOpens(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), List.of("synthetic", "mandated")),
                                  Arguments.of(AccessFlags.forModuleExports(Opcodes.ACC_SYNTHETIC + Opcodes.ACC_MANDATED), List.of("synthetic", "mandated"))));
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
