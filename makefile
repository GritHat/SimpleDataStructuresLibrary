# Java Compiler, Java Virtual Machine, JUnit Console, and JUnit Libraries
JavaC  = javac
JavaVM = java
JUnitC = ./zapsdtest/zjunit/junit-platform-console-standalone-1.12.1.jar
JUnitL = ./zapsdtest/zjunit/*

# Source files
MainSrc := Main.java
APSDSrc := $(shell find apsd -name "*.java")
TestSrc := $(shell find zapsdtest -name "*.java")

# Class files
MainCls := $(MainSrc:.java=.class)
APSDCls := $(APSDSrc:.java=.class)
TestCls := $(TestSrc:.java=.class)

# Phony targets
.PHONY: all main run test runtest clean drun

# Default target
all: main

# Compile main and apsd sources
main: $(MainCls) $(APSDCls)
	@echo "Run with: make run"

# Execute main
run: main
	$(JavaVM) -cp .:$(JUnitC) Main $(ARGS)

# Compile tests and apsd sources
test: $(TestCls) $(APSDCls)
	@echo "Run tests with: make runtest [ClassName]"

# Capture arguments after 'runtest'
RUNTEST_ARGS := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
RUNTEST_CLASS := $(word 1,$(RUNTEST_ARGS))

# Execute JUnit tests
runtest: test
	@if [ -z "$(RUNTEST_CLASS)" ]; then \
		$(JavaVM) -jar $(JUnitC) execute --scan-class-path --details tree --class-path .; \
	else \
		JAVA_FILE=$$(find zapsdtest/simpletest -name "$(RUNTEST_CLASS).java" 2>/dev/null | head -1); \
		if [ -z "$$JAVA_FILE" ]; then \
			echo "Error: Test class '$(RUNTEST_CLASS)' not found in zapsdtest/simpletest"; \
			exit 1; \
		fi; \
		FULL_CLASS=$$(echo "$$JAVA_FILE" | sed 's|/|.|g' | sed 's|\.java$$||'); \
		echo "Found: $$FULL_CLASS"; \
		$(JavaVM) -jar $(JUnitC) execute --select-class=$$FULL_CLASS --details tree --class-path .; \
	fi

# Build groups with a single javac invocation
$(MainCls): $(MainSrc) $(APSDCls)
	$(JavaC) -cp .:$(JUnitL) $(MainSrc)

$(APSDCls): $(APSDSrc)
	$(JavaC) -cp . $(APSDSrc)

$(TestCls): $(TestSrc) $(APSDCls)
	$(JavaC) -cp .:$(JUnitL) $(TestSrc)

# Cleaning
clean:
	clear; find . -name "*.class" -type f -exec rm -v {} \;

# Dynamic test runner: make drun ClassName [MethodName]
# Examples:
#   make drun DynVectorITest
#   make drun DynVectorITest ExpandFromZero
#   make drun LLOrderedSetITest Empty

# Capture arguments after 'drun'
DRUN_ARGS := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
DRUN_CLASS := $(word 1,$(DRUN_ARGS))
DRUN_METHOD := $(word 2,$(DRUN_ARGS))

drun: test
	@if [ -z "$(DRUN_CLASS)" ]; then \
		echo "Usage: make drun ClassName [MethodName]"; \
		echo "Examples:"; \
		echo "  make drun DynVectorITest"; \
		echo "  make drun DynVectorITest ExpandFromZero"; \
		exit 1; \
	fi; \
	JAVA_FILE=$$(find zapsdtest/simpletest -name "$(DRUN_CLASS).java" 2>/dev/null | head -1); \
	if [ -z "$$JAVA_FILE" ]; then \
		echo "Error: Test class '$(DRUN_CLASS)' not found in zapsdtest/simpletest"; \
		exit 1; \
	fi; \
	FULL_CLASS=$$(echo "$$JAVA_FILE" | sed 's|/|.|g' | sed 's|\.java$$||'); \
	echo "Found: $$FULL_CLASS"; \
	if [ -z "$(DRUN_METHOD)" ]; then \
		$(JavaVM) -cp .:$(JUnitL) Main test-class $$FULL_CLASS; \
	else \
		$(JavaVM) -cp .:$(JUnitL) Main test-filter $$FULL_CLASS $(DRUN_METHOD); \
	fi

# Prevent make from treating arguments as targets
%:
	@:
