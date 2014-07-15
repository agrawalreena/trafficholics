/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    private Timer timer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        
    }

    private final float TOUCH_SCALE_FACTOR = 2;
    private float x;
    private float y;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        x = e.getX();
        y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	if (timer != null) {
            		timer.cancel();
            		timer.purge();
            	}
            	timer = new Timer();
            	timer.schedule(new TimerTask() {
        			
        			@Override
        			public void run() {
        				// TODO Auto-generated method stub
        				int dir = x < getWidth() / 2 ? 1 : -1 ;
                        
                        mRenderer.setAngle(
                                mRenderer.getAngle() +
                                (dir * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                        requestRender();
        			}
        		}, 75, 75);
            	break;
            case MotionEvent.ACTION_UP:
            	timer.purge();
            	timer.cancel();
            	break;
        }

        return true;
    }
}
