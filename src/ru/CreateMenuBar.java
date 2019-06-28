package ru;

import java.awt.*;
import javax.swing.JFrame;
import ru.avalon.java.ui.AbstractFrame;

public class CreateMenuBar {

    private MenuBar mbar = new MenuBar();

    public CreateMenuBar(MenuBar mbar) {
        this.mbar = mbar;
    }

    public void CreateBar() {
        Menu fileBar = new Menu("File");
        MenuItem item1 = new MenuItem("New Project...");
        MenuItem item2 = new MenuItem("Open Project...");
        MenuItem item3 = new MenuItem("Close Project");
        fileBar.add(item1);
        fileBar.add(item2);
        fileBar.add(item3);

        Menu editBar = new Menu("Edit");
        Menu viewBar = new Menu("View");
        Menu optionsBar = new Menu("Options");
        Menu helpBar = new Menu("Help");

        mbar.add(fileBar);
        mbar.add(editBar);
        mbar.add(viewBar);
        mbar.add(optionsBar);
        mbar.add(helpBar);
    }
}
