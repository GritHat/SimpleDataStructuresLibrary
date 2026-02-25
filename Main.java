import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.PostDiscoveryFilter;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {

  // ========== OUTPUT MODE CONFIGURATION ==========
  // Change this to switch between output modes:
  //   "tree"    - Show test hierarchy as a tree
  //   "verbose" - Show detailed output with timing
  //   "summary" - Only show final summary (quiet mode)
  static String OUTPUT_MODE = "tree";
  // ================================================

  static public void main(String[] args) {
    System.out.println("APSD Libraries 2025!");

    if (args.length == 0) {
      printUsage();
      return;
    }

    String command = args[0];

    switch (command) {
      case "test-class":
        if (args.length < 2) {
          System.out.println("Error: Please provide a fully qualified class name.");
          System.out.println("Example: test-class zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest");
          return;
        }
        runTestClass(args[1]);
        break;

      case "test-method":
        if (args.length < 3) {
          System.out.println("Error: Please provide class name and method name.");
          System.out.println("Example: test-method zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest testMethodName");
          return;
        }
        runTestMethod(args[1], args[2]);
        break;

      case "test-nested":
        if (args.length < 4) {
          System.out.println("Error: Please provide concrete class, nested class name, and method name.");
          System.out.println("Example: test-nested zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest DynVectorStressTests RapidExpandShrinkCycles");
          return;
        }
        runNestedTestMethod(args[1], args[2], args[3]);
        break;

      case "test-filter":
        if (args.length < 3) {
          System.out.println("Error: Please provide class name and method name pattern.");
          System.out.println("Example: test-filter zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest RapidExpandShrinkCycles");
          return;
        }
        runTestWithFilter(args[1], args[2]);
        break;

      case "list-nested":
        if (args.length < 2) {
          System.out.println("Error: Please provide a fully qualified class name.");
          return;
        }
        listNestedClasses(args[1]);
        break;
      
      case "debug":
        debug();
        break;

      default:
        printUsage();
    }
  }

  // ========== DEBUG METHOD ==========
  // Add your temporary debugging code here.
  // This method is called when Main is executed without arguments.
  static void debug() {
    System.out.println("\n>>> DEBUG MODE <<<\n");
    
    // Add your custom debugging code below:
    runTestWithFilter("zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest", "ExpandWithZero");
    
  }

  static void printUsage() {
    System.out.println("\nUsage:");
    System.out.println("  test-class <fully.qualified.ClassName>");
    System.out.println("    Run all tests in a class (including inherited nested tests)");
    System.out.println();
    System.out.println("  test-method <fully.qualified.ClassName> <methodName>");
    System.out.println("    Run a specific test method (non-nested)");
    System.out.println();
    System.out.println("  test-filter <ConcreteClass> <methodNamePattern>");
    System.out.println("    Run tests matching the method name pattern (works with nested tests!)");
    System.out.println();
    System.out.println("  test-nested <ConcreteClass> <NestedClassName> <methodName>");
    System.out.println("    Run a specific test method inside a @Nested class");
    System.out.println();
    System.out.println("  list-nested <fully.qualified.ClassName>");
    System.out.println("    List all nested test classes and their methods");
    System.out.println();
    System.out.println("Examples:");
    System.out.println("  test-class zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest");
    System.out.println("  test-filter zapsdtest.simpletest.apsd.classes.containers.sequences.DynVectorITest RapidExpandShrinkCycles");
  }

  static void listNestedClasses(String className) {
    try {
      Class<?> clazz = Class.forName(className);
      System.out.println("\nNested classes in " + className + ":\n");
      
      // Check both declared and inherited nested classes
      List<Class<?>> allNested = new ArrayList<>();
      Class<?> current = clazz;
      while (current != null && current != Object.class) {
        for (Class<?> nested : current.getDeclaredClasses()) {
          allNested.add(nested);
        }
        current = current.getSuperclass();
      }
      
      for (Class<?> nested : allNested) {
        System.out.println("  " + nested.getSimpleName() + ":");
        for (Method m : nested.getDeclaredMethods()) {
          if (m.isAnnotationPresent(org.junit.jupiter.api.Test.class)) {
            System.out.println("    - " + m.getName());
          }
        }
      }
    } catch (ClassNotFoundException e) {
      System.out.println("Error: Class not found: " + className);
    }
  }

  static void runTestClass(String className) {
    System.out.println("\n>>> Running all tests in: " + className + "\n");

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectClass(className))
        .build();

    executeTests(request);
  }

  static void runTestMethod(String className, String methodName) {
    System.out.println("\n>>> Running test method: " + className + "#" + methodName + "\n");

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectMethod(className, methodName))
        .build();

    executeTests(request);
  }

  static void runTestWithFilter(String className, String methodPattern) {
    System.out.println("\n>>> Running tests in " + className + " matching: " + methodPattern + "\n");

    // Create a post-discovery filter to match by method name
    PostDiscoveryFilter methodFilter = new PostDiscoveryFilter() {
      @Override
      public FilterResult apply(TestDescriptor testDescriptor) {
        String displayName = testDescriptor.getDisplayName();
        String uniqueId = testDescriptor.getUniqueId().toString();
        
        // Include if it's a container (class/nested class) or if method name matches
        if (testDescriptor.isContainer()) {
          return FilterResult.includedIf(true);
        }
        
        // Check if the unique ID or display name contains our pattern
        if (uniqueId.contains(methodPattern) || displayName.contains(methodPattern)) {
          return FilterResult.included("Matches pattern: " + methodPattern);
        }
        
        return FilterResult.excluded("Does not match pattern: " + methodPattern);
      }
    };

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectClass(className))
        .filters(methodFilter)
        .build();

    executeTests(request);
  }

  static void runNestedTestMethod(String concreteClassName, String nestedClassName, String methodName) {
    // For inherited nested classes, we need to find the nested class from the hierarchy
    try {
      Class<?> concreteClass = Class.forName(concreteClassName);
      Class<?> nestedClass = findNestedClass(concreteClass, nestedClassName);
      
      if (nestedClass == null) {
        System.out.println("Error: Nested class '" + nestedClassName + "' not found in " + concreteClassName + " or its parents.");
        System.out.println("Use 'list-nested " + concreteClassName + "' to see available nested classes.");
        return;
      }

      // Use the filter approach for nested tests since direct selection doesn't work with abstract parents
      System.out.println("\n>>> Running nested test via filter: " + nestedClassName + "#" + methodName + "\n");
      runTestWithNestedFilter(concreteClassName, nestedClassName, methodName);
      
    } catch (ClassNotFoundException e) {
      System.out.println("Error: Class not found: " + concreteClassName);
    }
  }

  static void runTestWithNestedFilter(String className, String nestedClassName, String methodName) {
    // Create a post-discovery filter to match by nested class and method name
    PostDiscoveryFilter nestedMethodFilter = new PostDiscoveryFilter() {
      @Override
      public FilterResult apply(TestDescriptor testDescriptor) {
        String uniqueId = testDescriptor.getUniqueId().toString();
        
        // Include if it's a container
        if (testDescriptor.isContainer()) {
          return FilterResult.includedIf(true);
        }
        
        // Check if uniqueId contains both the nested class name and method name
        if (uniqueId.contains(nestedClassName) && uniqueId.contains(methodName)) {
          return FilterResult.included("Matches nested: " + nestedClassName + "#" + methodName);
        }
        
        return FilterResult.excluded("Does not match");
      }
    };

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectClass(className))
        .filters(nestedMethodFilter)
        .build();

    executeTests(request);
  }

  static Class<?> findNestedClass(Class<?> clazz, String nestedName) {
    Class<?> current = clazz;
    while (current != null && current != Object.class) {
      for (Class<?> nested : current.getDeclaredClasses()) {
        if (nested.getSimpleName().equals(nestedName)) {
          return nested;
        }
      }
      current = current.getSuperclass();
    }
    return null;
  }

  static void executeTests(LauncherDiscoveryRequest request) {
    // Flush stdout before running tests
    System.out.flush();
    
    Launcher launcher = LauncherFactory.create();
    SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();

    launcher.registerTestExecutionListeners(summaryListener);
    
    // Add custom listener based on OUTPUT_MODE
    if ("verbose".equals(OUTPUT_MODE) || "tree".equals(OUTPUT_MODE)) {
      launcher.registerTestExecutionListeners(new DetailedTestListener());
    }

    launcher.execute(request);
    
    // Flush stdout after tests to ensure all output is visible
    System.out.flush();

    TestExecutionSummary summary = summaryListener.getSummary();
    
    System.out.println("\n========== TEST SUMMARY ==========");
    System.out.println("Tests found:     " + summary.getTestsFoundCount());
    System.out.println("Tests started:   " + summary.getTestsStartedCount());
    System.out.println("Tests succeeded: " + summary.getTestsSucceededCount());
    System.out.println("Tests failed:    " + summary.getTestsFailedCount());
    System.out.println("Tests skipped:   " + summary.getTestsSkippedCount());
    System.out.println("==================================\n");

    if (summary.getTestsFailedCount() > 0) {
      System.out.println("FAILURES:");
      summary.printFailuresTo(new PrintWriter(System.out, true));
    }
  }

  // Custom test execution listener for detailed output
  static class DetailedTestListener implements TestExecutionListener {
    private int depth = 0;
    private long startTime;

    private String indent() {
      return "  ".repeat(depth);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
      startTime = System.currentTimeMillis();
      if (testIdentifier.isContainer()) {
        if ("tree".equals(OUTPUT_MODE)) {
          System.out.println(indent() + "├─ " + testIdentifier.getDisplayName());
        } else {
          System.out.println(indent() + "▶ " + testIdentifier.getDisplayName());
        }
        depth++;
      } else {
        if ("verbose".equals(OUTPUT_MODE)) {
          System.out.print(indent() + "  ● " + testIdentifier.getDisplayName() + " ... ");
        }
      }
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult result) {
      long elapsed = System.currentTimeMillis() - startTime;
      
      if (testIdentifier.isContainer()) {
        depth--;
      } else {
        String status = switch (result.getStatus()) {
          case SUCCESSFUL -> "✓ PASSED";
          case FAILED -> "✗ FAILED";
          case ABORTED -> "⊘ ABORTED";
        };
        
        if ("verbose".equals(OUTPUT_MODE)) {
          System.out.println(status + " (" + elapsed + "ms)");
        } else if ("tree".equals(OUTPUT_MODE)) {
          String symbol = switch (result.getStatus()) {
            case SUCCESSFUL -> "✓";
            case FAILED -> "✗";
            case ABORTED -> "⊘";
          };
          System.out.println(indent() + "  " + symbol + " " + testIdentifier.getDisplayName());
        }
      }
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
      System.out.println(indent() + "  ○ " + testIdentifier.getDisplayName() + " [SKIPPED: " + reason + "]");
    }
  }

}
