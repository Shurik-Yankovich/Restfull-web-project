package bookstore.controller.action.mainmenu;

import bookstore.controller.action.Action;

public class ExitAction implements Action {
    @Override
    public void execute() {
        System.exit(1);
    }
}
