package listeners;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

public interface NewScreenListener extends EventHandler {
    public void changeScreen(Node node);

}
