package master;

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author USER
 */
public class peta1 {
    public static void main(String[] args) {
        Browser browser = BrowserFactory.createBrowser();
        Engine engine = Engine.newInstance(EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
        .licenseKey("3GC4U6A49ZKW48X1R122P0AJ4J96VG6BDR1SYOI12OUQSE6M10EZGO7DK4KFI3W5H2UC37GV9WJS3P6DR4FODMHVQSWHEKG4RLP2V6H9OPBG8KSFL8BJSLLN0ZELO3KPFY4HEBTJJFIWO4OG").build());
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(browser.getComponent(), BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        browser.navigate("https://www.google.com");
    }
}
