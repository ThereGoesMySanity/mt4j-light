package basic;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.mt4j.AbstractMTApplication;
import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.util.logging.Log4jLogger;
import org.mt4j.util.logging.MTLoggerFactory;

public class SimpleSwing extends AbstractMTApplication {

	public SimpleSwing(Window window) {
		super(window);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		MTLoggerFactory.setLoggerProvider(new Log4jLogger());
		AbstractMTApplication app = new SimpleSwing(frame);

		AbstractMTLayer<JPanel> touchLayer = new AbstractMTLayer<JPanel>(app);
		touchLayer.registerInputProcessor(new PanProcessorTwoFingers(500));
		JLayer<JPanel> layer = new JLayer<JPanel>(panel, touchLayer);
		frame.add(layer);
		
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
