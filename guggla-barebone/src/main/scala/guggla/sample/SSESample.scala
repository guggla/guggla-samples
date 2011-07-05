package guggla.sample

import javax.script.{ ScriptEngineFactory, ScriptEngine, ScriptContext }
import java.io.StringWriter
import org.osgi.service.component.ComponentContext;

class SSESample {

  var sef: ScriptEngineFactory = null;

  protected[sample] def activate(context: ComponentContext) = {
    println("SSESample activated.");
    println("SSESample factory " + sef);
    println("test: " + testSimple());
  }

  def setScriptEngineFactory(sef: ScriptEngineFactory) = {
    println("setScriptEngineFactory: " + sef);
    this.sef = sef;
  }

  def unsetScriptEngineFactory(sef: ScriptEngineFactory) = {
    println("unsetScriptEngineFactory: " + sef);
    this.sef = null;
  }

  def testSimple() {

    //create the script
    var code = new StringBuilder();
    code.append("package guggla{");
    code.append("\n");
    code.append("class Script(args: ScriptArgs) {");
    code.append("\n");
    code.append("import args._");
    code.append("\n");
    code.append("println(\"output:\" + obj) ");
    code.append("\n");
    code.append("}}");

    //get the script engine
    val scriptEngine: ScriptEngine = sef.getScriptEngine();
    val b = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
    b.put("obj", "hello");

    //get a reference to the output
    val writer = new StringWriter();
    scriptEngine.getContext().setWriter(writer);
    scriptEngine.eval(code.toString(), b)

    writer.toString.trim();
  }
}