/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daan
 */
public class WatchServiceRunnable implements Runnable {

    private final WatchService watcher;  // the service object who processes events for us
    private Path dir;
    WatchKey key;
    private KochManager km;

    public WatchServiceRunnable(Path dir, KochManager km) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.dir = dir;
        this.km = km;

    }

    @Override
    public void run() {
        try {
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            while (true) {
                key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;

                    Path filename = ev.context();
                    Path child = dir.resolve(filename);

                    WatchEvent.Kind kind = ev.kind();
                    if (kind == ENTRY_CREATE) {
                        System.out.println(child + " created");     
                            km.loadMemoryMappedFile();
                    }
                    if (kind == ENTRY_DELETE) {
                        System.out.println(child + " deleted");
                    }
                    if (kind == ENTRY_MODIFY) {
                        System.out.println(child + " modified");
                    }
                }
                key.reset();

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(WatchServiceRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
