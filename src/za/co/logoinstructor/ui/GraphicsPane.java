package za.co.logoinstructor.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class GraphicsPane extends Canvas
{
	private static final long serialVersionUID = 1315109860295686464L;
	
	float x = 0f;
	float y = 0f;
	float pointerDirection = 90f;
	int vx[] = new int[4];
	int vy[] = new int[4];
	int centerx = 0;
	int centery = 0;
	boolean penIsDown = true;
	Color penColor = Color.BLACK;
	Vector lines = new Vector(50, 50);
	Vector colors = new Vector(50, 50);

	public GraphicsPane()
	{
		setBackground(Color.WHITE);
	}

	public void clearScreen()
	{
		x = 0f;
		y = 0f;
		pointerDirection = 90f;
		lines.removeAllElements();
		colors.removeAllElements();
		repaint();
	}

	void addLine(Graphics g, float x1, float y1, float x2, float y2)
	{
		Rectangle r;
		lines.addElement(r = new Rectangle((int) x1, (int) y1, (int) x2, (int) y2));
		colors.addElement(penColor);
		g.setColor(penColor);
		g.drawLine(centerx + r.x, centery - r.y, centerx + r.width, centery - r.height);
	}

	public void forward(float distance)
	{
		float angle = dtor(pointerDirection);
		float newX = (float) (x + distance * Math.cos(angle));
		float newY = (float) (y + distance * Math.sin(angle));

		if (penIsDown)
		{
			Graphics g = getGraphics();
			if (g != null)
			{
				addLine(g, x, y, newX, newY);
			}
			x = newX;
			y = newY;
		}
		else
		{
			x = newX;
			y = newY;
		}
	}

	public void back(float distance)
	{
		forward(-distance);
	}

	public void left(float turnAngle)
	{
		Graphics g = getGraphics();
		if (g != null)
		{
			pointerDirection += turnAngle;
			while (pointerDirection > 360f)
			{
				pointerDirection -= 360f;
			}
			while (pointerDirection < 0f)
			{
				pointerDirection += 360f;
			}
		}
	}

	public void right(float turnAngle)
	{
		left(-turnAngle);
	}

	public void paint(Graphics g)
	{
		super.paint(g);

		int n = lines.size();

		for (int i = 0; i < n; i++)
		{
			Color color = (Color) colors.elementAt(i);
			g.setColor(color);
			Rectangle r = (Rectangle) lines.elementAt(i);
			g.drawLine(centerx + r.x, centery - r.y, centerx + r.width, centery - r.height);
		}

	}

	float dtor(float degrees)
	{
		return (float) (degrees * Math.PI / 180.0);
	}

	public void setPenColor(Color newPenColor)
	{
		penColor = newPenColor;
	}

	public void penUp()
	{
		penIsDown = false;
	}

	public void penDown()
	{
		penIsDown = true;
	}

	public synchronized void reshape(int x, int y, int width, int height)
	{
		super.reshape(x, y, width, height);
		centerx = 25;
		centery = height - 25;
		repaint();
	}
}