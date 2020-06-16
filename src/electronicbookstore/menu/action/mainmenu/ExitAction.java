package electronicbookstore.menu.action.mainmenu;

import electronicbookstore.menu.action.Action;

public class ExitAction implements Action {
    @Override
    public void execute() {
        System.exit(1);
    }
}
