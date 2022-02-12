package dev.turingcomplete.asmtestkit.asmutils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

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
   * @param classNode the {@link ClassNode} which to search for the
   *                  {@link FieldNode} is to; never null.
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
   * Finds the first {@link MethodNode} with the given name in the given
   * {@link ClassNode}.
   *
   * @param classNode the {@link ClassNode} which to search for the
   *                  {@link MethodNode} is to; never null.
   * @param name      the name of a {@link MethodNode}; never null.
   * @return an {@link Optional} describing the found {@link MethodNode}; never
   * null.
   */
  public static Optional<? extends MethodNode> findMethodNode(ClassNode classNode, String name) {
    Objects.requireNonNull(classNode);
    Objects.requireNonNull(name);

    return Optional.ofNullable(classNode.methods)
                   .flatMap(fieldNodes -> fieldNodes.stream()
                                                    .filter(fieldNode -> name.equals(fieldNode.name))
                                                    .findFirst());
  }

  /**
   * Creates a new {@link ClassNode} by reading the class file from the given
   * {@link InputStream}.
   *
   * @param classFile      the content of a class file as an {@link InputStream}.
   * @param asmApi         the ASM API version, e.g., {@link Opcodes#ASM9}.
   * @param parsingOptions the options to use to parse this class. One or more
   *                       of {@link ClassReader#SKIP_CODE},
   *                       {@link ClassReader#SKIP_DEBUG},
   *                       {@link ClassReader#SKIP_FRAMES} or
   *                       {@link ClassReader#EXPAND_FRAMES}.
   * @return the {@link ClassNode} of the given class file; never null.
   * @throws IOException if an I/O error occurred.
   */
  public static ClassNode readClassNode(InputStream classFile, int asmApi, int parsingOptions) throws IOException {
    var classReader = new ClassReader(Objects.requireNonNull(classFile));
    var classNode = new ClassNode(asmApi);
    classReader.accept(classNode, parsingOptions);
    return classNode;
  }

  /**
   * Creates a new {@link ClassNode} by reading the class file from the given
   * {@link InputStream}.
   *
   * @param classFile      the content of a class file as an {@link InputStream};
   *                       never null.
   * @param classVisitor   the {@link ClassVisitor} that reads the class file;
   *                       never null.
   * @param parsingOptions the options to use to parse this class. One or more
   *                       of {@link ClassReader#SKIP_CODE},
   *                       {@link ClassReader#SKIP_DEBUG},
   *                       {@link ClassReader#SKIP_FRAMES} or
   *                       {@link ClassReader#EXPAND_FRAMES}.
   * @throws IOException if an I/O error occurred.
   */
  public static void readClassFile(InputStream classFile, ClassVisitor classVisitor, int parsingOptions) throws IOException {
    Objects.requireNonNull(classFile);
    Objects.requireNonNull(classVisitor);

    var classReader = new ClassReader(classFile);
    classReader.accept(classVisitor, parsingOptions);
  }

  /**
   * Creates a copy of the given {@link ClassNode}.
   *
   * @param classNode the {@link ClassNode} to be copied; never null.
   * @return a new {@link ClassNode}; never null.
   */
  public static ClassNode copy(ClassNode classNode) {
    Objects.requireNonNull(classNode);

    var copy = new ClassNode();
    classNode.accept(copy);

    return copy;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
