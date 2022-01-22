package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CompilationResultTest {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //
  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Test
  void testNoInputSources() throws IOException {
    CompilationEnvironment.create()
                          .compile();
  }

  @Test
  void testReadClassNode() throws IOException {
    Map<String, String> testSources = createTestSources(10);
    CompilationResult result = CompilationEnvironment
            .create()
            .addJavaInputSources(testSources.values())
            .compile();

    for (String expectedClassName : testSources.keySet()) {
      ClassNode actualClassNode = result.readClassNode(expectedClassName);
      assertThat(actualClassNode).isNotNull();
      assertThat(actualClassNode.name).isEqualTo(ClassNameUtils.toInternalName(expectedClassName));
    }
  }

  @Test
  void testReadClassNodes() throws IOException {
    // TODO: test class not found
    Map<String, String> testSources = createTestSources(20);
    CompilationResult result = CompilationEnvironment
            .create()
            .addJavaInputSources(testSources.values())
            .compile();

    Collection<ClassNode> actualClassNodes = result.readClassNodes();
    assertThat(actualClassNodes.stream().map(classNode -> ClassNameUtils.toClassName(classNode.name)))
              .containsExactlyInAnyOrderElementsOf(testSources.keySet());
  }

  @Test
  void testVisitClass() throws IOException {
    Map<String, String> testSources = createTestSources(10);
    CompilationResult result = CompilationEnvironment
            .create()
            .addJavaInputSources(testSources.values())
            .compile();

    for (String expectedClassName : testSources.keySet()) {
      result.readClass(expectedClassName, new ClassVisitor(Opcodes.ASM9) {
        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
          assertThat(name).isEqualTo(ClassNameUtils.toInternalName(expectedClassName));
        }
      });
    }
  }

  @Test
  void testVisitClasses() throws IOException {
    Map<String, String> testSources = createTestSources(10);
    CompilationResult result = CompilationEnvironment
            .create()
            .addJavaInputSources(testSources.values())
            .compile();

    Set<String> actualClassNames = new HashSet<>();
    result.readClasses(new ClassVisitor(Opcodes.ASM9) {
      @Override
      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        actualClassNames.add(ClassNameUtils.toClassName(name));
      }
    });

    assertThat(actualClassNames).containsExactlyInAnyOrderElementsOf(testSources.keySet());
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private Map<String, String> createTestSources(int num) {
    Map<String, String> testSources = new HashMap<>();

    for (int i = 0; i < num; i++) {
      testSources.put("foo.bar.MyClass" + i, "package foo.bar; public class MyClass" + i + " {}");
    }

    return testSources;
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
