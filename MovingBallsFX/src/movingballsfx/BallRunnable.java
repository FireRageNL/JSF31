/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private BallMonitor bm;

    public BallRunnable(Ball ball, BallMonitor bm) {
        this.ball = ball;
        this.bm = bm;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if(ball.getColor() == Color.BLUE && ball.isEnteringCs()){
                    bm.enterWriter();
                }
                else if(ball.getColor() == Color.RED && ball.isEnteringCs()){
                    bm.enterReader();
                }
                else if(ball.getColor() == Color.BLUE && ball.isLeavingCs()){
                    bm.exitWriter();
                }
                else if(ball.getColor() == Color.RED && ball.isLeavingCs()){
                    bm.exitReader();
                }
                
                ball.move();
                   
                Thread.sleep(ball.getSpeed());
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                if(ball.getColor() == Color.RED && ball.isInCs()){
                    bm.exitReader();
                }
                if(ball.getColor() == Color.BLUE && ball.isInCs()){
                    bm.exitWriter();
                }
            }
        }
    }
}
