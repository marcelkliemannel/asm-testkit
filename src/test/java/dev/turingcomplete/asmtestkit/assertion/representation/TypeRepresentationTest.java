package dev.turingcomplete.asmtestkit.assertion.representation;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

import static dev.turingcomplete.asmtestkit.assertion.representation.TypeRepresentation.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class TypeRepresentationTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testDefaultStringRepresentation() {
    assertThat(INSTANCE.toStringOf(Type.getType(String.class)))
            .isEqualTo("java.lang.String");

    assertThat(INSTANCE.toStringOf(Type.getType(String[].class)))
            .isEqualTo("java.lang.String[]");

    assertThat(INSTANCE.toStringOf(Type.getType(String[][].class)))
            .isEqualTo("java.lang.String[][]");
  }

  @Test
  void testArrayRepresentation() {
    assertThat(INSTANCE.toStringOf(Type.getType(String[].class)))
            .isEqualTo(Type.getType(String[].class).getClassName());

    assertThat(INSTANCE.toStringOf(Type.getType(String[][].class)))
            .isEqualTo(Type.getType(String[][].class).getClassName());
  }

  @Test
  void testClassNameStringRepresentation() {
    assertThat(new TypeRepresentation().useClassName().toStringOf(Type.getType(String.class)))
            .isEqualTo("java.lang.String");

    assertThat(new TypeRepresentation().useClassName().toStringOf(Type.getType(String[].class)))
            .isEqualTo("java.lang.String[]");

    assertThat(new TypeRepresentation().useClassName().toStringOf(Type.getType(String[][].class)))
            .isEqualTo("java.lang.String[][]");
  }

  @Test
  void testInternalNameStringRepresentation() {
    assertThat(new TypeRepresentation().useInternalName().toStringOf(Type.getType(String.class)))
            .isEqualTo("java/lang/String");

    assertThat(new TypeRepresentation().useInternalName().toStringOf(Type.getType(String[].class)))
            .isEqualTo("java/lang/String[]");

    assertThat(new TypeRepresentation().useInternalName().toStringOf(Type.getType(String[][].class)))
            .isEqualTo("java/lang/String[][]");
  }

  @Test
  void testDescriptorStringRepresentation() {
    assertThat(new TypeRepresentation().useDescriptor().toStringOf(Type.getType(String.class)))
            .isEqualTo("Ljava/lang/String;");

    assertThat(new TypeRepresentation().useDescriptor().toStringOf(Type.getType(String[].class)))
            .isEqualTo("Ljava/lang/String;[]");

    assertThat(new TypeRepresentation().useDescriptor().toStringOf(Type.getType(String[][].class)))
            .isEqualTo("Ljava/lang/String;[][]");
  }

  @Test
  void testTransformInternalName() {
    assertThat(new TypeRepresentation().useDescriptor().transformInternalName("java/lang/String"))
            .isEqualTo("Ljava/lang/String;");

    assertThat(new TypeRepresentation().useInternalName().transformInternalName("java/lang/String"))
            .isEqualTo("java/lang/String");

    assertThat(new TypeRepresentation().useClassName().transformInternalName("java/lang/String"))
            .isEqualTo("java.lang.String");
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
