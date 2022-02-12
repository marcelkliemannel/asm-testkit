package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils;
import dev.turingcomplete.asmtestkit.asmutils.ClassNodeUtils;
import dev.turingcomplete.asmtestkit.common.ThrowingFunction;
import dev.turingcomplete.asmtestkit.representation.DiagnosticRepresentation;
import org.assertj.core.api.ThrowingConsumer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompilationResult {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final List<Diagnostic<? extends JavaFileObject>> diagnostics;
  private final StandardJavaFileManager                    fileManager;
  private final DiagnosticRepresentation                   diagnosticRepresentation;
  private       int                                        asmApi           = Opcodes.ASM9;
  private       int                                        parsingOptions   = 0;
  private       int                                        classWriterFlags = ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  CompilationResult(List<Diagnostic<? extends JavaFileObject>> diagnostics,
                    StandardJavaFileManager fileManager,
                    DiagnosticRepresentation diagnosticRepresentation) {

    this.diagnostics = diagnostics;
    this.fileManager = fileManager;
    this.diagnosticRepresentation = diagnosticRepresentation;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  /**
   * Sets the ASM API version which is used in the methods of this class.
   *
   * <p>The default value is the highest version that the current ASM dependency
   * supports.
   *
   * @param asmApi the ASM API version, e.g., {@link Opcodes#ASM9}.
   * @return {@code this} {@link CompilationResult}; never null.
   */
  public CompilationResult useAsmApi(int asmApi) {
    this.asmApi = asmApi;

    return this;
  }

  /**
   * Sets the used parsing options to read a class file.
   *
   * <p>The default value is {@code 0}.
   *
   * @param parsingOptions the options to use to parse this class. One or more
   *                       of {@link ClassReader#SKIP_CODE},
   *                       {@link ClassReader#SKIP_DEBUG},
   *                       {@link ClassReader#SKIP_FRAMES} or
   *                       {@link ClassReader#EXPAND_FRAMES}.
   * @return {@code this} {@link CompilationResult}; never null.
   */
  public CompilationResult useParsingOptions(int parsingOptions) {
    this.parsingOptions = parsingOptions;

    return this;
  }

  /**
   * Sets the flags for the {@link ClassWriter} used in the modification methods
   * of this class.
   *
   * <p>The default values are {@link ClassWriter#COMPUTE_MAXS} and
   * {@link ClassWriter#COMPUTE_FRAMES}.
   *
   * @param classWriterFlags the {@link ClassWriter} flags represented as an
   *                         {@code int}.
   * @return {@code this} {@link CompilationResult}; never null.
   */
  public CompilationResult useClassWriterFlags(int classWriterFlags) {
    this.classWriterFlags = classWriterFlags;

    return this;
  }

  /**
   * Gets a {@link List} of {@link Diagnostic}s which where emitted by the
   * compiler.
   *
   * @return an unmodifiable {@link List} of {@link Diagnostic}s; never null.
   */
  public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
    return Collections.unmodifiableList(diagnostics);
  }

  /**
   * Gets the {@link StandardJavaFileManager} from the
   * {@link CompilationEnvironment}.
   *
   * @return the {@link StandardJavaFileManager}; never null.
   */
  public StandardJavaFileManager getFileManager() {
    return fileManager;
  }

  /**
   * Gets the {@link DiagnosticRepresentation} from the
   * {@link CompilationEnvironment}.
   *
   * @return the {@link DiagnosticRepresentation}; never null.
   */
  public DiagnosticRepresentation getDiagnosticRepresentation() {
    return diagnosticRepresentation;
  }

  /**
   * Creates a {@link ClassNode} from the class file of the given class name.
   *
   * <p><em>Warning:</em> Changes made on the {@code ClassNode} are not
   * written back to the class file. Use {@link #modifyClassNode(String, ThrowingConsumer)}
   * instead.
   *
   * @param className a {@link String} with the fully qualified class name;
   *                  never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public ClassNode readClassNode(String className) throws IOException {
    FileObject classFile = getClassFile(className);
    return readClassNode(classFile);
  }

  /**
   * Creates a {@link Collection} of {@link ClassNode}s of all class file in the
   * compiler output directory.
   *
   * <p><em>Warning:</em> Changes made on the {@code ClassNode} are not
   * written back to the class file. Use {@link #modifyClassNodes(ThrowingConsumer)}
   * instead.
   *
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public Collection<ClassNode> readClassNodes() throws IOException {
    List<ClassNode> classNodes = new ArrayList<>();

    for (JavaFileObject classFile : getClassFiles()) {
      classNodes.add(readClassNode(classFile));
    }

    return classNodes;
  }

  /**
   * Uses the {@link ClassVisitor} on the class file with the given name.
   *
   * <p><em>Warning:</em> Changes made by the {@code ClassVisitor} are not
   * written back to the class file. Use {@link #modifyClass(String, ThrowingFunction)}
   * instead.
   *
   * @param className   a {@link String} with the fully qualified class name;
   *                    never null.
   * @param classReader a {@link ClassVisitor}; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult readClass(String className, ClassVisitor classReader) throws IOException {
    readClassNode(className).accept(classReader);

    return this;
  }

  /**
   * Uses the {@link ClassVisitor} on all class files.
   *
   * <p><em>Warning:</em> Changes made by the {@code ClassVisitor} are not
   * written back to the class file. Use {@link #modifyClasses(ThrowingFunction)}
   * instead.
   *
   * @param classReader a {@link ClassVisitor}; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult readClasses(ClassVisitor classReader) throws IOException {
    readClassNodes().forEach(classNode -> classNode.accept(classReader));

    return this;
  }

  /**
   * Modifies the class file by writing back all changes made on the given
   * {@link ClassNode}.
   *
   * @param className     the name of the class to be modified; never null.
   * @param classModifier a {@link ThrowingConsumer} providing the
   *                      {@link ClassNode} which should be modified; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult modifyClassNode(String className, ThrowingConsumer<ClassNode> classModifier) throws IOException {
    Objects.requireNonNull(className);
    Objects.requireNonNull(classModifier);

    modifyClassFile(getClassFile(className), classModifier);

    return this;
  }

  /**
   * Modifies all class file by writing back all changes made on the given
   * {@link ClassNode}s.
   *
   * @param classModifier a {@link ThrowingConsumer} providing the
   *                      {@link ClassNode} which should be modified; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult modifyClassNodes(ThrowingConsumer<ClassNode> classModifier) throws IOException {
    Objects.requireNonNull(classModifier);

    for (JavaFileObject classFile : getClassFiles()) {
      modifyClassFile(classFile, classModifier);
    }

    return this;
  }

  /**
   * Modifies the class file by visiting it with the given {@link ClassVisitor}.
   *
   * <p> The {@link ClassVisitor} parameter given to the {@code classModifier}
   * must be passed as an argument to the returned {@link ClassVisitor}. For
   * example:
   * <pre>{@code
   * .modifyClasses(classVisitor -> new ClassVisitor(Opcodes.ASM9, classVisitor) {
   *    @Override
   *    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
   *      super.visit(version, access, name, signature, superName, interfaces);
   *      // Add new field
   *      visitField(Opcodes.ACC_PUBLIC, "myField", "I", null, null);
   *    }
   *  })
   * }</pre>
   *
   * @param className     the name of the class to be modified; never null.
   * @param classModifier a {@link ThrowingFunction} providing a  {@link ClassVisitor}
   *                      which will modify the class file  by using the given
   *                      {@link ClassVisitor} as as an argument; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult modifyClass(String className, ThrowingFunction<ClassVisitor, ClassVisitor> classModifier) throws IOException {
    modifyClassFile(getClassFile(className), classModifier);

    return this;
  }

  /**
   * Modifies all class files by visiting them with the given {@link ClassVisitor}.
   *
   * <p> The {@link ClassVisitor} parameter given to the {@code classModifier}
   * must be passed as an argument to the returned {@link ClassVisitor}. For
   * example:
   * <pre>{@code
   * .modifyClasses(classVisitor -> new ClassVisitor(Opcodes.ASM9, classVisitor) {
   *    @Override
   *    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
   *      super.visit(version, access, name, signature, superName, interfaces);
   *      // Add new field
   *      visitField(Opcodes.ACC_PUBLIC, "myField", "I", null, null);
   *    }
   *  })
   * }</pre>
   *
   * @param classModifier a {@link ThrowingFunction} providing a  {@link ClassVisitor}
   *                      which will modify the class file  by using the given
   *                      {@link ClassVisitor} as as an argument; never null.
   * @return {@code this} {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult modifyClasses(ThrowingFunction<ClassVisitor, ClassVisitor> classModifier) throws IOException {
    for (JavaFileObject classFile : getClassFiles()) {
      modifyClassFile(classFile, classModifier);
    }

    return this;
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //

  private FileObject getClassFile(String className) throws IOException {
    return fileManager.getFileForOutput(StandardLocation.CLASS_OUTPUT,
                                        ClassNameUtils.getPackage(className),
                                        ClassNameUtils.getSimpleName(className) + ".class",
                                        null);
  }

  private ClassNode readClassNode(FileObject classFile) throws IOException {
    return ClassNodeUtils.readClassNode(classFile.openInputStream(), asmApi, parsingOptions);
  }

  private void modifyClassFile(FileObject classFile, ThrowingConsumer<ClassNode> classModifier) throws IOException {
    ClassNode classNode = readClassNode(classFile);

    classModifier.accept(classNode);
    var classWriter = new ClassWriter(classWriterFlags);
    classNode.accept(classWriter);

    Files.write(fileManager.asPath(classFile), classWriter.toByteArray());
  }

  private void modifyClassFile(FileObject classFile, ThrowingFunction<ClassVisitor, ClassVisitor> classModifier) throws IOException {
    // The classModifier will modify the classNode
    ClassNode classNode = readClassNode(classFile);
    // The classModifier will visit the classNodeCopy
    // If we would use the same ClassNode for both, the changes will be made
    // twice.
    ClassNode classNodeCopy = ClassNodeUtils.copy(classNode);
    classNodeCopy.accept(classModifier.apply(classNode));

    var classWriter = new ClassWriter(classWriterFlags);
    classNode.accept(classWriter);

    Files.write(fileManager.asPath(classFile), classWriter.toByteArray());
  }

  private Iterable<JavaFileObject> getClassFiles() throws IOException {
    return fileManager.list(StandardLocation.CLASS_OUTPUT, "", Set.of(JavaFileObject.Kind.CLASS), true);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
