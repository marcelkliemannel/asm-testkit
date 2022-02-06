package dev.turingcomplete.asmtestkit.__helper;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.util.TextifierSupport;

import java.util.Map;

public class DummyAttribute extends Attribute implements TextifierSupport {
  // -- Class Fields ------------------------------------------------------------------------------------------------ //
  // -- Instance Fields --------------------------------------------------------------------------------------------- //

  private final String content;

  // -- Initialization ---------------------------------------------------------------------------------------------- //

  public DummyAttribute(String type) {
    this(type, null);
  }

  public DummyAttribute(String type, String content) {
    super(type);

    this.content = content;
  }

  // -- Exposed Methods --------------------------------------------------------------------------------------------- //

  @Override
  public void textify(StringBuilder outputBuilder, Map<Label, String> labelNameLookup) {
    outputBuilder.append(content);
  }

  // -- Private Methods --------------------------------------------------------------------------------------------- //
  // -- Inner Type -------------------------------------------------------------------------------------------------- //
}
