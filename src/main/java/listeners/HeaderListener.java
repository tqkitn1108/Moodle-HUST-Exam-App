package listeners;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;

public interface HeaderListener extends EventHandler {
    public void addAddressToBreadcrumbs(String address);
    public void removeAddress(Integer number);
    public void showEditingBtn();
    public void hideEditingBtn();
    public void showMenuButton();
    public void hideMenuButton();
}
