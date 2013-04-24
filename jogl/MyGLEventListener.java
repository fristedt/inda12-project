package first_test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;

import com.jogamp.common.nio.Buffers;

public class MyGLEventListener implements GLEventListener {
	private int[] vertexBufferId;
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		final GL2 gl = glDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        // Zoom out
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vertexBufferId[0]);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		
		gl.glVertexPointer(3, GL.GL_FLOAT, 3 * Buffers.SIZEOF_FLOAT, 0);
		gl.glDrawArrays(GL2.GL_TRIANGLES, 0, 3);
		
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
        // drawUsingVertexPointer(glDrawable);
        
        // Swap buffer is done automatically.
	}
	
	private void initVertexBuffer(GLAutoDrawable glDrawable) {
		GL2 gl = glDrawable.getGL().getGL2();
		
		float vertices[] = {
				-1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				1.0f, -1.0f, 0.0f
		};
		
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(4 * 3 * 3);           
		vertexByteBuffer.order(ByteOrder.nativeOrder());      
		FloatBuffer vertexBuffer = vertexByteBuffer.asFloatBuffer();          
		vertexBuffer.put(vertices);
		
		vertexBufferId = new int[1];
		gl.glGenBuffers(1, vertexBufferId, 0);
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vertexBufferId[0]);
		gl.glBufferData(GL2.GL_ARRAY_BUFFER, 4 * 3 * 3, null, GL2.GL_DYNAMIC_DRAW);
		gl.glBufferSubData(GL2.GL_ARRAY_BUFFER, 0, vertexByteBuffer.capacity(), vertexByteBuffer);
		
		gl.glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
	}
	
	private void drawUsingVertexPointer(GLAutoDrawable glDrawable){
		final GL2 gl = glDrawable.getGL().getGL2();
		float vertices[] = {
				-1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f,
				1.0f, -1.0f, 0.0f};
		
		// Fucking bullshit Java shit.
		FloatBuffer tmpVertexBuffer = Buffers.newDirectFloatBuffer(vertices.length);
		for (int i = 0; i < vertices.length; i++) {
			tmpVertexBuffer.put(vertices[i]);
		}
		tmpVertexBuffer.rewind();
		
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL2.GL_FLOAT, 0, tmpVertexBuffer);
		gl.glDrawArrays(GL2.GL_TRIANGLES, 0, 3);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	@Override
	public void init(GLAutoDrawable glDrawable) {
		GL2 gl = glDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.8f, 0.3f, 0.1f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
        initVertexBuffer(glDrawable);
	}

	@Override
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
			int height) {
		GL2 gl = glDrawable.getGL().getGL2();
        final float aspect = (float) width / (float) height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        final float fh = 0.5f;
        final float fw = fh * aspect;
        gl.glFrustumf(-fw, fw, -fh, fh, 1.0f, 1000.0f);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

}
