package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public final class ClassNodeUtils {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private ClassNodeUtils() {
    throw new UnsupportedOperationException();
  }

  // -- Initialization ---------------------------------------------------------------------------------------------- //
  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Finds a {@link FieldNode} with the given name in the given {@link ClassNode}.
   *
   * @param classNode the {@link ClassNode} in which a {@link FieldNode} is to
   *                  be searched for; never null.
   * @param name      the name of a {@link FieldNode}; never null.
   * @return an {@link Optional} describing the found {@link FieldNode}; never
   * null.
   */
  public static Optional<FieldNode> findFieldNode(ClassNode classNode, String name) {
    Objects.requireNonNull(classNode);
    Objects.requireNonNull(name);

    return Optional.ofNullable(classNode.fields)
                   .flatMap(fieldNodes -> fieldNodes.stream()
                                                    .filter(fieldNode -> name.equals(fieldNode.name))
                                                    .findFirst());
  }

  /**
   * Creates a new {@link ClassNode} by reading the class file from the given
   * {@link InputStream}.
   *
   * @param classFile the content of a class file as an {@link InputStream}.
   * @param asmApi the ASM API version, e.g., {@link Opcodes#ASM9}.
   * @return the {@link ClassNode} of the given class file; never null.
   * @throws IOException if an I/O error occurred.
   */
  public static ClassNode readClassNode(InputStream classFile, int asmApi) throws IOException {
    var classReader = new ClassReader(Objects.requireNonNull(classFile));
    var classNode = new ClassNode(asmApi);
    classReader.accept(classNode, 0);
    return classNode;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
