package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils;
import dev.turingcomplete.asmtestkit.assertion.AsmAssertions;
import org.assertj.core.api.Assertions;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    @Language("Java")
    String myClass = "package foo.bar;" +
                     "class MyClass {" +
                     "  class MyInnerClass {}" +
                     "}";

    CompilationResult result = CompilationEnvironment
            .create()
            .addJavaInputSource(myClass)
            .compile();

    Assertions.assertThat(result.readClassNode("foo.bar.MyClass").name)
              .isEqualTo("foo/bar/MyClass");

    Assertions.assertThat(result.readClassNode("foo.bar.MyClass$MyInnerClass").name)
              .isEqualTo("foo/bar/MyClass$MyInnerClass");
  }

  @Test
  void testReadClassNode_notFound() {
    Assertions.assertThatThrownBy(() -> CompilationEnvironment
                      .create()
                      .compile()
                      .readClassNode("DoesNotExist"))
              .isInstanceOf(NoSuchFileException.class);
  }

  @Test
  void testReadClassNodes() throws IOException {
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
  void testReadClass() throws IOException {
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
  void testReadClasses() throws IOException {
    Map<String, String> testSources = createTestSources(10);
    Set<String> actualClassNames = new HashSet<>();
    CompilationEnvironment
            .create()
            .addJavaInputSources(testSources.values())
            .compile()
            .readClasses(new ClassVisitor(Opcodes.ASM9) {
              @Override
              public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                actualClassNames.add(ClassNameUtils.toClassName(name));
              }
            });

    assertThat(actualClassNames).containsExactlyInAnyOrderElementsOf(testSources.keySet());
  }

  @Test
  void testModifyClassNode() throws IOException {
    @Language("Java")
    String actualSource = "class MyClass {}";

    ClassNode actual = CompilationEnvironment
            .create()
            .addJavaInputSource(actualSource)
            .compile()
            .modifyClassNode("MyClass", classNode -> {
              // Add new field
              classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "myField", "I", null, null));
            })
            .readClassNode("MyClass");

    @Language("Java")
    String expectedSource = "class MyClass {" +
                            "  public int myField;" +
                            "}";

    ClassNode expected = CompilationEnvironment
            .create()
            .addJavaInputSource(expectedSource)
            .compile()
            .readClassNode("MyClass");


    AsmAssertions.assertThat(actual)
                 .isEqualTo(expected);
  }

  @Test
  void testModifyClassNodes() throws IOException {
    List<String> actualSources = new ArrayList<>();
    List<String> expectedSources = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      @Language("Java")
      String actualSource = "class MyClass" + i + " {}";
      actualSources.add(actualSource);

      @Language("Java")
      String expectedSource = "class MyClass" + i + " {" +
                              "  public int myField;" +
                              "}";
      expectedSources.add(expectedSource);
    }

    Collection<ClassNode> actual = CompilationEnvironment
            .create()
            .addJavaInputSources(actualSources)
            .compile()
            .modifyClassNodes(classNode -> {
              // Add new field
              classNode.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "myField", "I", null, null));
            })
            .readClassNodes();

    Collection<ClassNode> expected = CompilationEnvironment
            .create()
            .addJavaInputSources(expectedSources)
            .compile()
            .readClassNodes();

    AsmAssertions.assertThatClasses(actual)
                 .containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  void testModifyClass_useVisitor() throws IOException {
    @Language("Java")
    String actualSource = "class MyClass {}";

    ClassNode actual = CompilationEnvironment
            .create()
            .addJavaInputSource(actualSource)
            .compile()
            .modifyClass("MyClass", foo -> new ClassVisitor(Opcodes.ASM9, foo) {
              @Override
              public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
                // Add new field
                visitField(Opcodes.ACC_PUBLIC, "myField", "I", null, null);
              }
            })
            .readClassNode("MyClass");

    @Language("Java")
    String expectedSource = "class MyClass {" +
                            "  public int myField;" +
                            "}";

    ClassNode expected = CompilationEnvironment
            .create()
            .addJavaInputSource(expectedSource)
            .compile()
            .readClassNode("MyClass");

    AsmAssertions.assertThat(actual)
                 .isEqualTo(expected);
  }


  @Test
  void testModifyClasses_useVisitor() throws IOException {
    List<String> actualSources = new ArrayList<>();
    List<String> expectedSources = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      @Language("Java")
      String actualSource = "class MyClass" + i + " {}";
      actualSources.add(actualSource);

      @Language("Java")
      String expectedSource = "class MyClass" + i + " {" +
                              "  public int myField;" +
                              "}";
      expectedSources.add(expectedSource);
    }

    Collection<ClassNode> actual = CompilationEnvironment
            .create()
            .addJavaInputSources(actualSources)
            .compile()
            .modifyClasses(classVisitor -> new ClassVisitor(Opcodes.ASM9, classVisitor) {
              @Override
              public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
                // Add new field
                visitField(Opcodes.ACC_PUBLIC, "myField", "I", null, null);
              }
            })
            .readClassNodes();

    Collection<ClassNode> expected = CompilationEnvironment
            .create()
            .addJavaInputSources(expectedSources)
            .compile()
            .readClassNodes();

    AsmAssertions.assertThatClasses(actual)
                 .containsExactlyInAnyOrderElementsOf(expected);
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
