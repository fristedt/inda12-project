CP=.:/usr/share/lwjgl/jar/*:/usr/share/java/slick.jar
NATIVES=java.library.path=/usr/share/lwjgl/native/linux

run: build
	java -D$(NATIVES) -cp $(CP) MyGame

build:	MyGame.class

MyGame.class: MyGame.java
	javac -cp $(CP) MyGame.java
