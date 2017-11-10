import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BookFrame extends JFrame {
	/**
	 * Constructs the book frame as per the requirement.
	 */
	public BookFrame() {
		setTitle(Constants.PROJECT_TITLE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int width = 400, height = 200;
		setBounds((d.width - width)/2, (d.height - height)/2, width, height);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				BookDB.close();
				System.exit(0);
			}
		});
		Container contentPane = getContentPane();
		BookPanel panel = new BookPanel();
		contentPane.add(panel);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new BookFrame();
		frame.setVisible(true);
	}
}