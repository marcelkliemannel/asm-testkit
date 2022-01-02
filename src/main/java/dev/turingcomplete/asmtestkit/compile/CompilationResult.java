package dev.turingcomplete.asmtestkit.compile;

import dev.turingcomplete.asmtestkit.asmutils.ClassNameUtils;
import dev.turingcomplete.asmtestkit.asmutils.ClassNodeUtils;
import dev.turingcomplete.asmtestkit.assertion.representation.DiagnosticRepresentation;
import org.assertj.core.api.ThrowingConsumer;
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
import java.util.Set;

public final class CompilationResult {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final List<Diagnostic<? extends JavaFileObject>> diagnostics;
  private final StandardJavaFileManager                    fileManager;
  private final DiagnosticRepresentation                   diagnosticRepresentation;
  private       int                                        asmApi           = Opcodes.ASM9;
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
   * @return the instance of this {@link CompilationResult}; never null.
   */
  public CompilationResult useAsmApi(int asmApi) {
    this.asmApi = asmApi;

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
   * {@code int}.
   * @return the instance of this {@link CompilationResult}; never null.
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
   * <p>Changes to the {@code ClassNode} are not written back to the class file,
   * use {@link #modifyClassNode(String, ThrowingConsumer)} instead.
   *
   * @param className a {@link String} with the fully qualified class name;
   *                  never null.
   * @return the instance of this {@link CompilationEnvironment}; never null.
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
   * <p>Changes to the {@code ClassNode} are not written back to the class file,
   * use {@link #modifyClassNodes(ThrowingConsumer)} instead.
   *
   * @return the instance of this {@link CompilationEnvironment}; never null.
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
   * <p>Changes made by the {@code ClassVisitor} are not written back to the
   * class file, use {@link #modifyClass(String, ClassVisitor)} instead.
   *
   * @param className    a {@link String} with the fully qualified class name;
   *                     never null.
   * @param classVisitor a {@link ClassVisitor}; never null.
   * @return the instance of this {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult readClass(String className, ClassVisitor classVisitor) throws IOException {
    readClassNode(className).accept(classVisitor);

    return this;
  }

  /**
   * Uses the {@link ClassVisitor} on all class file in the compiler output 
   * directory.
   *
   * <p>Changes made by the {@code ClassVisitor} are not written back to the
   * class file, use {@link #modifyClasses(ClassVisitor)} instead.
   *
   * @param classVisitor a {@link ClassVisitor}; never null.
   * @return the instance of this {@link CompilationEnvironment}; never null.
   * @throws IOException if an I/O error occurred.
   */
  public CompilationResult readClasses(ClassVisitor classVisitor) throws IOException {
    readClassNodes().forEach(classNode -> classNode.accept(classVisitor));

    return this;
  }

  public CompilationResult modifyClassNode(String className, ThrowingConsumer<ClassNode> classModification) throws IOException {
    modifyClassFile(getClassFile(className), classModification);

    return this;
  }

  public CompilationResult modifyClass(String className, ClassVisitor classModification) throws IOException {
    modifyClassFile(getClassFile(className), classNode -> classNode.accept(classModification));

    return this;
  }

  public CompilationResult modifyClassNodes(ThrowingConsumer<ClassNode> classModification) throws IOException {
    for (JavaFileObject classFile : getClassFiles()) {
      modifyClassFile(classFile, classModification);
    }

    return this;
  }

  public CompilationResult modifyClasses(ClassVisitor classVisitor) throws IOException {
    for (JavaFileObject classFile : getClassFiles()) {
      modifyClassFile(classFile, classNode -> classNode.accept(classVisitor));
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
    return ClassNodeUtils.readClassNode(classFile.openInputStream(), asmApi);
  }

  private void modifyClassFile(FileObject classFile, ThrowingConsumer<ClassNode> classModification) throws IOException {
    ClassNode classNode = readClassNode(classFile);

    classModification.accept(classNode);
    var classWriter = new ClassWriter(classWriterFlags);
    classNode.accept(classWriter);

    Files.write(fileManager.asPath(classFile), classWriter.toByteArray());
  }

  private Iterable<JavaFileObject> getClassFiles() throws IOException {
    return fileManager.list(StandardLocation.CLASS_OUTPUT, "", Set.of(JavaFileObject.Kind.CLASS), true);
  }

  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
