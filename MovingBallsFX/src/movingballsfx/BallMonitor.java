/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author daan
 */
public class BallMonitor {
    
    private int writersActive=0;
    private int readersActive=0;
    private int readersWaiting=0;
    private int writersWaiting=0;
    private Condition okToRead;
    private Condition okToWrite;
    private Lock monLock;
    
    public BallMonitor(){
        monLock = new ReentrantLock();
        okToRead = monLock.newCondition();
        okToWrite = monLock.newCondition();
        
    }
    
    public void enterReader() throws InterruptedException{
        monLock.lock();
        try{
            while(writersActive != 0){
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
         readersActive++;
        }
        finally{
            monLock.unlock();
            System.out.println("Readers: "+readersActive+" writers: "+writersActive);
        }
    }
    
    public void exitReader(){
        monLock.lock();
        try{
            readersActive--;
            if(readersActive == 0){
                okToWrite.signal();
            }
        }
        finally{
            monLock.unlock();
            System.out.println("Readers: "+readersActive+" writers: "+writersActive);
        }
    }
    
    public void enterWriter() throws InterruptedException{
        monLock.lock();
        try{
            while(writersActive > 0 || readersActive > 0){
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
         writersActive++;
        }
        finally{
            monLock.unlock();
            System.out.println("Readers: "+readersActive+" writers: "+writersActive);
        }
    }
    
    public void exitWriter(){
        monLock.lock();
        try{
            writersActive--;
            if(readersWaiting > 0 && writersWaiting <= 0){
                okToRead.signalAll();
            }
            else{
                okToWrite.signal();
            }
        }
        finally{
            monLock.unlock();
            System.out.println("Readers: "+readersActive+" writers: "+writersActive);
        }
    }

}
