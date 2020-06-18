package bookstore.menu.action.mainmenu;

import bookstore.menu.action.Action;

public class ExitAction implements Action {
    @Override
    public void execute() {
        System.exit(1);
    }
}
