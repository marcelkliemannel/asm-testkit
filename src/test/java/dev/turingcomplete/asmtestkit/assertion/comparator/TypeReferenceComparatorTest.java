package dev.turingcomplete.asmtestkit.assertion.comparator;

import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.tree.TypeAnnotationNode;

import java.io.IOException;
import java.util.List;

import static dev.turingcomplete.asmtestkit.assertion.comparator.TypeReferenceComparator.INSTANCE;
import static dev.turingcomplete.asmtestkit.compile.CompilationEnvironment.create;
import static org.assertj.core.api.Assertions.assertThat;

class TypeReferenceComparatorTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testCompare() throws IOException {
    @Language("Java")
    String typeParameterAnnotationA = "import java.lang.annotation.*;" +
                                     "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                     "@Retention(RetentionPolicy.RUNTIME)\n" +
                                     "@interface TypeParameterAnnotationA { }";
    @Language("Java")
    String typeParameterAnnotationB = "import java.lang.annotation.*;" +
                                     "@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})\n" +
                                     "@Retention(RetentionPolicy.RUNTIME)\n" +
                                     "@interface TypeParameterAnnotationB { }";
    @Language("Java")
    String myClass = "class MyClass<@TypeParameterAnnotationA @TypeParameterAnnotationB S, @TypeParameterAnnotationA T> { }";

    List<TypeAnnotationNode> visibleTypeAnnotations = create()
            .addJavaInputSource(typeParameterAnnotationA)
            .addJavaInputSource(typeParameterAnnotationB)
            .addJavaInputSource(myClass)
            .compile()
            .readClassNode("MyClass")
            .visibleTypeAnnotations;

    TypeAnnotationNode firstTypeAnnotationNode = visibleTypeAnnotations.get(0);
    TypeAnnotationNode secondTypeAnnotationNode = visibleTypeAnnotations.get(1);
    TypeAnnotationNode thirdTypeAnnotationNode = visibleTypeAnnotations.get(2);

    assertThat(INSTANCE.compare(new TypeReference(firstTypeAnnotationNode.typeRef), new TypeReference(secondTypeAnnotationNode.typeRef)))
            .isEqualTo(0);

    assertThat(INSTANCE.compare(new TypeReference(firstTypeAnnotationNode.typeRef), new TypeReference(thirdTypeAnnotationNode.typeRef)))
            .isLessThanOrEqualTo(-1);

    assertThat(INSTANCE.compare(new TypeReference(thirdTypeAnnotationNode.typeRef), new TypeReference(firstTypeAnnotationNode.typeRef)))
            .isGreaterThanOrEqualTo(1);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
