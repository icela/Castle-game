package game.commands.implement;

import game.Game;
import game.commands.BaseCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Eldath on 2016/7/26 0026.
 *
 * @author Eldath
 */
public class CommandReset implements BaseCommand {

    private Game game;

    public CommandReset(Game game) {
        this.game = game;
    }

    @Override
    public void runCommend(String cmd) {
        File archive = new File(System.getProperty("user.dir") + "\\save.ice");
        archive.delete();
        if (!archive.exists())
            game.echoln("存档删除成功。");
        else {
            game.echoln("存档删除失败，尝试清空存档。");
            try {
                FileWriter fw = new FileWriter(archive.getName(), false);
                fw.write("");
                fw.close();
            } catch (IOException e) {
                //TODO 异常处理。
            }
            game.clearScreen();
        }
    }
}
