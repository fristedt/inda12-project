CP=.:/usr/share/lwjgl/jar/*:/usr/share/java/slick.jar
NATIVES=java.library.path=/usr/share/lwjgl/native/linux

run: build
	java -D$(NATIVES) -cp $(CP) MyGame

build:	classfiles

classfiles:
	javac -cp $(CP) *.java
