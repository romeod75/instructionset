package za.co.logoinstructor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Provides UI for output.
 *
 * @author Romeo Dickason
 * @version 1.0
 */
public class LOGOInstructorFrame extends JFrame
{
	private static final long serialVersionUID = 1619806575344714467L;

	private GraphicsPane graphicsPane;
	private JTextArea outputTA;

	public void setOutputTA(JTextArea outputTA)
	{
		this.outputTA = outputTA;
	}

	public LOGOInstructorFrame()
	{
		init();
	}
	
	/**
	 * Initialise and display the JFrame
	 */
	private void init()
	{
		setTitle("LOGO Instructor");	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		// Frame size and position
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		setSize(xSize - 100, ySize - 100);
		setMinimumSize(new Dimension(xSize - 100,ySize - 100));
		setResizable(false);
		setLocationRelativeTo(null);
		// main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder("LOGO Instructor"));
		// The panel on which output will be shown
		outputTA = new JTextArea();	
		outputTA.setEditable(false);
		// Canvas for graphic
		// The canvas on which image is drawn
		graphicsPane = new GraphicsPane();
		graphicsPane.setMinimumSize(new Dimension(xSize - 100,ySize - 100));
		// 2 scroll panes
		JScrollPane outputScrollpane = new JScrollPane(outputTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane graphicScrollpane = new JScrollPane(graphicsPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		graphicScrollpane.setMinimumSize(new Dimension(xSize - 100,ySize - 100));
		// SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, outputScrollpane, graphicScrollpane);
        splitPane.setDividerLocation((xSize - 100)/2);

        mainPanel.add(splitPane, BorderLayout.CENTER);
 		getContentPane().add(mainPanel);
		pack();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				setVisible(true);			
			}
		});		
	}

	public GraphicsPane getGraphicsPane()
	{
		return graphicsPane;
	}
		
	public JTextArea getOutputTA()
	{
		return outputTA;
	}

}
