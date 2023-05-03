SOURCES=$(shell find ./main/ -name '*.java')
MAIN= ../../EXE/DriverPresentacio.class
$(MAIN): $(SOURCES)
	@mkdir -p ../../EXE
	@javac -d ../../EXE -sourcepath ./main/ ./main/DriverPresentacio.java

distclean:
	rm -r ../../EXE/*

.SILENT:
run: $(MAIN)
	-java -cp ../../EXE DriverPresentacio

.SILENT:
Test%:
	@javac -d ../../EXE -sourcepath ./main/ ./test/*.java 
	-java -cp ../../EXE:../../lib/* org.junit.runner.JUnitCore $@
