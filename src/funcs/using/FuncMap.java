package funcs.using;

import castle.Game;
import funcs.FuncSrc;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 显示地图
 * Created by asus1 on 2016/2/1.
 */
public class FuncMap extends FuncSrc {
    private boolean haveMap = false;

    public FuncMap(Game game) {
        super(game);
    }

    public void haveMap(boolean haveMap) {
        this.haveMap = haveMap;
    }

    @Override
    public void DoFunc(String cmd) {
        if (!haveMap){
            game.echoln("您还没有得到地图呢，请继续游戏以得到地图吧！");
            //TODO 记得处理掉
            game.echoln("此功能依赖pick和use，而这些功能未实现。敬请期待更新！！");
            return;
        }
        JFrame frame = new JFrame("地图");
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        Image image = Toolkit.getDefaultToolkit().getImage(
                "." + File.separator + "drawable" + File.separator + "map.png"
        );
        ImageIcon icon = new ImageIcon(image);
        label.setIcon(icon);
        frame.setSize(icon.getIconWidth(), icon.getIconHeight());
        panel.add(label);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
