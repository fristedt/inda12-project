package first_test;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.Animator;

public class Main {
	private static final String windowTitle = "MY WINDOW";
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	
	public static void main(String[] args) {
		final GLCanvas canvas = new GLCanvas();
		final Frame frame = new Frame(windowTitle);
		final Animator animator = new Animator(canvas);
		canvas.addGLEventListener(new MyGLEventListener());
		canvas.addKeyListener(new MyKeyListener());
		frame.add(canvas);
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setResizable(true);
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                		animator.stop();
                        frame.dispose();
                        System.exit(0);
                }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
	}
}
